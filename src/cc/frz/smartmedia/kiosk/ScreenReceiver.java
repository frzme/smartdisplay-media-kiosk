package cc.frz.smartmedia.kiosk;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver
{

	private final SmartdisplayMediaKioskActivity smartdisplayMediaKioskActivity;

	public ScreenReceiver(final SmartdisplayMediaKioskActivity smartdisplayMediaKioskActivity)
	{
		this.smartdisplayMediaKioskActivity = smartdisplayMediaKioskActivity;
	}

	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
		{
			final KeyguardManager keyguardManager = (KeyguardManager) smartdisplayMediaKioskActivity
					.getSystemService(Context.KEYGUARD_SERVICE);
			final KeyguardLock lock = keyguardManager.newKeyguardLock(Context.KEYGUARD_SERVICE);
			lock.disableKeyguard();
		}
	}

}