package com.example.usermodual.Views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.usermodual.R;


public class ArrowLayout extends AppCompatImageView {

    private Context context;
    private int gravity;
    private int defaultLeft,defaultRight;
    private int customArrow=0;
    public ArrowLayout(Context context,int gravity) {
        super(context);
        this.context = context;
        this.gravity=gravity;
        this.defaultLeft= R.drawable.ic_default_left;
        this.defaultRight=R.drawable.ic_default_right;
        setLayout();
    }

    private void setLayout(){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = gravity|Gravity.CENTER_VERTICAL;
        layoutParams.setMargins(8,0,8,0);
        setLayoutParams(layoutParams);
        if(gravity==Gravity.LEFT){
            if(customArrow==0){
                setImageResource(defaultLeft);
            }else{
                setImageResource(customArrow);
            }
        }else{
            if(customArrow==0){
                setImageResource(defaultRight);
            }else{
                setImageResource(customArrow);
            }
        }
    }

    public void changeArrows(int arrow){
        this.customArrow=arrow;
        //reset the indicatorlist everytime the user specifies a custom indicator
        setLayout();
    }

}
