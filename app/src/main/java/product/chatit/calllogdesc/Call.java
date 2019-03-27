package product.chatit.calllogdesc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Date;

import product.chatit.contactdesc.ProfileViewer;

public class Call {
    public static final String TAG = Call.class.getSimpleName();

        String name,phone;
        String date;
        int call_type;

    public String getName() {
        return name;
    }


    public String getPhone() {
        return phone;
    }


    public String getDate() {
        return date;
    }


    public int getCall_type() {
        return call_type;
    }

    public Call(){

    }

    public Call(String name,String phone, String date, int call_type) {
        this.name=name;
        this.phone = phone;
        this.date = date;
        this.call_type = call_type;
    }





}
