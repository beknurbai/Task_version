package com.example.task.ui.onBoard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.task.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text=view.findViewById(R.id.text_fragment);
       ImageView image=view.findViewById(R.id.image_fragment);
        int pos=getArguments().getInt("pos");
        switch (pos){
            case 0:
                image.setImageResource(R.drawable.images);
                text.setText(" Добро пожаловать! ");
                break;
            case 1:
                image.setImageResource(R.drawable.download);
                text.setText("Рады приветсвовать ");
                break;
            case 2:
                image.setImageResource(R.drawable.fixic);
                text.setText("Начните сейчас ");
                break;
        }

    }
}
