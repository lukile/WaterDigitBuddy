package com.example.lukile.pokeswim.profile;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.lukile.pokeswim.model.DataUser;
import com.example.lukile.pokeswim.model.Performance;
import com.example.lukile.pokeswim.model.SaveUserPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilePresenter {

    public IProfileView iprofileView;

    private static final String TAG = "ProfilePresenter";

    public ProfilePresenter(IProfileView iprofileView) {
        this.iprofileView = iprofileView;
    }

    public void getProfileUser(Context context){

        String token = SaveUserPreferences.getToken(context);
        Log.e("token profile :", token);
        String baseUrl = "https://aqueous-escarpment-35073.herokuapp.com/";

        AndroidNetworking.get(baseUrl + "users/current")
                .addHeaders("X-ACCESS-TOKEN", token)
                .setTag("Connect")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject getData = response.getJSONObject("data");
                            JSONObject userData = getData.getJSONObject("user");

                            Log.e("USERDATA : ", String.valueOf(userData));

                            iprofileView.getAndSetUserDate(userData);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("on error profile : ",anError.toString());
                    }
                });
    }

    public void getPerformanceUser(Context context) {
        String tokenPerf = SaveUserPreferences.getToken(context);
        Log.e("token profile performance :", tokenPerf);

        String baseUrl = "XXX";

        AndroidNetworking.get(baseUrl + "performances")
                .addHeaders("X-ACCESS-TOKEN", tokenPerf)
                .setTag("Connect")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: response : >" + response.toString());
                        try {
                            JSONObject dataPerf = response.getJSONObject("data");
                            Log.d(TAG, "onResponse: dataPerf : >" + dataPerf.toString());
                            JSONArray array = dataPerf.getJSONArray("performances");
                            JsonParser parser = new JsonParser();
                            JsonElement arr = parser.parse(array.toString());
                            Gson gson = new GsonBuilder().create();
                            Performance[] performances = gson.fromJson(arr, Performance[].class);

                            Log.d(TAG, "onResponse: array" + performances[0].getDistance());

                            iprofileView.getAndSetPerformanceDate(performances[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("on error profile : ", error.toString());
                    }
                });
    }
}
