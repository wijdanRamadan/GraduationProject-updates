package com.example.graduationprojectgallery.presentation.foryou;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.models.Album;

import java.util.ArrayList;


public class SeeAllAlbumsAdapter extends RecyclerView.Adapter<SeeAllAlbumsAdapter.ViewHolder> implements SeeAllAlbumsFragment.OnEditClick {


    private static final String TAG = "SeeAllAlbumsAdapter";

    //vars
    private ArrayList<Album> mAlbums = new ArrayList<>();
    private Context mContext;
    public Fragment f;
    public FragmentManager fm;


    public void StartDeleteMode() {
        SeeAllAlbumsFragment.first_click = true;
    }

    public void EndDeleteMode() {
        SeeAllAlbumsFragment.first_click = false;
    }


    public SeeAllAlbumsAdapter(Context mContext, ArrayList<Album> mAlbums, Fragment f, FragmentManager fm) {

        this.mContext = mContext;
        this.mAlbums = mAlbums;
        this.fm = fm;
        this.f = f;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_albums_recycler_item, parent, false);

        return new SeeAllAlbumsAdapter.ViewHolder(view);
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
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                NavController nav = Navigation.findNavController(view);
                if (!SeeAllAlbumsFragment.first_click) {
                    if (mAlbums.get(position).getName() == "Recent") {
                        nav.navigate(R.id.action_seeAllAlbumsFragment_to_photosFragment);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("album_name", mAlbums.get(position).getName());
                        nav.navigate(R.id.action_seeAllAlbumsFragment_to_openAlbumFragment, bundle);
                    }
                    Toast.makeText(mContext, mAlbums.get(position).getName() + "", Toast.LENGTH_SHORT).show();
                } else if (SeeAllAlbumsFragment.first_click
                        && mAlbums.get(position).getName() != "Recent" && mAlbums.get(position).getName() != "Favorites") {

                    final EditAlbumsDialog dialog = new EditAlbumsDialog(mAlbums.get(position).getName(), mContext, f, position);
                    dialog.setTargetFragment(f, 0);
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                    dialog.show(fm, "EditAlbumsDialog");
                    Toast.makeText(mContext, "EDIT MODE", Toast.LENGTH_SHORT).show();
                }
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
