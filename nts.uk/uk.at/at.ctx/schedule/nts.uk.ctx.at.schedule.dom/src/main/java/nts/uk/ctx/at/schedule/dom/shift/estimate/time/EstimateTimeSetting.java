/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import java.util.List;

import lombok.Getter;

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
}
