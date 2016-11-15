package com.example.d.savantn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.home));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent();
                i.setClass(ScoreActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        long timeTaken=0;
        int score=0;
        Long time=getIntent().getLongExtra(("totalTime"), timeTaken);
        int finalScore=getIntent().getIntExtra(("score"), score);
        double seconds = (double)time / 1000000000.0;
        System.out.println("timeTaken" + time.toString());
        System.out.println("finalScore" + finalScore);
        TextView scoreValue = (TextView) findViewById(R.id.textView14);
        scoreValue.setText(finalScore+ "");
        TextView timeTakenValue = (TextView) findViewById(R.id.textView5);
        timeTakenValue.setText(Math.round(seconds)+ " seconds");





    }

}
