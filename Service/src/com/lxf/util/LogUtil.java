package com.lxf.util;

import android.util.Log;

public final class LogUtil {
	private static final String TAG = "LogUtil";

	public static void d(String msg) {
		d(TAG, msg);
	}

	public static void d(String TAG, String msg) {
		Log.d(TAG, msg);
	}
}
