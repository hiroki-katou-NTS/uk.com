/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.holiday;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento;
import nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHDOccUnit;
import nts.uk.ctx.at.shared.dom.overtime.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.infra.entity.overtime.holiday.KshstSuperHdConMed;

/**
 * The Class JpaSuperHD60HConMedGetMemento.
 */
public class JpaSuperHD60HConMedGetMemento implements SuperHD60HConMedGetMemento {

	/** The entity. */
	private KshstSuperHdConMed entity;

	/**
	 * Instantiates a new jpa super HD 60 H con med get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSuperHD60HConMedGetMemento(KshstSuperHdConMed entity) {
		this.entity = entity;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getTimeRoundingSetting()
	 */
	@Override
	public TimeRoundingSetting getTimeRoundingSetting() {
		return new TimeRoundingSetting(Unit.valueOf(this.entity.getRoundTime()),
				Rounding.valueOf(this.entity.getRounding()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getSuperHolidayOccurrenceUnit()
	 */
	@Override
	public SuperHDOccUnit getSuperHolidayOccurrenceUnit() {
		return new SuperHDOccUnit(this.entity.getSuperHdUnit());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.holiday.SuperHD60HConMedGetMemento#
	 * getPremiumExtra60HRates()
	 */
	@Override
	public List<PremiumExtra60HRate> getPremiumExtra60HRates() {
		// TODO Auto-generated method stub
		return null;
	}

}
