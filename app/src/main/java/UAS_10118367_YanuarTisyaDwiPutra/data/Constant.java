package UAS_10118367_YanuarTisyaDwiPutra.data;

//09-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

public class Constant {


    public static String WEB_URL = "https://yanuartisyadwiputra.000webhostapp.com/";

    public static final double city_lat = -6.9174639;
    public static final double city_lng = 107.6191228;

    public static final String PROJECT_API_NUMBER = "792734718716";


    public static String getURLimgPlace(String file_name) {
        return WEB_URL + "uploads/place/" + file_name;
    }
    public static String getURLimgNews(String file_name) {
        return WEB_URL + "uploads/news/" + file_name;
    }

    public static final int LIMIT_PLACE_REQUEST = 40;
    public static final int LIMIT_LOADMORE = 40;

    public static final int LIMIT_NEWS_REQUEST = 40;


    public static int LOAD_IMAGE_NOTIF_RETRY = 3;

    public static final String LOG_TAG = "CITY_LOG";

    public enum Event {
        FAVORITES,
        THEME,
        NOTIFICATION,
        REFRESH
    }

}
