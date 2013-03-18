package zzuli.edu.cn.lish13;

import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class ToastTool {
	
	private static Toast toast;
	private static ToastTool tt;
	private static Context context;
	
	private static long preTime;
	//双击的最大时间间隔
	private static long totalTime = 2000;
	
	private ToastTool(Context context){
		this.context = context;
		preTime = 0;
	};
	
	public static ToastTool getToast(Context context){
		if(null == tt){
			tt = new ToastTool(context);
		}
		return tt;
	}
	
	/**
	 * 输出消息
	 * @param message
	 */
	public void showMessage(String message){
		if(null != toast)
			toast.cancel();
		toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * 判断是否双击，不是的话，输出消息
	 * @param message
	 * @return
	 */
	public boolean isDoubleClick(String message){
		
		if(null != toast)
			toast.cancel();
		
		long nowTime = new Date().getTime();
		if(nowTime < preTime + totalTime){
			return true;
		} else {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.show();
			preTime = nowTime;
			return false;
		}
	}
}
