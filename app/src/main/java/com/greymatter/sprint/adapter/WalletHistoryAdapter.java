package com.greymatter.sprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.sprint.databinding.WalletHistoryItemBinding;
import com.greymatter.sprint.model.WalletHistory;
import com.greymatter.sprint.model.response.StepHistoryResponse;
import com.greymatter.sprint.model.response.WalletHistoryResponse;

import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {


    Context context;
    List<WalletHistoryResponse.Datum> walletHistoryList;

    public WalletHistoryAdapter(Context context, List<WalletHistoryResponse.Datum> walletHistoryList) {
        this.context = context;
        this.walletHistoryList = walletHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WalletHistoryItemBinding view =  WalletHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.date.setText(walletHistoryList.get(position).getReward_date());
        holder.binding.amount.setText(walletHistoryList.get(position).getReward());
    }

    @Override
    public int getItemCount() {
        return walletHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        WalletHistoryItemBinding binding;
        public ViewHolder(@NonNull WalletHistoryItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
