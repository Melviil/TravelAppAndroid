package com.example.gscct.triceratopstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddLieu extends AppCompatActivity {
    DataBase2 myDB;
    EditText editName, editDate;
    Button btnValider;
    Button btnRetour;
    int idVoyage;
    int idLieu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlieu);
        myDB = new DataBase2(this);
        idVoyage = getIntent().getIntExtra("id", 0);
        editName = findViewById(R.id.editText_name3);
        editDate = findViewById(R.id.editText2);
        btnValider = findViewById(R.id.button9);
        btnRetour = findViewById(R.id.button10);

        addLieu();
    }


    public void addLieu(){
        btnValider.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = idVoyage;
                        boolean isInserted = myDB.insertLieu(id,
                                editName.getText().toString(),
                                editDate.getText().toString());

                        if(isInserted == true) {
                            Toast.makeText(AddLieu.this, "Lieu enregistré", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                            Toast.makeText(AddLieu.this, "Lieu non enregistré", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
/*
    public void UpdateData() {
        btnValider.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDB.updateData(editName.getText().toString(),
                                editDate.getText().toString(),idVoyage);
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
*/
    public void retour(){
        finish();
    }

}
