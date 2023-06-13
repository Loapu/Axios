package software.axios.paper.api.implementation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.configuration.ConfigManager;
import software.axios.api.configuration.SettingsField;
import software.axios.api.configuration.SettingsInterface;
import software.axios.api.AxiosApiPlugin;
import software.axios.paper.AxiosPlugin;
import software.axios.paper.configuration.Settings;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PaperConfigManager implements ConfigManager
{
	private final Map<Class<? extends SettingsInterface>, FileConfiguration> configMap = new HashMap<>();
	private final String configFileName = "config.yml";
	
	private static PaperConfigManager instance;
	public static PaperConfigManager getInstance()
	{
		if (instance == null)
		{
			instance = new PaperConfigManager();
		}
		
		return instance;
	}
	private PaperConfigManager()
	{
	}
	
	@Override
	public <R extends SettingsInterface> void setup(AxiosApiPlugin plugin, Class<R> settingsClazz)
	{
		reload(plugin, settingsClazz);
		AxiosPlugin.instance().debug("Setting up config manager for " + settingsClazz.getName());
		AxiosPlugin.instance().debug("The following settings are available:");
		for (SettingsInterface setting : getSettings(settingsClazz))
		{
			AxiosPlugin.instance().debug(setting.path());
		}
		saveToDisk(plugin, settingsClazz);
	}
	
	@Override
	public <R extends SettingsInterface> void reload(AxiosApiPlugin plugin, Class<R> settingsClazz)
	{
		FileConfiguration config = new YamlConfiguration();
		File configFile = new File(plugin.pluginFolder(), configFileName);
		
		if (configFile.exists()) config = YamlConfiguration.loadConfiguration(configFile);
		
		configMap.put(settingsClazz, config);
		
		loadDefaults(settingsClazz, config);
	}
	
	private <R extends SettingsInterface> void loadDefaults(Class<R> settingsClazz, FileConfiguration config)
	{
		FileConfiguration tempConfig = new YamlConfiguration();
		for (SettingsInterface setting : getSettings(settingsClazz))
		{
			String key = setting.path().toLowerCase();
			if (config.contains(key)) tempConfig.set(key, config.get(key));
			else tempConfig.set(key, setting.defaultValue());
			tempConfig.addDefault(key, setting.defaultValue());
			tempConfig.setComments(key, setting.comments());
		}
		configMap.put(settingsClazz, tempConfig);
	}
	
	private <R extends SettingsInterface> void saveToDisk(AxiosApiPlugin plugin, Class<R> settingsClazz)
	{
		FileConfiguration config = configMap.get(settingsClazz);
		File configFile = new File(plugin.pluginFolder(), configFileName);
		try
		{
			config.save(configFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static @NonNull <R extends SettingsInterface> List<SettingsInterface> getSettings(Class<R> settingsClazz)
	{
		List<SettingsInterface> settingsList = new ArrayList<>();
		Field[] fields = settingsClazz.getDeclaredFields();
		for (Field field : fields)
		{
			if (!field.getType().equals(settingsClazz)) continue;
			if (!field.isAnnotationPresent(SettingsField.class))
			{
				AxiosPlugin.instance().getLogger().warning("Field " + field.getName() + " is not annotated with @SettingsField");
				continue;
			}
			try
			{
				
				settingsList.add((SettingsInterface) field.get(null));
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return settingsList;
	}
	
	@Override
	public @NonNull <T, R extends SettingsInterface> T get(Class<R> settingsClazz, R setting)
	{
		Class<T> type = setting.type();
		FileConfiguration config = configMap.get(settingsClazz);
		if (config == null)
		{
			AxiosPlugin.instance().getLogger().warning("No config found for class: " + settingsClazz.getName());
			return setting.defaultValue();
		}
		String key = setting.path();
		if (!config.contains(key))
		{
			AxiosPlugin.instance().getLogger().warning("No value at given path: " + key);
			return setting.defaultValue();
		}
		Object value = config.get(key);
		if (type.isInstance(value)) return type.cast(value);
		AxiosPlugin.instance().getLogger().warning("Type mismatch in Config. Expected type: " + type.getSimpleName());
		return setting.defaultValue();
	}
	
	@Override
	public @NonNull Locale defaultLocale()
	{
		FileConfiguration tempConfig = configMap.get(Settings.class);
		String languageString = tempConfig.getString(Settings.LANGUAGE.path(), Settings.LANGUAGE.defaultValue());
		String[] language = languageString.split("_");
		assert language.length >= 1;
		Locale.Builder builder = new Locale.Builder();
		builder.setLanguage(language[0]);
		if (language.length >= 2) builder.setRegion(language[1]);
		builder.setVariant("custom");
		
		return builder.build();
	}
}
