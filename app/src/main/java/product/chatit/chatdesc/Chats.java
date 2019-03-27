package product.chatit.chatdesc;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import product.chatit.DatabaseManager.DatabaseContainer;
import product.chatit.R;
import product.chatit.messagefilter.DisplayMessage;
import product.chatit.messagefilter.MessageSelection;

import static android.app.Activity.RESULT_OK;


public class Chats extends Fragment {
    private static final int REQUEST_CODE = 3;
    private ArrayList<DisplayMessage> dispChat=new ArrayList<>();
    private FloatingActionButton addChats;
    private RecyclerView recyclerView;
    private DisplayChatAdapter dispChatAdapt;
    DatabaseContainer databaseContainer;
    private static final String Chats=Chats.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_chats, null);
        addChats=v.findViewById(R.id.addMessages);
        recyclerView=v.findViewById((R.id.accessMessage));
        databaseContainer=new DatabaseContainer(getContext());
        dispChat=databaseContainer.getChats();
        dispChatAdapt=new DisplayChatAdapter(dispChat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dispChatAdapt);

        addChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageSelection.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });



        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                String name=intent.getStringExtra("returnName");
                String number=intent.getStringExtra("returnPh");
                String date=intent.getStringExtra("returnDate");
                String msg=intent.getStringExtra("returnmsg");

                DisplayMessage dchat = new DisplayMessage(name, number, date, msg);
                databaseContainer.insertDataChats(dchat);
                dispChat=databaseContainer.getChats();
                dispChatAdapt=new DisplayChatAdapter(dispChat);
                recyclerView.setAdapter(dispChatAdapt);
            }
            else
                Log.d(Chats,"Result Code Error");
        }
        else
            Log.d(Chats,"Request Code Error");
    }




}



