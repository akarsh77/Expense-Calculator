package com.example.expensecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class view_expense extends AppCompatActivity {
    FloatingActionButton add;
    Cursor cursor = null;
    dbhelper dbhelper;
    ListView listView;
    Button date;

    ArrayList<HashMap<String, String>> detaillist;
    private static final String tag1 = "id";
    private static final String tag2 = "cat";
    private static final String tag3 = "amount";
    private static final String tag4 = "notes";
    private static final String tag5 = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        listView = findViewById(R.id.listview);
        date = (Button) findViewById(R.id.date);
        detaillist = new ArrayList<HashMap<String, String>>();
        dbhelper = new dbhelper(this);
        cursor = dbhelper.view_expenses();

        while (cursor.moveToNext()) {
            Log.d("valuies_",cursor.getString(0));
            HashMap<String, String> detaillist1 = new HashMap<String, String>();
            detaillist1.put(tag1, cursor.getString(0));
            detaillist1.put(tag2, cursor.getString(4));
            detaillist1.put(tag3, cursor.getString(1));
            detaillist1.put(tag4, cursor.getString(3));
            detaillist1.put(tag5, cursor.getString(2));
            detaillist.add(detaillist1);
        }
        ListAdapter adapter1 = new SimpleAdapter(view_expense.this, detaillist, R.layout.list_item,
                new String[]{tag1, tag2, tag3, tag4, tag5},
                new int[]{R.id.id, R.id.cat, R.id.amount, R.id.notes, R.id.date});
        listView.setAdapter(adapter1);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(view_expense.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                cursor = dbhelper.view_expenses(date.getText().toString());
                                Log.d("cursor_size",String.valueOf(cursor.getCount()));

                                while (cursor.moveToNext()) {
                                    Log.d("valuies_",cursor.getString(0));
                                    HashMap<String, String> detaillist1 = new HashMap<String, String>();
                                    detaillist1.put(tag1, cursor.getString(0));
                                    detaillist1.put(tag2, cursor.getString(4));
                                    detaillist1.put(tag3, cursor.getString(1));
                                    detaillist1.put(tag4, cursor.getString(3));
                                    detaillist1.put(tag5, cursor.getString(2));
                                    detaillist.add(detaillist1);
                                }
                                ListAdapter adapter1 = new SimpleAdapter(view_expense.this, detaillist, R.layout.list_item,
                                        new String[]{tag1, tag2, tag3, tag4, tag5},
                                        new int[]{R.id.id, R.id.cat, R.id.amount, R.id.notes, R.id.date});
                                listView.setAdapter(adapter1);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();





                add = (FloatingActionButton) findViewById(R.id.fab);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view_expense.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }


        });

    }
}