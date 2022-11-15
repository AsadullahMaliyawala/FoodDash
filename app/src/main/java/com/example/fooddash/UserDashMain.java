package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class UserDashMain extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth auth;
    TextView txt;
    FirebaseStorage storage;
    ImageView profile;
    StorageReference storageReference;
    RelativeLayout familtbtn,eventbtn,todo,showevents,showfriends,invites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_main);

        txt=(TextView)findViewById(R.id.dashboardtxt2);
        familtbtn=(RelativeLayout)findViewById(R.id.family_btn);
        eventbtn=(RelativeLayout)findViewById(R.id.event_btn);
        todo=(RelativeLayout)findViewById(R.id.todo_list);
        showevents=(RelativeLayout)findViewById(R.id.show_events);
        showfriends=(RelativeLayout)findViewById(R.id.family_show);
        invites=(RelativeLayout)findViewById(R.id.invites);


        String id = auth.getInstance().getCurrentUser().getUid();
        ref=database.getInstance("https://foodapp-4520c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserData");
        storageReference=FirebaseStorage.getInstance().getReference("images/"+id+".jpg");
        String idstring = id.toString();

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String value = extras.getString("KEY");
            profile.setImageURI(Uri.parse(value));

        }



        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass us = snapshot.getValue(UserHelperClass.class);
                ProfileHelperClass ps = snapshot.getValue(ProfileHelperClass.class);

                if (us != null){
                    txt.setText("Welcome, "+ us.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}