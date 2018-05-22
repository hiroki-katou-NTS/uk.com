package nts.uk.ctx.sys.assist.dom.mastercopy;

/**
 * The Interface MasterCopyDataSetMemento.
 */
public interface MasterCopyDataSetMemento {
	
	/**
	 * Sets the master copy id.
	 *
	 * @param masterCopyId the new master copy id
	 */
	void setMasterCopyId(String masterCopyId);

	/**
	 * Sets the master copy target.
	 *
	 * @param masterCopyTarget the new master copy target
	 */
	void setMasterCopyTarget(MasterCopyTarget masterCopyTarget);
}
