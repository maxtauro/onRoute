package e.maxtauro.onroute;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by maxtauro on 2018-03-12.
 */

public class MapTrackingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String email;
    DatabaseReference locations;
    Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tracking);

        //obtain the SupportMapFragment, get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                                                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Firebase Ref
        locations = FirebaseDatabase.getInstance().getReference("Locations");

        //Get intent
        if(getIntent() != null){
            email = getIntent().getStringExtra("email");
            lat = getIntent().getDoubleExtra("lat", 0);
            lng = getIntent().getDoubleExtra("lng", 0);
        }
        if(!TextUtils.isEmpty(email)){
            loadLocationForThisUser(email);
        }
    }

    private void loadLocationForThisUser(String email) {
        Query userLocation = locations.orderByChild("email").equalTo(email);

        userLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                    Tracking tracking = postSnapshot.getValue(Tracking.class);

                    //Create location for user
                    Location currentUser = new Location("");
                    currentUser.setLatitude(lat);
                    currentUser.setLongitude(lng);

                    //Add marker for friend
                    LatLng friendLatLng = new LatLng(Double.parseDouble(tracking.getLat()),
                                                        Double.parseDouble(tracking.getLng()));

                    //create location for friend
                    Location friendLocation = new Location("");
                    friendLocation.setLatitude(friendLatLng.latitude);
                    friendLocation.setLongitude(friendLatLng.longitude);

                    //clear old markers
                    mMap.clear();

                    //Add marker for friend
                    mMap.addMarker(new MarkerOptions()
                                    .position(friendLatLng)
                                    .title(tracking.getEmail())
                                    //.snippet()
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 12.0f));
                }

                //marker for user
                LatLng currUserLatLng = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(currUserLatLng).title(FirebaseAuth.getInstance().getCurrentUser().getEmail()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
