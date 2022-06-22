package com.mibugi.share;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PictureDetailsActivity extends AppCompatActivity {
    private int[] imgs={R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5,R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5};
    private String[] titles={"1","2","3","4","5","1","2","3","4","5"};
    private String[] countents={"1","2","3","4","5","1","2","3","4","5"};
    private List<Picture> pictureList = new ArrayList<>();
    private Picture[] pictures;
    private SQLiteDatabase db;
    private StringAndBitmap stringAndBitmap = new StringAndBitmap();
    private ImageView imageView;
    private TextView title,content;
    //长按后显示的 Item
    final String[] items = new String[] { "保存图片"};
    private Bitmap bitmap;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView=findViewById(R.id.imagedetails);
        title=findViewById(R.id.details_title);
        content=findViewById(R.id.details_content);
        Bundle bundle=getIntent().getExtras();
        int temp=bundle.getInt("p_string");
        String t=Integer.toString(temp);
        //连接数据库查询获取图片集合
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(PictureDetailsActivity.this);
        db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(PictureContract.PictureEntry.TABLE_NAME,null,null,null,null,null,null);
        int titleIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_TITLE);
        int contentIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_CONTENT);
        int imageIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_IMAGE);
        int usernameIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_USERNAME);
        int loveCountIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_LOVE_COUNT);

        while(cursor.moveToNext()){
            Picture picture = new Picture();
            String title = cursor.getString(titleIndex);
            String content = cursor.getString(contentIndex);
            String image = cursor.getString(imageIndex);
            Bitmap bitmap = stringAndBitmap.stringToBitmap(image);
            String username= cursor.getString(usernameIndex);
            Integer loveCount = cursor.getInt(loveCountIndex);
            picture.setTitle(title);
            picture.setContent(content);
            picture.setImage(bitmap);
            picture.setUsername(username);
            picture.setLoveCount(loveCount);
            pictureList.add(picture);
        }
        pictures = pictureList.toArray(new Picture[pictureList.size()]);
        imageView.setImageBitmap(pictures[temp].getImage());
        title.setText(pictures[temp].getTitle());
        content.setText(pictures[temp].getContent());
        bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        //详情页长按保存图片到系统相册
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(PictureDetailsActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:{
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, t, "description");
//                                saveImageToGallery(PictureDetailsActivity.this,bitmap);
                                Toast.makeText(PictureDetailsActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
    }
//    public static void saveImageToGallery(Context context, Bitmap bmp) {
//        // 首先保存图片 创建文件夹
//        File appDir = new File(Environment.getExternalStorageDirectory(), "picture");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        //图片文件名称
//        String fileName = "picture_"+System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 其次把文件插入到系统图库
//        String path = file.getAbsolutePath();
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        context.sendBroadcast(intent);
//    }
}