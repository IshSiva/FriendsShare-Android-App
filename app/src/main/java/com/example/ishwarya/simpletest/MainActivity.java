package com.example.ishwarya.simpletest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.ArrayList;

//author @Ish


public class MainActivity extends Activity implements View.OnClickListener {
    int maxlength_name = 10;
    int maxlength_num = 5;



    int[] give = new int[9]; //stores the positions of the persons in the list who need to pay money
    int[] take = new int[9]; //stores the positions of the persons in the list who need to receive money
    int give_i = 0, take_i = 0, pt = 0, pg = 0;
    /*give_i finally denotes the total number of persons who need to pay
      take_i finally denotes the total number of persons who need to receive money
      pt is a count variable used for pointing to receivers (take array)
      pg is a count variable used for pointing to people who have to pay(give array)
    */

    int total = 0; //the total amount spent

    Integer[] amtArray = new Integer[10]; // used to store the amount paid by each person
    double[] balarray = new double[10]; //used to store the balance to be paid or recieved by a person
    String[] nameArray = new String[10]; // used to store the names of the individuals

    ArrayList<String> outArray = new ArrayList<String>(); //used to pass to the next intent

    int nos, i,size;
    DecimalFormat df = new DecimalFormat("#.##");
    double each_share,pymt;

    LinearLayout linearLayout;
    //the edittext arrays are for creating edit text boxes dynamically
    EditText[] etnamelist = new EditText[10];
    EditText[] etamtlist = new EditText[10];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.ll);


        nos = getIntent().getIntExtra("nosoffrnds", 0);
        nos = nos + 1;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params3.setMargins(50, 60, 30, 0);
        params.setMargins(70, 30, 30, 0);

        params.setMarginStart(50);


        for (i = 0; i < nos; i++) {
            EditText nameet = new EditText(this);
            EditText amtet = new EditText(this);

            nameet.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME); //gets input and stores in the nameet edittext array
            nameet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength_name)}); //checking condn
            nameet.setHint("Name");
            nameet.setWidth(450);


            nameet.setBackgroundColor(Color.WHITE);
            nameet.setLayoutParams(params3);

            linearLayout.addView(nameet);
            etnamelist[i] = nameet; //sets the name to the corresponding index


            amtet.setInputType(InputType.TYPE_CLASS_NUMBER);
            amtet.setHint("Amount");
            amtet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxlength_num)}); //error condn when length of number is large
            amtet.setWidth(250);
            amtet.setBackgroundColor(Color.WHITE);
            amtet.setLayoutParams(params);
            linearLayout.addView(amtet);
            etamtlist[i] = amtet; //sets the amount to the corresponding index
        }

        Button bt = new Button(this);
        bt.setText("ShareIt");
        bt.setWidth(100);
        bt.setHeight(75);

        params2.setMargins(25, 200, 75, 15);
        bt.setLayoutParams(params2);

        bt.setOnClickListener(MainActivity.this);
        linearLayout.addView(bt);

    }


    @Override
    public void onClick(View v) {
        for (i = 0; i < nos; i++) {
            nameArray[i] = etnamelist[i].getText().toString();
            String inp = nameArray[i];
            //checks whether the name field is blank
            if(inp.matches("")){
                Toast.makeText(this, "Name field cannot be left blank", Toast.LENGTH_LONG).show();
                return;
            }

            String amt = etamtlist[i].getText().toString();
            //checks whether the amount field is blank
            if(amt.matches("")){
                Toast.makeText(this, "You did not enter the amount for "+nameArray[i], Toast.LENGTH_LONG).show();
                return;
            }
            amtArray[i] = Integer.parseInt(amt);
            total += amtArray[i];

        }

        /*calculation
        each_share is calculated. The difference between the amt spent and the each_share gives the balance, which is stored in
        balarray. If the balance is negative then the person has to receive some money and hence their index is stored in the
        take array. Else they have to pay and their index is stored in the give array.

        pymt = a person's share which is to be given + a person's share which is to be received

        if pymt =0 then the giver gives everything and both pt and pg are incremented
        if pymt<0 then the giver gives a particular person their share and the share paid is decremented from the balance of giver.
                Next person who has to receive the money comes in. Thus pt++.
        if pymt >0 then the receiver receives his share from the giver and moves on to the next giver. The amt received
                is decremented from the receiver's balance. Thus pg++.



         */
        each_share = total*1.0/nos;

        for (i = 0; i < nos; i++) {
            balarray[i] = each_share - amtArray[i];


            if (balarray[i] > 0) {
                give[give_i] = i;
                give_i++;
            }
            if (balarray[i] < 0) {
                take[take_i] = i;
                take_i++;

            }
        }
        i = 0;
        while (pt < take_i) {
            pymt = balarray[give[pg]] + balarray[take[pt]];
            if (pymt == 0) {
                outArray.add((nameArray[give[pg]] + " to pay " + df.format(balarray[give[pg]]) + " bucks to " + nameArray[take[pt]]));
                pt++;
                pg++;
            } else if (pymt > 0) {
                outArray.add((nameArray[give[pg]] + " to pay " + df.format(-balarray[take[pt]]) + " bucks to " + nameArray[take[pt]]));
                balarray[give[pg]] += balarray[take[pt]];
                pt++;
            } else {
                outArray.add(nameArray[give[pg]] + " to pay " + df.format(balarray[give[pg]]) + " bucks to " + nameArray[take[pt]]);
                balarray[take[pt]] += balarray[give[pg]];
                pg++;
            }

            size++;  // to keep track of the total number of display statements

        }


        Intent intent;
        intent = new Intent(this, resultspage.class);
        intent.putExtra("total", total);
        intent.putExtra("iterations", size);
        intent.putExtra("output_array", outArray);


        startActivity(intent);

        finish();
    }

}




