/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.personal;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatePrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal.KscmtEstPriceSya;

/**
 * The Class JpaEstimatedPersonalPriceGetMemento.
 */
public class JpaPerEstPriceGetMemento implements  EstimatedPriceSettingGetMemento{
	
	/** The estimate price Personal. */
	private KscmtEstPriceSya estimatePricePersonal;
	
	/**
	 * Instantiates a new jpa estimated Personal price get memento.
	 *
	 * @param estimatePricePersonal the estimate price Personal
	 */
	public JpaPerEstPriceGetMemento(KscmtEstPriceSya estimatePricePersonal) {
		this.estimatePricePersonal = estimatePricePersonal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.price.
	 * EstimatedPriceSettingGetMemento#getTargetClassification()
	 */
	@Override
	public EstimateTargetClassification getTargetClassification() {
		return EnumAdaptor.valueOf(
				this.estimatePricePersonal.getKscmtEstPriceSyaPK().getTargetCls(),
				EstimateTargetClassification.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.price.
	 * EstimatedPriceSettingGetMemento#getPriceSetting()
	 */
	@Override
	public List<EstimatedPrice> getPriceSetting() {
		List<EstimatedPrice> estimatedPrice = new ArrayList<>();
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_1ST,
				new EstimatePrice(this.estimatePricePersonal.getEstCondition1stMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_2ND,
				new EstimatePrice(this.estimatePricePersonal.getEstCondition2ndMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_3RD,
				new EstimatePrice(this.estimatePricePersonal.getEstCondition3rdMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_4TH,
				new EstimatePrice(this.estimatePricePersonal.getEstCondition4thMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_5TH,
				new EstimatePrice(this.estimatePricePersonal.getEstCondition5thMny())));
		return estimatedPrice;
	}
	
}
