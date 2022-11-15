package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;
    EditText name,email,password;
    Button registerbtn;
    TextView nameerr, emailerr, passerr;
    ImageView back;
    RelativeLayout prgbar;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Register.this, LoginRegister.class);
        startActivity(intent);
        Animatoo.animateSlideRight(Register.this);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back=(ImageView) findViewById(R.id.reg);
        prgbar= (RelativeLayout) findViewById(R.id.loaderrev);
        nameerr=(TextView) findViewById(R.id.nameerr_reg);
        emailerr=(TextView) findViewById(R.id.emailerr_reg);
        passerr=(TextView) findViewById(R.id.passerr_reg);
        name=(EditText) findViewById(R.id.Name_reg);
        email=(EditText) findViewById(R.id.Email_reg);
        password=(EditText) findViewById(R.id.Password_reg);
        registerbtn=(Button) findViewById(R.id.RegisterBtn_reg);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,LoginRegister.class);
                startActivity(intent);
                Animatoo.animateSlideRight(Register.this);
                finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myName = name.getText().toString().trim();
                String myEmail = email.getText().toString();
                String myPass = password.getText().toString();

                registerbtn.setVisibility(View.INVISIBLE);
                prgbar.setVisibility(View.VISIBLE);



                if (myName.matches("")){
                    nameerr.setText("Please Enter Name");
                    registerbtn.setVisibility(View.VISIBLE);
                }
                if (myEmail.matches("")){
                    emailerr.setText("Please Enter Email");
                    registerbtn.setVisibility(View.VISIBLE);
                }
                if (myPass.matches("")){
                    passerr.setText("Please Enter Your Password");
                    registerbtn.setVisibility(View.VISIBLE);
                }
                if (myPass.length() < 8){
                    nameerr.setText("Password Must Be 8 Characters");
                    registerbtn.setVisibility(View.VISIBLE);
                }
                else {
                    auth.getInstance().fetchSignInMethodsForEmail(myEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser== false){
                                emailerr.setText("Email Already Exists");
                                nameerr.setText("");
                                passerr.setText("");
                            }

                            else {
                                auth.getInstance().createUserWithEmailAndPassword(myEmail,myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @SuppressLint("ResourceAsColor")
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()){

                                            prgbar.setVisibility(View.GONE);
                                            registerbtn.setVisibility(View.VISIBLE);
                                            registerbtn.setBackgroundColor(R.color.green);
                                            registerbtn.setText("User Created");


                                            auth.getInstance().getCurrentUser().sendEmailVerification();

                                            String id = auth.getInstance().getCurrentUser().getUid();

                                            UserHelperClass helperClass = new UserHelperClass(myName,myEmail);


                                            ref=database.getInstance("https://foodapp-4520c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserData");
                                            ref.child(id).setValue(helperClass);

//                                            gettersetter gt= new gettersetter();
//                                            gt.setUser_Name(myName);
//                                            gt.setUser_Email(myEmail);
//                                            ref.setValue(gt).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            });

                                            nameerr.setText("");
                                            emailerr.setText("");
                                            passerr.setText("");

                                        }else {
                                            Toast.makeText(Register.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }




                }
        });

    }
}