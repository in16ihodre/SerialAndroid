package com.example.serialandroid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity {

	private UsbSerialDriver usb;

	protected String strings = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
		usb = UsbSerialProber.acquire(manager);
		if (usb != null) {
			try{
				usb.open();
				usb.setBaudRate(9600);
				//start_read_thread();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}

		Button Button1 = (Button) findViewById(R.id.button1);
		Button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					usb.write("0".getBytes("UTF-8"),1);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Button Button2 = (Button) findViewById(R.id.button2);
		Button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					usb.write("1".getBytes("UTF-8"),1);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


	}

	private void start_read_thread() {
		new Thread(new Runnable(){
			public void run(){
				try{
					while(true){
						usb.write(strings.getBytes("UTF-8"),1);
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
