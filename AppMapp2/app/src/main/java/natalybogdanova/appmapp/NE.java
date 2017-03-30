package natalybogdanova.appmapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static natalybogdanova.appmapp.AppConfig.GEOMETRY;
import static natalybogdanova.appmapp.AppConfig.GOOGLE_BROWSER_API_KEY;
import static natalybogdanova.appmapp.AppConfig.ICON;
import static natalybogdanova.appmapp.AppConfig.LATITUDE;
import static natalybogdanova.appmapp.AppConfig.LOCATION;
import static natalybogdanova.appmapp.AppConfig.LONGITUDE;
import static natalybogdanova.appmapp.AppConfig.NAME;
import static natalybogdanova.appmapp.AppConfig.OK;
import static natalybogdanova.appmapp.AppConfig.PHOTO;
import static natalybogdanova.appmapp.AppConfig.PHOTO_REF;
import static natalybogdanova.appmapp.AppConfig.PLACE_ID;
import static natalybogdanova.appmapp.AppConfig.PROXIMITY_RADIUS;
import static natalybogdanova.appmapp.AppConfig.REFERENCE;
import static natalybogdanova.appmapp.AppConfig.STATUS;
import static natalybogdanova.appmapp.AppConfig.SUPERMARKET_ID;
import static natalybogdanova.appmapp.AppConfig.TAG;
import static natalybogdanova.appmapp.AppConfig.VICINITY;
import static natalybogdanova.appmapp.AppConfig.ZERO_RESULTS;

public class NE extends Activity implements MapViewFragment.Listener, AnswerMapFragment.Listener {

    FragmentManager mFragmentManager;

    MapViewFragment map_fr;
    QuizFragment quiz_fr;
    AnswerMapFragment map_ans;
    PhotoView photo_view;
    double latitude, longitude;
    String placeName = null;

    StringBuilder googlephoto;
    StringBuilder latti;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ne);

        map_fr = new MapViewFragment();
        quiz_fr = new QuizFragment();
        map_ans = new AnswerMapFragment();
        photo_view = new PhotoView();

        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.add(R.id.frgmCont1, quiz_fr);
        ftr.commit();

        FragmentTransaction ftr1 = getFragmentManager().beginTransaction();
        ftr1.add(R.id.frgmCont, photo_view);
        ftr1.commit();
        loadNearByPlaces(53.38, -6.22);
    }

    public void onClick(View v) {
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        Button button = (Button) findViewById(R.id.btnanswer);
        switch (v.getId()) {
            case R.id.btnmap:
                ftr.replace(R.id.frgmCont, map_fr);
                button.setVisibility(View.INVISIBLE);
                ftr.commit();
                break;
            case R.id.btnanswer:
                AnswerMapFragment fragment = AnswerMapFragment.newInstance(latti.toString());
                ftr.replace(R.id.frgmCont, fragment);
                button.setVisibility(View.VISIBLE);
                ftr.commit();
                break;
            case R.id.btnnext:
                ftr.replace(R.id.frgmCont, photo_view);
                button.setVisibility(View.INVISIBLE);
                loadNearByPlaces(53.34, -6.26);
                ftr.commit();
                break;
            case R.id.btnback:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void onSelected (String s) {
        Button button = (Button) findViewById(R.id.btnanswer);
        button.setVisibility(View.VISIBLE);
        String[] lat = s.split(",");
        String first = lat[0];
        String second = lat[1];
        String [] fir = first.split("\\(");
        String [] sec = second.split("\\)");
        Fragment quizzy = getFragmentManager().findFragmentById(R.id.frgmCont1);
        if (Math.abs(Double.parseDouble(fir[1]) - latitude) < 0.001 && Math.abs(Double.parseDouble(sec[0]) - longitude) < 0.001) {
            ((TextView) quizzy.getView().findViewById(R.id.quiz_text)).setText("Правильно! " +
                    "Нажми на \"Дальше\", чтобы продолжить.");

        } else {
            ((TextView) quizzy.getView().findViewById(R.id.quiz_text)).setText("Неправильно! " +
                    "Нажми на \"Ответ\", чтобы увидеть верное местоположение " + placeName + ".");
        }

    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, 0).show();
            }
            return false;
        }
        return true;
    }

    private void loadNearByPlaces(double latitude, double longitude) {
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works
        String type = "amusement_park|aquarium|art_gallery|church|city_hall|hindu_temple|zoo|synagogue|stadium|park|place_of_worship|museum|mosque|library";
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=").append(type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {

                        Log.i(TAG, "onResponse: Result= " + result.toString());
                        parseLocationResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseLocationResult(JSONObject result) {

        String id, place_id, photo, reference, icon, vicinity = null;

        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                //for (int i = 0; i < jsonArray.length(); i++) {
                Random rand = new Random();
                int i = rand.nextInt(jsonArray.length());
                JSONObject place = jsonArray.getJSONObject(i);
                latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                        .getDouble(LATITUDE);
                longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                        .getDouble(LONGITUDE);
                while(!(latitude >= 53.34 && latitude <= 53.40 && longitude <= -6.18&& longitude >= -6.26)) {
                    rand = new Random();
                    i = rand.nextInt(jsonArray.length());
                    place = jsonArray.getJSONObject(i);
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE);
                }
                    id = place.getString(SUPERMARKET_ID);
                    place_id = place.getString(PLACE_ID);
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    reference = place.getString(REFERENCE);
                    icon = place.getString(ICON);
                    photo = place.getJSONArray(PHOTO).getJSONObject(0).getString(PHOTO_REF);

                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(latitude, longitude);
                    markerOptions.position(latLng);
                    markerOptions.title(placeName + " : " + vicinity);
                //}

                latti = new StringBuilder("");
                latti.append(latitude).append("=");
                latti.append(longitude);

                AnswerMapFragment fragment = AnswerMapFragment.newInstance(latti.toString());

                Fragment quizzy = getFragmentManager().findFragmentById(R.id.frgmCont1);
                ((TextView) quizzy.getView().findViewById(R.id.quiz_text)).setText("Где находится " +
                placeName+"?");
                googlephoto = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400");
                googlephoto.append("&photoreference=").append(photo);
                googlephoto.append("&key=" + GOOGLE_BROWSER_API_KEY);

                onPhoto();

            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No Places found in this area!!!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }

    public void onPhoto() {
        String photo_cur = googlephoto.toString();
        Fragment quizzy1 = getFragmentManager().findFragmentById(R.id.frgmCont);
        ImageView mImageView;
        mImageView = (ImageView) quizzy1.getView().findViewById(R.id.imageView1);
        Picasso.with(this).load(photo_cur).into(mImageView);
    }
}
