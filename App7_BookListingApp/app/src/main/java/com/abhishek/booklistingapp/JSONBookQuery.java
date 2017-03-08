package com.abhishek.booklistingapp;

import com.abhishek.booklistingapp.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONBookQuery {
    public static ArrayList<Book> read(String Response) throws JSONException {
        ArrayList<Book> books = new ArrayList<>();
        JSONObject JsonResponse = new JSONObject(Response);
        if(JsonResponse.getInt("totalItems")==0)
            return null;
        JSONArray items = JsonResponse.getJSONArray("items");
        int length=items.length();
        String title;
        String author;
        String imageURL=null;
        Book book;
        for(int i=0;i<length;i++)
        {
            JSONObject item = items.getJSONObject(i);
            JSONObject volumeInfo = item.getJSONObject("volumeInfo");
            title= volumeInfo.optString("title");
            JSONArray authorArray = volumeInfo.optJSONArray("authors");
            author=getAuthors(authorArray);

            JSONObject imagesArray = volumeInfo.optJSONObject("imageLinks");
            if(imagesArray!=null)
                imageURL= imagesArray.optString("thumbnail");
            book=new Book(title,author,imageURL);
            books.add(book);
        }
        return books;
    }

    public  static String getAuthors(JSONArray authorArray) throws JSONException {
        if(authorArray==null)
            return null;
        int length= authorArray.length();
        if(length==0)
            return null;
        String authors = authorArray.getString(0);
        for(int i=1;i<length;i++)
        {
            authors+=","+ authorArray.getString(i);
        }
        return authors;
    }
}
