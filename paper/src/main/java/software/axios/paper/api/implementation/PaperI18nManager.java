package software.axios.paper.api.implementation;

import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.i18n.I18nManager;
import software.axios.api.i18n.MessagesInterface;
import software.axios.paper.util.AxiosResourceBundleControl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class PaperI18nManager implements I18nManager
{
	private static final String bundleName = "i18n";
	private final Map<Class<? extends MessagesInterface>, ClassLoader> i18nLoaders = new HashMap<>();
	private static PaperI18nManager instance;
	public static PaperI18nManager getInstance()
	{
		if (instance == null)
		{
			instance = new PaperI18nManager();
		}
		return instance;
	}
	
	private PaperI18nManager()
	{
	}
	
	@Override
	public void setup(Plugin plugin, Class<? extends MessagesInterface> messageClazz, Locale... additionalLocales)
	{
		String resourcePathFirst = bundleName + File.separator + bundleName;
		String resourcePathLast = ".properties";
		plugin.saveResource(resourcePathFirst + resourcePathLast, true);
		for (Locale locale : additionalLocales)
		{
			String resourcePath = resourcePathFirst + "_" + locale + resourcePathLast;
			plugin.saveResource(resourcePath, true);
		}
		File i18nFolder = new File(plugin.getDataFolder(), bundleName);
		
		try
		{
			URL[] urls = {i18nFolder.getAbsoluteFile().toURI().toURL()};
			i18nLoaders.put(messageClazz, new URLClassLoader(urls));
		}
		catch (MalformedURLException e)
		{
			e.getStackTrace();
		}
	}
	
	@Override
	@NonNull
	public String get(Class<? extends MessagesInterface> messageClazz, Locale locale, String key)
	{
		if (i18nLoaders.containsKey(messageClazz))
		{
			ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale, i18nLoaders.get(messageClazz), new AxiosResourceBundleControl());
			return resourceBundle.containsKey(key) ? resourceBundle.getString(key).replaceAll("\\n", "<newline>") : key;
		}
		return key;
	}
}
