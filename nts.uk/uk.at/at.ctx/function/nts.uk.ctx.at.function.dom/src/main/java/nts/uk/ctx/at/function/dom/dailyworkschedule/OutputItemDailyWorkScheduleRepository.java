/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import java.util.Optional;

/**
 * The Interface OutputItemDailyWorkScheduleRepository.
 * @author HoangDD change by LienPTK 2020/08/29
 */
public interface OutputItemDailyWorkScheduleRepository {

	/**
	 * Find by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the optional
	 */
	public Optional<OutputItemDailyWorkSchedule> findByLayoutId(String layoutId);


	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(OutputItemDailyWorkSchedule domain, int selectionType, String companyId, String employeeId);
}
