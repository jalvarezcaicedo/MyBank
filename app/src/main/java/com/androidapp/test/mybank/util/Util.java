package com.androidapp.test.mybank.util;

import android.content.Context;

import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void set(Context context, String key, String value) {
        context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    public static <T> void setList(Context context, String key, List<T> list) {
        Gson gson = new Gson();
        String json;
        if (list != null || list.size() > 0) {
            json = gson.toJson(list);
        } else {
            json = "";
        }

        Util.set(context, key, json);
    }

    public static List<CustomerProductResponse> getListProducts(String json) {
        List<CustomerProductResponse> customerProductResponses = new ArrayList<>();
        try {
            JSONArray jsonMainArr = new JSONArray(json);
            for (int i = 0; i < jsonMainArr.length(); ++i) {
                CustomerProductResponse customerProductResponse = new CustomerProductResponse();
                JSONObject jsonObject = jsonMainArr.getJSONObject(i);
                customerProductResponse.setCustomerId(jsonObject.get("customer_id").toString());
                customerProductResponse.setProductName(jsonObject.get("product_name").toString());
                customerProductResponse.setCreationDate(jsonObject.get("creation_date").toString());
                customerProductResponse.setTerminationDate(jsonObject.has("termination_date") ? jsonObject.get("termination_date").toString() : "null");
                customerProductResponse.setBalance(Double.parseDouble(jsonObject.get("balance").toString()));
                customerProductResponse.setStatus(jsonObject.get("status").toString());
                customerProductResponse.setProductNumber(jsonObject.get("product_number").toString());

                customerProductResponses.add(customerProductResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customerProductResponses;
    }
}
