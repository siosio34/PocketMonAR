package com.ar.siosi.swmaestrobackendproject.mixare;

import android.graphics.Bitmap;
import android.location.Location;

import com.ar.siosi.swmaestrobackendproject.mixare.data.DataSource;
import com.ar.siosi.swmaestrobackendproject.mixare.gui.PaintScreen;

/**
 * Created by siosi on 2016-07-21.
 */
public class PocketMonMarker extends Marker {

    private static final int MAX_OBJECTS = 20;
    public int code =0;
    public int level = 0;

    public PocketMonMarker(String title, double latitude, double longitude, double altitude, String link, DataSource.DATASOURCE datasource, int code, int level) {
        super(title, latitude, longitude, altitude, link, datasource);

        this.code = code;
        this.level = level;

    }

    // 마커 갱신
    @Override
    public void update(Location curGPSFix) {

        // TODO: 2016-05-31  플래그에 따라서 갱신내용 달리해줘야한다.
        double altitude = curGPSFix.getAltitude()+Math.sin(0.35)*distance+Math.sin(0.4)*(distance/(MixView.dataView.getRadius()*1000f/distance));
        mGeoLoc.setAltitude(altitude - 0.2);
        super.update(curGPSFix);

    }

    // 페인트 스크린에 마커 출력
    @Override
    public void draw(PaintScreen dw) {

        // 텍스트 블록을 그린다
        drawTextBlock(dw,datasource);


        // 보여지는 상황이라면
        if (isVisible) {
            float maxHeight = Math.round(dw.getHeight() / 10f) + 1;	// 최대 높이 계산
            // 데이터 소스의 비트맵 파일을 읽어온다

            Bitmap bitmap = DataSource.getPocketBitmap(code);


            // 비트맵 파일이 읽혔다면 적절한 위치에 출력
            if(bitmap!=null) {
                dw.paintBitmap(bitmap, cMarker.x - maxHeight/1.5f, cMarker.y - maxHeight/1.5f);
            }
            else {	// 비트맵 파일을 갖지 않는 마커의 경우
                dw.setStrokeWidth(maxHeight / 10f);
                dw.setFill(false);
                dw.setColor(DataSource.getColor(datasource));
                dw.paintCircle(cMarker.x, cMarker.y, maxHeight / 1.5f);
            }
        }
    }

    // 최대 객체 수를 리턴
    @Override
    public int getMaxObjects() {
        return MAX_OBJECTS;
    }

}

