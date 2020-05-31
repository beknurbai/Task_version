package com.example.task.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.task.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.grpc.internal.LogExceptionRunnable;

public class ProfilesActivity extends AppCompatActivity {
    private EditText editName;
    private Button btnSave;
    private ImageView imageViewProfile;
    private Bitmap bitmap;
    private static final int SELECT_IMAGE = 11;
    String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        btnSave = findViewById(R.id.btn_save);
        imageViewProfile = findViewById(R.id.img_profile);
        imageViewProfile.setImageResource(R.drawable.default_profile_image);
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
                            showImage(user.getAvatar());

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

    private void showImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(imageViewProfile);
    }

    public void onClick(View view) {
        String uid = FirebaseAuth.getInstance().getUid();
        String name = editName.getText().toString().trim();
        User user = new User(name, 18,avatarUrl);
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
            Glide.with(this).load(data.getData()).circleCrop().into(imageViewProfile);
            upload(data.getData());
        }
    }

    private void upload(Uri data) {
//        String uid = FirebaseAuth.getInstance().getUid();
        String uid = "BexUsersId";
        final StorageReference reference = FirebaseStorage
                .getInstance()
                .getReference()
                .child(uid + ".jpg");
        UploadTask uploadTask = reference.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUrl = task.getResult();
                    Log.e("Profile", "downloadUrl " + downloadUrl);
                    avatarUrl=downloadUrl.toString();
                    updateAvatar(downloadUrl);
                } else {
                    Toast.makeText(ProfilesActivity.this, "ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateAvatar(Uri downloadUrl) {
        String uid = "BexUsersId";
        FirebaseFirestore.getInstance()
                .collection("user")
                .document(uid)
                .update("avatar", downloadUrl.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfilesActivity.this, "успешно", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfilesActivity.this, "ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

