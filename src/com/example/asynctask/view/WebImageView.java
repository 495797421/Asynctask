package com.example.asynctask.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebImageView extends ImageView {

	public WebImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WebImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public WebImageView(Context context) {
		super(context, null);
	}

	public void setImageURL(String url) {
		Bitmap bitmap = null;
		setImageBitmap(bitmap);
	}
}
