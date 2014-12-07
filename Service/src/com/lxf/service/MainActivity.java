package com.lxf.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lxf.service.DownloadService.DownloadBinder;
import com.lxf.util.LogUtil;
import com.lxf.util.Tools;

/**
 * <p>
 * 这个例子主要是演示Service和Activity的之间的生命周期。
 * 在bindService的情况下，Service会随着Activity的生命周期结束而结束吗？
 * </p>
 * 
 * @author lxf
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	protected static final String TAG = "MainActivity";
	private ServiceConnection mDownloadServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.e(TAG, "onServiceDisconnected");

		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			LogUtil.d(TAG, "onServiceConnected");
			Tools.showToast(MainActivity.this, "onServiceConnected");
			mDownloadBinder = (DownloadBinder) arg1;
		}
	};
	private DownloadBinder mDownloadBinder;

	private ProgressBar showPB;
	private Button startDownloadBt;
	private Button startServiceBt;
	private Button stopServiceBt;
	private Button bindServiceBt;
	private Button unBindServiceBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent startIntent = new Intent(this, DownloadService.class);
		startService(startIntent);

		Intent bindDownloadService = new Intent(this, DownloadService.class);
		bindService(bindDownloadService, mDownloadServiceConnection,
				BIND_AUTO_CREATE);

		showPB = (ProgressBar) findViewById(R.id.showPB);

		startDownloadBt = (Button) findViewById(R.id.startDownloadBt);
		startDownloadBt.setOnClickListener(this);

		startServiceBt = (Button) findViewById(R.id.startServiceBt);
		startServiceBt.setOnClickListener(this);

		stopServiceBt = (Button) findViewById(R.id.stopServiceBt);
		stopServiceBt.setOnClickListener(this);

		bindServiceBt = (Button) findViewById(R.id.bindServiceBt);
		bindServiceBt.setOnClickListener(this);

		unBindServiceBt = (Button) findViewById(R.id.unBindServiceBt);
		unBindServiceBt.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
		unbindService(mDownloadServiceConnection);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.startDownloadBt:
			LogUtil.d(TAG, "Click startDownloadBt");
			if (mDownloadBinder != null) {
				mDownloadBinder.startDownload(R.id.showPB, showPB);
			} else {
				Toast.makeText(this, "服务未启动", Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.startServiceBt:
			Intent startIntent = new Intent(this, DownloadService.class);
			startService(startIntent);
			break;

		case R.id.stopServiceBt:
			Intent stopIntent = new Intent(this, DownloadService.class);
			stopService(stopIntent);
			break;

		case R.id.bindServiceBt:

			break;

		case R.id.unBindServiceBt:

			break;

		default:
			break;
		}
	}

}
