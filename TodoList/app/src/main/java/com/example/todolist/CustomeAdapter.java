package com.example.todolist;

import android.content.Context;
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

    public CustomeAdapter(Context context, List<TaskModel> contactList) {
        super(context, R.layout.custome_adapter_design, contactList);
        this.taskList = contactList;
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

        // Set the default image


        return view;
    }
}
