package com.example.my_notes_record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


// 创建名为 "MainActivity" 的主活动类
public class MainActivity extends AppCompatActivity {

    private Context context = this; // 上下文对象，用于数据库操作
    private NoteDatabase dbHelper; // 数据库帮助类
    private NoteAdapter adapter; // 笔记适配器
    private List<Note> noteList = new ArrayList<>(); // 笔记列表
    private FloatingActionButton btn; // 悬浮按钮
    private ListView lv; // 列表视图

    // 定义一个 ActivityResultLauncher，用于处理其他活动的结果
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //调用父类的 onCreate 方法，用于执行一些初始化操作
        //savedInstanceState 参数用于恢复之前的状态

        setContentView(R.layout.activity_main);//设置当前 Activity 的布局

        btn = findViewById(R.id.floatingActionButton); // 悬浮按钮

        lv = findViewById(R.id.lv); // 列表视图，用于显示数据列表

        adapter = new NoteAdapter(getApplicationContext(), noteList);//初始化一个笔记适配器，并将应用的上下文对象和笔记列表传递给适配器

        refreshListView(); // 刷新笔记列表

        lv.setAdapter(adapter); // 将适配器与列表视图关联，从而显示笔记列表中的数据在界面上

        // 初始化 ActivityResultLauncher，用于处理启动其他活动的结果
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // 从 EditActivity 返回的内容和时间
                            String content = data.getStringExtra("content");
                            String time = data.getStringExtra("time");
                            Note note = new Note(content, time); // 创建笔记对象

                            // 打开数据库连接，将笔记添加到数据库
                            CRUD op = new CRUD(context);
                            op.open();
                            op.addNote(note);
                            op.close(); // 关闭数据库连接

                            refreshListView(); // 刷新笔记列表
                        }
                    }
                }
        );

        // 设置悬浮按钮的点击事件监听器
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动 EditActivity 并等待结果
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });
    }

    // 刷新笔记列表
    public void refreshListView() {
        // 创建数据库操作对象，打开数据库连接
        CRUD op = new CRUD(context);
        op.open();

        if (noteList.size() > 0) noteList.clear(); // 清空笔记列表
        noteList.addAll(op.getAllNotes()); // 获取数据库中所有笔记
        op.close(); // 关闭数据库连接

        adapter.notifyDataSetChanged(); // 通知适配器数据已更改，刷新列表视图
    }
}
