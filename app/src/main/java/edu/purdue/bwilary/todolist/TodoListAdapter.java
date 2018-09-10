package edu.purdue.bwilary.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoListAdapter extends ArrayAdapter {
    private List<TodoItem> todoItemList;
    private Context context;
    public TodoListAdapter(@NonNull Context context, List<TodoItem> todoItemList) {
        super(context, R.layout.list_item_layout);
        this.todoItemList = todoItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_layout, parent, false);
        TodoItem todoTodoTodoItem = todoItemList.get(position);
        TextView note = (TextView) rowView.findViewById(R.id.item_note);
        TextView date = (TextView) rowView.findViewById(R.id.item_date);

        note.setText(todoTodoTodoItem.getNote());
        date.setText(todoTodoTodoItem.getDate());

        return rowView;
    }

    @Override
    public int getCount(){
        return todoItemList !=null ? todoItemList.size() : 0;
    }

    public void setData(List<TodoItem> todoTodoTodoItemList){
        this.todoItemList = todoTodoTodoItemList;
    }
}
