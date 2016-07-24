/*
 * Copyright (C) 2010- Peer internet solutions
 * 
 * This file is part of mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package com.ar.siosi.swmaestrobackendproject.mixare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ar.siosi.swmaestrobackendproject.mixare.reality.PhysicalPlace;
import com.ar.siosi.swmaestrobackendproject.mixare.render.Matrix;
import com.ar.siosi.swmaestrobackendproject.mixare.render.MixVector;

// 현재의 상태에 관한 클래스
public class MixState {

    // 각 상태에 대한 상수값 설정
    public static int NOT_STARTED = 0;
    public static int PROCESSING = 1;
    public static int READY = 2;
    public static int DONE = 3;

    int nextLStatus = MixState.NOT_STARTED;    // 다음 상태
    String downloadId;    // 다운로드할 ID

    private float curBearing;    // 현재의 방위각
    private float curPitch;        // 현재의 장치각(?)

    private boolean detailsView;    // 디테일 뷰가 표시 중인지 여부

    // 이벤트 처리
    public boolean handleEvent(MixContext ctx, String onPress, String title, PhysicalPlace log) {

        DialogSelectOption(ctx, title, log, onPress);

        return true;
    }

    public void DialogSelectOption(final MixContext ctx, final String markerTitle, final PhysicalPlace log, final String onPress) {
        final String items[] = {"상세 정보 보기", "전투하기"};
        AlertDialog.Builder ab = new AlertDialog.Builder(ctx);
        ab.setTitle(markerTitle);
        ab.setItems(items,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 프로그램을 종료한다
                        Toast.makeText(ctx,
                                items[id] + " 선택했습니다.",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        if (id == 0) {
                            try {
                                String webpage = MixUtils.parseAction(onPress);
                                //this.detailsView = true;
                                ctx.loadMixViewWebPage(webpage);
                            } catch (Exception e) {
                            }

                        } else if (id == 1) {
                            // TODO: 2016-07-21  전투 구현
                        }
                    }
                }
        );
        // 다이얼로그 생성
        AlertDialog alertDialog = ab.create();
        // 다이얼로그 보여주기
        alertDialog.show();
    }


    // 현재의 방위각을 리턴
    public float getCurBearing() {
        return curBearing;
    }

    // 현재의 장치각을 리턴
    public float getCurPitch() {
        return curPitch;
    }

    // 디테일 뷰의 표시 여부를 리턴
    public boolean isDetailsView() {
        return detailsView;
    }

    // 디테일 뷰의 표시 여부를 설정
    public void setDetailsView(boolean detailsView) {
        this.detailsView = detailsView;
    }

    // 장치각과 방위각을 계산
    public void calcPitchBearing(Matrix rotationM) {
        MixVector looking = new MixVector();
        rotationM.transpose();
        looking.set(1, 0, 0);
        looking.prod(rotationM);
        this.curBearing = (int) (MixUtils.getAngle(0, 0, looking.x, looking.z) + 360) % 360;

        rotationM.transpose();
        looking.set(0, 1, 0);
        looking.prod(rotationM);
        this.curPitch = -MixUtils.getAngle(0, 0, looking.y, looking.z);
    }
}
