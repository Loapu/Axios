package software.axios.api.configuration;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * This interface is used to represent the final settings-class.
 *
 * @since 1.0
 */
public interface SettingsInterface
{
	/**
	 * This method is used to get a String representation of the setting's path.
	 *
	 * @return The path of the setting.
	 * @since 1.0
	 */
	@NonNull String path();
	
	/**
	 * This method is used to get the comments of the setting.
	 *
	 * @return The comments of the setting.
	 * @since 1.0
	 */
	@NonNull List<String> comments();
	
	/**
	 * This method is used to get the value of the setting.
	 *
	 * @param <T> The type of the setting.
	 * @return The value of the setting.
	 * @since 1.0
	 */
	@NonNull <T> T get();
	
	/**
	 * This method is used to get the default value of the setting.
	 *
	 * @param <T> The type of the setting.
	 * @since 1.0
	 *
	 * @return The default value of the setting.
	 */
	@NonNull <T> T defaultValue();
	
	/**
	 * This method is used to get the type of the setting.
	 *
	 * @param <T> The type of the setting.
	 * @since 1.0
	 *
	 * @return The type of the setting.
	 */
	@NonNull <T> Class<T> type();
}
