package com.example.task.ui.onBoard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.task.MainActivity;
import com.example.task.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
Button button;

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
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text=view.findViewById(R.id.text_fragment);
       ImageView image=view.findViewById(R.id.image_fragment);
       LinearLayout layout=view.findViewById(R.id.linear);
       button=view.findViewById(R.id.btn_get_started);


       button.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v) {
               saveIsShow();
               Intent intent=new Intent(getContext(), MainActivity.class);
               startActivity(intent);
               getActivity().finish();
           }
       });

        int pos=getArguments().getInt("pos");
        switch (pos){
            case 0:
                layout.setBackgroundColor(getResources().getColor(R.color.back1));
                image.setImageResource(R.drawable.images);
                text.setText(" Добро пожаловать! ");
                button.setVisibility(View.INVISIBLE);
                break;
            case 1:
                layout.setBackgroundColor(getResources().getColor(R.color.back2));
                image.setImageResource(R.drawable.download);
                text.setText("Рады приветсвовать ");
                button.setVisibility(View.INVISIBLE);
                break;
            case 2:
                layout.setBackgroundColor(getResources().getColor(R.color.back3));
                image.setImageResource(R.drawable.fixic);
                text.setText("Начните сейчас ");
                break;
        }

    }public void saveIsShow(){
        SharedPreferences preferences=getActivity()
                .getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShow",true).apply();

    }
}
