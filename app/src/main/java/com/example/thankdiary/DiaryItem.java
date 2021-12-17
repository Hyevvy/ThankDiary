package com.example.thankdiary;

public class DiaryItem {
    private int id; // 게시글의 고유 ID
    private String content; // 일기 내용
    private String writeDate; //작성 날짜

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
}
