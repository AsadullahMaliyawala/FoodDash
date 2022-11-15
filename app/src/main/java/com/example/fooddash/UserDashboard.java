package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UserDashboard extends AppCompatActivity {


    public Uri imageUri;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth auth;
    TextView txt,preferr;
    RelativeLayout progrss;
    FirebaseStorage storage;
    StorageReference storageReference;
    EditText pref,familyname;
    ImageView profile_pic;
    Button btn_done;


    public void onBackPressed() {
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        progrss= (RelativeLayout) findViewById(R.id.loaderrevdashboard);
        btn_done=(Button)findViewById(R.id.complete_btn);
        preferr=(TextView) findViewById(R.id.preferr1);
        pref=(EditText)findViewById(R.id.foodpref);
        familyname=(EditText)findViewById(R.id.familyname);
        txt=(TextView)findViewById(R.id.dashboardtxt);

        String id = auth.getInstance().getCurrentUser().getUid();
        ref=database.getInstance("https://foodapp-4520c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserData");

        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass us = snapshot.getValue(UserHelperClass.class);
                if (us != null){
                    txt.setText("Welcome, "+ us.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myPref = pref.getText().toString().toUpperCase();
                String myfamily = familyname.getText().toString().toUpperCase();
                String link = "images/"+id;

                if (myPref.matches("") && myfamily.matches("")){
                    preferr.setVisibility(View.VISIBLE);
                    preferr.setText("Please Enter Preference");
                }
                if (myPref.length()>1 && myfamily.length()>1){
                    preferr.setVisibility(View.INVISIBLE);
                    preferr.setText("Please Enter Preference");
                }

                ProfileHelperClass helperClass = new ProfileHelperClass(myPref,myfamily,link);


                ref=database.getInstance("https://foodapp-4520c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserData");
                ref.child(id+"profile").setValue(helperClass);

                Intent intent=new Intent(UserDashboard.this,UserDashMain.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(UserDashboard.this);
                finish();
            }
        });

    }

}