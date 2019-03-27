package product.chatit.calllogdesc;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import product.chatit.DatabaseManager.DatabaseContainer;
import product.chatit.Handlers.InfoHandlers;
import product.chatit.R;
import product.chatit.contactdesc.DisplayContacts;


public class CallLogs extends Fragment{
    RecyclerView recyclerView;
    ArrayList<Call> listCalls = new ArrayList<>();
    ArrayList<DisplayContacts> displayContacts;
    CallAdapter callAdapter;
    DatabaseContainer databaseContainer;
    Call call;
    InfoHandlers infoHandlers;
    BroadcastReceiver inComingContacts;
    private static final String CallLogs = CallLogs.class.getSimpleName();
    private static int flag=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoHandlers = new InfoHandlers();
        databaseContainer = new DatabaseContainer(getActivity());
        listCalls=databaseContainer.getCalls();
        sortLogs(listCalls);
        callAdapter=new CallAdapter(listCalls);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_call_logs, null);
        recyclerView = v.findViewById(R.id.accessCalls);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayout.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        inComingContacts=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String phoneNumber=intent.getExtras().getString("phval");
                String name=intent.getExtras().getString("phname");
                Log.d("Broadcast","received");
                getCallLogs(phoneNumber,name);
                listCalls=databaseContainer.getCalls();
                sortLogs(listCalls);
                callAdapter=new CallAdapter(listCalls);
                recyclerView.setAdapter(callAdapter);
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(inComingContacts,new IntentFilter("CallLogAction"));
        if(flag==0) {
            recyclerView.setAdapter(callAdapter);
            flag = 1;
        }
       // Log.d(CallLogs, "hello");
        return v;
    }

    public void sortLogs( ArrayList<Call> listCalls)
    {
        listCalls.sort(new Comparator<Call>() {
            @Override
            public int compare(Call o1, Call o2) {
                return (o1.getDate().compareTo(o2.getDate()));
            }
        });
    }



    public void getCallLogs(String phone,String pname)
    {
        String[] projection = {CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DATE};
        Uri uri = CallLog.Calls.CONTENT_URI;
          //  Log.d(CallLogs,"intogetcall logs");


        @SuppressLint("MissingPermission") Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, CallLog.Calls.DATE + " ASC");
        assert cursor != null;
        while (cursor.moveToNext())
        {
            String phNumber = infoHandlers.handleNumber(cursor.getString(0));
            if(phNumber.equals(phone))
            {
                long callDate = cursor.getLong(2);
                String date=String.valueOf(callDate);
                Log.d(CallLogs,phNumber);
                String name = pname;
                String callType = cursor.getString(1);


                int ch = Integer.parseInt(callType);
                int type_of_call = 0;
                switch (ch)
                {
                                case CallLog.Calls.OUTGOING_TYPE:
                                    type_of_call = 0;
                                    break;
                                case CallLog.Calls.INCOMING_TYPE:
                                    type_of_call = 1;
                                    break;
                                case CallLog.Calls.MISSED_TYPE:
                                    type_of_call = 2;
                                    break;
                }
                            call = new Call(name, phNumber, date, type_of_call);
                            listCalls.add(call);
                            databaseContainer.insertCalls(call);
              //  Log.d(CallLogs,name+" "+phNumber+" "+date+" "+"calllog inserted");
            }

        }
        cursor.close();


    }
}

