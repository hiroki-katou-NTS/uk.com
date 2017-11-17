package nts.uk.shr.pereg.app.command;

public interface PeregDeleteCommandHandler<C> {
	
	/**
	 * Returns ID of category that this handler can handle
	 * @return category ID
	 */
	String targetCategoryId();
	
	void handle(C command);
	
	@SuppressWarnings("unchecked")
	default void handlePeregCommand(Object peregCommand) {
		this.handle((C)peregCommand);
	}
}
