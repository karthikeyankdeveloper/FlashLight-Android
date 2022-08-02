package com.developer.flashlightadnew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean back = true;
    private ImageView imageView;
    private TextView textView;
    private CameraManager cameraManager;
    private String id;
    private ViewGroup parent;
    private boolean condition=true;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView_on_off);
        textView=findViewById(R.id.textView_on_off);

        boolean available = getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!available){
            NoAvailable();
        }

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            id=cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(id,false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cameraManager.setTorchMode(id,condition);

                    if(condition){
                        textView.setText("ON");
                        imageView.setImageResource(R.drawable.torchon);
                        condition=false;
                    }else {
                        textView.setText("OFF");
                        imageView.setImageResource(R.drawable.torchoff);
                        condition=true;
                    }

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void NoAvailable() {
        imageView.setEnabled(false);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert,parent,false);
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setView(view);
        alertDialog.setCancelable(false);
        alertDialog.show();

        Button button = view.findViewById(R.id.button_alert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back){
            back=false;
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    back=true;
                }
            },3000);
        }
        else {
            super.onBackPressed();
        }
    }
}