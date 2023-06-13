package software.axios.api;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;

/**
 * Represents a plugin that uses the Axios API.
 *
 * @since 1.1
 */
public interface AxiosApiPlugin
{
	/**
	 * Gets the folder where plugin data is stored.
	 *
	 * @return the folder
	 * @since 1.1
	 */
	@NonNull File pluginFolder();
	
	/**
	 * Saves the resource at the given path to the given path in the plugin folder.
	 *
	 * @param path the path to save the resource to
	 * @param replace whether to replace the file if it already exists
	 * @since 1.1
	 */
	void saveResources(String path, boolean replace);
}
