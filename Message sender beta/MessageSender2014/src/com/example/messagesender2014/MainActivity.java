package com.example.messagesender2014;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	// Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;    
    public static final int MEDIA_TYPE_IMAGE = 1;   
 
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
 
    private Uri fileUri; // file url to store image/video
 
    private ImageView imgPreview;     
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	// GPSTracker class
    GPSTracker gps;
    double latitude=0.0;
    double longitude=0.0;  
    String message="";
	
    //connect to DB
    String httpSend="http://maksim.tvsprojektai.lt/php%20quiery.php";
    String httpReceive="http://maksim.tvsprojektai.lt/php%20quiery2.php";
    InputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    /** Called when the user clicks the preview button */
    public void preview(View view) {
    	Intent intent = new Intent(this, PreviewActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        message = "Text:\n"+editText.getText().toString();       
          
        getLocation(view);
        message = message.concat("\nGPS: \n"+Double.toString(latitude)+" \n"+Double.toString(longitude)
        		+"\nImage directory: "+fileUri.getPath());
                
        intent.putExtra(EXTRA_MESSAGE, message);   
        startActivity(intent);
    }
    
    public void getLocation(View view) {        
        
    	Button btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
         
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {        
                // create class object
                gps = new GPSTracker(MainActivity.this);
 
                // check if GPS enabled     
                if(gps.canGetLocation()){
                     
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                     
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
        });
    }
    
    public void sendToDB(View view){
    	Button b = (Button)findViewById(R.id.btnSendToDB);          
         
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                
                 new Thread(new Runnable() {
                        public void run() {
                        	try {
								sendValuesToDB(httpSend);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}                          
                        }
                      }).start();               
            }
        });
    }
    
    public void sendValuesToDB(String httpUrl) throws JSONException{
    	String result = "";
    	//the values to send
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	nameValuePairs.add(new BasicNameValuePair("Text",message));   	
    	nameValuePairs.add(new BasicNameValuePair("lattitude",Double.toString(latitude))); 
    	nameValuePairs.add(new BasicNameValuePair("longtitude",Double.toString(longitude))); 
    	try{
    		//http post
    	        HttpClient httpclient = new DefaultHttpClient();
    	        HttpPost httppost = new HttpPost(httpUrl);
    	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    	        HttpResponse response = httpclient.execute(httppost);
    	        HttpEntity entity = response.getEntity();
    	        InputStream is = entity.getContent();
    	    //convert response to string    
    	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    	        StringBuilder sb = new StringBuilder();
    	        String line = null;
    	        while ((line = reader.readLine()) != null) {
    	                sb.append(line + "\n");
    	        }
    	        is.close();
    	 
    	        result=sb.toString();
    	    //parse json data    
    	        JSONArray jArray = new JSONArray(result);
    	        for(int i=0;i<jArray.length();i++){
    	                JSONObject json_data = jArray.getJSONObject(i);
    	                Log.i("log_tag","id: "+json_data.getInt("id")+
    	                        ", Time created: "+json_data.getString("Time created")+
    	                        
    	                        ", Text: "+json_data.getString("Text")+
    	                        ", Latitude: "+json_data.getDouble("GPSlattitude")+
    	                        ", Longitude: "+json_data.getDouble("GPSlongtitude")
    	                );
    	        }
    	}catch(JSONException je){
    		Log.e("log_tag", "Error parsing data "+je.toString());
    	}catch(Exception e){
            Log.e("log_tag", "Error in http connection or converting result "+e.toString());
    	}
    	
    }
    
    public void getFromDB(View view){
    	Button b = (Button)findViewById(R.id.btnFromDb);          
        
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                
                 new Thread(new Runnable() {
                        public void run() {
                        	try {
								sendValuesToDB(httpReceive);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}                          
                        }
                      }).start();               
            }
        });
    }
    
    public void sendToServer(View view){
    	Button b = (Button)findViewById(R.id.btnSendToDB);          
        
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {     
                        	uploadImage();                          
                                   
            }
        });
    }
    
    public void uploadImage(){
    	
    	Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());           
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
    	byte [] byte_arr = stream.toByteArray();
    	String image_str = Base64.encodeBytes(byte_arr);
    	final ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

    	nameValuePairs.add(new BasicNameValuePair("image",image_str));

    	Thread t = new Thread(new Runnable() {
     
    		@Override
    		public void run() {
    			try{
                 HttpClient httpclient = new DefaultHttpClient();
                 HttpPost httppost = new HttpPost("http://maksim.tvsprojektai.lt/toupload.php");
                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                 HttpResponse response = httpclient.execute(httppost);
                 final String the_string_response = convertResponseToString(response);
                 runOnUiThread(new Runnable() {
                         
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Response " + the_string_response, Toast.LENGTH_LONG).show();                          
                        }
                    });
                  
    			}catch(final Exception e){
                  runOnUiThread(new Runnable() {
                     
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();                              
                    }
                });
                   System.out.println("Error in http connection "+e.toString());
             }  
    		}
    	});
    	t.start();
    }
    
    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{
    	 
        final String res = "";
        final StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        final int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
         runOnUiThread(new Runnable() {
        
       @Override
       public void run() {
           Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();                     
       }
   });
     
        if (contentLength < 0){
        }
        else{
               byte[] data = new byte[512];
               int len = 0;
               try
               {
                   while (-1 != (len = inputStream.read(data)) )
                   {
                       buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                   }
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               try
               {
                   inputStream.close(); // closing the stream…..
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               

               runOnUiThread(new Runnable() {
                
               @Override
               public void run() {
                  Toast.makeText(MainActivity.this, "Result : " + buffer.toString(), Toast.LENGTH_LONG).show();
               }
           });
               //System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
   }
    
    public void takePicture(View view){

    	
   	 	Button btnCapturePicture = (Button) findViewById(R.id.btnCapture);
   	 	/**
         * Capture image button click event
         * */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // capture picture
                captureImage();
            }
        });
   }
    
    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
     
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
     
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
     
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }    
    
    /**
     * Creating file uri to store image
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
     
    /*
     * returning image 
     */
    private static File getOutputMediaFile(int type) {
     
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
     
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
     
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");        
        } else {
            return null;
        }
     
        return mediaFile;
    }
    
    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {           
            imgPreview.setVisibility(View.VISIBLE);
 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
 
            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
     
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }
     
    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
     
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    
}
