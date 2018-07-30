package com.example.esurat.home;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.esurat.R;
import com.example.esurat.customtabs.CustomTabActivityHelper;
import com.example.esurat.databinding.ActivityHomeDetailBinding;

public class HomeDetailActivity extends AppCompatActivity {

    private static final String TAG = "HomeDetailActivity";
    public static final String GOOGLE_VIEWER_URL = "https://docs.google.com/viewer?url=";

    private ActivityHomeDetailBinding mHomeDetailBinding;
    private CustomTabActivityHelper mCustomTabActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_detail);

        HomeModel homeModel = (HomeModel) getIntent().getSerializableExtra("home");
        mHomeDetailBinding.setHome(homeModel);

        switch (homeModel.getStatus()) {
            case "PROSES":
                mHomeDetailBinding.activityHomeDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(this, R.color.error));
                break;
            case "SELESAI":
                mHomeDetailBinding.activityHomeDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(this, R.color.info));
                break;
            default:
                mHomeDetailBinding.activityHomeDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(this, R.color.error));
                break;
        }

        mHomeDetailBinding.activityHomeDetailButtonLihatSurat.setEnabled(false);

        mCustomTabActivityHelper = new CustomTabActivityHelper();
        mCustomTabActivityHelper.setConnectionCallback(new CustomTabActivityHelper.ConnectionCallback() {
            @Override
            public void onCustomTabsConnected() {
                mHomeDetailBinding.activityHomeDetailButtonLihatSurat.setEnabled(true);
            }

            @Override
            public void onCustomTabsDisconnected() {
                mHomeDetailBinding.activityHomeDetailButtonLihatSurat.setEnabled(false);
            }
        });


        mHomeDetailBinding.activityHomeDetailButtonLihatSurat.setOnClickListener(v -> {
            redirectUsingCustomTab(homeModel.getLinkLihatSurat());
        });
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
        mHomeDetailBinding.activityHomeDetailButtonLihatSurat.setEnabled(false);
    }

    private void redirectUsingCustomTab(String url) {
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
}
