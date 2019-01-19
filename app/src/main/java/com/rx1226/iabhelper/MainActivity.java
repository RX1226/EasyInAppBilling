package com.rx1226.iabhelper;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.github.rx1226.iabhelper.EasyIabHelper;
import com.github.rx1226.iabhelper.google.IabResult;
import com.github.rx1226.iabhelper.google.Purchase;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements EasyIabHelper.IabHandler{
    private String iabKey = "yourkey";
    private String productId = "productId";
    private EasyIabHelper easyIabHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyIabHelper = new EasyIabHelper(this, iabKey, this);

        findViewById(R.id.btn_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyIabHelper.purchaseAndConsume(productId);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (easyIabHelper.unHandleActivityResult(requestCode, resultCode, data)) {
            // IABUtil not handle, so pass it
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            // onActivityResult handled by IABUtil
        }
    }

    @Override
    public void onDestroy() {
        easyIabHelper.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onIabSetupSuccess() {
        // Set up success
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
        // Handle Purchase Finished
    }
}
