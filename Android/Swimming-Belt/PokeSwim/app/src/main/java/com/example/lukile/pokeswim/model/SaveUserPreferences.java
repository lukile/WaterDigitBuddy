package com.example.lukile.pokeswim.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUserPreferences {

    private static SharedPreferences prefs;

    public static String PREF_USERMAIL; // nom du fichier
    private static String TOKEN_KEY;
    //private static String ID_USER;


    public SaveUserPreferences(String email) {
        PREF_USERMAIL = email;
    }

    public static String getToken(Context c) {
        prefs = c.getSharedPreferences(PREF_USERMAIL, Context.MODE_PRIVATE);
        // MODE_... = mode de fonctionnement
        // MODE_PRIVATE : le mode par défaut, où le fichier créé n'est accessible que par l'application appelante

        return prefs.getString(TOKEN_KEY, "");
    }

    public static void setToken(Context c, String token) {
        prefs = c.getSharedPreferences(PREF_USERMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    /*public static String getIdUser(Context c) {
        prefs = c.getSharedPreferences(PREF_USERMAIL, Context.MODE_PRIVATE);

        return prefs.getString(ID_USER, "");
    }

    public static void setIdUser(Context c, String idUser) {
        prefs = c.getSharedPreferences(PREF_USERMAIL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ID_USER, idUser);
        editor.apply();
    }*/
}
