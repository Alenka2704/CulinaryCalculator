package com.example.culinarycalculator;

/**
 * Created by str on 023 23/08.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PRODUCTS.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + Product.TABLE  + "("
                + ProductDB.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ProductDB.NAME + " TEXT, "
                + ProductDB.CALORIES + " REAL, "
                + ProductDB.PROTEINS + " REAL, "
                + ProductDB.FATS + " REAL, "
                + ProductDB.CARBOHYDRATES + " REAL )";
        String INITIALIZE_TABLE_PRODUCTS="INSERT INTO "+Product.TABLE+"("+ProductDB.ID+","+ProductDB.NAME+","+ProductDB.CALORIES+","+ProductDB.PROTEINS+","+ProductDB.FATS+","+ProductDB.CARBOHYDRATES+ ") values(1,'milk', 62,2.8,3.6,4.7)";

        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(INITIALIZE_TABLE_PRODUCTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE);

        // Create tables again
        onCreate(db);

    }

}