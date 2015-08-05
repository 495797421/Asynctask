package com.example.asynctask;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.asynctask.adpter.ListAdapter;
import com.example.asynctask.bean.News;
import com.example.asynctask.config.CustomApplication;
import com.example.asynctask.httputils.HttpUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView mListView;
	private ListAdapter mAdapter;
	private static final String URL = "http://www.imooc.com/api/teacher?type=4&num=100";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.listview);
		MyAnsycTask task = new MyAnsycTask();
		task.execute(new String[] { URL });
		Log.i("asynctask", CustomApplication.getInstance().getNetWorkState()
				+ "");
	}

	class MyAnsycTask extends AsyncTask<String, Void, List<News>> {

		@Override
		protected void onPostExecute(List<News> result) {
			super.onPostExecute(result);
			mAdapter = new ListAdapter(result, MainActivity.this);
			mListView.setAdapter(mAdapter);

		}

		@Override
		protected List<News> doInBackground(String... params) {
			List<BasicNameValuePair> paramslist = new ArrayList<BasicNameValuePair>();
			paramslist.add(new BasicNameValuePair("type", 4 + ""));
			paramslist.add(new BasicNameValuePair("num", 30 + ""));
			String jsonstring = HttpUtils.POST(params[0], paramslist);
			Log.i("asynctask", jsonstring);
			List<News> data = new ArrayList<News>();
			try {
				JSONObject obj = new JSONObject(jsonstring);
				JSONArray array = obj.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					News news = new News();
					JSONObject bean = array.getJSONObject(i);
					news.setTitle(bean.getString("name"));
					news.setContent(bean.getString("description"));
					news.setPicurl(bean.getString("picSmall"));
					data.add(news);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return data;
		}
	}

}
