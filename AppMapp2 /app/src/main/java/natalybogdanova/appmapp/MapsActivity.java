package natalybogdanova.appmapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polygon;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    final String LOG_TAG = "myLogs";
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    LatLng all[];
    LatLng dublin;
    LatLng top_mid;
    LatLng top_right;
    LatLng top_left;
    LatLng bot_mid;
    LatLng bot_right;
    LatLng bot_left;
    LatLng mid_right;
    LatLng mid_left;
    LatLngBounds main_position;
    private int flag_area = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        all = ((AppController)getApplication()).getLatLng();
        dublin = all[0];
        top_mid = all[1];
        top_right = all[2];
        top_left = all[3];
        bot_mid = all[4];
        bot_right = all[5];
        bot_left = all[6];
        mid_right = all[7];
        mid_left = all[8];
        main_position = new LatLngBounds(bot_right, top_left);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        all = ((AppController)getApplication()).getLatLng();
        dublin = all[0];
        top_mid = all[1];
        top_right = all[2];
        top_left = all[3];
        bot_mid = all[4];
        bot_right = all[5];
        bot_left = all[6];
        mid_right = all[7];
        mid_left = all[8];
        main_position = new LatLngBounds(bot_right, top_left);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(main_position, 30));
                mMap.addMarker(new MarkerOptions().position(dublin).title("Центр города"));
            }
        });

        Button button = (Button) findViewById(R.id.button);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button_sign_out);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        final Polygon polygon_tl = mMap.addPolygon(new PolygonOptions()
                .add(top_left, top_mid, dublin, mid_left)
                .fillColor(0x4000FF00)
                .strokeColor(0x4000FF00));
        polygon_tl.setClickable(true);

        final Polygon polygon_tr = mMap.addPolygon(new PolygonOptions()
                .add(top_mid, top_right, mid_right, dublin)
                .fillColor(0x40FF0000)
                .strokeColor(0x40FF0000));
        polygon_tr.setClickable(true);

        final Polygon polygon_bl = mMap.addPolygon(new PolygonOptions()
                .add(mid_left, dublin, bot_mid, bot_left)
                .fillColor(0x400000FF)
                .strokeColor(0x400000FF));
        polygon_bl.setClickable(true);

        final Polygon polygon_br = mMap.addPolygon(new PolygonOptions()
                .add(dublin, mid_right, bot_right, bot_mid)
                .fillColor(0x400F0F0F)
                .strokeColor(0x400F0F0F));
        polygon_br.setClickable(true);


        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            public void onPolygonClick(Polygon polygon) {
                AppController app = ((AppController) getApplicationContext());
                LatLngBounds area = main_position;
                if (polygon.hashCode() == polygon_br.hashCode()) {
                    area = new LatLngBounds(bot_right, dublin);
                    app.setPoly(dublin, mid_right, bot_right, bot_mid);
                    app.setArea(bot_right, dublin);
                    flag_area = 1;
                } else if (polygon.hashCode() == polygon_bl.hashCode()) {
                    area = new LatLngBounds(bot_mid, mid_left);
                    app.setPoly(mid_left, dublin, bot_mid, bot_left);
                    app.setArea(bot_mid, mid_left);
                    flag_area = 2;
                } else if (polygon.hashCode() == polygon_tr.hashCode()) {
                    area = new LatLngBounds(mid_right, top_mid);
                    app.setPoly(top_mid, top_right, mid_right, dublin);
                    app.setArea(mid_right, top_mid);
                    flag_area = 3;
                } else if (polygon.hashCode() == polygon_tl.hashCode()) {
                    area = new LatLngBounds(dublin, top_left);
                    app.setPoly(top_left, top_mid, dublin, mid_left);
                    app.setArea(dublin, top_left);
                    flag_area = 4;
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(area, 0));
                button1.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "but_CLICKED");
        switch (v.getId()) {
            case R.id.button1:
                Intent intent = new Intent(this, MapsActivity.class);
                if (flag_area >= 0) {
                    intent = new Intent(this, NE.class);
                }
                startActivity(intent);
                break;
            case R.id.button:
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(main_position, 0));
                Button button = (Button) findViewById(R.id.button1);
                button.setVisibility(View.INVISIBLE);
                break;
            case R.id.button_sign_out:
                Intent sign = new Intent(this, Signin.class);
                startActivity(sign);
                break;
            default:
                break;
        }
    }
}
