package com.example.task.ui.firestore;

import android.media.MediaCas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task.R;
import com.example.task.ui.Task;
import com.example.task.ui.User;
import com.example.task.ui.gallery.AdapterGallery;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FireStoreFragment extends Fragment {

    private FireStoreAdapter adapter;
    private ArrayList<Task> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fire_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_store);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FireStoreAdapter(list);
        recyclerView.setAdapter(adapter);
        listFromFB();

    }
    private void listFromFB(){
        String uid="qcEMqksAlKI0rlgvsdYp";
//        FirebaseFirestore.getInstance().
        FirebaseFirestore.getInstance().collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots!=null)
                list.addAll(queryDocumentSnapshots.toObjects(Task.class));
                adapter.notifyDataSetChanged();


            }
        });
    }
}
