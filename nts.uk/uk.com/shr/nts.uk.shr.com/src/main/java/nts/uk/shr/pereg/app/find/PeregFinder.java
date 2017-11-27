package nts.uk.shr.pereg.app.find;

public interface PeregFinder<T> {
	/**
	 * Returns ID of category that this handler can handle
	 * @return category ID
	 */
	String targetCategoryCode();
	
	/**
	 * Returns class of command that is handled by this handler
	 * @return class of command
	 */
	Class<T> dtoClass();
	
	
}
