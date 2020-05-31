package com.example.task.ui.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;
import com.example.task.ui.Task;

import java.util.ArrayList;

public class FireStoreAdapter extends RecyclerView.Adapter<FireStoreAdapter.FireStoreViewHolder> {
    private ArrayList<Task> list;
    public FireStoreAdapter(ArrayList<Task> list){
    this.list=list;
}

    @NonNull
    @Override
    public FireStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task,parent,false);
        return new FireStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FireStoreViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class FireStoreViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public FireStoreViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.textTitle);


        }
        public void bind(Task s) {
        textView.setText(s.getTitle());
        }
    }

    }

