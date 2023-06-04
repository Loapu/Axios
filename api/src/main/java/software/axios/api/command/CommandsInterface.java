package software.axios.api.command;

/**
 * The interface for commands created via JorelAli's CommandAPI
 * @see <a href="https://commandapi.jorel.dev/">CommandAPI</a>
 *
 * @author Loapu
 * @since 1.0
 */
public interface CommandsInterface
{
	/**
	 * Register the commands
	 *
	 * @since 1.0
	 */
	void register();
	
	/**
	 * Unregister the commands
	 *
	 * @since 1.0
	 */
	void unregister();
	
}
