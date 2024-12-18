package com.example.group7_project;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private final Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(this.context);
            // Calculate item size based on the screen width
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int columnWidth = (screenWidth) / 2; // 2 columns
            imageView.setLayoutParams(new GridView.LayoutParams(columnWidth, columnWidth));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(R.drawable.action6_question); // Set your image resource
        return imageView;
    }
}
