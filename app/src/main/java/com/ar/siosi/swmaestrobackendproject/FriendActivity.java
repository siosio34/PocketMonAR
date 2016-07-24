package com.ar.siosi.swmaestrobackendproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity implements FriendndListViewAdapter.ListBtnClickListener {

    public List<String> friendList = new ArrayList<>(); // 친구 ID 리스트
    public List<String> allUserList = new ArrayList<>(); // 전부다 보단 전체 유저중 일부 리스트 해야될듯 ?

    private ArrayList<FriendListView> myFrienditems = new ArrayList<FriendListView>();
    private ArrayList<FriendListView> allFrienditems = new ArrayList<FriendListView>();
    public ArrayList<FriendListView> items = new ArrayList<FriendListView>();

    private ListView listview;
    private FriendndListViewAdapter adapter;

    final private static int MY_FRIEND_LIST = 1;
    final private static int ALL_FRIEND_LIST = 2;

    int flag = ALL_FRIEND_LIST;

    private DatabaseReference friendBase;

    // TODO: 2016-07-19 친구 리스트 유저 이미지 받아오기, 친구리스트일때 버튼 없애기, 친구 만드는데 서로허락, 선물기능구현하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freind);

        //  Log.i("에러를 찾자", "1");

        Button bt1 = (Button) findViewById(R.id.myFriendBtn); // 내 친구 리스트
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 2016-07-13
                flag = MY_FRIEND_LIST;
                getFriendUserDataByFlag(flag);
            }
        });


        Button bt2 = (Button) findViewById(R.id.AllFreiendBtn); // 전체 유저 목록
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = ALL_FRIEND_LIST;
                getFriendUserDataByFlag(flag);
            }
        });


        listview = (ListView) findViewById(R.id.listview1);
        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // TODO : item click
            }
        });

        readMyFriendList(); // 내 친구목록 유저 id 들고오기

        adapter = new FriendndListViewAdapter(this, R.layout.listview_btn_item, allFrienditems, this) ;
        listview.setAdapter(adapter);

        friendBase = FirebaseDatabase.getInstance().getReference();
        getFriendUserDataByFlag(flag);

    }


    private void getFriendUserDataByFlag(final int flag) {

        friendBase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allFrienditems.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (!child.getKey().equals(PoketMonTrainer.currentTrainer.uId)) {

                        if(flag == MY_FRIEND_LIST) {
                            if (friendList.contains(child.getKey())) { // 키를 포함시

                                FriendListView item;
                                item = new FriendListView();
                                item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.go));
                                item.setText(child.getKey());
                                allFrienditems.add(item);

                            } else {


                            }
                        }
                        else if(flag == ALL_FRIEND_LIST)
                        {
                            if (!friendList.contains(child.getKey())) { // 키를 포함시
                                FriendListView item;
                                item = new FriendListView();
                                item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.go));
                                item.setText(child.getKey());
                                allFrienditems.add(item);
                            }
                            else {

                            }
                        }

                    }

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void myFriendUserDataByID(ArrayList<FriendListView> list) { // 내 친구들 정보 열람.


    }

    private void allUserDataByID(ArrayList<FriendListView> list) { // 모든 유저 데이터 들고오기

    }

    private void readMyFriendList() {
        friendList = PoketMonTrainer.currentTrainer.haveFriendList;
    }

    private void readAllUserList() {

        FriendListView item;

        friendBase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 친구 추가 확인해야됨

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    allUserList.add(child.getKey());
                   // Log.i("애는 끝나도 뒤에거하는데 왜다른건", "안하냐");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override // 메인에서 버튼 선택시
    public void onListBtnClick(int position) {

    }
}
