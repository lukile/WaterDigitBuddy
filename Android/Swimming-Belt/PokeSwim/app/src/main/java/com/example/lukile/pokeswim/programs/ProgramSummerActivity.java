package com.example.lukile.pokeswim.programs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lukile.pokeswim.R;
import com.example.lukile.pokeswim.model.Performance;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgramSummerActivity extends AppCompatActivity {

    @BindView(R.id.btn_ok)
    Button _launchProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programsummer);

        ButterKnife.bind(this);

        /*_launchProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Performance performance = new Performance();

                performance.setProgramType("SummerBody");
                performance.setLengthType(50);
            }
        });*/
    }

}
