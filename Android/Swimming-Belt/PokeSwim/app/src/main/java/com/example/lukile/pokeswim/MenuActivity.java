package com.example.lukile.pokeswim;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lukile.pokeswim.performance.ListPerformanceActivity;
import com.example.lukile.pokeswim.profile.ProfileActivity;
import com.example.lukile.pokeswim.programs.ProgramActivity;
import com.example.lukile.pokeswim.signin.SigninActivity;
import com.example.lukile.pokeswim.tuto.TutoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;

    @BindView(R.id.card_sport)
    CardView _card_sport;
    @BindView(R.id.card_WDB)
    CardView _card_WDB;
    @BindView(R.id.card_progress)
    CardView _card_progress;
    @BindView(R.id.card_aboutUs)
    CardView _card_aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _card_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutSport();
            }
        });

        _card_WDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutWDB();
            }
        });

        _card_progress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aboutProgress();
            }
        });

        _card_aboutUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aboutUs();
            }
        });
    }


    public void aboutSport() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sport);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.btn_ok_readed);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void aboutWDB() {
        Intent intent = new Intent(MenuActivity.this, TutoActivity.class);
        startActivity(intent);
    }

    public void aboutProgress() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progress);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.btn_ok);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void aboutUs() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_aboutus);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.btn_ok);

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.programs:
                intent = new Intent(this, ProgramActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.performances:
                intent = new Intent(this, ListPerformanceActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.dashboard:
                break;
            default:
                intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
