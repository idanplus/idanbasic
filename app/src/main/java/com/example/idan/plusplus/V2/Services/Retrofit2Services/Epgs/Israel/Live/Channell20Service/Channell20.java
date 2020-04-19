package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.Channell20Service;

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

public class Channell20 extends BaseAbstractService {

    private GridItem localGridItem;

    public Channell20(SharedPreferences sharedPreferences,OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences,completeCallback);
    }

    //region Live Channell 13
    public void getLiveChannell20(FragmentActivity fragmentActivity, int rootFragment, GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();
        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity,rootFragment);



        setFinalUrl("http://live.ch20-cdnwiz.com/ch20-abr/ch20-abr/playlist.m3u8");

        getHtmlFromUrl("https://www.20il.co.il/",false)
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

        //
