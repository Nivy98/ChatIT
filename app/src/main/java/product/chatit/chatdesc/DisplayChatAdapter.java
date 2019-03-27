package product.chatit.chatdesc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import product.chatit.R;
import product.chatit.Handlers.TextAndCall;
import product.chatit.messagefilter.DisplayMessage;

public class DisplayChatAdapter extends RecyclerView.Adapter<DisplayChatAdapter.MyViewHolder>
{
    public static final String DisplayChatAdapter=DisplayChatAdapter.class.getSimpleName();
    private ArrayList<DisplayMessage> dispChat;
    private Chats chat;
    private TextAndCall textAndCall;

    public DisplayChatAdapter(ArrayList<DisplayMessage> dispChat)
    {
        this.dispChat=dispChat;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name,phone,msgText,msgTime;


        public MyViewHolder(final View view) {
            super(view);
            name =  view.findViewById(R.id.chatName);
            phone =  view.findViewById(R.id.chatPhone);
            msgText = view.findViewById(R.id.chatMessage);
            msgTime = view.findViewById(R.id.chatTime);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisplayMessage dm=dispChat.get(getAdapterPosition());
                    textAndCall=new TextAndCall();
                    textAndCall.sendMessages(dm.getNumber(),v);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chats,parent,false);
        return new MyViewHolder(chatview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        DisplayMessage disp=dispChat.get(position);
        if((disp.getName()).equals(""))
            holder.name.setText(R.string.unknownContact);
        else
            holder.name.setText(disp.getName());

        holder.phone.setText(disp.getNumber());
        holder.msgText.setText(disp.getContent());
        holder.msgTime.setText(disp.getDate());
    }

    @Override
    public int getItemCount()
    {
        return dispChat.size();
    }
}
