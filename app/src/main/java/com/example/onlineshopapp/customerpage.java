package com.example.onlineshopapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class customerpage extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    DatabaseReference db1;
    DatabaseReference db2;
    myadapter myadapter;
    ArrayList<user> list =new ArrayList<>();
    ArrayList<String> id11 =new ArrayList<String>();
    Button buttonc;


     public String userid1;



//    public void setUserid1(String userid1) {
//        this.userid1 = userid1;
//    }
//    public String getUserid1() {
//        return userid1;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerpage);

        recyclerView=findViewById(R.id.recyclerview);
        db= FirebaseDatabase.getInstance().getReference("products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myadapter=new myadapter(list,getApplicationContext());
        recyclerView.setAdapter(myadapter);

        buttonc=findViewById(R.id.buttoncart);
        buttonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(customerpage.this,ShopCart.class);
                //intent.putExtra("orderid1",userid1);
                startActivity(intent);
            }
        });

        db.orderByValue().addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    user user =dataSnapshot.getValue(user.class);
//                    if (user != null) {
//                        user.setUserid1(userid1);
//                    }
                    list.add(user);
                    //System.out.println("the inside list "+list);
                }
                //System.out.println(list);
                myadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(customerpage.this, "some error", Toast.LENGTH_SHORT).show();
            }


        });


       //final user obj=new user();
        int i=0;
        Intent intent123=getIntent();
        String check=intent123.getStringExtra("from");

        System.out.println(check);

        if(check.equals("homepage")){
            userid1=intent123.getStringExtra("usernamecart");
           // obj.setUserid1(userid1);
            id11.add(userid1);
            System.out.println("fgf2 "+userid1);




        }else if(check.equals("myadapter")){
            // Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
            SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
            String m = sh.getString("userid111", "");
            System.out.println("hjhj hj"+m);
            //int a = sh.getInt("age", 0);

// We can then use the data
//            name.setText(s1);
//            age.setText(String.valueOf(a));

            //userid1= getUserid1();
//           String userid12=intent123.getStringExtra("userid");
//           // id11.add(userid12);
//            String m= id11.get(0);
//            System.out.println("uuu"+userid12+"array "+ id11);
            db1= FirebaseDatabase.getInstance().getReference("cart").child(m).push();
            String prodatan=intent123.getStringExtra("prodatan");
            String prodatap=intent123.getStringExtra("prodatap");
            // String cont=intent123.getStringExtra("cont");

            String prodatac=intent123.getStringExtra("procato");
            db1.child("procategory").setValue(prodatac);
            db1.child("proname").setValue(prodatan);
            db1.child("proprice").setValue(prodatap);
            //db1.child("procontex").setValue(cont);
            // db1.child("quantity").setValue("1");

            db2= FirebaseDatabase.getInstance().getReference("order").child(m).push();
         ;
            // String cont=intent123.getStringExtra("cont");
            db2.child("proname").setValue(prodatan);
            db2.child("proprice").setValue(prodatap);



            Log.d("kkk12", "onCreate: "+prodatan);

        }






        //Log.d("kkk12", "onCreate: "+userid1);

    }
}
