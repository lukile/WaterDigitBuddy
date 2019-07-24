package com.example.lukile.pokeswim.signin;

import org.json.JSONObject;

public interface ISigninView {

    void validationSignin(String token);
    void errorFailed();
}
