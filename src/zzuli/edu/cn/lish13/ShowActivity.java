package zzuli.edu.cn.lish13;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;

public class ShowActivity extends Activity {

	Bitmap bitmap;
	ShowGirlView sgv;
	
	ToastTool toast;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        int num = intent.getIntExtra("num", 0);
        
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        sgv = new ShowGirlView(ShowActivity.this, width, height, num);
        
        setContentView(sgv);
    }

	@Override
	protected void onDestroy() {
		if(null != sgv)
			sgv.realease();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			
			if(toast.isDoubleClick("�ٴε�����ذ�ť���ɷ������˵�����")){
				finish();
			}
			
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onResume() {
		toast = ToastTool.getToast(ShowActivity.this);
		super.onResume();
		toast.showMessage("˫�����ذ�ť���ɷ������˵�����");
	}
}
