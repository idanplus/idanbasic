package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.Channell5Service;

import android.content.SharedPreferences;

import androidx.fragment.app.FragmentActivity;

import com.example.idan.plusplus.V2.Events.OnAsyncTaskLoadCompletes;
import com.example.idan.plusplus.V2.Services.Retrofit2Services.BaseAbstractService;
import com.example.idan.plusplus.model.GridItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class Channell5 extends BaseAbstractService {

    private GridItem localGridItem;

    //https://sport5-lh.akamaihd.net/i/radiolivev_0@698733/master.m3u8
    public Channell5(SharedPreferences sharedPreferences, OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences, completeCallback);
    }

    //region Live Channell 13
    public void getLiveChannell5(FragmentActivity fragmentActivity, int rootFragment, GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();
        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity, rootFragment);
        setFinalUrl("https://sport5-lh.akamaihd.net/i/radiolivev_0@698733/master.m3u8");

        getHtmlFromUrl("https://radio.sport5.co.il/", false)


                //https://vsd17.mycdn.me/hls/655718550245.m3u8/sig/appTKrCe_7A/expires/1586742339951/srcIp/183.89.28.207/clientType/0/srcAg/CHROME/mid/1538874089189/video.m3u8
                //https://ok.ru/live/1538874089189
             .flatMap(this::getLiveChannellNext)
              .flatMap(t2 -> getFinalFromM3u8(t2, getFinalUrl()))
               .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(onSubscribeString);
    }

    private ObservableSource<String> getLiveChannellNext(String t) {
        String regex = "pathChannelVideo = ko\\.observable\\(\"(.*?)\"\\);";
        Matcher linkMatcher;
        linkMatcher = Pattern.compile(regex, Pattern.DOTALL).matcher(t);
        if (linkMatcher.find()) {
            setFinalUrl(linkMatcher.group(1));
        }
        return getHtmlFromUrl(getFinalUrl(), false);
    }
}