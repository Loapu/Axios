package software.axios.paper.api.implementation;

import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.configuration.AxiosSettings;
import software.axios.api.configuration.SettingsInterface;
import software.axios.paper.AxiosPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PaperAxiosSettings<T, R extends SettingsInterface> implements AxiosSettings<T, R>
{
	private final Class<R> callingClazz;
	private final String path;
	private final Class<T> type;
	private final T defaultValue;
	public PaperAxiosSettings(Class<R> callingClazz, String path, Class<T> type, T defaultValue)
	{
		this.callingClazz = callingClazz;
		this.path = path;
		this.type = type;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public @NonNull String path()
	{
		return path;
	}
	
	@Override
	public @NonNull List<String> comments()
	{
		String baseName = "comments.comments";
		ResourceBundle commentsBundle = ResourceBundle.getBundle(
			baseName,
			PaperConfigManager.getInstance().defaultLocale(),
			callingClazz.getClassLoader(),
			UTF8ResourceBundleControl.get()
		);
		ResourceBundle axiosMasterBundle = ResourceBundle.getBundle(
			baseName,
			PaperConfigManager.getInstance().defaultLocale(),
			AxiosPlugin.class.getClassLoader(),
			UTF8ResourceBundleControl.get()
		);
		assert axiosMasterBundle.containsKey("axios.master");
		String commentString = commentsBundle.containsKey(path) ? commentsBundle.getString(path) : "";
		String axiosMasterCommentString = axiosMasterBundle.getString("axios.master");
		axiosMasterCommentString = String.format(axiosMasterCommentString, type.getSimpleName(), defaultValue);
		List<String> comments = new ArrayList<>(Arrays.asList(commentString.split("\\s*\\n\\s*")));
		if (comments.size() == 1 && comments.get(0).equals(""))
			comments.set(0, axiosMasterCommentString);
		else
		{
			comments.add("");
			comments.add(axiosMasterCommentString);
		}
		
		return comments;
	}
	
	
	@Override
	public @NonNull T get(R setting)
	{
		return PaperConfigManager.getInstance().get(callingClazz, setting);
	}
	
	@Override
	public @NonNull T defaultValue()
	{
		return defaultValue;
	}
	
	@Override
	public @NonNull Class<T> type()
	{
		return type;
	}
}
