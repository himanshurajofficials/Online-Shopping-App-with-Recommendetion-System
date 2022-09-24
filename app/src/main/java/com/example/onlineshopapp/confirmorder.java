package com.example.onlineshopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class confirmorder extends AppCompatActivity {
Button buttonf2;
Button buttonf3;
TextView textView88;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);

        buttonf2=findViewById(R.id.button2);
        buttonf3=findViewById(R.id.button3);
        textView88=findViewById(R.id.textView8);
        Intent intent=getIntent();
        String result=intent.getStringExtra("result");
        textView88.setText(result);


        buttonf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(confirmorder.this,homepage.class);
//
//                intent.putExtra("from","homepage");

                SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_MULTI_PROCESS);

                String m = sh.getString("userid111", "");


                System.out.println("jhgf   "+m);
                intent2.putExtra("username",m);
                startActivity(intent2);
            }
        });

        buttonf3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(confirmorder.this,MainActivity.class);
//                intent.putExtra("usernamecart",userid);
//                intent.putExtra("from","homepage");

                startActivity(intent3);
            }
        });


    }
}