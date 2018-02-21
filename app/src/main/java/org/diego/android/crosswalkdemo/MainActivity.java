package org.diego.android.crosswalkdemo;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkView;

public class MainActivity extends XWalkActivity {
    private XWalkView xWalkWebView;


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Toast.makeText(getApplicationContext(), "keyCode="+event.getAction() + " " + event.getKeyCode(), Toast.LENGTH_LONG).show();
        if(event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case 19: //UP
                    xWalkWebView.evaluateJavascript("javascript:tab_up();", null);
                    break;
                case 20: //Down
                    xWalkWebView.evaluateJavascript("javascript:tab_down();", null);
                    break;
                case 21: //Left
                    xWalkWebView.evaluateJavascript("javascript:tab_prev();", null);
                    break;
                case 22: //Right
                    xWalkWebView.evaluateJavascript("javascript:tab_next();", null);
                    break;
                case 23: //Center
                    xWalkWebView.evaluateJavascript("javascript:tab_click();", null);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        boolean handled = false;

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_CENTER:
                handled = true;
                break;
            case KeyEvent.KEYCODE_BUTTON_A:
                // ... handle selections
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // ... handle left action
                handled = true;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // ... handle right action
                handled = true;
                break;
        }

        return handled || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        xWalkWebView=(XWalkView)findViewById(R.id.xwalkWebView);

        // turn on debugging
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        xWalkWebView.setResourceClient(myResourceClient);


    }

    @Override
    protected void onXWalkReady() {
        // Do anything with embedding API
        xWalkWebView.load("http://220.90.136.228:1220/001_sample/index.html", null);
    }

    XWalkResourceClient myResourceClient = new XWalkResourceClient(xWalkWebView){
        @Override
        public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
            if(url.contains("whatever")){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;
            }

            return super.shouldOverrideUrlLoading(view, url);
        }
    };

}
