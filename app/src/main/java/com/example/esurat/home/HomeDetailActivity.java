package com.example.esurat.home;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.esurat.R;
import com.example.esurat.databinding.ActivityHomeDetailBinding;

public class HomeDetailActivity extends AppCompatActivity {

    private static final String TAG = "HomeDetailActivity";

    private ActivityHomeDetailBinding mHomeDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_detail);
        mHomeDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_detail);

        HomeModel homeModel = (HomeModel) getIntent().getSerializableExtra("home");
        mHomeDetailBinding.setHome(homeModel);

        switch (homeModel.getStatus()) {
            case "PROSES":
                mHomeDetailBinding.activityHomeDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(this, R.color.info));
                break;
            case "SELESAI":
                mHomeDetailBinding.activityHomeDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(this, R.color.success));
                break;
            default:
                mHomeDetailBinding.activityHomeDetailTextViewValueStatus.setTextColor(ContextCompat.getColor(this, R.color.error));
                break;
        }

        Log.d(TAG, "onCreate: " + homeModel.toString());
    }
}
