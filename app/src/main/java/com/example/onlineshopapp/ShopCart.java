package com.example.onlineshopapp;

import static java.util.Objects.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class ShopCart extends AppCompatActivity {
    DatabaseReference myRefcart2;
    ArrayList<String>procatoglist111=new ArrayList<>();
    ArrayList<String>proprilist=new ArrayList<>();
    ListView listView;
    TextView textView;
    Button button21,buttonr22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        listView=findViewById(R.id.listview1);
        textView=findViewById(R.id.textView2);
        Intent intent=new Intent(ShopCart.this,recMainActivity.class);
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String m = sh.getString("userid111", "");
//        Intent intent123=getIntent();
        System.out.println("jhgf   "+ m);

        myRefcart2 = FirebaseDatabase.getInstance().getReference("cart").child(m);
           final Query query = myRefcart2.orderByValue();
                    System.out.println(query);
        final int[] totalprice = {0};
        final int[] c = {1};

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    if ((snapshot).child("proname").getValue() != null ) {

System.out.println(snapshot);
                        String proname11 = requireNonNull(snapshot.child("proname").getValue().toString().toUpperCase());
                        String procate11 = requireNonNull(snapshot.child("procategory").getValue()).toString();
                        procatoglist111.add(procate11);

//                    procatoglist111.add("mobile");
//////        arr.add("food");
//        procatoglist111.add("electronic");

                        String proprice11 = (String) snapshot.child("proprice").getValue();
                        String proname;
                        if (proname11.length() <= 20) {
                            proname = String.format("%-27s", proname11).replace(" ", "  ");
                            String prodis = c[0] + ".    " + proname + " Rs." + proprice11;
                            proprilist.add(prodis);
                        } else {
                            proname11 = proname11.substring(0, 22);
                            proname = String.format("%-27s", proname11).replace(" ", "  ");
                            String prodis = c[0] + ".    " + proname + " Rs." + proprice11;
                            proprilist.add(prodis);
                        }


                        if (proprice11 != null) {
                            int m = Integer.parseInt(proprice11);
                            totalprice[0] += m;
                            c[0]++;
//                   pronlist.add(proname11);


                            System.out.println(proname11);
                            System.out.println(proprice11);
                            System.out.println(totalprice[0]);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ShopCart.this, android.R.layout.simple_list_item_1, proprilist);
                            listView.setAdapter(arrayAdapter);
                            intent.putExtra("catlist12112", procatoglist111);
//                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
//
//// Creating an Editor object to edit(write to the file)
//                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
//
//// Storing the key and its value as the data fetched from edittext
////                        myEdit.putString("catlist111", String.valueOf(procatoglist111));
//                        myEdit.putStringSet("catlist111", (Set<String>) procatoglist111);
//                        myEdit.apply();


                            textView.setText("Rs." + totalprice[0]);
                        }
                        }
                    else {
Intent intent1121=new Intent(getApplicationContext(),ShopCart.class);
startActivity(intent1121);
//                        Toast.makeText(ShopCart.this, "hhhhhii", Toast.LENGTH_SHORT).show();
                    }
                    }

                    @Override
                    public void onChildChanged (@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName){

                    }

                    @Override
                    public void onChildRemoved (@NonNull DataSnapshot snapshot){

                    }

                    @Override
                    public void onChildMoved (@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName){

                    }

                    @Override
                    public void onCancelled (@NonNull DatabaseError error){

                    }

            });

        button21=findViewById(R.id.button21);


        buttonr22=findViewById(R.id.button4);
        buttonr22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //intent.putExtra("orderid1",userid1);
//                intent.
                startActivity(intent);
            }
        });



        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent121=new Intent(ShopCart.this,confirmorder.class);
//                intent.putExtra("usernamecart",userid);
//                intent.putExtra("from","homepage");
                System.out.println("hhhhh "+query);
                //DatabaseReference myRefcart3 = FirebaseDatabase.getInstance().getReference("orders").child(orderid1);
                myRefcart2.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(ShopCart.this, "Order Placed ", Toast.LENGTH_SHORT).show();
                        intent121.putExtra("result","Order Placed");
                        startActivity(intent121);
                    }
                });







            }
        });










    }
}