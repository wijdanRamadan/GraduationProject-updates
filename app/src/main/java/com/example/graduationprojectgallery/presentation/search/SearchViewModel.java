package com.example.graduationprojectgallery.presentation.search;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.graduationprojectgallery.models.PhotoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.graduationprojectgallery.activities.MainActivity.photos;
import static com.example.graduationprojectgallery.activities.MainActivity.photosUris;

public class SearchViewModel extends AndroidViewModel {
    private final FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();

    MutableLiveData<List<String>> imagePaths = new MutableLiveData<>();

    private List<String> images = new ArrayList<>();
    private Application context;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    //TODO pass photo uris as argument to fragment.
    public void getImagesByLabels(String lable) {
        imagePaths.setValue(new ArrayList<String>());
        images.clear();

        buildCompletableArray(photosUris, lable)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        return Observable.error(throwable);
                    }
                })
                .onExceptionResumeNext(Observable.empty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private Observable<Object> buildCompletableArray(final List<Uri> photoModels, final String searchedItem) {
        List<Observable<Object>> observableList = new ArrayList<>();
        for (Uri uri : photoModels) {
            observableList.add(taskToCompletable(uri, searchedItem).toObservable());
        }

        return Observable.concatDelayError(observableList);
    }

    private Completable taskToCompletable(final Uri uri, final String searchedItem) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                Log.d("whereis: ", uri.getPath());
                try {
                    FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(context, uri);
                    labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                            Log.d("whereis: ", "Success: " + uri.getPath());
                            PhotoModel photoModel = null;
                            for (FirebaseVisionImageLabel label : firebaseVisionImageLabels) {
                                if (searchedItem.equals(label.getText())) {
                                    if(label.getConfidence()>=0.80)
                                    {
                                        photoModel = photos.get(photosUris.indexOf(uri));
                                        images.add(photoModel.getPath());
                                        imagePaths.postValue(images);
                                        break;
                                    }
                                }
                            }

                            if (photoModel != null) {
                                emitter.onComplete();
                            } else {
                                emitter.tryOnError(new Throwable());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(new Throwable());
                }
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}