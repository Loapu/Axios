package software.axios.paper.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.Axios;
import software.axios.api.configuration.AxiosSettings;
import software.axios.api.configuration.ConfigManager;
import software.axios.api.configuration.SettingsInterface;
import software.axios.api.i18n.AxiosMessages;
import software.axios.api.i18n.I18nManager;
import software.axios.api.i18n.MessagesInterface;
import software.axios.api.i18n.TagBuilder;
import software.axios.api.platform.AxiosEntity;
import software.axios.paper.AxiosPlugin;
import software.axios.paper.api.implementation.PaperAxiosEntity;
import software.axios.paper.api.implementation.PaperAxiosMessages;
import software.axios.paper.api.implementation.PaperAxiosSettings;
import software.axios.paper.api.implementation.PaperConfigManager;
import software.axios.paper.api.implementation.PaperI18nManager;
import software.axios.paper.api.implementation.PaperTagBuilder;

import java.util.Locale;

public class AxiosApiProvider implements Axios
{
	private final AxiosPlugin plugin;
	
	public AxiosApiProvider(AxiosPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public @NonNull TagBuilder tagBuilder()
	{
		return new PaperTagBuilder();
	}
	
	@Override
	public @NonNull AxiosMessages axiosMessages(Class<? extends MessagesInterface> callingClazz, String path)
	{
		return new PaperAxiosMessages(callingClazz, path);
	}
	
	@Override
	public @NonNull I18nManager i18nManager()
	{
		return PaperI18nManager.getInstance();
	}
	
	@Override
	public @NonNull <T, R extends SettingsInterface> AxiosSettings<T, R> axiosSettings(Class<R> settingsClazz, String path, Class<T> type, T defaultValue)
	{
		return new PaperAxiosSettings<>(settingsClazz, path, type, defaultValue);
	}
	
	@Override
	public @NonNull ConfigManager configManager()
	{
		return PaperConfigManager.getInstance();
	}
	
	@Override
	public @NonNull Locale defaultLocale()
	{
		return configManager().defaultLocale();
	}
	
	@Override
	public @NonNull AxiosEntity entity(Object entity)
	{
		return new PaperAxiosEntity(entity);
	}
}
