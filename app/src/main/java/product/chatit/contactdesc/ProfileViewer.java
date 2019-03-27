package product.chatit.contactdesc;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import product.chatit.R;
import product.chatit.Handlers.TextAndCall;


public class ProfileViewer extends AppCompatActivity {

   TextAndCall textAndCall;
   Bundle bundle;
    public static final String ProfileViewer = ProfileViewer.class.getSimpleName();
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewer);
        bundle = getIntent().getExtras();

        TextView nameSet = findViewById(R.id.nameText);
        TextView phoneSet = findViewById(R.id.phoneText);
        ImageView photoSet = findViewById(R.id.photoViewer);
        Button call = findViewById(R.id.profileCall);
        Button sms = findViewById(R.id.profileSms);
        phoneNumber=bundle.getString("phone");
        textAndCall = new TextAndCall();
        nameSet.setText(bundle.getString("name"));
        phoneSet.setText(bundle.getString("phone"));
        if (bundle.getString("photo") != null)
            photoSet.setImageURI(Uri.parse(bundle.getString("photo")));
        else
            photoSet.setImageResource(R.drawable.contactimg);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAndCall.makeCall(phoneNumber, v);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textAndCall.sendMessages(bundle.getString("phone"), v);
            }
        });
    }
}








