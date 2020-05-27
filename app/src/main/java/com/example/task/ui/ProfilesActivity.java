package com.example.task.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.task.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfilesActivity extends AppCompatActivity {
    private EditText editName;
    private Button btnSave;
    private ImageView imageViewProfile;
    private Bitmap bitmap;
    private static final int SELECT_IMAGE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        btnSave = findViewById(R.id.btn_save);
        imageViewProfile = findViewById(R.id.img_profile);
       getData2();
    }

    private void getData2() {
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("user")
                .document("BexUsersId")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    editName.setText(user.getName());
                }
            }
        });
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                        }
//                    }
//                });
//    }
    }

    public void onClick(View view) {
        String uid = FirebaseAuth.getInstance().getUid();
        String name = editName.getText().toString().trim();
        User user = new User(name, 18, null);
        FirebaseFirestore.getInstance().collection("user")
                .document("BexUsersId")
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfilesActivity.this, "Успешно", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(ProfilesActivity.this, "Ошибка", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    public void onProfileImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data != null) {

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            } catch (IOException ioE) {
                ioE.printStackTrace();
            }
            imageViewProfile.setImageBitmap(bitmap);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}

