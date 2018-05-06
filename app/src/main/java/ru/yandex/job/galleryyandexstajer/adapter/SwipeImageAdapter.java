package ru.yandex.job.galleryyandexstajer.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ru.yandex.job.galleryyandexstajer.R;
import ru.yandex.job.galleryyandexstajer.handler.OnPagerPageChangeListener;
import ru.yandex.job.galleryyandexstajer.model.Image;

public class SwipeImageAdapter extends PagerAdapter{

    private Context context;
    private List<Image> images;
    private OnPagerPageChangeListener onPagerPageChangeListener;

    public SwipeImageAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.swipe, container, false);
        Image image = images.get(position);
        ImageView imageView = view.findViewById(R.id.full_image);
        ProgressBar progressBar = view.findViewById(R.id.progressFullImage);
        onPagerPageChangeListener.onPagerPageChanged(position);
        Glide.with(context)
                .load(image.getLargeImageURL())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
        container.addView(view);
        return view;
    }

    public void setOnPagerPageChangeListener(OnPagerPageChangeListener onPagerPageChangeListener) {
        this.onPagerPageChangeListener = onPagerPageChangeListener;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
