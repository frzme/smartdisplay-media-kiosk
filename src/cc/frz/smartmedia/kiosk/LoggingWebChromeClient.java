package cc.frz.smartmedia.kiosk;

import android.util.Log;
import android.webkit.WebChromeClient;

public class LoggingWebChromeClient extends WebChromeClient
{
	public void onConsoleMessage(final String message, final int lineNumber, final String sourceID)
	{
		Log.d("SmartMediaKiosk", message + " -- From line " + lineNumber + " of " + sourceID);
	}
}
