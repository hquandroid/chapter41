package cn.edu.hqu.cst.android.chapter4_1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.UserDictionary;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.*;
public class DictProvider extends ContentProvider {
    private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS=1;
    private static final int WORD=2;
    private MyDatabaseHelper dbOpenHelper;
    static{
        matcher.addURI(Words.AUTHORITY,"words",WORDS);
        matcher.addURI(Words.AUTHORITY,"word/#",WORD);
    }
    @Override
    public boolean onCreate() {
        dbOpenHelper=new MyDatabaseHelper(this.getContext(),"myDict.db3",1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String where, @Nullable String[] whereArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case WORDS:
                return db.query("dict",projection,where,whereArgs,null,null,sortOrder);
            case WORD:
                long id=ContentUris.parseId(uri);
                String whereClause=Words.Word._ID+"="+id;
                if(where!=null && !"".equals(where)){
                    whereClause=whereClause+" and "+where;
                }
                return db.query("dict",projection,whereClause,whereArgs,null,null,sortOrder);
            default:
                throw new IllegalArgumentException("未知Uri:"+uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)){
            case WORDS:
                return "vnd.android.cursor.dir/cn.edu.hqu.cst.android.chapter4_1.dict";
            case WORD:
                return "vnd.android.cursor.item/cn.edu.hqu.cst.android.chapter4_1.dict";
            default:
                throw new IllegalArgumentException("未知Uri:"+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case WORDS:
                long rowId=db.insert("dict",Words.Word._ID,contentValues);
                if(rowId>0){
                    Uri wordUri= ContentUris.withAppendedId(uri,rowId);
                    getContext().getContentResolver().notifyChange(wordUri,null);
                    return wordUri;
                }
                break;
            default:
                throw new IllegalArgumentException("未知Uri:"+uri);

        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
        int num=0;
        switch (matcher.match(uri)){
            case WORDS:
                num=db.delete("dict",where,whereArgs);
                break;
            case WORD:
                long id=ContentUris.parseId(uri);
                String whereClause=Words.Word._ID+"="+id;
                if(where!=null&&!where.equals("")){
                    whereClause=whereClause+" and "+where;
                }
                num=db.delete("dict",whereClause,whereArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri："+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String where, @Nullable String[] whereArgs) {
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        int num=0;
        switch (matcher.match(uri)){
            case WORDS:
                num=db.update("dict",contentValues,where,whereArgs);
                break;
            case WORD:
                long id=ContentUris.parseId(uri);
                String whereClause=Words.Word._ID+"="+id;
                if(where!=null&&!where.equals("")){
                    whereClause=whereClause+" and "+where;
                }
                num=db.update("dict",contentValues,whereClause,whereArgs);
                break;
            default:
                    throw new IllegalArgumentException("未知Uri："+uri);

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return num;
    }


}
