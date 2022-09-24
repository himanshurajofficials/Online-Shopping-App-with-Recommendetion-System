package com.example.onlineshopapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {
    DatabaseReference myRef2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button btnll1=findViewById(R.id.btnll1);
        Button btnll=findViewById(R.id.btnll);
        EditText lphone=findViewById(R.id.lphone);
        EditText lpass=findViewById(R.id.lpass);

        myRef2 = FirebaseDatabase.getInstance().getReference("users");

        btnll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(LogIn.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        btnll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lpass1=lpass.getText().toString();
                String lphone1=lphone.getText().toString();

                if(lpass1.length()<=5){
                    lpass.setError("Password is too short must be >5");
                }
                if(lphone1.length()==0){
                    lphone.setError("Invalid Email");
                }
//

                else {
                    Query query = myRef2.orderByValue();
//                    System.out.println(query);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long querysize=snapshot.getChildrenCount();
                            // System.out.println(querysize);
                            //System.out.println(snapshot);
                            int c1=0;
                            for (DataSnapshot user : snapshot.getChildren()) {
                                if (user.hasChild(lphone1)) {
                                    //System.out.println(user.child(lphone1).child("password").getValue());
                                    if (user.child(lphone1).child("password").getValue().toString().equals(lpass1)) {


                                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                                        myEdit.putString("userid111",lphone1);
                                        myEdit.apply();
                                        Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent2 = new Intent(LogIn.this, homepage.class);
                                        intent2.putExtra("username",lphone1);
                                        startActivity(intent2);

                                    }else {
                                        
                                        lpass.setError("Wrong Password");
                                        Toast.makeText(LogIn.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    }

                                    System.out.println("user exist");


                                    break;
                                }
                                else{
//                                    System.out.println(c1);
                                    if (c1 == querysize - 1) {
                                        System.out.println("dont exist  hai");
                                        lphone.setError("User does not exist with this no.");
                                        Toast.makeText(LogIn.this, "User does not exist", Toast.LENGTH_SHORT).show();

                                    } else {
                                        c1++;
                                    }
                                }
                            

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }



            }


        });
    }
}