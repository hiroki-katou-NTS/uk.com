/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.premium;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRateSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRate;
import nts.uk.ctx.at.shared.infra.entity.outsideot.premium.KshstPremiumExt60hRatePK;

/**
 * The Class JpaPremiumExtra60HRateSetMemento.
 */
public class JpaPremiumExtra60HRateSetMemento implements PremiumExtra60HRateSetMemento{

	
	/** The entity. */
	private KshstPremiumExt60hRate entity;
	
	/**
	 * Instantiates a new jpa premium extra 60 H rate set memento.
	 *
	 * @param entity the entity
	 * @param companyId the company id
	 */
	public JpaPremiumExtra60HRateSetMemento(KshstPremiumExt60hRate entity, String companyId) {
		if(entity.getKshstPremiumExt60hRatePK() ==null){
			entity.setKshstPremiumExt60hRatePK(new KshstPremiumExt60hRatePK());
		}
		entity.getKshstPremiumExt60hRatePK().setCid(companyId);
		this.entity = entity;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateSetMemento#setBreakdownItemNo(nts.uk.ctx.at.shared.dom
	 * .overtime.breakdown.BreakdownItemNo)
	 */
	@Override
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo) {
		this.entity.getKshstPremiumExt60hRatePK().setBrdItemNo(breakdownItemNo.value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateSetMemento#setPremiumRate(nts.uk.ctx.at.shared.dom.
	 * overtime.premium.PremiumRate)
	 */
	@Override
	public void setPremiumRate(PremiumRate premiumRate) {
		this.entity.setPremiumRate(premiumRate.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.premium.extra.
	 * PremiumExtra60HRateSetMemento#setOvertimeNo(nts.uk.ctx.at.shared.dom.
	 * overtime.OvertimeNo)
	 */
	@Override
	public void setOvertimeNo(OvertimeNo overtimeNo) {
		this.entity.getKshstPremiumExt60hRatePK().setOverTimeNo(overtimeNo.value);
	}

}
