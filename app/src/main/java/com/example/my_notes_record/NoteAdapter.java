package com.example.my_notes_record;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class NoteAdapter extends BaseAdapter {
    public interface OnNoteItemClickListener {
        void onNoteItemClick(long noteId);
    }
    public interface OnNoteItemLongClickListener {
        void onNoteItemLongClick(long noteId);
    }
    private Context context;
    private List<Note> noteList;
    private OnNoteItemClickListener onNoteItemClickListener;
    private OnNoteItemLongClickListener onNoteItemLongClickListener;

    // 默认构造函数
    public NoteAdapter(){
    }

    // 带参数的构造函数，接受上下文和笔记列表
    public NoteAdapter(Context Context,List<Note> noteList){
        this.context=Context;
        this.noteList=noteList;
    }

    public NoteAdapter(Context context, List<Note> noteList, OnNoteItemClickListener onNoteItemClickListener) {
        this.context = context;
        this.noteList = noteList;
        this.onNoteItemClickListener = onNoteItemClickListener;
    }
    public NoteAdapter(Context context, List<Note> noteList, OnNoteItemClickListener listener, OnNoteItemLongClickListener longClickListener) {
        this.context = context;
        this.noteList = noteList;
        this.onNoteItemClickListener = listener;
        this.onNoteItemLongClickListener = longClickListener;
    }
    // 获取列表项数量
    @Override
    public int getCount() {
        return noteList.size();
    }

    // 获取指定位置的笔记对象
    @Override
    public Object getItem(int position){
        return noteList.get(position);
    }

    // 获取指定位置的笔记ID
    @Override
    public long getItemId(int position){
        return position;
    }

    // 创建并返回每个列表项的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 从XML布局文件实例化视图
        View view = View.inflate(context, R.layout.note_list_item, null);
        // 获取布局中的TextView控件
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);

        // 从笔记对象中获取内容和时间信息
        String allText = noteList.get(position).getContent();

        // 设置TextView的文本内容
        tv_content.setText(allText.split("\n")[0]);
        tv_time.setText(noteList.get(position).getTime());

        // 将笔记ID作为视图的标签
        view.setTag(noteList.get(position).getId());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 触发笔记项点击事件
                if (onNoteItemClickListener != null) {
                    onNoteItemClickListener.onNoteItemClick(noteList.get(position).getId());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 触发笔记项长按事件
                if (onNoteItemLongClickListener != null) {
                    onNoteItemLongClickListener.onNoteItemLongClick(noteList.get(position).getId());
                }
                return true; // 消耗长按事件
            }
        });

        return view;
    }
}