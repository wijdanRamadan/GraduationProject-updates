package com.example.graduationprojectgallery.presentation.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.activities.PhotosViewActivity;
import com.example.graduationprojectgallery.base.BaseFragment;
import com.example.graduationprojectgallery.models.PhotoModel;
import com.example.graduationprojectgallery.presentation.search.adapter.SearchFragmentPhotosAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;
import static com.example.graduationprojectgallery.activities.MainActivity.photos;


public class SearchFragment extends BaseFragment  implements SearchFragmentPhotosAdapter.PhotoClickListener{
    private SearchFragmentPhotosAdapter mAdapter;
    private SearchFragmentPhotosAdapter.PhotoClickListener photoClickListener;
    private RecyclerView recyclerView;
    EditText searchText;
    Button searchButton;
    SearchViewModel viewModel;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new SearchFragmentPhotosAdapter(getActivity());
        searchText=view.findViewById(R.id.searchbar);
        searchButton=view.findViewById(R.id.search_button);

        recyclerView = view.findViewById(R.id.search_fragment_photos_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(mAdapter);
    }



    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.imagePaths.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) { mAdapter.setPhotoModelList(strings);}});
                viewModel.getImagesByLabels(searchText.getText().toString());
            }
        });


    }

    public void setNav() {
        BottomNavigationItemView photos = getActivity().findViewById(R.id.photos);
        BottomNavigationItemView albums = getActivity().findViewById(R.id.albums);
        BottomNavigationItemView search = getActivity().findViewById(R.id.search);


        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(SearchFragment.this).getCurrentDestination().getId() != R.id.photosFragment) {
                    findNavigationController().navigate(R.id.action_searchFragment_to_photosFragment);
                }
            }
        });

        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(SearchFragment.this).getCurrentDestination().getId() != R.id.albumsFragment) {
                    findNavigationController().navigate(R.id.action_searchFragment_to_albumsFragment);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findNavController(SearchFragment.this).getCurrentDestination().getId() != R.id.searchFragment) {

                }
            }
        });
    }

    @Override
    public void OnPhotoClick(int position) {
        PhotoModel photoModel = photos.get(position);
        Intent intent = new Intent(getContext(), PhotosViewActivity.class );
        intent.putExtra("photo", photoModel);
        startActivity(intent);
    }
}