package com.spinarplus.newsreport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollo on 8/24/2017.
 */

public class ArticleLoader extends AsyncTaskLoader {
    private static final String LOG_TAG = ArticleLoader.class.getSimpleName();
    private String mUrl;

    public ArticleLoader(Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        ArrayList<Article> articles = QueryUtils.fetchArticleData(mUrl);
        Log.i("URL in Loader",mUrl);

        printArray(articles);

        return articles;
    }

    public void printArray(ArrayList<Article> articles){
        int size = articles.size();
        for(int i = 0; i < size; i++){
            Log.d("Article "+i,articles.get(i).getTitle());
        }
    }


}
