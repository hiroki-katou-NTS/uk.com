package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

/**
 * The Interface MasterCopyDataGetMemento.
 */
public interface MasterCopyDataGetMemento {
	
	/**
	 * Gets the master copy id.
	 *
	 * @return the master copy id
	 */
	MasterCopyCategoryNo getCategoryNo();

	/**
	 * Gets the target table.
	 *
	 * @return the target table
	 */
	List<TargetTableInfo> getTargetTable();
}
