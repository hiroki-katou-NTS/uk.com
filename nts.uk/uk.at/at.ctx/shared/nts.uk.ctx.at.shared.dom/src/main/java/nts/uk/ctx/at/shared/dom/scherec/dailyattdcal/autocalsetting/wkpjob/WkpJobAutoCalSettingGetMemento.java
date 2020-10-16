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
 * The Interface WkpJobAutoCalSettingGetMemento.
 */
public interface WkpJobAutoCalSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	

	/**
	 * Gets the wkp id.
	 *
	 * @return the wkp id
	 */
	WorkplaceId getWkpId();
	
	/**
	 * Gets the position id.
	 *
	 * @return the position id
	 */
	JobTitleId getJobId();

	/**
	 * Gets the normal OT time.
	 *
	 * @return the normal OT time
	 */
	AutoCalOvertimeSetting getNormalOTTime();
	

	/**
	 * Gets the flex OT time.
	 *
	 * @return the flex OT time
	 */
	AutoCalFlexOvertimeSetting getFlexOTTime();
	

	/**
	 * Gets the rest time.
	 *
	 * @return the rest time
	 */
	AutoCalRestTimeSetting getRestTime();

	/**
	 * Gets the leave early.
	 *
	 * @return the leave early
	 */
	AutoCalcOfLeaveEarlySetting getLeaveEarly();
	
	/**
	 * Gets the raising salary.
	 *
	 * @return the raising salary
	 */
	AutoCalRaisingSalarySetting getRaisingSalary();
	
	/**
	 * Gets the divergence time.
	 *
	 * @return the divergence time
	 */
	AutoCalcSetOfDivergenceTime getDivergenceTime();
}
