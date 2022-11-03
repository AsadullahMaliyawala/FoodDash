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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Register extends AppCompatActivity {

    FirebaseAuth auth;
    EditText name,email,password;
    Button registerbtn;
    TextView nameerr, emailerr, passerr;
    ImageView back;

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Register.this, LoginRegister.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back=(ImageView) findViewById(R.id.reg);
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
                finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myName = name.getText().toString();
                String myEmail = email.getText().toString();
                String myPass = password.getText().toString();

                if (myName.matches("")){
                    nameerr.setText("Please Enter Name");
                }
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
                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser== false){
                                emailerr.setText("Email Already Exists");
                                nameerr.setText("");
                                passerr.setText("");
                            }
                            else {
                                auth.getInstance().createUserWithEmailAndPassword(myEmail,myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()){
                                            nameerr.setText("");
                                            emailerr.setText("");
                                            passerr.setText("");

                                            auth.getInstance().getCurrentUser().sendEmailVerification();
                                            Toast.makeText(Register.this, "UserCreated", Toast.LENGTH_SHORT).show();
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