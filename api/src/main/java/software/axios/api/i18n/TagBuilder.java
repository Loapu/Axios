package software.axios.api.i18n;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;
import software.axios.api.platform.AxiosEntity;

import java.util.Map;

/**
 * A builder for creating tag resolvers.
 *
 * @since 1.0
 */
public interface TagBuilder
{
	/**
	 * Adds a tag to the builder.
	 *
	 * @param tags map of tags to add
	 * @return the builder
	 * @since 1.0
	 */
	@NonNull TagBuilder add(Map<String, Object> tags);
	
	/**
	 * Adds a tag to the builder.
	 *
	 * @param tags map of tags to add
	 * @param parsed whether the tags should be parsed
	 * @return the builder
	 * @since 1.0
	 */
	@NonNull TagBuilder add(Map<String, Object> tags, boolean parsed);
	
	/**
	 * Adds a tag to the builder.
	 *
	 * @param key the path of the tag
	 * @param value the value of the tag
	 * @return the builder
	 * @since 1.0
	 */
	@NonNull TagBuilder add(String key, Object value);
	
	/**
	 * Adds a tag to the builder.
	 *
	 * @param key the path of the tag
	 * @param value the value of the tag
	 * @param parsed whether the tag should be parsed
	 * @return the builder
	 * @since 1.0
	 */
	@NonNull TagBuilder add(String key, Object value, boolean parsed);
	
	/**
	 * Adds a tag to the builder.
	 *
	 * @param key the path of the tag
	 * @param entity the entity to use
	 * @return the builder
	 * @since 1.0
	 */
	@NonNull TagBuilder add(String key, AxiosEntity entity);
	
	/**
	 * Builds the tag resolver.
	 *
	 * @return the tag resolver
	 * @since 1.0
	 */
	@NonNull TagResolver build();
}
