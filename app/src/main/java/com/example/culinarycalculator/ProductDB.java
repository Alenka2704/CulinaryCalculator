package com.example.culinarycalculator;

/**
 * Created by str on 023 23/08.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductDB {
    private DBHelper dbHelper;

    // Labels Table Columns names
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String CALORIES = "CALORIES";
    public static final String PROTEINS = "PROTEINS";
    public static final String FATS = "FATS";
    public static final String CARBOHYDRATES = "CARBOHYDRATES";

    public ProductDB(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Product product) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductDB.NAME, product.name);
        values.put(ProductDB.CALORIES, product.calories);
        values.put(ProductDB.PROTEINS, product.proteins);
        values.put(ProductDB.FATS, product.fats);
        values.put(ProductDB.CARBOHYDRATES, product.carbohydrates);

        // Inserting Row
        long product_ID = db.insert(Product.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) product_ID;
    }

    public void delete(int product_ID) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Product.TABLE, ProductDB.ID + "= ?", new String[]{String.valueOf(product_ID)});
        db.close(); // Closing database connection
    }

    public void update(Product product) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProductDB.NAME, product.name);
        values.put(ProductDB.CALORIES, product.calories);
        values.put(ProductDB.PROTEINS, product.proteins);
        values.put(ProductDB.FATS, product.fats);
        values.put(ProductDB.CARBOHYDRATES, product.carbohydrates);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Product.TABLE, values, ProductDB.ID + "= ?", new String[]{String.valueOf(product.product_ID)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getProductList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                ProductDB.ID + "," +
                ProductDB.NAME +
                " FROM " + Product.TABLE;

        //Product product = new Product();
        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> product = new HashMap<String, String>();
                product.put("id", cursor.getString(cursor.getColumnIndex(ProductDB.ID)));
                product.put("name", cursor.getString(cursor.getColumnIndex(ProductDB.NAME)));
                productList.add(product);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;

    }

    public String constructWhereClause(String[]ids){
        String temp="("+ids[0];
        for(int i=1;i<ids.length;i++){
            temp+=", "+ids[i];
        }
        temp+=")";
        return temp;
    }

    public ArrayList<Product> getProductsById(String[] ids) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                ProductDB.ID + ", " +
                ProductDB.NAME + ", " +
                ProductDB.CALORIES + ", " +
                ProductDB.PROTEINS +", " +
                ProductDB.FATS +", " +
                ProductDB.CARBOHYDRATES +
                " FROM " + Product.TABLE
                + " WHERE " +
                ProductDB.ID + " IN "+
                constructWhereClause(ids);

        ArrayList<Product> productsList = new ArrayList<Product>();
        Cursor cursor = db.rawQuery(selectQuery, ids);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.product_ID = cursor.getInt(cursor.getColumnIndex(ProductDB.ID));
                product.name = cursor.getString(cursor.getColumnIndex(ProductDB.NAME));
                product.calories = cursor.getInt(cursor.getColumnIndex(ProductDB.CALORIES));
                product.proteins = cursor.getInt(cursor.getColumnIndex(ProductDB.PROTEINS));
                product.fats = cursor.getInt(cursor.getColumnIndex(ProductDB.FATS));
                product.carbohydrates = cursor.getInt(cursor.getColumnIndex(ProductDB.CARBOHYDRATES));
                productsList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productsList;
    }

}