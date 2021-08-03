package com.example.billing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_product extends AppCompatActivity {

    private TextView name;
    private TextView price;
    private TextView serial;
    private Button add;
    public static TextView serialqr;
    private Button btnscan;
    private FirebaseAuth firebaseAuth;

    DatabaseReference dref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.addname);
        price = findViewById(R.id.addprice);
        serial = findViewById(R.id.addserial);
        add = findViewById(R.id.addproduct);

        btnscan = findViewById(R.id.qrScan);
        serialqr = findViewById(R.id.addserial);

        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(add_product.this,scannerView.class));
            }
        });


        dref = FirebaseDatabase.getInstance().getReference().child("Users");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItemData();
            }
        });



        //FirebaseDatabase.getInstance().getReference().child("Item").child("dove").setValue("10");

    }

    private void insertItemData() {

        String pname = name.getText().toString();
        String pprice = price.getText().toString();
        String pserial = serial.getText().toString();
        final FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String finaluser=users.getEmail();
        String resultemail = finaluser.replace(".","");

        Item item = new Item(pname,pprice,pserial);

        if(!TextUtils.isEmpty(pname)&&!TextUtils.isEmpty(pprice)&&!TextUtils.isEmpty(pserial))
        {
            dref.child(resultemail).child("items").child(pserial).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(add_product.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    price.setText("");
                    serial.setText("");
                    //startActivity(new Intent(add_product.this,MainActivity.class));
                }
            });
        }
        else {
            Toast.makeText(add_product.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}