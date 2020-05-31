package com.example.graduationprojectgallery.helperClasses;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.graduationprojectgallery.R;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.graduationprojectgallery.helperClasses.HelperClass.createAppDirectory;

public class Create_Empty_Icon {


    public Create_Empty_Icon(Activity activity) {

        File appDirectory = createAppDirectory(activity);
        File albumsDir = new File(appDirectory, "Albums");
        File f1 = new File(albumsDir, "empty_album.png");
        try {
            FileOutputStream outStream = new FileOutputStream(f1);
            Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.empty_album);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
