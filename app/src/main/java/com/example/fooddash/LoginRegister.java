package com.example.fooddash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class LoginRegister extends AppCompatActivity {

    Button btnlogin, btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        btnlogin = (Button) findViewById(R.id.LoginButton);
        btnregister = (Button) findViewById(R.id.RegisterBtn);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginRegister.this,Register.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(LoginRegister.this);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginRegister.this,Login.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(LoginRegister.this);
                finish();
            }
        });
    }
}