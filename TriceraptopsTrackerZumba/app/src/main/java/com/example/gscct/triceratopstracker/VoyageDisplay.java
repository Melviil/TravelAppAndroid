package com.example.gscct.triceratopstracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class VoyageDisplay extends AppCompatActivity {
    DataBase myDB;
    LinearLayout ListeVoyage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voyagedisplay);
        myDB = new DataBase(this);
        ListeVoyage = findViewById(R.id.layout_voyage);

    }

    @Override
    public void onResume(){
        super.onResume();
        viewAll();
    }
    public void viewAll(){
        final AppCompatActivity context = this;
        final Cursor res = myDB.getAllVoyage();
        if(res.getCount() == 0){
            showMessage("Erreur", "Pas de données");
            return;
        }
        while(res.moveToNext()){
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(res.getString(1));
            btnTag.setId(Integer.parseInt(res.getString(0)));
            ListeVoyage.addView(btnTag);
            final String title = res.getString(1);
            final int id = Integer.parseInt(res.getString(0));
            final String date = res.getString(2);
            btnTag.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent startNewActivity = new Intent (context, viewvoyage.class);
                            startNewActivity.putExtra("id", id);
                            startNewActivity.putExtra("title",title);
                            startNewActivity.putExtra("date",date);
                            startActivity(startNewActivity);
                        }
                    }
            );
        }
    }

    public void showMessage(String title, String Msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Msg);
        builder.show();
    }


}
