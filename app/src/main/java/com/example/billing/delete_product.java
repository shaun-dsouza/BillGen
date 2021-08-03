package com.example.billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class delete_product extends AppCompatActivity {

    private TextView serial;
    private Button delete;
    public static TextView serialqrdel;
    private Button deleteqr;
    private FirebaseAuth firebaseAuth;
    String serialnum;

    DatabaseReference dref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        firebaseAuth = FirebaseAuth.getInstance();


        serial = findViewById(R.id.deleteserial);
        delete = findViewById(R.id.pdelete);
        serialqrdel = findViewById(R.id.deleteserial);
        deleteqr = findViewById(R.id.delqr);




        deleteqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(delete_product.this, scannerViewDel.class));
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serialnum = serial.getText().toString();
                final FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
                String finaluser = users.getEmail();
                String resultemail = finaluser.replace(".", "");
                dref = FirebaseDatabase.getInstance().getReference().child("Users").child(resultemail).child("items");

                dref.child(serialnum).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            dref.child(serialnum).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(delete_product.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    serial.setText("");
                                    //startActivity(new Intent(delete_product.this, MainActivity.class));
                                }
                            });
                        } else {
                            Toast.makeText(delete_product.this, "Item does not exist!!", Toast.LENGTH_SHORT).show();
                            serial.setText("");
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }

                });

            }
        });

    }
}

   /* private void deleteProduct(TextView serial) {

        String serialnum = serial.getText().toString();
        final FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        String finaluser = users.getEmail();
        String resultemail = finaluser.replace(".", "");
        dref = FirebaseDatabase.getInstance().getReference().child("Users").child(resultemail).child("items");
        if (!TextUtils.isEmpty(serialnum)) {
            dref.child(serialnum).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(delete_product.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(delete_product.this, MainActivity.class));
                }
            });
        } else {
            Toast.makeText(delete_product.this, "Please enter serial number", Toast.LENGTH_SHORT).show();
        }


    }
}}*/