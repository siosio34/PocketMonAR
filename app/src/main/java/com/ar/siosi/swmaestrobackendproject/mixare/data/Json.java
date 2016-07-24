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

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.ar.siosi.swmaestrobackendproject.mixare.Marker;
import com.ar.siosi.swmaestrobackendproject.mixare.MixView;
import com.ar.siosi.swmaestrobackendproject.mixare.PocketMonMarker;
import com.ar.siosi.swmaestrobackendproject.mixare.SnsMarker;
import com.ar.siosi.swmaestrobackendproject.mixare.SocialMarker;
import com.ar.siosi.swmaestrobackendproject.mixare.data.DataSource.DATAFORMAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// JSON 파일을 다루는 클래스
public class Json extends DataHandler {

    public static final int MAX_JSON_OBJECTS = 100;    // JSON 객체의 최대 수

    // 각종 데이터를 로드
    public List<Marker> load(JSONObject root, DATAFORMAT dataformat) {
        // 데이터를 읽는데 사용할 JSON 객체와 데이터행렬, 마커들
        JSONObject jo = null;
        JSONArray dataArray = null;
        List<Marker> markers = new ArrayList<Marker>();


        try {

            if(dataformat == DATAFORMAT.SNS)
                dataArray = root.getJSONArray("list");
                //네이버 주변정보
            else if (root.has("result") && root.getJSONObject("result").has("site")) // d연결 가능한 링크를 가졌을시
                dataArray = root.getJSONObject("result").getJSONArray("site");

            else { //포켓몬 정보 리스트입니다.
                dataArray = root.getJSONArray("pocketmons"); // 안되면 ;pocketmon 으로해보기
                //dataArray = new JSONArray(root.toString()); // 이거 안되면 ㅠㅠ
            }

          //  else  //네이버 길찾기
            //    dataArray = root.getJSONObject("result").getJSONArray("route").getJSONObject(0).getJSONArray("point");

            Log.i("dataArray값", dataArray.toString());
            // 데이터행렬에 데이터들이 있다면
            if (dataArray != null) {
                // 일단 로그 생성. 데이터 포맷을 기록한다
                Log.i(MixView.TAG, "processing " + dataformat + " JSON Data Array");
                // 최대 객체 수와 실제 데이터 길이를 비교해 최소치를 탑으로 지정
                int top = Math.min(MAX_JSON_OBJECTS, dataArray.length());

                // 각 데이터들에 대한 처리
                for (int i = 0; i < top; i++) {
                    // 처리할 JSON 객체를 할당
                    jo = dataArray.getJSONObject(i);
                    Log.i("JSON값", jo.toString());

                    Marker ma = null;
                    // 데이터 포맷에 따른 처리
                    switch (dataformat) {

                        case CAFE:
                            ma = processCAFEJSONObject(jo);
                            break;
                        case Convenience:
                            ma = processConvenienceJSONObject(jo);
                            break;
                        case Restaurant:
                            ma = processRestaurantJSONObject(jo);
                            break;
                        case SNS:
                            ma = processSNSJSONObject(jo);
                            break;
                        case POCKETMON:
                            ma = processPocketmonJSONObject(jo);
                            break;

                    }
                    // 마커 추가
                    if (ma != null)
                        markers.add(ma);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 모든 마커가 추가된 리스트를 리턴
        return markers;
    }

    public Marker processConvenienceJSONObject(JSONObject jo)  throws JSONException {
        Marker ma = null;

        // 형식에 맞는지 검사. 타이틀과 위도, 경도, 고도 태그를 찾는다
        if (jo.has("x") && jo.has("y") && jo.has("name")) {
            Log.v(MixView.TAG, "processing Mixare JSON object");    // 로그 출력

            String linkTemp = null;
            linkTemp = jo.getString("id");
            String link = "http://lab.khlug.org/manapie/javap/getRes.php?id=" + linkTemp.substring(1);

            // 할당된 값들로 마커 생성, // 일단은 경도, 위도, 이름만.
            // 맨뒤에값은 플래그 일단 Flag 0 는 카페정보
            ma = new SocialMarker(
                    jo.getString("name"),
                    jo.getDouble("y"),
                    jo.getDouble("x"),
                    0,
                    link,
                    DataSource.DATASOURCE.Convenience,"CONVENICE");
        }
        return ma;    // 마커 리턴
    }

    public Marker processRestaurantJSONObject(JSONObject jo) throws JSONException {
        Marker ma = null;

        // 형식에 맞는지 검사. 타이틀과 위도, 경도, 고도 태그를 찾는다
        if (jo.has("x") && jo.has("y") && jo.has("name")) {

            String linkTemp = null;
            linkTemp = jo.getString("id");
            String link = "http://lab.khlug.org/manapie/javap/getRes.php?id=" + linkTemp.substring(1);

            // 할당된 값들로 마커 생성, // 일단은 경도, 위도, 이름만.
            // 맨뒤에값은 플래그 일단 Flag 0 는 카페정보
            ma = new SocialMarker(
                    jo.getString("name"),
                    jo.getDouble("y"),
                    jo.getDouble("x"),
                    0,
                    link,
                    DataSource.DATASOURCE.Restaurant, "RESTRAUNT");
        }
        return ma;    // 마커 리턴
    }

    public Marker processCAFEJSONObject(JSONObject jo)  throws JSONException {
        Marker ma = null;

        // 형식에 맞는지 검사. 타이틀과 위도, 경도, 고도 태그를 찾는다
        if (jo.has("x") && jo.has("y") && jo.has("name")) {

            String linkTemp = null;
            linkTemp = jo.getString("id");
            String link = ("http://lab.khlug.org/manapie/javap/getRes.php?id=") + (linkTemp.substring(1));

            // 할당된 값들로 마커 생성, // 일단은 경도, 위도, 이름만.
            // 맨뒤에값은 플래그 일단 Flag 0 는 카페정보
            ma = new SocialMarker(
                    jo.getString("name"),
                    jo.getDouble("y"),
                    jo.getDouble("x"),
                    0,
                    link,
                    DataSource.DATASOURCE.CAFE, "CAFE");
        }
        return ma;    // 마커 리턴
    }

    public Marker processSNSJSONObject(JSONObject jo) throws JSONException {
        // TODO: 2016-06-03 SNS 내용 추가해야됨.
        Marker ma = null;
        String idStr = null;
        String snsLink = null;

        if(jo.has("longitude") && jo.has("latitude") && jo.has("time")) {

            idStr = jo.getString("id");
            snsLink = ("http://lab.khlug.org/manapie/javap/message.php?id=" + idStr);
            ma = new SnsMarker("[발자취] " + jo.getString("name"),Double.parseDouble(jo.getString("latitude")),
                    Double.parseDouble(jo.getString("longitude")),0,snsLink,DataSource.DATASOURCE.SNS,jo.getString("message"),jo.getString("time"));

        }

        return ma;
    }

    // TODO: 2016-07-22
    // 포켓몬 코드를 가져오는 마커이다.

    public Marker processPocketmonJSONObject(JSONObject jo) throws JSONException {

        Marker ma = null;

        ma = new PocketMonMarker(jo.getString("id"), jo.getDouble("latitude"), jo.getDouble("longitude"),0,null,
                DataSource.DATASOURCE.POCKETMON,jo.getInt("code"),jo.getInt("level"));

        Log.i("포켓몬ㅗㅗㅗㅗㅗ",ma.getTitle());

      //  Log.i("내 경도",Double.toString(ma.getLongitude()));
        //Log.i("내 위도",Double.toString(ma.getLatitude()));

        // 포켓몬 코드와 레발을 가져온다.
        return ma;

    }


    // html 엔트리의 해쉬맵
    private static HashMap<String, String> htmlEntities;

    static {
        htmlEntities = new HashMap<String, String>();
        htmlEntities.put("&lt;", "<");
        htmlEntities.put("&gt;", ">");
        htmlEntities.put("&amp;", "&");
        htmlEntities.put("&quot;", "\"");
        htmlEntities.put("&agrave;", "à");
        htmlEntities.put("&Agrave;", "À");
        htmlEntities.put("&acirc;", "â");
        htmlEntities.put("&auml;", "ä");
        htmlEntities.put("&Auml;", "Ä");
        htmlEntities.put("&Acirc;", "Â");
        htmlEntities.put("&aring;", "å");
        htmlEntities.put("&Aring;", "Å");
        htmlEntities.put("&aelig;", "æ");
        htmlEntities.put("&AElig;", "Æ");
        htmlEntities.put("&ccedil;", "ç");
        htmlEntities.put("&Ccedil;", "Ç");
        htmlEntities.put("&eacute;", "é");
        htmlEntities.put("&Eacute;", "É");
        htmlEntities.put("&egrave;", "è");
        htmlEntities.put("&Egrave;", "È");
        htmlEntities.put("&ecirc;", "ê");
        htmlEntities.put("&Ecirc;", "Ê");
        htmlEntities.put("&euml;", "ë");
        htmlEntities.put("&Euml;", "Ë");
        htmlEntities.put("&iuml;", "ï");
        htmlEntities.put("&Iuml;", "Ï");
        htmlEntities.put("&ocirc;", "ô");
        htmlEntities.put("&Ocirc;", "Ô");
        htmlEntities.put("&ouml;", "ö");
        htmlEntities.put("&Ouml;", "Ö");
        htmlEntities.put("&oslash;", "ø");
        htmlEntities.put("&Oslash;", "Ø");
        htmlEntities.put("&szlig;", "ß");
        htmlEntities.put("&ugrave;", "ù");
        htmlEntities.put("&Ugrave;", "Ù");
        htmlEntities.put("&ucirc;", "û");
        htmlEntities.put("&Ucirc;", "Û");
        htmlEntities.put("&uuml;", "ü");
        htmlEntities.put("&Uuml;", "Ü");
        htmlEntities.put("&nbsp;", " ");
        htmlEntities.put("&copy;", "\u00a9");
        htmlEntities.put("&reg;", "\u00ae");
        htmlEntities.put("&euro;", "\u20a0");
    }

    // HTML 아스키 값들을 다시 복원. 변환할 소스와 시작점을 인자로 받는다
    public String unescapeHTML(String source, int start) {
        int i, j;    // 임시 변수

        // &와 ;의 위치로 값들을 읽는다
        i = source.indexOf("&", start);
        if (i > -1) {
            j = source.indexOf(";", i);
            if (j > i) {
                // 검색된 위치에서 값을 읽어옴
                String entityToLookFor = source.substring(i, j + 1);
                String value = (String) htmlEntities.get(entityToLookFor);

                // 값이 있을 시 복원작업 시작. 재귀호출 이용
                if (value != null) {
                    source = new StringBuffer().append(source.substring(0, i))
                            .append(value).append(source.substring(j + 1))
                            .toString();
                    return unescapeHTML(source, i + 1); // recursive call
                }
            }
        }
        return source;    // 복원된 소스 리턴
    }
}

