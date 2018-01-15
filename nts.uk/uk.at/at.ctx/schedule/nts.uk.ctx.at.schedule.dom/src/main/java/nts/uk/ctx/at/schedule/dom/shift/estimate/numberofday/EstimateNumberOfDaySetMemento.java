/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Interface EstimateNumberOfDaySetMemento.
 */
public interface EstimateNumberOfDaySetMemento {
	
	
	/**
	 * Sets the target classification.
	 *
	 * @param targetClassification the new target classification
	 */
	void setTargetClassification(EstimateTargetClassification targetClassification);
	
	
	/**
	 * Sets the yearly estimate number of day setting.
	 *
	 * @param yearlyEstimateNumberOfDaySetting the new yearly estimate number of day setting
	 */
	void setYearlyEstimateNumberOfDaySetting(List<YearlyEstimateNumberOfDay> yearlyEstimateNumberOfDaySetting);
	
	
	/**
	 * Sets the monthly estimate number of day setting.
	 *
	 * @param monthlyEstimateNumberOfDaySetting the new monthly estimate number of day setting
	 */
	void setMonthlyEstimateNumberOfDaySetting(List<MonthlyEstimateNumberOfDay> monthlyEstimateNumberOfDaySetting);

}
