/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job;

import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * The Interface JobAutoCalSettingSetMemento.
 */
public interface JobAutoCalSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void  setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	void  setPositionId(JobTitleId positionId);
	
	/**
	 * Sets the auto cal overtime setting.
	 *
	 * @param autoCalOvertimeSetting the new auto cal overtime setting
	 */
	void  setNormalOTTime(AutoCalOvertimeSetting normalOTTime);
	
	/**
	 * Sets the auto cal flex overtime setting.
	 *
	 * @param autoCalFlexOvertimeSetting the new auto cal flex overtime setting
	 */
	void  setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime);
	
	/**
	 * Sets the auto cal rest time setting.
	 *
	 * @param autoCalRestTimeSetting the new auto cal rest time setting
	 */
	void  setRestTime(AutoCalRestTimeSetting restTime);

	/**
	 * Sets the leave early.
	 *
	 * @param leaveEarly the new leave early
	 */
	void setLeaveEarly(AutoCalcOfLeaveEarlySetting leaveEarly);
	
	/**
	 * Sets the raising salary.
	 *
	 * @param raisingSalary the new raising salary
	 */
	void setRaisingSalary(AutoCalRaisingSalarySetting raisingSalary);
	
	/**
	 * Sets the divergence time.
	 *
	 * @param divergenceTime the new divergence time
	 */
	void setDivergenceTime(AutoCalcSetOfDivergenceTime divergenceTime);
	
}
