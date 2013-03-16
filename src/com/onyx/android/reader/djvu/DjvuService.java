/**
 * 
 */
package com.onyx.android.reader.djvu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Log;

import com.onyx.android.reader.djvu.core.DjvuModel;
import com.onyx.android.sdk.reader.DocPageLayout;
import com.onyx.android.sdk.reader.DocPagingMode;
import com.onyx.android.sdk.reader.DocTextSelection;
import com.onyx.android.sdk.reader.IRemoteReaderService;
import com.onyx.android.sdk.reader.Size;
import com.onyx.android.sdk.reader.TOCItem;

/**
 * @author joy
 *
 */
public class DjvuService extends Service
{
    private static final String TAG = "DjvuService";
    
    public static final ComponentName DJVU_SERVICE_COMPONENT = new ComponentName("com.onyx.android.reader.djvu", "com.onyx.android.reader.djvu.DjvuService");
    
    @Override
    public void onCreate()
    {
        Log.v(TAG, "onCreate");
        
        super.onCreate();
    }
    
    @Override
    public void onDestroy()
    {
        Log.v(TAG, "onDestroy");
        
        super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.v(TAG, "onBind");
        
        return mBinder;
    }
    
    @Override
    public void onRebind(Intent intent)
    {
        Log.v(TAG, "onRebind");
        
        super.onRebind(intent);
    }
    
    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.v(TAG, "onUnbind");
        
        return super.onUnbind(intent);
    }
    
    private final IRemoteReaderService.Stub mBinder = new IRemoteReaderService.Stub()
    {
        
        @Override
        public boolean canOpen(String path)
        {
            return DjvuModel.singleton().canOpen(path);
        }

        @Override
        public boolean isOpened()
        {
            return DjvuModel.singleton().isOpened();
        }

        @Override
        public String getFilePath()
        {
            return DjvuModel.singleton().getFilePath();
        }

        @Override
        public boolean openFile(String path)
        {
            return DjvuModel.singleton().openFile(path);
        }

        @Override
        public boolean close()
        {
            return DjvuModel.singleton().close();
        }

        @Override
        public void interrupt()
        {
            DjvuModel.singleton().interrupt();
        }

        @Override
        public void resume()
        {
            DjvuModel.singleton().resume();
        }

        @Override
        public int getPageCount()
        {
            return DjvuModel.singleton().getPageCount();
        }

        @Override
        public DocPageLayout getPageLayout()
        {
            return DjvuModel.singleton().getPageLayout();
        }

        @Override
        public DocPagingMode getPagingMode()
        {
            return DjvuModel.singleton().getPagingMode();
        }

        @Override
        public boolean setPagingMode(DocPagingMode mode)
        {
            return DjvuModel.singleton().setPagingMode(mode);
        }

        @Override
        public double getPagePosition()
        {
            return DjvuModel.singleton().getPagePosition();
        }

        @Override
        public double getPagePositionOfLocation(String location)
        {
            return DjvuModel.singleton().getPagePositionOfLocation(location);
        }

        @Override
        public boolean gotoPagePosition(double page)
        {
            return DjvuModel.singleton().gotoPagePosition(page);
        }

        @Override
        public boolean gotoDocLocation(String location)
        {
            return DjvuModel.singleton().gotoDocLocation(location);
        }

        @Override
        public boolean previousScreen()
        {
            return DjvuModel.singleton().previousScreen();
        }

        @Override
        public boolean nextScreen()
        {
            return DjvuModel.singleton().nextScreen();
        }

        @Override
        public boolean isLocationInCurrentScreen(String location)
        {
            return DjvuModel.singleton().isLocationInCurrentScreen(location);
        }

        @Override
        public String getScreenBeginningLocation()
        {
            return DjvuModel.singleton().getScreenBeginningLocation();
        }

        @Override
        public String getScreenEndLocation()
        {
            return DjvuModel.singleton().getScreenEndLocation();
        }

        @Override
        public Size getPageNaturalSize()
        {
            return DjvuModel.singleton().getPageNaturalSize();
        }

        @Override
        public Rect getPageContentArea()
        {
            return DjvuModel.singleton().getPageContentArea();
        }

        @Override
        public String getScreenText()
        {
            return DjvuModel.singleton().getScreenText();
        }

        @Override
        public boolean navigatePage(double zoom, int scrollX, int scrollY)
        {
            return DjvuModel.singleton().navigatePage(zoom, scrollX, scrollY);
        }

        @Override
        public byte[] renderPage(double zoom, int left, int top, int width, int height, boolean isPrefetch)
        {
            Bitmap b = DjvuModel.singleton().renderPage(zoom, left, top, width, height, Bitmap.Config.RGB_565, isPrefetch);
            if (b == null) {
                return null;
            }
            
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                b.compress(Bitmap.CompressFormat.JPEG, 100, os);
                return os.toByteArray();
            }
            finally {
                try {
                    os.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "exception", e);
                }
            }
        }

        @Override
        public boolean hasTOC()
        {
            return DjvuModel.singleton().hasTOC();
        }

        @Override
        public TOCItem[] getTOC()
        {
            return DjvuModel.singleton().getTOC();
        }

        @Override
        public boolean setFontSize(double size)
        {
            return DjvuModel.singleton().setFontSize(size);
        }

        @Override
        public boolean isGlyphEmboldenEnabled()
        {
            return DjvuModel.singleton().isGlyphEmboldenEnabled();
        }

        @Override
        public boolean setGlyphEmboldenEnabled(boolean enable)
        {
            return DjvuModel.singleton().setGlyphEmboldenEnabled(enable);
        }

        @Override
        public DocTextSelection hitTestWord(int x, int y)
        {
            return DjvuModel.singleton().hitTestWord(x, y);
        }

        @Override
        public DocTextSelection moveSelectionBegin(int x, int y)
        {
            return DjvuModel.singleton().moveSelectionBegin(x, y);
        }

        @Override
        public DocTextSelection moveSelectionEnd(int x, int y)
        {
            return DjvuModel.singleton().moveSelectionEnd(x, y);
        }

        @Override
        public DocTextSelection measureSelection(String locationBegin, String locationEnd)
        {
            return DjvuModel.singleton().measureSelection(locationBegin, locationEnd);
        }

        @Override
        public List<DocTextSelection> searchInCurrentScreen(String pattern)
        {
            return DjvuModel.singleton().searchInCurrentScreen(pattern);
        }

        @Override
        public String searchForwardAfterCurrentScreen(String pattern)
        {
            return DjvuModel.singleton().searchForwardAfterCurrentScreen(pattern);
        }

        @Override
        public String searchBackwardBeforeCurrentScreen(String pattern)
        {
            return DjvuModel.singleton().searchBackwardBeforeCurrentScreen(pattern);
        }

        @Override
        public String[] searchAllInDocument(String pattern)
        {
            return DjvuModel.singleton().searchAllInDocument(pattern);
        }
    };

}
