package com.example.my_notes_record;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // 日志标签
    final String TAG ="孤酒21L";

    // 悬浮按钮
    FloatingActionButton btn;

    // 用于启动 EditActivity 的 ActivityResultLauncher
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // 初始化悬浮按钮
        btn = findViewById(R.id.floatingActionButton4);

        // 注册用于处理ActivityResult的Launcher
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // 处理从 EditActivity 返回的数据
                        Intent data = result.getData();
                        if (data != null) {
                            // 获取 EditActivity 返回的文本
                            String edit = data.getStringExtra("input");
                            Log.d(TAG, edit);
                            textView.setText(edit);
                        }
                    }
                }
        );

        // 设置悬浮按钮的点击事件监听器
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动 EditActivity，并等待结果
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                someActivityResultLauncher.launch(intent);
            }
        });
    }
}
