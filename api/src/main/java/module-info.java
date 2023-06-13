/**
 * This module contains the Axios API.
 *
 * @since 1.0
 * @author Loapu
 * @see <a href="https://docs.axios.software">Documentation</a>
 */
module software.axios.api {
	requires org.checkerframework.checker.qual;
	requires net.kyori.adventure.text.minimessage;
	requires net.kyori.adventure;
	requires org.jetbrains.annotations;
	exports software.axios.api;
	exports software.axios.api.i18n;
	exports software.axios.api.command;
	exports software.axios.api.configuration;
	exports software.axios.api.platform;
}