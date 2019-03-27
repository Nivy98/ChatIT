package product.chatit.Handlers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import product.chatit.contactdesc.ProfileViewer;

public class TextAndCall
{
    public void permissionChecker(Activity activity)
        {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS =
        {
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALL_LOG
        };

        if (!hasPermissions(activity, PERMISSIONS))
        {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
        else
        {
            Toast.makeText(activity,"Permission Denied",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean hasPermissions(Context context, String[] permission) {

        for (String perm : permission) {
            if (ActivityCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public void sendMessages(String phoneNum,View v)
    {
        Intent SmsIntent=new Intent(v.getContext(), SendSms.class);
        SmsIntent.putExtra("phoneNum",phoneNum);
        v.getContext().startActivity(SmsIntent);
    }

    public void makeCall(final String phoneNumber, View v) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));
        if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        v.getContext().startActivity(callIntent);
    }

}
