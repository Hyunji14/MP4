package com.example.lp.lastpictures;

import android.os.Bundle;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

/**
 * Created by Hwang on 2016-11-28.
 */

public class NMapViewActivity extends NMapActivity {

    String clientID = "6tbLoSmdmKXBTMRK3uO3";
    public NMapViewActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        NMapView mMapView = new NMapView(this);

// 기존 API key 방식은 deprecated 함수이며, 2016년 말까지만 사용 가능합니다.
// mMapView.setApiKey(API_KEY);

// set Client ID for Open MapViewer Library
        mMapView.setClientId(clientID);


    }
}
