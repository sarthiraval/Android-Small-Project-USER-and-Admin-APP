package com.example.usermodual.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;

import com.example.usermodual.R;


@SuppressLint("ViewConstructor")
public class IndicatorView extends AppCompatImageView {
    private int indicatorSize;

    public IndicatorView(Context context, int indicatorSize) {
        super(context);
        this.indicatorSize = indicatorSize;
        setIndicator();
    }

    private void setIndicator() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(indicatorSize, indicatorSize);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.setMargins(4, 0, 4, 0);
        setLayoutParams(layoutParams);
    }

    public void onSelectedChange(boolean isSelected) {
        if (isSelected) {
            setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.indicator_selected, null));
        } else {
            setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.indicator_unselected, null));
        }
    }
}
