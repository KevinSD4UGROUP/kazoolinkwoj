package com.secrethq.ads;
import java.lang.ref.WeakReference;

import org.cocos2dx.lib.Cocos2dxActivity;
import android.util.Log;

import com.apptracker.android.listener.AppModuleListener;
import com.apptracker.android.track.AppTracker;
// Leadbolt SDK imports

public class PTAdLeadBoltBridge {
	private static PTAdLeadBoltBridge sInstance;
	private static final String TAG = "PTAdLeadBoltBridge";
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;

	private static String LB_INTERSTITIAL_ID;
	
	private static AppModuleListener leadboltListener = new AppModuleListener() {
	    @Override
	    public void onModuleLoaded(String location) {
	        Log.i("AppTracker", "Ad loaded successfully - "+location);
	    }
	    @Override
	    public void onModuleFailed(String location, String error, boolean isCache) {
	        if(isCache) {
	            Log.i("AppTracker", "Ad failed to cache - "+location);
	        } else {
	            Log.i("AppTracker", "Ad failed to display - "+location);
	        }
	    }
	    @Override
	    public void onModuleClosed(String location) {
	        Log.i("AppTracker", "Ad closed by user - "+location);
	    }
	    @Override
	    public void onModuleClicked(String location) {
	        Log.i("AppTracker", "Ad clicked by user - "+location);
	    }
	    @Override
	    public void onModuleCached(String location) {
	        Log.i("AppTracker", "Ad cached successfully - "+location);
	    }
	    @Override
	    public void onMediaFinished(boolean viewCompleted) {
	        if(viewCompleted) {
	            Log.i("AppTracker", "User finished watching rewarded video");
	        } else {
	            Log.i("AppTracker", "User skipped watching rewarded video");
	        }
	    }
	};

	public static PTAdLeadBoltBridge instance() {

		if (sInstance == null)
			sInstance = new PTAdLeadBoltBridge();
		return sInstance;
	}

	
	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdLeadBoltBridge  -- INIT");

		PTAdLeadBoltBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);
		PTAdLeadBoltBridge.activity = activity;
	}

	public static void startSession( String sdkKey ){
		Log.v(TAG, "startSession  sdkKey : " + sdkKey);
		PTAdLeadBoltBridge.LB_INTERSTITIAL_ID = sdkKey;
		
		PTAdLeadBoltBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {	
				try {
					AppTracker.setModuleListener(leadboltListener);
					AppTracker.startSession(PTAdLeadBoltBridge.activity.getApplicationContext(), PTAdLeadBoltBridge.LB_INTERSTITIAL_ID);
					AppTracker.loadModuleToCache(PTAdLeadBoltBridge.activity.getApplicationContext(), "inapp");
				} catch (Exception e) {
					Log.v(TAG, "startSession FAILED : " + e.getMessage());
				}
			}
		});
	}

	public static void showFullScreen() {
		Log.v(TAG, "showFullScreen");

		PTAdLeadBoltBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {	
				try {
					AppTracker.loadModule(PTAdLeadBoltBridge.activity.getApplicationContext(), "inapp");
				} catch (Exception e) {
					Log.v(TAG, "showFullScreen FAILED : " + e.getMessage());
				}
			}
		});			
	}

	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");

	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");

	}

}
