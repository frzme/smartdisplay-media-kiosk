package cc.frz.smartmedia.kiosk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;

public class SmartdisplayMediaKioskActivity extends Activity
{
	private WebView webView;
	public static final String PREFS_NAME = "SmartDisplayPrefs";
	private String homeUrl;
	private SharedPreferences settings;
	private DiplayController controller;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		settings = getSharedPreferences(PREFS_NAME, 0);
		homeUrl = settings.getString("homeUrl", "http://cc:50116/");

		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		final WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
				| PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "SmartdisplayMediaKioskActivity");

		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(homeUrl);
		webView.setWebViewClient(new HelloWebViewClient());
		controller = new DiplayController(getContentResolver(), wakeLock);
		controller.turnDisplayOff();
		webView.addJavascriptInterface(controller, "display");
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu)
	{
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	private String promptForNewUrl()
	{
		final EditText input = new EditText(this);
		new AlertDialog.Builder(SmartdisplayMediaKioskActivity.this).setTitle("New Url")
				.setMessage("Enter new Home URL").setView(input)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int whichButton)
					{
						final Editable value = input.getText();
						homeUrl = value.toString();
						savePreferences();
					}
				}).show();
		return homeUrl;
	}

	@Override
	protected void onDestroy()
	{
		savePreferences();
		controller.restoreDisplayTimeout();
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.seturl:
				webView.loadUrl(promptForNewUrl());
				return true;
			case R.id.home:
				webView.loadUrl(homeUrl);
				return true;
			case R.id.exit:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void savePreferences()
	{
		settings.edit().putString(homeUrl, homeUrl).commit();
	}
}