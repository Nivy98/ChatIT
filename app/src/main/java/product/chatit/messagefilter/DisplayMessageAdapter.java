package product.chatit.messagefilter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import product.chatit.Handlers.InfoHandlers;
import product.chatit.R;

public class DisplayMessageAdapter extends RecyclerView.Adapter<DisplayMessageAdapter.MyViewHolder> {

    private List<DisplayMessage> dispMsg;
    private InfoHandlers infoHandlers;
    public static final String DisplayMessageAdapter=DisplayMessageAdapter.class.getSimpleName();
    public DisplayMessageAdapter(List<DisplayMessage> dispMsg)
    {
        this.dispMsg=dispMsg;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, phone,date,content;

        public MyViewHolder(final View view) {
            super(view);
            name = view.findViewById(R.id.msgName);
            phone =  view.findViewById(R.id.msgPhone);
            date = view.findViewById(R.id.msgDate);
            content=view.findViewById(R.id.msgContent);
            infoHandlers=new InfoHandlers();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisplayMessage disp = dispMsg.get(getAdapterPosition());

                    String chatPh=disp.getNumber();
                    String chatName=infoHandlers.getContactName(disp.getNumber(),v.getContext());
                    String chatText=disp.getContent();
                    String chatTime=disp.getDate();

                    Intent returnIntent=new Intent();

                    //returns the values to Chat class (On Activity Result)
                    returnIntent.putExtra("returnPh",chatPh);
                    returnIntent.putExtra("returnName",chatName);
                    returnIntent.putExtra("returnDate",chatTime);
                    returnIntent.putExtra("returnmsg",chatText);
                    ((Activity)v.getContext()).setResult(Activity.RESULT_OK,returnIntent);
                    ((Activity) v.getContext()).finish();

                }
            });
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_selection,parent,false);
        return new MyViewHolder(chatview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DisplayMessage disp=dispMsg.get(position);
        holder.name.setText(disp.getName());
        holder.phone.setText(disp.getNumber());
        holder.date.setText(disp.getDate());
        holder.content.setText(disp.getContent());

    }

    @Override
    public int getItemCount() {
        return dispMsg.size();
    }
}
