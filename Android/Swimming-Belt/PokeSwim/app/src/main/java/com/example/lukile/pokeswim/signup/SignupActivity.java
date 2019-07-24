package com.example.lukile.pokeswim.signup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukile.pokeswim.R;
import com.example.lukile.pokeswim.signin.SigninActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements ISignupView {

    private static final String TAG = "SignupActivity";
    private static final String SEEKBARTAG = "test";

    SignupPresenter signupPresenter;

    @BindView(R.id.input_lastname)
    EditText _lastnameText;
    @BindView(R.id.input_firstname)
    EditText _firstnameText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_age)
    TextView _ageText;
    @BindView(R.id.seekBarAge)
    SeekBar _seekBarAge;
    @BindView(R.id.input_gender)
    TextView _genderText;
    @BindView(R.id.input_deviceid)
    TextView _deviceid;

    @BindView(R.id.btn_signup)
    Button _signupButton;

    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupPresenter = new SignupPresenter((ISignupView) this);

        ButterKnife.bind(this);

        _seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int value = 5;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
                Log.i(SEEKBARTAG, String.valueOf(value));
                //Toast.makeText(getApplicationContext(), "seekbar's progress", Toast.LENGTH_SHORT).show();
                _ageText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(SEEKBARTAG, String.valueOf(value));
                //Toast.makeText(getApplicationContext(), "Started seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(SEEKBARTAG, String.valueOf(value));
                //Toast.makeText(getApplicationContext(), "Stopped seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        _genderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupChooseGender();
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void popupChooseGender() {
        final String[] choiceGender = getResources().getStringArray(R.array.dialog_gender);
        int itemSelected = 0;

        new AlertDialog.Builder(this)
                .setTitle("Sexe :")
                .setSingleChoiceItems(choiceGender, itemSelected, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        Log.i("gender", String.valueOf(selectedIndex));

                        _genderText.setText(choiceGender[selectedIndex]);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void signup() {
        String lastname = _lastnameText.getText().toString();
        String firstname = _firstnameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String age = _ageText.getText().toString();
        String gender = _genderText.getText().toString();
        String deviceid = _deviceid.getText().toString();

        Integer ageInt = Integer.valueOf(age);

        Log.i("nom", lastname);
        Log.i("prenom", firstname);
        Log.i("email", email);
        Log.i("pwd", password);
        Log.i("age", String.valueOf(ageInt));
        Log.i("gender", gender);
        Log.i("deviceid", deviceid);

        signupPresenter.signup(lastname, firstname, email, password, ageInt, gender, deviceid);
    }


    @Override
    public void validationSignup() {
        //Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    public void errorFailed() {
        //Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        Log.e("Failed:", "onSignupFailed");
    }
}
