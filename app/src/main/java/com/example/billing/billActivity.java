package com.example.billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class billActivity extends AppCompatActivity {

    private TextView billQr,billDisplay;
    public static  TextView billglobal;
    private Button billAdd, billScann, billGet;
    private String billStr;
    private String billcat;
    private String billitems;
    private DatabaseReference dref;
    private float total;
    private FirebaseAuth firebaseAuth;
    ListView listView;
    ArrayList<String> arraylist ;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        firebaseAuth = FirebaseAuth.getInstance();

        listView = (ListView)findViewById(R.id.item_list);
        arraylist = new ArrayList<>();

        billcat = "Product\t\t\t\t\t\t\t                  Gst\t\t\t\t\t\t\t\t                  Price";

        arraylist.add(billcat);

        intit();

        billScann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(billActivity.this,scannerViewBill.class));

            }
        });

        billAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProduct();

            }
        });


        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arraylist);
        listView.setAdapter(adapter);

    }

    private void addProduct() {
        String qr = billQr.getText().toString();
        Log.d("scanncode",qr);
        if(!TextUtils.isEmpty(qr)) {
            dref.child(qr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        String title = "" + snapshot.child("name").getValue(String.class);
                        Log.d("scanncode1", title);
                        String price = "" + snapshot.child("price").getValue(String.class);

                        for (int i = title.length(); i < 20; i++) {
                            title += " ";
                        }

                        float gst = (float) (0.18 * Float.parseFloat(price));
                        String gstStr = String.valueOf(gst);
                        for (int j = gstStr.length(); j < 20; j++) {
                            gstStr += " ";
                        }


                        total += Float.parseFloat(price);
                        billitems = title + "\t\t\t\t\t\t\t" + gstStr + "\t\t\t\t\t\t" + price;
                        arraylist.add(billitems);
                        listView.setAdapter(adapter);
                        String totalString = String.valueOf(total);
                        billStr = "Total ₹" + totalString;
                        billDisplay.setText(billStr);
                        billQr.setText("");

                    }
                    else
                        {
                        Toast.makeText(billActivity.this, "Item not found!!!", Toast.LENGTH_SHORT).show();
                        billQr.setText("");
                        }
                }


               @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(billActivity.this, "Please enter the serial number", Toast.LENGTH_SHORT).show();
        }
    }

    private void intit() {
        billQr = findViewById(R.id.billQr);
        billScann = findViewById(R.id.billScann);
        billAdd = findViewById(R.id.billAdd);
        billglobal = findViewById(R.id.billQr);
        billDisplay = findViewById(R.id.billDisplay);
        final FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");
        dref = FirebaseDatabase.getInstance().getReference().child("Users").child(resultemail).child("items");
    }
}