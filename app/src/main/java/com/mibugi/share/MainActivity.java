package com.mibugi.share;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView found;
    private TextView xihuan;
    private ImageView wode;
    private ImageView add;

    private HomeAdapter homeAdapter;
    public int[] count;
    public static final String p_string="0";
    private int length;
    public static final String name="yonghumingchuandi";
    private SQLiteDatabase db;
    private StringAndBitmap stringAndBitmap = new StringAndBitmap();
    private List<PictureVO> pictureList;
    private Boolean[] isliked;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        found=findViewById(R.id.found);
        xihuan=findViewById(R.id.xihuan);
        wode=findViewById(R.id.me);
        add=findViewById(R.id.add);
        isliked = new Boolean[100];
        for(int i =0;i<100;i++)isliked[i]= true;
        count = new int[100];
        for (int i = 0;i<100;i++)count[i]=0;

        //获取intent中的name值
        Intent intenta=getIntent();
        String user=intenta.getStringExtra(name);

        pictureList = new ArrayList<PictureVO>();
        //打开数据库查询图片信息
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(MainActivity.this);
//        db = mySQLiteOpenHelper.getWritableDatabase();
//        db.execSQL("delete from picture");
        db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(PictureContract.PictureEntry.TABLE_NAME,null,null,null,null,null,null);
        int titleIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_TITLE);
        int contentIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_CONTENT);
        int imageIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_IMAGE);
        int usernameIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_USERNAME);
        int loveCountIndex = cursor.getColumnIndex(PictureContract.PictureEntry.COLUMN_NAME_LOVE_COUNT);



        while(cursor.moveToNext()){
            PictureVO picture = new PictureVO();
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
        cursor.close();
        db.close();
        if(pictureList.size() > 0){
            Userservice userservice = new Userservice(MainActivity.this);
            pictureList.forEach(picture -> {
                String username = picture.getUsername();
                picture.setHead(userservice.getBmp(username));
            });
        }
        homeAdapter=new HomeAdapter(MainActivity.this,R.layout.item,pictureList);
        homeAdapter.init();
        RecyclerView recyclerView=findViewById(R.id.item);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(homeAdapter);
        length=homeAdapter.getItemCount();
        homeAdapter.setOnItemClickListener(MyItemClickListener);
//        设置获取本人点赞的图片分享（点击喜欢）
        xihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                found.setTextColor(Color.parseColor("#000000"));
                xihuan.setTextColor(Color.parseColor("#FF0000"));
            }
        });
//        全部的图片展示（点击发现）
        found.setOnClickListener(view -> {
            found.setTextColor(Color.parseColor("#FF0000"));
            xihuan.setTextColor(Color.parseColor("#000000"));
        });
        //点击我的
        wode.setOnClickListener(view -> {
            Intent intent1 =new Intent();
            intent1.setClass(MainActivity.this,WodeActivity.class);
            intent1.putExtra(name,user);
            startActivity(intent1);
        });
        //点击发布
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,AddActivity.class);
                intent.putExtra(name,user);
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
    }

    //item点击事件监听
    private HomeAdapter.OnItemClickListener MyItemClickListener=new HomeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, HomeAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                case R.id.like:{
                    Toast.makeText(MainActivity.this,"你点击了第"+(position+1)+"个图片的喜欢",Toast.LENGTH_SHORT).show();


                    ImageView like = v.findViewById(R.id.like);
                    if(isliked[position])
                    {
                        like.setImageDrawable(getResources().getDrawable(R.drawable.liked));
                        count[position]++;

                    }
                    else
                    {
                        like.setImageDrawable(getResources().getDrawable(R.drawable.like));
                        count[position]--;


                    }
                    isliked[position] = !isliked[position];

                    break;
                }

                case R.id.image:{
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,PictureDetailsActivity.class);
                    intent.putExtra("p_string",position);
                    startActivity(intent);
                    break;
                }
            }
        }

        @Override
        public void onItemLongClick(View v,int position) {

        }
    };
    @Override
    public void onClick(View view) {

    }

}