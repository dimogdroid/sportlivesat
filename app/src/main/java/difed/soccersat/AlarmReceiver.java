package difed.soccersat;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import difed.entidades.Partidos;


public class AlarmReceiver extends BroadcastReceiver {

	// MediaPlayer m=MediaPlayer.create(AlarmReceiver.this, R.raw.audio);

	Partidos partido = new Partidos();

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {

		Notification mNotification;

		partido = intent.getParcelableExtra("recordar");

		// define sound URI, the sound to be played when there's a notification
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// intent triggered, you can add other intent for other actions
		Intent inten = new Intent(context, MainActivity.class);
		inten.putExtra("recordar", partido);

		PendingIntent pIntent = PendingIntent.getActivity(context, 0, inten,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// this is it, we'll build the notification!
		// in the addAction method, if you don't want any icon, just set the
		// first param to 0

		mNotification = new Notification.Builder(context)
				.setContentTitle("Sport Live Sat")
				.setContentText(context.getString(R.string.frasenotifica))
				.setSmallIcon(R.drawable.ic_notifica4).setContentIntent(pIntent)
				.setSound(soundUri)

				.build();

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// If you want to hide the notification after it was selected, do the
		// code below
		mNotification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify((int) System.currentTimeMillis(),
				mNotification);

	}

}
