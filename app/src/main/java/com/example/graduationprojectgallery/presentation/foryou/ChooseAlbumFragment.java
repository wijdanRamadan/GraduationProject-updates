package com.example.graduationprojectgallery.presentation.foryou;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.base.BaseFragment;
import com.example.graduationprojectgallery.helperClasses.HelperClass;
import com.example.graduationprojectgallery.models.Album;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class ChooseAlbumFragment extends BaseFragment {

    private ArrayList<Album> mAlbums = new ArrayList<>();

    private static ChooseAlbumAdapter adapter;
    private RecyclerView recyclerView;
    private View view;
    private ImageView back_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.choose_album_fragment, container, false);
        back_button = view.findViewById(R.id.back_imageView);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(ChooseAlbumFragment.this).popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BuildRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setNav();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide(); //tazzy this hides the original bar
        setHasOptionsMenu(false); // if menu added, this needs to be true

        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav); //hides bottom navigation menu
        navigationView.setVisibility(View.GONE);

        LoadDataSet();
    }

    public void BuildRecyclerView() {

        recyclerView = view.findViewById(R.id.choose_album_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new ChooseAlbumAdapter(this.getContext(), mAlbums, this);
        recyclerView.setAdapter(adapter);

    }


    private void LoadDataSet() { //updating arraylist of the adapter
        Log.d(TAG, "SeeAllAlbum : loading data set");

        HelperClass.loadAlbums(this.getActivity());
        mAlbums.removeAll(mAlbums);
        for (String name : HelperClass.albums_names_array) {

            mAlbums = new ArrayList<Album>(HelperClass.mAlbums);
        }


    }

}
