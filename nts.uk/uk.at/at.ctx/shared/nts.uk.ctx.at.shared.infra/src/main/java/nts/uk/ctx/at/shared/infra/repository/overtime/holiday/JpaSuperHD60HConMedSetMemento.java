/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.holiday;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedSetMemento;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHDOccUnit;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.infra.entity.overtime.holiday.KshstSuperHdConMed;

/**
 * The Class JpaSuperHD60HConMedSetMemento.
 */
public class JpaSuperHD60HConMedSetMemento implements SuperHD60HConMedSetMemento{
	
	/** The entity. */
	private KshstSuperHdConMed entity;
	
	/**
	 * Instantiates a new jpa super HD 60 H con med set memento.
	 *
	 * @param entity the entity
	 */
	public JpaSuperHD60HConMedSetMemento(KshstSuperHdConMed entity) {
		this.entity = entity;
	}
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.setCid(companyId.v());
	}

	/**
	 * Sets the time rounding setting.
	 *
	 * @param timeRoundingSetting the new time rounding setting
	 */
	@Override
	public void setTimeRoundingSetting(TimeRoundingSetting timeRoundingSetting) {
		this.entity.setRounding(timeRoundingSetting.getRounding().value);
		this.entity.setRoundTime(timeRoundingSetting.getRoundingTime().value);
	}

	/**
	 * Sets the super holiday occurrence unit.
	 *
	 * @param superHolidayOccurrenceUnit the new super holiday occurrence unit
	 */
	@Override
	public void setSuperHolidayOccurrenceUnit(SuperHDOccUnit superHolidayOccurrenceUnit) {
		this.entity.setSuperHdUnit(superHolidayOccurrenceUnit.valueAsMinutes());
		
	}

	/**
	 * Sets the premium extra 60 H rates.
	 *
	 * @param premiumExtra60HRates the new premium extra 60 H rates
	 */
	@Override
	public void setPremiumExtra60HRates(List<PremiumExtra60HRate> premiumExtra60HRates) {
		//TO DO
	}

}
