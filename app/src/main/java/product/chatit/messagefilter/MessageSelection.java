package product.chatit.messagefilter;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import product.chatit.Handlers.InfoHandlers;
import product.chatit.R;
import product.chatit.chatdesc.DisplayChatAdapter;

public class MessageSelection extends AppCompatActivity {

    private ArrayList<DisplayMessage> dispMsg = new ArrayList<>();
    private DisplayMessageAdapter displayMessageAdapter;
    private DisplayChatAdapter displayChatAdapter;
    private DisplayMessage displayMessage;
    private InfoHandlers infoHandlers;
    public static final String MessageSelection=MessageSelection.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_messages);
        infoHandlers=new InfoHandlers();
        RecyclerView rcv=findViewById(R.id.accessMessageList);
        displayMessageAdapter=new DisplayMessageAdapter(dispMsg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(displayMessageAdapter);
        loadMessages();
    }

    public void loadMessages() {
        String[] project = new String[]{"address", "creator", "date", "body"};
        displayMessage=new DisplayMessage();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getApplicationContext().getContentResolver().query(uri, project, null, null, null);
        if (c != null && c.moveToFirst()) {
            // do statement retrieves the first contact
            do {
                String number = c.getString(0);
                String name = c.getString(1);
                long date = c.getLong(2);
                String content = c.getString(3);
                String handledDate = infoHandlers.getHandledDate(date);


                displayMessage = new DisplayMessage(name, number, handledDate, content);

                dispMsg.add(displayMessage);
//                displayChatAdapter = new DisplayChatAdapter(dispMsg);
            }
            while (c.moveToNext()); //while statement executes the remaining contacts
        }


    }
}