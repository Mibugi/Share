package com.mibugi.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class WodeActivity extends AppCompatActivity {
    private Button logout;
    private ImageView wode_home;
    private ImageView wode_touxiang;
    private Button changepassword;
    private TextView zuopin;
    private TextView xihuan;
    private TextView wode_name;
    private ImageView wode_add;
    private String username;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    public static final String name="yonghumingchuandi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);
        logout=findViewById(R.id.logout);
        wode_home=findViewById(R.id.wode_home);
        wode_touxiang=findViewById(R.id.wode_touxiang);
        wode_add=findViewById(R.id.wode_add);
        changepassword=findViewById(R.id.modify);
        zuopin=findViewById(R.id.wode_found);
        xihuan=findViewById(R.id.wode_xihuan);
        Intent intent=getIntent();
        username=intent.getStringExtra(name);
        wode_name=findViewById(R.id.wodo_username);
        wode_name.setText(username);
        Userservice userservice=new Userservice(WodeActivity.this);
        wode_touxiang.setImageBitmap(userservice.getBmp(username));
        xihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zuopin.setTextColor(Color.parseColor("#000000"));
                xihuan.setTextColor(Color.parseColor("#FF0000"));
            }
        });
//        全部的图片展示
        zuopin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zuopin.setTextColor(Color.parseColor("#FF0000"));
                xihuan.setTextColor(Color.parseColor("#000000"));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WodeActivity.this, com.mibugi.share.LoginActivity.class);
                startActivity(intent);
            }
        });
        wode_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WodeActivity.this, com.mibugi.share.MainActivity.class);
                intent.putExtra(name,username);
                startActivity(intent);
            }
        });
        wode_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WodeActivity.this, com.mibugi.share.AddActivity.class);
                intent.putExtra(name,username);
                startActivity(intent);
            }
        });
        wode_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                // 打开手机相册,设置请求码
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(WodeActivity.this, com.mibugi.share.ChangePasswordActivity.class);
                intent.putExtra(name,username);
                startActivity(intent);
            }
        });

    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PHOTO_REQUEST_GALLERY){
            if (data!=null){
                Uri uri=data.getData();
//                crop(uri);
                wode_touxiang.setImageURI(uri);
                Bitmap bitmap =  null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Userservice userservice=new Userservice(WodeActivity.this);
                    //将bitmap图片转为byte类型
                    bitmap = ((BitmapDrawable)wode_touxiang.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                    byte[] result=baos.toByteArray();
                    userservice.changetouxiang(username,result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (requestCode == PHOTO_REQUEST_CUT){
            if (data!=null){
                Bitmap bitmap=data.getParcelableExtra("data");
                wode_touxiang.setImageBitmap(bitmap);
                Userservice userservice=new Userservice(WodeActivity.this);
                //将bitmap图片转为byte类型
                bitmap = ((BitmapDrawable)wode_touxiang.getDrawable()).getBitmap();
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                byte[] result=baos.toByteArray();
                userservice.changetouxiang(username,result);
            }
        }
    }
}
