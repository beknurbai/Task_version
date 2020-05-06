package com.example.task.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.Task;

public class ViewHolder extends RecyclerView.ViewHolder {
TextView textTitle;
OnClickItem onClickItem;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textTitle=itemView.findViewById(R.id.textTitle);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onItemClick(getAdapterPosition());
            }
        });
    }

    public void bind(Task task) {
        textTitle.setText(task.getTitle());

    }
}
