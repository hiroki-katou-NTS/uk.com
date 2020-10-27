/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatePrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstPriceCom;

/**
 * The Class JpaEstimatedCompanyPriceGetMemento.
 */
public class JpaComEstPriceGetMemento implements  EstimatedPriceSettingGetMemento{
	
	/** The estimate price company. */
	private KscmtEstPriceCom estimatePriceCompany;
	
	/**
	 * Instantiates a new jpa estimated company price get memento.
	 *
	 * @param estimatePriceCompany the estimate price company
	 */
	public JpaComEstPriceGetMemento(KscmtEstPriceCom estimatePriceCompany) {
		this.estimatePriceCompany = estimatePriceCompany;
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
				this.estimatePriceCompany.getKscmtEstPriceComPK().getTargetCls(),
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
				new EstimatePrice(this.estimatePriceCompany.getEstCondition1stMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_2ND,
				new EstimatePrice(this.estimatePriceCompany.getEstCondition2ndMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_3RD,
				new EstimatePrice(this.estimatePriceCompany.getEstCondition3rdMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_4TH,
				new EstimatePrice(this.estimatePriceCompany.getEstCondition4thMny())));
		estimatedPrice.add(new EstimatedPrice(EstimatedCondition.CONDITION_5TH,
				new EstimatePrice(this.estimatePriceCompany.getEstCondition5thMny())));
		return estimatedPrice;
	}
	
}
