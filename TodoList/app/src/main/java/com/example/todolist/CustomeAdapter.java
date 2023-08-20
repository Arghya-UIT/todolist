package com.example.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todolist.database.TaskModel;

import java.util.List;

public class CustomeAdapter extends ArrayAdapter<TaskModel> {
    private List<TaskModel> taskList;

    public CustomeAdapter(Context context, List<TaskModel> taskList) {
        super(context, R.layout.custome_adapter_design, taskList);
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.custome_adapter_design, parent, false);
        }

        ImageView doneMarker = view.findViewById(R.id.doneMarker);
        TextView titleText = view.findViewById(R.id.titleText);
        TextView showTime = view.findViewById(R.id.showTime);
        TextView showDate = view.findViewById(R.id.showDate);
        ImageView editBtn = view.findViewById(R.id.editBtn);
        ImageView priorityMarker = view.findViewById(R.id.highPriorityMarker);


        // Get the current contact from the list
        TaskModel task = taskList.get(position);

        Log.d("priority---marker"," "+task.getPriority());

        titleText.setText(task.getTitle());
        showTime.setText(task.getTime_for_store());
        showDate.setText(task.getDate_for_store());
        if ("1".equals(task.getPriority())) {
            priorityMarker.setImageResource(R.drawable.img);
        }


        return view;
    }
}
