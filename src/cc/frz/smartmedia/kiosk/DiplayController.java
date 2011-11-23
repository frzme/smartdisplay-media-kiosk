package cc.frz.smartmedia.kiosk;

import android.content.ContentResolver;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;

public class DiplayController
{
	private final WakeLock wakeLock;
	private final int defTimeOut;
	private final ContentResolver contentResolver;
	private static final int DELAY = 3000;

	public DiplayController(final ContentResolver contentResolver, final WakeLock wakeLock)
	{
		this.wakeLock = wakeLock;
		this.contentResolver = contentResolver;
		defTimeOut = Settings.System.getInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, DELAY);
	}

	public void keepDisplayAlive()
	{
		restoreDisplayTimeout();
		if (!wakeLock.isHeld())
		{
			wakeLock.acquire();
		}
	}

	public void turnDisplayOff()
	{
		if (wakeLock.isHeld())
		{
			wakeLock.release();
		}
		Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 5000);
	}

	public void restoreDisplayTimeout()
	{
		if (wakeLock.isHeld())
		{
			wakeLock.release();
		}
		Settings.System.putInt(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, defTimeOut);
	}
}
