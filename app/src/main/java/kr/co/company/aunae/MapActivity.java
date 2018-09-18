package kr.co.company.aunae;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng museum = new LatLng(36.759691, 127.308017);
        LatLng home = new LatLng(36.757372, 127.316870);
        LatLng park = new LatLng(36.7595552, 127.296461);
        LatLng church = new LatLng(36.757417, 127.316564);
        LatLng stone = new LatLng(36.763623, 127.296663);


        mMap.addMarker(new MarkerOptions().position(museum).title("유관순열사사우"));
        mMap.addMarker(new MarkerOptions().position(home).title("유관순열사생가"));
        mMap.addMarker(new MarkerOptions().position(park).title("독립운동기념공원"));
        mMap.addMarker(new MarkerOptions().position(church).title("매봉감리교회"));
        mMap.addMarker(new MarkerOptions().position(stone).title("독립운동기념비"));

        // Polygon polygon = mMap.addPolygon(new PolygonOptions().add(museum, home, park).strokeColor(Color.RED).fillColor(Color.argb(128, 0, 0, 255)));
        // Circle circle = mMap.addCircle((new CircleOptions().center(museum).radius(1000)).strokeColor(Color.RED).fillColor(Color.argb(64, 0, 0, 255)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(museum, 14f));
    }

    public void mapClick(View view) {
        if (view.getId() == R.id.mapBack) {
            finish();
        }
    }
}
