package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.Channell9Service;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.idan.plusplus.Classes.KeshetAkamiTokenResponse;
import com.example.idan.plusplus.V2.Events.OnAsyncTaskLoadCompletes;
import com.example.idan.plusplus.V2.Services.Retrofit2Services.BaseAbstractService;

//import com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.Channell9Service.
//Channell9Service
import com.example.idan.plusplus.model.GridItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Channell9 extends BaseAbstractService {

    private GridItem localGridItem;

   // private String baseUrl = "http://only-tv.org/9-kanal-izrail.html";
  ///   private String castTimeApi = "http://cdnpotok.com/onlytv/9-kanal-izrail.php";
   // private GridItem localGridItem;
    private String baseUrl = "https://13tv.co.il";   ///live/   //    private String baseUrl = "https://13tv.co.il";
    private String brightcoveApi = "https://edge.api.brightcove.com/playback/v1/accounts/1551111274001/videos/";
    private String brightcovePK = "application/json;pk=BCpkADawqM30eqkItS5d08jYUtMkbTKu99oEBllbfUaFKeknXu4iYsh75cRm2huJ_b1-pXEPuvItI-733TqJN1zENi-DcHlt7b4Dv6gCT8T3BS-ampD0XMnORQLoLvjvxD14AseHxvk0esW3";
    private String castTimeApi = "https://13tv-api.oplayer.io/api/getlink/?userId=45E4A9FB-FCE8-88BF-93CC-3650C39DDF28&serverType=web&cdnName=casttime&ch=";

    public Channell9(SharedPreferences sharedPreferences,OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences,completeCallback);
    }

    //region Live Channell 13
    public void getLiveChannell9(FragmentActivity fragmentActivity, int rootFragment, GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();

        LinkedHashMap<String, String> headers = getHeaders();
        headers.put("Referer", "http://only-tv.org/");
        setHeaders(headers);


        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity,rootFragment);
            getHtmlFromUrl("http://only-tv.org/9-kanal-izrail.html",false)

                 .flatMap(this::getLiveChannellNext)
                .flatMap(this::getLiveChannellNext1)

                  //  .flatMap(this::getLiveChannellNext)
                   // .flatMap(this::getLiveChannellNext1)

                .flatMap(t2 -> getFinalFromM3u8(t2, getFinalUrl()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSubscribeString);
    }
    //endregion


    //region Shared Methods

    private ObservableSource<String> getLiveChannellNext(String t) {
        String regex = "<iframe.*? src=\"(.+?)\".*?</iframe>";
        Matcher linkMatcher;
        linkMatcher = Pattern.compile(regex,Pattern.DOTALL).matcher(t);
        if (linkMatcher.find()) {
            setFinalUrl(linkMatcher.group(1));
        }
        return getHtmlFromUrl(getFinalUrl(),false);
    }


    private ObservableSource<String> getLiveChannellNext1(String t) {
        String regex = "new Playerjs.*file:\"(.+?)\"";
        Matcher linkMatcher = Pattern.compile(regex,Pattern.DOTALL).matcher(t);
        if (linkMatcher.find()) {
            setFinalUrl(linkMatcher.group(1));
        }
        return getHtmlFromUrl(getFinalUrl(),false);
    }



    //endregion

}
