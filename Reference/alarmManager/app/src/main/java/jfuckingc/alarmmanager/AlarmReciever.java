package jfuckingc.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by cyriljeanneret on 01.11.16.
 */

public class AlarmReciever extends BroadcastReceiver
{

    private static Ringtone r;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Alarm Triggered ", Toast.LENGTH_LONG).show();
        
    }

    public static void stopAlarm(Context context)
    {
        // TODO Auto-generated method stub

        try {
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Alarm stop", Toast.LENGTH_LONG).show();

    }

}

