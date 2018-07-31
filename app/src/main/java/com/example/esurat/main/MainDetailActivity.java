package com.example.esurat.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.esurat.R;
import com.example.esurat.customtabs.CustomTabActivityHelper;
import com.example.esurat.databinding.ActivityMainDetailBinding;
import com.example.esurat.model.Surat;
import com.example.esurat.utils.ServiceGeneratorUtils;

import java.io.File;
import java.util.Objects;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainDetailActivity extends AppCompatActivity {

    private static final String TAG = "MainDetailActivity";
    public static final String GOOGLE_VIEWER_URL = "https://docs.google.com/viewer?url=";

    private ActivityMainDetailBinding mActivityMainDetailBinding;
    private CustomTabActivityHelper mCustomTabActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainDetailBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_detail);

        Surat surat = (Surat) getIntent().getSerializableExtra("surat");
        mActivityMainDetailBinding.setSurat(surat);

        if (surat.getStatus().equals(MainConstant.NEW)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.error));
        }
        if (surat.getStatus().equals(MainConstant.PROCESS)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.warning));
        }
        if (surat.getStatus().equals(MainConstant.DONE)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.info));
        }

        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setEnabled(false);

        mCustomTabActivityHelper = new CustomTabActivityHelper();
        mCustomTabActivityHelper.setConnectionCallback(new CustomTabActivityHelper.ConnectionCallback() {
            @Override
            public void onCustomTabsConnected() {
                mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setEnabled(true);
            }

            @Override
            public void onCustomTabsDisconnected() {
                mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setEnabled(false);
            }
        });

        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setOnClickListener(v ->
                openCustomTabs(surat.getLinkLihatSurat()));
        mActivityMainDetailBinding.activityMainDetailButtonUploadFile.setOnClickListener(v -> {
            if (isStoragePermissionGranted()) {
                openFilePicker();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCustomTabActivityHelper.setConnectionCallback(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomTabActivityHelper.bindCustomTabsService(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCustomTabActivityHelper.unbindCustomTabsService(this);
        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Log.d(TAG, "onActivityResult: " + data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    String[] selectedDocs = data.getStringArrayExtra(FilePickerConst.KEY_SELECTED_DOCS);
                    uploadFile(selectedDocs[0]);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+ permissions[0]+ "was "+ grantResults[0]);
            //resume tasks needing this permission
            switch (permissions[0]) {
                case android.Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    mActivityMainDetailBinding.activityMainDetailButtonUploadFile.performClick();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    private void openCustomTabs(String url) {
        Uri googlePDFViewerUri = Uri.parse(GOOGLE_VIEWER_URL + url);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder(mCustomTabActivityHelper.getSession());

        // set desired toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.primary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.primary_dark));

        // add start and exit animations if you want(optional)
        intentBuilder.setStartAnimations(this,
                R.anim.push_left_in, R.anim.push_left_out);
        intentBuilder.setExitAnimations(this,
                R.anim.push_right_in, R.anim.push_right_out);

        CustomTabsIntent customTabsIntent = intentBuilder.build();

        CustomTabActivityHelper.openCustomTab(this, customTabsIntent, googlePDFViewerUri, (activity, uri) -> {
        });
    }

    private void openFilePicker() {
        FilePickerBuilder.getInstance()
                .setMaxCount(1)
                .setActivityTheme(R.style.FilePickerAppTheme)
                .pickFile(this);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void uploadFile(String filePath) {
        // create upload service client
        SuratService service = ServiceGeneratorUtils.createService(SuratService.class);

        Uri fileUri = Uri.parse(filePath);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(filePath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("document", file.getName(), requestFile);

        Log.d(TAG, "uploadFile: " + body);

        // finally, execute the request
//        Call<String> call = service.uploadSurat(body);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                Log.d(TAG, "onResponse: Upload success: " + response.body());
//            }
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                Log.d(TAG, "onFailure: Upload failed: " + t.getLocalizedMessage());
//            }
//        });
    }
}
