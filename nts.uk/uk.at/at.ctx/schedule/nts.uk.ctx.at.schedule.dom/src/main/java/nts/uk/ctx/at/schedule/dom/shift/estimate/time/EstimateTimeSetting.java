/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;

/**
 * The Class EstimateTimeSetting.
 */
// 目安時間設定
@Getter
public class EstimateTimeSetting {

	/** The target classification. */
	// 対象区分
	private EstimateTargetClassification targetClassification;
	
	/** The yearly estimate time setting. */
	// 年間目安時間設定
	private List<YearlyEstimateTimeSetting> yearlyEstimateTimeSetting;
	
	/** The monthly estimate time setting. */
	// 月間目安時間設定
	private List<MonthlyEstimateTimeSetting> monthlyEstimateTimeSetting;
	
	
	/**
	 * Instantiates a new estimate time setting.
	 *
	 * @param memento the memento
	 */
	public EstimateTimeSetting(EstimateTimeSettingGetMemento memento){
		this.targetClassification = memento.getTargetClassification();
		this.yearlyEstimateTimeSetting = memento.getYearlyEstimateTimeSetting();
		this.monthlyEstimateTimeSetting = memento.getMonthlyEstimateTimeSetting();
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
