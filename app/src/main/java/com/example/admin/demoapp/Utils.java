package com.example.admin.demoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    public static   byte[] encodeValue(String string){
        return Base64.encode(string.getBytes(), Base64.DEFAULT);
    }
    public static byte[] decodeValue(byte[]encodeValue){
        return  Base64.decode(encodeValue, Base64.DEFAULT);
    }

    public static String decode(String string){
        return new String(Base64.decode(string,Base64.DEFAULT));
    }

    public static boolean checkInternetConnection(Context context) {
        try {
            ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static void openSettingNetwork(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
        activity.startActivity(intent);
    }

    public static void openWifiSetting(Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }
    /**
     * Hides virtual keyboard
     *
     *
     */
    public static void hideKeyboard(View view, Context context)
    {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String getHASH(String email,String indentity) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(indentity.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = (sha256_HMAC.doFinal(email.getBytes()));
            StringBuffer result = new StringBuffer();
            for (byte b : hash) {
                result.append(String.format("%02x", b));
            }
            return result.toString();

            //7485d90846cfc7a2ca6c9ca58a015e6bb336c64ec5ab982a2c4d2b8e1b5a28bc
            //7485d90846cfc7a2ca6c9ca58a015e6bb336c64ec5ab982a2c4d2b8e1b5a28bc

        }
        catch (Exception e){
            System.out.println("Error");
        }
        return null;
    }
    public static boolean checkPermisson(Context context,List<String> listPermissonRequest){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> listPermisson=new ArrayList<>();

        //android.Manifest.permission.RECORD_AUDIO
        for(String s:listPermissonRequest){
            if (ActivityCompat.checkSelfPermission(context, s) == PackageManager.PERMISSION_DENIED) {
                Log.d("", "checkPermisson: "+s.toString());
                listPermisson.add(s);
            }
        }
        if(listPermisson.size()==0){
            return true;
        }
        int index=0;
        String permisson[]=new String[listPermisson.size()];
        for(String list:listPermisson){
            permisson[index]=list;
            index++;
        }
        ActivityCompat.requestPermissions((Activity) context,permisson,100);
        return false;
    }


}