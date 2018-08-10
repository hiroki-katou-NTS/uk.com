package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

/**
 * The Interface MasterCopyDataSetMemento.
 */
public interface MasterCopyDataSetMemento {
	
	/**
	 * Sets the master copy id.
	 *
	 * @param masterCopyId the new master copy id
	 */
	void setCategoryNo(MasterCopyCategoryNo categoryNo);

	/**
	 * Sets the target table.
	 *
	 * @param targetTable the new target table
	 */
	void setTargetTable(List<TargetTableInfo> targetTables);
}
