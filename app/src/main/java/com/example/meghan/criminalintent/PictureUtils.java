package com.example.meghan.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;


public class PictureUtils { //page 301

    //302, Static method called getScaledBitmap(String, Activity) to scale a Bitmap for a particular Activity's size.
    public static Bitmap getScaledBitmap(String path, Activity activity) { //page 302, Conservative scale method
        Point size = new Point(); //This method checks to see how big the screen is, and then scales teh image down to that size.
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);

        return getScaledBitmap(path, size.x, size.y);
    }



    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) { //Adding New static method getScaledBitmap //page 302
        //Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth; //page 302
        float srcHeight = options.outHeight;

        // Figure out how much to scale down by
        int inSampleSize = 1; //The key parameter, this determines how big each "sample" should be for each pixel.
        if (srcHeight > destHeight || srcWidth > destWidth) { //page 302
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }

        options = new BitmapFactory.Options(); //page 302
        options.inSampleSize = inSampleSize;

        //Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options); //page 302
    }
}
