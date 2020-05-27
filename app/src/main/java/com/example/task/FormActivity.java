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
import android.widget.Toast;

import com.example.task.ui.Task;
import com.example.task.ui.home.HomeFragment;
import com.example.task.ui.home.TaskAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
                String title = editTitle.getText().toString().trim();
                String desc = editDescription.getText().toString().trim();
                Task task = new Task(title, desc);
                App.getInstance().getDatabase().taskDao().insert(task);
            }
            String title = editTitle.getText().toString().trim();
            String desc = editDescription.getText().toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("title", title);
            map.put("desc", desc);
            FirebaseFirestore.getInstance().collection("tasks")
                    .add(map)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FormActivity.this, "Успешно", Toast.LENGTH_SHORT);
                            } else {
                                Toast.makeText(FormActivity.this, "Ошибка", Toast.LENGTH_SHORT);
                            }
                        }
                    });
            finish();
            }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}