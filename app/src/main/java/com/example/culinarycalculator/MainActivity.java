package com.example.culinarycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAdd;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
//        btnAdd.setOnClickListener(this);

        btnCalculate = (Button) findViewById(R.id.buttonCalculate);
//        btnCalculate.setOnClickListener(this);
    }
}
