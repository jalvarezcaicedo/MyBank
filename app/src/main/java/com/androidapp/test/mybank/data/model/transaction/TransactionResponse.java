package com.androidapp.test.mybank.data.model.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TransactionResponse {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("product_number")
    @Expose
    private String productNumber;
    @SerializedName("channel_id")
    @Expose
    private Integer channelId;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("transaction_number")
    @Expose
    private Integer transactionNumber;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
}
