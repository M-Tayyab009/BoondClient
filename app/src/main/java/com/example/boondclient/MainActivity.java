package com.example.boondclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText etName, etShopName, etAddress, etNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void login (View v)
    {

        startActivity();
    }

    public void add(View v)
    {
        if(etName.getText().toString().trim().isEmpty())
        {
            etName.setError("Please Enter Your Name");
        }
        else
        {
            HashMap<String, String> data = new HashMap<>();
            data.put("Name",etName.getText().toString().trim());
            data.put("Shop_Name",etShopName.getText().toString().trim());
            data.put("Address",etAddress.getText().toString().trim());
            data.put("Number",etNumber.getText().toString().trim());

            FirebaseDatabase.getInstance().getReference()
                    .child("Shop")
                    .push()
                    .setValue(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Data added successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void init()
    {
        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etName);
        etShopName = findViewById(R.id.etShopName);
        etNumber = findViewById(R.id.etPhone);
    }

    public void startActivity(){
        Intent intent = new Intent(MainActivity.this, MainView.class);
        intent.putExtra("Number", "+923344227779");
        startActivity(intent);
    }
}