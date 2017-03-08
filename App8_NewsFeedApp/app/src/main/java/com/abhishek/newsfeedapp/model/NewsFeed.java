package com.abhishek.newsfeedapp.model;

public class NewsFeed {

    private String mTitle;
    private String mDate;
    private String mAuthor;
    private String mURL;
    private String mSection;
    public NewsFeed(String Title,String Author,String Date,String Section,String URL)
    {
        mTitle=Title;
        mAuthor=Author;
        mDate=Date;
        mSection=Section;
        mURL=URL;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setURL(String mURL) {
        this.mURL = mURL;
    }

    public void setSection(String mSection) {
        this.mSection = mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getURL() {
        return mURL;
    }

    public String getSection() {
        return mSection;
    }
}
