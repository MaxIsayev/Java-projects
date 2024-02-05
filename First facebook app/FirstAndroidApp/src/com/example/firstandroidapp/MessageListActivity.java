package com.example.firstandroidapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MessageListActivity extends ListActivity  {
	private ProgressDialog pDialog;
	// Hashmap for ListView
	private ArrayList<HashMap<String, String>> messagelist = new ArrayList<HashMap<String, String>>();
	//URL to get JSON Array
	private static String access_token = "CAACEdEose0cBADedWPbac5cMhxGmLW3ZCxO5EZCysi8Ti07ZCVJQWHMv2Ymuo25N0Nhjs2wVX0p9YxcHVwA99OvjQjwHZCG2JKaBFcXZBZAPIH9t5znlS1QJ6EZCWEr96x7yta82yDZAmjLvRFWkf98t2SZCgi4fpku1lmOgSrgCmbNJokQ0TraOM8h74UrGvZBAM5Uke7YhRZAbZAxFSF5P0WgpegOKmKkYnfEZD";
	private static String old_token;
	private static String url = "https://graph.facebook.com/me/posts?fields=id,created_time,message,story,picture,type&access_token="+access_token;	
	// JSON Node name
    private static final String TAG_DATA = "data";    
    private static final String TAG_TIMESTAMP = "created_time";
    private static final String TAG_TYPE = "type";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STORY = "story";
    // messages JSONArray
    JSONArray data = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_list);
		messagelist = new ArrayList<HashMap<String, String>>();
        				 
        ListView lv = getListView();
                
		// Calling async task to get json
        new GetContacts().execute();
    }

	/**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MessageListActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            
            // Get the message from the intent
            Intent i = getIntent();
            
            String newToken = i.getStringExtra(MainActivity.EXTRA_MESSAGE);
            
            old_token=access_token;
            if((!newToken.isEmpty())&(newToken!=null)){
            			Log.d("got new token ",newToken);
            			access_token = newToken;
            			url="https://graph.facebook.com/me/posts?fields=id,created_time,message,story,picture,type&access_token="+"access_token";
            }			
            
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            
            Log.d("Access token: ", "> " + access_token);
            Log.d("Response: ", "> " + jsonStr);
            
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    if(jsonObj.has("error")){                    	
                    	access_token=old_token;  
                    	url="https://graph.facebook.com/me/posts?fields=id,created_time,message,story,picture,type&access_token="+"access_token";
                    }else
                    	old_token=access_token;  
                    
                    
                    data = jsonObj.getJSONArray(TAG_DATA);
                    
                    
                    	
                    // looping through All Contacts
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);
                        String date = "";
                        String type ="";
                        String message ="";
                                              
                        if (c.has(TAG_TIMESTAMP)) 
                        	date= c.getString(TAG_TIMESTAMP);                        
                        if (c.has(TAG_TYPE)) 
                        	type = c.getString(TAG_TYPE);
                        if(c.has(TAG_MESSAGE)) 
                        	message = c.getString(TAG_MESSAGE);
                        if(c.has(TAG_STORY))
                        	message = c.getString(TAG_STORY);  
                        
                        // tmp hashmap for single contact
                        HashMap<String, String> record = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        record.put(TAG_TIMESTAMP, date);
                        record.put(TAG_TYPE, type);
                        record.put(TAG_MESSAGE, message);
                         
                        // adding record to contact list
                        messagelist.add(record);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
            		MessageListActivity.this, messagelist,
                    R.layout.list_item, new String[] { TAG_TIMESTAMP, TAG_TYPE,
            				TAG_MESSAGE }, new int[] { R.id.date,
                            R.id.type, R.id.message });
 
            setListAdapter(adapter);
        }
 
    }
	    

    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in dataManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
