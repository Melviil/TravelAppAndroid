package com.example.gscct.triceratopstracker;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TakePicture extends AppCompatActivity {

    private Button takePictureButton;
    private ImageView imageView;
    private Button goBack;

    private Uri file;
    private static final int REQUEST_IMAGE_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_picture);

        takePictureButton = findViewById(R.id.button_image);
        imageView = findViewById(R.id.imageview);
    }


    public void takePicture( View view){
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if ( imageTakeIntent.resolveActivity(getPackageManager()) != null ){
            startActivityForResult( imageTakeIntent, REQUEST_IMAGE_CAPTURE);


        }
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data){
        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap= ( Bitmap ) extras.get("data");
            imageView.setImageBitmap(imageBitmap);


            String path = saveToInternalStorage(imageBitmap);

            DataBase2 myDB2 = new DataBase2(this);

            String idLieu = getIntent().getStringExtra("lieuId");

            myDB2.addImageToLieu(idLieu, path);



        }

    }



    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File myfile =new File(directory,"profile.jpg");
        Integer nombre = 0;

        while(myfile.exists()){

            nombre = nombre + 1;
            String increment = nombre.toString();
            myfile =new File(directory,"profile.jpg" + increment);

        }

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(myfile);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myfile.getAbsolutePath();
    }

}
