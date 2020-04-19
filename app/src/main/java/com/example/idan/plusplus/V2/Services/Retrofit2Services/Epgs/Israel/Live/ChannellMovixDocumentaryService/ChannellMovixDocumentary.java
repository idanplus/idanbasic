package com.example.idan.plusplus.V2.Services.Retrofit2Services.Epgs.Israel.Live.ChannellMovixDocumentaryService;

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

public class ChannellMovixDocumentary extends BaseAbstractService {

    private GridItem localGridItem;

    public ChannellMovixDocumentary(SharedPreferences sharedPreferences,OnAsyncTaskLoadCompletes completeCallback) {
        super(sharedPreferences,completeCallback);
    }



    public void getLiveChannellMovixDocumentary(FragmentActivity fragmentActivity, int rootFragment, GridItem gridItem, OnAsyncTaskLoadCompletes<GridItem> callback) {
        freshStartChannell();

        setCurrentGridItem(gridItem);
        setFragmentActivity(fragmentActivity);
        setFinalVideoLinkCallback(callback);
        startSpinner(fragmentActivity,rootFragment);

        LinkedHashMap<String, String> headers = getHeaders();
        headers.put("Referer", "https://cloudvideo.tv/embed-yxhe0jzejsqo.html");

        headers.put("User-Agent","MovixDocumentary-KODI");
      //  headers.put("Accept,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        headers.put("User-Agent","en-US,en;q=0.5");
        headers.put("Connection","keep-alive");
        headers.put("Upgrade-Insecure-Requests","1");




        setHeaders(headers);



  
              getHtmlFromUrl("https://movix.live/genre/Documentary/",false)
                .flatMap(this::getLiveChannellNext)
                .flatMap(t2 -> getFinalFromM3u8(t2,getFinalUrl()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSubscribeString);
    }
    //endregion
    //region Shared Methods

    private ObservableSource<String> getLiveChannellNext(String t) {
        String regex = "div data-movie-id=\".+? class=\"ml-item.*?>.*?<a href=\"(.+?)\"";   //"a href=\"(.+?)\">(.+?)</a></li>";



        Matcher linkMatcher;
        linkMatcher = Pattern.compile(regex,Pattern.DOTALL).matcher(t);










        while (linkMatcher.find()) {




           // setFinalUrl(linkMatcher.group(1));

            String category;

            category=linkMatcher.group(1);


            String movies;

            movies=linkMatcher.group(1);
            Log.d("xxx","movie  "+linkMatcher.group(1));



        //    na=name.replace('Home','ראשי').replace('Documentary','פעולה').replace('Comedy','קומדיה').replace('Crime','פשע').replace('Drama','דרמה').replace('Mystery','מיסטורין').replace('Thriller','מותחן')
         //   na=na.replace('From. Fiction','מדע בדיוני').replace('Horror','אימה').replace('Animation','אנימציה').replace('Fantasy','פנטסיה').replace('War','מלחמה').replace('Documentary','דוקומנטרי').replace('Family','משפחה').replace('Kids','ילדים').replace('Most Viewed','הנצפים ביותר').replace('Movies','סרטים').replace('Series','סדרות')



        }

       // Log.d("xxx","looking for iframe \n"+t.substring(t.length()-3000,t.length()));





        return getHtmlFromUrl(getFinalUrl(),false);
    }






    //endregion

}
