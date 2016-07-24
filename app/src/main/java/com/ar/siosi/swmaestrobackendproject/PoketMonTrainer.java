package com.ar.siosi.swmaestrobackendproject;

import android.util.Log;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by siosi on 2016-07-13.
 */
public class PoketMonTrainer {


    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private DatabaseReference mDatabase;

    public static PoketMonTrainer currentTrainer = new PoketMonTrainer();

    public String uId;
    public String nickName; // 새로 짓는 유저의 닉네임
    public String userEmail; // 유저닉네임
    public String userName; // 유저이름
    // private String userImageUrl; //  유저 프로필 이미지
    public Date firstStartTime; // 최초 서버접속시간
    public Date lastPlayStartTime; // 마지막 플레이 서버 시작한 시간
    public Date lastPlayEndTime;  // 마지막 플레이 서버 종료 접속 시간 Date 잼
    public long totalGamePlayTime; // 총 게임 플레이 시간.
    public long lastGamePlayTime; // 마지막으로 플레이를 시작한 시간
    public List<PoketMon> havePoketMon = new ArrayList<PoketMon>();// 가져오는 포켓몬 정보
    public List<String> haveFriendList = new ArrayList<>(); // 친구 ID 리스트
    public int totalPower; // 전투력 for 랭킹 측정


    MakePocketMon makepocketMon = new MakePocketMon();

    //public void onCreate()

    public PoketMonTrainer() {

    }

    // 정보를 가져오고 그 유저가 없을때... !
    // 사용자가입 -> makeTrainerNickname() 함수 동작.
    // 유저들한테서 로그인 받고 그 로그인 정보로 내 유저 디비에서 찾아야됨

    public PoketMonTrainer(String uid, String userName, String nickName, String userEmail, Date firstStartTime, Date lastPlayStartTime,Date lastPlayEndTime, long totalGamePlayTime,
                           long lastGamePlayTime, int totalPower, List<PoketMon> havePoketMon, List<String> haveFriendList) {

        this.uId = uid;
        this.userName = userName;
        this.userEmail = userEmail;
        this.nickName = nickName;
        this.firstStartTime = firstStartTime;
        this.lastPlayStartTime = lastPlayStartTime;
        this.lastPlayEndTime = lastPlayEndTime;
        this.totalGamePlayTime = totalGamePlayTime;
        this.lastGamePlayTime = lastGamePlayTime;
        this.havePoketMon = havePoketMon;
        this.haveFriendList = haveFriendList;
        this.totalPower = totalPower;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uId", uId);
        result.put("userName", userName);
        result.put("nickName", nickName);
        result.put("userEmail", userEmail);
        result.put("firstStartTime", firstStartTime);
        result.put("lastGamePlayTime", lastGamePlayTime);
        result.put("lastPlayTime", lastPlayEndTime);
        // result.put("havePoketMon",havePoketMon);
        result.put("totalPower", totalPower);

        return result;
    }


    public PoketMonTrainer makeTrainerData(FirebaseUser user, String userNickName, int pocketCode) { // 닉네임 작성, 오박사 액티비티

        PoketMonTrainer poketMonTrainer = null;

        uId = user.getUid();
       // boolCheckUser(uId);

        if (userNickName != null) {

            if (userNickName.equals("basic"))  // 유저가 기본 유저이름으로 선택한경우
                userNickName = user.getDisplayName(); // 유저 기본이름
            else
                userNickName = userNickName;

                myRef = FirebaseDatabase.getInstance().getReference("users");

                userName = user.getDisplayName();
                userEmail = user.getEmail();
                nickName = userNickName;
                firstStartTime = new java.util.Date();
                lastPlayStartTime = new java.util.Date();
                lastPlayEndTime = new java.util.Date();
                totalGamePlayTime = 0;
                lastGamePlayTime = 0;
                totalPower = 0;

                // TODO: 2016-07-13 오박사 포켓몬 선택
                havePoketMon.add(new MakePocketMon().pocketMakerByCode(pocketCode));
                haveFriendList.add("sample");

                poketMonTrainer = new PoketMonTrainer(uId, userName, nickName, userEmail, firstStartTime, lastPlayStartTime, lastPlayEndTime, totalGamePlayTime,
                        lastGamePlayTime, totalPower, havePoketMon , haveFriendList);

                //PoketMonTrainer.currentTrainer = poketMonTrainer;
                myRef.child(uId.toString()).push().setValue(poketMonTrainer);

                // push 를쓰게되면 타임스탬프를 기준으로 새로운 키가 생성됨
            }
            //makepocketMon.newSpeicesPocketMake();}
            else { // 기존에 디비에 존재하는경우.
                // TODO: 2016-07-15  다른데서 처리하고 있음.
            }

        return poketMonTrainer;

    }

    // 만약 유저가 있으면...?
    // 유저 디비에서 내 정보를 가져옴

    public void getTrainerData() { //

    }

    public void getPlayTime( ) {

        lastPlayEndTime = new java.util.Date(); // 게임 종료하는 데이트.
        long currentPlayTime = currentTrainer.lastPlayEndTime.getTime() - lastPlayStartTime.getTime();
        lastGamePlayTime = currentPlayTime / (1000); // 단위 분
        Log.i("플레이타임", currentPlayTime +  " / " + lastGamePlayTime);
        totalGamePlayTime += lastGamePlayTime; // 단위 분
    }


}
