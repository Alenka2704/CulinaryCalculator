package com.example.culinarycalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> labels;
    List<Integer> ids;
    ArrayList<HashMap<String, String>> productsList;
    LinearLayout parentLayout;
    int productsCount;

    //TODO prevent selection of the same item twice, order items by name. If item is selected, it's removed from other spinners lists, added back on item entry removal. Set ids for spinners items that respond ids of these products in database

    /**
     * as far as storing of ids for Spinner items is performed using two lists, this method is supposed to match them
     * @param i index of current Spinner parent layout i.e. number of product on the screen
     * @return id of product in database
     */
    public String getProductId(int i) {
        int k = ((Spinner) getViewByTag("spnProduct", i)).getSelectedItemPosition();
        return k==0?"":productsList.get(k-1).get("id");

    }

    /**
     * get one of controls in product LinearLayout
     * @param tag type of control to get
     * @param index number of LineaLayout on the screen
     * @return control instance
     */
    public View getViewByTag(String tag, int index) {
        LinearLayout temp = (LinearLayout) parentLayout.getChildAt(index);
        return temp.findViewWithTag(tag);
    }

    public void computeTotalWeight() {
        float weight = 0;
        for (int i = 0; i < productsCount; i++) {
            weight += Float.parseFloat(((EditText) getViewByTag("txtAmount", i)).getText().toString());
        }
        EditText totalWeight = (EditText) findViewById(R.id.editTextWeight);
        totalWeight.setText(Float.toString(weight));
    }

    /**
     * searches for the product in the list using id
     * @param productsList list of products requested from DB
     * @param num Spinner selected item index
     * @return Spinner item details for calculation of totals
     */
    public Product getSelectedProduct(ArrayList<Product> productsList, int num) {
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getProduct_ID() == ids.get(num)) {
                return productsList.get(i);
            }
        }
        return new Product(); //should never happen
    }

    public void onClickCalculate() {
        String[] productsIds = new String[productsCount];
        for (int i = 0; i < productsCount; i++) {
            productsIds[i] = getProductId(i);
        }
        ProductDB productDB = new ProductDB(getApplicationContext());
        ArrayList<Product> productsList = productDB.getProductsById(productsIds);
        float totalProteins = 0, totalFats = 0, totalCarbohydrates = 0, totalCalories = 0;
        for (int i = 0; i < productsCount; i++) {
            float amount = Float.parseFloat(((EditText) getViewByTag("txtAmount", i)).getText().toString());
            Product tempProduct = getSelectedProduct(productsList, (int) (((Spinner) getViewByTag("spnProduct", i)).getSelectedItemId()));
            totalProteins += tempProduct.proteins * amount;
            totalFats += tempProduct.fats * amount;
            totalCarbohydrates += tempProduct.carbohydrates * amount;
            totalCalories += tempProduct.calories * amount;
        }

        ((TextView) findViewById(R.id.textViewCalories)).setText("Calories:         " + totalCalories);
        ((TextView) findViewById(R.id.textViewProteins)).setText("Proteins:         " + totalProteins);
        ((TextView) findViewById(R.id.textViewFats)).setText("Fats:             " + totalFats);
        ((TextView) findViewById(R.id.textViewCarbohydrates)).setText("Carbohydrates:    " + totalCarbohydrates);
    }

    public void onClickRemove(View view) {
        parentLayout.removeView(view);
        productsCount = parentLayout.getChildCount() - 2;
        computeTotalWeight();
    }

    public void onClickAdd() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout itemLayout = (LinearLayout) layoutInflater.inflate(R.layout.product_layout, parentLayout, false);
        Spinner spinner = (Spinner) itemLayout.findViewWithTag("spnProduct");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        final EditText editText = (EditText) itemLayout.findViewWithTag("txtAmount");
        editText.setText("0");

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    computeTotalWeight();
                }
            }
        });
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
        productsCount = parentLayout.getChildCount() - 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductDB productDB = new ProductDB(getApplicationContext());
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        productsCount = parentLayout.getChildCount() - 2;
        productsList = productDB.getProductList();
        labels = new ArrayList<String>();
        ids = new ArrayList<Integer>();
        labels.add("select");
        ids.add(-1);
        for (HashMap<String, String> item : productsList) {
            labels.add(item.get("name"));
            ids.add(Integer.parseInt(item.get("id")));
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
