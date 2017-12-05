package com.androidapp.test.mybank.data.model.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TransactionRequest {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("product_number")
    @Expose
    private String productNumber;
    @SerializedName("channel_id")
    @Expose
    private int channelId;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("type")
    @Expose
    private int type;

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

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
