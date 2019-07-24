package com.example.lukile.pokeswim.signup;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.lukile.pokeswim.signup.ISignupView;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupPresenter {

    public ISignupView iSignupView;
    private static final String TAG = "SignupPresenter";

    public SignupPresenter(ISignupView iSignupView) {
        this.iSignupView = iSignupView;
    }


    public void signup(String lastname, String firstname, String email, String password, int age, String gender, String deviceid) {

        String baseUrl = "XXX";
        JSONObject userJson = new JSONObject();
        JSONObject userPropertiesJson = new JSONObject();

        try {
            userPropertiesJson.put("firstname", firstname);
            userPropertiesJson.put("lastname", lastname);
            userPropertiesJson.put("password", password);
            userPropertiesJson.put("age", age);
            userPropertiesJson.put("email", email);
            userPropertiesJson.put("gender", gender);
            userPropertiesJson.put("deviceid", deviceid);

            userJson.put("user", userPropertiesJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.e("lastname:", userJson.getJSONObject("user").getString("lastname"));

        AndroidNetworking.post(baseUrl + "authentication/register")
                .addJSONObjectBody(userJson)
                .setTag("Inscription")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response:", response);
                        iSignupView.validationSignup();
                    }

                    @Override
                    public void onError(ANError anError) {
                        //Log.e("errorCode:", String.valueOf(anError.getErrorCode()));
                        //Log.e("error:", anError.getErrorBody());
                        iSignupView.errorFailed();
                        anError.printStackTrace();
                    }
                });
    }
}
