package com.example.graduationprojectgallery.presentation.photos;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.activities.PhotosViewActivity;
import com.example.graduationprojectgallery.base.BaseFragment;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.PhotoModel;
import com.example.graduationprojectgallery.presentation.foryou.ChooseAlbumAdapter;
import com.example.graduationprojectgallery.presentation.photos.adapter.PhotosFragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.example.graduationprojectgallery.activities.MainActivity.photos;


public class PhotosFragment extends BaseFragment implements PhotosFragmentAdapter.PhotoClickListener, ChooseAlbumAdapter.OnAlbumSelected {

    Toolbar toolbar;

    private PhotosFragmentAdapter mAdapter;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView deleteBottomBar;
    private PhotosFragmentAdapter.PhotoClickListener photoClickListener;
    private RecyclerView recyclerView;
    private TextView select_button;
    private TextView done_button;
    public static boolean first_click = false;
    public static ArrayList<PhotoModel> selected_photos = new ArrayList<>();
    public static ArrayList<ImageView> selected_image_views = new ArrayList<>();

    public PhotosFragment() {
    }

    public static PhotosFragment newInstance() {

        PhotosFragment fragment = new PhotosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav);
        deleteBottomBar = getActivity().findViewById(R.id.delete_nav_bar);
        toolbar = getActivity().findViewById(R.id.app_toolbar);
        if(first_click )bottomNavigationView.setVisibility(View.GONE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        deleteBottomBar = (BottomNavigationView) view.findViewById(R.id.delete_nav_bar);
        select_button = view.findViewById(R.id.select_photos_button);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav); //hides bottom navigation menu
        deleteBottomBar.setItemIconSize(30);
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //tazzy create new album dialog
                first_click = true;
                select_button.setVisibility(View.GONE);
                done_button.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.GONE);
                select_button.setVisibility(View.GONE);
                deleteBottomBar.setVisibility(View.VISIBLE);
            }

        });

        done_button = view.findViewById(R.id.done_photos_button);
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_click = false;
                select_button.setVisibility(View.VISIBLE);
                done_button.setVisibility(View.GONE);
                select_button.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.VISIBLE);
                deleteBottomBar.setVisibility(View.GONE);
                if (!selected_photos.isEmpty()) {
                    for (ImageView photo : selected_image_views) {
                        photo.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                    }
                    for (PhotoModel photo : selected_photos) {
                        photo.setSelect(false);
                    }

                }

                // mAdapter.notifyItemRangeChanged();

            }
        });

        deleteBottomBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_favorites:
                                if (!selected_photos.isEmpty()) {
                                    HelperClass.addImageToAlbum(selected_photos, "Favorites", getActivity());
                                }
                                if (!selected_image_views.isEmpty()) {
                                    for (ImageView photo : selected_image_views) {
                                        photo.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                                    }
                                    selected_image_views.clear();

                                }
                                break;
                            case R.id.action_add:
                                if (!selected_photos.isEmpty()) {
                                    findNavigationController().navigate(R.id.action_photosFragment_to_chooseAlbumFragment);
                                    for(PhotoModel photo : selected_photos){
                                        photo.setSelect(false);
                                    }

                                }
                                if (!selected_image_views.isEmpty()) {
                                    for (ImageView photo : selected_image_views) {
                                        photo.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                                    }
                                    selected_image_views.clear();
                                }
                                break;

                            case R.id.action_delete:
                                if (!selected_photos.isEmpty()) {
                                    for (PhotoModel photo : selected_photos) {
                                        HelperClass.DeletePhoto(photo, getActivity());
                                        int position = photos.indexOf(photo);
                                        //photos.remove(photo);
//                                        urls.remove(photo);
//                                        mAdapter.notifyItemRemoved(position);
//                                        mAdapter.notifyItemRangeChanged(position, photos.size()-1);
                                        mAdapter.notifyDataSetChanged();
                                        for (ImageView photoi : selected_image_views) {
                                            photoi.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                                        }
                                        selected_image_views.clear();
                                    }
                                }
                                Toast.makeText(getContext(), "to be deleted", Toast.LENGTH_SHORT);
                                break;

                        }
                        return false;
                    }
                });

        return view;
    }

    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(first_click )bottomNavigationView.setVisibility(View.GONE);
//        FaceDetector detector = new FaceDetector( urls , getContext());
//        detector.detect_face(urls.get(1), getContext());

    }

    public void setNav() {
        BottomNavigationItemView photos = getActivity().findViewById(R.id.photos);
        BottomNavigationItemView albums = getActivity().findViewById(R.id.albums);
        BottomNavigationItemView search = getActivity().findViewById(R.id.search);

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(PhotosFragment.this).getCurrentDestination().getId() != R.id.photosFragment) {
                }
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(PhotosFragment.this).getCurrentDestination().getId() != R.id.searchFragment) {

                    findNavigationController().navigate(R.id.action_photosFragment_to_searchFragment2);
                }
            }
        });
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(PhotosFragment.this).getCurrentDestination().getId() != R.id.albumsFragment) {
                    findNavigationController().navigate(R.id.action_photosFragment_to_albumsFragment);
                }
            }
        });

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayAdapter<String> detailesAdapter = new ArrayAdapter<String>(getActivity()
                , android.R.layout.simple_list_item_1
        );
        detailesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        mAdapter = new PhotosFragmentAdapter(getActivity());
        mAdapter.setPhotoClickListener(this);

        recyclerView = view.findViewById(R.id.photos_recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));

        // Set
//        textCustomTitle.setTypeface(customFont);
        toolbar.setTitle(R.string.Photos);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        if(first_click )bottomNavigationView.setVisibility(View.GONE);
    }

    public void OnPhotoClick(int position, ImageView photosFragmentImageView) {
        PhotoModel photoModel = photos.get(position);
        if (first_click) {
            if (photoModel.isSelect()) {
                photosFragmentImageView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                photoModel.setSelect(false);
                selected_photos.remove(photoModel);
                return;
            }
            if (!photoModel.isSelect()) {
                photoModel.setSelect(true);
                photosFragmentImageView.setBackgroundColor(getContext().getResources().getColor(R.color.black));
                Toast.makeText(this.getContext(), "Selector mode on ", Toast.LENGTH_SHORT).show();
                selected_photos.add(photoModel);
                selected_image_views.add(photosFragmentImageView);
                return;
            }
        } else {
            Intent intent = new Intent(getContext(), PhotosViewActivity.class);
            intent.putExtra("photo", photoModel);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav);
        navigationView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.GONE);
        if(!selected_photos.isEmpty()){
            for(PhotoModel photo : selected_photos){
                photo.setSelect(false);
            }
            selected_photos.clear();}
        if (!selected_image_views.isEmpty())selected_image_views.clear();
    }

    @Override
    public void sendInput(String input) {
        first_click = false;
        String album_name = input;
        HelperClass.addImageToAlbum(selected_photos, album_name, getActivity());

        for(PhotoModel photo : selected_photos){
            photo.setSelect(false);
        }
        if (!selected_image_views.isEmpty()) {
            for (ImageView photo : selected_image_views) {
                photo.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }
            selected_image_views.clear();
        }
        selected_photos.clear();
        Toast.makeText(getContext(), "added!", Toast.LENGTH_SHORT);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
