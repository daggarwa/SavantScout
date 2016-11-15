package com.example.d.savantn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int rightBox;
    Button buttonOption1;
    Button buttonOption2;
    Button buttonOption3;
    Random rand=new Random();
    private int points=0;
    private int quesCounter=0;
    private static long start_time=0;
    private static long end_time=0;
    private int penalize;
    private int level;

    public enum Operation {
        SUM, DIVISION, MULTIPLICATION, SUBSTRACT
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.home));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Go home to play more", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent();
                i.setClass(MainActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        start_time=System.nanoTime();
        buttonOption1 = (Button) findViewById(R.id.button);
        buttonOption2 = (Button) findViewById(R.id.button2);
        buttonOption3 = (Button) findViewById(R.id.button3);
        buttonOption1.setOnClickListener(this);
        buttonOption2.setOnClickListener(this);
        buttonOption3.setOnClickListener(this);
        createOperation();
    }


    private void createOperation() {
        quesCounter++;
        Operation operation = generateRandOperation();

        level=quesCounter/10;
        int operand1=0,operand2=0;
        if(level==0) {
             operand1 = rand.nextInt(10);
             operand2 = rand.nextInt(10)+1;
        }

        else if(level==1) {
            operand1 = rand.nextInt(100);
            operand2 = rand.nextInt(10)+1;
        }

        else
        {
            int flag=rand.nextInt(2);
            if(flag==0)
            {
                operand1=rand.nextInt(100);
                operand2=rand.nextInt(100);
            }
            else
            {
                operand1=rand.nextInt(1000);
                operand2=rand.nextInt(10)+1;
            }
        }

        String operationText = String.valueOf(operand1) + " " +
                getOperationString(operation) + " " + String.valueOf(operand2) + "?";

        TextView textViewOperation = (TextView) findViewById(R.id.textView3);
        textViewOperation.setText(operationText);

        TextView question = (TextView) findViewById(R.id.textView2);
        question.setText("Q."+quesCounter);

        TextView hello = (TextView) findViewById(R.id.textView4);

        hello.setText("Here is your question:");

        TextView score = (TextView) findViewById(R.id.textView);
        score.setText("Score :");




        float rightValue = calculateRightValue(operation, operand1, operand2);

        rightBox = rand.nextInt(3);
        double randWrongValue1 = Math.round((rand.nextFloat()*20+rightValue)*100.0)/100.0;;
        double randWrongValue2 = Math.round((rightValue - rand.nextFloat() * 20) * 100.0)/100.0;



        switch (rightBox) {
            case 0:
                buttonOption1.setText(String.valueOf("A. " +rightValue));
                buttonOption2.setText(String.valueOf("B. " +randWrongValue1));
                buttonOption3.setText(String.valueOf("C. " +randWrongValue2));
                break;
            case 1:
                buttonOption2.setText(String.valueOf("B. " +rightValue));
                buttonOption1.setText(String.valueOf("A. " +randWrongValue1));
                buttonOption3.setText(String.valueOf("C. " +randWrongValue2));
                break;
            case 2:
                buttonOption3.setText(String.valueOf("C. " +rightValue));
                buttonOption1.setText(String.valueOf("A. " +randWrongValue1));
                buttonOption2.setText(String.valueOf("B. " +randWrongValue2));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private float calculateRightValue(Operation oper, int operand1, int operand2) {
        float calculation = 0;

        if (oper == Operation.SUM) {
            calculation = operand1 + operand2;
        } else if (oper == Operation.MULTIPLICATION) {
            calculation = operand1 * operand2;
        } else if (oper == Operation.SUBSTRACT) {
            calculation = operand1 - operand2;
        } else if (oper == Operation.DIVISION) {
            calculation = operand1 / operand2;
        }

        return calculation;
    }

    private Operation generateRandOperation() {
        int oper = rand.nextInt(4);
        Operation operation = null;

        switch(oper) {
            case 0:
                operation = Operation.SUM;
                break;
            case 1:
                operation = Operation.DIVISION;
                break;
            case 2:
                operation = Operation.MULTIPLICATION;
                break;
            case 3:
                operation = Operation.SUBSTRACT;
                break;
        }
        return operation;
    }


    @Override
    public void onClick(View view) {

        boolean correct = false;
        boolean play = true;

        switch(view.getId()) {
            case R.id.button:
                if (rightBox == 0) {
                    correct = true;
                }
                break;
            case R.id.button2:
                if (rightBox == 1) {
                    correct = true;
                }
                break;
            case R.id.button3:
                if (rightBox == 2) {
                    correct = true;
                }
                break;
            //case R.id.imageButtonHome:
              //  startActivity(new Intent(MathRallyActivity.this, ChooseActivity.class));
               // play = false;
                //break;
        }
        if (correct) {
            points++;
            updateScore();
        }else{
            penalize++;
        }
        if(play) {
            if(quesCounter==30)
            {
                end_time= System.nanoTime()+penalize;
                long timeTaken=Math.abs(start_time-end_time);
                Intent i = new Intent();
                i.setClass(MainActivity.this, ScoreActivity.class);
                i.putExtra("totalTime", timeTaken);
                i.putExtra("score", points);
                startActivity(i);
            }
            else {
                createOperation();
            }
        }
    }

    private void updateScore() {
        TextView editText = (TextView) findViewById(R.id.textView11);
        editText.setText(String.valueOf(points));
    }

    private String getOperationString(Operation oper) {
        String operationText = "";
        if (oper == Operation.SUM) {
            operationText = "+";
        } else if (oper == Operation.MULTIPLICATION) {
            operationText = "*";
        } else if (oper == Operation.SUBSTRACT) {
            operationText = "-";
        } else if (oper == Operation.DIVISION) {
            operationText = "/";
        }
        return operationText;
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
}
