package com.tianyuhou.paymo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yu on 10/13/2017.
 */

public class DBhelper extends SQLiteOpenHelper {
    private static final String DB_NAME="Paymo";
    private static final int DB_VER = 1;
    private static final String DB_TABLE_DAIRY = "DAIRY";

    private static final String DB_TABLE_DAIRY_DATE = "dairy_date";
    private static final String DB_TABLE_DAIRY_TITLE = "dairy_title";
    private static final String DB_TABLE_DAIRY_DESC = "dairy_desc";
    private static final String DB_TABLE_DAIRY_CATEGORY = "dairy_category";

    private static final String DB_TABLE_DAIRY_COST = "dairy_cost";

    public DBhelper(Context context) {

        super(context, DB_NAME,null,DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryDairy = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, " +
                                            "%s TEXT,%s TEXT,%s TEXT,%s FLOAT)",
                                            DB_TABLE_DAIRY,DB_TABLE_DAIRY_DATE,
                                            DB_TABLE_DAIRY_TITLE,DB_TABLE_DAIRY_DESC,
                                            DB_TABLE_DAIRY_CATEGORY,DB_TABLE_DAIRY_COST);
        sqLiteDatabase.execSQL(queryDairy);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryDairy = String.format("DROP TABLE IF EXISTS %s",DB_TABLE_DAIRY);

        sqLiteDatabase.execSQL(queryDairy);
        onCreate(sqLiteDatabase);
    }

