/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;
import java.util.Optional;

/**
 * The Interface FlowWorkSettingRepository.
 */
public interface FlowWorkSettingRepository {

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	Optional<FlowWorkSetting> find(String companyId, String workTimeCode);

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(FlowWorkSetting domain);

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(FlowWorkSetting domain);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	void remove(String companyId, String workTimeCode);

	/**
	 * Find by C id.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<FlowWorkSetting> findByCId(String companyId);

}
