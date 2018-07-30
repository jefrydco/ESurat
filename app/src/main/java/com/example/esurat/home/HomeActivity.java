package com.example.esurat.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.esurat.R;
import com.example.esurat.auth.LoginActivity;
import com.example.esurat.databinding.ActivityHomeBinding;
import com.example.esurat.utils.DataUtils;
import com.example.esurat.utils.DateUtils;
import com.example.esurat.utils.NetworkUtils;
import com.example.esurat.utils.RecyclerItemClickSupportUtils;
import com.example.esurat.utils.ULID;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private ULID ulid = new ULID();
    private Calendar calendar = Calendar.getInstance();

    private HomeService mHomeService;
    private Call<String> mCall;
    private Callback<String> mCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String jsonResponse = response.body();
            Log.d(TAG, "onResponse: " + jsonResponse);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    };

    private static final Comparator<HomeModel> COMPARATOR = new SortedListAdapter.ComparatorBuilder<HomeModel>()
            .setOrderForModel(HomeModel.class, (a, b) -> Integer.signum(a.getRank() - b.getRank()))
            .build();

    private HomeAdapter mHomeAdapter;
    private ActivityHomeBinding mHomeBinding;
    private Animator mAnimator;

    private List<HomeModel> mHomeModelList;

    private SortedListAdapter.Callback mHomeAdapterCallback = new SortedListAdapter.Callback() {
        @Override
        public void onEditStarted() {
            if (mHomeBinding.activityHomeProgressBar.getVisibility() != View.VISIBLE) {
                mHomeBinding.activityHomeProgressBar.setVisibility(View.VISIBLE);
                mHomeBinding.activityHomeProgressBar.setAlpha(0.0f);
            }

            if (mAnimator != null) {
                mAnimator.cancel();
            }

            mAnimator = ObjectAnimator.ofFloat(
                    mHomeBinding.activityHomeProgressBar,
                    View.ALPHA,
                    1.0f);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.start();

            mHomeBinding.activityHomeRecyclerView.animate().alpha(0.5f);
        }

        @Override
        public void onEditFinished() {
            mHomeBinding.activityHomeRecyclerView.scrollToPosition(0);
            mHomeBinding.activityHomeRecyclerView.animate().alpha(1.0f);

            if (mAnimator != null) {
                mAnimator.cancel();
            }

            mAnimator = ObjectAnimator.ofFloat(
                    mHomeBinding.activityHomeProgressBar,
                    View.ALPHA,
                    0.0f);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.addListener(new AnimatorListenerAdapter() {

                private boolean mCanceled = false;

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    mCanceled = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (!mCanceled) {
                        mHomeBinding.activityHomeProgressBar.setVisibility(View.GONE);
                    }
                }
            });
            mAnimator.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentToLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(intentToLoginActivity);

        calendar.setTimeInMillis(System.currentTimeMillis());

        mHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mHomeAdapter = new HomeAdapter(this, HomeModel.class, COMPARATOR);

        mHomeAdapter.addCallback(mHomeAdapterCallback);

        RecyclerItemClickSupportUtils.addTo(mHomeBinding.activityHomeRecyclerView).setOnItemClickListener(((recyclerView, position, v) -> {
            HomeModel currentHomeModel = mHomeModelList.get(position);
//            Toast.makeText(this, currentHomeModel.getPerihal(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "\nonCreate: HomeModel\nPosition: " + position + "\n" + currentHomeModel.toString());
            Intent intentToHomeDetailActivity = new Intent(this, HomeDetailActivity.class);
            intentToHomeDetailActivity.putExtra("home", currentHomeModel);
            startActivity(intentToHomeDetailActivity);
        }));

        mHomeModelList = generateHomeModelList(30);
        mHomeAdapter.edit().replaceAll(mHomeModelList).commit();

        mHomeBinding.activityHomeRecyclerView
                .setLayoutManager(new LinearLayoutManager(this));

        mHomeBinding.activityHomeRecyclerView
                .setAdapter(mHomeAdapter);

//        mHomeService = (HomeService) NetworkUtils.fetch(HomeService.class);
//        mCall = mHomeService.getHomeList();
//        mCall.enqueue(mCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        final MenuItem menuHomeSearch = menu.findItem(R.id.menu_home_search);
        final SearchView searchView = (SearchView) menuHomeSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                final List<HomeModel> filteredHomeModelList = filterHomeModelList(mHomeModelList, query);
                mHomeAdapter.edit()
                        .replaceAll(filteredHomeModelList)
                        .commit();
                return true;
            }
        });
        return true;
    }

    private List<HomeModel> filterHomeModelList(List<HomeModel> mHomeModelList, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<HomeModel> filteredHomeModelList = new ArrayList<>();
        for (HomeModel homeModel: mHomeModelList) {
            final String id = String.valueOf(homeModel.getId()).toLowerCase();
            final String rank = String.valueOf(homeModel.getRank()).toLowerCase();
            final String dari = String.valueOf(homeModel.getDari()).toLowerCase();
            final String perihal = String.valueOf(homeModel.getPerihal()).toLowerCase();
            final String tanggalTerima = String.valueOf(homeModel.getTanggalTerima()).toLowerCase();
            final String status = String.valueOf(homeModel.getStatus()).toLowerCase();
            final String noSurat = String.valueOf(homeModel.getNoSurat()).toLowerCase();
            final String sifat = String.valueOf(homeModel.getSifat()).toLowerCase();
            final String tanggalSurat = String.valueOf(homeModel.getTanggalSurat()).toLowerCase();

            if (id.contains(lowerCaseQuery) ||
                    rank.contains(lowerCaseQuery) ||
                    dari.contains(lowerCaseQuery) ||
                    perihal.contains(lowerCaseQuery) ||
                    tanggalTerima.contains(lowerCaseQuery) ||
                    status.contains(lowerCaseQuery) ||
                    noSurat.contains(lowerCaseQuery) ||
                    sifat.contains(lowerCaseQuery) ||
                    tanggalSurat.contains(lowerCaseQuery)) {
                filteredHomeModelList.add(homeModel);
            }
        }

        return filteredHomeModelList;
    }

    private List<HomeModel> generateHomeModelList(int count) {
        List<HomeModel> homeModelList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String[] status = {"PROSES", "SELESAI"};
            Random random = new Random();
            int selectedIndex = random.nextInt(status.length);

            calendar.add(Calendar.DATE, 1);

            homeModelList.add(new HomeModel(
                    i,
                    i,
                    String.format(
                            Locale.US,
                            "%s %d",
                            DataUtils.toSentenceCase(DataUtils.generateRandomWords(2)),
                            i),
                    String.format(
                            Locale.US,
                            "%s %d",
                            DataUtils.toSentenceCase(DataUtils.generateRandomWords(2)),
                            i),
                    DateUtils.formatDate(calendar.getTimeInMillis()),
                    status[selectedIndex],
                    String.valueOf(i),
                    ulid.nextULID(),
                    ulid.nextULID(),
                    DateUtils.formatDate(calendar.getTimeInMillis()),
                    String.format(
                            Locale.US,
                            "%s %d",
                            DataUtils.toSentenceCase(DataUtils.generateRandomWords(5)),
                            i),
                    "https://abc.xyz/investor/pdf/2018_Q1_Earnings_Transcript.pdf")
            );
        }

        return homeModelList;
    }
}
