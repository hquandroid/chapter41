package cn.edu.hqu.cst.android.chapter4_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL="create table dict(_id integer primary key autoincrement,word,detail)";

    public MyDatabaseHelper(Context context, String name,int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        System.out.println("----------更新调用-------"+oldVersion+"--->"+newVersion);
    }
}
