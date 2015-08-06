package com.example.asynctask.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button {

	private Paint paint;

	public CustomButton(Context context) {
		this(context, null);
	}

	public CustomButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
	}

}
