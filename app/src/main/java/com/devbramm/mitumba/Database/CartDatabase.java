package com.devbramm.mitumba.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.devbramm.mitumba.Models.Cart;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class CartDatabase extends SQLiteAssetHelper {

    private static final String DB_NAME = "mitumba_shop.db";
    private static final int DB_VER = 1;

    public CartDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Cart> getCarts()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductID","ProductName","Quantity","Price","Discount","ProductImage","ProductSize","ProductColor"};
        String sqlTable = "CartDetails";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Cart> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                result.add(new Cart(c.getString(c.getColumnIndex("ProductID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("ProductImage")),
                        c.getString(c.getColumnIndex("ProductSize")),
                        c.getString(c.getColumnIndex("ProductColor"))
                ));
            } while (c.moveToNext());
        }

        return result;
    }

    public void addToCart(Cart cart) {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO CartDetails(ProductID,ProductName,Quantity,Price,Discount,ProductImage,ProductSize,ProductColor) VALUES('%s','%s','%s','%s','%s','%s','%s','%s');",
                cart.getProductID(),
                cart.getProductName(),
                cart.getQuantity(),
                cart.getPrice(),
                cart.getDiscount(),
                cart.getProductImage(),
                cart.getProductSize(),
                cart.getProductColor());

        db.execSQL(query);
    }

    public void cleanCart() {

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM CartDetails");

        db.execSQL(query);
    }
}
