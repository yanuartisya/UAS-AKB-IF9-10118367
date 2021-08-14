package UAS_10118367_YanuarTisyaDwiPutra.utils;

//07-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

public interface Callback<T> {

    void onSuccess(T result);

    void onError(String result);

}
