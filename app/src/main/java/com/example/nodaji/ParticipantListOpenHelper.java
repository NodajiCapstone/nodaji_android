package com.example.nodaji;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ParticipantListOpenHelper extends SQLiteOpenHelper {
    public ParticipantListOpenHelper(Context context){
        super(context, "participant.db", null, 1);
    }
    //db를 생성하기 위한 sqlite open helper의 추상메서드.
    //id, 이름, 성별만 일단
    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS participant (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(50)," +
                "sex VARCHAR(50));");
        db.execSQL("insert into participant values (null, '김철수', '남자');");
        db.execSQL("insert into participant values (null, '김길동', '남자');");
        db.execSQL("insert into participant values (null, '서민영', '여자');");
        db.execSQL("insert into participant values (null, '김채은', '여자');");
        db.execSQL("insert into participant values (null, '김가현', '여자');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS participant");
        onCreate(db);
    }

//    public void insert(String name, String sex) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("INSERT INTO participant VALUES('" + name + "', '" + sex + "')");
//        db.close();
    }
//    public void Update(String name, int age, String Addr) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("UPDATE participant SET age = " + age + ", ADDR = '" + Addr + "'" + " WHERE NAME = '" + name + "'");
//        db.close();
//    }

//    // Person Table 데이터 삭제
//    public void Delete(String name) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE participant WHERE NAME = '" + name + "'");
//        db.close();
//    }

//    // Person Table 조회
//    public String getResult() {
//        // 읽기가 가능하게 DB 열기
//        SQLiteDatabase db = getReadableDatabase();
//        String result = "";
//
//        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
//        Cursor cursor = db.rawQuery("SELECT * FROM participant", null);
//        while (cursor.moveToNext()) {
//            result += " 이름 : " + cursor.getString(0)
//                    + ", 나이 : "
//                    + cursor.getInt(1)
//                    + ", 주소 : "
//                    + cursor.getString(2)
//                    + "\n";
//        }
//
//        return result;
//    }
//}
