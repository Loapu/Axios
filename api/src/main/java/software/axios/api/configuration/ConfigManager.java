package software.axios.api.configuration;

import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Locale;

/**
 * Manages the configuration files for the plugin
 *
 * @since 1.0
 */
public interface ConfigManager
{
	/**
	 * Set up the config manager
	 *
	 * @param <R> the settings class to use
	 * @param plugin the plugin to use
	 * @param settingsClazz the settings class to use
	 *
	 * @since 1.0
	 */
	<R extends SettingsInterface> void setup(Plugin plugin, Class<R> settingsClazz);
	
	/**
	 * Reload your configuration files <br>
	 * <strong>IMPORTANT:</strong> Use this method only after you have called {@link #setup(Plugin, Class)}
	 * or your settings will not be saved.
	 *
	 * @param <R> the settings class to use
	 * @param plugin the plugin to use
	 * @param settingsClazz the settings class to use
	 *
	 * @since 1.0
	 */
	<R extends SettingsInterface> void reload(Plugin plugin, Class<R> settingsClazz);
	
	
	/**
	 * Get a setting for a given settings class
	 *
	 * @param <T> the type of the specific setting.
	 * @param <R> your used settings class implementing {@link SettingsInterface}.
	 * @param settingsClazz the settings class to use
	 * @param setting the setting to get
	 *
	 * @return a setting for a given settings class
	 *
	 * @since 1.0
	 */
	@NonNull <T, R extends SettingsInterface> T get(Class<R> settingsClazz, R setting);
	
	
	/**
	 * Get the default locale for the plugin
	 *
	 * @return the default locale for the plugin
	 *
	 * @since 1.0
	 */
	@NonNull Locale defaultLocale();
}
