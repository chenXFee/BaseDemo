package com.cxf.moudule_common.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.List;

public class SQLiteUtil extends SQLiteOpenHelper {

    private static final String DB_NAME = "Demo.db";
    private Context context;
    private static final int DB_VERSION = 1;

    public SQLiteUtil(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void createTable(String tableName, List<String> row, List<String> rowProperty,SQLiteDatabase mDatabase){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table " + tableName + "(_id integer primary key autoincrement, ");
        if(row.size()!=rowProperty.size()){
            return;
        }
        for(int i=0;i<row.size();i++) {
            if (i == row.size() - 1) {
                stringBuffer.append(row.get(i) + " " + rowProperty.get(i) + " ");
            } else {
                stringBuffer.append(row.get(i) + " " + rowProperty.get(i) + ", ");
            }
        }
        stringBuffer.append(" )");
        mDatabase.execSQL(stringBuffer.toString());
    }


}
