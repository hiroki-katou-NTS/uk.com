/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingSetMemento;

/**
 * The Class EstimatePriceDto.
 */
@Getter
@Setter
public class EstimatePriceDto implements EstimatedPriceSettingSetMemento{
	
	/** The price 1st. */
	private int price1st;
	
	/** The price 2nd. */
	private int price2nd;
	
	/** The price 3rd. */
	private int price3rd;
	
	/** The price 4th. */
	private int price4th;
	
	/** The price 5th. */
	private int price5th;


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.price.
	 * EstimatedPriceSettingSetMemento#setTargetClassification(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.EstimateTargetClassification)
	 */
	@Override
	public void setTargetClassification(EstimateTargetClassification targetClassification) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.price.
	 * EstimatedPriceSettingSetMemento#setPriceSetting(java.util.List)
	 */
	@Override
	public void setPriceSetting(List<EstimatedPrice> priceSetting) {
		
		priceSetting.forEach(price->{
			switch (price.getEstimatedCondition()) {
			case CONDITION_1ST:
				price1st = price.getEstimatedPrice().v();
				break;
			case CONDITION_2ND:
				price2nd = price.getEstimatedPrice().v();
				break;
			case CONDITION_3RD:
				price3rd = price.getEstimatedPrice().v();
				break;
			case CONDITION_4TH:
				price4th = price.getEstimatedPrice().v();
				break;
			case CONDITION_5TH:
				price5th = price.getEstimatedPrice().v();
				break;

			default:
				break;
			}
		});
	}

}
