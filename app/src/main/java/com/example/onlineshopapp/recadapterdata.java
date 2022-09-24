package com.example.onlineshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class recadapterdata extends AppCompatActivity {
    DatabaseReference db1,db2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recadapterdata);

        int i=0;

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);


        String m121 = sh.getString("userid111", "");
        System.out.println("hjhj hj"+m121);

        db1= FirebaseDatabase.getInstance().getReference("cart").child(m121).push();
        Intent intent=getIntent();
        String prodatan=intent.getStringExtra("prodatan");
        String prodatap=intent.getStringExtra("prodatap");

        String prodatac=intent.getStringExtra("procato");
        db1.child("procategory").setValue(prodatac);

        db1.child("proname").setValue(prodatan);
        db1.child("proprice").setValue(prodatap);
//        db1.child("quantity").setValue("1");
        Log.d("kkk12", "onCreate: "+prodatan);

        db2= FirebaseDatabase.getInstance().getReference("order").child(m121).push();
        ;
        // String cont=intent123.getStringExtra("cont");
        db2.child("proname").setValue(prodatan);
        db2.child("proprice").setValue(prodatap);


    }
}