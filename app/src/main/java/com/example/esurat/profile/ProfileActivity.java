package com.example.esurat.profile;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.esurat.R;
import com.example.esurat.auth.LoginActivity;
import com.example.esurat.databinding.ActivityProfileBinding;
import com.example.esurat.model.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding mActivityProfileBinding;
    private User user;
    private Realm realm;

    @BindView(R.id.activity_profile_nama)
    EditText profileNamaEditText;
    @BindView(R.id.activity_profile_hp)
    EditText profileHpEditText;
    @BindView(R.id.activity_profile_id_induk)
    EditText profileIdIndukEditText;
    @BindView(R.id.activity_profile_jabatan)
    EditText profileJabatanEditText;
    @BindView(R.id.activity_profile_keluar)
    Button profileKeluarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
        user = realm.where(User.class).findFirst();

        profileNamaEditText.setText(Objects.requireNonNull(user).getNama());
        profileHpEditText.setText(Objects.requireNonNull(user).getHp());
        profileIdIndukEditText.setText(Objects.requireNonNull(user).getIdInduk());
        profileJabatanEditText.setText(Objects.requireNonNull(user).getJabatan());

        disableEditText(profileNamaEditText);
        disableEditText(profileHpEditText);
        disableEditText(profileIdIndukEditText);
        disableEditText(profileJabatanEditText);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name_account);

        profileKeluarButton.setOnClickListener(v -> onLogout());
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

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
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
}
