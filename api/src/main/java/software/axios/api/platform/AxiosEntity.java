package software.axios.api.platform;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A base interface for all Axios entities used
 * inside the Axios {@link software.axios.api.i18n.TagBuilder}.
 *
 * @since 1.0
 */
public interface AxiosEntity
{
	/**
	 * Returns the unique identifier of this entity.
	 *
	 * @return the unique identifier of this entity.
	 * @since 1.0
	 */
	@NonNull UUID uniqueId();
	
	/**
	 * Returns the name of this entity.
	 *
	 * @return the name of this entity.
	 * @since 1.0
	 */
	@NonNull String name();
	
	/**
	 * Returns the translation key of this entity.
	 *
	 * @return the translation key of this entity.
	 * @since 1.0
	 */
	@NonNull String translationKey();
}
