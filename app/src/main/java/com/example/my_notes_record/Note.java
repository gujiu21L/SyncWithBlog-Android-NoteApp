package com.example.my_notes_record;

// 定义笔记的数据类型
public class Note {
    private long id; // 笔记ID
    private String content; // 笔记内容
    private String time; // 笔记创建时间

    public Note(){}
    public Note(String content , String time){
        this.content=content;
        this.time=time;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public String getTime() {
        return time;
    }
}