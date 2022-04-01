/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.uk.ctx.sys.assist.dom.mastercopy.handler.KeyValueHolder;
import nts.uk.shr.com.company.CompanyId;
import nts.uk.shr.com.tenant.TenantCode;

import java.util.List;

public interface MasterCopyDataRepository {

	
	/**
	 * Find by category no.
	 *
	 * @param categoryNo the category no
	 * @return the master copy data
	 */
	MasterCopyData findByCategoryNo(Integer categoryNo);

	/**
	 * Find by master copy ids.
	 *
	 * @param listCategoryNo the master copy ids
	 * @return the list
	 */
	List<MasterCopyData> findByListCategoryNo(List<Integer> listCategoryNo);
	
	/**
	 * Do copy.
     * @param tableName the table name
     * @param keys
     * @param copyMethod the copy method
     * @param companyId the company id
     */
	void doCopy(
			String tableName,
			List<String> keys,
			CopyMethod copyMethod,
			CompanyId companyId,
			KeyValueHolder keyValueHolder);

	/**
	 *
	 * @param categoryNo
	 * @return
	 */
	MasterCopyCategory findCatByCategoryNo(Integer categoryNo);
}
