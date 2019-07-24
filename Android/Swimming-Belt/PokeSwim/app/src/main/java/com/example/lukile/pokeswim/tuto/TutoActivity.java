package com.example.lukile.pokeswim.tuto;

import android.support.constraint.motion.MotionLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.lukile.pokeswim.R;

public class TutoActivity extends AppCompatActivity {

    MotionLayout motionLayout;

    private int selectedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);

        motionLayout = findViewById(R.id.motion_container);

        FrameLayout _view1 = findViewById(R.id.v1);
        FrameLayout _view2 = findViewById(R.id.v2);
        FrameLayout _view3 = findViewById(R.id.v3);

        _view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TutoActivity.this.selectedIndex != 0) {
                    motionLayout.setTransition(R.id.s2, R.id.s1); //orange to blue transition
                    motionLayout.transitionToEnd();
                    TutoActivity.this.selectedIndex = 0;
                }
            }
        });

        _view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TutoActivity.this.selectedIndex != 1) {
                    if (TutoActivity.this.selectedIndex == 2) {
                        motionLayout.setTransition(R.id.s3, R.id.s2); //red to orange transition
                    } else {
                        motionLayout.setTransition(R.id.s1, R.id.s2); //blue to orange transition
                    }

                    motionLayout.transitionToEnd();
                    TutoActivity.this.selectedIndex = 1;
                }
            }
        });

        _view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TutoActivity.this.selectedIndex != 2) {
                    motionLayout.setTransition(R.id.s2, R.id.s3); //orange to red transition
                    motionLayout.transitionToEnd();
                    TutoActivity.this.selectedIndex = 2;
                }
            }
        });
    }
}
