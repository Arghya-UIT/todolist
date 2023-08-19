package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.database.MyDbHelper;
import com.example.todolist.database.TaskModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private ArrayList<TaskModel> taskList;
    private ArrayAdapter<TaskModel> adapter;
    private MyDbHelper dbHelper;
    private final int ADD_TASK_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FloatingActionButton addTaskbtn = findViewById(R.id.addTaskBtn);

        dbHelper = new MyDbHelper(this);
        taskList = new ArrayList<>();
        adapter = new CustomeAdapter(this, taskList);


        ListView contactListView = findViewById(R.id.displayNormalTasks);
        contactListView.setAdapter(adapter);

        fetchContact();
        addTaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        fetchContact();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            // Contact was successfully saved in NewContactActivity
            // You can update the contact list here if needed
            fetchContact();
            adapter.notifyDataSetChanged();
        }
    }
    private void fetchContact() {
        taskList.clear();
        taskList.addAll(dbHelper.fetchTask());
        adapter.notifyDataSetChanged();
    }
}
