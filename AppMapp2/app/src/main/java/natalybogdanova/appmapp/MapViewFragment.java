package natalybogdanova.appmapp;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MapViewFragment extends Fragment implements View.OnClickListener{

    final String LOG_TAG = "myLogs";
    TextView myTextView;
    GoogleMap mMap;
    MapView mMapView;
    private final LatLng dublin = new LatLng(53.34, -6.26);
    private final LatLng top_mid = new LatLng(53.40, -6.26);
    private final LatLng top_right = new LatLng(53.40, -6.34);
    private final LatLng top_left = new LatLng(53.40, -6.18);
    private final LatLng bot_mid = new LatLng(53.28, -6.26);
    private final LatLng bot_right = new LatLng(53.28, -6.34);
    private final LatLng bot_left = new LatLng(53.28, -6.18);
    private final LatLng mid_right = new LatLng(53.34, -6.34);
    private final LatLng mid_left = new LatLng(53.34, -6.18);
    private final LatLngBounds main_position = new LatLngBounds(bot_right, top_left);
    private int flag_area = 0;

    Listener mListener = (Listener) getActivity();

    public interface Listener {
        public void onSelected(String s);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mListener = (Listener) getActivity();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap Map) {
                mMap = Map;
                LatLngBounds area = new LatLngBounds(dublin, top_left);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(area, 0));
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        String s = latLng.toString();
                        mListener.onSelected(s);
                    }
                });
                final Polygon polygon_tl = mMap.addPolygon(new PolygonOptions()
                        .add(top_left, top_mid, dublin, mid_left)
                        .strokeColor(0x4000FF00));
            }
        });

        mMapView.setClickable(true);
        mMapView.setOnClickListener(this);
        mMapView.onResume();

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}