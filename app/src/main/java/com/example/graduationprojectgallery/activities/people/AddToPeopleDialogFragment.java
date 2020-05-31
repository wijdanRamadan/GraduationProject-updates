package com.example.graduationprojectgallery.activities.people;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.graduationprojectgallery.R;
import com.example.graduationprojectgallery.helperClasses.FaceRecognition;

import static com.example.graduationprojectgallery.activities.PhotosViewActivity.photoModel;

public class AddToPeopleDialogFragment  extends DialogFragment {

    EditText personName;
    TextView cancelButton;
    TextView saveButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_to_people, container, false);

        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);

        personName = view.findViewById(R.id.new_person_input);
        cancelButton = view.findViewById(R.id.action_cancel);
        saveButton = view.findViewById(R.id.action_save);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = personName.getText().toString();
                if (!input.equals("")) {
                    FaceRecognition.send_known(photoModel,input,getActivity());

                }
                getDialog().dismiss();
            }
        });

        personName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String title = personName.getText().toString().trim();
                saveButton.setEnabled(!title.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }



}
