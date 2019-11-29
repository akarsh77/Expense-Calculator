package com.example.expensecalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbhelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expense_tracker.db";
    public static final String EXPENSES_TABLE_NAME = "expenses";
    public static final String COL_1="ID";
    public static final String COL_2="AMOUNT";
    public static final String COL_3="DATE";
    public static final String COL_4="NOTES";
    public static final String COL_5="CATEGORY";

    private Context mContext;

    public dbhelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(ExpensesTable.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ExpensesTable.DELETE_TABLE_QUERY);
        onCreate(db);
    }
    public boolean insertData(Float amount, String date,String notes,String category){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,amount);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,notes);
        contentValues.put(COL_5,category);
        long result=db.insert(EXPENSES_TABLE_NAME,null,contentValues);
        Log.d("insert",String.valueOf(result));
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor view_expenses(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from expenses",null);
        return cursor;

    }
    public Cursor view_expenses(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from expenses where DATE=?",new String[]{date});
        return cursor;

    }



    private static final class ExpensesTable {
        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + EXPENSES_TABLE_NAME + " ( ID  INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT  FLOAT NOT NULL, DATE NOT NULL, NOTES TEXT, CATEGORY TEXT);";

        public static final String DELETE_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME + ";";
    }
}
