package UAS_10118367_YanuarTisyaDwiPutra.gcm;

//09-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.nostra13.universalimageloader.core.ImageLoader;

import UAS_10118367_YanuarTisyaDwiPutra.data.AppConfig;
import UAS_10118367_YanuarTisyaDwiPutra.data.SharedPref;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static int VIBRATION_TIME = 500; // in millisecond
    private SharedPref sharedPref;
    private ImageLoader imgloader = ImageLoader.getInstance();

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPref = new SharedPref(context);

        sharedPref.setRefreshPlaces(true);
        if(imgloader.isInited() && AppConfig.REFRESH_IMG_NOTIF){
            imgloader.clearDiskCache();
            imgloader.clearMemoryCache();
        }

    }


}
