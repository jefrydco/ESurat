package com.example.esurat.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.webkit.MimeTypeMap;
import android.webkit.WebView;

import com.example.esurat.R;
import com.example.esurat.customtabs.CustomTabActivityHelper;
import com.example.esurat.databinding.ActivityMainDetailBinding;
import com.example.esurat.model.Status;
import com.example.esurat.model.Surat;
import com.example.esurat.model.SuratList;
import com.example.esurat.utils.ServiceGeneratorUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDetailActivity extends AppCompatActivity {

    private static final String TAG = "MainDetailActivity";
    public static final String GOOGLE_VIEWER_URL = "https://docs.google.com/viewer?url=";

    private ActivityMainDetailBinding mActivityMainDetailBinding;
    private CustomTabActivityHelper mCustomTabActivityHelper;
    SuratService service;
    Surat mSurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainDetailBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_detail);

        mSurat = (Surat) getIntent().getSerializableExtra("surat");
        mActivityMainDetailBinding.setSurat(mSurat);
        service = ServiceGeneratorUtils.createService(SuratService.class);

        if (mSurat.getStatus().equals(MainConstant.BELUM)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.error));
        }
        if (mSurat.getStatus().equals(MainConstant.PROSSES)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.warning));
        }
        if (mSurat.getStatus().equals(MainConstant.DISPOSISI)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.info));
        }
        if (mSurat.getStatus().equals(MainConstant.SELESAI)) {
            mActivityMainDetailBinding
                    .activityMainDetailTextViewValueStatus
                    .setTextColor(ContextCompat.getColor(this, R.color.success));
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

        // TODO: Uncomment this code bellow to make pdf viewer viewe the correct pdf.
//        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setOnClickListener(v ->
//                openCustomTabs(MainConstant.BASE_URL + mSurat.getPathPdf()));

        // TODO: Comment this code bellow to make pdf viewer viewe the correct pdf.
        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setOnClickListener(v ->
                openPdfViewer(MainConstant.BASE_URL + "images/gambar_.pdf"));

        mActivityMainDetailBinding.activityMainDetailButtonUploadFile.setOnClickListener(v -> {
            if (isStoragePermissionGranted()) {
                openFilePicker();
            }
        });
        mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setOnClickListener(v -> {
            setSuratStatus(mActivityMainDetailBinding.getSurat().getId(), "DONE");
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
                    ArrayList<String> selectedDocs = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
                    uploadFile(selectedDocs.get(0));
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
//        Uri googlePDFViewerUri = Uri.parse(GOOGLE_VIEWER_URL + url);
        Uri googlePDFViewerUri = Uri.parse(url);

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

    private void openPdfViewer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
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
        Uri fileUri = Uri.parse(filePath);

        File file = new File(filePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(
                MediaType.parse(getMimeType(fileUri)),
                file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "document",
                file.getName(),
                requestFile);

        // finally, execute the request
        Call<Status> call = service.uploadSurat(body);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(@NonNull Call<Status> call, @NonNull Response<Status> response) {
                Log.d(TAG, "onResponse: Upload success: " + response.body().getStatus());
            }
            @Override
            public void onFailure(@NonNull Call<Status> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: Upload failed: " + t.getLocalizedMessage());
            }
        });
    }

    public String getMimeType(Uri uri) {
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
    }

    private void setSuratStatus(String id, String status) {
        Call<SuratList> call = service.setStatus(id, status);
        Context context = this;
        call.enqueue(new Callback<SuratList>() {
            @Override
            public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
//                Log.d(TAG, "onResponse: Set status successfully: " + Objects.requireNonNull(response.body()).getStatus());
//                if (Objects.requireNonNull(response.body()).getStatus().equals("OK")) {
//                    mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setEnabled(false);
//                    mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setAlpha(.5f);
//                    mActivityMainDetailBinding.activityMainDetailTextViewValueStatus.setText(Objects.requireNonNull(response.body()).getData().get(0).getStatus());
//                    mActivityMainDetailBinding.activityMainDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(context, R.color.info));
//                    Toast.makeText(MainDetailActivity.this, "Sukses", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainDetailActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: Set status failed: " + t.getLocalizedMessage());
            }
        });
    }
}
