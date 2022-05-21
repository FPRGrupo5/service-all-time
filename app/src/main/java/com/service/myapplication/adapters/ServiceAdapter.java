package com.service.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.service.myapplication.FullScreenService;
import com.service.myapplication.R;
import com.service.myapplication.models.ServiceModel;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{
    private Context ctx;
    private final ArrayList<ServiceModel> serviceArrayList;
    public ServiceAdapter(Context cxt, ArrayList<ServiceModel> serviceArrayList) {
        this.ctx = cxt;
        this.serviceArrayList = serviceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_service, parent, false);
        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(ctx, FullScreenService.class);
            ctx.startActivity(intent);
        });
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceModel service = serviceArrayList.get(position);
        holder.txtServiceName.setText(service.getName());
        holder.txtProName.setText("Profesional ?");
        //holder.imgAvatar.setImageURI();
        //holder.imgService.setImageURI();
        holder.txtRating.setText(R.string.service_rating);
        holder.txtRating.setText(holder.txtRating.getText()+": 3");
        holder.txtDatePublish.setText(service.getDateCreated());
    }

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtServiceName;
        private final TextView txtProName;
        private ImageView imgAvatar;
        private ImageView imgService;
        private final TextView txtRating;
        private final TextView txtDatePublish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtServiceName = itemView.findViewById(R.id.txt_serviceName);
            txtProName = itemView.findViewById(R.id.txt_proName);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            imgService = itemView.findViewById(R.id.img_service);
            txtRating = itemView.findViewById(R.id.txt_rating);
            txtDatePublish = itemView.findViewById(R.id.txt_datePublish);
        }
    }
}
