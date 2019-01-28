package com.example.gscct.triceratopstracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class viewvoyage extends AppCompatActivity {
    DataBase myDB;
    DataBase2 myDB2;
    LinearLayout ListeLieux;
    TextView title;
    TextView date;
    TextView id;
    Button Supprimer;
    Button ParNom;
    int voyageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewvoyage);
        Intent myIntent = getIntent();
        myDB = new DataBase(this);
        myDB2 = new DataBase2(this);
        title = findViewById(R.id.textView2);
        date = findViewById(R.id.textView3);
        ListeLieux = findViewById(R.id.linearLayout);
        Supprimer = findViewById(R.id.button7);
        ParNom = findViewById(R.id.button8);
        voyageId = myIntent.getIntExtra("id",0);
        title.setText(myIntent.getStringExtra("title"));
        date.setText(myIntent.getStringExtra("date"));
        delete_voyage();

    }


    @Override
    public void onResume(){
        super.onResume();
        viewLieu();
    }

    public void displayVoyage(int id, String name, String date){
        Intent startNewActivity = new Intent (this, AddVoyage.class);
        startActivity(startNewActivity);
    }


    public void ajouter_lieu(View view){
        Intent startNewActivity = new Intent (this, AddLieu.class);
        startNewActivity.putExtra("id", voyageId);
        startActivity(startNewActivity);
    }

    public void viewLieu(){
        ListeLieux.removeAllViews();
        final AppCompatActivity context = this;
        final Cursor res = myDB2.getLieu(voyageId);
        if(res.getCount() == 0){
            showMessage("Pas de lieu enregistré", "Entrer un lieu");
            return;
        }
        while(res.moveToNext()){
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(res.getString(1));
            btnTag.setId(Integer.parseInt(res.getString(0)));
            ListeLieux.addView(btnTag);
            final String id = res.getString(0);
            final String title = res.getString(1);
            final String date = res.getString(2);
            final String ImagePath = res.getString(4);
            btnTag.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent startNewActivity = new Intent (context, viewlieu.class);
                            startNewActivity.putExtra("id", id);
                            startNewActivity.putExtra("title",title);
                            startNewActivity.putExtra("date",date);
                            startNewActivity.putExtra("ImagePath",ImagePath);
                            startActivity(startNewActivity);
                        }
                    }
            );
        }
    }

    public void delete_voyage(){
        final AppCompatActivity context = this;
        Supprimer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB.deleteVoyage(voyageId);
                        if(deletedRows > 0) {
                            Toast.makeText(viewvoyage.this, "Voyage supprimé", Toast.LENGTH_LONG).show();

                            finish();
                            Intent startNewActivity = new Intent (context, HomePage.class);
                            startActivity(startNewActivity);
                        }
                        else
                            Toast.makeText(viewvoyage.this, "Voyage non supprimé", Toast.LENGTH_LONG).show();
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
