/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting.com;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;

/**
 * The Interface ComAutoCalSettingSetMemento.
 */
public interface ComAutoCalSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void  setCompanyId(CompanyId companyId);
	
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
}
