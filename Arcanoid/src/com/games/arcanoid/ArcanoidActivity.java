package com.games.arcanoid;

import android.app.Activity;
import android.os.Bundle;

//ArcanoidActivity - game screen 
public class ArcanoidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}