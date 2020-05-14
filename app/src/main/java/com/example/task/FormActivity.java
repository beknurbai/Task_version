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
EditText editTitle,editDescription;
Task task;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            editTitle = findViewById(R.id.edit_task);
            editDescription = findViewById(R.id.edit_description);
            Button change =findViewById(R.id.save_change);
            Button save = findViewById(R.id.save_button);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = editTitle.getText().toString().trim();
                    String desc = editDescription.getText().toString().trim();
                    Task task = new Task(title, desc);
                    App.getInstance().getDatabase().taskDao().insert(task);
//                    Intent intent=new Intent();
//                    intent.putExtra("task",task);
//                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            if (getIntent().getSerializableExtra("result") != null) {
                task = (Task) getIntent().getSerializableExtra("result");
                editTitle.setText(task.getTitle());
                editDescription.setText(task.getDescription());
                save.setVisibility(View.GONE);
                change.setVisibility(View.VISIBLE);
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Task task = new Task();
                        if (getIntent().getSerializableExtra("sss") != null) {
                            Intent intent = getIntent();
                            Integer posit = intent.getIntExtra("sss", 1);
                            App.getInstance().getDatabase().taskDao().updateSalaryByIdList(posit, editTitle.getText().toString(), editDescription.getText().toString());
                            Log.d("pzd", "us " + posit.toString());
                            finish();
                        }

                    }
                });

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
            finish();
        return super.onOptionsItemSelected(item);
    }


}
