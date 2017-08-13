package com.widuu;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
import android.media.MediaScannerConnection;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by widuu on 2017/8/12.
 */

public class SaveImageModule extends ReactContextBaseJavaModule{

    private static Context mContext;
    private static Bitmap  mBitmap;
    private static String  saveDir = "saveImage";
    private static int     compressQuality = 100;
    private static String  saveMessage;
    private static String  filePath;
    private static String  suffix;
    private static String  fileName;
    private static ProgressDialog mSaveDialog = null;

    private static final String TAG = "saveImageModule";

    public SaveImageModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SaveImageModule";
    }
    
    @ReactMethod
    public void downloadImage(String url,String saveName){
        mContext = getCurrentActivity();
        filePath = url;
        suffix = filePath.substring( filePath.lastIndexOf(".") + 1 );
        if( suffix.indexOf("?") != -1 ){
            suffix = suffix.substring(0, suffix.indexOf("?"));
        }
        mSaveDialog = ProgressDialog.show(mContext,"保存图片","图片正在保存中...",true);
        new Thread(saveFileRunnable).start();
    }

    @ReactMethod
    public void setCompressQuality(int value){
        compressQuality = value;
    }

    @ReactMethod
    public void setAlbumName(String albumName){
        saveDir = albumName;
    }

    private  Runnable saveFileRunnable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            Handler messageHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mSaveDialog.dismiss();
                    Toast.makeText(mContext, saveMessage, Toast.LENGTH_SHORT).show();
                }
            };
            try{
                mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
                if( mBitmap == null ){
                    saveMessage = "网络传输错误";
                }else{
                    saveFile(mBitmap);
                    saveMessage = "图片保存成功";
                }
            }catch (IOException e) {
                saveMessage = "图片保存失败！";
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
            Looper.loop();
        }

    };


    private  InputStream getImageStream(String filePath) throws Exception{
        URL url = new URL(filePath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }

    private void saveFile(Bitmap bm) throws IOException {
        String sdcardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        File dirPath = new File(sdcardPath,saveDir);
        if(!dirPath.exists()){
            dirPath.mkdir();
        }

        String fileName = System.currentTimeMillis() + "." + suffix;

        File imageFile = new File( dirPath,fileName );

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
        Bitmap.CompressFormat type;
        switch (suffix.toLowerCase()){
            case "jpg":
                type = Bitmap.CompressFormat.JPEG;
                break;
            case "jpeg":
                type = Bitmap.CompressFormat.JPEG;
                break;
            case "png":
                type = Bitmap.CompressFormat.PNG;
                break;
            case "webp":
                type = Bitmap.CompressFormat.WEBP;
                break;
            default:
                type = null;
        }
        
        if( type != null ){
            bm.compress(type, compressQuality, bos);
        }

        bos.flush();
        bos.close();
        MediaScannerConnection.scanFile(mContext, new String[] { imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri)
            {
                if( uri == null ){
                    saveMessage = "添加图片错误";
                }
            }
        });

    }

}