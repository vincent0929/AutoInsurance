package com.vincent.autoinsurance.widget;

import org.xmlpull.v1.XmlPullParser;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vincent.autoinsurance.R;

/**
 * Created by Vincent on 2016/4/3 0003.
 */
public class SegmentView extends LinearLayout {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private OnSegmentViewClickListener listener;
    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SegmentView(Context context) {
        super(context);
        init();
    }

    private void init() {
//		this.setLayoutParams(new LinearLayout.LayoutParams(dp2Px(getContext(), 60), LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView3 = new TextView(getContext());
        LayoutParams layoutParams=new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        textView1.setLayoutParams(layoutParams);
        textView2.setLayoutParams(layoutParams);
        textView3.setLayoutParams(layoutParams);
        textView1.setText("活动");
        textView2.setText("服务");
        textView3.setText("会员");
        XmlPullParser xrp = getResources().getXml(R.drawable.widget_seg_text_color_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
            textView3.setTextColor(csl);
        } catch (Exception e) {
        }
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView3.setGravity(Gravity.CENTER);
        textView1.setPadding(5, 20, 5, 20);
        textView2.setPadding(5, 20, 5, 20);
        textView3.setPadding(5, 20, 5, 20);
        setSegmentTextSize(16);
        textView1.setBackgroundResource(R.drawable.widget_seg_radiobutton_left);
        textView2.setBackgroundResource(R.drawable.widget_seg_radiobutton_middle);
        textView3.setBackgroundResource(R.drawable.widget_seg_radiobutton_right);
        textView1.setSelected(true);
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.addView(textView3);
        this.invalidate();

        textView1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    return;
                }
                setCurrentTab(0);
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    return;
                }
                setCurrentTab(1);
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });
        textView3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView3.isSelected()) {
                    return;
                }
                setCurrentTab(2);
                if (listener != null) {
                    listener.onSegmentViewClick(textView3, 2);
                }
            }
        });
    }

    public void setCurrentTab(int positon){
        switch (positon){
            case 0:
                textView1.setSelected(true);
                textView2.setSelected(false);
                textView3.setSelected(false);
                break;
            case 1:
                textView2.setSelected(true);
                textView1.setSelected(false);
                textView3.setSelected(false);
                break;
            case 2:
                textView3.setSelected(true);
                textView1.setSelected(false);
                textView2.setSelected(false);
                break;
        }
    }

    /**
     * 设置字体大小 单位dip
     * <p>2016年4月3日</p>
     * @param dp
     * @author vincent
     */
    public void setSegmentTextSize(int dp) {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 设置文字
     * <p>2016年4月3日</p>
     * @param text
     * @param position
     * @author vincent
     */
    public void setSegmentText(CharSequence text,int position) {
        if (position == 0) {
            textView1.setText(text);
        }
        if (position == 1) {
            textView2.setText(text);
        }
        if (position == 3) {
            textView2.setText(text);
        }
    }

    public interface OnSegmentViewClickListener{
        /**
         *
         * <p>2016年4月3日</p>
         * @param v
         * @param position 0-左边 1-右边
         * @author Vincent
         */
        public void onSegmentViewClick(View v,int position);
    }

    public void setOnSegmentViewClickListener(OnSegmentViewClickListener listener) {
        this.listener = listener;
    }
}