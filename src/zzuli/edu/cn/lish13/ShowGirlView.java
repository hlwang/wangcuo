package zzuli.edu.cn.lish13;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ShowGirlView extends View {
	
	Bitmap fore_bitmap;
	Bitmap back_bitmap;
	Bitmap temp_bitmap;
	Canvas canvasTemp;
	Paint paint;
	Path path;
	
	int startX;
	int startY;
	
	int width;
	int height;
	int num;
	Matrix matrix;
	ImageResource ir;

	public ShowGirlView(Context context, int width, int height, int num) {
		super(context);
		this.width = width;
		this.height = height;
		this.num = num;
		init();
	}

	public ShowGirlView(Context context, AttributeSet attrs, int width, int height, int num) {
		super(context, attrs);
		this.width = width;
		this.height = height;
		this.num = num;
		init();
	}

	void init() {
		
		ir = ImageResource.getImageResource();
		
		temp_bitmap = ir.getForeBitmap(getResources(), num);
		back_bitmap = ir.getBackBitmap(getResources(), num);
		fore_bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
		
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		
		canvasTemp = new Canvas(fore_bitmap);
		canvasTemp.drawColor(Color.TRANSPARENT);
		matrix = new Matrix();
		matrix.setScale(width*1.0F/temp_bitmap.getWidth(), 
				height*1.0F/temp_bitmap.getHeight());
		canvasTemp.drawBitmap(temp_bitmap, matrix, paint);
		temp_bitmap.recycle();
		
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        BlurMaskFilter bmf = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);
        paint. setMaskFilter(bmf);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
		
		path = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawBitmap(back_bitmap, matrix, null);
		canvas.drawBitmap(fore_bitmap, 0, 0, null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int x = (int) event.getX();
		int y = (int) event.getY();
		int position = event.getAction();
		
		int endX = 0;
		int endY = 0;
		
		switch (position)
		{
			case MotionEvent.ACTION_DOWN:
				startX = x;
				startY = y;
				endX = x;
				endY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				endX = x;
				endY = y;
				break;
			case MotionEvent.ACTION_UP:
				endX = x;
				endY = y;
				break;
		}
		
		path.moveTo(startX, startY);
		path.lineTo(endX, endY);
		canvasTemp.drawPath(path, paint);
		
		postInvalidate();
		
		startX = endX;
		startY = endY;
		
		return true;
	}
	
	public void realease(){
		if(null != back_bitmap && !fore_bitmap.isRecycled())
			back_bitmap.recycle();
		if(null != fore_bitmap && !fore_bitmap.isRecycled())
			fore_bitmap.recycle();
	}
}
