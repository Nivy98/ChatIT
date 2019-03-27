package product.chatit.contactdesc;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import product.chatit.DatabaseManager.DatabaseContainer;
import product.chatit.R;

public class DisplayContactsAdapter extends RecyclerView.Adapter<DisplayContactsAdapter.ViewHolder>
{
    private ArrayList<DisplayContacts> listContacts;
    private static final String DisplayContactsAdapter = DisplayContactsAdapter.class.getSimpleName();

    public DisplayContactsAdapter(ArrayList<DisplayContacts> displayContacts) {
        this.listContacts=displayContacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView name,phone;
        public ImageView photo;

        public ViewHolder(final View view)
        {
            super(view);
            name=view.findViewById(R.id.name);
            phone=view.findViewById(R.id.phone);
            photo=view.findViewById(R.id.contactImage);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                DisplayContacts disp=listContacts.get(getAdapterPosition());

                //Passing the clicked position's Name,Phone Number and Image path
                Intent intent = new Intent(v.getContext(), ProfileViewer.class);
                intent.putExtra("name",disp.getName());
                intent.putExtra("phone",disp.getPhoneNum());
                intent.putExtra("photo",disp.getPhotoURI());

                v.getContext().startActivity(intent);
                }
            });

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contacts,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DisplayContacts disp=listContacts.get(position);

        holder.name.setText(disp.getName());
        holder.phone.setText(disp.getPhoneNum());
        //set Default image from drawable folder if null or select the desired contact image and assign via Image Uri
        if(disp.getPhotoURI()!=null)

            holder.photo.setImageURI(Uri.parse(disp.getPhotoURI()));
        else
            holder.photo.setImageResource(R.drawable.contactimg);
    }

}
