package com.example.esurat.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esurat.R;
import com.example.esurat.main.MainActivity;
import com.example.esurat.model.Login;
import com.example.esurat.model.User;
import com.example.esurat.utils.ServiceGeneratorUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_LOGIN = 0;

    ProgressDialog mProgressDialog;

    @BindView(R.id.input_username)
    EditText _usernameText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    LoginService loginService;
    Realm realm;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Realm.init(this);

        realm = Realm.getDefaultInstance();

        user = realm.where(User.class).findFirst();
        if (user != null) {
            Intent intentToMainActivityFromLoginActivity = new Intent(this, MainActivity.class);
            startActivity(intentToMainActivityFromLoginActivity);

            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }

//        _usernameText.setText("dirut");
//        _passwordText.setText("dirut");
//        _loginButton.performClick();

        _loginButton.setOnClickListener(v -> login());
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        mProgressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Authenticating...");
        mProgressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        loginService = ServiceGeneratorUtils.createService(LoginService.class);
        Call<Login> call = loginService.login(username, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                if (!Objects.requireNonNull(response.body()).getError()) {
                    User user = Objects.requireNonNull(response.body()).getUser();
                    onLoginSuccess(user);
                    mProgressDialog.dismiss();
                } else {
                    onLoginFailed();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {

            }
        });

//        new android.os.Handler().postDelayed(
//                () -> {
//                    // On complete call either onLoginSuccess or onLoginFailed
//                    onLoginSuccess();
//                    // onLoginFailed();
//                    progressDialog.dismiss();
//                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {

                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(User user) {
        _loginButton.setEnabled(true);

        realm.beginTransaction();
        RealmResults<User> realmUsers = realm.where(User.class).contains("id", user.getId()).findAll();
        if (realmUsers.size() == 0) {
            realm.copyToRealm(user);
            realm.commitTransaction();
        } else {
            realm.cancelTransaction();
        }

        Intent intentToMainActivityFromLoginActivity = new Intent(this, MainActivity.class);
        startActivity(intentToMainActivityFromLoginActivity);

        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed() {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.activity_login_linearLayout),
                R.string.login_failed,
                Snackbar.LENGTH_LONG);

        snackbar.setAction("OK", v -> snackbar.dismiss());

        snackbar.show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("Username tidak boleh kosong");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Password tidak boleh kosong");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
