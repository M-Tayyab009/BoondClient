package com.example.boondclient;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.OrderViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options, Context context) {

        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {

        holder.tvOrderPrice.setText("Price: "+model.getPrice());
        holder.tvOrderQuantity.setText("Quantity: "+model.getQuantity());
        holder.tvOrderSize.setText("Size: "+model.getSize());
        holder.tvOrderStatus.setText(model.getStatus());

        holder.ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.view_order))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                dialog.show();

                View dv = dialog.getHolderView();

                TextView tvPrice, tvSize, tvQuantity, tvAddress, tvNumber;
                Button btnDelivered, btnBack;

                tvPrice = dv.findViewById(R.id.tvPrice);
                tvSize = dv.findViewById(R.id.tvSize);
                tvQuantity = dv.findViewById(R.id.tvQuantity);
                tvAddress = dv.findViewById(R.id.tvAddress);
                tvNumber = dv.findViewById(R.id.tvNumber);
                btnDelivered = dv.findViewById(R.id.btnDelivered);
                btnBack = dv.findViewById(R.id.btnBack);

                tvPrice.setText("P: "+model.getPrice());
                tvSize.setText("S: "+model.getSize());
                tvQuantity.setText("Q: "+model.getQuantity());
                tvNumber.setText("N: "+model.getNumber());

                btnDelivered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("Status", "Delivered");

                        FirebaseDatabase.getInstance().getReference()
                                .child("Order")
                                .child(getRef(position).getKey())
                                .updateChildren(updatedData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Delivered", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        dialog.dismiss();
                    }
                });

            }
        });
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlist, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrderPrice, tvOrderQuantity, tvOrderSize, tvOrderStatus;
        ImageView ivView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvOrderSize = itemView.findViewById(R.id.tvOrderSize);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            ivView = itemView.findViewById(R.id.ivView);

        }
    }

}
