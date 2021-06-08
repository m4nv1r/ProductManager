package com.example.productmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView product_id;
    EditText product_name;
    EditText product_price;
    ListView product_list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set variables to the ids of the .xml elements
        product_id = (TextView) findViewById(R.id.product_id);
        product_name = (EditText) findViewById(R.id.product_name);
        product_price = (EditText) findViewById(R.id.product_price);
        product_list = (ListView) findViewById(R.id.product_list);
    }

    public void newProduct(View view){
        if (product_name.getText().toString().equals("") || product_price.getText().toString().equals("")){
            return;
        }
        MyDBHandler dbHandler = new MyDBHandler(this);

        // get price from the price box
        double price = Double.parseDouble(product_price.getText().toString());

        // get product name form the text box
        // use the constructor from Product.java
        Product product = new Product(product_name.getText().toString(), price);

        // add to database with the addProduct() method from MyDBHandler.java
        dbHandler.addProduct(product);

        // clear the text boxes
        product_price.setText("");
        product_name.setText("");

        Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
    }

    public void lookupProduct(View view){
        if (product_name.getText().toString().equals("")){
            return;
        }
        MyDBHandler dbHandler = new MyDBHandler(this);

        // get product in the database using findProduct() method from MyDBHandler.java
        Product product = dbHandler.findProduct(product_name.getText().toString());

        // if found, then display the product details
        // if not, display "No Match Found"
        if (product!=null){
            product_id.setText(String.valueOf(product.getID()));
            product_price.setText(String.valueOf(product.getPrice()));
            Toast.makeText(this, "Product Found", Toast.LENGTH_SHORT).show();
        } else {
            product_id.setText("No Match Found");
            Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeProduct(View view){
        if (product_name.getText().toString().equals("")){
            return;
        }
        MyDBHandler dbHandler = new MyDBHandler(this);

        // delete product in the database using deleteProduct() method from MyDBHandler.java
        boolean result = dbHandler.deleteProduct(product_name.getText().toString());

        // "Record Deleted" or "No Match Found"
        if (result){
            product_id.setText("Record Deleted");
            product_name.setText("");
            product_price.setText("");
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        } else {
            product_id.setText("No Match Found");
            Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayAllProducts(View view){

        MyDBHandler dbHandler = new MyDBHandler(this);

        ArrayList<String> products = dbHandler.allProducts();

        if (products == null){
            return;
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, products);

        product_list.setAdapter(adapter);
    }
}