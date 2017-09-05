/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

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
 * The Class EstimateTimeSetting.
 */
// 目安時間設定
@Getter
public class EstimateTimeSetting extends DomainObject{

	/** The target classification. */
	// 対象区分
	private EstimateTargetClassification targetClassification;
	
	/** The yearly estimate time setting. */
	// 年間目安時間設定
	private List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting;
	
	/** The monthly estimate time setting. */
	// 月間目安時間設定
	private List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting;
	
	/** The next index. */
	private static final int NEXT_INDEX = 1;
	
	/** The Constant ZERO_VALUE. */
	private static final int ZERO_VALUE = 0;
	
	
	/**
	 * Instantiates a new estimate time setting.
	 *
	 * @param memento the memento
	 */
	public EstimateTimeSetting(EstimateTimeSettingGetMemento memento) {
		this.targetClassification = memento.getTargetClassification();
		this.yearlyEstimateTimeSetting = memento.getYearlyEstimateTimeSetting();
		this.monthlyEstimateTimeSetting = memento.getMonthlyEstimateTimeSetting();

		// validate

		// target yearly
		if (this.targetClassification.value == EstimateTargetClassification.YEARLY.value) {
			Map<Integer, YearlyEstimateTimeSetting> mapYearly = this.yearlyEstimateTimeSetting
					.stream().collect(Collectors.toMap((yearly) -> {
						return yearly.getEstimatedCondition().value;
					}, Function.identity()));

			// check validate of 1st to 4th
			for (int indexCondition = EstimatedCondition.CONDITION_1ST.value; 
					indexCondition <= EstimatedCondition.CONDITION_4TH.value; indexCondition++) {
				YearlyEstimateTimeSetting yearlyNow = mapYearly.get(indexCondition);
				YearlyEstimateTimeSetting yearlyNext = mapYearly.get(indexCondition + NEXT_INDEX);
				if (yearlyNow.getTime().v() != ZERO_VALUE && yearlyNext.getTime().v() != ZERO_VALUE
						&& yearlyNext.getTime().v() < yearlyNow.getTime().v()) {
					throw new BusinessException("Msg_147", "KSM001_24");
				}
				if (yearlyNow.getTime().v() == ZERO_VALUE
						&& yearlyNext.getTime().v() > ZERO_VALUE) {
					throw new BusinessException("Msg_182", "KSM001_24");
				}
			}
		} else {
			Map<Integer, MonthlyEstimateTimeSetting> mapMonthly = this.monthlyEstimateTimeSetting
					.stream().collect(Collectors.toMap((monthly) -> {
						return monthly.getEstimatedCondition().value;
					}, Function.identity()));

			// check validate of 1st to 4th
			for (int indexCondition = EstimatedCondition.CONDITION_1ST.value; 
					indexCondition <= EstimatedCondition.CONDITION_4TH.value; indexCondition++) {
				MonthlyEstimateTimeSetting monthlyNow = mapMonthly.get(indexCondition);
				MonthlyEstimateTimeSetting monthlyNext = mapMonthly
						.get(indexCondition + NEXT_INDEX);
				if (monthlyNow.getTime().v() != ZERO_VALUE
						&& monthlyNext.getTime().v() != ZERO_VALUE
						&& monthlyNext.getTime().v() < monthlyNow.getTime().v()) {
					throw new BusinessException("Msg_147", "KSM001_24");
				}
				if (monthlyNow.getTime().v() == ZERO_VALUE
						&& monthlyNext.getTime().v() > ZERO_VALUE) {
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
	public void saveToMemento(EstimateTimeSettingSetMemento memento){
		memento.setTargetClassification(this.targetClassification);
		memento.setYearlyEstimateTimeSetting(this.yearlyEstimateTimeSetting);
		memento.setMonthlyEstimateTimeSetting(this.monthlyEstimateTimeSetting);
	}
	
}
