package data.riyasha.women;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

public class CallActivity extends manu {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        call=findViewById(R.id.btn);
        getSupportActionBar().hide();
        call.setOnClickListener(View->{
            String number=e1.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            if (ContextCompat.checkSelfPermission(  this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //You already have permission
                try {
                    startActivity(callIntent);
                } catch(SecurityException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}