package com.example.esurat.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esurat.R;
import com.example.esurat.model.Login;
import com.example.esurat.model.Status;
import com.example.esurat.utils.ServiceGeneratorUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_LOGIN = 0;

    ProgressDialog mProgressDialog;

    @BindView(R.id.input_username)
    EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    LoginService loginService;
    Call<Login> call;
    Callback<Login> callback = new Callback<Login>() {
        @Override
        public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
            if (!Objects.requireNonNull(response.body()).getError()) {
                onLoginSuccess();
                mProgressDialog.dismiss();
            } else {
                onLoginFailed();
                mProgressDialog.dismiss();
            }
        }

        @Override
        public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _usernameText.setText("a@a.co");
        _passwordText.setText("admin123");
        _loginButton.performClick();

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

        // TODO: Implement your own authentication logic here.
        loginService = (LoginService) ServiceGeneratorUtils.createService(LoginService.class);
        call = loginService.login("login", username, password);
        call.enqueue(callback);

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

                // TODO: Implement successful signin logic here
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

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
