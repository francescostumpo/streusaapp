package org.streusa.entities;

import com.google.gson.JsonArray;

import java.util.Date;

public class Purchase {

    private String _id;
    private String _rev;
    private JsonArray books;
    private JsonArray merchandises;
    private float finalTotal;
    private Date purchaseDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public JsonArray getBooks() {
        return books;
    }


    public void setBooks(JsonArray books) {
        this.books = books;
    }

    public JsonArray getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(JsonArray merchandises) {
        this.merchandises = merchandises;
    }


    public float getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(float finalTotal) {
        this.finalTotal = finalTotal;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
