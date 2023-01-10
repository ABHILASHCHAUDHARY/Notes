package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

ListView listView;
static ArrayList<String> arrayList;
 static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;

public void onClick(View view){
    Intent intent = new Intent(getApplicationContext(),NotesAdd.class);
    startActivity(intent);
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         Intent intent = new Intent(getApplicationContext(),NotesAdd.class);
        startActivity(intent);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<String>();
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

     Intent intent = new Intent(getApplicationContext(),NotesAdd.class);
        listView = findViewById(R.id.ListView);

        HashSet<String> set =(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set== null){
            arrayList.add("You can type your notes here");
        }else {
            arrayList = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                int itemTodelete = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrayList.remove(itemTodelete);
                                arrayAdapter.notifyDataSetChanged();


                                HashSet<String> set = new HashSet<>(MainActivity.arrayList);
                                sharedPreferences.edit().putStringSet("notes",set).apply();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });

    }
}