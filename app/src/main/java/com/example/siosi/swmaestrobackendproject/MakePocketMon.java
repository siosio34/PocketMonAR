package com.example.siosi.swmaestrobackendproject;

import java.util.Random;

/**
 * Created by siosi on 2016-07-13.
 */

// 전략 패턴 사용

public class MakePocketMon   {

    Random random = new Random();
    public PoketMon makeRandPocketMon() { // 랜덤 포켓몬 뽑기
        PoketMon poketMon = new PoketMon();
        int poketId = random.nextInt(15); // 포켓몬 아이디

        switch (poketId) {
            // TODO: 2016-07-13
            // 여기에 포켓몬 만들거 적어두기
        }

        return poketMon;

    }


}



