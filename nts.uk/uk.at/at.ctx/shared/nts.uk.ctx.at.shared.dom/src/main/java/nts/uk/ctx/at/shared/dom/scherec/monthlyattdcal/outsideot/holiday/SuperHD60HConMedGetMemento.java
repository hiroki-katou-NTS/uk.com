/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface SuperHD60HConMedGetMemento.
 */
public interface SuperHD60HConMedGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the time rounding setting.
	 *
	 * @return the time rounding setting
	 */
	public TimeRoundingSetting getTimeRoundingSetting();
	
	
	/**
	 * Gets the super holiday occurrence unit.
	 *
	 * @return the super holiday occurrence unit
	 */
	public SuperHDOccUnit getSuperHolidayOccurrenceUnit();
	
	
	/**
	 * Gets the premium extra 60 H rates.
	 *
	 * @return the premium extra 60 H rates
	 */
	public List<PremiumExtra60HRate> getPremiumExtra60HRates();
}
