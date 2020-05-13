package com.example.task.ui.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.App;
import com.example.task.FormActivity;
import com.example.task.R;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.Task;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    ArrayList<Task> list;
    OnClickItem onClick;
    Task task = new Task();

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.onClickItem = onClick;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.onClickItem = onClick;
        holder.setIsRecyclable(true);
        if(position %2== 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else if (position %2 == 1){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#626262"));
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,textDesc;
        OnClickItem onClickItem;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc=itemView.findViewById(R.id.textDes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FormActivity.class);
                    Task task = new Task();
                    task.setTitle(textTitle.getText().toString());
                    task.setDescription(textDesc.getText().toString());
                    intent.putExtra("result", task);
                    v.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());//Here I have to use v.getContext() istead of just cont.
                    alertDialog.setTitle("Delete file?");
                    alertDialog.setMessage("Are you sure you want to delete the file?");
                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    delete();

                                }
                            });
                    alertDialog.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    alertDialog.show();

                    return false;
                }
            });
        }


        public void bind(Task task) {
            textTitle.setText(task.getTitle());

        }

        public void delete() {
            int ID = list.get(getAdapterPosition()).getId();
            task.setId(ID);
            App.getInstance().getDatabase().taskDao().delete(task);
            Log.e("olol", "delete");
        }
        public void update(){
int Id=list.get(getAdapterPosition()).getId();
task.setId(Id);
App.getInstance().getDatabase().taskDao().update(task);
        }
    }
}


