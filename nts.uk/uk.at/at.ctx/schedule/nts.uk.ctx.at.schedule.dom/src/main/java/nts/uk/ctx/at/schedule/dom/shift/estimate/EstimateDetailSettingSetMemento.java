/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.price.EstimatedPriceSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;

/**
 * The Interface EstimateDetailSettingSetMemento.
 */
public interface EstimateDetailSettingSetMemento {
	
	/**
	 * Sets the estimate time.
	 *
	 * @param estimateTime the new estimate time
	 */
	void setEstimateTime(List<EstimateTimeSetting> estimateTime);
	
	
	/**
	 * Sets the estimate price.
	 *
	 * @param estimatePrice the new estimate price
	 */
	void setEstimatePrice(List<EstimatedPriceSetting> estimatePrice);
	
	
	/**
	 * Sets the estimate number of day.
	 *
	 * @param estimateNumberOfDay the new estimate number of day
	 */
	void setEstimateNumberOfDay(List<EstimateNumberOfDay> estimateNumberOfDay);

}
