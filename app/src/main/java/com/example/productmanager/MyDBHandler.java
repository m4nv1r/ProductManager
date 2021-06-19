package com.example.productmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    // defining the schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_PRICE = "price";

    // constructor
    public MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create the table
    @Override
    public void onCreate(SQLiteDatabase db){
        // CREATE TABLE TABLE_PRODUCTS (COLUMN_ID INTEGER PRIMARY KEY, COLUMN_PRODUCTNAME TEXT,
        // COLUMN_PRICE DOUBLE)
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE "+TABLE_PRODUCTS+"("+COLUMN_ID+
                " INTEGER PRIMARY KEY,"+COLUMN_PRODUCTNAME+" TEXT,"+COLUMN_PRICE+" DOUBLE"+")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    // deletes old tables and creates a new one
    // change tables by incrementing the database version number
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PRODUCTS);
        onCreate(db);
    }

    // insert into database
    public void addProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        // creating a new map of values where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_PRICE, product.getPrice());

        // insert into table and close
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    // read from database
    public Product findProduct(String productname){
        SQLiteDatabase db = this.getWritableDatabase();

        // run a query to find the product
        // SELECT * FROM TABLE_PRODUCTS WHERE COLUMN_PRODUCTNAME = productname
        String query = "SELECT * FROM "+TABLE_PRODUCTS+" WHERE "+COLUMN_PRODUCTNAME+
                " = \"" + productname+"\"";
        Cursor cursor = db.rawQuery(query, null);

        // create an object and get the result
        Product product = new Product();
        if (cursor.moveToFirst()){
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setPrice(Double.parseDouble(cursor.getString(2)));
            cursor.close();
        } else{
            productname = null;
        }
        db.close();
        return product;
    }

    // delete from database
    public boolean deleteProduct(String productname){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        // run a query to find the product then delete
        // SELECT * FROM TABLE_PRODUCTS WHERE COLUMN_PRODUCTNAME = productname
        String query = "SELECT * FROM "+TABLE_PRODUCTS+" WHERE "+COLUMN_PRODUCTNAME+" = \""+
                productname + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            String idStr = cursor.getString(0);
            db.delete(TABLE_PRODUCTS, COLUMN_ID+" = "+idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public ArrayList<String> allProducts(){
        ArrayList<String> products = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM "+TABLE_PRODUCTS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
               products.add(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCTNAME))+"\n$"+
                       cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }
}
