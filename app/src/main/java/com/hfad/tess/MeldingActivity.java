package com.hfad.tess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.widget.Toast;

public class MeldingActivity extends AppCompatActivity {
    Button send;
    EditText phone, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melding);
        send = (Button)findViewById(R.id.btnsend);
        phone = (EditText)findViewById(R.id.txtPhone);
        message =(EditText)findViewById(R.id.txtMessage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmessage();

            }
        });
    }
    public void sendmessage(){
        String number = phone.getText().toString().trim();
        String mess = message.getText().toString().trim();
        if(number==null || number.equals("")|| mess==null|| mess.equals("")){
            Toast.makeText(this,"linjen kan ikke være tom",Toast.LENGTH_LONG).show();
        }
        else {
            if (TextUtils.isDigitsOnly(number)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number,null,mess,null,null);
                Toast.makeText(this,"melding sendt"+number, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Kun tall",Toast.LENGTH_LONG).show();
            }
        }

    }
}
