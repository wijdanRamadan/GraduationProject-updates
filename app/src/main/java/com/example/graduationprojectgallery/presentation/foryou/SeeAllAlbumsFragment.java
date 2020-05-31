package com.example.graduationprojectgallery.presentation.foryou;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SeeAllAlbumsFragment extends BaseFragment implements EditAlbumsDialog.OnInputSelected {


    private ArrayList<Album> mAlbums = new ArrayList<>();

    private static SeeAllAlbumsAdapter adapter;
    private RecyclerView recyclerView;
    private View view;
    private View view2;
    private ImageView back_albums_button;
    private ImageView disabled_back_album_button;
    private TextView edit_albums_button;
    private TextView done_albums_button;
    static boolean first_click = false;

    @Override
    public void sendInput(String input, int position) {
        System.out.println(input);
        deleteAlbum(position, input);
    }


    public interface OnEditClick {

        void StartDeleteMode();

        void EndDeleteMode();


    }

    public OnEditClick minusClicked = new OnEditClick() {
        @Override
        public void StartDeleteMode() {
            first_click = true;
            Toast.makeText(getContext(), "" + first_click, Toast.LENGTH_SHORT).show();
        }

        public void EndDeleteMode() {
            SeeAllAlbumsFragment.first_click = false;
        }

    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide(); //tazzy this hides the original bar

        setHasOptionsMenu(true); // if menu added, this needs to be true


        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav); //hides bottom navigation menu
        navigationView.setVisibility(View.GONE);

        LoadDataSet();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_display_all_albums, container, false);
        view2 = inflater.inflate(R.layout.display_albums_recycler_item, container, false);
        back_albums_button = view.findViewById(R.id.back_imageView2);
        back_albums_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavigationController().navigate(R.id.action_seeAllAlbumsFragment_to_albumsFragment);

            }
        });


        edit_albums_button = view.findViewById(R.id.edit_albums_button);
        disabled_back_album_button = view.findViewById(R.id.gray_back_imageView);
        done_albums_button = view.findViewById(R.id.done_albums_button);

        edit_albums_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_albums_button.setVisibility(View.GONE);
                disabled_back_album_button.setVisibility(View.VISIBLE);
                edit_albums_button.setVisibility(View.GONE);
                done_albums_button.setVisibility(View.VISIBLE);
                minusClicked.StartDeleteMode();


            }
        });


        done_albums_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_albums_button.setVisibility(View.VISIBLE);
                disabled_back_album_button.setVisibility(View.GONE);
                edit_albums_button.setVisibility(View.VISIBLE);
                done_albums_button.setVisibility(View.GONE);
                minusClicked.EndDeleteMode();
                //  LoadDataSet(); //tazyy do NOT do this, does NOT work
            }
        });

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setNav();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BuildRecyclerView();


    }

    private void LoadDataSet() { //updating arraylist of the adapter
        Log.d(TAG, "SeeAllAlbum : loading data set");

        HelperClass.loadAlbums(this.getActivity());
        mAlbums.removeAll(mAlbums);
        for (String name : HelperClass.albums_names_array) {

            mAlbums = new ArrayList<Album>(HelperClass.mAlbums);
        }


    }


    public void BuildRecyclerView() {

        recyclerView = view.findViewById(R.id.see_all_albums_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new SeeAllAlbumsAdapter(this.getContext(), mAlbums, SeeAllAlbumsFragment.this, getParentFragmentManager());
        recyclerView.setAdapter(adapter);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.display_albums_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void deleteAlbum(int position, String input) {
        HelperClass.deleteAlbum(input, getActivity());
        mAlbums.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, mAlbums.size());
        recyclerView.smoothScrollToPosition(position);

    }

}
