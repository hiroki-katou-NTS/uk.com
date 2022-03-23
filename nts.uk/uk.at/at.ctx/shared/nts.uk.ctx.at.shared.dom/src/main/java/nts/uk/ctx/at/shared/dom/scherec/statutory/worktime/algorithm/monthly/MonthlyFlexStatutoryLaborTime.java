package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

/**
 * Output：月の法定労働時間（フレックス用）
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MonthlyFlexStatutoryLaborTime {

	/** 法定時間 */
	private MonthlyEstimateTime statutorySetting = new MonthlyEstimateTime(0);
	/** 所定時間 */
	private MonthlyEstimateTime specifiedSetting = new MonthlyEstimateTime(0);
	/** 週平均時間 */
	private MonthlyEstimateTime weekAveSetting = new MonthlyEstimateTime(0);
}
