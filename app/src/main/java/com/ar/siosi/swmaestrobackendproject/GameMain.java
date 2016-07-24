package com.ar.siosi.swmaestrobackendproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// TODO: 2016-07-18  GameMain class 랑 Tutorial class 클래스 이름 바꾸기
public class GameMain extends AppCompatActivity implements FriendndListViewAdapter.ListBtnClickListener {


    private DatabaseReference trainerBase;

    private ImageButton friendButton; // 친구 목록 보기
    private ImageButton tripButton;
    private static final int REQUEST_FRIEND_LIST= 21; // 친구 리스트 요청하기
    private static final int REQUEST_AR_SCREEN = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        trainerBase = FirebaseDatabase.getInstance().getReference(); // 데이터 베이스 객체참조

        GoogleSignInActivity firstActivity = (GoogleSignInActivity) GoogleSignInActivity.firstActivity; // 첫번째 액티비티 종료
        firstActivity.finish();

        friendButton= (ImageButton) findViewById(R.id.socialBtn); // 친구 버튼
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //readMyFriendList();
                readAllUserList(); // TODO: 2016-07-18 여기에 친구추가 유아이 액티비티 만들면될듯.
                Intent friendIntent = new Intent(GameMain.this, FriendActivity.class);
                startActivityForResult(friendIntent,REQUEST_FRIEND_LIST); // 21번은 친구 목록 보기

            }
        });

        tripButton = (ImageButton) findViewById(R.id.goCatchBtn);
        tripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent arIntent = new Intent(GameMain.this, com.ar.siosi.swmaestrobackendproject.mixare.MixView.class);
                startActivity(arIntent);
            }
        });

        PoketMonTrainer.currentTrainer.lastPlayStartTime = new java.util.Date(); // 플레이어 마지막으로 플레이한 시작시간.

    }

    private void readAllUserList() {

     //  trainerBase.child("users").addValueEventListener(new ValueEventListener() {
     //      @Override
     //      public void onDataChange(DataSnapshot dataSnapshot) {
     //          for(DataSnapshot child : dataSnapshot.getChildren()) {
     //              Log.i("작동된다", "이예~");
     //          }
     //      }

     //      @Override
     //      public void onCancelled(DatabaseError databaseError) {

     //      }
     //  });
        //Toast.makeText(getApplicationContext(),trainerBase.getKey(),Toast.LENGTH_LONG).show();

    }


    private void prepareMessageData(){




       // trainerBase.addValueEventListener()
    }



    private void writeNewPost(PoketMonTrainer currentTrainer) {
        trainerBase.child("users").child(currentTrainer.uId).push().setValue(currentTrainer);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("종료")
                        .setMessage("종료 하시겠어요 ?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                PoketMonTrainer.currentTrainer.getPlayTime();
                                Log.i("종료타임", PoketMonTrainer.currentTrainer.lastPlayEndTime.toString());
                                writeNewPost(PoketMonTrainer.currentTrainer);
                                finish();
                            }
                        })
                        .setNegativeButton("아니요", null).show();
                return false;
            default:
                return false;
        }

    }


    @Override
    public void onListBtnClick(int position) {

    }
}
