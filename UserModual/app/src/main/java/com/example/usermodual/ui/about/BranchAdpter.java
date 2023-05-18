package com.example.usermodual.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.usermodual.R;

import java.util.List;

public class BranchAdpter extends PagerAdapter {

    Context context;
    List<BranchModel> list;

    public BranchAdpter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.branchitemlayout,container,false);
        ImageView brIcon;
        TextView brTitle,brDesc;

        brDesc = view.findViewById(R.id.br_desc);
        brTitle = view.findViewById(R.id.br_title);
        brIcon = view.findViewById(R.id.br_icon);

        brIcon.setImageResource(list.get(position).getImg());
        brTitle.setText(list.get(position).getTitle());
        brDesc.setText(list.get(position).getDescription());

        container.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
