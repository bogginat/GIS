package natalybogdanova.appmapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TableUsers extends Activity {

    final String LOG_TAG = "myLogs";
    DataBaseUsers dbHelper;
    SQLiteDatabase db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        String names[] = new String[100];
        int i = 0;
        dbHelper = new DataBaseUsers(this);
        String orderBy = "scores";
        db = dbHelper.getWritableDatabase();
        Cursor c = null;
        c = db.query("users", null, null, null, null, null, orderBy);
        // находим список
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    int ind1 = c.getColumnIndex(DataBaseUsers.KEY_NAME);
                    int ind2 = c.getColumnIndex(DataBaseUsers.KEY_SCORES);
                    str = (i + 1) + ". " + c.getString(ind1) + "\nЧисло очков: " + c.getString(ind2) + "\nУровень: " + (c.getInt(ind2) / 20 + 1);
                    names[i] = str;
                    ++i;
                } while (c.moveToNext() && i < 100);
            }
            c.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

        String res[] = new String[i];
        for (int j = 0; j < i; ++j) {
            res[j]=names[j];
        }
        dbHelper.close();
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        if (lvMain != null) {
            // создаем адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res);

            // присваиваем адаптер списку
            lvMain.setAdapter(adapter);
        }
    }
}
