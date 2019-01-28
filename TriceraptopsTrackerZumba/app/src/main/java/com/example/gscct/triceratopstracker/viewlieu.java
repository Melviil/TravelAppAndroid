package com.example.gscct.triceratopstracker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class viewlieu extends AppCompatActivity {
    DataBase myDB;
    DataBase2 myDB2;
    TextView titleTv, dateTv;
    LinearLayout ListePhotos;
    Button Supprimer;
    Button AjoutPhoto;
    String lieuId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlieu);
        Intent myIntent = getIntent();
        myDB = new DataBase(this);
        myDB2 = new DataBase2(this);
        titleTv = findViewById(R.id.textView4);
        dateTv = findViewById(R.id.textView5);
        ListePhotos = findViewById(R.id.linearLayout);
        Supprimer = findViewById(R.id.button3);
        AjoutPhoto = findViewById(R.id.button11);
        lieuId = myIntent.getStringExtra("id");


        updateInfoLieu();
        add_photo();
        delete_lieu();

    }

    /* Mets à jour les données présentes sur la view */
    public void updateInfoLieu(){
        String title = "";
        String date ="";
        String ImagePath = "";

        final Cursor res = myDB2.getLieuWithLieuId(Integer.parseInt(lieuId));
        while(res.moveToNext()) {

            title = res.getString(1);
            date = res.getString(2);
            ImagePath = res.getString(4);
        }


        titleTv.setText(title);
        dateTv.setText(date);


        if( ImagePath != null ){
            File imgFile = new  File(ImagePath);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = findViewById(R.id.imageView);

                myImage.setImageBitmap(myBitmap);

            }

        }

    }

    /* Ajoute le listener sur le bouton permettant d'ajouter une photo */
    public void add_photo(){
        final AppCompatActivity context = this;
        AjoutPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Intent startNewActivity = new Intent (context, TakePicture.class);
                            startNewActivity.putExtra("lieuId", lieuId);
                            startActivity(startNewActivity);
                    }
                }
        );
    }
    /* Ajoute le listener sur le bouton permettant de supprimer un lieu */
    public void delete_lieu(){
        Supprimer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB2.deleteLieu(Integer.parseInt(lieuId));
                        if(deletedRows > 0) {
                            Toast.makeText(viewlieu.this, "Lieu supprimé", Toast.LENGTH_LONG).show();

                            finish();
                        }
                        else
                            Toast.makeText(viewlieu.this, "Lieu non supprimé", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }



    //PHOTO
    /*static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/




    @Override
    public void onResume(){
        super.onResume();
        updateInfoLieu();
    }

}
