package com.example.app_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;
    ImageView imageViewIcon;
   AlertDialog.Builder alertDialogBuilder1;
    AlertDialog dialog;
    Button btn_delete_yes,btn_delete_no;
    TextView dlt_text1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageViewIcon= findViewById(R.id.row_image);
        mListView = findViewById(R.id.listView1);
        mList = new ArrayList<Model>();
        mAdapter = new RecordListAdapter(this, R.layout.row_layout, mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >=0){
                    Cursor c = MainActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
                    ArrayList<Integer> arrID = new ArrayList<Integer>();
                    while(c.moveToNext()){
                        arrID.add(c.getInt(0));

                    }

                    showDiologBox(arrID.get(position));
                    Toast.makeText(SecondActivity.this, "working", Toast.LENGTH_SHORT).show();


                }
            }
        });

        Cursor cursor = MainActivity.mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String age = cursor.getString(2);
            String phone = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            mList.add(new Model(id, name, age, phone, image));
        }
        mAdapter.notifyDataSetChanged();
        if (mList.size() == 0) {
            Toast.makeText(this, "no items found", Toast.LENGTH_SHORT).show();
        }

    }

    private void showDiologBox(final int id) {
        Toast.makeText(SecondActivity.this, "works", Toast.LENGTH_SHORT).show();
        alertDialogBuilder1= new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.delete_dialog,null);
        dlt_text1 = (TextView) view.findViewById(R.id.dlt_text) ;
        btn_delete_yes = (Button) view.findViewById(R.id.dlt_button_yes);
        alertDialogBuilder1.setView(view);
        dialog = alertDialogBuilder1.create();
        dialog.show();
        btn_delete_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            MainActivity.mSQLiteHelper.deleteData(id);
                Log.d("deleted item count",String.valueOf(MainActivity.mSQLiteHelper.getDataCount()));
                Toast.makeText(SecondActivity.this, "deleting..", Toast.LENGTH_SHORT).show();
           Intent np7 = new Intent(SecondActivity.this,MainActivity.class);
           startActivity(np7);
            }
        });

    }

}
