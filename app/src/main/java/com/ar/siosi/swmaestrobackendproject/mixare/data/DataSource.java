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

package com.ar.siosi.swmaestrobackendproject.mixare.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.ar.siosi.swmaestrobackendproject.R;


// 데이터 소스를 실질적으로 다루는 클래스
public class DataSource {


    // 데이터 소스와 데이터 포맷의 열거형 변수
    public enum DATASOURCE {
        CAFE, Convenience, Restaurant,SNS,POCKETMON
    };

    public enum DATAFORMAT {
        CAFE ,Convenience,Restaurant,SNS,POCKETMON
    };

    // 주의할것! 방대한 양의 데이터(MB단위 이상)을 산출할 때에는, 작은 반경이나 특정한 쿼리만을 사용해야한다
    /**
     * URL 부분 끝
     */

    // 아이콘들. 트위터와 버즈

    public static Bitmap cafeIcon;
    public static Bitmap busIcon;
    public static Bitmap restraurantIcon;
    public static Bitmap bankIcon;
    public static Bitmap convenienceIcon;
    public static Bitmap routeIcon;
    public static Bitmap messeage_icon;

    public static Bitmap sns_add;

    public static Bitmap selectPocketIcon[] = new Bitmap[120];


    //TODO : naver map URL
    private static final String NAVER_MAP_URL ="http://map.naver.com/findroute2/findWalkRoute.nhn?call=route2&output=json&coord_type=naver&search=0";


    // TODO: 2016-05-31  위에 나머지 비트맵 이미지 넣어놓기. 
    // 기본 생성자
    public DataSource() {

    }

    // 리소스로부터 각 아이콘 생성
    public static void createIcons(Resources res) {

        cafeIcon = BitmapFactory.decodeResource(res, R.drawable.icon_cafe);
        messeage_icon = BitmapFactory.decodeResource(res,R.drawable.sns_2);
        busIcon = BitmapFactory.decodeResource(res,R.drawable.icon_metro);
        restraurantIcon = BitmapFactory.decodeResource(res,R.drawable.icon_store);
        convenienceIcon = BitmapFactory.decodeResource(res,R.drawable.icon_conveni);
        sns_add = BitmapFactory.decodeResource(res,R.drawable.sns_add);
        String resName = "@pocket_";
        String temp="";
       // selectPocketIcon = new Bitmap[130];
        for(int i=1; i<30; i++) {
            temp = resName + i;
            int resId = res.getIdentifier(temp,"drawable","com.ar.siosi.swmaestrobackendproject");
            selectPocketIcon[i] = BitmapFactory.decodeResource(res,resId);
        }
    }

    public static Bitmap getPocketBitmap(int code) {
        return selectPocketIcon[code];
    }



    // 아이콘 비트맵의 게터
    public static Bitmap getBitmap(String ds) {
        Bitmap bitmap = null;
        switch (ds) {

            case "CAFE":
                bitmap = cafeIcon;
                break;
            case "BUSSTOP":
                bitmap = busIcon;
                break;
            case "CONVENICE":
                bitmap = convenienceIcon;
                break;
            case "RESTRAUNT":
                bitmap = restraurantIcon;
                break;
            case "SNS":
                bitmap = messeage_icon;
                break;
            case "SNSADD":
                bitmap = sns_add;
                break;
            case "EVENT": // 이벤트
                break;


            // TODO: 2016-05-31  여기에 케이스 더 다양하게해서 비트맵 파일을 받아놔야된다.

        }
        return bitmap;
    }

    // 데이터 소스로부터 데이터 포맷을 추출
    public static DATAFORMAT dataFormatFromDataSource(DATASOURCE ds) {
        DATAFORMAT ret;
        // 소스 형식에 따라 포맷을 할당한다
        switch (ds) {


            case CAFE:
                ret = DATAFORMAT.CAFE;
                break;

            case Convenience:
                ret = DATAFORMAT.Convenience;
                break;

            case Restaurant:
                ret = DATAFORMAT.Restaurant;
                break;

            case SNS:
                ret = DATAFORMAT.SNS;
                break;
            case POCKETMON:
                ret = DATAFORMAT.POCKETMON;
                break;

            default:
                ret = DATAFORMAT.SNS;
                break;


        }
        return ret;    // 포맷 리턴
    }


    // 각 정보들로 완성된 URL 리퀘스트를 생성
    public static String createRequestURL(DATASOURCE source, double lat, double lon, double alt, float radius, String locale) {
        String ret = "";    // 결과 스트링

        // 파일로부터 읽는 것이 아니라면
        if (!ret.startsWith("file://")) {

            // 각 소스에 따른 URL 리퀘스트를 완성한다
            switch (source) {

                // 네이버 웹페이지에서 가져오는 정보
                case CAFE:
                    ret = "http://map.naver.com/search2/interestSpot.nhn?type=CAFE&boundary=" + Double.toString(lon - 0.02) + "%3B" +
                            Double.toString(lat - 0.01) + "%3B" + Double.toString(lon + 0.02) +
                            "%3B" + Double.toString(lat + 0.01) + "&pageSize=100";
                    break;

                case Convenience:
                    ret = "http://map.naver.com/search2/interestSpot.nhn?type=STORE&boundary=" + Double.toString(lon - 0.02) + "%3B" +
                            Double.toString(lat - 0.01) + "%3B" + Double.toString(lon + 0.02) +
                            "%3B" + Double.toString(lat + 0.01) + "&pageSize=100";
                    break;

                case Restaurant:
                    ret =  "http://map.naver.com/search2/interestSpot.nhn?type=DINING_KOREAN&boundary=" + Double.toString(lon - 0.02) + "%3B" +
                            Double.toString(lat - 0.01) + "%3B" + Double.toString(lon + 0.02) +
                            "%3B" + Double.toString(lat + 0.01) + "&pageSize=100";
                    break;

                // php 서버
                case SNS:
                    ret = "http://lab.khlug.org/manapie/javap/getMessage.php?longitude=" + Double.toString(lon) + "&latitude=" + Double.toString(lat);
                    break;

                // ruby on rails 서버

                case POCKETMON:
                    // 이건 전체 포멧몬의 정보를 가져옴:
                    ret = "http://163.180.117.118:8080/pocketmons.json";
                    // 내주위에 있는 포켓몬 정보를 가져옴
                  //  ret = "http://163.180.117.118:3000/findpocket?lat=" + Double.toString(lat) + "&log="+ Double.toString(lon);
                    break;


            }

        }

        return ret;
    }

    // 각 소스에 따른 색을 리턴
    public static int getColor(DATASOURCE datasource) {
        int ret;
        switch (datasource) {

            default:
                ret = Color.GREEN;
                break;
        }
        return ret;
    }

}
