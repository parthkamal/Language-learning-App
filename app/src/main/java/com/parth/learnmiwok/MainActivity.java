package com.parth.learnmiwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView numbers,family,phrases,colors;
        //assigning id
        numbers =findViewById(R.id.numbers);
        colors =findViewById(R.id.colors);
        family =findViewById(R.id.family);
        phrases=findViewById(R.id.phrases);

        //context declaration
        Context context =getApplicationContext();



        //click on listeners
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,NumbersActivity.class);
                startActivity(intent);
            }
        });
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,ColorsActivity.class));
            }
        });
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,PhrasesActivity.class));
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,FamilyActivity.class));
            }
        });
    }


}