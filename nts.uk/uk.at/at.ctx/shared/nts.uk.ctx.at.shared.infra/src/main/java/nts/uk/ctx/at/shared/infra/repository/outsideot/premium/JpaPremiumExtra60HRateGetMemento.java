/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.premium;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRateGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshmtHd60hPremiumRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshmtHd60hPremiumRatePK;

/**
 * The Class JpaPremiumExtra60HRateGetMemento.
 */
public class JpaPremiumExtra60HRateGetMemento implements PremiumExtra60HRateGetMemento {
	
	/** The entity. */
	private KshmtHd60hPremiumRate entity;
	
	/**
	 * Instantiates a new jpa premium extra 60 H rate get memento.
	 *
	 * @param entity the entity
	 */
	public JpaPremiumExtra60HRateGetMemento(KshmtHd60hPremiumRate entity) {
		if(entity.getKshmtHd60hPremiumRatePK() ==null){
			entity.setKshmtHd60hPremiumRatePK(new KshmtHd60hPremiumRatePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateGetMemento#getBreakdownItemNo()
	 */
	@Override
	public BreakdownItemNo getBreakdownItemNo() {
		return BreakdownItemNo.valueOf(this.entity.getKshmtHd60hPremiumRatePK().getBrdItemNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateGetMemento#getPremiumRate()
	 */
	@Override
	public PremiumRate getPremiumRate() {
		return new PremiumRate(this.entity.getPremiumRate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateGetMemento#getOvertimeNo()
	 */
	@Override
	public OvertimeNo getOvertimeNo() {
		return OvertimeNo.valueOf(this.entity.getKshmtHd60hPremiumRatePK().getOverTimeNo());
	}

}
