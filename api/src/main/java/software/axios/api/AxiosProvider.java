package software.axios.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import static  org.jetbrains.annotations.ApiStatus.Internal;

/**
 * Provides static access to the {@link Axios} API.
 *
 * <p>Ideally, the ServiceManager for the platform should be used to obtain an
 * instance, however, this provider can be used if this is not viable.</p>
 * @since 1.1
 */
public final class AxiosProvider
{
	private static Axios instance = null;
	
	/**
	 * Gets an instance of the {@link Axios} API,
	 * throwing {@link IllegalStateException} if Axios has not been initialized yet.
	 *
	 * <p>This method will never return null.</p>
	 *
	 * @return an instance of the {@link Axios} API
	 * @throws IllegalStateException if Axios has not been initialized yet
	 * @since 1.1
	 */
	public static @NonNull Axios get()
	{
		Axios instance = AxiosProvider.instance;
		if (instance == null)
		{
			throw new IllegalStateException("Axios has not been initialized yet!");
		}
		return instance;
	}
	
	@Internal
	static void register(Axios instance)
	{
		AxiosProvider.instance = instance;
	}
	
	@Internal
	static void unregister()
	{
		AxiosProvider.instance = null;
	}
	
	@Internal
	private AxiosProvider()
	{
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}
}
