package com.example.graduationprojectgallery.presentation.foryou;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.PhotoModel;

import java.util.ArrayList;

import static com.example.graduationprojectgallery.R.id.open_album_recycler_image;

public class OpenAlbumAdapter extends RecyclerView.Adapter<OpenAlbumAdapter.OpenAlbumViewHolder> {

    private ArrayList<Uri> mUri;
    private Context mContext;
    private PhotoClickListener photoClickListener;
    private String album_name;


    public OpenAlbumAdapter(Context mContext, String album_name, ArrayList<Uri> mUri) {

        this.mContext = mContext;
        this.mUri = mUri;
        this.album_name = album_name;
    }


    @NonNull
    @Override
    public OpenAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.open_album_recycler_item, parent, false);

        OpenAlbumViewHolder vh = new OpenAlbumViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OpenAlbumViewHolder holder, final int position) {

        Glide
                .with(mContext)
                .load(mUri.get(position).toString())
                .override(300, 300)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photosFragmentImageView);
        holder.photosFragmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPhotoClickListener().OnPhotoClick(position);
            }
        });
        System.out.println(mUri.get(position));

    }

    @Override
    public int getItemCount() {
        return mUri.size();
    }


    public PhotoClickListener getPhotoClickListener() {
        if (photoClickListener == null) {
            photoClickListener = new PhotoClickListener() {
                @Override
                public void OnPhotoClick(int position) {
                    Toast.makeText(mContext, "null", Toast.LENGTH_LONG).show();

                }

            };
        }
        return photoClickListener;
    }

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        this.photoClickListener = photoClickListener;
    }


    public class OpenAlbumViewHolder extends RecyclerView.ViewHolder {

        public ImageView photosFragmentImageView;

        public OpenAlbumViewHolder(@NonNull View itemView) {
            super(itemView);

            photosFragmentImageView = itemView.findViewById(open_album_recycler_image);
        }



    }

    public interface PhotoClickListener {
        void OnPhotoClick(int position);

    }
}
