package com.example.thankdiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    public String getTodayDiaryList(String _writeDate) {
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String content = null;
        Cursor cursor = db.rawQuery("SELECT * FROM Diary WHERE writeDate = '" + _writeDate + "'", null);

        // String todayContent = String.valueOf(db.rawQuery("SELECT content FROM Diary WHERE writeDate = '"+ _writeDate +"'", null));
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
                return content;
            }
        } else {
            return null;
        }
        cursor.close();

        return content;
    }

    public Integer getTotalDay() {
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Integer id = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM Diary", null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            }
        } else {
            return null;
        }
        cursor.close();

        return id;
    }

    //오늘 날짜를 기준으로 연속 며칠동안 일기를 작성하였는지 return
    public Integer getSeriesDay(String todayDate) {
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        //내림차순으로 정렬
        Integer seriesDay = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM Diary ORDER BY writeDate DESC", null);

        String prevDate = todayDate;


        if (cursor.getCount() != 0) {
            //조회한 데이터가 있는 경우
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));

                System.out.println("prevDate" + prevDate);
                System.out.println("writeDate" + writeDate);

                //오늘 기준의 일기가 있을 때
                if(writeDate.equals(prevDate)) {
                    seriesDay++;
                    continue;
                }

                //오늘 기준의 일기가 없을 때
                if(writeDate != prevDate && seriesDay == 0){
                    return 0;
                }

                Calendar getToday = Calendar.getInstance();
                try {
                    getToday.setTime(new SimpleDateFormat("yyyy. MM. dd").parse(prevDate)); //금일 날짜
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                try {
                    Date date = new SimpleDateFormat("yyyy. MM. dd").parse(writeDate);
                    Calendar cmpDate = Calendar.getInstance();
                    cmpDate.setTime(date); //특정 일자
                    long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
                    long diffDays = diffSec / (24*60*60); //일자수 차이

                    if(diffDays != 1){
                        return seriesDay;
                    }
                    seriesDay++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DiaryItem diaryItem = new DiaryItem();
                diaryItems.add(diaryItem);

                prevDate = writeDate;
            }
        }
        cursor.close();
        return seriesDay;
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
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                DiaryItem diaryItem = new DiaryItem();
                diaryItem.setId(id);
                diaryItem.setWriteDate(writeDate);
                diaryItems.add(diaryItem);
            }
        }
        cursor.close();

        return diaryItems;
    }

    //그 달의 일기가 있는 날들을 조회한다.
    public ArrayList<Integer> getThisMonthDiary(int month, int year){
        ArrayList<DiaryItem> diaryItems = new ArrayList<>();
       ArrayList<Integer> arr = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Diary ORDER BY writeDate DESC", null);

        if(cursor.getCount() != 0){
            //조회한 데이터가 있는 경우
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                DiaryItem diaryItem = new DiaryItem();
                diaryItem.setId(id);
                diaryItem.setWriteDate(writeDate);

                if(diaryItem.getMonth() == month && diaryItem.getYear() == year){
                    //같은 월과 연도의 일기인지 체크
                    //arr.add(writeDate);
                    arr.add((writeDate.charAt(10)-'0') * 10 + writeDate.charAt(11)-'0');
                }
            }
        }
        cursor.close();

        return arr;
    }


    //INSERT문 (일기를 DB에 넣는다.)
    public void InsertDiary(String _content, String _writeDate){
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
