package natalybogdanova.appmapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

public class ShowAwards extends Activity {

    DataBaseUsers dbHelper;
    DataBaseAwards db1Helper, db2Helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showawards);
        int ans = 0;
        GoogleSignInAccount acc_tmp = ((AppController) getApplication()).provideUser();
        String mEmail = acc_tmp.getEmail();
        dbHelper = new DataBaseUsers(this);
        db1Helper = new DataBaseAwards(this);
        db2Helper = new DataBaseAwards(this);
        SQLiteDatabase tmp_database = dbHelper.getWritableDatabase();
        SQLiteDatabase tmp_database1 = db1Helper.getWritableDatabase();
        SQLiteDatabase tmp_database2 = db2Helper.getWritableDatabase();
        Cursor cursor = tmp_database.query(DataBaseUsers.TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DataBaseUsers.KEY_MAIL);
            do {
                Log.d("myLogs", " MAIL: " + cursor.getString(idIndex));
                if (cursor.getString(idIndex).equals(mEmail)) {
                    idIndex = cursor.getColumnIndex(DataBaseUsers.KEY_SCORES);
                    ans = cursor.getInt(idIndex);
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        ContentValues new_elem = new ContentValues();
        new_elem.put(DataBaseAwards.KEY_NAME, "http://www.tourprom.ru/site_media/images/upload/2016/8/30/resortimage/moskva-kremlj.jpg");
        tmp_database2.insert(DataBaseAwards.TABLE_CONTACTS, null, new_elem);
        new_elem = new ContentValues();
        new_elem.put(DataBaseAwards.KEY_NAME, "https://www.homesoverseas.ru/upload/userfiles/images/Swiss_Image_sts8589.jpg");
        tmp_database2.insert(DataBaseAwards.TABLE_CONTACTS, null, new_elem);
        new_elem = new ContentValues();
        new_elem.put(DataBaseAwards.KEY_NAME, "http://www.visituzbekistan.travel/ru/sightseeing/wp-content/uploads/2012/02/tashkent-culture-museums-reppression2.jpg");
        tmp_database2.insert(DataBaseAwards.TABLE_CONTACTS, null, new_elem);
        new_elem = new ContentValues();
        new_elem.put(DataBaseAwards.KEY_NAME, "http://irelandru.com/wp-content/uploads/2014/12/dublin-irl-620x330.jpg");
        tmp_database2.insert(DataBaseAwards.TABLE_CONTACTS, null, new_elem);
        Cursor cursor1 = tmp_database2.query(DataBaseAwards.TABLE_CONTACTS, null, null, null, null, null, null);
        cursor1.moveToFirst();
        int idIndex1 = cursor1.getColumnIndex(DataBaseAwards.KEY_NAME);
        String needed;
        ImageView mImageView = (ImageView) findViewById(R.id.imageView2);
        TextView mTextView = (TextView) findViewById(R.id.textview);
        mTextView.setText("К сожалению, у Вас пока нет фишек.");
        if (ans / 20 > 0) {
            needed = cursor1.getString(idIndex1);
            cursor1.moveToNext();
            Picasso.with(this).load(needed).into(mImageView);
            mTextView.setText("У Вас 1 фишка!");
        }
        if (ans / 20 > 1) {
            mImageView = (ImageView) findViewById(R.id.imageView3);
            idIndex1 = cursor1.getColumnIndex(DataBaseAwards.KEY_NAME);
            needed = cursor1.getString(idIndex1);
            cursor1.moveToNext();
            Picasso.with(this).load(needed).into(mImageView);
            mTextView.setText("У Вас 2 фишки!");
        }
        if (ans / 20 > 2) {
            mImageView = (ImageView) findViewById(R.id.imageView4);
            idIndex1 = cursor1.getColumnIndex(DataBaseAwards.KEY_NAME);
            needed = cursor1.getString(idIndex1);
            cursor1.moveToNext();
            Picasso.with(this).load(needed).into(mImageView);
            mTextView.setText("У Вас 3 фишки!");
        }
        if (ans / 20 > 3) {
            mImageView = (ImageView) findViewById(R.id.imageView5);
            idIndex1 = cursor1.getColumnIndex(DataBaseAwards.KEY_NAME);
            needed = cursor1.getString(idIndex1);
            cursor1.moveToNext();
            Picasso.with(this).load(needed).into(mImageView);
            mTextView.setText("У Вас 4 фишки!");
        }
        cursor1.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
