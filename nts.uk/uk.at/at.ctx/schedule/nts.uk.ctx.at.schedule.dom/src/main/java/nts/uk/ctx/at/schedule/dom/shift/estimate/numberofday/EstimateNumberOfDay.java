/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimatedCondition;

/**
 * The Class EstimateNumberOfDay.
 */
// 目安日数設定
@Getter
public class EstimateNumberOfDay extends DomainObject{
	
	/** The target classification. */
	// 対象区分
	private EstimateTargetClassification targetClassification;

	
	/** The yearly estimate number of day setting. */
	// 年間目安日数設定
	private List<YearlyEstimateNumberOfDay> yearlyEstimateNumberOfDaySetting;
	
	/** The monthly estimate number of day setting. */
	// 月間目安日数設定
	private List<MonthlyEstimateNumberOfDay> monthlyEstimateNumberOfDaySetting;
	
	/** The next index. */
	private static final int NEXT_INDEX = 1;
	
	/** The Constant ZERO_VALUE. */
	private static final int ZERO_VALUE = 0;
	
	
	/**
	 * Instantiates a new estimate number of day.
	 *
	 * @param memento the memento
	 */
	public EstimateNumberOfDay(EstimateNumberOfDayGetMemento memento){
		this.targetClassification = memento.getTargetClassification();
		this.yearlyEstimateNumberOfDaySetting = memento.getYearlyEstimateNumberOfDaySetting();
		this.monthlyEstimateNumberOfDaySetting = memento.getMonthlyEstimateNumberOfDaySetting();
		
	// validate

		// target yearly
		if (this.targetClassification.value == EstimateTargetClassification.YEARLY.value) {
			
			// convert to map
			Map<Integer, YearlyEstimateNumberOfDay> mapYearly = this.yearlyEstimateNumberOfDaySetting
					.stream().collect(Collectors.toMap((yearly) -> {
						return yearly.getEstimatedCondition().value;
					}, Function.identity()));

			// check validate of 1st to 4th
			for (int indexCondition = EstimatedCondition.CONDITION_1ST.value; 
					indexCondition <= EstimatedCondition.CONDITION_4TH.value; indexCondition++) {
				YearlyEstimateNumberOfDay yearlyNow = mapYearly.get(indexCondition);
				YearlyEstimateNumberOfDay yearlyNext = mapYearly.get(indexCondition + NEXT_INDEX);
				if (yearlyNow.getDays().v() != ZERO_VALUE && yearlyNext.getDays().v() != ZERO_VALUE
						&& yearlyNext.getDays().v() < yearlyNow.getDays().v()) {
					throw new BusinessException("Msg_147", "KSM001_24");
				}
				if (yearlyNow.getDays().v() == ZERO_VALUE
						&& yearlyNext.getDays().v() > ZERO_VALUE) {
					throw new BusinessException("Msg_182", "KSM001_24");
				}
			}
		} else {
			
			// convert to map
			Map<Integer, MonthlyEstimateNumberOfDay> mapMonthly = this.monthlyEstimateNumberOfDaySetting
					.stream().collect(Collectors.toMap((monthly) -> {
						return monthly.getEstimatedCondition().value;
					}, Function.identity()));

			// check validate of 1st to 4th
			for (int indexCondition = EstimatedCondition.CONDITION_1ST.value; 
					indexCondition <= EstimatedCondition.CONDITION_4TH.value; indexCondition++) {
				MonthlyEstimateNumberOfDay monthlyNow = mapMonthly.get(indexCondition);
				MonthlyEstimateNumberOfDay monthlyNext = mapMonthly
						.get(indexCondition + NEXT_INDEX);
				if (monthlyNow.getDays().v() != ZERO_VALUE
						&& monthlyNext.getDays().v() != ZERO_VALUE
						&& monthlyNext.getDays().v() < monthlyNow.getDays().v()) {
					throw new BusinessException("Msg_147", "KSM001_24");
				}
				if (monthlyNow.getDays().v() == ZERO_VALUE
						&& monthlyNext.getDays().v() > ZERO_VALUE) {
					throw new BusinessException("Msg_182", "KSM001_24");
				}
			}
		}
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EstimateNumberOfDaySetMemento memento){
		memento.setTargetClassification(this.targetClassification);
		memento.setYearlyEstimateNumberOfDaySetting(this.yearlyEstimateNumberOfDaySetting);
		memento.setMonthlyEstimateNumberOfDaySetting(this.monthlyEstimateNumberOfDaySetting);
	}
}
