package product.chatit.StartScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import product.chatit.Handlers.TextAndCall;
import product.chatit.R;
import product.chatit.viewfiles.ChatAppView;

public class HomeScreen extends AppCompatActivity
{
TextAndCall textAndCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        textAndCall=new TextAndCall();
        textAndCall.permissionChecker(this);

        Button next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this,ChatAppView.class);
                startActivity(intent);

            }
        });

    }
}
