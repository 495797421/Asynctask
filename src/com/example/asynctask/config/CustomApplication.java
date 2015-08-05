package com.example.asynctask.config;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CustomApplication extends Application {
	private static CustomApplication instance;
	private int mNetWorkState;

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();
	}

	public static CustomApplication getInstance() {
		return instance;
	}

	public int getNetWorkState() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		// if(connectivityManager.get)
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		// switch (info.getType()) {
		// case ConnectivityManager.TYPE_MOBILE:
		// return ConnectivityManager.TYPE_MOBILE;
		// }
		if (info == null)
			return -1;
		return info.getType();

	}
}
