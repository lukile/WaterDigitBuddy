package com.example.lukile.pokeswim.performance;

import android.content.Context;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.lukile.pokeswim.model.Performance;
import com.example.lukile.pokeswim.model.SaveUserPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.androidnetworking.common.Priority.LOW;

public class ListPerformancePresenter implements IListPerformancePresenter {
    private static final String TAG = "ListPerformancePresenter";
    private static final String apiUrl = "XXX";

    IListPerformanceView iListPerformanceView;

    public ListPerformancePresenter(@NonNull IListPerformanceView v) {
        iListPerformanceView = v;
    }

    public void getAllPerformances(Context context) {

        String token = SaveUserPreferences.getToken(context);
        AndroidNetworking.get(apiUrl)
                //.addQueryParameter("page", page + "")
                .addHeaders("X-ACCESS-TOKEN", token)
                .setTag("Connect")
                //.setPriority(LOW)
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

                            iListPerformanceView.printPerformances(performances);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error);

                    }
                });
    }
}
