package com.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shashank.sony.fancytoastlib.FancyToast;

class ConnectReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        if (isConnected(context)) {
            FancyToast.makeText(context, "Internet Is Connected", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        } else {
            FancyToast.makeText(context, "No Internet Connection", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
    public boolean isConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

