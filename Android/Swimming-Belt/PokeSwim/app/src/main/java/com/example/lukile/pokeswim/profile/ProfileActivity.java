package com.example.lukile.pokeswim.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lukile.pokeswim.MenuActivity;
import com.example.lukile.pokeswim.R;
import com.example.lukile.pokeswim.model.Performance;
import com.example.lukile.pokeswim.model.User;
import com.example.lukile.pokeswim.performance.ListPerformanceActivity;
import com.example.lukile.pokeswim.programs.ProgramActivity;
import com.example.lukile.pokeswim.signin.SigninActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukile.pokeswim.model.SaveUserPreferences.PREF_USERMAIL;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IProfileView {

    ProfilePresenter profilePresenter;

    @BindView(R.id.text_name_user)
    TextView _name_user;
    @BindView(R.id.email_user)
    TextView _email_user;
    @BindView(R.id.account_user)
    ImageView _account;

    @BindView(R.id.text_speed_swim)
    TextView _speed_swim;
    @BindView(R.id.text_distance_swim)
    TextView _distance_swim;
    @BindView(R.id.text_length_swim)
    TextView _length_swim;

    @BindView(R.id.text_deviceId)
    TextView _deviceId;
    @BindView(R.id.btn_listPerformances)
    Button _listPerformances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        // To have Burger Menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        _email_user.setText(PREF_USERMAIL);

        profilePresenter = new ProfilePresenter((IProfileView) this);
        profilePresenter.getProfileUser(this);

        _listPerformances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ListPerformanceActivity.class);
                startActivity(intent);
            }
        });
    }


    public void getAndSetUserDate(JSONObject userData) {
        User user = new User();

        try {
            user.setId(userData.getString("_id"));
            user.setFirstname(userData.getString("firstname"));
            user.setLastname(userData.getString("lastname"));
            user.setEmail(userData.getString("email"));
            user.setPassword(userData.getString("password"));
            user.setAge(userData.getString("age"));
            user.setGender(userData.getString("gender"));
            user.setDeviceId(userData.getString("deviceid"));

            String first = user.getFirstname();
            String last = user.getLastname();
            String gender = user.getGender();

            String deviceid = user.getDeviceId();

            if(gender.equals("Homme")) {
                _account.setImageDrawable(getResources().getDrawable(R.drawable.man));
            }else if(gender.equals("Femme")){
                _account.setImageDrawable(getResources().getDrawable(R.drawable.woman));
            }else{
                _account.setImageDrawable(getResources().getDrawable(R.drawable.account));
            }

            _name_user.setText(String.format("%s %s", first, last));
            _deviceId.setText(String.format("%s", deviceid));


            profilePresenter.getPerformanceUser(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAndSetPerformanceDate(Performance performance) {
            float speed = performance.getSpeed();
            int distance = performance.getDistance();
            //int lengthSwim = performance.getLengthType();

            _speed_swim.setText(String.valueOf(speed) + " m");
            _distance_swim.setText(String.valueOf(distance) + " m/h");
            _length_swim.setText("50 m");
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
                intent = new Intent(this, ListPerformanceActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.profile:
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
