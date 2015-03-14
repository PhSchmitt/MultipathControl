package be.uclouvain.multipathcontrol;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MainService extends Service {

	private MPCtrl mpctrl;

	@Override
	public IBinder onBind(Intent intent) {
		return null; // not allow binding
	}

	public void onCreate() {
		super.onCreate();
		mpctrl = Manager.create(this);
		Log.i(Manager.TAG, "Create service");
		if (mpctrl == null) {
			Toast.makeText(this,
					"MPControl: It seems this is not a rooted device",
					Toast.LENGTH_LONG).show();
			stopSelf();
			return;
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (mpctrl != null) {
			Manager.destroy(this);
			Log.i(Manager.TAG, "Destroy service");
		}
	}
}
