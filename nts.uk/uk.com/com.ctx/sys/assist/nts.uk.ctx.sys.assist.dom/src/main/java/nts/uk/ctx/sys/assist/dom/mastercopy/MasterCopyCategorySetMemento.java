package nts.uk.ctx.sys.assist.dom.mastercopy;

/**
 * The Interface MasterCopyCategorySetMemento.
 */
public interface MasterCopyCategorySetMemento {
	
	/**
	 * Sets the system type.
	 *
	 * @param systemType the new system type
	 */
	void setSystemType(SystemType systemType);

	/**
	 * Sets the master copy id.
	 *
	 * @param masterCopyId the new master copy id
	 */
	void setCategoryNo(MasterCopyCategoryNo masterCopyId);

	/**
	 * Sets the master copy category.
	 *
	 * @param masterCopyCategory the new master copy category
	 */
	void setMasterCopyCategory(MasterCopyCategoryName masterCopyCategory);

	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	void setOrder(MasterCopyCategoryOrder order);
}
