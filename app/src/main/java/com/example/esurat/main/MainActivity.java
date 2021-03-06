package com.example.esurat.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.esurat.R;
import com.example.esurat.auth.LoginActivity;
import com.example.esurat.databinding.ActivityMainBinding;
import com.example.esurat.model.Surat;
import com.example.esurat.model.SuratList;
import com.example.esurat.model.User;
import com.example.esurat.profile.ProfileActivity;
import com.example.esurat.utils.EndlessRecyclerOnScrollListenerUtils;
import com.example.esurat.utils.RecyclerItemClickSupportUtils;
import com.example.esurat.utils.RecyclerViewPositionUtils;
import com.example.esurat.utils.ServiceGeneratorUtils;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ActivityMainBinding mActivityHomeBinding;
    List<Surat> mSuratList;
    private Animator mAnimator;
    private MainAdapter mMainAdapter;
    User user;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setRecyclerViewAdapter();

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();

        Log.d(TAG, "onCreate: " + user);

        getSuratList();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name_list);
    }

    private void setRecyclerViewAdapter() {
        Comparator<Surat> comparator = new SortedListAdapter.ComparatorBuilder<Surat>()
                .setOrderForModel(Surat.class, (a, b) -> Long.signum(a.getNo() - b.getNo()))
                .build();

        mMainAdapter = new MainAdapter(this, Surat.class, comparator);

        SortedListAdapter.Callback sortedListAdapterCallback = new SortedListAdapter.Callback() {
            @Override
            public void onEditStarted() {
                if (mActivityHomeBinding.activityMainProgressBar.getVisibility() != View.VISIBLE) {
                    mActivityHomeBinding.activityMainProgressBar.setVisibility(View.VISIBLE);
                    mActivityHomeBinding.activityMainProgressBar.setAlpha(0.0f);
                }

                if (mAnimator != null) {
                    mAnimator.cancel();
                }

                mAnimator = ObjectAnimator.ofFloat(
                        mActivityHomeBinding.activityMainProgressBar,
                        View.ALPHA,
                        1.0f);
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.start();

                mActivityHomeBinding.activityMainRecyclerView.animate().alpha(0.5f);
            }

            @Override
            public void onEditFinished() {
                mActivityHomeBinding.activityMainRecyclerView.scrollToPosition(0);
                mActivityHomeBinding.activityMainRecyclerView.animate().alpha(1.0f);

                if (mAnimator != null) {
                    mAnimator.cancel();
                }

                mAnimator = ObjectAnimator.ofFloat(
                        mActivityHomeBinding.activityMainProgressBar,
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
                            mActivityHomeBinding.activityMainProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
                mAnimator.start();
            }
        };

        mMainAdapter.addCallback(sortedListAdapterCallback);

        RecyclerItemClickSupportUtils.addTo(mActivityHomeBinding.activityMainRecyclerView)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Surat currentSurat = mSuratList.get(position);
                    Intent intentToActivityMainDetail = new Intent(this, MainDetailActivity.class);
                    intentToActivityMainDetail.putExtra("surat", (Serializable) currentSurat);
                    startActivity(intentToActivityMainDetail);
        });

        mActivityHomeBinding.activityMainRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListenerUtils() {
            @Override
            public void onLoadMore(RecyclerView recyclerView, int currentPage) {
                SuratService suratService = ServiceGeneratorUtils.createService(SuratService.class);
                Call<SuratList> suratListNextCall = suratService.getListSuratNextPage(user.getId(), Integer.valueOf(currentPage).longValue());
                suratListNextCall.enqueue(new Callback<SuratList>() {
                    @Override
                    public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
                        mSuratList.addAll(Objects.requireNonNull(response.body()).getData());
                        mMainAdapter.edit().replaceAll(mSuratList).commit();
                    }

                    @Override
                    public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        mActivityHomeBinding.activityMainRecyclerView
                .setLayoutManager(new LinearLayoutManager(this));

        mActivityHomeBinding.activityMainRecyclerView
                .setAdapter(mMainAdapter);
    }

    private void getSuratList() {
        SuratService suratService = ServiceGeneratorUtils.createService(SuratService.class);
        Call<SuratList> suratListCall = suratService.getListSurat(user.getId());
        suratListCall.enqueue(new Callback<SuratList>() {
            @Override
            public void onResponse(@NonNull Call<SuratList> call, @NonNull Response<SuratList> response) {
                mSuratList = new ArrayList<>();
                mSuratList.addAll(Objects.requireNonNull(response.body()).getData());
                mMainAdapter.edit().replaceAll(mSuratList).commit();
            }

            @Override
            public void onFailure(@NonNull Call<SuratList> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItemSearch = menu.findItem(R.id.menu_main_search);
        SearchView searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                List<Surat> filteredSuratList = filterSuratList(mSuratList, query);
                mMainAdapter.edit().replaceAll(filteredSuratList).commit();
                return true;
            }
        });

        MenuItem menuItemLogout = menu.findItem(R.id.menu_main_logout);
        menuItemLogout.setOnMenuItemClickListener(item -> {
            onLogout();
            return true;
        });

        MenuItem menuItemAccount = menu.findItem(R.id.menu_main_account);
        menuItemAccount.setOnMenuItemClickListener(item -> {
           Intent intentToProfileActivityFromMainActivity = new Intent(this, ProfileActivity.class);
           startActivity(intentToProfileActivityFromMainActivity);
           return true;
        });

        return true;
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

    private List<Surat> filterSuratList(List<Surat> mSuratList, String query) {
        String lowerCasedQuery = query.toLowerCase();

        List<Surat> filteredSuratList = new ArrayList<>();

        for (Surat surat: mSuratList) {
            String id = surat.getId().toLowerCase();
            String rank = String.valueOf(surat.getNo()).toLowerCase();
            String perihal = surat.getPerihal().toLowerCase();
            String dari = surat.getDari().toLowerCase();
            String tanggalTerima = surat.getTglTerima().toLowerCase();
            String status = surat.getStatus().toLowerCase();
            String noAgenda = surat.getAgenda().toLowerCase();
            String noSurat = surat.getNoSurat().toLowerCase();
            String sifat = surat.getStatus().toLowerCase();
            String tanggalSurat = surat.getTglSurat().toLowerCase();
            String keterangan = surat.getKet().toLowerCase();
//            String linkLihatSurat = surat.getL().toLowerCase();

            if (id.contains(lowerCasedQuery) ||
                    rank.contains(lowerCasedQuery) ||
                    perihal.contains(lowerCasedQuery) ||
                    dari.contains(lowerCasedQuery) ||
                    tanggalTerima.contains(lowerCasedQuery) ||
                    status.contains(lowerCasedQuery) ||
                    noAgenda.contains(lowerCasedQuery) ||
                    noSurat.contains(lowerCasedQuery) ||
                    sifat.contains(lowerCasedQuery) ||
                    tanggalSurat.contains(lowerCasedQuery) ||
                    keterangan.contains(lowerCasedQuery)) {
                filteredSuratList.add(surat);
            }
        }

        return filteredSuratList;
    }
}
