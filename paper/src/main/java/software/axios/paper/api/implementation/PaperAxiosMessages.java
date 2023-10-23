package software.axios.paper.api.implementation;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.i18n.AxiosMessages;
import software.axios.api.i18n.MessagesInterface;

import java.util.Locale;

public class PaperAxiosMessages implements AxiosMessages
{
	private final Class<? extends MessagesInterface> callingClazz;
	private final String path;
	
	public PaperAxiosMessages(Class<? extends MessagesInterface> callingClazz, String path)
	{
		this.path = path;
		this.callingClazz = callingClazz;
	}
	
	@Override
	public @NonNull String toString(Locale locale)
	{
		return PaperI18nManager.getInstance().get(callingClazz, locale, path);
	}
	
	@Override
	public @NonNull String toString()
	{
		return toString(PaperConfigManager.getInstance().defaultLocale());
	}
	
	@Override
	public void sendTo(Audience audience, TagResolver placeholder)
	{
		var mm = MiniMessage.miniMessage();
		Component parsed = mm.deserialize(toString(), placeholder);
		audience.forEachAudience((a) -> {
			if (a instanceof Player player)
			{
				Component component = mm.deserialize(toString(player.locale()), placeholder);
				player.sendMessage(component);
			}
			else a.sendMessage(parsed);
		});
	}
	
	@Override
	public void sendTo(Audience audience)
	{
		sendTo(audience, TagResolver.empty());
	}
}
