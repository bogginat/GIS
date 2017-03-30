package natalybogdanova.appmapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


public class AnswerMapFragment extends Fragment implements View.OnClickListener {
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

    Listener my_case = (Listener) getActivity();

    String srt = "";

    public interface Listener {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            my_case = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        srt = this.getArguments().getString("URL");
        String[] lat = srt.split("=");
        String first = lat[0];
        String second = lat[1];
        final LatLng answer = new LatLng(Double.parseDouble(first), Double.parseDouble(second));
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap Map) {
                mMap = Map;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(answer));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(answer, 14));
                final Polygon polygon_tl = mMap.addPolygon(new PolygonOptions()
                        .add(top_left, top_mid, dublin, mid_left)
                        .strokeColor(0x4000FF00));
                final Marker my_mark = mMap.addMarker(new MarkerOptions()
                        .position(answer)
                        .title("Правильный ответ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
        });

        mMapView.setClickable(true);
        mMapView.setOnClickListener(this);
        mMapView.onResume();
        return rootView;
    }

    public static AnswerMapFragment newInstance(String url){
        AnswerMapFragment fragment = new AnswerMapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        my_case = null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStop() {
        super.onStop();
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
