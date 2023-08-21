package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.database.MyDbHelper;
import com.example.todolist.database.TaskModel;

import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        TextView getDate = findViewById(R.id.getDate);
        TextView getTime = findViewById(R.id.getTime);
        TextView cancelBtn = findViewById(R.id.cancelBtn);
        TextView saveBtn = findViewById(R.id.saveBtn);
        CheckBox highPriority = findViewById(R.id.highPriority);
        EditText getTitle = findViewById(R.id.getTitle);
        EditText getDescription = findViewById(R.id.getDescription);



        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
//                String priority ;
//                if(isHighPriority==true){
//                    priority="1";
//                }else{
//                    priority="0";
//                }

                // Get the selected date and time from the TextViews
                String selectedDate = getDate.getText().toString(); // Assuming you update the TextView when selecting a date
                String selectedTime = getTime.getText().toString(); // Assuming you update the TextView when selecting a time

                TaskModel taskModel = new TaskModel();
                taskModel.setTitle(title);
                taskModel.setDescription(description);
                taskModel.setTime_for_store(selectedTime);
                taskModel.setDate_for_store(selectedDate);
                taskModel.setPriority(isHighPriority ? "1" : "0");


                MyDbHelper dbHelper = new MyDbHelper(AddTaskActivity.this);
                dbHelper.addTask(taskModel);
                setResult(RESULT_OK);
                finish();
                Log.d("sendin fron btn"," "+title+" "+description+" "+highPriority+" "+selectedDate+" "+selectedTime);
            }
        });

    }

    protected void showDatePickerDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dailouge_get_date, null);

        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int dayOfMonth = datePicker.getDayOfMonth();

                        // Here you can handle the selected date (year, month, dayOfMonth)
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        // Assuming you have a TextView for displaying the selected date
                        // textViewSelectedDate.setText(selectedDate);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    protected void showTimePickerDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dailouge_get_time, null);

        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int hour, minute;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                // Get AM/PM selection
                int amPm = hour >= 12 ? 1 : 0;

                // Convert to 12-hour format if needed
                if (hour > 12) {
                    hour -= 12;
                } else if (hour == 0) {
                    hour = 12;
                }

                // Handle the selected time (hour, minute, amPm) here
                String amPmText = amPm == 1 ? "PM" : "AM";
                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, amPmText);
                // Assuming you have a TextView for displaying the selected time

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
