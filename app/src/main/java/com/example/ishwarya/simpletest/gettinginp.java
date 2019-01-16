package com.example.ishwarya.simpletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Spinner;



public class gettinginp extends AppCompatActivity {


    int numberoffrnds=0; //to store the number of friends

    Spinner spinner; //for the dropdown menu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettinginp); // creates the window

        spinner = (Spinner) findViewById(R.id.frnds_spinner);
        ArrayAdapter<CharSequence> numadapter = ArrayAdapter.createFromResource(this,R.array.numoffriends, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(numadapter);

        //the adapter has been set in the resources file.

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberoffrnds = position;
                //the number of friends corresponds to the position of the element in the dropdown menu
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void onNextClick(View view) {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("nosoffrnds", numberoffrnds); //passing the number of friends to the next window

        startActivity(intent);
        finish();

    }
}
