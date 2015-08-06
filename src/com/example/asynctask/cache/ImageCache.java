package com.example.asynctask.cache;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageCache {
	private static final String IMAGE_DISK_CACHE_PATH = "/imagecache/";// 设置图片的磁盘缓存路径
	private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache;// 通过软引用的方式设置图片的内存缓存
	private String diskCachePath;// 磁盘缓存路径
	private boolean diskCacheEnabled = false;
	private ExecutorService writeThread;//

	public ImageCache(Context context) {
		memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();
		diskCachePath = context.getApplicationContext().getCacheDir()
				.getAbsolutePath()
				+ IMAGE_DISK_CACHE_PATH;
		// 设置输出路径
		File outFile = new File(diskCachePath);
		// 如果路径不存在则创建路径
		if (!outFile.exists()) {
			diskCacheEnabled = outFile.mkdirs();
		}
		writeThread = Executors.newSingleThreadExecutor();
	};

	public Bitmap getBitmap(String url) {
		Bitmap bitmap = null;
		// 先从内存中读取图片
		bitmap = getBitmapFromMemory(url);
		// 如果不存在再从磁盘中读取图片
		if (bitmap == null) {
			bitmap = getBitmapFromDisk(url);
			if (bitmap != null) {
				cacheBitmapToMemory(url, bitmap);
			}
		}

		return bitmap;
	}

	private Bitmap getBitmapFromMemory(String url) {
		return null;
	}

	private Bitmap getBitmapFromDisk(String url) {
		return null;
	}

	private Bitmap cacheBitmapToDisk(String url) {
		return null;
	}

	private Bitmap cacheBitmapToMemory(String url, Bitmap bitmap) {
		return null;
	}
}
