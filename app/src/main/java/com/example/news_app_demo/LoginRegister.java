package com.example.news_app_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_app_demo.database.DatabaseAccess;
import com.example.news_app_demo.util.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class LoginRegister extends AppCompatActivity {

    EditText etxtPhone, etxtPassword;
    TextView txtLogin,signUp;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


        Objects.requireNonNull(getSupportActionBar()).hide();

        etxtPhone = findViewById(R.id.etxt_phone);
        etxtPassword = findViewById(R.id.etxt_password);
        txtLogin = findViewById(R.id.txt_login);
        signUp = findViewById(R.id.signUp);
        sp = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String phone = sp.getString(Constant.SP_PHONE, "");
        String password = sp.getString(Constant.SP_PASSWORD, "");


        etxtPhone.setText(phone);
        etxtPassword.setText(password);

        if (phone.length() >= 3 && password.length() >= 3) {
            login(phone, password);
        }


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etxtPhone.getText().toString().trim();
                String password = etxtPassword.getText().toString().trim();

                if (phone.isEmpty()) {
                    etxtPhone.setError("Введите номер телефона");
                    etxtPhone.requestFocus();
                } else if (password.isEmpty()) {
                    etxtPassword.setError("Введите парол");
                    etxtPassword.requestFocus();
                } else {
                    login(phone, password);
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginRegister.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void login(String phone, String password) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(LoginRegister.this);
        databaseAccess.open();
        List<HashMap<String, String>> userData;
        userData = databaseAccess.checkUser(phone, password);
        if (userData.isEmpty()) {
            Toasty.error(this, "Не найдено", Toasty.LENGTH_SHORT).show();
        } else {
            String userName = userData.get(0).get("user_name");
            String userType = userData.get(0).get("user_type");
            Log.d("TAG", "login: " + userName);
            Log.d("TAG", "login: " + userType);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString(Constant.SP_PHONE, phone);
            editor.putString(Constant.SP_PASSWORD, password);
            editor.putString(Constant.SP_USER_NAME, userName);
            editor.putString(Constant.SP_USER_TYPE, userType);
            editor.apply();

            Intent intent1 = new Intent(LoginRegister.this, MainActivity.class);
            startActivity(intent1);
            finish();

            //Toasty.success(this, "Успешно", Toast.LENGTH_SHORT).show();
        }

    }
}