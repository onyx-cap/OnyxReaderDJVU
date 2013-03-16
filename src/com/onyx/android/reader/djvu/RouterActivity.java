/**
 * 
 */
package com.onyx.android.reader.djvu;

import java.io.File;

import com.onyx.android.sdk.reader.ReaderRemoteServiceUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * @author joy
 *
 */
public class RouterActivity extends Activity
{
    private static final String TAG = "RouterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");
        
        super.onCreate(savedInstanceState);
        
        this.handleNewIntent();
    }
    
    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.v(TAG, "onNewIntent");
        
        this.setIntent(intent);
        this.handleNewIntent();
    }
    
    @Override
    protected void onDestroy()
    {
        Log.v(TAG, "onDestroy");
        
        super.onDestroy();
    }
    
    private boolean handleNewIntent()
    {
        try {
            if (this.getIntent().getAction().equals(Intent.ACTION_VIEW)) {
                Uri uri = this.getIntent().getData();
                if (uri == null) {
                    return false;
                }

                Log.d(TAG, "handleNewIntent, file uri: " + uri.toString());
                String file_path = uri.getPath();

                File file = new File(file_path);
                return ReaderRemoteServiceUtil.startReaderWithService(this, DjvuService.DJVU_SERVICE_COMPONENT, file);
            }

            return false;
        }
        finally {
            this.finish();
        }
    }
    
}
