/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.EstimateNumberOfDayGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.MonthlyEstimateNumberOfDay;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateDays;
import nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday.YearlyEstimateNumberOfDay;

/**
 * The Class CompanyEstimateNumberOfDayDto.
 */
@Getter
@Setter
public class EstablishmentNumberOfDayDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The monthly estimates. */
	private List<EstimateNumberOfDayDto> monthlyEstimates;

	/** The yearly estimate. */
	private EstimateNumberOfDayDto yearlyEstimate;
	
	
	/**
	 * To yearly.
	 *
	 * @return the estimate number of day
	 */
	public EstimateNumberOfDay toYearly() {
		
		return new EstimateNumberOfDay(new EstimateNumberOfDayGetMemento() {

			/**
			 * Gets the yearly estimate number of day setting.
			 *
			 * @return the yearly estimate number of day setting
			 */
			@Override
			public List<YearlyEstimateNumberOfDay> getYearlyEstimateNumberOfDaySetting() {
				
				List<YearlyEstimateNumberOfDay> yearlyEstimateDaysSetting = new ArrayList<>();
				yearlyEstimateDaysSetting.add(new YearlyEstimateNumberOfDay(
						new YearlyEstimateDays(yearlyEstimate.getDays1st()),
						EstimatedCondition.CONDITION_1ST));
				yearlyEstimateDaysSetting.add(new YearlyEstimateNumberOfDay(
						new YearlyEstimateDays(yearlyEstimate.getDays2nd()),
						EstimatedCondition.CONDITION_2ND));
				yearlyEstimateDaysSetting.add(new YearlyEstimateNumberOfDay(
						new YearlyEstimateDays(yearlyEstimate.getDays3rd()),
						EstimatedCondition.CONDITION_3RD));
				yearlyEstimateDaysSetting.add(new YearlyEstimateNumberOfDay(
						new YearlyEstimateDays(yearlyEstimate.getDays4th()),
						EstimatedCondition.CONDITION_4TH));
				yearlyEstimateDaysSetting.add(new YearlyEstimateNumberOfDay(
						new YearlyEstimateDays(yearlyEstimate.getDays5th()),
						EstimatedCondition.CONDITION_5TH));
				return yearlyEstimateDaysSetting;
			}

			/**
			 * Gets the target classification.
			 *
			 * @return the target classification
			 */
			@Override
			public EstimateTargetClassification getTargetClassification() {
				return EstimateTargetClassification.YEARLY;
			}

			/**
			 * Gets the monthly estimate number of day setting.
			 *
			 * @return the monthly estimate number of day setting
			 */
			@Override
			public List<MonthlyEstimateNumberOfDay> getMonthlyEstimateNumberOfDaySetting() {
				return new ArrayList<>();
			}
		});
			
	}
	
	
	public List<EstimateNumberOfDay> toListMonthly(){
		List<EstimateNumberOfDay> estimateDaysSettings = new ArrayList<>();
		monthlyEstimates.forEach(monthly->{
			estimateDaysSettings.add(new EstimateNumberOfDay(new EstimateNumberOfDayGetMemento() {
				
				/**
				 * Gets the yearly estimate number of day setting.
				 *
				 * @return the yearly estimate number of day setting
				 */
				@Override
				public List<YearlyEstimateNumberOfDay> getYearlyEstimateNumberOfDaySetting() {
					return new ArrayList<>();
				}
				
				/**
				 * Gets the target classification.
				 *
				 * @return the target classification
				 */
				@Override
				public EstimateTargetClassification getTargetClassification() {
					return EstimateTargetClassification.valueOf(monthly.getMonth());
				}
				
				/**
				 * Gets the monthly estimate number of day setting.
				 *
				 * @return the monthly estimate number of day setting
				 */
				@Override
				public List<MonthlyEstimateNumberOfDay> getMonthlyEstimateNumberOfDaySetting() {
					List<MonthlyEstimateNumberOfDay> monthlyEstimateDaysSetting = new ArrayList<>();
					monthlyEstimateDaysSetting.add(new MonthlyEstimateNumberOfDay(
							new MonthlyEstimateDays(monthly.getDays1st()),
							EstimatedCondition.CONDITION_1ST));
					monthlyEstimateDaysSetting.add(new MonthlyEstimateNumberOfDay(
							new MonthlyEstimateDays(monthly.getDays2nd()),
							EstimatedCondition.CONDITION_2ND));
					monthlyEstimateDaysSetting.add(new MonthlyEstimateNumberOfDay(
							new MonthlyEstimateDays(monthly.getDays3rd()),
							EstimatedCondition.CONDITION_3RD));
					monthlyEstimateDaysSetting.add(new MonthlyEstimateNumberOfDay(
							new MonthlyEstimateDays(monthly.getDays4th()),
							EstimatedCondition.CONDITION_4TH));
					monthlyEstimateDaysSetting.add(new MonthlyEstimateNumberOfDay(
							new MonthlyEstimateDays(monthly.getDays5th()),
							EstimatedCondition.CONDITION_5TH));
					return monthlyEstimateDaysSetting;
				}
			}));
		});
		return estimateDaysSettings;
	}
}
