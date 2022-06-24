package com.mibugi.share;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    private Boolean bPwdSwitch1=false;
    private Boolean bPwdSwitch2=false;
    private ImageView switch1;
    private ImageView switch2;
    private EditText etPwd1;
    private EditText etPwd2;
    private TextView eta;
    private Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        switch1=findViewById(R.id.cp_pwd_switch);
        switch2=findViewById(R.id.cp_pwd_switch1);
        etPwd1=findViewById(R.id.cp_pwd);
        etPwd2=findViewById(R.id.cp_cpwd);
        change=findViewById(R.id.cp_changepassword);
        eta=findViewById(R.id.cp_username);

        Intent intent=getIntent();
        String username=intent.getStringExtra(WodeActivity.name);
        eta.setText(username);
        switch1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bPwdSwitch1 = !bPwdSwitch1;
                if (bPwdSwitch1) {
                    switch1.setImageResource(
                            R.drawable.browse);
                    etPwd1.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    switch1.setImageResource(
                            R.drawable.not_visible);
                    etPwd1.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etPwd1.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        switch2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bPwdSwitch2 = !bPwdSwitch2;
                if (bPwdSwitch2) {
                    switch2.setImageResource(
                            R.drawable.browse);
                    etPwd2.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    switch2.setImageResource(
                            R.drawable.not_visible);
                    etPwd2.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etPwd2.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password1=etPwd1.getText().toString();
                String password2=etPwd2.getText().toString();
                Userservice userservice=new Userservice(ChangePasswordActivity.this);
                if (password1.equals(password2)){
                    User user=new User(username,password1);
                    userservice.changepas(user);
                    if (userservice.login(username,password1)){
                        Toast.makeText(ChangePasswordActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        intent.setClass(ChangePasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(ChangePasswordActivity.this, "修改失败，存在错误！", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "两次输入的密码不一致！\n请重新输入！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}