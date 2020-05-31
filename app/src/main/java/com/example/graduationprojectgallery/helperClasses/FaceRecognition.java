package com.example.graduationprojectgallery.helperClasses;

import android.app.Activity;
import android.os.SystemClock;

import com.example.graduationprojectgallery.models.PhotoModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.graduationprojectgallery.activities.MainActivity.photos;

public class FaceRecognition {



    public static final String BASE_URL = "http://192.168.88.67:5000/all";
    public static final String KNOWN_URL = "http://192.168.88.67:5000/known";

    public static void SendEverything(){

        for (PhotoModel photo : photos) {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse(photo.getPath());
            System.out.println(photo.getPath());
            File file = new File(photo.getPath());
            OkHttpClient client = new OkHttpClient();
            RequestBody requestFile = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", photo.getPath(), RequestBody.create(MEDIA_TYPE_PNG, file))
                    .build();

            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .post(requestFile)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Cancel the post on failure.
//                    e.printStackTrace();
//                    call.cancel();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    System.out.println(response);
                }
            });
            SystemClock.sleep(500);
        }

    }

    public static void send_known(PhotoModel photo, String name, Activity activity){


        final MediaType MEDIA_TYPE_PNG = MediaType.parse(photo.getPath());
        System.out.println(photo.getPath());
        File file = new File(photo.getPath());
        OkHttpClient client = new OkHttpClient().newBuilder()
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .build();

        RequestBody requestFile = new MultipartBody.Builder()

                .setType(MultipartBody.FORM)
                .addFormDataPart("image", photo.getPath(), RequestBody.create(MEDIA_TYPE_PNG, file))
                .addFormDataPart("name", name)
                .build();

        Request request = new Request.Builder()
                .url(KNOWN_URL)
                .post(requestFile)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                e.printStackTrace();
//                    call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream in = response.body().byteStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                ArrayList<String> images = new ArrayList<>();
                while((line = reader.readLine()) != null) {
                    images.add(line);
                }
                HelperClass.createPeopleDirectory(name, images, activity);
                images.clear();

            }
        });

    }



}