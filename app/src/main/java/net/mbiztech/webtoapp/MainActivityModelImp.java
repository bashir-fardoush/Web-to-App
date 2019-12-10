package net.mbiztech.webtoapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import net.mbiztech.webtoapp.network.ApiClient;
import net.mbiztech.webtoapp.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityModelImp implements MainActivityModel {
    private Context context;
    private Util util;


    public MainActivityModelImp(Context context) {
        this.context = context;
        util = new Util();
    }

    @Override
    public boolean isPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
             return true;
        else return false;
    }

    @Override
    public boolean isNetworkConnected() {
        return Util.isNetworkConnected(context);
    }

    @Override
    public void sendImeiToDb(int userId, final RequestCompleteListener<String> callback) {

        String imei = readImei();

        Log.d("mytag", "sendImeiToDb: device IMEI is: "+ imei);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.sendImeiToDb(userId, imei).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body().equals("success")){
                    callback.onRequestSuccess("Success!");
                }else {
                    callback.onRequestFailed("Unable to save information");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                callback.onRequestFailed("Failed to save information, please try later");
                Log.d("mytag", "onFailure: "+t.getMessage());
            }
        });


    }

    @SuppressLint("MissingPermission")
    private String readImei() {
        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imei = telephonyManager.getImei();
        }else {
            imei  = telephonyManager.getDeviceId();
        }
        return imei;
    }



}
