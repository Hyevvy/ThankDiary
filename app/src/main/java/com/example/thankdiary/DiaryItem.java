package com.example.thankdiary;

public class DiaryItem {
    private int id; // 게시글의 고유 ID
    private String content; // 일기 내용
    private String writeDate; //작성 날짜
    private int year; // 작성 날
    private int month; //작성 달


    public DiaryItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    public int getYear() {
        if(this.writeDate != null){
            this.year = ((this.writeDate.charAt(0)-'0')*1000 + (this.writeDate.charAt(1)-'0')*100 + (this.writeDate.charAt(2)-'0')*10 + this.writeDate.charAt(3)-'0');
        }
        return year;
    }

    public int getMonth() {
        if(this.writeDate != null){
            this.month = (this.writeDate.charAt(6)-'0')*10 + this.writeDate.charAt(7)-'0';
        }
        return this.month;
    }

}
