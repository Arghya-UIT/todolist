package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.database.MyDbHelper;
import com.example.todolist.database.TaskModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private ArrayList<TaskModel> taskList;
    private ArrayAdapter<TaskModel> adapter;
    private MyDbHelper dbHelper;
    private GestureDetector gestureDetector;
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

        gestureDetector = new GestureDetector(this, new SwipeGestureListener(contactListView));

        // Attach gesture detector to ListView
//        contactListView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
        addTaskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchContact();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }

    private void fetchContact() {
        taskList.clear();
        taskList.addAll(dbHelper.fetchTask());
        adapter.notifyDataSetChanged();
    }

    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private ListView listView;

        SwipeGestureListener(ListView listView) {
            this.listView = listView;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Detect leftward swipe
            if (e1.getX() - e2.getX() > 50) {
                int position = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
                if (position != ListView.INVALID_POSITION) {
                    TaskModel task = adapter.getItem(position);
                    taskList.remove(task);
                    adapter.notifyDataSetChanged();
                    // Perform archive action (remove task from the list)
                    Toast.makeText(WelcomeActivity.this, "swap detected", Toast.LENGTH_SHORT).show();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}

