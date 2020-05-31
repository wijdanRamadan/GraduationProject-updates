package com.example.graduationprojectgallery.activities.people;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.activities.PhotosViewActivity;
import com.example.graduationprojectgallery.activities.people.adapter.PersonFragmentAdapter;
import com.example.graduationprojectgallery.base.BaseFragment;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.PhotoModel;
import com.example.graduationprojectgallery.models.person;
import com.example.graduationprojectgallery.presentation.foryou.OpenAlbumAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.example.graduationprojectgallery.activities.MainActivity.photos;

public class PersonFragment extends BaseFragment implements PersonFragmentAdapter.PhotoClickListener {

    private GridLayoutManager layoutManager;
    public PersonFragmentAdapter mAdapter;
    private BottomNavigationView deleteBottomBar;
    private RecyclerView recyclerView;
    private View view;
    private TextView album_name_header;
    private TextView albums_back_button;
    private ImageView back_button;
    private Toolbar toolbar;
    private ArrayList<Uri> person_image_uri = new ArrayList<>();


    private TextView select_button;
    private TextView done_button;
    public static boolean first_click = false;
    public static ArrayList<PhotoModel> selected_photos = new ArrayList<>();
    public static ArrayList<ImageView> selected_image_views = new ArrayList<>();
    //endregion
    private PersonFragmentAdapter.PhotoClickListener clickListener;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BuildRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_person, container, false);
        album_name_header = view.findViewById(R.id.person_name_header_textview);
        select_button = view.findViewById(R.id.edit_person_button);
        done_button = view.findViewById(R.id.done_person_button);
        album_name_header.setText(getArguments().getString("person_name"));
        deleteBottomBar = (BottomNavigationView) view.findViewById(R.id.person_delete_nav_bar);
        deleteBottomBar.setVisibility(View.VISIBLE);

        back_button = view.findViewById(R.id.back_to_photos_imageView);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(PersonFragment.this).popBackStack();
            }
        });

        albums_back_button = view.findViewById(R.id.back_to_photos_textview);
        albums_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavigationController().navigate(R.id.action_personFragment_to_mainActivityFragment);
            }
        });

        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   //tazzy create new album dialog
                first_click = true;
                select_button.setVisibility(View.GONE);
                done_button.setVisibility(View.VISIBLE);
                select_button.setVisibility(View.GONE);
                deleteBottomBar.setVisibility(View.VISIBLE);
            }

        });

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_click = false;
                select_button.setVisibility(View.VISIBLE);
                done_button.setVisibility(View.GONE);
                select_button.setVisibility(View.VISIBLE);
                deleteBottomBar.setVisibility(View.GONE);
                if (!selected_photos.isEmpty()) {
                    for (ImageView photo : selected_image_views) {
                        photo.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                    }

                }

            }
        });




        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false); // if menu added, this needs to be true

        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav); //hides bottom navigation menu
        navigationView.setVisibility(View.GONE);
        toolbar = getActivity().findViewById(R.id.app_toolbar);
        deleteBottomBar = getActivity().findViewById(R.id.person_delete_nav_bar);
    }

    public void BuildRecyclerView() {

        ArrayAdapter<String> detailesAdapter = new ArrayAdapter<String>(getActivity()
                , android.R.layout.simple_list_item_1
        );
        detailesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        person_image_uri = HelperClass.getPersonImages(getArguments().getString("person_name"), getActivity());
        mAdapter = new PersonFragmentAdapter(getContext(), getArguments().getString("person_name"),person_image_uri);
        mAdapter.setPhotoClickListener(this);
        recyclerView = view.findViewById(R.id.person_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void OnPhotoClick(int position) {

        for (PhotoModel p : photos) {
            String bug = (person_image_uri.get(position).getPath()).toString();
            if (p.getPath() != null && p.getPath().contains(bug)) {
                Intent intent = new Intent(getContext(), PhotosViewActivity.class);
                intent.putExtra("photo", p); //tazzy do NOT change "photo" !!!!!!!!!
                startActivity(intent);
                break;
            }
        }

    }


}
