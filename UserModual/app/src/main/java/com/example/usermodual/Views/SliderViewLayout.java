package com.example.usermodual.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.usermodual.ui.gallery.ImageAdapter;
import com.example.usermodual.R;
import com.example.usermodual.beans.SliderImage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SliderViewLayout extends FrameLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    Context context;
    private AppCompatActivity userActivity;
    private ViewPager viewPager;
    private ArrayList<SliderImage> sliderImagesList = new ArrayList<>();
    private ArrayList<SliderImage> pendingImagesList = new ArrayList<>();
    private Boolean showIndicator, infiniteView, showArrow, setupFinished = false;
    private int indicatorSize, timeInterval;
    private int placeholder = 0;
    private int leftArrowId, rightArrowId;
    private IndicatorLayout indicatorLayout;
    private Timer timer;
    ArrowLayout leftArrowLayout, rightArrowLayout;

    public SliderViewLayout(Context context) {
        super(context);
        this.context = context;
    }

    public SliderViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttributes(attrs);
    }

    public SliderViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttributes(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SliderViewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        getAttributes(attrs);
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.SliderViewLayout);
        timeInterval = attrArray.getInt(R.styleable.SliderViewLayout_timeInterval, 2000);
        showIndicator = attrArray.getBoolean(R.styleable.SliderViewLayout_showIndicator, true);
        indicatorSize = attrArray.getDimensionPixelSize(R.styleable.SliderViewLayout_indicatorSize, 10);
        infiniteView = attrArray.getBoolean(R.styleable.SliderViewLayout_infiniteview, true);
        showArrow = attrArray.getBoolean(R.styleable.SliderViewLayout_showarrow, false);
        attrArray.recycle();
        setLayout();
    }

    private void setLayout() {
        if (getContext() instanceof AppCompatActivity) {
            userActivity = (AppCompatActivity) getContext();
        } else {
            throw new RuntimeException("Activity must extend AppCompatActivity");
        }
        viewPager = new ViewPager(getContext());
        viewPager.setId(View.generateViewId());

        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        viewPager.addOnPageChangeListener(SliderViewLayout.this);
        addView(viewPager);
        if (showIndicator) {
            indicatorLayout = new IndicatorLayout(getContext(), indicatorSize);
            addView(indicatorLayout);
        }

        //adding arrows
        if (showArrow) {
            leftArrowLayout = new ArrowLayout(getContext(), Gravity.LEFT);
            leftArrowLayout.setId(View.generateViewId());
            leftArrowId = leftArrowLayout.getId();
            addView(leftArrowLayout);
            rightArrowLayout = new ArrowLayout(getContext(), Gravity.RIGHT);
            rightArrowLayout.setId(View.generateViewId());
            rightArrowId = rightArrowLayout.getId();
            addView(rightArrowLayout);
            leftArrowLayout.setOnClickListener(this);
            rightArrowLayout.setOnClickListener(this);
        }
        startTimer();
        setupFinished = true;
        setSliderImages(pendingImagesList, placeholder);
    }

    public void setSliderImages(ArrayList<SliderImage> sliderImagesList, int placeholder) {
        if (setupFinished) {
            this.sliderImagesList = sliderImagesList;
            for (int i = 0; i < sliderImagesList.size(); i++) {
                if (indicatorLayout != null) {
                    indicatorLayout.onImageAdded();
                }
            }
            ImageAdapter imageAdapter;
            imageAdapter = new ImageAdapter(userActivity.getSupportFragmentManager(), sliderImagesList, placeholder, infiniteView);
            viewPager.setAdapter(imageAdapter);

            if (infiniteView) {
                viewPager.setCurrentItem(1, false);
                if (indicatorLayout != null) {
                    indicatorLayout.onImageChanged(0);
                }
            } else {
                indicatorLayout.onImageChanged(viewPager.getCurrentItem());
            }
        } else {
            this.placeholder = placeholder;
            pendingImagesList.addAll(sliderImagesList);
        }
    }

    private void startTimer() {
        Log.e("status", "timer has been started");
        if (timeInterval > 0) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ((AppCompatActivity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (infiniteView) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            }
                        }
                    });
                }
            }, timeInterval);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            Log.e("status", "timer has been stopped");
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (infiniteView) {
            updateSlider(position);
        } else {
            indicatorLayout.onImageChanged(position);
        }
    }

    private void updateSlider(int position) {
        if (position == 0) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(sliderImagesList.size(), false);
                }
            }, 100);
            if (indicatorLayout != null) {
                indicatorLayout.onImageChanged(sliderImagesList.size() - 1);
            }
        } else if (position == sliderImagesList.size() + 1) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(1, false);
                }
            }, 100);
            if (indicatorLayout != null) {
                indicatorLayout.onImageChanged(0);
            }
        } else {
            if (indicatorLayout != null) {
                indicatorLayout.onImageChanged(position - 1);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Log.e("status","state changed");
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                stopTimer();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                startTimer();
                break;
        }
    }


    public void setCustomIndicators(Drawable selectedIndicator, Drawable unselectedIndicator) {
        indicatorLayout.changeIndicator(selectedIndicator, unselectedIndicator);
        if (infiniteView) {
            updateSlider(viewPager.getCurrentItem());
        } else {
            indicatorLayout.onImageChanged(viewPager.getCurrentItem());
        }

    }

    public void setCustomArrows(int leftArrow, int rightArrow) {
        if (leftArrowLayout != null) {
            removeView(leftArrowLayout);
        }
        if (rightArrowLayout != null) {
            removeView(rightArrowLayout);
        }
        leftArrowLayout = new ArrowLayout(getContext(), Gravity.LEFT);
        leftArrowLayout.setId(View.generateViewId());
        leftArrowId = leftArrowLayout.getId();
        leftArrowLayout.changeArrows(leftArrow);
        addView(leftArrowLayout);
        rightArrowLayout = new ArrowLayout(getContext(), Gravity.RIGHT);
        rightArrowLayout.setId(View.generateViewId());
        rightArrowId = rightArrowLayout.getId();
        rightArrowLayout.changeArrows(rightArrow);
        addView(rightArrowLayout);
        leftArrowLayout.setOnClickListener(this);
        rightArrowLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == leftArrowId) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            if (infiniteView) {
                stopTimer();
                updateSlider(viewPager.getCurrentItem());
            } else {
                indicatorLayout.onImageChanged(viewPager.getCurrentItem());
            }

        } else if (view.getId() == rightArrowId) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (infiniteView) {
                stopTimer();
                updateSlider(viewPager.getCurrentItem());
            } else {
                indicatorLayout.onImageChanged(viewPager.getCurrentItem());
            }
        }
    }
}
