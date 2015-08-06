package com.example.asynctask.utils;

import java.io.File;
import java.io.IOException;

import libcore.io.DiskLruCache;

import com.example.asynctask.config.CustomApplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

public class FileCache {
	private Context mContext;
	private String mUniqueName;
	private DiskLruCache mDiskLruCache;
	public static final int CONFIG_CACHE_MOBILE_TIMEOUT = 3600000; // 1 hour
	public static final int CONFIG_CACHE_WIFI_TIMEOUT = 300000; // 5 minute

	public static String getUrlCache(String url) {
		if (url == null) {
			return url;
		}
		String result = null;
		// File file = new File(AppApplication.mSdcardDataDir + "/"
		// + getCacheDecodeString(url));
		// if (file.exists() && file.isFile()) {
		// long expiredTime = System.currentTimeMillis()
		// - file.lastModified();
		// // 1. in case the system time is incorrect (the time is turn
		// // back long ago)
		// // 2. when the network is invalid, you can only read the cache
		// if (AppApplication.mNetWorkState != NetworkUtils.NETWORN_NONE
		// && expiredTime < 0) {
		// return null;
		// }
		// if (AppApplication.mNetWorkState == NetworkUtils.NETWORN_WIFI
		// && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
		// return null;
		// } else if (AppApplication.mNetWorkState ==
		// NetworkUtils.NETWORN_MOBILE
		// && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
		// return null;
		// }
		// try {
		// result = FileUtils.readTextFile(file);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		return result;
	}

	public FileCache(String uniqueName) {
		this.mContext = CustomApplication.getInstance().getApplicationContext();
		this.mUniqueName = uniqueName;

	}

	public FileCache openDiskLruCache() {
		// int maxSize = maxSize;
		try {
			File cachedir = getDiskCacheDir(mContext, mUniqueName);
			if (!cachedir.exists())
				cachedir.mkdirs();
			mDiskLruCache = DiskLruCache.open(cachedir,
					getAppVersion(mContext), 1, 20 * 1024 * 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	// 获得当前应用程序版本号
	public int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	// 获得缓存目录
	public File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	// public saveCache()
}
