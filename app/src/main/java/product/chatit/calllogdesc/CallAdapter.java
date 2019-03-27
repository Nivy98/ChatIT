package product.chatit.calllogdesc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import product.chatit.Handlers.InfoHandlers;
import product.chatit.R;
import product.chatit.Handlers.TextAndCall;


public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder>
{
    private ArrayList<Call> listCalls;
    TextAndCall textAndCall;
    InfoHandlers infoHandlers;
    private static final String CallAdapter = CallAdapter.class.getSimpleName();

    public CallAdapter(ArrayList<Call> listCalls) {
        this.listCalls=listCalls;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name,phone,time;
        public ImageView type;

        public ViewHolder(final View view)
        {
            super(view);
            name=view.findViewById(R.id.call_name);
            phone=view.findViewById(R.id.call_phone);
            type=view.findViewById(R.id.call_type);
            time=view.findViewById(R.id.call_time);
            infoHandlers=new InfoHandlers();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call call=listCalls.get(getAdapterPosition());
                    textAndCall=new TextAndCall();
                    textAndCall.makeCall(call.getPhone(),v);

                }
            });
        }
    }

    @Override
    public CallAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_calls,parent,false);
        return new CallAdapter.ViewHolder(itemview);
    }

    @Override
    public int getItemCount() {
        return listCalls.size();
    }


    @Override
    public void onBindViewHolder(CallAdapter.ViewHolder holder, int position) {
        Call disp=listCalls.get(position);
        holder.name.setText(disp.getName());
        holder.phone.setText(disp.getPhone());
        holder.time.setText(infoHandlers.getHandledDate(Long.parseLong(disp.getDate())));
        if(disp.getCall_type()==0)
            holder.type.setImageResource(R.drawable.outgoing);
        else if(disp.getCall_type()==1)
            holder.type.setImageResource(R.drawable.incoming);
        else
            holder.type.setImageResource(R.drawable.missed);
    }


}
