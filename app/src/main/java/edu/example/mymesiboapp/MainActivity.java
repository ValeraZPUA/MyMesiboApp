package edu.example.mymesiboapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboAudioCallFragment;
import com.mesibo.calls.MesiboVideoCallFragment;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.calls.MesiboCall;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements Mesibo.MessageListener,
        Mesibo.ConnectionListener, MesiboCall.MesiboCallListener {

    private Button button, btnCall;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = findViewById(R.id.btn);
        editText = findViewById(R.id.et);
        textView = findViewById(R.id.tv);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag", "onClick");
                Mesibo.MessageParams p = new Mesibo.MessageParams();
                //p.peer = "10";
                p.peer = "20";
                p.flag = Mesibo.FLAG_READRECEIPT | Mesibo.FLAG_DELIVERYRECEIPT;

                Mesibo.sendMessage(p, Mesibo.random(), editText.getText().toString());
            }
        });

        btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener( v -> {
            Log.d("tag", "CALL");


            Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
            userProfile.name = "10";
            userProfile.address = "10";
            MesiboCall.getInstance().call(MainActivity.this, Mesibo.random(), userProfile, true);
        });


        Mesibo api = Mesibo.getInstance();
        api.init(this);

        api.addListener(this);

        // set user authentication token obtained by creating user
        //api.setAccessToken("705ed4d063ef87b4dfb40c5630810498de30e93acca0fe3f90eee128702"); //10
        api.setAccessToken("89579e45be71c19ecf4adb9b51fac7aba6e38b41628edd128576"); //20
        //api.setAccessToken("bfce31d2dd42d32fa85ad5162702c4675aa5e308abd8e66128704"); //30
        api.start();

       /* MesiboCall mesiboCall = MesiboCall.getInstance();
        mesiboCall.init(this);

        Mesibo.UserProfile userProfile = new Mesibo.UserProfile();
        userProfile.name = "User Name";
        userProfile.address = "User address";
        MesiboCall.getInstance().call(MainActivity.this, Mesibo.random(), userProfile, true);*/



    }

    @Override
    public void Mesibo_onConnectionStatus(int status) {
        // You will receive the connection status here
        Log.d("tag", "on Mesibo Connection: " + status);

        MesiboCall mesiboCall = MesiboCall.getInstance();
        mesiboCall.init(this);
        mesiboCall.setListener(this);

        //MesiboUI.launch(this, 0, false, true); //launch mesibo default UI
    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
        Log.d("tag", "Mesibo_onMessage");
        Log.d("tag", "on Mesibo Connection: " + new String(bytes, StandardCharsets.UTF_8));
        textView.setText(new String(bytes, StandardCharsets.UTF_8));
        return false;
    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {
        Log.d("tag", "Mesibo_onMessageStatus");
    }

    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }

    @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public Fragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }
}
