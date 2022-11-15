package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Forgot extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email;
    Button reset;
    TextView txtvw;
    ImageView back;

    private static int SPACE_TIME =2000;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Forgot.this, Login.class);
        startActivity(intent);
        Animatoo.animateSlideRight(Forgot.this);
        onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        email=(EditText) findViewById(R.id.Email_frgt);
        reset=(Button) findViewById(R.id.resetpass);
        back=(ImageView) findViewById(R.id.frgt);
        txtvw=(TextView) findViewById(R.id.txtvw);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Forgot.this,Login.class);
                startActivity(intent);
                Animatoo.animateSlideRight(Forgot.this);
                finish();
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                String myEmail = email.getText().toString();
                if (myEmail.matches("")){
                    txtvw.setText("Please Enter Email");
                    txtvw.setTextColor(R.color.red);
                }else{
                    auth.getInstance().fetchSignInMethodsForEmail(myEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser== false){
                                auth.getInstance().sendPasswordResetEmail(myEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("ResourceAsColor")
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            reset.setText("Email Sent");
                                            reset.setBackgroundColor(R.color.green);
                                        }
                                    }
                                });


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(Forgot.this,LoginRegister.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },SPACE_TIME);
                            }
                            else {
                                txtvw.setText("Email Not Registered");
                                txtvw.setTextColor(R.color.red);
                            }
                        }
                    });
                }




            }
        });






    }
}