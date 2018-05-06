package ru.yandex.job.galleryyandexstajer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.yandex.job.galleryyandexstajer.R;
import ru.yandex.job.galleryyandexstajer.handler.OnImageItemClickListener;
import ru.yandex.job.galleryyandexstajer.model.Image;

public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerImageAdapter.MyViewHolder> {

    private List<Image> images;
    private Context context;

    private OnImageItemClickListener onImageItemClickListener;

    public RecyclerImageAdapter(Context context, List<Image> images) {
        this.context = context;
        this.images = images;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Image image = images.get(position);
        ImageView imageView = holder.imageView;

        Glide.with(context)
                .load(image.getPreviewURL())
                .into(imageView);

        imageView.setOnClickListener(v-> onImageItemClickListener.onImageItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setOnImageItemClickListener(OnImageItemClickListener onImageItemClickListener) {
        this.onImageItemClickListener = onImageItemClickListener;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}


