package com.example.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.task.ui.Task;
import com.example.task.ui.home.HomeFragment;

public class FormActivity extends AppCompatActivity {
EditText editTitle,editDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if (getSupportActionBar()!=null){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTitle=findViewById(R.id.edit_task);
        editDescription=findViewById(R.id.edit_description);
            Button save=findViewById(R.id.save_button);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title=editTitle.getText().toString().trim();
                    String desc=editDescription.getText().toString().trim();
                    Task task=new Task(title,desc);
                    Intent intent=new Intent();
                    intent.putExtra("task",task);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

            finish();
        return super.onOptionsItemSelected(item);
    }

}
