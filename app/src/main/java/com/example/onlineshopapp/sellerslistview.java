package com.example.onlineshopapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sellerslistview extends AppCompatActivity {
    DatabaseReference myref111,db112;
    String userid112, proname221, proprice221;
    ArrayList<String> arr112 = new ArrayList<>();
    ListView listview1131;
    int countno=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerslistview);

        listview1131=findViewById(R.id.listview111);
        myref111 = FirebaseDatabase.getInstance().getReference("order");
        db112= FirebaseDatabase.getInstance().getReference("users");
        Query query = myref111.orderByValue();
//                    System.out.println(query);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long querysize = snapshot.getChildrenCount();
                System.out.println(querysize);
//                System.out.println(snapshot);

                for (DataSnapshot user : snapshot.getChildren()) {
                    userid112 = user.getKey();
//                    detail112 = detail112 + "Userid : " + userid112 + "\n  ";
//                    System.out.println(userid112);
//                    System.out.println(user);
                    String mm=userid112;
                    assert userid112 != null;

                    Query m = myref111.child(userid112).orderByValue();
                    m.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final int[] c1 = {0};
                            long querysize1 = snapshot.getChildrenCount();
//                            System.out.println(querysize1);
                System.out.println(snapshot);
                            String detail112 ="";

                            for (DataSnapshot user1 : snapshot.getChildren()) {
                                System.out.println(mm);
//                                System.out.println(user1);
                                proname221= (String) user1.child("proname").getValue();
                                proprice221= (String) user1.child("proprice").getValue();
                                c1[0]++;

                                detail112= detail112 + c1[0] +". " +"Product name : " + proname221 + "\n     Product price : Rs " +proprice221 +" \n" ;
                                //System.out.println(mm);
//                            System.out.println(detail112);
//                                    System.out.println("fff"+snapshot.getChildrenCount());
//                        System.out.println(proname221);
//                            System.out.println(proprice221+"\n");
                                if(c1[0] == querysize1){
                                    System.out.println(mm);

                                    String finalDetail11 = detail112;
                                    db112.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot user1121 : snapshot.getChildren())
                                            {
//                                            System.out.println(user1121);
                                                if (user1121.child(mm).getValue()!=null) {
                                                    String name112121= (String) user1121.child(mm).child("name").getValue();
                                                    String phone112121= (String) user1121.child(mm).child("phone_no").getValue();
                                                    String address112121= (String) user1121.child(mm).child("address").getValue();

//                                                System.out.println(name112121);
                                                    arr112.add("Order "+ countno +"\n \n"+"Customer Details :   \n"+"     Name :"+ name112121+" \n     Phone No : "+phone112121 + " \n     Address : "+address112121 +" \n \n"+"Order Details :  \n "+ finalDetail11);
                                                    countno++;
                                                System.out.println(arr112);
                                                    System.out.println(arr112.size());
                                                    System.out.println(querysize);
                                                    if(arr112.size()==querysize-1) {

//                                                        System.out.println(arr112.get(1));
                                                        ArrayAdapter<String> arrayAdapter1121=new ArrayAdapter<String>(sellerslistview.this, android.R.layout.simple_list_item_1,arr112);
                                                        listview1131.setAdapter(arrayAdapter1121);
                                                    }
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



//                                String userdet=userdet(mm);
//                                System.out.println(detail112);
//                                arr112.add(userdet +" \n "+ detail112);
//                                if(arr112.size()==querysize) {
//
//                                    System.out.println(arr112);
//                                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(move11.this, android.R.layout.simple_list_item_1 ,arr112);
//                                    listview.setAdapter(arrayAdapter);
                                }
                            }

//
//                                String finalDetail11 = detail112;
//                                db112.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        for (DataSnapshot user1121 : snapshot.getChildren())
//                                        {
////                                            System.out.println(user1121);
//                                            if (user1121.child(mm).getValue()!=null) {
//                                                String name112121= (String) user1121.child(mm).child("name").getValue();
//                                                String phone112121= (String) user1121.child(mm).child("phone_no").getValue();
//                                                arr112.add("Name :"+ name112121+" \n Phone No : "+phone112121 +" \n "+ finalDetail11);
//                                                if(arr112.size()==querysize) {
//
//                                                    System.out.println(arr112);
//                                                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(move11.this, android.R.layout.simple_list_item_1 ,arr112);
//                                                    listview.setAdapter(arrayAdapter);
//                                                }
//                                            }
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });


                        }




                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }

//            private String userdet(String mm) {
//
//                db112.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot user1121 : snapshot.getChildren())
//                        {
////                                            System.out.println(user1121);
//                            if (user1121.child(mm).getValue()!=null) {
//                                String name112121= (String) user1121.child(mm).child("name").getValue();
//                                String phone112121= (String) user1121.child(mm).child("phone_no").getValue();
//
//
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//
//            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
