package software.axios.api.i18n;

import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.AxiosApiPlugin;

import java.util.Locale;

/**
 * Manages the loading and retrieval of messages.
 *
 * @since 1.0
 */
public interface I18nManager
{
	/**
	 * Set up the i18n manager
	 *
	 * @param plugin the plugin to use
	 * @param messageClazz the message class to use
	 * @param additionalLocales additional locales to load
	 *
	 * @since 1.0
	 */
	void setup(AxiosApiPlugin plugin, Class<? extends MessagesInterface> messageClazz, Locale... additionalLocales);
	
	/**
	 * Get a message from the manager.
	 *
	 * @param messageClazz the message class to use
	 * @param locale the locale to use
	 * @param key the key to use
	 * @return the message
	 *
	 * @since 1.0
	 */
	@NonNull String get(Class<? extends MessagesInterface> messageClazz, Locale locale, String key);
}
