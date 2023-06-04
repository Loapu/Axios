package software.axios.paper;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import software.axios.api.Axios;
import software.axios.paper.api.AxiosApiProvider;
import software.axios.paper.commands.CommandAxios;
import software.axios.paper.configuration.Settings;
import software.axios.paper.i18n.Messages;

import java.util.Locale;

public class AxiosPlugin extends JavaPlugin
{
	private AxiosApiProvider apiProvider;
	
	private static AxiosPlugin instance;
	public static AxiosPlugin instance()
	{
		return instance;
	}
	
	@Override
	public void onLoad()
	{
		instance = this;
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
	}
	
	@Override
	public void onEnable()
	{
		// Register API
		this.apiProvider = new AxiosApiProvider(this);
		registerApi(this.apiProvider);
		
		// Setup settings
		// Setup messages
		reload();
		
		CommandAPI.onEnable();
		
		// Setup commands
		setupCommands();
		
		getLogger().info("Axios is enabled!");
	}

	@Override
	public void onDisable()
	{
		CommandAxios.instance().unregister();
		CommandAPI.onDisable();
		getLogger().info("Axios is disabled!");
	}
	
	public AxiosApiProvider axiosApiProvider()
	{
		return apiProvider;
	}
	
	private void registerApi(Axios api)
	{
		getServer().getServicesManager().register(Axios.class, api, this, ServicePriority.Normal);
	}
	
	private void setupMessages()
	{
		axiosApiProvider().i18nManager().setup(this, Messages.class, Locale.GERMAN);
	}
	
	private void setupSettings()
	{
		axiosApiProvider().configManager().setup(this, Settings.class);
	}
	
	private void setupCommands()
	{
		CommandAxios.instance().register();
	}
	
	public void reload()
	{
		setupSettings();
		setupMessages();
	}
	
	public void debug(String message)
	{
		if (Settings.DEBUG.get())
		{
			getLogger().info("[DEBUG] " + message);
		}
	}
}
