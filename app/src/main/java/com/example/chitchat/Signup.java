package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.Reference;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    TextView loginbut;
    EditText rg_username;
    EditText rg_email;
    EditText rg_password, rg_repasword;
    Button rg_signup;
    CircleImageView rg_profileImages;
    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginbut = findViewById(R.id.logbutton);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_password = findViewById(R.id.rgpass);
        rg_repasword = findViewById(R.id.rgrepass);
        rg_signup = findViewById(R.id.rgSignup);
        rg_profileImages = findViewById(R.id.profilerg0);
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, login.class);
                startActivity(intent);
                finish();
            }
        });


        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee = rg_username.getText().toString();
                String emaill = rg_email.getText().toString();
                String password = rg_password.getText().toString();
                String cpassword = rg_repasword.getText().toString();
                String status = "Hey I'm using ChitChat!";



                if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {

                    Toast.makeText(Signup.this, "Please Enter Valid Information!", Toast.LENGTH_SHORT).show();
                } else if (!emaill.matches(emailPattern)) {

                    rg_email.setError("Enter A Valid Email!");
                } else if (password.length() < 6) {

                    rg_password.setError("Password Must Be More than 6 character!");
                } else if (!password.equals(cpassword)) {

                    rg_repasword.setError("Password Doesn't Match!");
                } else {
                    auth.createUserWithEmailAndPassword(emaill, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                DatabaseReference reference = database.getReference().child("user").child(id);
                                StorageReference storagerefrence = storage.getReference().child("Upload").child(id);
                                if (imageURI != null) {
                                    storagerefrence.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                storagerefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri = uri.toString();
                                                        Users users = new Users(id, namee, emaill, password, imageuri, status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    Intent intent = new Intent(Signup.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {

                                                                    Log.e("SignupActivity", "Error creating user", task.getException());
                                                                    Toast.makeText(Signup.this, "Error In Creating The User!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    String status = "Hey I'm using ChitChat!";
                                    imageuri = "https://firebasestorage.googleapis.com/v0/b/chitchat-26e36.appspot.com/o/man.png?alt=media&token=fb49abb7-e5bf-4051-94be-91a83849315f";
                                    Users users = new Users(id, namee, emaill, password, imageuri, status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(Signup.this, "Error In Creating The User!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }else{
                                Toast.makeText(Signup.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        rg_profileImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture."), 10);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                imageURI = data.getData();
                rg_profileImages.setImageURI(imageURI);
            }
        }
    }
}

