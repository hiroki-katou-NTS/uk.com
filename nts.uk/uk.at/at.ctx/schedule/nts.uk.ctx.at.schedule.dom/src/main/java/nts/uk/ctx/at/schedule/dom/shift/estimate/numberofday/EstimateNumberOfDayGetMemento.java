/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Interface EstimateNumberOfDayGetMemento.
 */
public interface EstimateNumberOfDayGetMemento {
	
	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	EstimateTargetClassification getTargetClassification();
	
	
	
	/**
	 * Gets the yearly estimate number of day setting.
	 *
	 * @return the yearly estimate number of day setting
	 */
	List<YearlyEstimateNumberOfDay> getYearlyEstimateNumberOfDaySetting();
	
	
	/**
	 * Gets the monthly estimate number of day setting.
	 *
	 * @return the monthly estimate number of day setting
	 */
	List<MonthlyEstimateNumberOfDay> getMonthlyEstimateNumberOfDaySetting();
}
