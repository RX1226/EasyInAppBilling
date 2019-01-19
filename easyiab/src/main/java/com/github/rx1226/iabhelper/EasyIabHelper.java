package com.github.rx1226.iabhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.rx1226.iabhelper.google.IabHelper;
import com.github.rx1226.iabhelper.google.IabResult;
import com.github.rx1226.iabhelper.google.Purchase;


public class EasyIabHelper {
    private static final String TAG = "[IAB]";
    private final int PURCHASE_REQUEST_CODE = 2019;
    private final String PURCHASE_EXTRA_DATA = "PURCHASE_EXTRA_DATA";
    private Context context;
    private IabHelper iabHelper;
    private IabHandler iabHandler;

    public interface IabHandler{
        void onIabSetupSuccess();
        void onIabPurchaseFinished(IabResult result, Purchase purchase);
    }

    public EasyIabHelper(Context context, String base64PublicKey, IabHandler iabHandler){
        this.context = context;
        this.iabHandler = iabHandler;
        iabHelper = new IabHelper(context, base64PublicKey);
        iabHelper.startSetup(result -> {
            Log.d(TAG,"onIabSetupFinished()");
            if (!result.isSuccess()) {
                Log.d(TAG, "Problem setting up In-app Billing: " + result);
            }else {
                this.iabHandler.onIabSetupSuccess();
            }
        });
    }

    public boolean unHandleActivityResult(int requestCode, int resultCode, Intent data){
        return (iabHelper != null) && !iabHelper.handleActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        if (iabHelper != null) {
            try {
                iabHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
            iabHelper = null;
        }
    }

    public void purchase(String productId){
        if (iabHelper != null) {
            try {
                iabHelper.launchPurchaseFlow((Activity) context,
                        productId,
                        PURCHASE_REQUEST_CODE,
                        (result, purchase) -> {
                            iabHandler.onIabPurchaseFinished(result, purchase);
                        },
                        PURCHASE_EXTRA_DATA);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
    }
    public void purchaseAndConsume(String productId){
        if (iabHelper != null) {
            try {
                iabHelper.launchPurchaseFlow((Activity) context,
                        productId,
                        PURCHASE_REQUEST_CODE,
                        (result, purchase) -> {
                            iabHandler.onIabPurchaseFinished(result, purchase);
                            if(result.isSuccess()){
                                consumeAsync(purchase);
                            }
                        },
                        PURCHASE_EXTRA_DATA);
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
    }
    public void queryInventoryAsync(IabHelper.QueryInventoryFinishedListener listener){
        try {
            iabHelper.queryInventoryAsync(listener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    public void consumeAsync(Purchase purchase){
        consumeAsync(purchase, (purchase1, result) -> Log.d(TAG, "consume " + purchase1.getSku() + " result = " + result));
    }

    public void consumeAsync(Purchase purchase, IabHelper.OnConsumeFinishedListener listener){
        try {
            iabHelper.consumeAsync(purchase, listener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }
}
