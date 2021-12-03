package com.example.storage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private final static String STORETEXT = "storage.txt";
    private TextView textView;
    private EditText editText;
    private Button btn_saveint;
    private Button btn_readint;
    private Button btn_saveext;
    private Button btn_readext;
    String myData = "";

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        btn_saveint = (Button) findViewById(R.id.btn_saveint);
        btn_readint = (Button) findViewById(R.id.btn_readint);
        btn_saveext = (Button) findViewById(R.id.btn_saveext);
        btn_readext = (Button) findViewById(R.id.btn_readext);

        btn_saveint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OutputStreamWriter out = new OutputStreamWriter(openFileOutput(STORETEXT, 0));
                    out.write(editText.getText().toString());
                    out.close();
                    Toast.makeText(getBaseContext(), "Success saved Internal Storage!", Toast.LENGTH_SHORT).show();
                    editText.getText().clear();
                } catch (Throwable t) {
                    Toast.makeText(getBaseContext(),
                            "Error:" + t.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_readint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream in = openFileInput(STORETEXT);
                    if (in != null) {
                        InputStreamReader tmp = new InputStreamReader(in);
                        BufferedReader reader = new BufferedReader(tmp);

                        String str;
                        StringBuilder buf = new StringBuilder();

                        while ((str = reader.readLine()) != null) {
                            buf.append(str);
                        }
                        in.close();
                        editText.setText(buf.toString());
                    }

                } catch (java.io.IOException e) {

                }
            }
        });


        btn_saveext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File myFile = new File("/sdcard/storage.txt");
                    myFile.createNewFile();

                    FileOutputStream fOut = new FileOutputStream(myFile);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(editText.getText());
                    myOutWriter.close();
                    fOut.close();

                    Toast.makeText(getBaseContext(), "Success saved External Storage", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "Error:" + e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_readext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File myFile = new File("/sdcard/storage.txt");
                    myFile.createNewFile();
                    FileInputStream fis = new FileInputStream(myFile);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = strLine;

                        editText.setText(myData);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}