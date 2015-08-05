package com.example.asynctask.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseListAdapter<E> extends BaseAdapter {

	protected List<E> mList;
	protected Context mContext;
	protected LayoutInflater mInflater;

	public BaseListAdapter(List<E> list, Context context) {
		this.mList = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);// 创建视图时使用的对象
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = bindView(position, convertView, parent);
		return convertView;
	}

	public abstract View bindView(int position, View convertView,
			ViewGroup parent);
}
