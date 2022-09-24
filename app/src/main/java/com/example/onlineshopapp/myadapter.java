package com.example.onlineshopapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {
Context context;
ArrayList<user> list;

    public myadapter(ArrayList<user> list,Context context) {

        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
    user user=list.get(position);
    holder.proname1.setText(user.getProduct_name());
    holder.proprice1.setText(String.format("Rs %s", user.getProduct_price()));
Picasso.with(context).load(user.getImageuri()).fit().into(holder.imageView1);

    holder.pbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context,customerpage.class);
            intent.putExtra("from","myadapter");
//            intent.putExtra("userid",user.getUserid1());
            intent.putExtra("prodatap",user.getProduct_price());
            intent.putExtra("procato",user.getCategory());
            intent.putExtra("prodatan",user.getProduct_name());
//            intent.putExtra("cont", String.valueOf(context));
  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(context, user.getProduct_name()+" added to cart", Toast.LENGTH_SHORT).show();
    context.startActivity(intent);

        }
    });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
    TextView proname1,proprice1;
    ImageView imageView1;
    Button pbutton;


        public myviewholder(@NonNull View itemView) {
            super(itemView);
            proname1=itemView.findViewById(R.id.proname);
            proprice1=itemView.findViewById(R.id.proprice);
            imageView1=itemView.findViewById(R.id.productimage);
            pbutton=itemView.findViewById(R.id.button);



        }
    }
}
