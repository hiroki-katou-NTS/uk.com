package nts.uk.shr.pereg.app.find;

public interface PeregFinder <R, Q> {
	/**
	 * Returns ID of category that this handler can handle
	 * @return category ID
	 */
	String targetCategoryCode();
	
	/**
	 * Returns class of command that is handled by this handler
	 * @return class of command
	 */
	Class<?> finderClass();
	
	R getData(Q query);
	
	@SuppressWarnings("unchecked")
	default R handleProcessor(Object query) {
		return this.getData((Q) query);
	}
}
