package com.example.lp.lastpictures;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;

import java.util.ArrayList;

/**
 * Created by Hwang on 2016-11-28.
 */

public class NMapViewActivity extends NMapActivity implements LocationListener {

    private static final int MY_PERMISSIONS_COARSE_LOCATION = 1;
    private static final int MY_PERMISSIONS_FINE_LOCATION = 2;
    String clientID = "6tbLoSmdmKXBTMRK3uO3";

    //private MapContainerView mMapContainerView;

    private NMapView mMapView;
    private MapContainerView mMapContainerView;
    NMapController mMapController ;

    //private NMapViewerResourceProvider mMapViewerResourceProvider;
    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        NMapView mMapView = new NMapView(this);//view 객체 생성

        mMapView.setClientId(clientID);

        setContentView(mMapView);

// initialize map view
        mMapView.setClickable(true);


// use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        mMapView.setBuiltInZoomControls(true, null);

        Toast.makeText(this, "여기까지", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_COARSE_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_FINE_LOCATION);
        }

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //권한설정



        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location != null) {
            lat = location.getLatitude();
            lon = location.getLongitude();
        } else {


            location = manager.getLastKnownLocation(manager.NETWORK_PROVIDER);
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                Toast.makeText(this, "정확한 위치정보를 위해 GPS를 켜주세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        showLocation(lat, lon);




        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        //showLocation(lat,lon);



    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode>0) {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
    }



    private void showLocation(double latitude, double logitude){
        NMapViewerResourceProvider mMapViewerResourceProvider = null;
        NGeoPoint mypoint = new NGeoPoint(logitude, latitude);

        int markerID = NMapPOIflagType.PIN;

        NMapPOIdata poIdata = new NMapPOIdata(1, mMapViewerResourceProvider);
        poIdata.beginPOIdata(1);
        poIdata.addPOIitem(mypoint, "", markerID, 0);

        mMapController.animateTo(mypoint);



    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        showLocation(latitude,longitude);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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




}
