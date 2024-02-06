
package com.example.my_notes_record;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CRUD {
    SQLiteOpenHelper dbHandler; // SQLiteOpenHelper 实例用于处理数据库连接
    SQLiteDatabase db; // SQLiteDatabase 实例用于执行数据库操作

    // 定义数据库表的列名
    private static final String[] columns = {
            NoteDatabase.ID,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME
    };

    // 构造方法，接受上下文参数
    public CRUD(Context context) {
        dbHandler = new NoteDatabase(context); // 初始化数据库处理器
    }

    // 打开数据库连接
    public void open() {
        db = dbHandler.getWritableDatabase(); // 获取可写的数据库连接
    }

    // 关闭数据库连接
    public void close() {
        dbHandler.close(); // 关闭数据库处理器
    }

    // 添加一条笔记记录
    public Note addNote(Note note) {
        ContentValues contentValues = new ContentValues(); // 创建一个用于存储数据的 ContentValues 对象
        contentValues.put(NoteDatabase.CONTENT, note.getContent()); // 添加内容
        contentValues.put(NoteDatabase.TIME, note.getTime()); // 添加时间
        long insertId = db.insert(NoteDatabase.TABLE_NAME, null, contentValues); // 将数据插入数据库
        note.setId(insertId); // 将插入后的 ID 设置到笔记对象中
        return note; // 返回包含新数据的笔记对象
    }

    public List<Note> getAllNotes() {

        Cursor cursor = db.query(
                NoteDatabase.TABLE_NAME,  // 表名
                columns,                // 要查询的列（在这里是ID、内容、时间）
                null,                   // 查询条件（null表示无特殊条件）
                null,                   // 查询条件参数（null表示无特殊条件）
                null,                   // 分组方式（null表示不分组）
                null,                   // 过滤方式（null表示不过滤）
                null                    // 排序方式（null表示不排序）
        );

        List<Note> notes = new ArrayList<>(); // 创建一个笔记列表用于存储查询结果
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Note note = new Note(); // 创建笔记对象
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID))); // 设置 ID
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT))); // 设置内容
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME))); // 设置时间
                notes.add(note); // 将笔记对象添加到列表中
            }
        }
        cursor.close(); // 关闭游标
        return notes; // 返回包含所有笔记记录的列表
    }

}
