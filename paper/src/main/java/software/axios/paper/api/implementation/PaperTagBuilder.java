package software.axios.paper.api.implementation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.i18n.TagBuilder;
import software.axios.api.platform.AxiosEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaperTagBuilder implements TagBuilder
{
	private final List<TagResolver> resolverList = new ArrayList<>();
	private final MiniMessage mm = MiniMessage.miniMessage();
	@Override
	public @NonNull TagBuilder add(Map<String, Object> tags)
	{
		return add(tags, false);
	}
	
	@Override
	public @NonNull TagBuilder add(Map<String, Object> tags, boolean parsed)
	{
		for (Map.Entry<String, Object> entry : tags.entrySet())
		{
			resolverList.add(parsed
								 ? Placeholder.parsed(entry.getKey(), String.valueOf(entry.getValue()))
								 : Placeholder.unparsed(entry.getKey(), String.valueOf(entry.getValue()))
			);
		}
		return this;
	}
	
	@Override
	public @NonNull TagBuilder add(String key, Object value)
	{
		return add(key, value, false);
	}
	
	@Override
	public @NonNull TagBuilder add(String key, Object value, boolean parsed)
	{
		resolverList.add(parsed
							 ? Placeholder.parsed(key, String.valueOf(value))
							 : Placeholder.unparsed(key, String.valueOf(value))
		);
		return this;
	}
	
	@Override
	public @NonNull TagBuilder add(String key, AxiosEntity entity)
	{
		Component parsedComponent = mm.deserialize("<hover:show_text:'" + entity.name() + "<newline><lang:gui.entity_tooltip.type:\"<lang:" + entity.translationKey() + ">\"><newline>" + entity.uniqueId() + "'>" + entity.name() + "</hover>");
		resolverList.add(Placeholder.component(key, parsedComponent));
		return this;
	}
	
	@Override
	public @NonNull TagResolver build()
	{
		return TagResolver.builder().resolvers(resolverList.toArray(new TagResolver[0])).build();
	}
}
