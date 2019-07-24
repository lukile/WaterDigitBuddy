package com.example.lukile.pokeswim.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukile.pokeswim.MenuActivity;
import com.example.lukile.pokeswim.R;
import com.example.lukile.pokeswim.model.SaveUserPreferences;
import com.example.lukile.pokeswim.model.User;
import com.example.lukile.pokeswim.signup.SignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SigninActivity extends AppCompatActivity implements ISigninView {

    private static final String TAG = "SigninActivity";
    private static final int REQUEST_SIGNIN = 0;

    SigninPresenter signinPresenter;

    SaveUserPreferences userPreferences = null;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;

    @BindView(R.id.btn_login)
    Button _loginButton;

    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signinPresenter = new SigninPresenter((ISigninView) this);

        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        }); //lambda

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNIN);
            }
        });
    }

    public void login() {
        //Log.d(TAG, "Login");

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        signinPresenter.signin(email, password);
    }

    @Override
    public void onBackPressed() {
        //Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public void validationSignin(String token) {
        //Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();

        String email = _emailText.getText().toString();
        SaveUserPreferences dataUser = new SaveUserPreferences(email);

        userPreferences.setToken(this, token);

        _loginButton.setEnabled(true);

        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    public void errorFailed () {
        //Toast.makeText(getBaseContext(), "Echec de la connexion", Toast.LENGTH_LONG).show();
        Log.e("Failed:", "onSigninFailed");
    }
}
