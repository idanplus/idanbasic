package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.ChannellMovixService;

import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.example.idan.plusplus.V2.Events.OnAsyncTaskLoadCompletes;
import com.example.idan.plusplus.V2.Services.Retrofit2Services.BaseAbstractService;
import com.example.idan.plusplus.model.GridItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import android.util.Log;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChannellMovix extends BaseAbstractService {

    private GridItem localGridItem;

    public ChannellMovix(SharedPreferences sharedPreferences,OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences,completeCallback);
    }

    private String baseUrl = "https://13tv.co.il";   ///live/   //    private String baseUrl = "https://13tv.co.il";
     private String castTimeApi = "https://13tv-api.oplayer.io/api/getlink/?userId=45E4A9FB-FCE8-88BF-93CC-3650C39DDF28&serverType=web&cdnName=casttime&ch=";

//https://s30.cdncv.net/hls/,mfqjxoapvlo6sr7qw54u3mif466rvdsak6jefpctwqd2dyivd6t4omo75fiq,.urlset/master.m3u8

    //region Live Channell 13
    public void getLiveChannellMovix(FragmentActivity fragmentActivity, int rootFragment, GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();

        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity,rootFragment);

        LinkedHashMap<String, String> headers = getHeaders();
        headers.put("Referer", "https://cloudvideo.tv/embed-yxhe0jzejsqo.html");

        headers.put("User-Agent","MOVIX-KODI");
      //  headers.put("Accept,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("User-Agent","en-US,en;q=0.5");
        headers.put("Connection","keep-alive");
        headers.put("Upgrade-Insecure-Requests","1");




        setHeaders(headers);



      //  html=read_site_html('http://movix.live/')
       // regex='a href="(.+?)">(.+?)</a></li>'

        // setFinalUrl("https://s30.cdncv.net/hls/,mfqjxoapvlo6sr7qw54u3mif466rvdsak6jefpctwqd2dyivd6t4omo75fiq,.urlset/master.m3u8");
              getHtmlFromUrl("http://movix.live/",false)
                .flatMap(this::getLiveChannellNext)
                .flatMap(t2 -> getFinalFromM3u8(t2,getFinalUrl()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSubscribeString);
    }
    //endregion
    //region Shared Methods

    private ObservableSource<String> getLiveChannellNext(String t) {
        String regex = "a href=\"(.+?)\">(.+?)</a></li>";



        Matcher linkMatcher;
        linkMatcher = Pattern.compile(regex,Pattern.DOTALL).matcher(t);







        Log.d("xxx","looking for movies \n"+t.length());



        while (linkMatcher.find()) {


            Log.d("xxx","xxx found "+linkMatcher.group(1));

           // setFinalUrl(linkMatcher.group(1));

            String category;

            category=linkMatcher.group(1);




        }







        return getHtmlFromUrl(getFinalUrl(),false);
    }






    //endregion

}
