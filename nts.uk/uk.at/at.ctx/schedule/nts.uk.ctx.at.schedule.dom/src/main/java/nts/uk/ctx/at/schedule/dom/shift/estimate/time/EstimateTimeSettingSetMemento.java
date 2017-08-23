/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Interface EstimateTimeSettingSetMemento.
 */
public interface EstimateTimeSettingSetMemento {

	
	/**
	 * Sets the target classification.
	 *
	 * @param targetClassification the new target classification
	 */
	void setTargetClassification(EstimateTargetClassification targetClassification);
	
	
	/**
	 * Sets the yearly estimate time setting.
	 *
	 * @param yearlyEstimateTimeSetting the new yearly estimate time setting
	 */
	void setYearlyEstimateTimeSetting(List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting);
	
	
	
	/**
	 * Gets the monthly estimate time setting.
	 *
	 * @return the monthly estimate time setting
	 */
	void setMonthlyEstimateTimeSetting(List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting);
}
