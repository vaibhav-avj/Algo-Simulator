package com.example.cryptographyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public DrawerLayout dl;
    public ActionBarDrawerToggle abdt;

    final String about_us="" +
            "About us:\n" +
            "        This application is made to simulate the Cryptography algorithms. Algorithms used are RSA, Base64, DES.\n\n" +
            "In this application:\n" +
            "       RSA algorithm is implemented as an encryption method using mathematical formula, which derives Public and Private Keys.\n" +
            "       Base64 is implemented as an Image Encryption method, where image is encrypted into string.\n" +
            "       DES algorithm shows the encryption and Decryption of String messages using a common public key.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView abt = (TextView) findViewById(R.id.textView12);
        abt.setText(about_us);


        dl = findViewById(R.id.drawLayout);
        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        abdt= new ActionBarDrawerToggle(this,dl,R.string.nav_open,R.string.nav_close);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homePage()).commit();
            nv.setCheckedItem(R.id.homePage);
        }


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.homePage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homePage()).commit();
                break;
            case R.id.rsaAlgo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new rsaAlgo()).commit();
                break;
            case R.id.base64:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new base64()).commit();
                break;
            case R.id.desAlgo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new desAlgo()).commit();
                break;

        }

        dl.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}