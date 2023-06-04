package software.axios.paper.i18n;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.Axios;
import software.axios.api.i18n.AxiosMessages;
import software.axios.api.i18n.MessagesInterface;
import software.axios.paper.AxiosPlugin;

import java.util.Locale;

public class Messages implements MessagesInterface
{
	public static final Messages COMMAND = new Messages("axios.command");
	public static final Messages COMMAND_RELOAD = new Messages("axios.command.reload");
	
	private final Axios axios = AxiosPlugin.instance().axiosApiProvider();
	private final AxiosMessages axiosMessages;
	
	private Messages(String path)
	{
		this.axiosMessages = axios.axiosMessages(this.getClass(), path);
	}
	
	@Override
	public @NonNull String toString(Locale locale)
	{
		return axiosMessages.toString(locale);
	}
	
	@Override
	public @NonNull String toString()
	{
		return axiosMessages.toString();
	}
	
	@Override
	public void sendTo(Audience audience, TagResolver placeholder)
	{
		axiosMessages.sendTo(audience, placeholder);
	}
	
	@Override
	public void sendTo(Audience audience)
	{
		axiosMessages.sendTo(audience);
	}
}
