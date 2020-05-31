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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.activities.MainActivity;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.Album;
import com.example.graduationprojectgallery.presentation.photos.PhotosFragment;

import java.util.ArrayList;

import static com.example.graduationprojectgallery.presentation.photos.PhotosFragment.selected_image_views;
import static com.example.graduationprojectgallery.presentation.photos.PhotosFragment.selected_photos;

public class ChooseAlbumAdapter extends RecyclerView.Adapter<ChooseAlbumAdapter.ViewHolder> {


    private static final String TAG = "ChooseAnAlbumsAdapter";

    //vars
    private ArrayList<Album> mAlbums = new ArrayList<>();
    private Context mContext;
    private ChooseAlbumFragment mFragment;

    public interface OnAlbumSelected {
        void sendInput(String input);
    }

    public OnAlbumSelected mOnAlbumSelected = new OnAlbumSelected() {
        @Override
        public void sendInput(String input) {
            PhotosFragment.first_click = false;
            String album_name = input;
            HelperClass.addImageToAlbum(selected_photos, album_name, mFragment.getActivity());
            selected_photos.clear();

            Toast.makeText(mContext, "added!", Toast.LENGTH_SHORT).show();

        }
    };

    public ChooseAlbumAdapter(Context mContext, ArrayList<Album> mAlbums, ChooseAlbumFragment mFragment) {

        this.mContext = mContext;
        this.mAlbums = mAlbums;
        this.mFragment = mFragment;
    }

    @NonNull
    @Override
    public ChooseAlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_albums_recycler_item, parent, false);

        return new ChooseAlbumAdapter.ViewHolder(view);

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
                NavController nav = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putString("album_name", mAlbums.get(position).getName());
                nav.navigate(R.id.action_chooseAlbumFragment_to_photosFragment, bundle);
                mOnAlbumSelected.sendInput(mAlbums.get(position).getName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mAlbums.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView album_thumbnail;
        private TextView album_name;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            album_name = itemView.findViewById(R.id.see_all_album_name_textView);
            album_thumbnail = itemView.findViewById(R.id.see_all_album_thumbnail_imageView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }

    }


}
