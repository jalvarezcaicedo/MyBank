package com.androidapp.test.mybank.ui.transaction;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidapp.test.mybank.R;
import com.androidapp.test.mybank.data.model.transaction.TransactionResponse;

import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

    private Context context;
    private List<TransactionResponse> transactions;


    public TransactionAdapter(Context context, List<TransactionResponse> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionHolder(v);
    }

    public void clearData() {
        transactions.clear();
    }

    @Override
    public void onBindViewHolder(TransactionHolder holder, int position) {
        if (position % 2 == 0)
            holder.relativeContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        else
            holder.relativeContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        holder.customerId.setText("CustomerId " + transactions.get(position).getCustomerId());
        holder.prodNumb.setText("Number " + transactions.get(position).getProductNumber());
        holder.status.setText(transactions.get(position).getStatus());
        holder.channel.setText("Channel " + transactions.get(position).getChannelId());
        holder.amount.setText("Amount " + String.valueOf(transactions.get(position).getAmount()));
        holder.txNumber.setText("Tx #" + String.valueOf(transactions.get(position).getTransactionNumber()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeContainer;
        TextView customerId;
        TextView prodNumb;
        TextView status;
        TextView channel;
        TextView amount;
        TextView txNumber;

        TransactionHolder(View v) {
            super(v);
            relativeContainer = v.findViewById(R.id.transaction_container);
            customerId = v.findViewById(R.id.transaction_id);
            prodNumb = v.findViewById(R.id.transaction_prod_number);
            status = v.findViewById(R.id.transaction_status);
            channel = v.findViewById(R.id.transaction_channel);
            amount = v.findViewById(R.id.transaction_amount);
            txNumber = v.findViewById(R.id.transaction_number);
        }
    }
}
