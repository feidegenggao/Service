package com.lxf.service;

import java.util.HashMap;
import java.util.Map;

import com.lxf.util.LogUtil;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.ProgressBar;

public class DownloadService extends Service {
	private static final String TAG = "TestService";

	private Map idView;
	private Map idAsynTask;

	class DownloadBinder extends Binder {

		public DownloadBinder() {
			Log.d(TAG, "create Downloadbinder");
		}

		public void startDownload(int id, ProgressBar showPBTemp) {
			Log.d(TAG, "startDownload");
			idView.remove(id);
			idView.put(id, showPBTemp);

			if (idAsynTask.containsKey(id)) {

			} else {
				DownloadAsynTask temp = new DownloadAsynTask();
				idAsynTask.put(id, temp);
				temp.execute(id);
			}
		}
	}

	private DownloadBinder mDownloadBinder = new DownloadBinder();

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(TAG, "onBind");
		return mDownloadBinder;
	}

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
		idView = new HashMap<Integer, ProgressBar>();
		idAsynTask = new HashMap<Integer, DownloadAsynTask>();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand, :flags:" + flags + "|startId:" + startId);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	class DownloadAsynTask extends AsyncTask<Integer, Integer, Boolean> {
		private int id;

		@Override
		protected Boolean doInBackground(Integer... arg0) {
			assert (arg0.length == 1);
			id = arg0[0];
			int time = 0;
			while (time++ < 100) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.d(TAG, "current time:" + time);
				publishProgress(time);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			Log.d(TAG, "onPostExecute");
			super.onPostExecute(result);
			idView.remove(id);
			idAsynTask.remove(id);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			assert (values.length == 1);
			int value = values[0];
			LogUtil.d(TAG, "onProgressUpdate's values" + value);
			ProgressBar showPB = (ProgressBar) idView.get(id);
			showPB.setProgress(value);
		}

	}

}
