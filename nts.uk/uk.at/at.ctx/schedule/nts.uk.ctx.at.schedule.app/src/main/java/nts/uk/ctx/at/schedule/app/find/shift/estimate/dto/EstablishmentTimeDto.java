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
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTime;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.MonthlyEstimateTimeSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTime;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.YearlyEstimateTimeSetting;

/**
 * The Class CompanyEstimateTimeDto.
 */
@Getter
@Setter
public class EstablishmentTimeDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The monthly estimates. */
	private List<EstimateTimeDto> monthlyEstimates;

	/** The yearly estimate. */
	private EstimateTimeDto yearlyEstimate;
	
	
	/**
	 * To yearly.
	 *
	 * @return the estimate time setting
	 */
	public EstimateTimeSetting toYearly() {
		return new EstimateTimeSetting(new EstimateTimeSettingGetMemento() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
			 * EstimateTimeSettingGetMemento#getYearlyEstimateTimeSetting()
			 */
			@Override
			public List<YearlyEstimateTimeSetting> getYearlyEstimateTimeSetting() {
				List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting = new ArrayList<>();
				yearlyEstimateTimeSetting
						.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_1ST,
								new YearlyEstimateTime(yearlyEstimate.getTime1st())));
				yearlyEstimateTimeSetting
				.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_2ND,
						new YearlyEstimateTime(yearlyEstimate.getTime2nd())));
				yearlyEstimateTimeSetting
				.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_3RD,
						new YearlyEstimateTime(yearlyEstimate.getTime3rd())));
				yearlyEstimateTimeSetting
				.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_4TH,
						new YearlyEstimateTime(yearlyEstimate.getTime4th())));
				yearlyEstimateTimeSetting
				.add(new YearlyEstimateTimeSetting(EstimatedCondition.CONDITION_5TH,
						new YearlyEstimateTime(yearlyEstimate.getTime5th())));
				return yearlyEstimateTimeSetting;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
			 * EstimateTimeSettingGetMemento#getTargetClassification()
			 */
			@Override
			public EstimateTargetClassification getTargetClassification() {
				return EstimateTargetClassification.YEARLY;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.time.
			 * EstimateTimeSettingGetMemento#getMonthlyEstimateTimeSetting()
			 */
			@Override
			public List<MonthlyEstimateTimeSetting> getMonthlyEstimateTimeSetting() {
				return new ArrayList<>();
			}
		});
	}
	
	
	public List<EstimateTimeSetting> toListMonthly(){
		List<EstimateTimeSetting> estimateTimeSettings = new ArrayList<>();
		monthlyEstimates.forEach(monthly->{
			estimateTimeSettings.add(new EstimateTimeSetting(new EstimateTimeSettingGetMemento() {
				
				/**
				 * Gets the yearly estimate time setting.
				 *
				 * @return the yearly estimate time setting
				 */
				@Override
				public List<YearlyEstimateTimeSetting> getYearlyEstimateTimeSetting() {
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
				 * Gets the monthly estimate time setting.
				 *
				 * @return the monthly estimate time setting
				 */
				@Override
				public List<MonthlyEstimateTimeSetting> getMonthlyEstimateTimeSetting() {
					List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting = new ArrayList<>();
					monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
							new MonthlyEstimateTime(monthly.getTime1st()),
							(EstimatedCondition.CONDITION_1ST)));
					monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
							new MonthlyEstimateTime(monthly.getTime2nd()),
							(EstimatedCondition.CONDITION_2ND)));
					monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
							new MonthlyEstimateTime(monthly.getTime3rd()),
							(EstimatedCondition.CONDITION_3RD)));
					monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
							new MonthlyEstimateTime(monthly.getTime4th()),
							(EstimatedCondition.CONDITION_4TH)));
					monthlyEstimateTimeSetting.add(new MonthlyEstimateTimeSetting(
							new MonthlyEstimateTime(monthly.getTime5th()),
							(EstimatedCondition.CONDITION_5TH)));
					return monthlyEstimateTimeSetting;
				}
			}));
		});
		return estimateTimeSettings;
	}
}
