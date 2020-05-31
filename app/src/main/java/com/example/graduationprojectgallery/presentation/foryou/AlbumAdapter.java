package com.example.graduationprojectgallery.presentation.foryou;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.models.Album;

import java.util.ArrayList;


//tazzy this :https://www.youtube.com/watch?v=94rCjYxvzEE


public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {


    private static final String TAG = "AlbumsRecyclerAdapter";
    //vars
    private ArrayList<Album> mAlbums;
    private Context mContext;


    public AlbumAdapter(Context mContext, ArrayList<Album> mAlbums) {
        this.mContext = mContext;
        this.mAlbums = mAlbums;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //tazzy inflating the recycler item layout


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foryou_fragment_recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setTag(mAlbums.get(position));
        holder.album_name.setText(mAlbums.get(position).getName());
        Glide
                .with(mContext)
                .load(mAlbums.get(position).getThumbnail_path())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.album_thumbnail);


        holder.album_thumbnail.setOnClickListener(new View.OnClickListener() {  // tazzy when clicking on an album thumbnail
            @Override
            public void onClick(View view) {
                Log.d(TAG, "OnBindViewHolder: onClick: clicked on an image : " + mAlbums.get(position));
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                NavController nav = Navigation.findNavController(view);
                if (mAlbums.get(position).getName() == "Recent") {
                    nav.navigate(R.id.action_foryouFragment_to_photosFragment);
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putString("album_name", mAlbums.get(position).getName());
                    nav.navigate(R.id.action_albumsFragment_to_openAlbumFragment, bundle);
                }

                Toast.makeText(mContext, mAlbums.get(position).getName() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {

        return mAlbums.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView album_thumbnail;
        TextView album_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            album_thumbnail = itemView.findViewById(R.id.album_thumbnail_imageView);
            album_name = itemView.findViewById(R.id.album_name_textView);


        }

    }


}
