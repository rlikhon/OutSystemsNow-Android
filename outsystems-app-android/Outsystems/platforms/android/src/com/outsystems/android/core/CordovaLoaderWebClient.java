/*
 * OutSystems Project
 * 
 * Copyright (C) 2014 OutSystems.
 * 
 * This software is proprietary.
 */
package com.outsystems.android.core;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;


import android.content.res.AssetManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;


/**
 * Class description.
 * 
 * @author <a href="mailto:vmfo@xpand-it.com">vmfo</a>
 * @version $Revision: 666 $
 * 
 */
public class CordovaLoaderWebClient extends SystemWebViewClient {

    /** The identifier cordova. */
    private static String[] CORDOVA_IDENTIFIERS = {"/cdvload/", "/scripts/"};

    /** The mngr. */
    private AssetManager mngr;

    /**
     * @param cordova
     * @param engine
     */
    public CordovaLoaderWebClient(CordovaInterface cordova, SystemWebViewEngine engine){
        super(engine);
        mngr = cordova.getActivity().getAssets();
    }


    /*
     * (non-Javadoc)
     * 
     * @see android.webkit.WebViewClient#shouldInterceptRequest(android.webkit.WebView, java.lang.String)
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

        for (String cordovaID: CORDOVA_IDENTIFIERS) {
            if (url.contains(cordovaID)) {
                // Get path to load local file Cordova JS
                String[] split = url.split(cordovaID);

                String path = "";
                if (split.length > 1) {
                    path = split[1];
                }

                if(path.contains("cordova.js?")) {
                    path = path.split("\\?")[0];
                }

                try {
                    InputStream stream = mngr.open("www/" + path);
                    WebResourceResponse response = new WebResourceResponse("text/javascript", "UTF-8", stream);
                    return response;

                } catch (IOException e) {
                    //EventLogger.logError(getClass(), e);
                }
            }
        }

        return null;
    }
}
