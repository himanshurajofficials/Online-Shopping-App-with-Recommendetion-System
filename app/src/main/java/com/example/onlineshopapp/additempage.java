package com.example.onlineshopapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.UUID;

public class additempage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView textView;
    Button button;
    EditText editText1123;
   private DatabaseReference myRef1;
   private int c=0,err=0;
    StorageReference firebaseStorage= FirebaseStorage.getInstance().getReference("images").child("img"+ UUID.randomUUID().toString());
    DatabaseReference fb=FirebaseDatabase.getInstance().getReference("products").push();
    DatabaseReference dr=fb.child("imageuri");
    DatabaseReference dr1=fb.child("category");
    DatabaseReference dr23=fb.child("rating");
    DatabaseReference dr24=fb.child("key");
   private ImageButton imageButton;
   Uri imageuri;
    Spinner spinner;
    String[] list = { "select categories", "electronic", "sports",
                "food", "mobile",
                "laptop", "others" };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_additempage);
            spinner=findViewById(R.id.spinner1);

            System.out.println(Arrays.toString(list));
            ArrayAdapter<String> ad= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
            spinner.setAdapter(ad);
            spinner.setOnItemSelectedListener(this);

            editText1123=findViewById(R.id.rating);

        button=findViewById(R.id.buttonadditem);

        EditText spname=findViewById(R.id.saproductname);

        EditText sprice=findViewById(R.id.saproductprice);
//        myRef1 = FirebaseDatabase.getInstance().getReference("products").push();
        myRef1=fb;
        imageButton=findViewById(R.id.imageButton2);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,
                        300);


//
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               fb.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       System.out.println(snapshot.getKey());
                       dr24.setValue(snapshot.getKey());
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });



                c = 0;
                String sname1 = spname.getText().toString();

                String sprice1 = sprice.getText().toString();
                String srating1 = editText1123.getText().toString();
                dr23.setValue(srating1);

                if (sname1.length() == 0) {
                    err++;
                    spname.setError("Product Name is required");
                }

                if (sprice1.length() == 0) {

                    sprice.setError("Product price is required");
                }
                else {
                    fun(sprice1,sname1,err);
                }



            }

            private void fun(String sprice1,String sname1,int  err) {

                if (err == 0) {
                    Log.d("err1", "onClick: uploading");
                    DatabaseReference myRef = myRef1;




                    DatabaseReference myRefn = myRef.child("product_name");
                    myRefn.setValue(sname1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                c++;

                            } else {
                                Toast.makeText(additempage.this, "failed to add name", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                    DatabaseReference myRefph = myRef.child("product_price");
                    myRefph.setValue(sprice1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                c++;
                                if (c == 2) {
                                    Toast.makeText(additempage.this, "Product added  Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(additempage.this, sellerpage.class);
                                    startActivity(intent1);
                                } else {

                                    Toast.makeText(additempage.this, "Product not added ", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(additempage.this, "failed to add product price", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });

                } else {
                    Toast.makeText(additempage.this, "Fields Can't be empty", Toast.LENGTH_SHORT).show();
                    err = 0;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==300 && resultCode==RESULT_OK){
            imageuri= data.getData();
            imageButton.setImageURI(null);
            imageButton.setImageURI(imageuri);
            upload(imageuri);
        }
    }

    private void upload(Uri imageuri) {
//        StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference("images").child("img121");
//        DatabaseReference fb=FirebaseDatabase.getInstance().getReference("images").push();
//        DatabaseReference dr=fb.child("imageuri");
//        button=findViewById(R.id.buttonadditem);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                firebaseStorage.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        firebaseStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                dr.setValue(uri.toString());
//                                Toast.makeText(additempage.this, "image added successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(i>0 ) {


                String m = list[i];
                dr1.setValue(m);
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}