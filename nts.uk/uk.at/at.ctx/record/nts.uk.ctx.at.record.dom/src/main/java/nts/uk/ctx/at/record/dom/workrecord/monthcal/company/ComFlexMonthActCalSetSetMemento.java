/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.company;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.FlexMonthWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface CompanyCalMonthlyFlexSetMemento.
 */
public interface ComFlexMonthActCalSetSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the aggr setting monthly of flx new.
	 *
	 * @param aggrSettingMonthlyOfFlxNew the new aggr setting monthly of flx new
	 */
	void setFlexAggrSetting(FlexMonthWorkTimeAggrSet aggrSettingMonthlyOfFlxNew);

}
