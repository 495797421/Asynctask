package com.example.asynctask.adpter;

import android.util.SparseArray;
import android.view.View;

public class ViewHodler {
	public static <T extends View> T getView(View contentView, int id) {
		SparseArray<View> holder = (SparseArray<View>) contentView.getTag();
		if (holder == null) {
			holder = new SparseArray<View>();
			contentView.setTag(holder);
		}
		View view = holder.get(id);
		if (view == null) {
			view = contentView.findViewById(id);
			holder.put(id, view);
		}
		return (T) view;
	}
}
