package com.longkd.android.core.demosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate, btBack, btRemove;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        initView();

        btBack.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);

        item =(Item) getIntent().getSerializableExtra("item");

        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());
        int p= 0;
        for (int i = 0; i< sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())) {
                p = i;
                break;
            }
        }

        sp.setSelection(p);

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btnUpdate);
        btBack = findViewById(R.id.btnBack);
        btRemove = findViewById(R.id.btnRemove);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View v) {
        if (v == btBack) {
            finish();
        }

        if (v == btUpdate) {
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();

            if (!t.isEmpty() && p.matches("\\d+")) {
//                Item i = new Item(t, c, p, d);
                item.setTitle(t);
                item.setCategory(c);
                item.setPrice(p);
                item.setDate(d);
                SQLiteHelper db = new SQLiteHelper(this);
                db.updateItem(item);
                finish();
            }
        }

        if (v == btRemove) {
            int id = item.getId();
            SQLiteHelper db = new SQLiteHelper(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("thong baos xoa");
            builder.setMessage("Ban co chac muon xoa " + item.getTitle() + " khong?");
            builder.setIcon(R.drawable.ic_add);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteItem(id);
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}