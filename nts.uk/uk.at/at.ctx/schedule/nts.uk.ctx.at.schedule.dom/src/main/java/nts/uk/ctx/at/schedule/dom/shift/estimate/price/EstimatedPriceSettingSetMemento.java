/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Interface EstimatedPriceSettingSetMemento.
 */
public interface EstimatedPriceSettingSetMemento {
	
	/**
	 * Sets the target classification.
	 *
	 * @param targetClassification the new target classification
	 */
	void setTargetClassification(EstimateTargetClassification targetClassification);
	
	
	/**
	 * Sets the price setting.
	 *
	 * @param priceSetting the new price setting
	 */
	void setPriceSetting(List<EstimatedPrice> priceSetting);

}
