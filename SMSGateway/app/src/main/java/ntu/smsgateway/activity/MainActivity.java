package ntu.smsgateway.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import ntu.smsgateway.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text = (EditText)findViewById(R.id.firebasetoken);
        text.setText(FirebaseInstanceId.getInstance().getToken());
    }
}
