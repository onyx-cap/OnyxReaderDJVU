/**
 * 
 */
package com.onyx.android.reader.djvu.core;

import java.util.ArrayList;

import universe.constellation.orion.viewer.PageInfo;
import universe.constellation.orion.viewer.djvu.DjvuDocument;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;

import com.onyx.android.sdk.data.cms.OnyxMetadata;
import com.onyx.android.sdk.data.util.FileUtil;
import com.onyx.android.sdk.data.util.ProfileUtil;
import com.onyx.android.sdk.reader.DocPageLayout;
import com.onyx.android.sdk.reader.DocPagingMode;
import com.onyx.android.sdk.reader.DocTextSelection;
import com.onyx.android.sdk.reader.IDocumentModel;
import com.onyx.android.sdk.reader.Size;
import com.onyx.android.sdk.reader.TOCItem;

/**
 * @author joy
 *
 */
public class DjvuModel implements IDocumentModel
{
    private static final String TAG = "DjvuModel";
    
    private static final DjvuModel sInstance = new DjvuModel();
    
    /**
     * engine can not be accessed concurrently, using locker to enforce this point
     */
    private Object mSyncLocker = new Object();

    private DjvuDocument mDjvuDocument = new DjvuDocument();
    private String mDocPath = null;
    private boolean mOpened = false;
    
    private DjvuModel()
    {
        
    }
    
    public static DjvuModel singleton()
    {
        return sInstance;
    }

    @Override
    public boolean canOpen(String path)
    {
        String ext = FileUtil.getFileExtension(path);
        return ext.equalsIgnoreCase("djvu");
    }

    @Override
    public boolean isOpened()
    {
        synchronized (mSyncLocker) {
            return mOpened;
        }
    }

    @Override
    public String getFilePath()
    {
        synchronized (mSyncLocker) {
            return mDocPath;
        }
    }

    @Override
    public boolean openFile(String path)
    {
        synchronized (mSyncLocker) {
            boolean succ = mDjvuDocument.openDocument(path);
            if (succ) {
                mDocPath = path;
            }
            return succ;
        }
    }

    @Override
    public boolean close()
    {
        synchronized (mSyncLocker) {
            mDjvuDocument.destroy();
            return true;
        }
    }

    @Override
    public OnyxMetadata readMetadata()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void interrupt()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public DocPageLayout getPageLayout()
    {
        return DocPageLayout.FixedPage;
    }

    @Override
    public DocPagingMode getPagingMode()
    {
        return DocPagingMode.Hard_Pages;
    }

    @Override
    public boolean setPagingMode(DocPagingMode mode)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getPageCount()
    {
        synchronized (mSyncLocker) {
            return mDjvuDocument.getPageCount();
        }
    }

    @Override
    public double getPagePosition()
    {
        synchronized (mSyncLocker) {
            return mDjvuDocument.getCurrentPage();
        }
    }

    @Override
    public double getPagePositionOfLocation(String location)
    {
        return Integer.parseInt(location);
    }

    @Override
    public boolean gotoPagePosition(double page)
    {
        synchronized (mSyncLocker) {
            mDjvuDocument.gotoPage((int)page);
            return true;
        }
    }

    @Override
    public boolean gotoDocLocation(String location)
    {
        synchronized (mSyncLocker) {
            int n = Integer.parseInt(location);
            return this.gotoPagePosition(n);
        }
    }

    @Override
    public String getScreenText()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean previousScreen()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean nextScreen()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setFontSize(double size)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Size getPageNaturalSize()
    {
        synchronized (mSyncLocker) {
            PageInfo info = mDjvuDocument.getPageInfo(mDjvuDocument.getCurrentPage());
            return new Size(info.width, info.height);
        }
    }

    @Override
    public Rect getPageContentArea()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean navigatePage(double zoom, int scrollX, int scrollY)
    {
        return true;
    }

    @Override
    public Bitmap renderPage(double zoom, int left, int top, int width, int height, Config conf, boolean isPrefetch)
    {
        synchronized (mSyncLocker) {
            ProfileUtil.start(TAG, "drawPage");
            int[] data = mDjvuDocument.renderPage(zoom, left, top, left + width, top + height);
            if (data == null) {
                ProfileUtil.end(TAG, "drawPage");
                return null;
            }
            Bitmap b = Bitmap.createBitmap(data, width, height, conf);
            ProfileUtil.end(TAG, "drawPage");

            return b;
        }
    }

    @Override
    public boolean hasTOC()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public TOCItem[] getTOC()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isLocationInCurrentScreen(String location)
    {
        synchronized (mSyncLocker) {
            int n = Integer.parseInt(location);
            return n == mDjvuDocument.getCurrentPage();
        }
    }

    @Override
    public String getScreenBeginningLocation()
    {
        synchronized (mSyncLocker) {
            return String.valueOf(mDjvuDocument.getCurrentPage());
        }
    }

    @Override
    public String getScreenEndLocation()
    {
        synchronized (mSyncLocker) {
            return String.valueOf(mDjvuDocument.getCurrentPage());
        }
    }

    @Override
    public boolean isGlyphEmboldenEnabled()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setGlyphEmboldenEnabled(boolean enable)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DocTextSelection hitTestWord(int x, int y)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocTextSelection moveSelectionBegin(int x, int y)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocTextSelection moveSelectionEnd(int x, int y)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DocTextSelection measureSelection(String locationBegin, String locationEnd)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<DocTextSelection> searchInCurrentScreen(String pattern)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String searchForwardAfterCurrentScreen(String pattern)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String searchBackwardBeforeCurrentScreen(String pattern)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] searchAllInDocument(String pattern)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
