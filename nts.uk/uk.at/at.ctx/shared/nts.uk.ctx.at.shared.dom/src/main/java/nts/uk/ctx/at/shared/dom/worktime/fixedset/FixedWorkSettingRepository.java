/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.Optional;

/**
 * The Interface WorkdayoffFrameRepository.
 */
public interface FixedWorkSettingRepository {

	/**
	 * Find workdayoff frame.
	 *
	 * @param companyId the company id
	 * @param workdayoffFrNo the workdayoff fr no
	 * @return the optional
	 */
	Optional<FixedWorkSetting> find(String companyId, String workTimeCode);

}
