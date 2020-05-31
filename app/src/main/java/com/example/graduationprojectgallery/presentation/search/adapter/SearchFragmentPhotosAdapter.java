package com.example.graduationprojectgallery.presentation.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.PhotoModel;
import com.example.graduationprojectgallery.presentation.photos.adapter.PhotosFragmentAdapter;
import com.example.graduationprojectgallery.presentation.photos.adapter.PhotosFragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchFragmentPhotosAdapter extends RecyclerView.Adapter<SearchFragmentPhotosAdapter.SearchFragmentViewHolder> {

    private PhotosFragmentAdapter.PhotoClickListener photoClickListener;
    private Context mContext;
    private List<String> photoModelList = new ArrayList<>();


    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }


    public SearchFragmentPhotosAdapter(Context context, List<String> photoModelList) {
        mContext = context;
        this.photoModelList = photoModelList;

    }

    public SearchFragmentPhotosAdapter(Context context) {
        this.mContext = context;
    }

    public PhotosFragmentAdapter.PhotoClickListener getPhotoClickListener() {
        if (photoClickListener == null) {
            photoClickListener = new PhotosFragmentAdapter.PhotoClickListener() {
                @Override
                public void OnPhotoClick(int position, ImageView photoView) {

                }

            };
        }
        return photoClickListener;
    }

    public void setPhotoClickListener(PhotosFragmentAdapter.PhotoClickListener photoClickListener) {
        this.photoClickListener = photoClickListener;
    }

    public void setPhotoModelList (List <String> photoModelList) {
        this.photoModelList = photoModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchFragmentPhotosAdapter.SearchFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_fragment_recycler_item, parent, false);
        return new SearchFragmentPhotosAdapter.SearchFragmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFragmentViewHolder holder, int position) {
        if (photoModelList != null) {
            String photo = photoModelList.get(position);
            holder.bind(photo, position);
        }
    }

    @Override
    public int getItemCount() {
        return photoModelList.size();
    }

    public class SearchFragmentViewHolder extends RecyclerView.ViewHolder {

        public ImageView searchFragmentImageView;

        public SearchFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            searchFragmentImageView = itemView.findViewById(R.id.photos_fragment_recycler_image);
        }

        public void bind(final String photoPath, final int position) {
            HelperClass.show(photoPath, mContext, searchFragmentImageView);
        }

        public void bind(final PhotoModel photoModel, final int position) {
            HelperClass.show(photoModel, mContext, searchFragmentImageView);
        }
    }

    public interface PhotoClickListener {
        void OnPhotoClick(int position);

    }
}

