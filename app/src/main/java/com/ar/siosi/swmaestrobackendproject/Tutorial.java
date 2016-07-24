package com.ar.siosi.swmaestrobackendproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Tutorial extends AppCompatActivity {

    EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        Button bt1 = (Button) findViewById(R.id.button);
        Button bt2 = (Button) findViewById(R.id.button2);
        etName = (EditText) findViewById(R.id.etName);

        bt1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validate())
                    Toast.makeText(getBaseContext(), "닉네임을 입력하세요.", Toast.LENGTH_LONG).show();
                    else {
                    String userNickName = etName.getText().toString();
                    Random random = new Random();
                    String pocketMonCode = Integer.toString(random.nextInt(3) * 3 + 1);
                    Intent intent = new Intent();
                    intent.putExtra("code", pocketMonCode);
                    intent.putExtra("nickName", userNickName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    setResult(0,intent);
                    finish();

                }
            }
        });
        bt2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();
                String pocketMonCode = Integer.toString(random.nextInt(3)*3+1);
                Intent intent = new Intent();
                intent.putExtra("code",pocketMonCode);
                intent.putExtra("nickName","basic");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                setResult(0,intent);
                finish();

                //// TODO: 2016-07-13
                // 페이스북 리스너도 만들어야된다.
            }
        });

    }

    private boolean validate(){
        if(etName.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
}
