package com.example.group7_project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Data> dataSet;
    private MyClickListener mCallback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        ViewHolder dataObjHolder = new ViewHolder(view);
        return dataObjHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.title.setText(dataSet.get(position).getTitle());
        holder.desc.setText(dataSet.get(position).getDesc());
        //decode base64 string to image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(dataSet.get(position).getIcon(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.icon.setImageBitmap(decodedImage);
    }

    @Override
    public int getItemCount(){
        return dataSet.size();
    }

    public void setOnItemClickListener(MyClickListener mCallback){
        this.mCallback = mCallback;
    }

    public MyAdapter(List<Data> myDataSet){
        dataSet = myDataSet;
    }

    public interface MyClickListener{
        public void onItemClick(int position, View v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, desc;
        ImageView icon;

        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.description);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            mCallback.onItemClick(getAdapterPosition(), v);
        }
    }
}
