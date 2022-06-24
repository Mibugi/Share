package com.mibugi.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements View.OnClickListener {
    private List<PictureVO> mPictureData = new ArrayList<PictureVO>();
    private Context mContext;
    private int resourceId;
    private PictureVO[] pictures;
    private int[] touxiangimage;

    public HomeAdapter(Context context, int resourceId, List<PictureVO> data) {
//        super(context, resourceId, data);
        this.mContext = context;
        this.resourceId = resourceId;
        this.mPictureData = data;
    }


    public void init(){
        //将List集合转成对象数组
        pictures = mPictureData.toArray(new PictureVO[mPictureData.size()]);
    }
    private OnItemClickListener onItemClickListener;
    //item点击事件监听
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }
    @Override
    public void onClick(View view) {
        int position=(int) view.getTag();
        if (onItemClickListener!=null){
            switch (view.getId()){
                case R.id.item:onItemClickListener.onItemClick(view, ViewName.PRACTISE,position);break;
                default:onItemClickListener.onItemClick(view, ViewName.ITEM,position);
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(resourceId,parent,false);
        return new MyViewHolder(view);
    }

    //数据绑定
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img.setImageBitmap(pictures[position].getImage());
        holder.title.setText(pictures[position].getTitle());
        holder.head.setImageBitmap(pictures[position].getHead());
        holder.username.setText(pictures[position].getUsername());
        holder.img.setTag(position);
        holder.like.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mPictureData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img,head,like;
        public TextView title,username,count;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            head=itemView.findViewById(R.id.touxiang);
            username=itemView.findViewById(R.id.username);
            like=itemView.findViewById(R.id.like);
            itemView.setOnClickListener(HomeAdapter.this);
            img.setOnClickListener(HomeAdapter.this);
            like.setOnClickListener(HomeAdapter.this);
        }
    }
    public enum ViewName{
        ITEM,
        PRACTISE
    }
    public interface OnItemClickListener{
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v,int position);
    }
    public void Changelike(int position){

    }
}
