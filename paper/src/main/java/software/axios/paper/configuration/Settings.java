package software.axios.paper.configuration;

import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.Axios;
import software.axios.api.configuration.AxiosSettings;
import software.axios.api.configuration.SettingsField;
import software.axios.api.configuration.SettingsInterface;
import software.axios.paper.AxiosPlugin;

import java.util.List;

@SuppressWarnings("unchecked")
public class Settings<T> implements SettingsInterface
{
	@SettingsField
	public static final Settings<String> LANGUAGE = new Settings<>("general.language", String.class, "de_DE");
	@SettingsField
	public static final Settings<Boolean> DEBUG = new Settings<>("general.debug", Boolean.class, false);
	
	private final AxiosPlugin plugin = AxiosPlugin.instance();
	private final Axios axios = plugin.axiosApiProvider();
	private final AxiosSettings<T, Settings<T>> axiosSettings;
	
	private Settings(String path, Class<T> type, T defaultValue)
	{
		this.axiosSettings = (AxiosSettings<T, Settings<T>>) axios.axiosSettings(this.getClass(), path, type, defaultValue);
	}
	
	@Override
	public @NonNull String path()
	{
		return axiosSettings.path();
	}
	
	@Override
	public @NonNull List<String> comments()
	{
		return axiosSettings.comments();
	}
	
	@Override
	public @NonNull T get()
	{
		return axiosSettings.get(this);
	}
	
	@Override
	public @NonNull T defaultValue()
	{
		return axiosSettings.defaultValue();
	}
	
	@Override
	public @NonNull Class<T> type()
	{
		return axiosSettings.type();
	}
}
