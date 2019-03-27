package product.chatit.StartScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import product.chatit.R;
import product.chatit.viewfiles.ChatAppView;


public class Splash extends AppCompatActivity {
    private Intent splash_intent;
    private SharedPreferences sharedPreferences;
    public static final String preferences = "SharedPreference";
    public static String storedPreference;
    SharedPreferences.Editor edit;
    private static final String Splash = Splash.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences=getSharedPreferences(preferences, Context.MODE_PRIVATE);
        edit=sharedPreferences.edit();

        Handler handler=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run() {
                try{
                    if(sharedPreferences.getString(storedPreference, "First time").equals("First time"))
                    {
                        //If First Time
                        splash_intent = new Intent(getApplicationContext(), HomeScreen.class);
                        startActivity(splash_intent);
                        edit.putString(storedPreference,"Not First");
                        edit.apply();
                    }
                    else
                    {
                        splash_intent = new Intent(getApplicationContext(), ChatAppView.class);
                        startActivity(splash_intent);
                    }

                    finish();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r,3000 );

//        Thread splash=new Thread(){
//            public void run(){
//
//                try{
//                    sleep(3*1000);
//
//                    if(sharedPreferences.getString(storedPreference,"First time").toString().equals("First time")) {
//                        //If First Time
//                        splash_intent = new Intent(getApplicationContext(), HomeScreen.class);
//                        startActivity(splash_intent);
//                        edit.putString(storedPreference,"Not First");
//                        edit.commit();
//                    }
//                    else
//                    {
//                        splash_intent = new Intent(getApplicationContext(), ChatAppView.class);
//                        startActivity(splash_intent);
//                    }
//
//                    finish();
//
//                }catch (Exception e){
//
//                }
//            }
//        };
//        splash.start();

    }

}
