package com.example.graduationprojectgallery.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.graduationprojectgallery.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class BaseFragment extends Fragment {

    protected NavController findNavigationController() {
        return findNavController(this);
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setNav();

    }


    public void setNav() {
        BottomNavigationItemView photos = getActivity().findViewById(R.id.photos);
        BottomNavigationItemView search = getActivity().findViewById(R.id.search);
        BottomNavigationItemView albums = getActivity().findViewById(R.id.albums);


        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(BaseFragment.this).getCurrentDestination().getId() != R.id.photosFragment) {
                    findNavigationController().navigate(R.id.action_mainActivityFragment_to_photosFragment);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(BaseFragment.this).getCurrentDestination().getId() != R.id.searchFragment) {

                    findNavigationController().navigate(R.id.action_mainActivityFragment_to_searchFragment);
                }
            }
        });

        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(BaseFragment.this).getCurrentDestination().getId() != R.id.albumsFragment) {

                    findNavigationController().navigate(R.id.action_mainActivityFragment_to_albumsFragment);
                }

            }
        });

    }


}
