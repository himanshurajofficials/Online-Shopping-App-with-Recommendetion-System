package com.example.onlineshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homepage extends AppCompatActivity {
 Button buttonh1,buttonh2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

         buttonh1=findViewById(R.id.buttonh2);
        buttonh2=findViewById(R.id.buttonh3);
        Intent intent=getIntent();
        String userid=intent.getStringExtra("username");
        System.out.println("hjhj hj"+userid);


        buttonh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homepage.this,customerpage.class);
              intent.putExtra("usernamecart",userid);
              intent.putExtra("from","homepage");
                System.out.println("hjhj hj1"+userid);
                startActivity(intent);
            }
        });

        buttonh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(homepage.this,sellerpage.class);
                startActivity(intent);
            }
        });
    }
}