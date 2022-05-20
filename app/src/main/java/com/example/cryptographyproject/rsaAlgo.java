package com.example.cryptographyproject;

import android.app.Notification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;


public class rsaAlgo extends Fragment implements View.OnClickListener {
    public Button genKey,encrypt,decrypt,reset;
    public EditText mesg,puk,prik,printencMsg,encMsgText,printMsg;
    public TextView pk,prk;
    public int p,q,n,z,d=0,i;
    public double c;
    public BigInteger msgBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rsa_algorithm, container, false);
        genKey =(Button) view.findViewById(R.id.genKey);
        genKey.setOnClickListener(this);
        encrypt =(Button) view.findViewById(R.id.button2);
        decrypt =(Button) view.findViewById(R.id.button3);
        decrypt.setOnClickListener(this);
        reset=view.findViewById(R.id.resetKey);
        pk =view.findViewById(R.id.textView3);
        prk =view.findViewById(R.id.textView4);
        puk=view.findViewById(R.id.pubicKey);
        prik =view.findViewById(R.id.privateKey);
        mesg=view.findViewById(R.id.messageInt);
        printencMsg=view.findViewById(R.id.encMsg);
        printMsg= view.findViewById(R.id.decMsg);
        encMsgText=view.findViewById(R.id.encMsgTxt);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pk.setText("");
                prk.setText("");
                puk.setText("");
                prik.setText("");
                mesg.setText("");
                printMsg.setText("");
                printencMsg.setText("");
                encMsgText.setText("");
                genKey.setEnabled(true);
                p=0;q=0;d=0;n=0;z=0;i=0;
                c=0;
                msgBack=null;

            }
        });
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((mesg.getText().toString().isEmpty() || puk.getText().toString().isEmpty()) )
                {
                    Toast.makeText(getActivity(), "Please Fill in the text Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    int msg = Integer.parseInt(mesg.getText().toString());

                    if (msg >= Integer.MAX_VALUE || msg == Integer.MAX_VALUE || msg <= 0) {
                        Toast.makeText(getActivity(), "Entered Message is Over-sized!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        int e = Integer.parseInt(puk.getText().toString());
                        if (e == (Integer.parseInt(pk.getText().toString()))) {
                            c = (Math.pow(msg, e)) % n;
                            printencMsg.setText(Double.toString(c));
                        } else {
                            Toast.makeText(getActivity(), "Please enter valid Public Key", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (encMsgText.getText().toString().isEmpty() || prik.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Please Fill in the text Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int e=Integer.parseInt(prk.getText().toString());
                    if(e==(Integer.parseInt(prik.getText().toString())))
                    {
                        BigInteger N = BigInteger.valueOf(n);

                        // converting float value of c to BigInteger
                        BigInteger C = BigDecimal.valueOf(Integer.parseInt(encMsgText.getText().toString())).toBigInteger();
                        msgBack = (C.pow(Integer.parseInt(prik.getText().toString()))).mod(N);
                        printMsg.setText(msgBack.toString());
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please enter valid Public Key", Toast.LENGTH_SHORT).show();
                        prik.setText("");
                    }

                }


            }
        });

        return view;


    }
    @Override
    public void onClick(View v) {
        int e;

            p=primeGen();
            q=primeGen();

            if((p>0 && q>0) && (q>p && q!=p) && q/p!=0) {
                n = p * q;
                z = (p - 1) * (q - 1);

                for (e = 2; e < z; e++) {

                    // e is for public key exponent
                    if (gcd(e, z) == 1) {
                        break;
                    }
                }
                pk.setText(Integer.toString(e));
                for (i = 0; i <= 9; i++) {
                    int x = 1 + (i * z);

                    // d is for private key exponent
                    if (x % e == 0) {
                        d = x / e;
                        break;
                    }
                }
                prk.setText(Integer.toString(d));
            }
            else{
                Toast.makeText(getActivity(), "Appropriate values not found, Please Try Again!!", Toast.LENGTH_SHORT).show();
            }

        if(!(pk.getText().toString()).isEmpty() && !(prk.getText().toString()).isEmpty() )
            genKey.setEnabled(false);
    }
    private int gcd(int e,int z)
    {
        if(e==0)
            return z;
        else
            return gcd(z%e,e);
    }

    private int primeGen() {
        int[] primeNumbers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        int pos;

        Random random = new Random();
        try {
            pos= random.nextInt(primeNumbers.length);
            return primeNumbers[pos];
        }
        catch(Exception e){
            Toast.makeText(getActivity(), "Please Retry ", Toast.LENGTH_SHORT).show();
        }

        return 0;
    }

}
