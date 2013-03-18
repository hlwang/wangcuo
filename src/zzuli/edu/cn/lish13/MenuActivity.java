package zzuli.edu.cn.lish13;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	int width;
	GridView grid;
	TextView message;
	TextView author_message;
	
	ImageResource ir = ImageResource.getImageResource();
	ToastTool toast;
	Handler handler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        float density = getResources().getDisplayMetrics().density;
        width = (int) (getWindowManager().getDefaultDisplay().getWidth()/density);
        message = (TextView) findViewById(R.id.gallary_text_926);
        grid = (GridView) findViewById(R.id.gallary_picture_menu_925);
        author_message = (TextView) findViewById(R.id.gallary_text_927);
        
        loadingPicture();
    }
    
    public void loadingPicture(){
    	new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				publishProgress();
				ir.loadingBitmap(getResources(), width, 3);
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				handler.removeCallbacks(update);
				message.setVisibility(View.GONE);
				author_message.setVisibility(View.GONE);
				grid.setVisibility(View.VISIBLE);
				grid.setNumColumns(3);
				grid.setHorizontalSpacing(20);
				grid.setVerticalSpacing(40);
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent();
						intent.setClass(MenuActivity.this, ShowActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("num",position);
						intent.putExtras(bundle);
						MenuActivity.this.startActivity(intent);
					}
				});
				adapter.notifyDataSetChanged();
				super.onPostExecute(result);
			}

			@Override
			protected void onProgressUpdate(Object... values) {
				
				handler = new Handler();
				//显示加载进度
				handler.post(update);
				
				super.onProgressUpdate(values);
			}
		}.execute();
    }
    
    BaseAdapter adapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(MenuActivity.this);
			iv.setMaxWidth(width / 3 - 30);
			iv.setAdjustViewBounds(true);
			iv.setImageBitmap(ir.getIconBitmap(position));
			return iv;
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int arg0) {
			return arg0;
		}
		
		@Override
		public int getCount() {
			return ir.size();
		}
	};

	@Override
	protected void onDestroy() {
		System.exit(0);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			
			if(toast.isDoubleClick("再次点击返回按钮即可退出此应用……")){
				finish();
			}
			
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onResume() {
		toast = ToastTool.getToast(MenuActivity.this);
		super.onResume();
		toast.showMessage("双击返回按钮即可退出此应用……");
	}
	
	Runnable update = new Runnable() {
		@Override
		public void run() {
			int progress = ir.getProgress();
			if(null != message){
				message.setText("数据加载中（"+progress+"%），请稍等……\n\n");
			}
			if(100 == progress){
				handler.removeCallbacks(update);
			} else {
				handler.postDelayed(update, 200);
			}
		}
	};
}