package com.example.esurat.main;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.esurat.R;
import com.example.esurat.auth.LoginActivity;
import com.example.esurat.customtabs.CustomTabActivityHelper;
import com.example.esurat.databinding.ActivityMainDetailBinding;
import com.example.esurat.model.Status;
import com.example.esurat.model.Surat;
import com.example.esurat.model.SuratList;
import com.example.esurat.model.User;
import com.example.esurat.profile.ProfileActivity;
import com.example.esurat.utils.ServiceGeneratorUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    Realm realm;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainDetailBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main_detail);

        setupService();

        setupRealm();

        setupSurat();

        changeSuratStatusToProses();

        setupColor();

        setupLihatSurat();

        setupUploadFile();

        setupButtonSelesai();

        setupActionBar();
    }

    private void setupService() {
        service = ServiceGeneratorUtils.createService(SuratService.class);
    }

    private void setupSurat() {
        mSurat = (Surat) getIntent().getSerializableExtra("surat");
    }

    private void changeSuratStatusToProses() {
        Call<SuratList> suratStatusToProsesCall = service.setSuratStatusToProses(user.getId(), mSurat.getId());
        suratStatusToProsesCall.enqueue(new Callback<SuratList>() {
            @Override
            public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
                SuratList suratList = Objects.requireNonNull(response.body());
                if (suratList.getData().size() > 0) {
                    mSurat = suratList.getData().get(0);
                    mActivityMainDetailBinding.setSurat(mSurat);
                    if (mSurat.getStatus().equals(MainConstant.PROSSES)) {
                        mActivityMainDetailBinding.activityMainDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.warning));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {

            }
        });
    }

    private void setupRealm() {
        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();
    }

    private void setupButtonSelesai() {
        if (mSurat.getStatus().equals(MainConstant.SELESAI)) {
            mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setEnabled(false);
            mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_dark));
        }
        mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setOnClickListener(v -> changeSuratStatusToSelesai());
    }

    private void changeSuratStatusToSelesai() {
        Call<SuratList> suratStatusToSelesaiCall = service.setSuratStatusToSelesai(user.getId(), mSurat.getId());
        suratStatusToSelesaiCall.enqueue(new Callback<SuratList>() {
            @Override
            public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
                SuratList suratList = Objects.requireNonNull(response.body());
                if (suratList.getData().size() > 0) {
                    mSurat = suratList.getData().get(0);
                    mActivityMainDetailBinding.setSurat(mSurat);
                    mActivityMainDetailBinding.activityMainDetailTextViewValueStatus
                            .setText(mSurat.getStatus());
                    mActivityMainDetailBinding.activityMainDetailTextViewValueStatus
                            .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.success));

                    Snackbar snackbar = Snackbar.make(
                            mActivityMainDetailBinding.activityMainDetailScrollView,
                            R.string.selesai_info_success,
                            Snackbar.LENGTH_LONG);

                    snackbar.setAction("OK", v -> snackbar.dismiss());

                    snackbar.show();
                    mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setEnabled(false);
                    mActivityMainDetailBinding.activityMainDetailButtonStatusSelesai.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.primary_dark));
                } else {
                    Snackbar snackbar = Snackbar.make(
                            mActivityMainDetailBinding.activityMainDetailScrollView,
                            R.string.selesai_info_failed,
                            Snackbar.LENGTH_LONG);

                    snackbar.setAction("OK", v -> snackbar.dismiss());

                    snackbar.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {

            }
        });
    }

    private void setupUploadFile() {
        mActivityMainDetailBinding.activityMainDetailButtonUploadFile.setOnClickListener(v -> {
            if (isStoragePermissionGranted()) {
                openFilePicker();
            }
        });
    }

    private void setupLihatSurat() {
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

        // TODO: Uncomment this code bellow to make pdf viewer viewed the correct pdf.
//        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setOnClickListener(v ->
//                downloadSurat(MainConstant.BASE_URL + mSurat.getPathPdf()));

        // TODO: Comment this code bellow to make pdf viewer viewed the correct pdf.
        mActivityMainDetailBinding.activityMainDetailButtonLihatSurat.setOnClickListener(v ->
                downloadSurat(MainConstant.BASE_URL + "images/gambar.pdf"));
    }

    private void downloadSurat(String url) {
        SuratService suratService = ServiceGeneratorUtils.createService(SuratService.class);
        Call<ResponseBody> call = suratService.getSurat(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.body());
                if(!response.isSuccessful()){
                    Log.e(TAG, "Something's gone wrong");
                    // TODO: show error message
                    Snackbar snackbar = Snackbar.make(
                            findViewById(R.id.activity_main_detail_scrollView),
                            R.string.download_surat_info_failed,
                            Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", v -> snackbar.dismiss());
                    snackbar.show();
                    return;
                }

                DownloadFileAsyncTask downloadFileAsyncTask =
                        new DownloadFileAsyncTask(
                                MainDetailActivity.this,
                                mActivityMainDetailBinding,
                                mSurat.getPathPdf().replace("/images/", ""));
                downloadFileAsyncTask.execute(Objects.requireNonNull(response.body()).byteStream());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage());
                Snackbar snackbar = Snackbar.make(
                        findViewById(R.id.activity_main_detail_scrollView),
                        R.string.download_surat_info_failed,
                        Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", v -> snackbar.dismiss());
                snackbar.show();
            }
        });
    }

    private static class DownloadFileAsyncTask extends AsyncTask<InputStream, Void, Boolean> {

        final String directoryName = "Surat";
        final File fileRoot = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), directoryName);
        String fileName;

        WeakReference<ActivityMainDetailBinding> mainDetailBindingRef;
        WeakReference<Context> contextRef;

        DownloadFileAsyncTask(
                Context contextRef,
                ActivityMainDetailBinding mainDetailBindingRef,
                String fileName) {
            this.contextRef = new WeakReference<>(contextRef);
            this.mainDetailBindingRef = new WeakReference<>(mainDetailBindingRef);
            this.fileName = fileName;
        }

        @Override
        protected Boolean doInBackground(InputStream... params) {
            if (!fileRoot.exists()) {
                boolean result = fileRoot.mkdir();
                Log.d(TAG, "doInBackground: Directory Created " + result);
            }
            InputStream inputStream = params[0];
            File file = new File(fileRoot, fileName);
            OutputStream output = null;
            try {
                output = new FileOutputStream(file);

                byte[] buffer = new byte[1024]; // or other buffer size
                int read;

                Log.d(TAG, "Attempting to write to: " + fileRoot + "/" + fileName);
                while ((read = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                    Log.v(TAG, "Writing to buffer to output stream.");
                }
                Log.d(TAG, "Flushing output stream.");
                output.flush();
                Log.d(TAG, "Output flushed.");
            } catch (IOException e) {
                Log.e(TAG, "IO Exception: " + e.getMessage());
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (output != null) {
                        output.close();
                        Log.d(TAG, "Output stream closed sucessfully.");
                    }
                    else{
                        Log.d(TAG, "Output stream is null");
                    }
                } catch (IOException e){
                    Log.e(TAG, "Couldn't close output stream: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            Log.d(TAG, "Download success: " + result);
            // TODO: show a snackbar or a toast
            if (result) {
                File file = new File(fileRoot, fileName);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Uri fileUri = FileProvider.getUriForFile(
                        contextRef.get(),
                        contextRef.get().getApplicationContext()
                                .getPackageName() + ".provider", file);
                intent.setDataAndType(fileUri, "application/pdf");
                contextRef.get().startActivity(intent);
            } else {
                Snackbar snackbar = Snackbar.make(
                        mainDetailBindingRef.get().activityMainDetailScrollView,
                        R.string.download_surat_info_failed,
                        Snackbar.LENGTH_LONG);

                snackbar.setAction("OK", v -> snackbar.dismiss());

                snackbar.show();
            }
        }
    }

    private void setupActionBar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name_detail);
    }

    private void setupColor() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        MenuItem menuItemLogout = menu.findItem(R.id.menu_detail_logout);
        menuItemLogout.setOnMenuItemClickListener(item -> {
            onLogout();
            return true;
        });

        MenuItem menuItemAccount = menu.findItem(R.id.menu_detail_account);
        menuItemAccount.setOnMenuItemClickListener(item -> {
            Intent intentToProfileActivityFromMainActivity = new Intent(this, ProfileActivity.class);
            startActivity(intentToProfileActivityFromMainActivity);
            return true;
        });

        return true;
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

    private void onLogout() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        Intent intentToLoginActivityFromMainActivity = new Intent(this, LoginActivity.class);
        startActivity(intentToLoginActivityFromMainActivity);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
        Call<SuratList> call = service.uploadSurat(user.getId(), mSurat.getId(), body);
        call.enqueue(new Callback<SuratList>() {
            @Override
            public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
                if (!Objects.requireNonNull(response.body()).getError()) {
                    Snackbar snackbar = Snackbar.make(
                            mActivityMainDetailBinding.activityMainDetailScrollView,
                            R.string.upload_balasan_info_success,
                            Snackbar.LENGTH_LONG);

                    snackbar.setAction("OK", v -> snackbar.dismiss());

                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(
                            mActivityMainDetailBinding.activityMainDetailScrollView,
                            R.string.upload_balasan_info_failed,
                            Snackbar.LENGTH_LONG);

                    snackbar.setAction("OK", v -> snackbar.dismiss());

                    snackbar.show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: Upload failed: " + t.getLocalizedMessage());
            }
        });
    }

    public String getMimeType(Uri uri) {
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
    }

//    private void setSuratStatus(String id, String status) {
//        Call<SuratList> call = service.setStatus(id, status);
//        Context context = this;
//        call.enqueue(new Callback<SuratList>() {
//            @Override
//            public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
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
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {
//                Log.d(TAG, "onFailure: Set status failed: " + t.getLocalizedMessage());
//            }
//        });
//    }
}
