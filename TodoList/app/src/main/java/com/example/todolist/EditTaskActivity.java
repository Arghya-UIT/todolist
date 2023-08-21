package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todolist.database.MyDbHelper;
import com.example.todolist.database.TaskModel;

public class EditTaskActivity extends AddTaskActivity {
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittask);

        TextView getDate = findViewById(R.id.getDate);
        TextView getTime = findViewById(R.id.getTime);
        TextView cancelBtn = findViewById(R.id.cancelBtn);
        TextView saveBtn = findViewById(R.id.saveBtn);
        CheckBox highPriority = findViewById(R.id.highPriority);
        EditText getTitle = findViewById(R.id.getTitle);
        EditText getDescription = findViewById(R.id.getDescription);

        dbHelper = new MyDbHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            int taskId = intent.getIntExtra("task_id", -1);
            if (taskId != -1) {
                Log.d("getting here task id", " " + taskId);
                TaskModel task = dbHelper.fetchTaskById(taskId);
                if (task != null) {
                    getDate.setText(task.getDate_for_store());
                    getTime.setText(task.getTime_for_store());
                    getDescription.setText(task.getDescription());
                    getTitle.setText(task.getTitle());
                    if ("1".equals(task.getPriority())) {
                        highPriority.setChecked(true); // Set the CheckBox as checked
                    }
                }
            }
        }

        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getTitle.getText().toString();
                String description = getDescription.getText().toString();
                boolean isHighPriority = highPriority.isChecked();

                // Get the selected date and time from the TextViews
                String selectedDate = getDate.getText().toString(); // Assuming you update the TextView when selecting a date
                String selectedTime = getTime.getText().toString(); // Assuming you update the TextView when selecting a time

                TaskModel taskModel = new TaskModel();
                taskModel.setTitle(title);
                taskModel.setDescription(description);
                taskModel.setTime_for_store(selectedTime);
                taskModel.setDate_for_store(selectedDate);
                taskModel.setPriority(isHighPriority ? "1" : "0");


                MyDbHelper dbHelper = new MyDbHelper(EditTaskActivity.this);
                dbHelper.updateTask(taskModel,intent.getIntExtra("task_id", -1));

                setResult(RESULT_OK);
                finish();
                Log.d("sending from btn in edittask", " " + title + " " + description + " " + isHighPriority + " " + selectedDate + " " + selectedTime);

            }
        });

        // Rest of your implementation for the EditTaskActivity
    }
}
