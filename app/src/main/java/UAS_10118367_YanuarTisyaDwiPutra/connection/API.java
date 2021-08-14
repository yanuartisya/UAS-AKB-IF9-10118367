package UAS_10118367_YanuarTisyaDwiPutra.connection;

import UAS_10118367_YanuarTisyaDwiPutra.connection.callbacks.CallbackDevice;
import UAS_10118367_YanuarTisyaDwiPutra.connection.callbacks.CallbackListNewsInfo;
import UAS_10118367_YanuarTisyaDwiPutra.connection.callbacks.CallbackListPlace;
import UAS_10118367_YanuarTisyaDwiPutra.connection.callbacks.CallbackPlaceDetails;
import UAS_10118367_YanuarTisyaDwiPutra.model.DeviceInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//07-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

public interface API {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "User-Agent: Place";

    @Headers({CACHE, AGENT})
    @GET("app/services/listPlaces")
    Call<CallbackListPlace> getPlacesByPage(
            @Query("page") int page,
            @Query("count") int count,
            @Query("draft") int draft
    );

    @Headers({CACHE, AGENT})
    @GET("app/services/getPlaceDetails")
    Call<CallbackPlaceDetails> getPlaceDetails(
            @Query("place_id") int place_id
    );

    @Headers({CACHE, AGENT})
    @GET("app/services/listNewsInfo")
    Call<CallbackListNewsInfo> getNewsInfoByPage(
            @Query("page") int page,
            @Query("count") int count
    );

    @Headers({CACHE, AGENT})
    @POST("app/services/insertGcm")
    Call<CallbackDevice> registerDevice(
            @Body DeviceInfo deviceInfo
    );

}
