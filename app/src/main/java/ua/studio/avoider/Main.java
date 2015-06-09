package ua.studio.avoider;

import android.app.Activity;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Main extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    Button alramButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alramButton = (Button) findViewById(R.id.alarmButton);
        if (isConnectionAvailable()) {
            SupportMapFragment fragmentById = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
            fragmentById.getMapAsync(this);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        MapsInitializer.initialize(this);

        // Check if we were successful in obtaining the map.
        if (googleMap != null) {
            initLocationChangeListener(googleMap);
        }
    }

    private void initLocationChangeListener(final GoogleMap googleMap) {
        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(loc).title("Marker"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        };

        googleMap.setOnMyLocationChangeListener(myLocationChangeListener);
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
