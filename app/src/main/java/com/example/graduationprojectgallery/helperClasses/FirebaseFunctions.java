package com.example.graduationprojectgallery.helperClasses;


import android.content.Context;
import android.net.Uri;

import com.example.graduationprojectgallery.models.PhotoModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetector;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class FirebaseFunctions {
    static String x = null;

//    public static Observable<String> getImagesBasedOnLabels(final Context context, final List<Uri> photoModels, final String searchedItem) {
//       final FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
//
//       return Observable.fromIterable(photoModels).flatMap(new Function<Uri, ObservableSource<Task<List<FirebaseVisionImageLabel>>>>() {
//           @Override
//           public ObservableSource<Task<List<FirebaseVisionImageLabel>>> apply(Uri uri) throws Exception {
//               FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(context, uri);
//              return Observable.just(labeler.processImage(image));
//           }
//       }).map(new Function<Task<List<FirebaseVisionImageLabel>>, String>() {
//           @Override
//           public String apply(Task<List<FirebaseVisionImageLabel>> listTask) {
//              Iterator<> iterator = listTask.getResult().iterator();
//              while (iterator.hasNext()) {
//                  iterator.next();
//
//              }
//           }
//       });
//
//    }



//    public static List<PhotoModel> getImagesBasedOnLabels(Context context, final List<Uri> photoModels, final String searchedItem) {
//
//        final List<PhotoModel> result = new ArrayList<>();
//
//
//        FirebaseVisionObjectDetectorOptions options =
//                new FirebaseVisionObjectDetectorOptions.Builder()
//                        .setDetectorMode(FirebaseVisionObjectDetectorOptions.SINGLE_IMAGE_MODE)
//                        .enableMultipleObjects()
//                        .enableClassification()
//                        .build();
//
//        FirebaseVisionObjectDetector objectDetector =
//                FirebaseVision.getInstance().getOnDeviceObjectDetector(options);
//        FirebaseVisionImage image;
//        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
//                .getOnDeviceImageLabeler();
//        try {
//
//            for (final Uri photo : photoModels) {
//                image = FirebaseVisionImage.fromFilePath(context, photo);
//
//                labeler.processImage(image)
//                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
//                            @Override
//                            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
//                                for (FirebaseVisionImageLabel label : labels) {
//                                    if (searchedItem.equals( label.getText())) {
//                                        result.add(photos.get(photoModels.indexOf(photo)).getPath());
//                                    }
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Task failed with an exception
//                                // ...
//                            }
//                        });
//
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


    public static String testFunction(PhotoModel photoModel, Context context) {

        FirebaseVisionObjectDetectorOptions options =
                new FirebaseVisionObjectDetectorOptions.Builder()
                        .setDetectorMode(FirebaseVisionObjectDetectorOptions.SINGLE_IMAGE_MODE)
                        .enableMultipleObjects()
                        .enableClassification()
                        .build();

        FirebaseVisionObjectDetector objectDetector =
                FirebaseVision.getInstance().getOnDeviceObjectDetector(options);
        FirebaseVisionImage image;
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                .getOnDeviceImageLabeler();


        try {
            image = FirebaseVisionImage.fromFilePath(context, Uri.fromFile(new File(photoModel.getPath())));
            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                            x = firebaseVisionImageLabels.get(0).getText();
                        }
                    });
        } catch (IOException e) {


            e.printStackTrace();
        }


        return x;
    }


}

