package com.example.ishwarya.simpletest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class resultspage extends Activity implements View.OnClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    LinearLayout linearLayout;
    ArrayList<String> outarr = new ArrayList<String>();
    Intent intent;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultspage);

        intent = getIntent();

        outarr = (ArrayList<String>) intent.getSerializableExtra("output_array");


        linearLayout = (LinearLayout) findViewById(R.id.linlay);
        TextView tot = (TextView) findViewById(R.id.total);

        int tota = getIntent().getIntExtra("total", 0);
        int iteration = getIntent().getIntExtra("iterations", 0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        params.setMargins(0, 30, 30, 0);
        params.setMarginStart(25);
        params.setMarginEnd(25);
        params2.setMargins(50, 50, 75, 50);


        tot.setText("Total amount spent= "+ Integer.toString(tota));

        int i=0;

        if(outarr.size() == 0){
            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setWidth(450);
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER);
            tv.setText("No balance amount to be paid.");
            tv.setBackgroundColor(Color.WHITE);
            linearLayout.addView(tv);
        }

            //text views are set based upon the number of display strings.
            // the text for the text views is got from the outarr which is passed from the previous intent
        else {
            for (i = 0; i < iteration; i++) {

                TextView textView = new TextView(this);
                textView.setLayoutParams(params);
                textView.setWidth(450);
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                textView.setText(outarr.get(i).toString());
                textView.setBackgroundColor(Color.WHITE);
                linearLayout.addView(textView);
            }
        }

        TextView tv = new TextView(this);

        tv.setBackgroundColor(Color.WHITE);
        tv.setText("ENJOY!");
        tv.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(params2);
        linearLayout.addView(tv);
        
        Button btn = new Button(this);
        btn.setWidth(50);
        btn.setHeight(75);
        btn.setText("FINISH");
        btn.setTextSize(20);
        btn.setLayoutParams(params2);
        btn.setOnClickListener((View.OnClickListener) resultspage.this);
        linearLayout.addView(btn);

    }
    
    @Override
    public void onClick(View v) {
        Toast.makeText(resultspage.this, "GoodBye!", Toast.LENGTH_LONG).show();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        
    }
}
