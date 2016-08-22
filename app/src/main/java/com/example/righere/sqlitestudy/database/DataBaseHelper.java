package com.example.righere.sqlitestudy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Righere on 2016/8/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper{
    public static final String TIME = "time";
    public static final String WORD_DICTIONARY = "dictionary";
    public static final String DETAILS = "detials";
    public static final String WORD = "word" ;

    //创建一个data_study.db数据库
    public DataBaseHelper(Context context) {
        super(context, "data_study.db", null, 1);
    }

    //执行SQL语句。创建一个数据库表，设置表的三个变量time、word和detail
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "create table "
                + WORD_DICTIONARY + "("
                + TIME + " char(60) not null,"
                + WORD + " char(60) not null,"
                + DETAILS + " char(60) not null);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
