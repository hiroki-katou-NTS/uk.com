/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDaySetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;

/**
 * The Class EstimatedaysDto.
 */

@Getter
@Setter
public class EstimateNumberOfDayDto implements EstimateNumberOfDaySetMemento{
	
	/** The month. */
	private int month;
	
	/** The is yearly. */
	private boolean isYearly; 
	
	/** The days 1st. */
	private double days1st;
	
	/** The days 2nd. */
	private double days2nd;
	
	/** The days 3rd. */
	private double days3rd;
	
	/** The days 4th. */
	private double days4th;
	
	/** The days 5th. */
	private double days5th;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDaySetMemento#setTargetClassification(nts.uk.ctx.at.
	 * schedule.dom.shift.estimate.EstimateTargetClassification)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.
	 * EstimateNumberOfDaySetMemento#setYearlyEstimateNumberOfDaySetting(java.
	 * util.List)
	 */
	@Override
	public void setYearlyEstimateNumberOfDaySetting(
			List<YearlyEstimateNumberOfDay> yearlyEstimateNumberOfDaySetting) {

		// target classification yearly
		if (this.isYearly) {
			yearlyEstimateNumberOfDaySetting.forEach(yearly -> {
				switch (yearly.getEstimatedCondition()) {
				case CONDITION_1ST:
					days1st = yearly.getDays().v();
					break;
				case CONDITION_2ND:
					days2nd = yearly.getDays().v();
					break;
				case CONDITION_3RD:
					days3rd = yearly.getDays().v();
					break;
				case CONDITION_4TH:
					days4th = yearly.getDays().v();
					break;
				case CONDITION_5TH:
					days5th = yearly.getDays().v();
					break;
				}
			});
		}
		
	}

	@Override
	public void setMonthlyEstimateNumberOfDaySetting(
			List<MonthlyEstimateNumberOfDay> monthlyEstimateNumberOfDaySetting) {

		// target classification not yearly
		if (!this.isYearly) {
			monthlyEstimateNumberOfDaySetting.forEach(monthly -> {
				switch (monthly.getEstimatedCondition()) {
				case CONDITION_1ST:
					days1st = monthly.getDays().v();
					break;
				case CONDITION_2ND:
					days2nd = monthly.getDays().v();
					break;
				case CONDITION_3RD:
					days3rd = monthly.getDays().v();
					break;
				case CONDITION_4TH:
					days4th = monthly.getDays().v();
					break;
				case CONDITION_5TH:
					days5th = monthly.getDays().v();
					break;
				}
			});
		}
	}

	

}
