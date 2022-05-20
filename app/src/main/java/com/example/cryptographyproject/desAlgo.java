package com.example.cryptographyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.style.TabStopSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class desAlgo extends Fragment {

    EditText keytext;
    EditText normaltext;
    EditText ciphertext;
    EditText decrypted;
    Button copy_cipher;
    Button encrypt;
    Button decrypt;
    Button delete_normal,genKey;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.des_layout,container,false);
        decrypted= view.findViewById(R.id.decText);
        normaltext = view.findViewById(R.id.msgText);
        keytext = view.findViewById(R.id.secretKey);
        ciphertext = view.findViewById(R.id.encText);
        copy_cipher = view.findViewById(R.id.copyBt);
        encrypt = view.findViewById(R.id.Encrypt);
        decrypt = view.findViewById(R.id.Decrypt);
        delete_normal = view.findViewById(R.id.clearBt);
        genKey=view.findViewById(R.id.genKey);


        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(normaltext.getText().toString().isEmpty()||keytext.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter values in the fields!!", Toast.LENGTH_SHORT).show();

                }
                else if(keytext.getText().toString().length()!=8) {
                    Toast.makeText(getActivity(),"Enter key of 8 Characters!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ciphertext.setText(encrypt(normaltext.getText().toString(),getActivity()));
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ciphertext.getText().toString().matches("")||keytext.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(), "Please enter values in the fields!!", Toast.LENGTH_SHORT).show();
                }
                else if(keytext.getText().toString().length()!=8) {
                    Toast.makeText(getActivity(),"Enter key of 8 Characters!", Toast.LENGTH_SHORT).show();

                }
                else {
                    decrypted.setText(decrypt(ciphertext.getText().toString(),getActivity()));
                }
            }
        });

        copy_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ciphertext.getText().toString().isEmpty() || !keytext.getText().toString().isEmpty())
                    normaltext.setText(ciphertext.getText().toString());
                else{
                    Toast.makeText(getActivity(), "Please encrypt before copying", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 clear();
            }
        });
        genKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keytext.setText(genKey());
                genKey.setEnabled(false);
            }
        });
        return view;
    }

    private String genKey()
    {
        String key="";
        List<Integer> values= new ArrayList<Integer>();
        for(int i=0;i<=9;i++)
            values.add(i);
        Collections.shuffle(values);

        for(int i=0;i<8;i++) {
            key+=values.get(i);
        }

        return key;
    }
    private void clear()
    {
        normaltext.setText("");
        keytext.setText("");
        ciphertext.setText("");
        decrypted.setText("");
        genKey.setEnabled(true);
    }
    private String decrypt(String value, Context c) {
        String coded;
        String result=null;
        if(value.startsWith("code==")){
            coded=value.substring(6, value.length()).trim();
        }
        else{
            coded=value.trim();
        }
        try{
            byte[] bytesDecoded = Base64.decode(coded.getBytes("UTF-8"),Base64.DEFAULT);
            SecretKeySpec key = new SecretKeySpec(keytext.getText().toString().getBytes(),"DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] textDecrypted = cipher.doFinal(bytesDecoded);
            result  = new String(textDecrypted);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (NoSuchPaddingException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (IllegalBlockSizeException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (BadPaddingException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());

            return "Encrypt Error";
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());

            return "Encrypt Error";
        }
        catch (Exception e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());

            return "Encrypt Error";
        }
        return result;

    }

    private String encrypt(String value, Context c){
        String crypted="";
        try{
            byte[] cleartext= value.getBytes("UTF-8");
            SecretKeySpec key = new SecretKeySpec(keytext.getText().toString().getBytes(),"Des");
            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            crypted = Base64.encodeToString(cipher.doFinal(cleartext),Base64.DEFAULT);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (NoSuchPaddingException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (IllegalBlockSizeException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (BadPaddingException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (InvalidKeyException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch (Exception e){
            e.printStackTrace();
            app.DialogMaker(c, "Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        return crypted;
    }
}


class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration newConfig = new Configuration();
        newConfig.locale = Locale.ENGLISH;
        super.onConfigurationChanged(newConfig);
        Locale.setDefault(newConfig.locale);
        getBaseContext().getResources().updateConfiguration(newConfig, getResources().getDisplayMetrics());

    }

    public static void Loger(String m) {
        Log.e("mip tag", m);
    }

    public static void ToastMaker(Context c, String m) {
        Toast.makeText(c, m, Toast.LENGTH_SHORT).show();
    }

    public static void DialogMaker(Context c, String title, String mes) {
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setTitle(title);
        alert.setMessage(mes);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }
}
