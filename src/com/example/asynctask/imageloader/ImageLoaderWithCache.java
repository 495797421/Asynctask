package com.example.asynctask.imageloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.asynctask.httputils.HttpUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class ImageLoaderWithCache {
	private LruCache<String, Bitmap> mCache;// 先进先出的缓存
	private static ImageLoaderWithCache instance;// 当前实例
	private static final String DISK_CACHE_PATH = "/image_cache/";
	private String diskCachePath;
	private boolean diskCacheEnabled = false;
	private ExecutorService writeThread;

	// 单例模式
	public static ImageLoaderWithCache getInstance(Context context) {
		// 双重校验锁
		if (instance == null) {
			synchronized (ImageLoaderWithCache.class) {
				if (instance == null)
					instance = new ImageLoaderWithCache(context);
			}
		}
		return instance;
	}

	private ImageLoaderWithCache(Context context) {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();// 最大可用内存
		int cacheSize = maxMemory / 4;// 设置缓存内存
		mCache = new LruCache<String, Bitmap>(cacheSize) {
			// 重写sizeOf方法，可以正确返回Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
		diskCachePath = context.getApplicationContext().getCacheDir()
				.getAbsolutePath()
				+ DISK_CACHE_PATH;
		File outFile = new File(diskCachePath);
		outFile.mkdirs();

		diskCacheEnabled = outFile.exists();
		writeThread = Executors.newSingleThreadExecutor();
	}

	/**
	 * 加入缓存
	 * 
	 * @param url
	 * @param bitmap
	 */
	private void addBitmapToCache(final String url, final Bitmap bitmap) {
		if (getBitmapFromMemoryCache(url) == null) {
			mCache.put(url, bitmap);
		}
		if (getBitmapFromDiskCache(url) == null) {
			writeThread.execute(new Runnable() {
				@Override
				public void run() {
					if (diskCacheEnabled) {
						BufferedOutputStream ostream = null;
						try {
							ostream = new BufferedOutputStream(
									new FileOutputStream(new File(
											diskCachePath, getCacheKey(url))),
									2 * 1024);
							bitmap.compress(CompressFormat.PNG, 100, ostream);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} finally {
							try {
								if (ostream != null) {
									ostream.flush();
									ostream.close();
								}
							} catch (IOException e) {
							}
						}
					}
				}
			});
		}
	}

	/**
	 * 从内存缓存中读取位图
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromMemoryCache(String url) {
		return mCache.get(url);
	}

	/**
	 * 从磁盘缓存中读取Bitmap
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFromDiskCache(String url) {
		Bitmap bitmap = null;
		if (diskCacheEnabled) {
			String filePath = getFilePath(url);
			File file = new File(filePath);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(filePath);
			}
		}
		return bitmap;
	}

	/**
	 * 传递图片的URL,显示图片
	 * 
	 * @param imageview
	 * @param url
	 */
	public void showImage(ImageView imageview, String url) {
		// 防止图片错位
		if (imageview.getTag().equals(url)) {
			Bitmap bitmap = null;
			// 先从内存中获取位图
			bitmap = getBitmapFromMemoryCache(url);
			// 如果不存在，从磁盘缓存中获取位图
			if (bitmap == null) {
				// 从磁盘缓存中获取Bitmap
				bitmap = getBitmapFromDiskCache(url);
				// 如果磁盘缓存中不存在，再从网络上加载图片
				if (bitmap == null) {
					MyAsyncTask task = new MyAsyncTask(imageview);
					task.execute(url);
				}
				// 否则加载到内存缓存中，并显示图片
				else {
					addBitmapToCache(url, bitmap);
					imageview.setImageBitmap(bitmap);
					System.out.println("从磁盘加载");
				}
			}
			// 否则显示图片
			else {
				imageview.setImageBitmap(bitmap);
				System.out.println("从内存加载");
			}
		}
	}

	private String getFilePath(String url) {
		return diskCachePath + getCacheKey(url);
	}

	private String getCacheKey(String url) {
		if (url == null) {
			throw new RuntimeException("Null url passed in");
		} else {
			return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
	}

	private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

		private ImageView imageview;

		public MyAsyncTask(ImageView imageView) {
			this.imageview = imageView;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			imageview.setImageBitmap(result);
			System.out.println("从网络加载");
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bit = null;
			String url = params[0];
			bit = BitmapFactory.decodeStream(HttpUtils.getInputStream2(url));
			addBitmapToCache(url, bit);
			return bit;
		}
	}

}
