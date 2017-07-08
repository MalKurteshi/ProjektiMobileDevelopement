package com.fiek.mali.projektimobiledevelopement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mali on 5/13/2017.
 */

// versioni i vjeter
// versioni i pare
//class Databaza extends SQLiteOpenHelper {
//    private static final String DB_NAME = "OrariNeFiek.db";
//    private static final int DB_VERSION = 1;
//
//    private static final String SCHEDULE_TABLE_NAME = "schedule";
//    private static final String SCHEDULE_COLUMN_ID = "_id";
//    private static final String SCHEDULE_COLUMN_DAY = "day";
//    private static final String SCHEDULE_COLUMN_CLASSNUMBER = "classnumber";
//    private static final String SCHEDULE_COLUMN_PROFESORNAME = "profesorname";
//    private static final String SCHEDULE_COLUMN_STARTTIME = "starttime";
//    private static final String SCHEDULE_COLUMN_ENDTIME = "endtime";
//
//    public Databaza(Context context) {
//        super(context, DB_NAME , null, DB_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE schedule (_id INT PRIMARY KEY NOT NULL, day TEXT NOT NULL, classnumber TEXT NOT NULL, " +
//                "profesorname TEXT NOT NULL, starttime TEXT NOT NULL, endtime TEXT NOT NULL)");
//        db.execSQL("CREATE TABLE comments (_id INT PRIMARY KEY NOT NULL, classroom TEXT NOT NULL, commentcontent TEXT NOT NULL, reg_date TEXT NOT NULL)");
//    }
//
//    public void insertLecture(int id, String day, String classnumber, String profesorname, String starttime, String endtime) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(SCHEDULE_COLUMN_ID, id);
//        contentValues.put(SCHEDULE_COLUMN_DAY, day);
//        contentValues.put(SCHEDULE_COLUMN_CLASSNUMBER, classnumber);
//        contentValues.put(SCHEDULE_COLUMN_PROFESORNAME, profesorname);
//        contentValues.put(SCHEDULE_COLUMN_STARTTIME, starttime);
//        contentValues.put(SCHEDULE_COLUMN_ENDTIME, endtime);
//        db.insert(SCHEDULE_TABLE_NAME, null, contentValues);
//    }
//
//    public void insertLectureOrIgnore(int id, String day, String classnumber, String profesorname, String starttime, String endtime){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT OR IGNORE INTO schedule(_id, day, classnumber, classname, starttime, endtime) VALUES('"+id+"','"+day+"','"+classnumber+"','"+profesorname+"','"+starttime+"','"+endtime+"')");
//    }
//
//    public void insertCommentOrIgnore(int id, String classroom, String content, String reg_date){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT OR IGNORE INTO comments(_id, classroom, commentcontent, reg_date) VALUES('"+id+"','"+classroom+"','"+content+"','"+reg_date+"')");
//    }
//
//    public Cursor getAllLectures() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery( "SELECT * FROM " + SCHEDULE_TABLE_NAME, null );
//    }
//
//    public Cursor getTodayLecturesTimes(String classnumber) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("select starttime,endtime from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
//    }
//
//    public Cursor getClassComments(String classroom) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("select * from comments where classroom='"+classroom+"' order by reg_date desc",null);
//    }
//
//    public Cursor getTodayLectures(String classnumber) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("select * from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // In case the db needs to upgraded, we just drop and recreate
//        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
//        onCreate(db);
//    }
//}


//versioni i gentit


class Databaza extends SQLiteOpenHelper {
    private static final String DB_NAME = "OrariNeFiek.db";
    private static final int DB_VERSION = 1;

    private static final String SCHEDULE_TABLE_NAME = "schedule";
    private static final String SCHEDULE_COLUMN_ID = "_id";
    private static final String SCHEDULE_COLUMN_DAY = "day";
    private static final String SCHEDULE_COLUMN_CLASSNUMBER = "classnumber";
    private static final String SCHEDULE_COLUMN_STARTTIME = "starttime";
    private static final String SCHEDULE_COLUMN_ENDTIME = "endtime";
    private static final String SCHEDULE_COLUMN_GRUPI = "grupi";
    private static final String SCHEDULE_COLUMN_TYPE = "type";
    private static final String SCHEDULE_COLUMN_PROFESORNAME = "profesorname";
    private static final String SCHEDULE_COLUMN_SUBJECT = "subject";

