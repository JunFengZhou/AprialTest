package zjf.com.aprialtest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* 获取SharedPreferences对象的三种方法
        SharedPreferences shared = getSharedPreferences("shared", 0);

        getPreferences(MODE_PRIVATE);*/
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();
        editor.putBoolean("1",true);
        editor.putString("2","start");
        editor.putInt("3",3);
       // editor.apply();

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.hide();
//        }

/*  SQLiteOpenHelper
        SqlTest sqlTest = new SqlTest(this, "BookStore.db", null, 1);
        writableDatabase = sqlTest.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name","三体");
        values.put("author","大刘");
        writableDatabase.insert("BookStore.db",null,values);
        values.clear();
        values.put("name","白夜行");
        values.put("author","东野圭吾");
        writableDatabase.insert("BookStrore.db",null,values);
        values.clear();

        //更新update
        values.put("price",79);
        writableDatabase.update("Book",values,"name=?",new String[]{"三体"});
        writableDatabase.delete("Book","name=?",new String[]{"白夜行"});
        */


        SQLiteDatabase database = Connector.getDatabase();
    }

    public void save() {
        BufferedWriter writer = null;
        FileOutputStream out = null;
        try {
            String data = "Data to save";
            out = openFileOutput(data, MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }

    class SqlTest extends SQLiteOpenHelper{
        private Context mContext;
        public static final String CREATE_BOOK = "create table Book(" +
                "id integer primary key autoincrement," +
                "author text," +
                "price real," +
                "pages integer," +
                "name text)";
        public SqlTest(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BOOK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            writableDatabase.execSQL("drop table if exists Book");
            onCreate(writableDatabase);
        }
    }

}
