package com.example.my_notes_record;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity{

    private String content; // 声明用于存储笔记内容的变量
    private String time; // 声明用于存储笔记时间的变量

    EditText et; // 声明文本编辑框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout); // 设置视图为 "edit_layout"
        et = findViewById(R.id.et); // 从布局文件中获取文本编辑框
    }

    // 处理按键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true; // 如果按下 HOME 键，返回 true，表示事件已处理
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(); // 创建一个新意图
            intent.putExtra("content", et.getText().toString()); // 将编辑框中的文本内容放入意图中
            intent.putExtra("time", dateToString()); // 将当前时间放入意图中
            setResult(RESULT_OK, intent); // 设置结果为 OK，并附带意图
            finish(); // 结束当前活动
            return true; // 返回 true，表示事件已处理
        }
        return super.onKeyDown(keyCode, event); // 如果按键不是 HOME 或 BACK，使用默认处理
    }

    // 将当前时间格式化为字符串
    public String dateToString(){
        Date date = new Date(); // 获取当前日期和时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 创建日期格式化器
        return simpleDateFormat.format(date); // 格式化日期并返回字符串
    }
}