    public boolean insertDairy(Dairy dairy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_TABLE_DAIRY_DATE,dairy.getDate());
        values.put(DB_TABLE_DAIRY_TITLE,dairy.getTitle());
        values.put(DB_TABLE_DAIRY_DESC,dairy.getDesc());
        values.put(DB_TABLE_DAIRY_CATEGORY,dairy.getCategory());
        values.put(DB_TABLE_DAIRY_COST,dairy.getCost());
        long result = db.insert(DB_TABLE_DAIRY,null,values);
        db.close();
        if(result == -1){
            return  false;
        }else{
            return  true;
        }

    }


    public void deleteDairy(String dairy){
        SQLiteDatabase db = this.getWritableDatabase();
//        String qu = "DELETE FROM "+DB_TABLE_DAIRY+" WHERE "+DB_TABLE_DAIRY_DATE +" = "
//               + dairy;
//        db.execSQL(qu);
        db.delete(DB_TABLE_DAIRY,DB_TABLE_DAIRY_DATE+ "= ?",new String[]{dairy});
        db.close();
    }

    public ArrayList<Dairy> getDairy(){
        ArrayList<Dairy> dairy = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] qu = {DB_TABLE_DAIRY_TITLE,DB_TABLE_DAIRY_DESC,DB_TABLE_DAIRY_DATE,DB_TABLE_DAIRY_CATEGORY,DB_TABLE_DAIRY_COST};
        Cursor cursor = db.query(DB_TABLE_DAIRY,qu,null,null,null,null,null);
        String title,desc,date,category;
        float cost;
        while (cursor.moveToNext()){
            title = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_TITLE));
            desc = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_DESC));
            date = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_DATE));
            category = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_CATEGORY));
            cost = cursor.getFloat(cursor.getColumnIndex(DB_TABLE_DAIRY_COST));
            Dairy dairy1 = new Dairy(title,desc,date,category,cost);
            dairy.add(dairy1);
        }
        cursor.close();
        db.close();
        return dairy;
    }

    public ArrayList<String> getTitle(){
        ArrayList<String> titleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE_DAIRY,new String[]{DB_TABLE_DAIRY_TITLE},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_TABLE_DAIRY_TITLE);
            titleList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return titleList;
    }

    public ArrayList<Cost> getCost(String startdate, String enddate){
        ArrayList<Cost> costlist = new ArrayList<>();
        String qu = "SELECT "+DB_TABLE_DAIRY_CATEGORY+", SUM("+DB_TABLE_DAIRY_COST+") FROM "+DB_TABLE_DAIRY+
                " WHERE strftime ('%s',"+DB_TABLE_DAIRY_DATE+") <= strftime('%s','"+enddate+"') AND STRFTIME ('%s',"+DB_TABLE_DAIRY_DATE+") >= STRFTIME('%s','"+startdate+"') AND " +DB_TABLE_DAIRY_COST+" <= 0 "+
                "GROUP BY "+DB_TABLE_DAIRY_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qu,null);
        String name;
        float cost;
        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_CATEGORY));
            cost = cursor.getFloat(cursor.getColumnIndex("SUM("+DB_TABLE_DAIRY_COST+")"));
            Cost costclass = new Cost(name,cost);
            costlist.add(costclass);
        }
        cursor.close();
        db.close();
        return costlist;
    }

    public ArrayList<Cost> getCost(String enddate) {
        ArrayList<Cost> costlist = new ArrayList<>();
        String qu = "SELECT "+DB_TABLE_DAIRY_CATEGORY+", SUM("+DB_TABLE_DAIRY_COST+") FROM "+DB_TABLE_DAIRY+
                " WHERE strftime ('%s',"+DB_TABLE_DAIRY_DATE+") <= strftime('%s','"+enddate+"') AND " +DB_TABLE_DAIRY_COST+" <= 0 "+
                "GROUP BY "+DB_TABLE_DAIRY_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qu,null);
        String name;
        float cost;
        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_CATEGORY));
            cost = cursor.getFloat(cursor.getColumnIndex("SUM("+DB_TABLE_DAIRY_COST+")"));
            Cost costclass = new Cost(name,cost);
            costlist.add(costclass);
        }
        cursor.close();
        db.close();
        return costlist;
    }

    public ArrayList<Cost> getEarn(String startdate, String enddate) {
        ArrayList<Cost> costlist = new ArrayList<>();
        String qu = "SELECT "+DB_TABLE_DAIRY_CATEGORY+", SUM("+DB_TABLE_DAIRY_COST+") FROM "+DB_TABLE_DAIRY+
                " WHERE strftime ('%s',"+DB_TABLE_DAIRY_DATE+") <= strftime('%s','"+enddate+"') AND STRFTIME ('%s',"+DB_TABLE_DAIRY_DATE+") >= STRFTIME('%s','"+startdate+"') AND " +DB_TABLE_DAIRY_COST+" >= 0 "+
                "GROUP BY "+DB_TABLE_DAIRY_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qu,null);
        String name;
        float cost;
        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_CATEGORY));
            cost = cursor.getFloat(cursor.getColumnIndex("SUM("+DB_TABLE_DAIRY_COST+")"));
            Cost costclass = new Cost(name,cost);
            costlist.add(costclass);
        }
        cursor.close();
        db.close();
        return costlist;

    }

    public ArrayList<Cost> getEarn(String enddate) {
        ArrayList<Cost> costlist = new ArrayList<>();
        String qu = "SELECT "+DB_TABLE_DAIRY_CATEGORY+", SUM("+DB_TABLE_DAIRY_COST+") FROM "+DB_TABLE_DAIRY+
                " WHERE strftime ('%s',"+DB_TABLE_DAIRY_DATE+") <= strftime('%s','"+enddate+"') AND " +DB_TABLE_DAIRY_COST+" >= 0 "+
                "GROUP BY "+DB_TABLE_DAIRY_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qu,null);
        String name;
        float cost;
        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(DB_TABLE_DAIRY_CATEGORY));
            cost = cursor.getFloat(cursor.getColumnIndex("SUM("+DB_TABLE_DAIRY_COST+")"));
            Cost costclass = new Cost(name,cost);
            costlist.add(costclass);
        }
        cursor.close();
        db.close();
        return costlist;
    }
}
