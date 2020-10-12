/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

//import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
//import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

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
	
	
	List<FlowWorkSetting> findByCidAndWorkCodes(String cid, List<String> workTimeCodes);

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

	/**
	 * Gets the fix offday work rest timezones.
	 *
	 * @param companyId the company id
	 * @return the fix offday work rest timezones
	 */
	Map<WorkTimeCode, List<AmPmWorkTimezone>> getFlowOffdayWorkRestTimezones(String companyId, List<String> workTimeCodes);

	/**
	 * Gets the fix half day work rest timezones.
	 *
	 * @param companyId the company id
	 * @return the fix half day work rest timezones
	 */
	Map<WorkTimeCode, List<AmPmWorkTimezone>> getFlowHalfDayWorkRestTimezones(String companyId, List<String> workTimeCodes);
}
