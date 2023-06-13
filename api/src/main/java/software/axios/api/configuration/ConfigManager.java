package software.axios.api.configuration;

import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.AxiosApiPlugin;

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
	<R extends SettingsInterface> void setup(AxiosApiPlugin plugin, Class<R> settingsClazz);
	
	/**
	 * Reload your configuration files <br>
	 * <strong>IMPORTANT:</strong> Use this method only after you have called {@link #setup(AxiosApiPlugin, Class)}
	 * or your settings will not be saved.
	 *
	 * @param <R> the settings class to use
	 * @param plugin the plugin to use
	 * @param settingsClazz the settings class to use
	 *
	 * @since 1.0
	 */
	<R extends SettingsInterface> void reload(AxiosApiPlugin plugin, Class<R> settingsClazz);
	
	
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
