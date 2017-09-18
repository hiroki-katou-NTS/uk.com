/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.holiday;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHD60HConMedGetMemento;
import nts.uk.ctx.at.shared.dom.outsideot.holiday.SuperHDOccUnit;
import nts.uk.ctx.at.shared.dom.outsideot.premium.extra.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.holiday.KshstSuperHdConMed;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRate;
import nts.uk.ctx.at.shared.infra.repository.overtime.premium.JpaPremiumExtra60HRateGetMemento;

/**
 * The Class JpaSuperHD60HConMedGetMemento.
 */
public class JpaSuperHD60HConMedGetMemento implements SuperHD60HConMedGetMemento {

	/** The entity. */
	private KshstSuperHdConMed entity;
	
	/** The entity premium extra 60 H rates. */
	private List<KshstPremiumExt60hRate> entityPremiumExtra60HRates;

	/**
	 * Instantiates a new jpa super HD 60 H con med get memento.
	 *
	 * @param entity the entity
	 * @param entityPremiumExtra60HRates the entity premium extra 60 H rates
	 */
	public JpaSuperHD60HConMedGetMemento(KshstSuperHdConMed entity,
			List<KshstPremiumExt60hRate> entityPremiumExtra60HRates) {
		this.entity = entity;
		this.entityPremiumExtra60HRates = entityPremiumExtra60HRates;
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
		return this.entityPremiumExtra60HRates.stream().map(
				entity -> new PremiumExtra60HRate(new JpaPremiumExtra60HRateGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
