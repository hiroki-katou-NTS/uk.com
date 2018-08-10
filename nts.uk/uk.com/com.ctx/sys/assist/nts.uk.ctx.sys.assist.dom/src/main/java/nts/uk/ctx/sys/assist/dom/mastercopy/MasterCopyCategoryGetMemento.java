package nts.uk.ctx.sys.assist.dom.mastercopy;

/**
 * The Interface MasterCopyCategoryGetMemento.
 */
public interface MasterCopyCategoryGetMemento {
	
	/**
	 * Gets the system type.
	 *
	 * @return the system type
	 */
	SystemType getSystemType();

	/**
	 * Gets the master copy id.
	 *
	 * @return the master copy id
	 */
	MasterCopyCategoryNo getCategoryNo();

	/**
	 * Gets the master copy category.
	 *
	 * @return the master copy category
	 */
	MasterCopyCategoryName getCategoryName();

	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	MasterCopyCategoryOrder getOrder();
}
