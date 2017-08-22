/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Interface EstimateTimeSettingGetMemento.
 */
public interface EstimateTimeSettingGetMemento {

	
	/**
	 * Gets the target classification.
	 *
	 * @return the target classification
	 */
	EstimateTargetClassification getTargetClassification();
	
	
	/**
	 * Gets the yearly estimate time setting.
	 *
	 * @return the yearly estimate time setting
	 */
	List<YearlyEstimateTimeSetting> getYearlyEstimateTimeSetting();
	
	
	
	/**
	 * Gets the monthly estimate time setting.
	 *
	 * @return the monthly estimate time setting
	 */
	List<MonthlyEstimateTimeSetting> getMonthlyEstimateTimeSetting();
}
