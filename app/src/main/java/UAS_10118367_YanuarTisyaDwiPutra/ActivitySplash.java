package UAS_10118367_YanuarTisyaDwiPutra;

//06-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import UAS_10118367_YanuarTisyaDwiPutra.R;
import UAS_10118367_YanuarTisyaDwiPutra.connection.RestAdapter;
import UAS_10118367_YanuarTisyaDwiPutra.connection.callbacks.CallbackDevice;
import UAS_10118367_YanuarTisyaDwiPutra.data.SharedPref;
import UAS_10118367_YanuarTisyaDwiPutra.model.DeviceInfo;
import UAS_10118367_YanuarTisyaDwiPutra.utils.PermissionUtil;
import UAS_10118367_YanuarTisyaDwiPutra.utils.Tools;
import retrofit2.Call;
import retrofit2.Response;

public class ActivitySplash extends AppCompatActivity {

    private SharedPref sharedPref;
    private View parent_view;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        parent_view = findViewById(R.id.parent_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sharedPref = new SharedPref(this);
        Tools.initImageLoader(getApplicationContext());
        parent_view.setBackgroundColor(sharedPref.getThemeColorInt());

        if (Tools.needRequestPermission()) {
            String[] permission = PermissionUtil.getDeniedPermission(this);
            if (permission.length != 0) {
                requestPermissions(permission, 200);
            } else {
                initGcmData();
            }
        } else {
            initGcmData();
        }

        Tools.systemBarLolipop(this);
    }

    private void startActivityMainDelay() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
                startActivity(i);
                finish(); // kill current activity
            }
        };
        new Timer().schedule(task, 2000);
    }

    private void initGcmData() {
        if (sharedPref.isGcmRegIdEmpty() && Tools.cekConnection(this)) {
            prepareDeviceInfo();
        } else if (sharedPref.isOpenAppCounterReach() && Tools.cekConnection(this)) {
            registerDeviceToServer(Tools.getDeviceInfo(this));
        } else {
            startActivityMainDelay();
        }
    }

    private void prepareDeviceInfo() {
        progressBar.setVisibility(View.VISIBLE);
        Tools.obtainGcmRegId(this, new Tools.CallbackRegId() {
            @Override
            public void onSuccess(DeviceInfo result) {
                if (Tools.cekConnection(getApplicationContext())) {
                    registerDeviceToServer(result);
                } else {
                    startActivityMainDelay();
                }
            }

            @Override
            public void onError() {
                startActivityMainDelay();
            }
        });
    }

    private void registerDeviceToServer(DeviceInfo deviceInfo) {
        progressBar.setVisibility(View.VISIBLE);
        Call<CallbackDevice> callback = RestAdapter.createShortAPI().registerDevice(deviceInfo);
        callback.enqueue(new retrofit2.Callback<CallbackDevice>() {
            @Override
            public void onResponse(Call<CallbackDevice> call, Response<CallbackDevice> response) {
                CallbackDevice resp = response.body();
                if (resp.status.equals("success")) {
                    sharedPref.setOpenAppCounter(0);
                }
                startActivityMainDelay();
            }

            @Override
            public void onFailure(Call<CallbackDevice> call, Throwable t) {
                startActivityMainDelay();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            for (String perm : permissions) {
                boolean rationale = shouldShowRequestPermissionRationale(perm);
                sharedPref.setNeverAskAgain(perm, !rationale);
            }
            initGcmData();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
