package com.example.boondclient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;


public class List extends Fragment {

    View view, dialogView;
    FloatingActionButton btnViewAddDialog;
    Button btnAddSize;
    EditText etAddSize, etAddPrice;
    RecyclerView recyclerView;
    SizeAdaptor adapter;
    String number;

    public List() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        number = getArguments().getString("Number");
        view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Size> options =
                new FirebaseRecyclerOptions.Builder<Size>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Size").orderByChild("Number").equalTo(number), Size.class)
                        .build();
        adapter = new SizeAdaptor(options, getContext());
        recyclerView.setAdapter(adapter);

        btnViewAddDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(getContext())
                        .setContentHolder(new ViewHolder(R.layout.add_size))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();
                dialogView = dialog.getHolderView();

                btnAddSize = dialogView.findViewById(R.id.btnAddSize);
                etAddPrice = dialogView.findViewById(R.id.etAddPrice);
                etAddSize = dialogView.findViewById(R.id.etAddSize);

                btnAddSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HashMap<String, String> data = new HashMap<>();
                        data.put("ID","1");
                        data.put("Price", etAddPrice.getText().toString().trim());
                        data.put("Size", etAddSize.getText().toString().trim());
                        data.put("Number", number);

                        FirebaseDatabase.getInstance().getReference()
                                .child("Size")
                                .push()
                                .setValue(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Data added successfully.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }
                });

            }
        });


    }

    public void init()
    {
        btnViewAddDialog = view.findViewById(R.id.btnViewAddDialog);
        recyclerView = view.findViewById(R.id.rvSizeList);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}