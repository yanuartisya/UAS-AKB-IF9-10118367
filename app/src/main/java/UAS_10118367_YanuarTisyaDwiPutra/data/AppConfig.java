package UAS_10118367_YanuarTisyaDwiPutra.data;

//07-Agustus-2021 == 11-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

public class AppConfig {

    public static final boolean ADS_MAIN_INTERSTITIAL = false;
    public static final boolean ADS_PLACE_DETAILS_BANNER = false;
    public static final boolean ADS_NEWS_DETAILS_BANNER = false;

    public static final boolean ENABLE_NEWS_INFO = true;

    public static final boolean IMAGE_CACHE = true;

    public static final boolean LAZY_LOAD = false;

    public static final boolean ENABLE_ANALYTICS = true;

    public static final boolean REFRESH_IMG_NOTIF = true;


    // when user enable gps, places will sort by distance
    public static final boolean SORT_BY_DISTANCE = false;

    // distance metric, fill with KILOMETER or MILE only
    public static final String DISTANCE_METRIC_CODE = "KILOMETER";

    // related to UI display string
    public static final String DISTANCE_METRIC_STR = "Km";

    // flag for enable disable theme color chooser, in Setting
    public static final boolean THEME_COLOR = true;

}
