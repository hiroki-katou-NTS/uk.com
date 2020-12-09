/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceComSetPK;

/**
 * The Class JpaEstimatedCompanyPriceSetMemento.
 */
public class JpaComEstPriceSetMemento implements EstimatedPriceSettingSetMemento{
	
	/** The estimate price company. */
	private KscmtEstPriceCom estimatePriceCompany;
	
	/**
	 * Instantiates a new jpa estimated company price set memento.
	 *
	 * @param estimatePriceCompany the estimate price company
	 */
	public JpaComEstPriceSetMemento(KscmtEstPriceCom estimatePriceCompany) {
		if (estimatePriceCompany.getKscmtEstPriceComSetPK() == null) {
			estimatePriceCompany.setKscmtEstPriceComSetPK(new KscmtEstPriceComSetPK());
		}
		this.estimatePriceCompany = estimatePriceCompany;
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
		this.estimatePriceCompany.getKscmtEstPriceComSetPK()
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
				this.estimatePriceCompany.setEstCondition1stMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_2ND:
				this.estimatePriceCompany.setEstCondition2ndMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_3RD:
				this.estimatePriceCompany.setEstCondition3rdMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_4TH:
				this.estimatePriceCompany.setEstCondition4thMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_5TH:
				this.estimatePriceCompany.setEstCondition5thMny(price.getEstimatedPrice().v());
				break;

			default:
				break;
			}
		});
	}

}
