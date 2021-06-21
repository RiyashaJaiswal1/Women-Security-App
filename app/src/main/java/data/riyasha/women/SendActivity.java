package data.riyasha.women;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;


public class SendActivity extends manu {
Button b1;
    private final static int SEND_SMS_PERMISSION_REQ = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        getSupportActionBar().hide();
        b1=findViewById(R.id.btn_n);
        if(checkPermission())
        {
            b1.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }
        b1.setOnClickListener(v -> {
            String s1=e1.getText().toString();
            String s2=e2.getText().toString();
            if(!TextUtils.isEmpty(s1)&&!TextUtils.isEmpty(s2))
            {

                if(checkPermission())
                {
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(s1,null,s2,null,null);
                    Toast.makeText(SendActivity.this,"messagesent!!!",Toast.LENGTH_LONG).show();}
                else {
                    Toast.makeText(SendActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(SendActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean checkPermission() {

        int checkpermission= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        return checkpermission== PackageManager.PERMISSION_GRANTED;
    }
}