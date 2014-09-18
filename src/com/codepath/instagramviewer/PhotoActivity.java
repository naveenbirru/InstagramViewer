package com.codepath.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotoActivity extends Activity {

	public static final String CLIENT_ID = "a86c620c392c45ed937760d4342b1752";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotoAdapter aPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        fetchPopularPhotos();
    }


    private void fetchPopularPhotos() {
    	
    	photos = new ArrayList<InstagramPhoto>();// Initialize the list
    	
    	// create and initialize the adapter and bind it to the ArrayList
    	aPhotos = new InstagramPhotoAdapter(this, photos);
    	
    	//Get the List View
    	ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
    	
    	//Bind the adapter to the View
    	lvPhotos.setAdapter(aPhotos);
    	
		// https://api.instagram.com/v1/media/popular?client_id=a86c620c392c45ed937760d4342b1752
    	// data => [x] => "images" => "standard_resolution" => "url"
    	
    	
    	//Setup popular end point URL
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	//Create Network Client
    	AsyncHttpClient client = new AsyncHttpClient();
    	client.get(popularUrl, new JsonHttpResponseHandler() {
    		//define success failure call backs.
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,
    				JSONObject response) {
    			// Fired when success response comes back.
    			//data => [x] => "images" => "standard_resolution" => "url"
    			//data => [x] => "images" => "standard_resolution" => "height"
    			//data => [x] => "user" => "username"
    			//data => [x] => "caption" => "text"
    			
    			//Log.i("INFO", response.toString());
    			JSONArray photosJSON = null;
    			try {
    				photos.clear();
    				photosJSON = response.getJSONArray("data");
    				for(int i = 0; i < photosJSON.length(); i++) {
    					JSONObject photoJSON = photosJSON.getJSONObject(i);
    					InstagramPhoto photo = new InstagramPhoto();
    					photo.username = photoJSON.getJSONObject("user").getString("username");
    					if(photoJSON.getJSONObject("caption") != null) {
    						photo.caption = photoJSON.getJSONObject("caption").getString("text");
    					}
    					photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
    					photo.profile_pic_url = photoJSON.getJSONObject("user").getString("profile_picture");
    					photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
    					photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
    					//Log.i("DEBUG", photo.toString());
    					photos.add(photo);
    				}
    				
    			}catch (JSONException e) {
    				e.printStackTrace();
    			}
    			aPhotos.notifyDataSetChanged();	
    		}
    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				String responseString, Throwable throwable) {
    			// TODO Auto-generated method stub
    			super.onFailure(statusCode, headers, responseString, throwable);
    		}
    	});
    	//Trigger the network request
    	
    	//handle the data (JSON)
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
