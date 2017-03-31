package kr.dsm.wherehere;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by BeINone on 2017-03-31.
 */

public class MapFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;

    private GoogleMap mGoogleMap;
    private MapView mMapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                initializeMap();
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeMap();
                } else {
                    Toast.makeText(getContext(), "지도를 사용하기 위해서는 위치 권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mGoogleMap != null) mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleMap != null) mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleMap != null) mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleMap != null) mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mGoogleMap != null) mMapView.onLowMemory();
    }

    private void initializeMap() {
        if (mGoogleMap != null) {
            // check permission
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // show My Location button
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }


        }
    }

}
