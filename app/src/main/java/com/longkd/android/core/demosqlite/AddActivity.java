package com.longkd.android.core.demosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.longkd.android.core.demosqlite.dal.SQLiteHelper;
import com.longkd.android.core.demosqlite.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate, btCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitleAdd);
        ePrice = findViewById(R.id.tvPriceAdd);
        eDate = findViewById(R.id.tvDateAdd);
        btUpdate = findViewById(R.id.btnUpdate);
        btCancel = findViewById(R.id.btnCancel);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));


        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date = "";
                        if (m>8) {
                            date = d + "/" + (m+1) + "/" + y;
                        } else {
                            date = d + "/0" + (m+1) + "/" + y;
                        }
                        eDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == eDate) {

        }

        if (v == btCancel) {
            finish();
        }

        if (v == btUpdate) {
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();

            if (!t.isEmpty() && p.matches("\\d+")) {
                Item i = new Item(t, c, p, d);

                SQLiteHelper db = new SQLiteHelper(this);
                db.insertItem(i);
                finish();
            }
        }

    }
}