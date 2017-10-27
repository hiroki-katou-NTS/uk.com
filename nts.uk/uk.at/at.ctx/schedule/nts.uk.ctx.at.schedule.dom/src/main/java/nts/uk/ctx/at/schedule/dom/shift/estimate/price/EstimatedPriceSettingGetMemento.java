/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Interface EstimatedPriceSettingGetMemento.
 */
public interface EstimatedPriceSettingGetMemento {

	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	EstimateTargetClassification getTargetClassification();
	
	
	/**
	 * Gets the price setting.
	 *
	 * @return the price setting
	 */
	List<EstimatedPrice> getPriceSetting();
}
