package UAS_10118367_YanuarTisyaDwiPutra.data;

//07-Agustus-2021 ==> 12-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import UAS_10118367_YanuarTisyaDwiPutra.R;

public class SharedPref {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences prefs;
    private static final int MAX_OPEN_COUNTER = 15;

    private static final String GCM_PREF_KEY = "UAS_10118367_YanuarTisyaDwiPutra.data.GCM_PREF_KEY";
    private static final String SERVER_FLAG_KEY = "UAS_10118367_YanuarTisyaDwiPutra.data.SERVER_FLAG_KEY";
    private static final String THEME_COLOR_KEY = "UAS_10118367_YanuarTisyaDwiPutra.data.THEME_COLOR_KEY";
    private static final String LAST_PLACE_PAGE = "LAST_PLACE_PAGE_KEY";

    // need refresh
    public static final String REFRESH_PLACES = "UAS_10118367_YanuarTisyaDwiPutra.data.REFRESH_PLACES";

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setGCMRegId(String gcmRegId) {
        sharedPreferences.edit().putString(GCM_PREF_KEY, gcmRegId).apply();
    }

    public String getGCMRegId() {
        return sharedPreferences.getString(GCM_PREF_KEY, null);
    }

    public boolean isGcmRegIdEmpty() {
        return TextUtils.isEmpty(getGCMRegId());
    }

    public void setRegisteredOnServer(boolean registered) {
        sharedPreferences.edit().putBoolean(SERVER_FLAG_KEY, registered).apply();
    }

    public boolean isRegisteredOnServer() {
        return sharedPreferences.getBoolean(SERVER_FLAG_KEY, false);
    }

    public boolean isNeedRegisterGcm() {
        return (isGcmRegIdEmpty() || !isRegisteredOnServer());
    }

    public boolean getNotification() {
        return prefs.getBoolean(context.getString(R.string.pref_key_notif), true);
    }

    public boolean isRefreshPlaces() {
        return sharedPreferences.getBoolean(REFRESH_PLACES, false);
    }

    public void setRefreshPlaces(boolean need_refresh) {
        sharedPreferences.edit().putBoolean(REFRESH_PLACES, need_refresh).apply();
    }


    public void setThemeColor(String color) {
        sharedPreferences.edit().putString(THEME_COLOR_KEY, color).apply();
    }

    public String getThemeColor() {
        return sharedPreferences.getString(THEME_COLOR_KEY, "");
    }

    public int getThemeColorInt() {
        if (getThemeColor().equals("")) {
            return context.getResources().getColor(R.color.colorPrimary);
        }
        return Color.parseColor(getThemeColor());
    }


    public void setLastPlacePage(int page) {
        sharedPreferences.edit().putInt(LAST_PLACE_PAGE, page).apply();
    }

    public int getLastPlacePage() {
        return sharedPreferences.getInt(LAST_PLACE_PAGE, 1);
    }


    public void setNeverAskAgain(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getNeverAskAgain(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean isOpenAppCounterReach() {
        int counter = sharedPreferences.getInt("OPEN_COUNTER_KEY", MAX_OPEN_COUNTER) + 1;
        setOpenAppCounter(counter);
        Log.e("COUNTER", "" + counter);
        return (counter >= MAX_OPEN_COUNTER);
    }

    public void setOpenAppCounter(int val) {
        sharedPreferences.edit().putInt("OPEN_COUNTER_KEY", val).apply();
    }

}
