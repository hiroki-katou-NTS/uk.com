/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkpjob;

import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * The Interface WkpJobAutoCalSettingSetMemento.
 */
public interface WkpJobAutoCalSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void  setCompanyId(CompanyId companyId);

	

	/**
	 * Sets the wkp id.
	 *
	 * @param workplaceId the new wkp id
	 */
	void  setWkpId(WorkplaceId workplaceId);

	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	void  setJobId(JobTitleId positionId);

	/**
	 * Sets the normal OT time.
	 *
	 * @param normalOTTime the new normal OT time
	 */
	void  setNormalOTTime(AutoCalOvertimeSetting normalOTTime);

	

	/**
	 * Sets the flex OT time.
	 *
	 * @param flexOTTime the new flex OT time
	 */
	void  setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime);

	

	/**
	 * Sets the rest time.
	 *
	 * @param restTime the new rest time
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
