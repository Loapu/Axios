package software.axios.paper.api.implementation;

import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.AxiosApiPlugin;
import software.axios.api.i18n.I18nManager;
import software.axios.api.i18n.MessagesInterface;
import software.axios.paper.AxiosPlugin;

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
	private static UTF8ResourceBundleControl axiosUTF8ResourceBundleControl;
	public static PaperI18nManager getInstance()
	{
		if (instance == null)
		{
			instance = new PaperI18nManager();
		}
		return instance;
	}
	public static UTF8ResourceBundleControl getAxiosUTF8ResourceBundleControl()
	{
		if (axiosUTF8ResourceBundleControl == null)
		{
			axiosUTF8ResourceBundleControl = new UTF8ResourceBundleControl();
		}
		return axiosUTF8ResourceBundleControl;
	}
	
	private PaperI18nManager()
	{
	}
	
	@Override
	public void setup(AxiosApiPlugin plugin, Class<? extends MessagesInterface> messageClazz, Locale... additionalLocales)
	{
		String resourcePathFirst = bundleName + File.separator + "default" + File.separator + "default";
		String resourcePathLast = ".properties";
		plugin.saveResources(resourcePathFirst + resourcePathLast, true);
		for (Locale locale : additionalLocales)
		{
			String resourcePath = resourcePathFirst + "_" + locale + resourcePathLast;
			plugin.saveResources(resourcePath, true);
		}
		File i18nFolder = new File(plugin.pluginFolder(), bundleName);
		File defaultFolder = new File(i18nFolder, "default");
		if (!defaultFolder.exists())
		{
			defaultFolder.mkdirs();
		}
		File customFolder = new File(i18nFolder, "custom");
		if (!customFolder.exists())
		{
			customFolder.mkdirs();
		}
		
		try
		{
			URL[] urls = {
				customFolder.getAbsoluteFile().toURI().toURL(),
				defaultFolder.getAbsoluteFile().toURI().toURL()
			};
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
			ResourceBundle resourceBundle = null;
			try
			{
				resourceBundle = ResourceBundle.getBundle("custom", locale, i18nLoaders.get(messageClazz), getAxiosUTF8ResourceBundleControl());
			}
			catch (Exception e)
			{
				AxiosPlugin.instance().debug("No custom translation files found. Using default.");
			}
			if (resourceBundle == null || !resourceBundle.containsKey(key))
			{
				try
				{
					resourceBundle = ResourceBundle.getBundle("default", locale, i18nLoaders.get(messageClazz), getAxiosUTF8ResourceBundleControl());
				}
				catch (Exception e)
				{
					AxiosPlugin.instance().debug("No default translation files found. Using key.");
				}
			}
			if (resourceBundle == null || !resourceBundle.containsKey(key)) return key;
			return resourceBundle.getString(key).replaceAll("\\n", "<newline>");
		}
		return key;
	}
}
