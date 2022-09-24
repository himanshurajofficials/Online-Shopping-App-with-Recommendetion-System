package com.example.onlineshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.androidgamesdk.gametextinput.Listener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class recMainActivity extends AppCompatActivity {
RecyclerView recyclerView;
DatabaseReference db;
//DatabaseReference db1,db2;
recmyadapter myadapter;
ArrayList<recuser> list =new ArrayList<>();
    ArrayList<String> list1 =new ArrayList<>();
    ArrayList<String> arr=new ArrayList<>();
Button buttonc ,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recactivity_main);

        recyclerView=findViewById(R.id.recyclerview);
        db= FirebaseDatabase.getInstance().getReference("products");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myadapter=new recmyadapter(list,getApplicationContext());
        recyclerView.setAdapter(myadapter);

//        buttonc=findViewById(R.id.buttoncart);
//        buttonc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            Intent intent=new Intent(recMainActivity.this,ShopCart.class);
//            startActivity(intent);
//            }
//        });


//        SharedPreferences sh1 = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);
            Intent intent1211=getIntent();
            ArrayList<String> arr1=intent1211.getStringArrayListExtra("catlist12112");
            arr= (ArrayList<String>) arr1.stream().distinct().collect(Collectors.toList());
//            arr=arr1;
//        Set<String> ccctags = sh1.getStringSet("catlist111", Collections.singleton(""));
        System.out.println("hjhj hj"+arr1);
//
//        arr= (ArrayList<String>) ccctags;
        System.out.println(arr);
//        arr.add("mobile");
////        arr.add("food");
//        arr.add("electronic");
        recommend11(arr);
//
//        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);
//
//// The value will be default as empty string because for
//// the very first time when the app is opened, there is nothing to show
//        String m = sh.getString("userid111", "");
//        System.out.println("hjhj hj"+m);
//
//       db1= FirebaseDatabase.getInstance().getReference("cart").child(m).push();
//        Intent intent=getIntent();
//        String prodatan=intent.getStringExtra("prodatan");
//        String prodatap=intent.getStringExtra("prodatap");
//      db1.child("proname").setValue(prodatan);
//        db1.child("proprice").setValue(prodatap);
////        db1.child("quantity").setValue("1");
//        Log.d("kkk12", "onCreate: "+prodatan);
//
//        db2= FirebaseDatabase.getInstance().getReference("order").child(m).push();
//        ;
//        // String cont=intent123.getStringExtra("cont");
//        db2.child("proname").setValue(prodatan);
//        db2.child("proprice").setValue(prodatap);



    }



    private void recommend11(ArrayList<String> arr) {

        String url="https://onlineshop112.herokuapp.com/predict";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray m = jsonObject.getJSONArray("values");
//                                    String m =jsonObject.getString("value");

                    System.out.println("values hhjhjh " + m.length() + " mmm " + m + " kk " + m.get(0));

                     list1=json2java(m);
                    System.out.println("values hhjhjh " + list1);
                    db.orderByValue().addValueEventListener(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                String mmm1231=dataSnapshot.getKey();
                                if (list1.contains(mmm1231)) {

                                    System.out.println("the inside list " + dataSnapshot);
                                    recuser user =dataSnapshot.getValue(recuser.class);
                                    list.add(user);
                                    //System.out.println("the inside list "+list);
                                }
                            }
                            //System.out.println(list);
                            myadapter.notifyDataSetChanged();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(recMainActivity.this, "some error", Toast.LENGTH_SHORT).show();
                        }


                    });






                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private ArrayList<String> json2java(JSONArray jsonArray) {

                ArrayList<String> listdata = new ArrayList<String>();

                //Checking whether the JSON array has some value or not


                    //Iterating JSON array
                    for (int i=0;i<jsonArray.length();i++){

                        //Adding each element of JSON array into ArrayList
                        try {
                            listdata.add((String) jsonArray.get(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return listdata;


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recMainActivity.this, "Error while loading heroku Server", Toast.LENGTH_SHORT).show();
            }
        }

        ){

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                System.out.println(arr.toString());
                params.put("taglist",arr.toString());

                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(recMainActivity.this);
        queue.add(stringRequest);



    }
}