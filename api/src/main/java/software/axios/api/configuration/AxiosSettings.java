package software.axios.api.configuration;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Interface for settings.
 *
 * @author Loapu
 * @since 1.0
 * @param <T> the type of the specific setting.
 * @param <R> your used settings class implementing {@link SettingsInterface}.
 */
public interface AxiosSettings<T, R extends SettingsInterface>
{
	/**
	 * The path of the setting.
	 *
	 * @since 1.0
	 * @return the path of the setting.
	 */
	@NonNull String path();
	
	/**
	 * Returns all comments for this specific setting.
	 *
	 * @since 1.0
	 * @return the comments of the setting.
	 */
	@NonNull List<String> comments();
	
	/**
	 * Returns the value of the setting.
	 *
	 * @since 1.0
	 * @param setting the setting to get the value from.
	 * @return the value of the setting.
	 */
	@NonNull T get(R setting);
	
	/**
	 * Returns the default value of the setting.
	 *
	 * @since 1.0
	 * @return the default value of the setting.
	 */
	@NonNull T defaultValue();
	
	/**
	 * Returns the type of the setting.
	 *
	 * @since 1.0
	 * @return the type of the setting.
	 */
	@NonNull Class<T> type();
}
