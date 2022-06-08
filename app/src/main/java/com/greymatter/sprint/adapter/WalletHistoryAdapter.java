package com.greymatter.sprint.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greymatter.sprint.databinding.WalletHistoryItemBinding;
import com.greymatter.sprint.model.WalletHistory;

import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.ViewHolder> {


    Context context;
    List<WalletHistory> walletHistoryList;

    public WalletHistoryAdapter(Context context, List<WalletHistory> walletHistoryList) {
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

        holder.binding.message.setText(walletHistoryList.get(position).getMsg());
        holder.binding.date.setText(walletHistoryList.get(position).getDate());
        holder.binding.amount.setText(walletHistoryList.get(position).getAmount());
        holder.binding.transactionType.setText(walletHistoryList.get(position).getTransaction_type());
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
