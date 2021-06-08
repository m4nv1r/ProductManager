package com.example.productmanager;

public class Product {
    private int _id;
    private String _productname;
    private double _price;

    // constructors
    public Product(){
    }

    public Product(int id, String productname,double price){
        _id = id;
        _productname = productname;
        _price = price;
    }

    public Product(String productname, double price){
        _productname = productname;
        _price = price;
    }

    // setters and getters
    public void setID(int id){
        _id = id;
    }

    public int getID(){
        return _id;
    }

    public void setProductName(String productName){
        _productname = productName;
    }

    public String getProductName(){
        return _productname;
    }

    public void setPrice(double price){
        _price = price;
    }

    public double getPrice(){
        return _price;
    }
}
