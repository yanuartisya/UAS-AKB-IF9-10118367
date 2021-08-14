package UAS_10118367_YanuarTisyaDwiPutra.gcm;

//09-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import UAS_10118367_YanuarTisyaDwiPutra.ActivityNewsInfoDetails;
import UAS_10118367_YanuarTisyaDwiPutra.ActivityPlaceDetail;
import UAS_10118367_YanuarTisyaDwiPutra.ActivitySplash;
import UAS_10118367_YanuarTisyaDwiPutra.R;
import UAS_10118367_YanuarTisyaDwiPutra.data.Constant;
import UAS_10118367_YanuarTisyaDwiPutra.data.DatabaseHandler;
import UAS_10118367_YanuarTisyaDwiPutra.model.GcmNotif;
import UAS_10118367_YanuarTisyaDwiPutra.model.NewsInfo;
import UAS_10118367_YanuarTisyaDwiPutra.model.Place;
import UAS_10118367_YanuarTisyaDwiPutra.utils.Tools;

public class GcmIntentService extends IntentService {
    private static final String TAG = GcmIntentService.class.getName();
    private ImageLoader imgloader = ImageLoader.getInstance();
    private int retry_count = 0;

    public GcmIntentService() {
        super(TAG);
        if (!imgloader.isInited()) Tools.initImageLoader(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        showGcmNotif(intent);
    }

    private void showGcmNotif(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!intent.getExtras().isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                final GcmNotif gcmNotif = new GcmNotif();
                gcmNotif.setTitle(intent.getStringExtra("title"));
                gcmNotif.setContent(intent.getStringExtra("content"));
                gcmNotif.setType(intent.getStringExtra("type"));

                String place_str = intent.getStringExtra("place");
                Place p = place_str != null ? new Gson().fromJson(place_str, Place.class) : null;
                gcmNotif.setPlace(p);

                String news_str = intent.getStringExtra("news");
                NewsInfo n = news_str != null ? new Gson().fromJson(news_str, NewsInfo.class) : null;
                gcmNotif.setNews(n);
                loadRetryImageFromUrl(gcmNotif, new CallbackImageNotif() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        displayNotificationIntent(gcmNotif, bitmap);
                    }

                    @Override
                    public void onFailed() {
                        displayNotificationIntent(gcmNotif, null);
                    }
                });
            }
        }
    }

    private void displayNotificationIntent(GcmNotif gcmNotif, Bitmap bitmap) {
        Intent intent = new Intent(this, ActivitySplash.class);
        if (gcmNotif.getPlace() != null) {
            intent = ActivityPlaceDetail.navigateBase(this, gcmNotif.getPlace(), true);
        } else if(gcmNotif.getNews() != null){ // handle notification for open News Info Details
            new DatabaseHandler(this).refreshTableNewsInfo();
            intent = ActivityNewsInfoDetails.navigateBase(this, gcmNotif.getNews(), true);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(gcmNotif.getTitle());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(gcmNotif.getContent()));
        builder.setContentText(gcmNotif.getContent());
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        if (bitmap != null) {
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).setSummaryText(gcmNotif.getContent()));
        }
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int unique_id = (int) System.currentTimeMillis();
        notificationManager.notify(unique_id, builder.build());
    }

    private void loadRetryImageFromUrl(final GcmNotif gcmNotif, final CallbackImageNotif callback){
        String url = "";
        if (gcmNotif.getPlace() != null) {
            url = Constant.getURLimgPlace(gcmNotif.getPlace().image);
        } else if(gcmNotif.getNews() != null){
            url = Constant.getURLimgNews(gcmNotif.getNews().image);
        } else {
            callback.onFailed();
            return;
        }
        loadImageFromUrl(url, new CallbackImageNotif() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callback.onSuccess(bitmap);
            }

            @Override
            public void onFailed() {
                Log.e("onFailed", "on Failed");
                if (retry_count <= Constant.LOAD_IMAGE_NOTIF_RETRY) {
                    retry_count++;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadRetryImageFromUrl(gcmNotif, callback);
                        }
                    }, 1000);
                } else {
                    callback.onFailed();
                }
            }
        });
    }

    private void loadImageFromUrl(String url, final CallbackImageNotif callback){
        imgloader.loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                callback.onSuccess(loadedImage);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                callback.onFailed();
                super.onLoadingFailed(imageUri, view, failReason);
            }
        });
    }

    private interface CallbackImageNotif {
        void onSuccess(Bitmap bitmap);
        void onFailed();
    }
}