    public Databaza(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE schedule (_id INT PRIMARY KEY NOT NULL, day TEXT NOT NULL, classnumber TEXT NOT NULL, " +
                "starttime TEXT NOT NULL, endtime TEXT NOT NULL, grupi TEXT NOT NULL, type TEXT NOT NULL, profesorname TEXT NOT NULL, subject TEXT NOT NULL)");
        Log.v("Databaza","CREATE TABLE schedule (_id INT PRIMARY KEY NOT NULL, day TEXT NOT NULL, classnumber TEXT NOT NULL, starttime TEXT NOT NULL, endtime TEXT NOT NULL, grupi TEXT NOT NULL, type TEXT NOT NULL, profesorname TEXT NOT NULL, subject TEXT NOT NULL)");
    }

    public void insertLecture(int id, String day, String classnumber, String starttime, String endtime, String grupi, String type, String profesorname, String subject) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COLUMN_ID, id);
        contentValues.put(SCHEDULE_COLUMN_DAY, day);
        contentValues.put(SCHEDULE_COLUMN_CLASSNUMBER, classnumber);
        contentValues.put(SCHEDULE_COLUMN_STARTTIME, starttime);
        contentValues.put(SCHEDULE_COLUMN_ENDTIME, endtime);
        contentValues.put(SCHEDULE_COLUMN_GRUPI, grupi);
        contentValues.put(SCHEDULE_COLUMN_TYPE, type);
        contentValues.put(SCHEDULE_COLUMN_PROFESORNAME, profesorname);
        contentValues.put(SCHEDULE_COLUMN_SUBJECT, subject);
        db.insert(SCHEDULE_TABLE_NAME, null, contentValues);
    }

    public void insertLectureOrIgnore(int id, String day, String classnumber, String starttime, String endtime, String grupi, String type, String profesorname, String subject){
        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT OR IGNORE INTO schedule(_id, day, classnumber, starttime, endtime, grupi, type, profesorname, subject) VALUES('"+id+"','"+day+"','"+classnumber+"','"+starttime+"','"+endtime+"','"+grupi+"','"+type+"','"+profesorname+"','"+subject+"')");
        db.execSQL("INSERT OR IGNORE INTO schedule(_id, day, classnumber, starttime, endtime, grupi, type, profesorname, subject) VALUES('"+id+"','"+day+"','"+classnumber+"','"+starttime+"','"+endtime+"','"+grupi+"','"+type+"','"+profesorname+"','"+subject+"')");
        Log.v("Databaza","INSERT OR IGNORE INTO schedule(_id, day, classnumber, starttime, endtime, grupi, type, profesorname, subject) VALUES('"+id+"','"+day+"','"+classnumber+"','"+starttime+"','"+endtime+"','"+grupi+"','"+type+"','"+profesorname+"','"+subject+"')");
    }

    public void insertCommentOrIgnore(int id, String classroom, String content, String reg_date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT OR IGNORE INTO comments(_id, classroom, commentcontent, reg_date) VALUES('"+id+"','"+classroom+"','"+content+"','"+reg_date+"')");
    }

    public Cursor getAllLectures() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "SELECT * FROM " + SCHEDULE_TABLE_NAME, null );
    }

    public Cursor getTodayLecturesTimes(String classnumber) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        Log.v("Databaza.java",day);
        // E kthen shqip
        switch (day)
        {
            case "Monday": day="E hene"; break;
            case "Tuesday": day="E marte"; break;
            case "Wednesday": day="E merkure"; break;
            case "Thursday": day="E enjte"; break;
            case "Friday": day="E premte"; break;
            case "Saturday": day="E shtune"; break;
            case "Sunday": day="E diele"; break;
        }
        Log.v("Databaza.java",day);
        SQLiteDatabase db = this.getReadableDatabase();
//Query i rregulluar
//        return db.rawQuery("select starttime,endtime from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
        // Query per diten e Hane (Testuese)
        return db.rawQuery("select starttime,endtime from schedule where day='E marte' and classnumber='"+classnumber+"'",null);
    }

//    public Cursor getClassComments(String classroom) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("select * from comments where classroom='"+classroom+"' order by reg_date desc",null);
//    }

    public Cursor getTodayLectures(String classnumber) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        Log.v("Databaza",day);
        // E kthen shqip
        switch (day)
        {
            case "Monday": day="E hene"; break;
            case "Tuesday": day="E marte"; break;
            case "Wednesday": day="E merkure"; break;
            case "Thursday": day="E enjte"; break;
            case "Friday": day="E premte"; break;
            case "Saturday": day="E shtune"; break;
            case "Sunday": day="E diele"; break;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Log.v("Databaza.java",day);
        // Origjinali
//        return db.rawQuery("select * from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
        // Testuese
        return db.rawQuery("select * from schedule where day='E marte' and classnumber='"+classnumber+"'",null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // In case the db needs to upgraded, we just drop and recreate
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
        onCreate(db);
    }
}