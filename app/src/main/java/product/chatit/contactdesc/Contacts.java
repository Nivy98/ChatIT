package product.chatit.contactdesc;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import product.chatit.DatabaseManager.DatabaseContainer;
import product.chatit.Handlers.InfoHandlers;
import product.chatit.R;
import product.chatit.Handlers.TextAndCall;
import product.chatit.calllogdesc.CallLogs;

import static android.app.Activity.RESULT_OK;


public class Contacts extends Fragment {

    private static final int REQUEST_CODE = 1;

    DisplayContacts dc;
    InfoHandlers infoHandlers;

    private ArrayList<DisplayContacts> displayContacts=new ArrayList<>();
    private RecyclerView recyclerView;
    private DisplayContactsAdapter dca;
    private FloatingActionButton addContacts;
    private static final String Contacts = Contacts.class.getSimpleName();
    DatabaseContainer databaseContainer;
    CallLogs callLogs;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        infoHandlers=new InfoHandlers();
        callLogs=new CallLogs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_contacts, null);
        addContacts =  v.findViewById(R.id.addContacts);
        recyclerView=v.findViewById(R.id.accesslist);

        databaseContainer =new DatabaseContainer(getContext());
        displayContacts = databaseContainer.getContacts();
        dca=new DisplayContactsAdapter(displayContacts);

         LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayout.VERTICAL, false);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(dca);
         loadContacts();
         return v;
    }



    public void loadContacts()
    {
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://contacts");
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                intent.setType(Phone.CONTENT_TYPE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent)
    {
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = intent.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.Contacts.PHOTO_URI};

                Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection,null, null, null);
                cursor.moveToFirst();

                String number = cursor.getString(0);
                String name = cursor.getString(1);
                String photoURI = cursor.getString(2);


                dc=new DisplayContacts();
                dc.setName(name);
                String phNumber=infoHandlers.handleNumber(number);
                dc.setPhoneNum(phNumber);
                dc.setPhotoURI(photoURI);

                //Checking If Contact Already Exists
                boolean flag=false;
                for(DisplayContacts check:displayContacts)
                {
                    if(check.getPhoneNum().equals(number))
                    {
                        Toast.makeText(getContext(),"Contact Already Available",Toast.LENGTH_SHORT).show();
                        flag=true;
                    }
                }
                //Inserting if Contact is Not Already Available
                if(!flag)
                {
                    databaseContainer.insertData(dc);
                    displayContacts.add(dc);
                    sendyBroadcast(dc.getPhoneNum(),dc.getName());


                    Toast.makeText(getContext(),"Contact Inserted",Toast.LENGTH_SHORT).show();
                    dca.notifyDataSetChanged();
                }
                cursor.close();
            }
            else
            {
                Log.d(Contacts,"ResultCode Error");
            }
        }
        else {
            Log.d(Contacts, "RequestCode Error");
        }
    }


    public void sendyBroadcast(String phone,String name)
    {
   //     Log.d("Broadcast","Came");
        Intent intent=new Intent("CallLogAction");
        intent.putExtra("phval",phone);
        intent.putExtra("phname",name);
//        intent.putExtra("parcel_data",(Serializable) dc);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }


}
