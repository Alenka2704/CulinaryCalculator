package com.example.culinarycalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> labels;
    ArrayList<HashMap<String, String>> productsList;

    //TODO prevent selection of the same item twice, order items by name. If item is selected, it's removed from other spinners lists, added back on item entry removal. Set ids for spinners items that respond ids of these products in database

public String getProductId(LinearLayout parentLayout, int i){
    LinearLayout temp = (LinearLayout) parentLayout.getChildAt(i);
    Spinner tempSpinner = (Spinner) temp.findViewWithTag("spnProduct");
    int k = tempSpinner.getSelectedItemPosition();
    return productsList.get(k + 1).get("id");

}
    public void onClickCalculate() {
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        int productsCount = parentLayout.getChildCount() - 1;
        String[] productsIds = new String[productsCount];
        for (int i = 0; i < productsCount; i++) {
            productsIds[i]=getProductId(parentLayout,i);
        }
        ProductDB productDB=new ProductDB(getApplicationContext());
        ArrayList<Product>productsList= productDB.getProductsById(productsIds);
        float totalProteins=0,totalFats=0,totalCarbohydrates=0,totalCalories=0;
        for (int i = 0; i < productsCount; i++) {
//            Product temp=
        }
    }

    public void onClickRemove(View view) {
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        parentLayout.removeView(view);
    }

    public void onClickAdd() {
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout itemLayout = (LinearLayout) layoutInflater.inflate(R.layout.product_layout, parentLayout, false);
        Spinner spinner = (Spinner) itemLayout.findViewWithTag("spnProduct");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        itemLayout.findViewWithTag("btnRemove").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentLayout = (View) v.getParent();
                onClickRemove(parentLayout);
            }
        });
        parentLayout.addView(itemLayout, parentLayout.getChildCount() - 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductDB productDB = new ProductDB(getApplicationContext());
        productsList = productDB.getProductList();
        labels = new ArrayList<String>();
        labels.add("select");
        for (HashMap<String, String> item : productsList) {
            labels.add(item.get("name"));
        }
        onClickAdd();

        Button btnCalculate = (Button) findViewById(R.id.buttonCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCalculate();
            }
        });

        ImageButton btnAdd = (ImageButton) findViewById(R.id.imageButtonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });
    }
}
