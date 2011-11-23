package cc.frz.smartmedia.kiosk;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UrlLoadingWebViewClient extends WebViewClient
{
	@Override
	public boolean shouldOverrideUrlLoading(final WebView view, final String url)
	{
		view.loadUrl(url);
		return true;
	}
}