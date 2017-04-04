/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.avgearn;

import java.util.List;

/**
 * The Interface AvgEarnLevelMasterSettingRepository.
 */
public interface AvgEarnLevelMasterSettingRepository {

	/**
	 * Find all.
	 *
	 * @param companyCode the company code
	 * @return the list
	 */
	List<AvgEarnLevelMasterSetting> findAll(String companyCode);

}
