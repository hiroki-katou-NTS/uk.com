/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;

/**
 * The Class MonthlyEstimateTimeDto.
 */

@Getter
@Setter
public class MonthlyEstimateTimeDto implements EstimateTimeSettingSetMemento{
	
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

	@Override
	public void setTargetClassification(EstimateTargetClassification targetClassification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYearlyEstimateTimeSetting(
			List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting) {
		// TODO Auto-generated method stub
		
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
		monthlyEstimateTimeSetting.forEach(monthly->{
			switch (monthly.getEstimatedCondition()) {
			case CONDITION_1ST:
				time1st = monthly.getTime().v();
				break;
			case CONDITION_2ND:
				time2nd = monthly.getTime().v();
				break;
			case CONDITION_3RD:
				time3rd = monthly.getTime().v();
				break;
			case CONDITION_4TH:
				time4th = monthly.getTime().v();
				break;
			case CONDITION_5TH:
				time5th = monthly.getTime().v();
				break;
			}
		});
		
	}

}
