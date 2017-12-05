package com.androidapp.test.mybank.data.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("password")
    @Expose
    private String password;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                ", customerId =" + customerId +
                ", password =" + password +
                '}';
    }
}