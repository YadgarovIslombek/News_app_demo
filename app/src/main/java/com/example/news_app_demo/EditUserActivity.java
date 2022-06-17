package com.example.news_app_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_app_demo.database.DatabaseAccess;
import com.example.news_app_demo.database.DatabaseOpenHelper;

import es.dmoral.toasty.Toasty;

public class EditUserActivity extends AppCompatActivity {
    EditText etxt_user_name,etxt_phone_number,etxt_user_password;
    TextView txt_edit_user,txt_update_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        getSupportActionBar().setTitle(R.string.edit_user);

        etxt_user_name=findViewById(R.id.etxt_user_name);
        etxt_phone_number=findViewById(R.id.etxt_phone_number);
        etxt_user_password=findViewById(R.id.etxt_user_password);

        //buttons
        txt_edit_user=findViewById(R.id.txt_edit_user);
        txt_update_user=findViewById(R.id.txt_update_user);

        String id=getIntent().getExtras().getString(DatabaseOpenHelper.USERS_ID);
        String userName=getIntent().getExtras().getString(DatabaseOpenHelper.USER_NAME);
        String userPhone=getIntent().getExtras().getString(DatabaseOpenHelper.USER_PHONE);
        String userPassword=getIntent().getExtras().getString(DatabaseOpenHelper.USER_PASSWORD);

        etxt_user_name.setText(userName);
        etxt_phone_number.setText(userPhone);
        etxt_user_password.setText(userPassword);
        //button
        txt_update_user.setVisibility(View.INVISIBLE);
        etxt_user_name.setEnabled(false);
        etxt_phone_number.setEnabled(false);
        etxt_user_password.setEnabled(false);
        txt_edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etxt_user_name.setEnabled(true);
                etxt_phone_number.setEnabled(true);
                etxt_user_password.setEnabled(true);
                txt_update_user.setVisibility(View.VISIBLE);
                etxt_user_name.setTextColor(Color.RED);
                etxt_phone_number.setTextColor(Color.RED);
                etxt_user_password.setTextColor(Color.RED);
                txt_edit_user.setVisibility(View.GONE);
            }
        });
        txt_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etxt_user_name.getText().toString().trim();
                String userPhone = etxt_phone_number.getText().toString().trim();
                String userPassword = etxt_user_password.getText().toString().trim();
                if (userName.isEmpty()) {
                    etxt_user_name.setError(getString(R.string.please_enter_user_name));
                    etxt_user_name.requestFocus();
                }
                else if (userPhone.isEmpty())
                {
                    etxt_phone_number.setError(getString(R.string.please_enter_phone_number));
                    etxt_phone_number.requestFocus();
                }
                else if (userPassword.isEmpty())
                {
                    etxt_user_password.setError(getString(R.string.please_enter_password));
                    etxt_user_password.requestFocus();
                }

                else {

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(EditUserActivity.this);
                    databaseAccess.open();

                    boolean check = databaseAccess.updateUser(id,userName,userPhone,userPassword);

                    if (check) {
                        Toasty.success(EditUserActivity.this, R.string.successfully_updated, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditUserActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    } else {

                        Toasty.error(EditUserActivity.this, R.string.user_already_exist_with_this_phone_number, Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}