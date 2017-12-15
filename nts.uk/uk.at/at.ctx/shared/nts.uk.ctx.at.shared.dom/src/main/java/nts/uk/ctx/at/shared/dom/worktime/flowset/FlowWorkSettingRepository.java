/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.Optional;

/**
 * The Interface FlowWorkSettingRepository.
 */
public interface FlowWorkSettingRepository {

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the worktime code
	 * @return the optional
	 */
	public Optional<FlowWorkSetting> find(String companyId, String workTimeCode);

	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(FlowWorkSetting domain);

}
