package software.axios.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.configuration.AxiosSettings;
import software.axios.api.configuration.ConfigManager;
import software.axios.api.configuration.SettingsInterface;
import software.axios.api.i18n.AxiosMessages;
import software.axios.api.i18n.I18nManager;
import software.axios.api.i18n.MessagesInterface;
import software.axios.api.i18n.TagBuilder;
import software.axios.api.platform.AxiosEntity;

import java.util.Locale;

/**
 * The Axios API.
 *
 * <p>The API allows other plugins to use the central {@link ConfigManager},
 * {@link I18nManager}, {@link software.axios.api.command.CommandsInterface}
 * as well as different utility classes.</p>
 *
 * @since 1.0
 */
public interface Axios
{
	/**
	 * Gets the {@link TagBuilder} instance.
	 *
	 * <p>The TagBuilder provides an easy way to build different kind
	 * of placeholder tags for MiniMessage.</p>
	 *
	 * @return the {@link TagBuilder} instance
	 * @since 1.0
	 */
	@NonNull TagBuilder tagBuilder();
	
	/**
	 * Gets the {@link AxiosMessages} instance.
	 *
	 * <p>This interface is needed in order to allow any child of {@link MessagesInterface}
	 * to use the implementation of Axios' {@link I18nManager}.</p>
	 *
	 * @param callingClazz the calling class
	 * @param path         the path to the message
	 * @return the {@link AxiosMessages} instance
	 * @since 1.0
	 */
	@NonNull AxiosMessages axiosMessages(Class<? extends MessagesInterface> callingClazz, String path);
	
	/**
	 * Gets the {@link I18nManager} instance.
	 *
	 * <p>Provides some useful methods to manage messages via {@link java.util.ResourceBundle}s.</p>
	 *
	 * @return the {@link I18nManager} instance
	 * @since 1.0
	 */
	@NonNull I18nManager i18nManager();
	
	/**
	 * Gets the {@link AxiosSettings} instance.
	 *
	 * <p>This interface is needed in order to allow any child of {@link SettingsInterface}
	 * to use the implementation of Axios' {@link ConfigManager}.</p>
	 *
	 * @param <T>           the type of the setting
	 * @param <R>           your used settings class implementing {@link SettingsInterface}
	 * @param settingsClazz the {@link SettingsInterface} instance
	 * @param path          the path to the setting
	 * @param type          the same as T
	 * @param defaultValue  the default value of the setting
	 *
	 * @return the {@link AxiosSettings} instance
	 * @since 1.0
	 */
	@NonNull <T, R extends SettingsInterface> AxiosSettings<T, R> axiosSettings(Class<R> settingsClazz, String path, Class<T> type, T defaultValue);
	
	/**
	 * Gets the {@link ConfigManager} instance.
	 *
	 * <p>Provides some useful methods to manage settings via YamlConfigurations.</p>
	 *
	 * @return the {@link ConfigManager} instance
	 * @since 1.0
	 */
	@NonNull ConfigManager configManager();
	
	/**
	 * Gets the default {@link Locale} of Axios.
	 *
	 * @return the default {@link Locale}
	 * @since 1.0
	 */
	@NonNull Locale defaultLocale();
	
	/**
	 * Gets a new {@link AxiosEntity} instance.
	 *
	 * @param entity the entity to be wrapped
	 * @return the {@link AxiosEntity} instance
	 * @since 1.0
	 */
	@NonNull AxiosEntity entity(Object entity);
}
