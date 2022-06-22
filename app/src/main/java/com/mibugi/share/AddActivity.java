package com.mibugi.share;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView add_image;
    private EditText add_title;
    private EditText add_content;
    private Button btnShare;
    private Bitmap bitmap;
    private String user;
    public static final String name="yonghumingchuandi";
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private StringAndBitmap stringAndBitmap = new StringAndBitmap();
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Intent intent=getIntent();
        user=intent.getStringExtra(name);
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(AddActivity.this);
        db = mySQLiteOpenHelper.getWritableDatabase();

        add_image=findViewById(R.id.add_image);
        add_title=findViewById(R.id.add_title);
        add_content=findViewById(R.id.add_content);
        btnShare=findViewById(R.id.btn_share);
        btnShare.setOnClickListener(this);
        //设置上传点击事件
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                // 打开手机相册,设置请求码
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
//                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });
    }
//    private void crop(Uri uri) {
//        // 裁剪图片意图
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        // 裁剪框的比例，1：1
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // 裁剪后输出图片的尺寸大小
//        intent.putExtra("outputX", 250);
//        intent.putExtra("outputY", 250);
//        intent.putExtra("outputFormat", "JPEG");// 图片格式
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", true);
//        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
//        startActivityForResult(intent, PHOTO_REQUEST_CUT);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_REQUEST_GALLERY){
            if (data!=null){
                Uri uri=data.getData();
                //ImageView显示图片
                add_image.setImageURI(uri);
                if (uri != null) {
                    try {
                        //获取所选图片的bitmap
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                bitmap=data.getParcelableExtra("data");
//                crop(uri);
            }
        }else if (requestCode == PHOTO_REQUEST_CUT){
            if (data!=null){
                Bitmap bitmap=data.getParcelableExtra("data");
                add_image.setImageBitmap(bitmap);
            }
        }
    }

    //点击分享按钮将数据保存到数据库中
    @Override
    public void onClick(View v) {
        String title = add_title.getText().toString();
        String content = add_content.getText().toString();
//        Bitmap bitmap = ((BitmapDrawable)add_image.getDrawable()).getBitmap();
        String image = stringAndBitmap.bitmapToString(bitmap);
        ContentValues contentValues = new ContentValues();
        contentValues.put(PictureContract.PictureEntry.COLUMN_NAME_TITLE,title);
        contentValues.put(PictureContract.PictureEntry.COLUMN_NAME_CONTENT,content);
        contentValues.put(PictureContract.PictureEntry.COLUMN_NAME_IMAGE,image);
        contentValues.put(PictureContract.PictureEntry.COLUMN_NAME_USERNAME,user);


        db.insert(PictureContract.PictureEntry.TABLE_NAME,null,contentValues);

        Intent intent=new Intent();
        intent.setClass(AddActivity.this, com.mibugi.share.MainActivity.class);
        intent.putExtra(name,user);
        startActivity(intent);
    }
}