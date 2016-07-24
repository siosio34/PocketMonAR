package com.ar.siosi.swmaestrobackendproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.client.Firebase;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        ImageButton googleButton = (ImageButton) findViewById(R.id.imageButton);


        googleButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

       // FirebaseMessaging.getInstance().subscribeToTopic("test");
        String test = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(this,test,Toast.LENGTH_SHORT).show();


    }



}
