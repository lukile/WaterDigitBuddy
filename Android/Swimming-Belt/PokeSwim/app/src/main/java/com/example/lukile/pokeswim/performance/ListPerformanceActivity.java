package com.example.lukile.pokeswim.performance;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lukile.pokeswim.MenuActivity;
import com.example.lukile.pokeswim.R;
import com.example.lukile.pokeswim.model.Performance;
import com.example.lukile.pokeswim.profile.ProfileActivity;
import com.example.lukile.pokeswim.programs.ProgramActivity;
import com.example.lukile.pokeswim.signin.SigninActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListPerformanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IListPerformanceView{
    private static final String TAG = "ListPerformanceActivity";
    RecyclerView rcvPerfs;
    private List<Performance> performanceList = new ArrayList<>();
    //@BindView(R.id.rcv_list_perf)
    //RecyclerView rcvPerfs;

    private ListPerformanceAdapter listPerformanceAdapter;
    private ListPerformancePresenter listPerformancePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_performance);
        // To have Burger Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listPerformancePresenter = new ListPerformancePresenter(this);
        listPerformancePresenter.getAllPerformances(this);
        rcvPerfs = findViewById(R.id.rcv_list_perf);

        rcvPerfs.setLayoutManager(new LinearLayoutManager(this));
        listPerformanceAdapter = new ListPerformanceAdapter(this);
        rcvPerfs.setAdapter(listPerformanceAdapter);


    }

    @Override
    public void printPerformances(Performance[] performances) {
        performanceList.addAll(Arrays.asList(performances));
        listPerformanceAdapter.resetData(performanceList);
        listPerformanceAdapter.notifyDataSetChanged();

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
                break;
            case R.id.profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.dashboard:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
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
