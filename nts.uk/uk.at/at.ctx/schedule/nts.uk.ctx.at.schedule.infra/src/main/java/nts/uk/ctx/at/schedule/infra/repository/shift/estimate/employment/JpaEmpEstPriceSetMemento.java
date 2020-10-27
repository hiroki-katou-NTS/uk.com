/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.employment;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmp;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment.KscmtEstPriceEmpPK;

/**
 * The Class JpaEmploymentEstimatedPriceSetMemento.
 */
public class JpaEmpEstPriceSetMemento implements EstimatedPriceSettingSetMemento{
	
	/** The estimate price employment. */
	private KscmtEstPriceEmp estimatePriceEmployment;
	
	/**
	 * Instantiates a new jpa employment estimated price set memento.
	 *
	 * @param estimatePriceEmployment the estimate price employment
	 */
	public JpaEmpEstPriceSetMemento(KscmtEstPriceEmp estimatePriceEmployment) {
		if (estimatePriceEmployment.getKscmtEstPriceEmpPK() == null) {
			estimatePriceEmployment.setKscmtEstPriceEmpPK(new KscmtEstPriceEmpPK());
		}
		this.estimatePriceEmployment = estimatePriceEmployment;
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
		this.estimatePriceEmployment.getKscmtEstPriceEmpPK()
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
				this.estimatePriceEmployment.setEstCondition1stMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_2ND:
				this.estimatePriceEmployment.setEstCondition2ndMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_3RD:
				this.estimatePriceEmployment.setEstCondition3rdMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_4TH:
				this.estimatePriceEmployment.setEstCondition4thMny(price.getEstimatedPrice().v());
				break;
			case CONDITION_5TH:
				this.estimatePriceEmployment.setEstCondition5thMny(price.getEstimatedPrice().v());
				break;

			default:
				break;
			}
		});
	}

}
