package com.example.nodaji;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MarkerOptionsListOpenHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "options.db";
    static final String TABLE_MARKER = "markers"; //Table 이름
    private SQLiteDatabase mydatabase = null;

//    public Cursor query(String[] colums,
//                        String selection,
//                        String[] selectionArgs,
//                        String groupBy,
//                        String having,
//                        String orderby)
//    {
//        return mydatabase.query(TABLE_MARKER,
//                colums,
//                selection,
//                selectionArgs,
//                groupBy,
//                having,
//                orderby);
//    }

    // DBHelper 생성자
    public MarkerOptionsListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // 싱글톤 패턴으로 구현
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE options(_id INT PRIMARY KEY, name VARCHAR(30))");
        db.execSQL("insert into options values (1, '사과');");
        db.execSQL("insert into options values (2, '당근');");
        db.execSQL("insert into options values (3, '물컵');");
        db.execSQL("insert into options values (4, '지갑');");
        db.execSQL("insert into options values (5, '휴대폰');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS options");
        onCreate(db);
    }

    public void insert(String name, int age, String Addr) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO options VALUES('" + name + "')");
        db.close();
    }

    public void Update(String name, int age, String Addr) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE options SET age = " + age + ", ADDR = '" + Addr + "'" + " WHERE NAME = '" + name + "'");
        db.close();
    }

    public void Delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE options WHERE NAME = '" + name + "'");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM options", null);
        while (cursor.moveToNext()) {
            result += " option : " + cursor.getString(0);
        }

        return result;
    }
}
