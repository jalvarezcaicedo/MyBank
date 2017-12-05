package com.androidapp.test.mybank.data.model.customerinfo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CustomerProductResponse {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("termination_date")
    @Expose
    private String terminationDate;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("product_number")
    @Expose
    private String productNumber;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    @Override
    public String toString() {
        return "CustomerId ->" + getCustomerId() + "\n"
                + "ProductName -> " + getProductName() + "\n"
                + "CreationDate -> " + getCreationDate() + "\n"
                + "TerminationDate -> " + getTerminationDate() + "\n"
                + "Balance -> " + getBalance() + "\n"
                + "Status -> " + getStatus() + "\n"
                + "ProductNumber -> " + getProductNumber() + "\n";
    }
}
