package com.example.fooddash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email,password;
    FirebaseDatabase database;
    DatabaseReference ref;
    RelativeLayout load;
    Button loginbtn;
    TextView  emailerr, passerr, forgot;
    ImageView back;


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Login.this, LoginRegister.class);
        startActivity(intent);
        Animatoo.animateSlideRight(Login.this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back=(ImageView) findViewById(R.id.lgn);
        load=(RelativeLayout) findViewById(R.id.loaderrevlogin);
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
                Animatoo.animateSlideLeft(Login.this);
                finish();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,LoginRegister.class);
                startActivity(intent);
                Animatoo.animateSlideRight(Login.this);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myEmail = email.getText().toString().trim();
                String myPass = password.getText().toString().trim();
                load.setVisibility(View.VISIBLE);
                loginbtn.setVisibility(View.INVISIBLE);


                if (myEmail.matches("")){
                    load.setVisibility(View.INVISIBLE);
                    loginbtn.setVisibility(View.VISIBLE);

                    emailerr.setText("Please Enter Email");
                }
                if (myPass.matches("")){
                    load.setVisibility(View.INVISIBLE);
                    loginbtn.setVisibility(View.VISIBLE);

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
                                            load.setVisibility(View.INVISIBLE);

                                            String id = auth.getInstance().getCurrentUser().getUid();
                                            ref=database.getInstance("https://foodapp-4520c-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("UserData");
                                            ref.child(id+"profile").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    ProfileHelperClass ps = snapshot.getValue(ProfileHelperClass.class);
                                                    if (ps != null){
                                                        Intent intent=new Intent(Login.this,UserDashMain.class);
                                                        startActivity(intent);
                                                        Animatoo.animateSlideLeft(Login.this);
                                                        finish();
                                                    }else{

                                                        Intent intent=new Intent(Login.this,UserDashboard.class);
                                                        startActivity(intent);
                                                        Animatoo.animateSlideLeft(Login.this);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

//                                            Intent intent=new Intent(Login.this,UserDashboard.class);
//                                            startActivity(intent);
//                                            Animatoo.animateSlideLeft(Login.this);
//                                            finish();
                                            emailerr.setText("");
                                            passerr.setText("");

                                        } else {
                                            emailerr.setText("Wrong Credentials");
                                            load.setVisibility(View.INVISIBLE);
                                            loginbtn.setVisibility(View.VISIBLE);
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

