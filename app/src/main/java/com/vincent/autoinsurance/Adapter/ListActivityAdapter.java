package com.vincent.autoinsurance.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.vincent.autoinsurance.Activity.BaseActivity;
import com.vincent.autoinsurance.Bean.AutoActivity;
import com.vincent.autoinsurance.Context.BitmapCache;
import com.vincent.autoinsurance.Context.MyApplication;
import com.vincent.autoinsurance.R;

import java.util.List;

/**
 * Created by Vincent on 2016/4/13 0013.
 */
public class ListActivityAdapter extends BaseAdapter{

    private List<AutoActivity> list;
    private LayoutInflater inflater;

    public ListActivityAdapter(List<AutoActivity> list, Context context) {
        super();
        this.list=list;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AutoActivity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout ll;

        if(convertView==null){
            ll= (LinearLayout) inflater.inflate(R.layout.list_cell_auto_activity,null);
        }else{
            ll= (LinearLayout) convertView;
        }

        ViewHolder viewHolder=new ViewHolder();
        viewHolder.ivActivityImage= (ImageView) ll.findViewById(R.id.ivActivityImage);
        viewHolder.tvActivityName= (TextView) ll.findViewById(R.id.tvActivityName);
        viewHolder.tvActivitySummary= (TextView) ll.findViewById(R.id.tvActivitySummary);
        viewHolder.tvActivityDate= (TextView) ll.findViewById(R.id.tvActivityDate);
        viewHolder.tvActivityDuration= (TextView) ll.findViewById(R.id.tvActivityDuration);
        AutoActivity autoActivity=list.get(position);
        getActivityImage(viewHolder.ivActivityImage,autoActivity.getActivityImageUrl());
        viewHolder.tvActivityName.setText(autoActivity.getName());
        viewHolder.tvActivityDate.setText(autoActivity.getDate());
        viewHolder.tvActivitySummary.setText(autoActivity.getSummary());
        viewHolder.tvActivityDuration.setText(autoActivity.getDuration());
        return ll;
    }

    public void getActivityImage(ImageView imageView,String imageUrl){
        ImageLoader.ImageListener imageListener=ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        MyApplication.getImageLoader().get(imageUrl,imageListener,300,200);
    }

    class ViewHolder{
        private ImageView ivActivityImage;
        private TextView tvActivityName,tvActivitySummary,tvActivityDate,tvActivityDuration;
    }
}
