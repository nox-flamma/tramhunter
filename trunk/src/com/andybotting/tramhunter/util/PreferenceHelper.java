package com.andybotting.tramhunter.util;

import com.andybotting.tramhunter.activity.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
	
	private final SharedPreferences preferences;

	public PreferenceHelper(final Context context) {
		this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public boolean isWelcomeQuoteEnabled()
	{
		return preferences.getBoolean(SettingsActivity.KEY_WELCOME_MESSAGE, SettingsActivity.KEY_WELCOME_MESSAGE_DEFAULT_VALUE);
	}
	
	public String defaultLaunchActivity()
	{
		return preferences.getString(SettingsActivity.KEY_DEFAULT_LAUNCH_ACTIVITY, "HomeActivity");
	}
	
	public boolean isTramImageEnabled()
	{
		return preferences.getBoolean(SettingsActivity.KEY_TRAM_IMAGE, SettingsActivity.KEY_TRAM_IMAGE_DEFAULT_VALUE);
	}	
	
	public boolean isSendStatsEnabled()
	{
		return preferences.getBoolean(SettingsActivity.KEY_SEND_STATS, SettingsActivity.KEY_SEND_STATS_DEFAULT_VALUE);
	}
	
}
