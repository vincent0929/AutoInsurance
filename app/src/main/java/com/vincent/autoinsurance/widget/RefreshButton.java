package com.vincent.autoinsurance.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vincent.autoinsurance.R;

/**
 * Created by Vincent on 2016/4/4 0004.
 */
public class RefreshButton extends LinearLayout {

    private ImageView imageView;
    RotateAnimation rotateAnimation;
//    private OnReButtonClickListener listener;

    public RefreshButton(Context context) {
        super(context);
        init();
    }

    public RefreshButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        imageView=new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_atychossecity_refresh);
//        imBtn.setBackgroundColor(Color.TRANSPARENT);
        imageView.setPadding(20,20,20,20);
        imageView.setFocusable(false);
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,0);
        imageView.setLayoutParams(params);
        this.addView(imageView);
        rotateAnimation=new RotateAnimation(0,-360,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(600);
        rotateAnimation.setRepeatCount(-1);
    }

    public void startRotate(){
        imageView.startAnimation(rotateAnimation);
    }

    public void stopRotate(){
        imageView.clearAnimation();
    }

    public boolean isRotate(){
        if(imageView.getAnimation()!=null){
            return true;
        }
        return false;
    }
}
