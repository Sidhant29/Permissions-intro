package com.example.sidhant.permissionstutorial;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btAdd,btRetrieve;
    EditText etText;
    TextView tvText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = (TextView) findViewById(R.id.tvText);
        etText = (EditText) findViewById(R.id.etText);
        btAdd = (Button) findViewById(R.id.btAdd);
        btRetrieve= (Button) findViewById(R.id.btRetrieve);
        btRetrieve.setOnClickListener(this);
        btAdd.setOnClickListener(this);
    }

    void writeFile(String data)   {
        File f = new File(Environment.getExternalStorageDirectory(),"file.txt") ;
        try {
            FileOutputStream fos = new FileOutputStream(f);
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
    String retrieveData() {
        File f = new File(Environment.getExternalStorageDirectory(),"file.txt");
        String buf = "";
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            while (buf != null) {
                sb.append(buf + "\n");
                buf = br.readLine();
            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

        return sb.toString();

    }
    @Override
    public void onClick(View view) {
        int perm =ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);



        switch(view.getId()){
            case R.id.btAdd:
                switch (perm){
                case PackageManager.PERMISSION_GRANTED:
                    writeFile(etText.getText().toString());
                    break;
                    case  PackageManager.PERMISSION_DENIED:
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },324
                        );
                        break;
                }

                break;
            case R.id.btRetrieve:
                Toast.makeText(this, "r", Toast.LENGTH_SHORT).show();
                tvText.setText(retrieveData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==324){
            for(int i=0;i<permissions.length;i++){
                if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        writeFile(etText.getText().toString());
                    }
                    else{
                        Toast.makeText(this, "Permission to dedo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
}
