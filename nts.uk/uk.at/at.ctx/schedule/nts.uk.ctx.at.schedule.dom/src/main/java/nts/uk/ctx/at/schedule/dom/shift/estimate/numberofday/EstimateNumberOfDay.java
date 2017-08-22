/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTargetClassification;

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
}
