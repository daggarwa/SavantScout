package com.example.d.savantn;

import android.content.Intent;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class CalendarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private int score=0;
    Calendar cal;
    private int dayOfWeek=0;
    private int counter=0;
    private static long start_time=0;
    private static long end_time=0;
    private int penalize=0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
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
                i.setClass(CalendarActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner dropDown = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(this, R.array.days_array,
                        android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(arrayAdapter);
        dropDown.setOnItemSelectedListener(this);
        start_time=System.nanoTime();
        startCalendarGame();


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        counter++;
        boolean correct = false;
        String selectedDay=parent.getItemAtPosition(pos).toString();
        dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
        Log.i("TAG", "select" + selectedDay);
        switch(dayOfWeek)
        {
            case 1:if(selectedDay.equals("Sunday"))
                    {
                        score++;
                        correct=true;

                    }
                    break;
            case 2:if(selectedDay.equals("Monday"))
                   {
                        score++;
                       correct=true;

                   }
                   break;
            case 3:if(selectedDay.equals("Tuesday"))
                   {
                    score++;
                       correct=true;


                   }
                   break;
            case 4:if(selectedDay.equals("Wednesday"))
                   {
                     score++;
                       correct=true;

                   }
                break;
            case 5:if(selectedDay.equals("Thursday"))

                {
                    score++;
                    correct=true;

                }
                break;
            case 6:if(selectedDay.equals("Friday"))

                {
                    score++;
                    correct=true;

                }
                break;
            case 7:if(selectedDay.equals("Saturday"))

                {
                    score++;
                    correct=true;

                }
                break;

        }
        if(correct)
        {
            updateScore();
        }
        else
        {
            penalize++;
        }
        if(counter<15) {
            startCalendarGame();
        }
        else
        {
            end_time= System.nanoTime()+penalize;
            long timeTaken=Math.abs(start_time-end_time);
            Intent i = new Intent();
            i.setClass(CalendarActivity.this, ScoreActivity.class);
            i.putExtra("totalTime", timeTaken);
            i.putExtra("score", score);
            startActivity(i);
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void startCalendarGame()
    {

        Random rand=new Random();
        int year=rand.nextInt(400)+1700;
        int month=rand.nextInt(12)+1;
        String monthString="";
        int day=rand.nextInt(31)+1;
        if((month==4||month==6||month==9||month==11)&&day==31)
            day=30;
        if(month==2&&day>28){
            if(year%4==0)
                day=29;
            else
                day=28;
        }
        cal = new GregorianCalendar(year, month,
                day);


        switch(month)
        {
            case 1:monthString="January";
                   break;
            case 2:monthString="February";
                break;
            case 3:monthString="March";
                break;
            case 4:monthString="April";
                break;
            case 5:monthString="May";
                break;
            case 6:monthString="June";
                break;
            case 7:monthString="July";
                break;
            case 8:monthString="August";
                break;
            case 9:monthString="September";
                break;
            case 10:monthString="October";
                break;
            case 11:monthString="November";
                break;
            case 12:monthString="December";
                break;


        }

        String calendarQuestion="What day of the week was "+ monthString+ " "+ day+" "+year+"?";
        TextView question = (TextView) findViewById(R.id.textView7);
        question.setText(calendarQuestion);
        TextView select = (TextView) findViewById(R.id.textView8);
        select.setText("Select ");
        TextView score = (TextView) findViewById(R.id.textView9);
        score.setText("Score :");

    }

    private void updateScore() {
        TextView editText = (TextView) findViewById(R.id.textView10);
        editText.setText(String.valueOf(score));
    }

}


