package data.riyasha.women.Map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import data.riyasha.women.R;
import data.riyasha.women.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapLoc extends FragmentActivity implements OnMapReadyCallback, ValueEventListener {

    private GoogleMap mMap;
    float hue = 337;
    private FirebaseAuth mAuth;
    DatabaseReference trackingUserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_loc);
        mAuth = FirebaseAuth.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        registerEventRealTime();
    }

    private void registerEventRealTime() {

        trackingUserLocation = FirebaseDatabase.getInstance()
                .getReference(User.PUBLIC_LOCATION)
                .child(mAuth.getCurrentUser().getUid());

        trackingUserLocation.addValueEventListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        trackingUserLocation.addValueEventListener(this);
    }

    @Override
    protected void onStop() {

        trackingUserLocation.removeEventListener(this);
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // enable zoom ui

        mMap.getUiSettings().setZoomControlsEnabled(true);

        //set skin

        boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.my_uber_style));

    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


        if(dataSnapshot.getValue()!=null){

            mMap.clear();

            MyLocation location = dataSnapshot.getValue(MyLocation.class);

            //adding marker


            LatLng userMarker = new LatLng(location.getLatitude(),location.getLongitude());


            mMap.addMarker(new MarkerOptions().position(userMarker)
                    .title(User.trackingUser.getEmail())
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(hue))
                    .snippet(User.getDateFormatted(User.convertTimeStampToDate(location.getTime())))).showInfoWindow();



            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker,16F));





        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
