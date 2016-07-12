package com.example.siosi.swmaestrobackendproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        ImageButton googleButton = (ImageButton) findViewById(R.id.imageButton);
        ImageButton facebookButton = (ImageButton) findViewById(R.id.imageButton2);

        googleButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GoogleSignInActivity.class));
            }
        });
        facebookButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 2016-07-13
                // 페이스북 리스너도 만들어야된다.
            }
        });

    }

}
