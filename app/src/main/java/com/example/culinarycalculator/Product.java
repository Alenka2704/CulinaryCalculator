package com.example.culinarycalculator;

/**
 * Created by str on 023 23/08.
 */
public class Product {
    // Labels table name
    public static final String TABLE = "Product";

    // Labels Table Columns names
    public static final String _ID = "id";
    public static final String _NAME = "name";
    public static final String _CALORIES = "calories";
    public static final String _PROTEINS = "proteins";
    public static final String _FATS = "fats";
    public static final String _CARBOHYDRATES = "carbohydrates";

    // property help us to keep data
    public int product_ID;
    public String name;
    public int calories;
    public int proteins;
    public int fats;
    public int carbohydrates;
}
