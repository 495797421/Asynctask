package com.example.asynctask.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Reciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
			// BSToast.showLong(context, "网络不可以用");
			Log.i("ansyctask", "网络不可以用");
			// 改变背景或者 处理网络的全局变量
		} else if (mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
			Log.i("ansyctask", "移动网络");
		} else if (wifiNetInfo.isConnected()) {
			Log.i("ansyctask", "无线网络");
		}
	}

}
