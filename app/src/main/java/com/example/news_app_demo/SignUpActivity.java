package com.example.news_app_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_app_demo.database.DatabaseAccess;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {
    EditText etxtPhone, etxtPassword,etx_name;
    TextView txt_reg,login;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).hide();

        etxtPhone = findViewById(R.id.etxt_phone);
        etxtPassword = findViewById(R.id.etxt_password);
        etx_name = findViewById(R.id.etxt_name);
        login = findViewById(R.id.login);
        txt_reg = findViewById(R.id.txt_reg);
        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etx_name.getText().toString().trim();
                String phone = etxtPhone.getText().toString().trim();
                String password = etxtPassword.getText().toString().trim();
                addUser(name,phone,password);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginRegister.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addUser(String name, String phone, String password){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(SignUpActivity.this);
        databaseAccess.open();
        databaseAccess.addUser(name,phone,password);
        Intent intent1 = new Intent(SignUpActivity.this, LoginRegister.class);
        startActivity(intent1);

        Toasty.success(this, "R.string.login_successful", Toast.LENGTH_SHORT).show();

    }
}