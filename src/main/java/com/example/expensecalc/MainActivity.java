package com.example.expensecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    dbhelper db;
    TextView Day;
    Button Date,Add;
    EditText Amount, Notes;
    Spinner cat;
    String scat;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new dbhelper(this);
        cursor= db.view_expenses();
        while (cursor.moveToNext()){
            Log.d("exp_id",cursor.getString(0));
            Log.d("exp_amt",cursor.getString(1));
            Log.d("exp_date",cursor.getString(2));
            Log.d("exp_note",cursor.getString(3));
            Log.d("exp_cat",cursor.getString(4));
        }

        Date = (Button) findViewById(R.id.button);
        Add=(Button)findViewById(R.id.button2);
        Day = (TextView) findViewById(R.id.textView);
        Amount = (EditText) findViewById(R.id.editText);
        Notes = (EditText) findViewById(R.id.editText3);
        cat = (Spinner) findViewById(R.id.spinner);
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                scat= String.valueOf(adapterView.getItemAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Date.setText(get_todays_date());


        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });
        Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = Amount.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG);
                msg.show();
            }
        });
        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = Notes.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(), str1, Toast.LENGTH_LONG);
                msg.show();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = db.insertData(Float.parseFloat(Amount.getText().toString()), Date.getText().toString(),Notes.getText().toString(),scat);
                if (isInserted = true) {
                    Toast.makeText(MainActivity.this, "Expense for the day is Inserted", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(MainActivity.this, "Expense for the day is not Inserted", Toast.LENGTH_LONG).show();
                }

            }
        });
            }





    public String get_todays_date() {
        Calendar calendar = Calendar.getInstance();

        int thisYear = calendar.get(Calendar.YEAR);

        int thisMonth = calendar.get(Calendar.MONTH);

        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);

        return String.valueOf(thisDay) + "-" + String.valueOf(thisMonth) + "-" + String.valueOf(thisYear);
    } → W


}
