package product.chatit.messagefilter;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class DisplayMessage {
    private String name,number,content,date;

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public DisplayMessage(){ }

    public DisplayMessage(String name, String number, String date, String content) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }




}
