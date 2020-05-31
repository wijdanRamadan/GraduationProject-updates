package com.example.graduationprojectgallery.helperClasses;

import com.example.graduationprojectgallery.models.PhotoModel;
import com.example.graduationprojectgallery.presentation.photos.PhotosFragment;

public class Selector {


    public interface turnSelectorModeOn {

        void onSelectClick(int position, PhotoModel model);

        void onDoneClick();
    }

    public turnSelectorModeOn mturnSelectorModeOn = new turnSelectorModeOn() {
        @Override
        public void onSelectClick(int position, PhotoModel model) {
            PhotosFragment.first_click = true;
        }

        @Override
        public void onDoneClick() {
            PhotosFragment.first_click = false;
        }
    };

}
