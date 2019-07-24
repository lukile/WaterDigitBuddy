package com.example.lukile.pokeswim.signin;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.lukile.pokeswim.model.DataUser;
import com.example.lukile.pokeswim.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SigninPresenter {

    public ISigninView iSigninView;
    public DataUser dataUser;

    private static final String TAG = "SignupPresenter";


    public SigninPresenter(ISigninView iSignupView) {
        this.iSigninView = iSignupView;
    }


    public void signin(String email, String password) {
        String baseUrl = "XXX";
        JSONObject userJson = new JSONObject();
        JSONObject userPropertiesJson = new JSONObject();

        try {
            userPropertiesJson.put("email", email);
            userPropertiesJson.put("password", password);

            userJson.put("user", userPropertiesJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.e("email:", userJson.getJSONObject("user").getString("email"));

        AndroidNetworking.post(baseUrl+"authentication/sign")
                .addJSONObjectBody(userJson)
                .setTag("Connexion")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject getData = response.getJSONObject("data");

                            String token = getData.getString("token");

                            DataUser dataUser = new DataUser();
                            dataUser.setToken(token);

                            Log.e("TOKEN : ", token);

                            iSigninView.validationSignin(token);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        //Log.e("errorCode:", String.valueOf(anError.getErrorCode()));
                        //Log.e("error:", anError.getErrorBody());
                        iSigninView.errorFailed();
                        anError.printStackTrace();
                    }
                });


    }
}
