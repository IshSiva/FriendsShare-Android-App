package com.example.ishwarya.simpletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
    }

    public void onFirstPageClick(View view){
        Intent intent;
        intent = new Intent(this, gettinginp.class);

        startActivity(intent);

        finish();
    }
}
