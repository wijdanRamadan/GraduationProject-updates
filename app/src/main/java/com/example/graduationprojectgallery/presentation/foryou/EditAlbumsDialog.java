package com.example.graduationprojectgallery.presentation.foryou;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.graduationprojectgallery.R;

public class EditAlbumsDialog extends DialogFragment {

    public interface OnInputSelected {
        void sendInput(String input, int position);
    }

    public EditAlbumsDialog.OnInputSelected mOnInputSelected;

    private static final String TAG = "DeleteAlbumDialog";
    private Button cancel_button;
    private Button delete_button;
    private TextView album_name;
    private String album_name_string;
    private Context mcontext;
    private Fragment targetFragment;
    private int position;

    public EditAlbumsDialog(String input, Context context, Fragment fragment, int position) {

        album_name_string = input;
        this.targetFragment = fragment;
        this.mcontext = context;
        this.position = position;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_albums_dialog, container, false);
        cancel_button = view.findViewById(R.id.cancel_delete_album);
        delete_button = view.findViewById(R.id.delete_album);
        album_name = view.findViewById(R.id.delete_album_name_textview);
        album_name.setText(album_name_string);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                String input = album_name_string;
                mOnInputSelected.sendInput(input, position);
                getDialog().dismiss();
            }
        });


        return view;
    }


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (EditAlbumsDialog.OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

}
