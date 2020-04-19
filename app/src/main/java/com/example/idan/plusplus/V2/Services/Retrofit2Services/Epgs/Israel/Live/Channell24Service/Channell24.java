package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.Channell24Service;

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

public class Channell24 extends BaseAbstractService {

    private GridItem localGridItem;

    public Channell24(SharedPreferences sharedPreferences,OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences,completeCallback);
    }

    //region Live Channell 13
    public void getLiveChannell24(FragmentActivity fragmentActivity, int rootFragment, GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();
        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity,rootFragment);



        setFinalUrl("https://keshethlslive-lh.akamaihd.net/i/24live_1@195271/index_1400_av-b.m3u8?sd=10&b=200-2500&rebase=on");

        getHtmlFromUrl("https://www.mako.co.il/aa77c0ed-a8ed-4cf2-977b-809615ccf174",false)
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
