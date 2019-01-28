package com.example.gscct.triceratopstracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    DataBase myDB;
    Button ListeVoyage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        myDB = new DataBase(this);
        ListeVoyage = findViewById(R.id.button);
        viewAll();

    }

    //Fonction creation_voyage pour ouvrir une nouvelle page
    public void creation_voyage(View view){
        Intent startNewActivity = new Intent (this, AddVoyage.class);
        startActivity(startNewActivity);
    }

    public void viewAll(){
        final AppCompatActivity context = this;
        ListeVoyage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent startNewActivity = new Intent (context, VoyageDisplay.class);
                        startActivity(startNewActivity);
                    }
                }
        );
    }

    public void showMessage(String title, String Msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Msg);
        builder.show();
    }

}
