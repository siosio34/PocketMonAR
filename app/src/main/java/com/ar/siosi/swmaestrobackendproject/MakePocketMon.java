package com.ar.siosi.swmaestrobackendproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by siosi on 2016-07-13.
 */

// 전략 패턴 사용

    public class MakePocketMon   {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public List<PoketMon> pocketlist = new ArrayList<PoketMon>();

        public void pocketMakerByUser() { // 유저에 의해서 기존 포켓몬 자동생성, 나중에 구현할 예정, 레벨 ,세기 다양하게~

        }

        public PoketMon pocketMakerByCode(int pocketMonCode) { // 코드를 이용한 포켓몬 한마리 생성

            PoketMon temp = null;

            switch(pocketMonCode) {

                case 1:
                    temp = new PoketMon(1, 100, "이상해씨",1,100,"풀",20,
                            10);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    temp = new PoketMon(4, 100, "파이리",1,100,"불",20,
                            10);
                    break;
                case 7:
                    temp = new PoketMon(7, 100, "꼬부기",1,100,"물",20,
                            10);
                    break;
            }

            return temp;
        }

        public void newPocketMakeinDB() { // FireBsee DB에 들어갈 새로운  포켓몬을 생성합니다.무조건 레벨 1
            myRef = FirebaseDatabase.getInstance().getReference("pocketMons");

            // 포켓몬 추가 방법, 키보드 입력 보다는 이게 편할듯 하다
            Random random = new Random();
            int code = random.nextInt(10);
            PoketMon poketMon = pocketMakerByCode(code);
            myRef.push().setValue(poketMon);
        }

}



