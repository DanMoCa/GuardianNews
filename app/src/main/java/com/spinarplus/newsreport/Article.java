package com.spinarplus.newsreport;

/**
 * Created by Desarrollo on 8/23/2017.
 */

public class Article {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mUrl;

    public Article(String title, String sectionName, String date, String url){
        mTitle = title;
        mSection = sectionName;
        mDate = date;
        mUrl = url;
    }

    public String getTitle() { return mTitle; }

    public String getSection() {
        return mSection;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
