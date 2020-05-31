package com.example.graduationprojectgallery.presentation.foryou;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.graduationprojectgallery.R;

public class NewAlbumDialog extends DialogFragment {


    public interface OnInputSelected {
        void sendInput(String input);
    }

    public OnInputSelected mOnInputSelected;

    private static final String TAG = "NewAlbumDialog";
    EditText new_album_title;
    TextView cancel_button;
    TextView save_button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_album_dialog, container, false);

        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);

        new_album_title = view.findViewById(R.id.new_album_input);
        cancel_button = view.findViewById(R.id.action_cancel);
        save_button = view.findViewById(R.id.action_save);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: save capturing input.");
                String input = new_album_title.getText().toString();
                if (!input.equals("")) {
                    mOnInputSelected.sendInput(input);
                }
                getDialog().dismiss();
            }
        });

        new_album_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String title = new_album_title.getText().toString().trim();
                save_button.setEnabled(!title.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }


}

