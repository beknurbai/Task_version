package com.example.task.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.App;
import com.example.task.FormActivity;
import com.example.task.MainActivity;
import com.example.task.R;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.Task;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements OnClickItem {

    private TaskAdapter adapter;
    Task task;
    private ArrayList<Task> lists = new ArrayList<>();
    LinearLayoutManager LayoutManagerTask;

    int pos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lists.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter = new TaskAdapter(lists);
        recyclerView.setAdapter(adapter);
        LayoutManagerTask = new LinearLayoutManager(getContext());
        LayoutManagerTask.setReverseLayout(true);
        LayoutManagerTask.setStackFromEnd(true);
        recyclerView.setLayoutManager(LayoutManagerTask);
        loadData();

    }

    private void loadData() {
        App.getInstance().getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                lists.clear();
                lists.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int pos) {
        if (pos != 0) {
            lists.remove(pos);
        }
    }

    @Override
    public void onItemLong(int pos) {
        DeleteAlert alert = new DeleteAlert();
        alert.show(getParentFragmentManager(), "delete");
    }

    public void sortList() {
        lists.clear();
        lists.addAll(App.getInstance().getDatabase().taskDao().sort());
        adapter.notifyDataSetChanged();
        LayoutManagerTask.setReverseLayout(false);
        LayoutManagerTask.setStackFromEnd(false);
    }

    public void initList() {
        lists.clear();
        lists.addAll(App.getInstance().getDatabase().taskDao().getAll());
        adapter.notifyDataSetChanged();
        LayoutManagerTask.setReverseLayout(true);
        LayoutManagerTask.setStackFromEnd(true);
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==42&&resultCode==RESULT_OK&&data!=null){
//            task= (Task) data.getSerializableExtra("task");
//            lists.add(pos,task);
//            adapter.update(lists);
//            adapter.notifyDataSetChanged();
//
//        }
//    }
}
