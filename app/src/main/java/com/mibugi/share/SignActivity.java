package com.mibugi.share;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class SignActivity extends AppCompatActivity implements View.OnClickListener{
    private Boolean bPwdSwitch1=false;
    private Boolean bPwdSwitch2=false;
    private EditText etPwd1;
    private EditText etPwd2;
    private EditText etAccount;
    private ImageView switch1;
    private ImageView switch2;
    private Button button;
    private ImageView back1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        etAccount=findViewById(R.id.sn_account);
        etPwd1=findViewById(R.id.sn_pwd);
        etPwd2=findViewById(R.id.sn_cpwd);
        switch1=findViewById(R.id.sn_pwd_switch);
        switch2=findViewById(R.id.sn_pwd_switch1);
        button=findViewById(R.id.bt_sign);
        back1=findViewById(R.id.sn_back);
        switch1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bPwdSwitch1 = !bPwdSwitch1;
                if (bPwdSwitch1) {
                    switch1.setImageResource(
                            R.drawable.ic_baseline_visibility_24);
                    etPwd1.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    switch1.setImageResource(
                            R.drawable.ic_baseline_visibility_off_24);
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
                            R.drawable.ic_baseline_visibility_24);
                    etPwd2.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    switch2.setImageResource(
                            R.drawable.ic_baseline_visibility_off_24);
                    etPwd2.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etPwd2.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=etAccount.getText().toString();
                String password1=etPwd1.getText().toString();
                String password2=etPwd2.getText().toString();
                //调用方法写入数据库，自定义一个默认的头像，在个人中心可以自行更换
                User user=new User(username,password1);
                Userservice userservice1=new Userservice(SignActivity.this);
                //

                if (password1.equals(password2)){
                    //if()判断数据库中是否存在同一个用户名
                    if (userservice1.login1(username))
                    {
                        Toast.makeText(SignActivity.this, "存在相同的用户名！！！", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //将drawable中的图片转换为bitmap类型
                        Resources res=SignActivity.this.getResources();
                        Bitmap bmp= BitmapFactory.decodeResource(res,R.drawable.default_head);
                        //将bitmap图片转为byte类型
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG,100,baos);
                        byte[] result=baos.toByteArray();
                        userservice1.register(user,result);
                        if (userservice1.login1(username))
                        {
                            Toast.makeText(SignActivity.this, "注册成功！！！", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.setClass(SignActivity.this, com.mibugi.share.LoginActivity.class);
                            startActivity(intent);
                        }
                        else Toast.makeText(SignActivity.this, "注册不成功！！！存在错误", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignActivity.this, "两次输入的密码不一致！！！\n请重新输入！！！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(SignActivity.this, com.mibugi.share.LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}