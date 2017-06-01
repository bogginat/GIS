package natalybogdanova.appmapp;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.lang.reflect.Array;

import static natalybogdanova.appmapp.AppConfig.TAG;

public class AppController extends Application {

    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    GoogleSignInAccount curAcc;

    LatLng city, top_mid, top_right, top_left, bot_mid, bot_right, bot_left, mid_right, mid_left;

    LatLng polygon_cur1, polygon_cur2, polygon_cur3, polygon_cur4;
    LatLng area_cur1, area_cur2;

    public void setLatLng(LatLng city1, LatLng top_mid1, LatLng top_right1, LatLng top_left1,
                          LatLng bot_mid1, LatLng bot_right1, LatLng bot_left1, LatLng mid_right1,
                          LatLng mid_left1) {
        city = city1;
        top_mid = top_mid1;
        top_right = top_right1;
        top_left = top_left1;
        bot_mid = bot_mid1;
        bot_right = bot_right1;
        bot_left = bot_left1;
        mid_right = mid_right1;
        mid_left = mid_left1;
    }
    public LatLng[] getLatLng() {
        LatLng ans[] = {city, top_mid, top_right, top_left, bot_mid, bot_right, bot_left, mid_right, mid_left};
        return ans;
    }

    public void setPoly(LatLng first, LatLng second, LatLng third, LatLng forth) {
        polygon_cur1 = first;
        polygon_cur2 = second;
        polygon_cur3 = third;
        polygon_cur4 = forth;
    }
    public LatLng[] getPoly() {
        LatLng ans[] = {polygon_cur1, polygon_cur2, polygon_cur3, polygon_cur4};
        return ans;
    }

    public LatLng getCentre() {
        return city;
    }

    public void setArea(LatLng first, LatLng second) {
        area_cur1 = first;
        area_cur2 = second;
    }
    public LatLng[] getArea() {
        LatLng ans[] = {area_cur1, area_cur2};
        return ans;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void setUser(GoogleSignInAccount acc) {
        curAcc = acc;
    }

    public GoogleSignInAccount provideUser() {
        return curAcc;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}