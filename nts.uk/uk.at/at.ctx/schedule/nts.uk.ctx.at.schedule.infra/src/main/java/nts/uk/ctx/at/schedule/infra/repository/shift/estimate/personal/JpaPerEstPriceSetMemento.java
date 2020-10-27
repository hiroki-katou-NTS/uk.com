/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSyaPK;

/**
 * The Class JpaEstimatedPersonalPriceSetMemento.
 */
public class JpaPerEstPriceSetMemento implements EstimatedPriceSettingSetMemento{
	
	/** The estimate price Personal. */
	private KscmtEstPriceSya estimatePricePersonal;
	
	/**
	 * Instantiates a new jpa estimated Personal price set memento.
	 *
	 * @param estimatePricePersonal the estimate price Personal
	 */
	public JpaPerEstPriceSetMemento(KscmtEstPriceSya estimatePricePersonal) {
		if (estimatePricePersonal.getKscmtEstPriceSyaPK() == null) {
			estimatePricePersonal.setKscmtEstPriceSyaPK(new KscmtEstPriceSyaPK());
		}
		this.estimatePricePersonal = estimatePricePersonal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.price.
	 * EstimatedPriceSettingSetMemento#setTargetClassification(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.EstimateTargetClassification)
	 */
	@Override
	public void setTargetClassification(EstimateTargetClassification targetClassification) {
		this.estimatePricePersonal.getKscmtEstPriceSyaPK()
				.setTargetCls(targetClassification.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingSetMemento#setPriceSetting(java.util.List)
	 */
	@Override
	public void setPriceSetting(List<EstimatedPrice> priceSetting) {
		priceSetting.forEach(price -> {
			switch (price.getEstimatedCondition()) {
			case CONDITION_1ST:
				this.estimatePricePersonal.setEstCondition1stMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_2ND:
				this.estimatePricePersonal.setEstCondition2ndMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_3RD:
				this.estimatePricePersonal.setEstCondition3rdMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_4TH:
				this.estimatePricePersonal.setEstCondition4thMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_5TH:
				this.estimatePricePersonal.setEstCondition5thMny(price.getEstimatedPrice().v());
				break;

			default:
				break;
			}
		});
	}

}
