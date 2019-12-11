package net.mbiztech.webtoapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.mbiztech.webtoapp.model.MainActivityModel;
import net.mbiztech.webtoapp.model.MainActivityModelImp;
import net.mbiztech.webtoapp.presenter.MainActivityPresenter;
import net.mbiztech.webtoapp.presenter.MainActivityPresenterImp;
import net.mbiztech.webtoapp.R;
import net.mbiztech.webtoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityView {
    private static final int PHONE_STATE_PERMISSION_REQUEST_CODE = 101;
    private int user_id = 0;
    private MainActivityPresenter presenter;
    private MainActivityModel model;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        model = new MainActivityModelImp(getApplicationContext());
        presenter = new MainActivityPresenterImp(this,model);


        Intent intent = getIntent();

        if (intent.getData() != null){
            Uri data = intent.getData();
            String scheme = data.getScheme();
            String host = data.getHost();
            List<String> pathSegments = data.getPathSegments();
            String prefix = pathSegments.get(0);
            String id = pathSegments.get(1);

            user_id = Integer.parseInt(id);
            Log.d("mytag", "onCreate: scheme: "+scheme+", host: "+host+", prefix: "+prefix+", id:"+id);

        }else{
            Log.d("mytag", "onCreate: null intent, user call");
        }

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onReceiveUserId(user_id);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mytag", "onResume: welcome ");
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();

        presenter.onReceiveUserId(user_id);


    }


    @Override
    public void handleProgressBarVisibility(boolean visible) {


        if (visible) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.progressBar.setIndeterminate(true);
        }
        else binding.progressBar.setVisibility(View.GONE);




    }

    @Override
    public void setMessage(String message) {

        binding.tvMessage.setText(message);
    }

    @Override
    public void onImeiSendSuccess(String message) {

        binding.tvMessage.setText(message);
        user_id = 0;

    }

    @Override
    public void onImeiSendFailed(String message) {
        binding.tvMessage.setText(message);
    }

    @Override
    public void toggleRetryButtonVisibility(boolean visible) {

        if (visible) binding.button.setVisibility(View.VISIBLE);
        else binding.button.setVisibility(View.GONE);
    }

    @Override
    public void askPhoneStatePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},PHONE_STATE_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PHONE_STATE_PERMISSION_REQUEST_CODE){
            if (permissions[0]== Manifest.permission.READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                presenter.onReceiveUserId(user_id);
            }else if (permissions[0]== Manifest.permission.READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_DENIED){

                setMessage("Permission denied. To continue please allow app to read phone state from settings.");
                toggleRetryButtonVisibility(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();

    }
}
