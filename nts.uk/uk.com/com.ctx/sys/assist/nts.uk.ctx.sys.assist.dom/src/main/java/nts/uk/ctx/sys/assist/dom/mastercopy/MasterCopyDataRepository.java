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
	List<MasterCopyData> findByMasterCopyId(Integer categoryNo);

	/**
	 * Find by master copy ids.
	 *
	 * @param masterCopyIds the master copy ids
	 * @return the list
	 */
	List<MasterCopyData> findByMasterCopyIds(List<Integer> masterCopyIds);
	
	/**
	 * Do copy.
	 *
	 * @param tableName the table name
	 * @param copyMethod the copy method
	 * @param companyId the company id
	 */
	void doCopy(String tableName, CopyMethod copyMethod, String companyId);
}
