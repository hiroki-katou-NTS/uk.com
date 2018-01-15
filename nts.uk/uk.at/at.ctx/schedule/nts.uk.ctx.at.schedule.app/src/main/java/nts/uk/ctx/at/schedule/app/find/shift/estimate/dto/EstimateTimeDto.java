/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;

/**
 * The Class EstimateTimeDto.
 */

@Getter
@Setter
public class EstimateTimeDto implements EstimateTimeSettingSetMemento{
	
	/** The month. */
	private int month;
	
	/** The is yearly. */
	private boolean isYearly; 
	
	/** The time 1st. */
	private int time1st;
	
	/** The time 2nd. */
	private int time2nd;
	
	/** The time 3rd. */
	private int time3rd;
	
	/** The time 4th. */
	private int time4th;
	
	/** The time 5th. */
	private int time5th;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
	 * EstimateTimeSettingSetMemento#setTargetClassification(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.time.EstimateTargetClassification)
	 */
	@Override
	public void setTargetClassification(EstimateTargetClassification targetClassification) {
		this.isYearly = false;

		// check target classification yearly
		if(targetClassification.value == EstimateTargetClassification.YEARLY.value){
			this.isYearly = true;
		}
		this.month = targetClassification.value;
	}

	@Override
	public void setYearlyEstimateTimeSetting(
			List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting) {

		// target classification yearly
		if(this.isYearly){
			yearlyEstimateTimeSetting.forEach(yearly->{
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					time1st = yearly.getTime().valueAsMinutes();
					break;
				case CONDITION_2ND:
					time2nd = yearly.getTime().valueAsMinutes();
					break;
				case CONDITION_3RD:
					time3rd = yearly.getTime().valueAsMinutes();
					break;
				case CONDITION_4TH:
					time4th = yearly.getTime().valueAsMinutes();
					break;
				case CONDITION_5TH:
					time5th = yearly.getTime().valueAsMinutes();
					break;
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
	 * EstimateTimeSettingSetMemento#setMonthlyEstimateTimeSetting(java.util.
	 * List)
	 */
	@Override
	public void setMonthlyEstimateTimeSetting(
			List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting) {

		// target classification not yearly
		if (!this.isYearly) {
			monthlyEstimateTimeSetting.forEach(monthly -> {
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					time1st = monthly.getTime().valueAsMinutes();
					break;
				case CONDITION_2ND:
					time2nd = monthly.getTime().valueAsMinutes();
					break;
				case CONDITION_3RD:
					time3rd = monthly.getTime().valueAsMinutes();
					break;
				case CONDITION_4TH:
					time4th = monthly.getTime().valueAsMinutes();
					break;
				case CONDITION_5TH:
					time5th = monthly.getTime().valueAsMinutes();
					break;
				}
			});
		}
	}

}
