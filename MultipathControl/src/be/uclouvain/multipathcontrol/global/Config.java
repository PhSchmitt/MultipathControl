/*
 * This file is part of MultipathControl.
 *
 * Copyright 2012 UCLouvain - Gregory Detal <first.last@uclouvain.be>
 * Copyright 2015 UCLouvain - Matthieu Baerts <first.last@student.uclouvain.be>
 *
 * MultipathControl is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package be.uclouvain.multipathcontrol.global;

import android.content.Context;
import android.content.SharedPreferences;
import be.uclouvain.multipathcontrol.system.Sysctl;

public class Config {

	public static final String PREFS_NAME           = "MultipathControl";
	public static final String PREFS_STATUS         = "enableMultiInterfaces";
	public static final String PREFS_DEFAULT_DATA   = "defaultData";
	public static final String PREFS_DATA_BACKUP    = "dataBackup";
	public static final String PREFS_SAVE_BATTERY   = "saveBattery";
	public static final String PREFS_IPV6           = "ipv6";
	public static final String PREFS_SAVE_POWER_GPS = "savePowerGPS";
	public static final String PREFS_TCPCC          = "tcpcc";
    public static final String PREFS_SCHEDULER      = "scheduler";

	public static boolean mEnabled;
	public static boolean defaultRouteData;
	public static boolean dataBackup;
	public static boolean saveBattery;
	public static boolean ipv6;
	public static boolean savePowerGPS;
	public static String tcpcc;
	public static String scheduler;

	public static int mobileDataActiveTime = 5000;

	private Config() {
	}

	public static void getDefaultConfig(Context context) {
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		mEnabled = settings.getBoolean(PREFS_STATUS, true);
		defaultRouteData = settings.getBoolean(PREFS_DEFAULT_DATA, false);
		dataBackup = settings.getBoolean(PREFS_DATA_BACKUP, false);
		saveBattery = settings.getBoolean(PREFS_SAVE_BATTERY, true);
		savePowerGPS = settings.getBoolean(PREFS_SAVE_POWER_GPS, true);

		// Dynamic
		ipv6 = settings.getBoolean(PREFS_IPV6, false);
		if (ipv6 != Sysctl.getIPv6())
			Sysctl.setIPv6(ipv6);

		tcpcc = settings.getString(PREFS_TCPCC, "lia");
		if (!tcpcc.equals(Sysctl.getCC()))
			Sysctl.setCC(tcpcc);

        scheduler = settings.getString(PREFS_SCHEDULER, "default");
        if (!scheduler.equals(Sysctl.getScheduler()))
            Sysctl.setScheduler(scheduler);
	}

	public static void updateDynamicConfig() {
		ipv6 = Sysctl.getIPv6();
		tcpcc = Sysctl.getCC();
	}

	public static void saveStatus(Context context) {

		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(PREFS_STATUS, mEnabled);
		editor.putBoolean(PREFS_DEFAULT_DATA, defaultRouteData);
		editor.putBoolean(PREFS_DATA_BACKUP, dataBackup);
		editor.putBoolean(PREFS_SAVE_BATTERY, saveBattery);
		editor.putBoolean(PREFS_IPV6, ipv6);
		editor.putBoolean(PREFS_SAVE_POWER_GPS, savePowerGPS);
		editor.putString(PREFS_TCPCC, tcpcc);
		editor.apply();
	}
}
