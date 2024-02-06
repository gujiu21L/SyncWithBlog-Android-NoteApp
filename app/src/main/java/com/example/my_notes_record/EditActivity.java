package com.example.my_notes_record;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    // 编辑文本的控件
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        // 初始化编辑文本控件
        editText = findViewById(R.id.et);
    }

    // 处理按键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            // 如果按下 HOME 键，则拦截事件，不做任何操作
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 如果按下返回键，则将编辑文本的内容返回给调用者，并设置返回结果为 RESULT_OK
            Intent intent = new Intent();
            intent.putExtra("input", editText.getText().toString());
            setResult(RESULT_OK, intent);
            finish(); // 关闭当前 Activity
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
