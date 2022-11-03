package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email,password;
    Button loginbtn;
    TextView  emailerr, passerr, forgot;
    ImageView back;


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Login.this, LoginRegister.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back=(ImageView) findViewById(R.id.lgn);
        emailerr=(TextView) findViewById(R.id.emailerr_lgn);
        passerr=(TextView) findViewById(R.id.passerr_lgn);
        email=(EditText) findViewById(R.id.Email_lgn);
        password=(EditText) findViewById(R.id.Password_lgn);
        loginbtn=(Button) findViewById(R.id.LoginBtn_lgn);
        forgot = (Button) findViewById(R.id.forgot_pass);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Forgot.class);
                startActivity(intent);
                finish();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,LoginRegister.class);
                startActivity(intent);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myEmail = email.getText().toString();
                String myPass = password.getText().toString();

                if (myEmail.matches("")){
                    emailerr.setText("Please Enter Email");
                }
                if (myPass.matches("")){
                    passerr.setText("Please Enter Your Password");
                }
                else {
                    auth.getInstance().fetchSignInMethodsForEmail(myEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                auth.getInstance().signInWithEmailAndPassword(myEmail, myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            Intent intent=new Intent(Login.this,UserDashboard.class);
                                            startActivity(intent);
                                            finish();
                                            emailerr.setText("");
                                            passerr.setText("");

                                        } else {
                                            emailerr.setText("Wrong Credentials");
                                        }
                                    }
                                });
                            }
                    });

                }

            }
        });

    }
}

