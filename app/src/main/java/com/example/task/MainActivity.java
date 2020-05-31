package com.example.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.task.login.PhoneActivity;
import com.example.task.ui.OnClickItem;
import com.example.task.ui.ProfilesActivity;
import com.example.task.ui.Task;
import com.example.task.ui.User;
import com.example.task.ui.home.DeleteAlert;
import com.example.task.ui.home.HomeFragment;
import com.example.task.ui.onBoard.OnBoardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//3. Вывести название файлов в recyclerView в GalleryFragment

    private AppBarConfiguration mAppBarConfiguration;
    Task task;
    ArrayList<Task> lists = new ArrayList<>();
    int pos;
    private ImageView imageView;
    private TextView texHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isShow()) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
//        if (FirebaseAuth.getInstance().getCurrentUser() == null){
//            startActivity(new Intent(this, PhoneActivity.class));
//            finish();
//            return;
//        }
        setHeader();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, FormActivity.class), 42);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_fire_store)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View view=navigationView.getHeaderView(0);
        texHeader = view.findViewById(R.id.text_header);
        imageView = view.findViewById(R.id.imageView_header);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                saveIsShow();
                finish();
                return true;
            case R.id.action_sort:
                sort();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean isShow() {
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        return preferences.getBoolean("isShow", false);

    }

    public void saveIsShow() {
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShow", false).apply();
    }

    public void startProfActivity(View view) {
        startActivity(new Intent(MainActivity.this, ProfilesActivity.class));
    }

    private boolean flag;

    public void sort() {
        if (flag) {
            Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            ((HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).sortList();
            flag = false;
        } else {
            Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            ((HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).initList();
            flag = true;
        }
    }

    public void setHeader() {
        FirebaseFirestore.getInstance().collection("user")
                .document("BexUsersId")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            texHeader.setText(user.getName());
                            setImage(user.getAvatar());

                        }
                    }
                });

    }

    private void setImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(imageView);

    }


}