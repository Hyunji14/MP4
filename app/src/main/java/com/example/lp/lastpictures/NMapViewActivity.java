package com.example.lp.lastpictures;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;

/**
 * Created by Hwang on 2016-11-28.
 */

public class NMapViewActivity extends NMapActivity {

    String clientID = "6tbLoSmdmKXBTMRK3uO3";

    //private MapContainerView mMapContainerView;

    private NMapView mMapView;
    private NMapController mMapController;
    private MapContainerView mMapContainerView;

    //private NMapViewerResourceProvider mMapViewerResourceProvider;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        NMapView mMapView = new NMapView(this);//view 객체 생성

        mMapView.setClientId(clientID);

        mMapController = mMapView.getMapController();

        setContentView(mMapView);

// initialize map view
        mMapView.setClickable(true);

// use map controller to zoom in/out, pan and set map center, zoom level etc.
        NMapController mMapController = mMapView.getMapController();
 }


    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }

        }
    }

    public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
        if (errorInfo == null) { // success
            mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
        } else { // fail
            //Log.e(LOG_TAG, "onMapInitHandler: error=" + errorInfo.toString());
        }
    }


}
