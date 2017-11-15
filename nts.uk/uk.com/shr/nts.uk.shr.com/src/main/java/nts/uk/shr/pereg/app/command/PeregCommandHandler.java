package nts.uk.shr.pereg.app.command;

import lombok.val;

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
	
	void handle(C command);
	
	@SuppressWarnings("unchecked")
	default void handlePeregCommand(Object peregCommand) {
		this.handle((C)peregCommand);
	}
	
	default void handlePeregCommand(String personId, String employeeId, ItemsByCategory itemsByCategory) {
		val commandForSystemDomain = itemsByCategory.createCommandForSystemDomain(
				personId, employeeId, this.commandClass());
		
		this.handlePeregCommand(commandForSystemDomain);
	}
}
