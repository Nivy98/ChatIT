package product.chatit.Handlers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class InfoHandlers
{

    public String getContactName(final String phoneNumber, Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }
        return contactName;
    }

    public String getHandledDate(long time)
    {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm aa dd-MM-yyyy ", cal).toString();
        return date;
    }

    public String handleNumber(String num)
    {
        if(num.contains("+91"))
            num=num.replace("+91","");
        if(num.contains("-"))
            num=num.replace("-","");
        if(num.contains(" "))
            num=num.replace(" ","");
        return num;
    }

}
