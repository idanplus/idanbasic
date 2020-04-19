package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.Channell21Service;

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

public class Channell21 extends BaseAbstractService {

    private GridItem localGridItem;
    private String baseUrl = "https://13tv.co.il";
    private String castTimeApi = "https://13tv-api.oplayer.io/api/getlink/?userId=45E4A9FB-FCE8-88BF-93CC-3650C39DDF28&serverType=web&cdnName=casttime&ch=";

    public Channell21(SharedPreferences sharedPreferences,OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences,completeCallback);
    }

    //region Live Channell 13
    public void getLiveChannell21(FragmentActivity fragmentActivity,int rootFragment,GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();
        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity,rootFragment);



        setFinalUrl("http://82.80.192.30/shoppingil_ShoppingIL21TVRepeat/_definst_/smil:ShoppingIL21TV.smil/chunklist_w2043660694_b2128000.m3u8?ttl=1524643697&cdn_token=5a6c4f1270440ae72118c8ae16c58de4");

        getHtmlFromUrl("https://www.21.tv/home/lineup?a=5",false)
                .flatMap(this::getLiveChannellNext)
                .flatMap(t2 -> getFinalFromM3u8(t2,getFinalUrl()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSubscribeString);
    }

    private ObservableSource<String> getLiveChannellNext(String t) {
        String regex = "pathChannelVideo = ko\\.observable\\(\"(.*?)\"\\);";
        Matcher linkMatcher;
        linkMatcher = Pattern.compile(regex,Pattern.DOTALL).matcher(t);
        if (linkMatcher.find()) {
            setFinalUrl(linkMatcher.group(1));
        }
        return getHtmlFromUrl(getFinalUrl(),false);
    }

    //endregion

}
