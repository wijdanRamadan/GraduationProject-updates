package com.example.graduationprojectgallery.activities.people.adapter;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.Album;
import com.example.graduationprojectgallery.models.person;

import java.util.ArrayList;


public class personAlbumAdapter  extends RecyclerView.Adapter<personAlbumAdapter.ViewHolder>{

    private ArrayList<person> mPerson;
    private Context mContext;

    public personAlbumAdapter(ArrayList<person> mPerson, Context mContext) {
        this.mPerson = mPerson;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public personAlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull personAlbumAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(mPerson.get(position));
        holder.person_name.setText(mPerson.get(position).getName());

        Glide
                .with(mContext)
                .load(mPerson.get(position).getThumbnail())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.person_thumbnail);

        holder.person_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                NavController nav = Navigation.findNavController(v);
                Bundle bundle = new Bundle();
                bundle.putString("person_name", mPerson.get(position).getName());
                nav.navigate(R.id.action_albumsFragment_to_personFragment, bundle);

                Toast.makeText(v.getContext(),mPerson.get(position).getName(),Toast.LENGTH_LONG).show();



            }
        });

    }

    @Override
    public int getItemCount() {
        return mPerson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView person_thumbnail;
        TextView person_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            person_name=itemView.findViewById(R.id.person_name_textView);
            person_thumbnail=itemView.findViewById(R.id.person_thumbnail_imageView);


        }
    }
}
