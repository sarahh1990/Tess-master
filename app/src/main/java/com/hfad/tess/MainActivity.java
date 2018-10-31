package com.hfad.tess;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    Button btnGetAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView aktivtetsliste = findViewById(R.id.aktivtetsliste);

        btnGetAll = (Button)findViewById(R.id.btnGetAll);
        viewAll();


        //Database
        SQLiteOpenHelper dbHelper = new DBHelper(this);
        //Prøver å opprette database og hente ut data. Sender feilmelding hvis det ikke går

        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("aktivitet", new String[] {"_id", "navn", "beskrivelse"},null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"navn"}, new int[]{android.R.id.text1},0);
            aktivtetsliste.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast dbToast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }
    } // end onCreate()

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.action_se_kart:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void viewAll(){
        btnGetAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res = db.getAllAktivitet();
                       //if ==0, finnes ikke noe data/aktiviteter i databasen
                       if(res.getCount()== 0){
                           showMessage("Error","Ingen aktiviteter funnet");
                           return;
                       }
                       StringBuffer buffer = new StringBuffer();
                       while(res.moveToNext()){
                           buffer.append("_Id:"+res.getString(0)+"\n");
                           buffer.append("navn:"+res.getString(0)+"\n");
                       }
                       //hvis alle aktiviteter
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

