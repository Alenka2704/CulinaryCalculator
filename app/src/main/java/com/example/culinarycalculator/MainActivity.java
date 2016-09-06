package com.example.culinarycalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    int count = -1;

    public void onClickCalculate(View view) {

    }

    public void onClickRemove(int i) {
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        parentLayout.removeView(findViewById(i));
    }

    public void onClickAdd() {
        count++;
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout itemLayout = (LinearLayout) layoutInflater.inflate(R.layout.product_layout, parentLayout, false);
        itemLayout.setId(count);
        itemLayout.findViewWithTag("btnRemove").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentLayout=(View) v.getParent();
                onClickRemove(parentLayout.getId());
            }
        });
        parentLayout.addView(itemLayout, parentLayout.getChildCount()-2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onClickAdd();

//        Button btnCalculate = (Button) findViewById(R.id.buttonCalculate);
//        btnCalculate.setOnClickListener(this);

        ImageButton btnAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickAdd();
                    }
                });
    }
}
