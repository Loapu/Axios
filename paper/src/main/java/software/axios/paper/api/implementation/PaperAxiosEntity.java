package software.axios.paper.api.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import software.axios.api.platform.AxiosEntity;

import java.util.UUID;

public class PaperAxiosEntity implements AxiosEntity
{
	private final UUID uuid;
	private final String name;
	private final EntityType type;
	public PaperAxiosEntity(Object entity)
	{
		if (!(entity instanceof Entity bukkitEntity))
		{
			throw new IllegalArgumentException("entity must be an instance of org.bukkit.entity.Entity");
		}
		this.uuid = bukkitEntity.getUniqueId();
		var customName = bukkitEntity.customName();
		this.name = customName != null ? MiniMessage.miniMessage().serialize(customName) : bukkitEntity.getName();
		this.type = bukkitEntity.getType();
	}
	@Override
	public @NotNull UUID uniqueId()
	{
		return uuid;
	}
	
	@Override
	public @NotNull String name()
	{
		return name;
	}
	
	@Override
	public @NotNull String translationKey()
	{
		return type.translationKey();
	}
}
