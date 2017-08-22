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
 * The Interface EstimateDetailSettingGetMemento.
 */
public interface EstimateDetailSettingGetMemento {
	
	/**
	 * Gets the estimate time.
	 *
	 * @return the estimate time
	 */
	List<EstimateTimeSetting> getEstimateTime();
	
	
	/**
	 * Gets the estimate price.
	 *
	 * @return the estimate price
	 */
	List<EstimatedPriceSetting> getEstimatePrice();
	
	
	/**
	 * Gets the estimate number of day.
	 *
	 * @return the estimate number of day
	 */
	List<EstimateNumberOfDay> getEstimateNumberOfDay();

}
