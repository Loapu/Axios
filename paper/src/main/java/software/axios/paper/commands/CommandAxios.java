package software.axios.paper.commands;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import io.papermc.paper.plugin.configuration.PluginMeta;
import software.axios.api.Axios;
import software.axios.api.command.CommandsInterface;
import software.axios.paper.AxiosPlugin;
import software.axios.paper.i18n.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandAxios implements CommandsInterface
{
	private static CommandAxios instance;
	private final AxiosPlugin plugin = AxiosPlugin.instance();
	private final Axios axios = plugin.axiosApiProvider();
	private final CommandAPICommand command;
	private final List<CommandAPICommand> subCommands = new ArrayList<>();
	
	private CommandAxios()
	{
		command = new CommandAPICommand("axios");
		commandRoot();
		commandReload();
		command.withSubcommands(subCommands.toArray(new CommandAPICommand[0]));
	}
	
	public static CommandAxios instance()
	{
		if (instance == null)
		{
			instance = new CommandAxios();
		}
		return instance;
	}
	
	@SuppressWarnings("all")
	private void commandRoot()
	{
		command.withAliases("ax");
		command.withPermission("axios.command");
		command.executes((sender, args) -> {
			PluginMeta pluginMeta = plugin.getPluginMeta();
			assert pluginMeta != null;
			Messages.COMMAND.sendTo(sender, axios.tagBuilder().add(Map.of(
				"version", pluginMeta.getVersion(),
				"author", pluginMeta.getAuthors().get(0),
				"website", "<click:open_url:'" + pluginMeta.getWebsite() + "'>" + pluginMeta.getWebsite() + "</click>",
				"description", pluginMeta.getDescription(),
				"name", pluginMeta.getName()
			), true).build());
		});
	}
	
	private void commandReload()
	{
		CommandAPICommand subCommand = new CommandAPICommand("reload");
		subCommand.withAliases("r");
		subCommand.withPermission("axios.command.reload");
		subCommand.executes((sender, args) -> {
			plugin.reload();
			Messages.COMMAND_RELOAD.sendTo(sender);
		});
		subCommands.add(subCommand);
	}
	
	@Override
	public void register()
	{
		command.register();
	}
	
	@Override
	public void unregister()
	{
		CommandAPI.unregister(command.getName());
	}
}
