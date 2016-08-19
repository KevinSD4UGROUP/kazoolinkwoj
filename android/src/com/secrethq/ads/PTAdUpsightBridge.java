package com.secrethq.ads;
import java.lang.ref.WeakReference;

import org.cocos2dx.lib.Cocos2dxActivity;

import com.google.ads.AdRequest;
import com.playhaven.android.Placement;
import com.playhaven.android.PlayHaven;
import com.playhaven.android.PlayHavenException;
import com.playhaven.android.req.OpenRequest;
import com.playhaven.android.view.FullScreen;

import android.R;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class PTAdUpsightBridge {
	private static PTAdUpsightBridge sInstance;
	private static final String TAG = "Upsight";
	private static Cocos2dxActivity activity;
	private static WeakReference<Cocos2dxActivity> s_activity;

	private static String token; 
	private static String key;
	
	public static PTAdUpsightBridge instance() {

		if (sInstance == null)
			sInstance = new PTAdUpsightBridge();
		return sInstance;
	}

	public static void initBridge(Cocos2dxActivity activity){
		Log.v(TAG, "PTAdUpsightBridge  -- INIT");

		PTAdUpsightBridge.s_activity = new WeakReference<Cocos2dxActivity>(activity);
		PTAdUpsightBridge.activity = activity;

	}

	public static void startSession( String token, String key ) {
		PTAdUpsightBridge.token = token;
		PTAdUpsightBridge.key = key;
		
		PTAdUpsightBridge.s_activity.get().runOnUiThread( new Runnable() {
			public void run() {
				try {
					PlayHaven.configure(PTAdUpsightBridge.activity, PTAdUpsightBridge.token, PTAdUpsightBridge.key);
				} catch (PlayHavenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				OpenRequest open = new OpenRequest();
				open.send(PTAdUpsightBridge.activity);

				Placement placement = new Placement("showinterstitial");
				placement.preload( PTAdUpsightBridge.activity );
				
//				PHPublisherContentRequest request = new PHPublisherContentRequest(PTAdUpsightBridge.activity, "showinterstitial");
//				request.preload();	
			}
		});
	}

	public static void showFullScreen() {
		Log.v(TAG, "showFullScreen");

		PTAdUpsightBridge.activity.startActivity(FullScreen.createIntent(PTAdUpsightBridge.activity, "showinterstitial"));
		
//		PHPublisherContentRequest request = new PHPublisherContentRequest(PTAdUpsightBridge.activity, "showinterstitial");
//		request.setOnContentListener(PlayHavenXBridge.instance());		
//		request.send();
	}

	public static void showBannerAd(){
		Log.v(TAG, "showBannerAd");

	}

	public static void hideBannerAd(){
		Log.v(TAG, "hideBannerAd");

	}

}
