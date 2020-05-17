package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.task.ui.Task;
import com.example.task.ui.home.HomeFragment;
import com.example.task.ui.home.TaskAdapter;

public class FormActivity extends AppCompatActivity {
    EditText editTitle, editDescription;
    Task task;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            editTitle = findViewById(R.id.edit_task);
            editDescription = findViewById(R.id.edit_description);
        }
            if (getIntent().getSerializableExtra("result") != null) {
                Task task = (Task) getIntent().getSerializableExtra("result");
                editTitle.setText(task.getTitle());
                editDescription.setText(task.getDescription());
                Button save = findViewById(R.id.save_button);
                save.setText("Update");
            }
        }

        public void onClick(View view) {
            if (getIntent().getSerializableExtra("result") != null) {
                Task editTask = new Task(editTitle.getText().toString(), editDescription.getText().toString());
                Integer position = getIntent().getIntExtra("sss", 1);
                App.getInstance().getDatabase().taskDao().updateSalaryByIdList(position,editTask.getTitle(), editTask.getDescription());
                finish();
            } else {
                String title = editTitle.getText().toString().trim();   // trim - убирание пробелов
                String desc = editDescription.getText().toString().trim();
                Task task = new Task(title, desc);
                App.getInstance().getDatabase().taskDao().insert(task);
            /*Intent intent = new Intent();
            intent.putExtra(TASK_KEY, task); •PREVIOUS VERSION…
            setResult(RESULT_OK, intent);*/
                finish();
            }
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}