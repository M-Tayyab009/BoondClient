package com.example.boondclient;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class SizeAdaptor extends FirebaseRecyclerAdapter<Size, SizeAdaptor.SizeViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public SizeAdaptor(@NonNull FirebaseRecyclerOptions<Size> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull SizeViewHolder holder, int position, @NonNull Size model) {

        holder.tvListSize.setText(model.getSize());
        holder.tvListPrice.setText(model.getPrice());

        holder.ivListEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.update_size))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

                View dialogView = dialog.getHolderView();

                EditText etUpdateSize, etUpdatePrice;
                Button btnUpdateSize;

                etUpdatePrice = dialogView.findViewById(R.id.etUpdatePrice);
                etUpdateSize = dialogView.findViewById(R.id.etUpdateSize);
                btnUpdateSize = dialogView.findViewById(R.id.btnUpdateSize);

                etUpdatePrice.setText(model.getPrice());
                etUpdateSize.setText(model.getSize());

                btnUpdateSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("Price", etUpdatePrice.getText().toString().trim());
                        updatedData.put("Size", etUpdateSize.getText().toString().trim());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Size")
                                .child(getRef(position).getKey())
                                .updateChildren(updatedData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Data has been updated", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();
                    }
                });
            }
        });

        holder.ivListDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Size")
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Data has been Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sizelist, parent, false);
        return new SizeViewHolder(view);
    }

    public class SizeViewHolder extends RecyclerView.ViewHolder{

        TextView tvListSize, tvListPrice;
        ImageView ivListEdit, ivListDelete;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvListPrice = itemView.findViewById(R.id.tvListPrice);
            tvListSize = itemView.findViewById(R.id.tvListSize);
            ivListDelete = itemView.findViewById(R.id.ivListDelete);
            ivListEdit = itemView.findViewById(R.id.ivListEdit);

        }
    }

}
