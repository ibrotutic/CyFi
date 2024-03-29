package com.example.cyfi.utils;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class ExifUtil {
    /**
     * Utility to rotate a bitmap.
     * @param src
     *  Image source.
     * @param bitmap
     *  Bitmap to rotate.
     * @return
     *  Rotated bitmap.
     */
    public static Bitmap rotateBitmap(String src, Bitmap bitmap) {
        try {
            int orientation = getExifOrientation(src);

            if (orientation == 1) {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * Get orientation of image.
     * @param src
     *  Source image.
     * @return
     *  The orientation of the image (vertical or horizontal.
     * @throws IOException
     *  Image doesn't exist.
     */
    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        try {
            ExifInterface exif = new ExifInterface(src);
             orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return orientation;
    }
}
