package com.vegetablecorp.technews;

public class Article {

    private String mSectionName;
    private String mWebPublicationDate;
    private String mWebTitle;
    private String mWebUrl;

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }

    public Article(String mSectionName, String mWebPublicationDate, String mWebTitle, String mWebUrl) {
        this.mSectionName = mSectionName;
        this.mWebPublicationDate = mWebPublicationDate;
        this.mWebTitle = mWebTitle;
        this.mWebUrl = mWebUrl;
    }
}
