package com.example.gscct.triceratopstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddVoyage extends AppCompatActivity {
    DataBase myDB;
    EditText editName, editDate;
    Button btnValider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvoyage);
        myDB = new DataBase(this);

        editName = findViewById(R.id.editText_name);
        editDate = findViewById(R.id.editText_date);
        btnValider = findViewById(R.id.button4);
        addData();
    }

    public void addData(){
        btnValider.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertVoyage(editName.getText().toString(),
                                editDate.getText().toString());
                        if(isInserted == true) {
                            Toast.makeText(AddVoyage.this, "Voyage enregistré", Toast.LENGTH_LONG).show();
                            retour();
                        }
                        else
                            Toast.makeText(AddVoyage.this, "Voyage non enregistré", Toast.LENGTH_LONG).show();
                    }
                }

        );
    }


    public void retour(){
        finish();
    }
}
