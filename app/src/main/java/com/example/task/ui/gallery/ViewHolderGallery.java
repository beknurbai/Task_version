package com.example.task.ui.gallery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.Task;

public class ViewHolderGallery extends RecyclerView.ViewHolder {
TextView textTitle;
OnClickItem onClickItem;

    public ViewHolderGallery(@NonNull View itemView) {
        super(itemView);
        textTitle=itemView.findViewById(R.id.textTitle);

    }

    public void bind(String task) {
        textTitle.setText(task);

    }
}
