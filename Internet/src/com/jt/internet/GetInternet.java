package com.jt.internet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GetInternet extends Activity {

	TextView mTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.get_internet);
		mTextView = (TextView) findViewById(R.id.text);
		mTextView.setText("Start Loading!");

		new GetAsyncTask().execute();
	}

	class GetAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub

			String path = "http://10.1.1.55:8080/ThemeServer/ServletTheme";
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", "jt");
			map.put("age", "22");

			return sendRequest(map, path, "UTF-8");
		}

		boolean sendRequest(Map<String, String> map, String path,
				String encoding) {
			StringBuilder builder = new StringBuilder();
			builder.append(path);

			boolean result = false;
			
			if (map != null && !map.isEmpty()) {
				builder.append("?");
				for (Map.Entry<String, String> entry : map.entrySet()) {
					builder.append(entry.getKey()).append("=");
					try {
						builder.append(URLEncoder.encode(entry.getValue(),
								encoding));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					builder.append("&");
				}
				builder.deleteCharAt(builder.length() - 1); // delete last &
				Log.e("JT", "url: " + builder.toString());
			}

			HttpURLConnection conn = null;
			try {
				URL url = new URL(builder.toString());
				conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(5000);
				conn.setRequestMethod("GET");
				if (conn.getResponseCode() == 200) {
					result = true;
					Log.e("JT", "return true");
					return true;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Log.e("JT", "finally result: " + result);
				if (conn != null) {
					conn.disconnect();
				}
			}
			Log.e("JT", "return result: " + result); 
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			
			if (result) {
				mTextView.setText("Success!");
			} else {
				mTextView.setText("Failed!");
			}
			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			mTextView.setText("Start Loading!");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			mTextView.setText("Loading...");
		}
		
		

	}

}
