package UAS_10118367_YanuarTisyaDwiPutra.connection.callbacks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import UAS_10118367_YanuarTisyaDwiPutra.model.NewsInfo;

//07-Agustus-2021
//10119367 - Yanuar Tisya DWi Putra - IF-9

public class CallbackListNewsInfo implements Serializable {

    public String status = "";
    public int count = -1;
    public int count_total = -1;
    public int pages = -1;
    public List<NewsInfo> news_infos = new ArrayList<>();

}
