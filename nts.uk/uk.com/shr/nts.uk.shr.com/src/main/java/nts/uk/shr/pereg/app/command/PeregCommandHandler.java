package nts.uk.shr.pereg.app.command;

public interface PeregCommandHandler<C> {

	/**
	 * Returns ID of category that this handler can handle
	 * @return category ID
	 */
	String targetCategoryId();
	
	/**
	 * Returns class of command that is handled by this handler
	 * @return class of command
	 */
	Class<?> commandClass();
}
