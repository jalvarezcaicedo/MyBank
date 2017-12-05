package com.androidapp.test.mybank.ui.product;

import com.androidapp.test.mybank.data.model.customerinfo.CustomerProductResponse;
import com.androidapp.test.mybank.ui.base.BaseView;

public interface CustomerProductView extends BaseView {

    void showCustomerProductData(CustomerProductResponse body);

}
