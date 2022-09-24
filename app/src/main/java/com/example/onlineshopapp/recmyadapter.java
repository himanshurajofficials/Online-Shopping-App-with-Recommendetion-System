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

public class recmyadapter extends RecyclerView.Adapter<recmyadapter.myviewholder> {
Context context;
ArrayList<recuser> list;

    public recmyadapter(ArrayList<recuser> list, Context context) {

        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reccardview,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
    recuser user=list.get(position);
    holder.proname1.setText(user.getProduct_name());
    holder.proprice1.setText(user.getProduct_price());
Picasso.with(context).load(user.getImageuri()).fit().into(holder.imageView1);

    holder.pbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context,recadapterdata.class);
            intent.putExtra("prodatap",user.getProduct_price());
            intent.putExtra("prodatan",user.getProduct_name());
            intent.putExtra("procato",user.getCategory());
  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
            Toast.makeText(context, user.getProduct_name()+" added to cart", Toast.LENGTH_SHORT).show();
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
