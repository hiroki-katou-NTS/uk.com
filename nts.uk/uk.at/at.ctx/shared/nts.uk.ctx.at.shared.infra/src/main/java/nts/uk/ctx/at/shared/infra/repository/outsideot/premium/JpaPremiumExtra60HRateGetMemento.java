/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.premium;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRateGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRatePK;

/**
 * The Class JpaPremiumExtra60HRateGetMemento.
 */
public class JpaPremiumExtra60HRateGetMemento implements PremiumExtra60HRateGetMemento {
	
	/** The entity. */
	private KshstPremiumExt60hRate entity;
	
	/**
	 * Instantiates a new jpa premium extra 60 H rate get memento.
	 *
	 * @param entity the entity
	 */
	public JpaPremiumExtra60HRateGetMemento(KshstPremiumExt60hRate entity) {
		if(entity.getKshstPremiumExt60hRatePK() ==null){
			entity.setKshstPremiumExt60hRatePK(new KshstPremiumExt60hRatePK());
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
		return BreakdownItemNo.valueOf(this.entity.getKshstPremiumExt60hRatePK().getBrdItemNo());
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
		return OvertimeNo.valueOf(this.entity.getKshstPremiumExt60hRatePK().getOverTimeNo());
	}

}
