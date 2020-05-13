package com.example.task.ui.gallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;

import java.io.File;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

private AdapterGallery adapter;
    private ArrayList<String> lists =new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView=view.findViewById(R.id.recycler_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new AdapterGallery(lists);
        recyclerView.setAdapter(adapter);
    getPermission();
    }
    public void getPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            listsGallery();
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},101
            );
        }

    }
    private void listsGallery(){
        File folder=new File(Environment.getExternalStorageDirectory(),"DCIM/Camera ");
        for (File file:folder.listFiles()){
            lists.add(file.toString());
        }
    }
}
