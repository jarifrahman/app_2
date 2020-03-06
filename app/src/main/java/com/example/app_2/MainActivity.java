package com.example.app_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView tv_1;
    EditText name1, age1, phone1;
    ImageView image1;
    Button saveButton, nextPage;

    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
   // DatabaseHandler objectDatabaseHandler;

    public static SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_1 = (TextView) findViewById(R.id.tv_11);
        name1=(EditText) findViewById(R.id.imageName1);
        age1 = (EditText) findViewById(R.id.age);
        phone1= (EditText) findViewById(R.id.phone);
        image1 =(ImageView) findViewById(R.id.addImage1);
        saveButton = (Button) findViewById(R.id.saveButton1);
        nextPage = (Button) findViewById(R.id.nextPage1);
        // create database
        mSQLiteHelper = new SQLiteHelper(this,"RECORDDB.sqlite",null,1);
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR, age VARCHAR, phone VARCHAR, image BLOB)");


       // objectDatabaseHandler = new DatabaseHandler(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeImage(v);

            }
        });
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent next = new Intent(MainActivity.this,SecondActivity.class);
               startActivity(next);
            }
        });


    }


    public void chooseImage(View objectView){
        try{
            Intent objectIntent = new Intent ();
            objectIntent.setType("image/*");
            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);

        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&& resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imageFilePath= data.getData();
            try {
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image1.setImageBitmap(imageToStore);
        }
    }

    public void storeImage(View view){
        try{
            if(!name1.getText().toString().isEmpty()
                    // && image1.getDrawable()!=null
            ){
                mSQLiteHelper.insertData(name1.getText().toString(),
                        age1.getText().toString(),phone1.getText().toString(),
                        imageTobyte(image1));
                // reset
                name1.setText("");
                age1.setText("");
                phone1.setText("");
                image1.setImageResource(R.drawable.ic_launcher_background);

                Log.d("items added:",String.valueOf(mSQLiteHelper.getDataCount()));
              //  Intent np_11 = new Intent(MainActivity1.this,showImageActivity.class);
               // startActivity(np_11);
                }
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public static byte[] imageTobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}

