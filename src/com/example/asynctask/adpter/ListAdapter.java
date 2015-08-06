package com.example.asynctask.adpter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asynctask.R;
import com.example.asynctask.bean.News;
import com.example.asynctask.imageloader.ImageLoader;
import com.example.asynctask.imageloader.ImageLoaderWithCache;

public class ListAdapter extends BaseListAdapter<News> implements
		OnScrollListener {

	private TextView title;
	private TextView content;
	private ImageView img;
	private int mStart, mEnd;
	private static String[] URLS;

	public ListAdapter(List<News> list, Context context) {
		super(list, context);
		ImageLoader imageLoader = ImageLoader.getInstance();
		URLS = new String[list.size()];
		for (int i = 0; i < URLS.length; i++)
			URLS[i] = list.get(i).getPicurl();
	}

	@Override
	public View bindView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.listview_item, parent, false);
		title = ViewHodler.getView(convertView, R.id.item_title);
		content = ViewHodler.getView(convertView, R.id.item_content);
		img = ViewHodler.getView(convertView, R.id.item_img);
		title.setText(mList.get(position).getTitle());
		content.setText(mList.get(position).getContent());
		String url = mList.get(position).getPicurl();
		img.setTag(url);// 设置URL为图片的标志，防止图片加载错位
		ImageLoaderWithCache.getInstance(mContext).showImage(img, url);
		return convertView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if (scrollState == SCROLL_STATE_IDLE) {
			// 加载可见项
		} else {
			// 停止所有加载项

		}
	}

	//
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mStart = firstVisibleItem;
		mEnd = visibleItemCount + firstVisibleItem;

	}
}
