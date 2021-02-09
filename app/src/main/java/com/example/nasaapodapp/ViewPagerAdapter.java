package com.example.nasaapodapp;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

class ViewPagerAdapter extends PagerAdapter {

    Context context;

    ArrayList<String> images;

    LayoutInflater mLayoutInflater;


    public ViewPagerAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);
        TextView title =  itemView.findViewById(R.id.title);
        TextView description = itemView.findViewById(R.id.description);
        TextView date = itemView.findViewById(R.id.date);

        Glide.with(context).load(images.get(position)).into(imageView);
        title.setText(Html.fromHtml(ImageDetail.ShowTitle.get(position)));
        description.setText(Html.fromHtml(ImageDetail.ShowDescription.get(position)));
        date.setText(Html.fromHtml(ImageDetail.ShowDate.get(position)));
        //Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout) object);
    }
}
