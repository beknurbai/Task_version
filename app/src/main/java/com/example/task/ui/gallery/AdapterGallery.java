package com.example.task.ui.gallery;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.Task;
import com.example.task.ui.home.ViewHolder;

import java.util.ArrayList;

public class AdapterGallery extends RecyclerView.Adapter<ViewHolderGallery> {
private ArrayList<String> list;
private OnClickItem onClickItem;

    public AdapterGallery(ArrayList<String> list) {

        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolderGallery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task,parent,false);
        return new ViewHolderGallery(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGallery holder, int position) {
        holder.bind(list.get(position));
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

}
