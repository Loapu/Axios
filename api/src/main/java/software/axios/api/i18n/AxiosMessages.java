package software.axios.api.i18n;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Locale;

/**
 * Represents a message that can be sent to an audience.
 *
 * @since 1.0
 */
public interface AxiosMessages
{
	/**
	 * Gets the message as a string.
	 *
	 * @param locale The locale to use.
	 * @return The message as a string.
	 *
	 * @since 1.0
	 */
	@NonNull String toString(Locale locale);
	
	/**
	 * Gets the message as a string.
	 *
	 * @return The message as a string.
	 *
	 * @since 1.0
	 */
	@NonNull String toString();
	
	/**
	 * Sends the message to the audience.
	 *
	 * @param audience The audience to send the message to.
	 * @param placeholder The placeholder resolver to use.
	 *
	 * @since 1.0
	 */
	void sendTo(Audience audience, TagResolver placeholder);
	
	/**
	 * Sends the message to the audience.
	 *
	 * @param audience The audience to send the message to.
	 *
	 * @since 1.0
	 */
	void sendTo(Audience audience);
}
