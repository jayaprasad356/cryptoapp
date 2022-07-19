package com.greymatter.sprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.sprint.databinding.NotificationItemBinding;
import com.greymatter.sprint.model.Notification;
import com.greymatter.sprint.model.response.NotificationResponse;
import com.greymatter.sprint.model.response.StepHistoryResponse;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    Context context;
    List<NotificationResponse.Datum> NotificationList;

    public NotificationAdapter(Context context, List<NotificationResponse.Datum> NotificationList) {
        this.context = context;
        this.NotificationList = NotificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationItemBinding view =  NotificationItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.title.setText(NotificationList.get(position).getTitle());
        holder.binding.description.setText(NotificationList.get(position).getDescription());
        holder.binding.date.setText(NotificationList.get(position).getDate_created());
    }

    @Override
    public int getItemCount() {
        return NotificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NotificationItemBinding binding;
        public ViewHolder(@NonNull NotificationItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
