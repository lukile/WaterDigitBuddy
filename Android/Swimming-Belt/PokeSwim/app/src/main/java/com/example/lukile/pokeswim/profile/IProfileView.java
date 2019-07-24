package com.example.lukile.pokeswim.profile;

import com.example.lukile.pokeswim.model.Performance;

import org.json.JSONObject;

public interface IProfileView {

    void getAndSetUserDate(JSONObject performanceData);
    void getAndSetPerformanceDate(Performance performance);
}
