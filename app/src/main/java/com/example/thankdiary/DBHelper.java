package com.example.thankdiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "hyevvy.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터 베이스가 생성이 될 때 호출

        // 데이터베이스 -> 테이블 -> 컬럼 -> 값값
        //primary key : 컬럼들을 계속 집어넣을건데, 이게 그 열쇠
        //AUTOINCREMENT : id가 데이터가 들어올 때마다 하나씩 자동 증가
        //NOT NULL : 비어 있지 않으면
       db.execSQL("CREATE TABLE IF NOT EXISTS Diary (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT NOT NULL, writeDate TEXT NOT NULL UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //특정 날짜 일기 조회
    public String getTodayDiaryList(String _writeDate){
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String content = null;
        Cursor cursor = db.rawQuery("SELECT * FROM Diary WHERE writeDate = '"+ _writeDate +"'", null);

       // String todayContent = String.valueOf(db.rawQuery("SELECT content FROM Diary WHERE writeDate = '"+ _writeDate +"'", null));
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
               int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
               content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                return content;
            }
        }
        else{
            return null;
        }
        cursor.close();

        return content;
    }

    public Integer getTotalDay(){
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Integer id = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM Diary", null);

        // String todayContent = String.valueOf(db.rawQuery("SELECT content FROM Diary WHERE writeDate = '"+ _writeDate +"'", null));
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
        }
        else{
            return null;
        }
        cursor.close();

        return id;
    }


    //SELECT 문 (일기를 조회한다.)
    public ArrayList<DiaryItem> getDiaryList(){
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        //내림차순으로 정렬
        Cursor cursor = db.rawQuery("SELECT * FROM Diary ORDER BY writeDate DESC", null);

        if(cursor.getCount() != 0){
            //조회한 데이터가 있는 경우
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                DiaryItem diaryItem = new DiaryItem();
                diaryItem.setId(id);
                diaryItem.setContent(content);
                diaryItem.setWriteDate(writeDate);

                diaryItems.add(diaryItem);
            }
        }
        cursor.close();

        return diaryItems;
    }


    //INSERT문 (일기를 DB에 넣는다.)
    public void InsertDiary(String _content, String _writeDate){
        System.out.println("db에 일기넣ㅇ늠");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Diary(content, writeDate) VALUES('" + _content + "', '" + _writeDate + "' )");
    }

    //UPDATE 문(일기를 수정한다.)
    public void UpdateDiary(String _content, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Diary SET content = '" + _content +"' ,  writeDate = '" + _writeDate +"' WHERE writeDate = '"+ _writeDate +"'");
    }

    // DELETE 문 (일기를 삭제한다.)
    public void DeleteDiary(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Diary WHERE id = '"+ _id +"' ");
    }
}
