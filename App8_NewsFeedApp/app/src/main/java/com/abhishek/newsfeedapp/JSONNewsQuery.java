package com.abhishek.newsfeedapp;

import android.net.Uri;
import android.util.Log;

import com.abhishek.newsfeedapp.model.NewsFeed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JSONNewsQuery {

    public static String createQueryURL(int page) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.encodedAuthority("content.guardianapis.com");
        builder.appendPath("search");
        builder.appendQueryParameter("order-by", "newest");
        builder.appendQueryParameter("show-references", "author");
        builder.appendQueryParameter("show-tags", "contributor");
        builder.appendQueryParameter("q", "Android");
        builder.appendQueryParameter("page", String.valueOf(page));
        builder.appendQueryParameter("api-key", "test");
        String url = builder.build().toString();
        return url;
    }

    private static String formatDate(String rawDate) throws ParseException {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        Date parsedJsonDate = jsonFormatter.parse(rawDate);
        String finalDatePattern = "MMM d, yyy";
        SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
        return finalDateFormatter.format(parsedJsonDate);
    }

    public static ArrayList<NewsFeed> read(String response) throws JSONException, ParseException {
        ArrayList<NewsFeed> newsFeeds = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonResponse = jsonObject.getJSONObject("response");
        JSONArray jsonResult = jsonResponse.getJSONArray("results");
        for (int i = 0; i < jsonResult.length(); i++) {
            JSONObject result = jsonResult.getJSONObject(i);
            String Title = result.getString("webTitle");
            String Date = formatDate(result.getString("webPublicationDate"));
            String URL = result.getString("webUrl");
            String Section = result.getString("sectionName");
            JSONArray Tags = result.getJSONArray("tags");
            String Author = null;
            if (Tags.length() > 0)
                Author = Tags.getJSONObject(0).getString("webTitle") + ". ";
            for (int j = 1; j < Tags.length(); j++) {
                Author += Tags.getJSONObject(j).getString("webTitle") + ". ";
            }
            newsFeeds.add(new NewsFeed(Title, Author, Date, Section, URL));
        }
        return newsFeeds;
    }

    public static int getServerResultLength(String response) throws JSONException {
        int length=0;
     //   Log.v("Json Length: ",response);
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonResponse = jsonObject.getJSONObject("response");
        length=jsonResponse.optInt("total",0);
        return length;
    }
}
