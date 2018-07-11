/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.dom.mastercopy;

import java.util.List;

public interface MasterCopyDataRepository {

	/**
	 * Find by master copy id.
	 *
	 * @return the optional
	 */
	List<MasterCopyData> findByMasterCopyId(String masterCopyId);

	/**
	 * Find by master copy ids.
	 *
	 * @param masterCopyIds the master copy ids
	 * @return the list
	 */
	List<MasterCopyData> findByMasterCopyIds(List<String> masterCopyIds);
}
