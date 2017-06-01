package natalybogdanova.appmapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import static natalybogdanova.appmapp.R.id.spinner;


public class Signin extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, Button.OnClickListener, AdapterView.OnItemSelectedListener {

    private static int RC_SIGN_IN = 0;

    final String LOG_TAG = "myLogs";

    DataBaseUsers dbHelper;

    private static final String TAG = "";
    GoogleApiClient mGoogleApiClient;

    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        dbHelper = new DataBaseUsers(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestEmail()
               .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.table_button).setOnClickListener(this);
        findViewById(R.id.awards_button).setOnClickListener(this);

        if (((AppController) getApplication()).provideUser() != null &&
                mGoogleApiClient != null) {
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            findViewById(R.id.awards_button).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.sign_out_button).setVisibility(View.INVISIBLE);
            findViewById(R.id.awards_button).setVisibility(View.INVISIBLE);
        }

        String picture = "http://www.zzorik.ru/ris/it/paris_sm2.jpg";

        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(picture).into(mImageView);

        spinner1 = (Spinner) findViewById(spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String elem = spinner1.getSelectedItem().toString();
        LatLng city = new LatLng(53.34, -6.26);
        LatLng top_mid = new LatLng(53.40, -6.26);
        LatLng top_right = new LatLng(53.40, -6.34);
        LatLng top_left = new LatLng(53.40, -6.18);
        LatLng bot_mid = new LatLng(53.28, -6.26);
        LatLng bot_right = new LatLng(53.28, -6.34);
        LatLng bot_left = new LatLng(53.28, -6.18);
        LatLng mid_right = new LatLng(53.34, -6.34);
        LatLng mid_left = new LatLng(53.34, -6.18);
        AppController app = ((AppController) getApplicationContext());
        if (elem.equals("Цюрих")) {
            city = new LatLng(47.37, 8.54);
            top_mid = new LatLng(47.43, 8.54);
            top_right = new LatLng(47.43, 8.46);
            top_left = new LatLng(47.43, 8.62);
            bot_mid = new LatLng(47.31, 8.54);
            bot_right = new LatLng(47.31, 8.46);
            bot_left = new LatLng(47.31, 8.62);
            mid_right = new LatLng(47.37, 8.46);
            mid_left = new LatLng(47.37, 8.62);
        } else if (elem.equals("Москва")) {
            city = new LatLng(55.75, 37.61);
            top_mid = new LatLng(55.81, 37.61);
            top_right = new LatLng(55.81, 37.53);
            top_left = new LatLng(55.81, 37.69);
            bot_mid = new LatLng(55.69, 37.61);
            bot_right = new LatLng(55.69, 37.53);
            bot_left = new LatLng(55.69, 37.69);
            mid_right = new LatLng(55.75, 37.53);
            mid_left = new LatLng(55.75, 37.69);
        } else if (elem.equals("Ташкент")) {
            city = new LatLng(41.31, 69.24);
            top_mid = new LatLng(41.37, 69.24);
            top_right = new LatLng(41.37, 69.16);
            top_left = new LatLng(41.37, 69.32);
            bot_mid = new LatLng(41.25, 69.24);
            bot_right = new LatLng(41.25, 69.16);
            bot_left = new LatLng(41.25, 69.32);
            mid_right = new LatLng(41.31, 69.16);
            mid_left = new LatLng(41.31, 69.32);
        }
        app.setLatLng(city, top_mid, top_right, top_left, bot_mid, bot_right, bot_left, mid_right, mid_left);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
        signOut();
    }

    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }

    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume ");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                findViewById(R.id.sign_out_button).setVisibility(View.INVISIBLE);
                break;
            case R.id.table_button:
                Intent intent = new Intent(this, TableUsers.class);
                startActivity(intent);
                break;
            case R.id.awards_button:
                Intent intent1 = new Intent(this, ShowAwards.class);
                startActivity(intent1);
            default:
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            boolean flag = false;
            SQLiteDatabase tmp_database = dbHelper.getWritableDatabase();
            GoogleSignInAccount acct = result.getSignInAccount();
            AppController app = ((AppController) getApplicationContext());
            app.setUser(acct);
            String mFullName = acct.getDisplayName();
            String mEmail = acct.getEmail();
            Cursor cursor = tmp_database.query(DataBaseUsers.TABLE_CONTACTS, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DataBaseUsers.KEY_MAIL);
                do {
                    Log.d("myLogs", " MAIL: " + cursor.getString(idIndex));
                    if (cursor.getString(idIndex).equals(mEmail)) {
                        flag = true;
                        break;
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
            if (!flag) {
                ContentValues new_elem = new ContentValues();
                new_elem.put(DataBaseUsers.KEY_SCORES, 0);
                new_elem.put(DataBaseUsers.KEY_NAME, mFullName);
                new_elem.put(DataBaseUsers.KEY_MAIL, mEmail);
                Log.d("myLogs", " MAIL: add " + mFullName);
                tmp_database.insert(DataBaseUsers.TABLE_CONTACTS, null, new_elem);
            }
            Log.d("myLogs", " MAIL: found " + mFullName);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Signin.class);
            startActivity(intent);
        }
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                    }
                });
        findViewById(R.id.awards_button).setVisibility(View.INVISIBLE);
    }

    public void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

}
