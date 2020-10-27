/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishmentGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstDaysSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstTimeSya;

/**
 * The Class JpaPersonalEstablishmentGetMemento.
 */
public class JpaPerEstablishmentGetMemento implements PersonalEstablishmentGetMemento{
	
	private static final int FIRST_TIME = 0;
	
	/** The estimate time Personals. */
	private List<KscmtEstTimeSya> estimateTimePersonals;
	
	/** The estimate price Personals. */
	private List<KscmtEstPriceSya> estimatePricePersonals;
	
	/** The estimate days Personals. */
	private List<KscmtEstDaysSya> estimateDaysPersonals;
	
	
	
	/**
	 * Instantiates a new jpa Personal establishment get memento.
	 *
	 * @param estimateTimePersonals the estimate time Personals
	 */
	public JpaPerEstablishmentGetMemento(List<KscmtEstTimeSya> estimateTimePersonals,
			List<KscmtEstPriceSya> estimatePricePersonals,
			List<KscmtEstDaysSya> estimateDaysPersonals) {
		this.estimateTimePersonals = estimateTimePersonals;
		this.estimatePricePersonals = estimatePricePersonals;
		this.estimateDaysPersonals = estimateDaysPersonals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Personal.
	 * PersonalEstablishmentGetMemento#getTargetYear()
	 */
	@Override
	public Year getTargetYear() {
		return new Year(this.estimateTimePersonals.get(FIRST_TIME).getKscmtEstTimeSyaPK()
				.getTargetYear());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.Personal.
	 * PersonalEstablishmentGetMemento#getAdvancedSetting()
	 */
	@Override
	public EstimateDetailSetting getAdvancedSetting() {
		return new EstimateDetailSetting(new JpaPerEstDetailSetGetMemento(
				this.estimateTimePersonals, this.estimatePricePersonals, this.estimateDaysPersonals));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.personal.
	 * PersonalEstablishmentGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return estimateTimePersonals.get(FIRST_TIME).getKscmtEstTimeSyaPK().getSid();
	}

}
