package com.greymatter.sprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.sprint.databinding.StepHistoryItemBinding;
import com.greymatter.sprint.model.StepHistory;

import java.util.List;

public class StepHistoryAdapter extends RecyclerView.Adapter<StepHistoryAdapter.ViewHolder> {


    Context context;
    List<StepHistory> StepHistoryList;

    public StepHistoryAdapter(Context context, List<StepHistory> StepHistoryList) {
        this.context = context;
        this.StepHistoryList = StepHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StepHistoryItemBinding view =  StepHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.step.setText(StepHistoryList.get(position).getStep());
        holder.binding.calorie.setText(StepHistoryList.get(position).getCalorie());
        holder.binding.date.setText(StepHistoryList.get(position).getDate());
        holder.binding.amount.setText(StepHistoryList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return StepHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        StepHistoryItemBinding binding;
        public ViewHolder(@NonNull StepHistoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
