package com.example.graduationprojectgallery.activities.people.adapter;

import android.content.Context;
import android.net.Uri;
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
import com.example.graduationprojectgallery.models.person;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.graduationprojectgallery.helperClasses.HelperClass.mpeople;

public class  PersonFragmentAdapter extends RecyclerView.Adapter<PersonFragmentAdapter.PersonViewHolder> {

    private ArrayList<Uri> people;
    private Context mContext;
    private PhotoClickListener photoClickListener;
    private String name;



    public PersonFragmentAdapter(Context mContext, String name,ArrayList<Uri> mUri) {

        this.mContext = mContext;
        this.people = mUri;
        this.name=name;

    }
    


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.person_fragment_item, parent, false);

        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonFragmentAdapter.PersonViewHolder holder, int position) {


       Glide
                .with(mContext)
                .load(people.get(position).toString())
                .override(300, 300)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.personFragmentImageView);
        holder.personFragmentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPhotoClickListener().OnPhotoClick(position);

            }
        });






    }



    @Override
    public int getItemCount() {
        return people.size();
    }


    public PhotoClickListener getPhotoClickListener() {
        if (photoClickListener == null) {
            photoClickListener = new PhotoClickListener() {
                @Override
                public void OnPhotoClick(int position) {



                }

            };
        }
        return photoClickListener;
    }

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        this.photoClickListener = photoClickListener;
    }



    public class PersonViewHolder extends RecyclerView.ViewHolder{

        public ImageView personFragmentImageView;



        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            personFragmentImageView=itemView.findViewById(R.id.person_recycler_image);
        }


    }

    public interface PhotoClickListener {
        void OnPhotoClick(int position);

    }

}
