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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    Button button;
    DatabaseReference myRef1,db1;
    private int c=0,err=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);
         Button btnl=findViewById(R.id.btnl);
//        Button skipbtnl=findViewById(R.id.buttonskip);
//        skipbtnl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intents1 = new Intent(MainActivity.this, homepage.class);
//                startActivity(intents1);
//            }
//        });
        EditText sname=findViewById(R.id.sname);
        EditText spass=findViewById(R.id.spass);
        EditText semail=findViewById(R.id.semail);
        EditText sphone=findViewById(R.id.sphone);
        myRef1 = FirebaseDatabase.getInstance().getReference("users");
        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LogIn.class);
                startActivity(intent);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = 0;
                String sname1 = sname.getText().toString();
                String spass1 = spass.getText().toString();
                String semail1 = semail.getText().toString();
                String sphone1 = sphone.getText().toString();


                ArrayList<String> arrl = new ArrayList<>();
                if (sname1.length() == 0) {
                    err++;
                    sname.setError("Name is required");
                }
                if (spass1.length() <= 5) {
                    err++;
                    spass.setError("Password is is too short must be >5");
                }
                if (semail1.length() == 0) {
                    err++;
                    semail.setError("Invalid Address");
                }
//                System.out.println(arrl);
//                else {
//                    if (!semail1.matches("[A-Za-z0-9._%+-]+@iiitmanipur+.ac+.in")) {
//                        semail.setError("Invalid email use @iiitmanipur.ac.in domain");
//                    }
//                }
                if (sphone1.length() == 0) {

                    sphone.setError("Phone no. is required");
                } else {
                    Query query = myRef1.orderByValue();
//                    System.out.println(query);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long querysize=snapshot.getChildrenCount();
                           // System.out.println(querysize);
                            //System.out.println(snapshot);
                                int c1=0;
                            for (DataSnapshot user : snapshot.getChildren()) {
                                if(user.hasChild(sphone1)){
                                    System.out.println("user exist");

                                Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                                sphone.setError("User already exist with this no.");

                                    break;
                                }
                                else{
//                                    System.out.println(c1);
                                    if(c1==querysize-1) {
                                        System.out.println("dont exist  hai");
                                        fun(sphone1,sname1,semail1,spass1,err);
                                    }else{
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


//                dbcheck(false);
                String err1 = String.valueOf(err);
                Log.d("err1", "err pehle" + err1);

            }

            private void fun(String sphone1,String sname1,String semail1,String spass1,int  err) {

                if (err == 0) {
                    Log.d("err1", "onClick: uploading");
                    DatabaseReference myRef = myRef1.push().child(sphone1);

                    DatabaseReference myRefn = myRef.child("name");
                    myRefn.setValue(sname1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                c++;

                            } else {
                                Toast.makeText(MainActivity.this, "failed to add name", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    DatabaseReference myRefp = myRef.child("password");
                    myRefp.setValue(spass1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                c++;

                            } else {
                                Toast.makeText(MainActivity.this, "failed to add password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    DatabaseReference myRefe = myRef.child("address");
                    myRefe.setValue(semail1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                c++;

                            } else {
                                Toast.makeText(MainActivity.this, "failed to add address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    DatabaseReference myRefph = myRef.child("phone_no");
                    myRefph.setValue(sphone1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                c++;
                                if (c == 4) {
                                    Toast.makeText(MainActivity.this, "User created Successfully", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                                    myEdit.putString("userid111",sphone1);
                                    myEdit.apply();

                                    Intent intent1 = new Intent(MainActivity.this, homepage.class);

                                   intent1.putExtra("username",sphone1);

                                    startActivity(intent1);
                                } else {

                                    Toast.makeText(MainActivity.this, "Cant create user", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(MainActivity.this, "failed to add phone no.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                } else {
                    Toast.makeText(MainActivity.this, "Fields Can't be empty", Toast.LENGTH_SHORT).show();
                    err = 0;
                }
            }
        });


    }

//    private void cart(String sphone1) {
//        Intent intent123=getIntent();
//        //String userid1=intent123.getStringExtra("usernamecart");
//
//        db1= FirebaseDatabase.getInstance().getReference("cart").child(sphone1).push();
//
//        String prodatan=intent123.getStringExtra("prodatan");
//        String prodatap=intent123.getStringExtra("prodatap");
//
//        db1.child("proname").setValue(prodatan);
//        db1.child("proprice").setValue(prodatap);
//
//        db1.child("quantity").setValue("1");
//    }

    private void dbcheck(boolean m) {
    if(m) {
        myRef1.setValue("himanshu raj454545 ").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Success ho gaya", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed ho gyaa", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "complete and successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "failed 11", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    }
}