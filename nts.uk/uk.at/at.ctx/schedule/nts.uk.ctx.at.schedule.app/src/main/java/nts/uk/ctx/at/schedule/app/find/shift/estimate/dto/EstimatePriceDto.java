/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatePrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPrice;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSettingSetMemento;

/**
 * The Class EstimatePriceDto.
 */
@Getter
@Setter
public class EstimatePriceDto implements EstimatedPriceSettingSetMemento{
	
	/** The month. */
	private int month;
	
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
		this.month = targetClassification.value;
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
	
	/**
	 * To price setting.
	 *
	 * @return the list
	 */
	public List<EstimatedPrice> toPriceSetting() {
		List<EstimatedPrice> priceSetting = new ArrayList<>();
		priceSetting.add(new EstimatedPrice(EstimatedCondition.CONDITION_1ST,
				new EstimatePrice(this.getPrice1st())));
		priceSetting.add(new EstimatedPrice(EstimatedCondition.CONDITION_2ND,
				new EstimatePrice(this.getPrice2nd())));
		priceSetting.add(new EstimatedPrice(EstimatedCondition.CONDITION_3RD,
				new EstimatePrice(this.getPrice3rd())));
		priceSetting.add(new EstimatedPrice(EstimatedCondition.CONDITION_4TH,
				new EstimatePrice(this.getPrice4th())));
		priceSetting.add(new EstimatedPrice(EstimatedCondition.CONDITION_5TH,
				new EstimatePrice(this.getPrice5th())));
		return priceSetting;
	}

}
