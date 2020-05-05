package com.example.task.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<ViewHolder> {
private ArrayList<Task> list;
private OnClickItem onClickItem;

    public TaskAdapter(ArrayList<Task> list) {

        this.list = list;
    }
    public void update(ArrayList<Task> Task) {
        list = Task;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

}
