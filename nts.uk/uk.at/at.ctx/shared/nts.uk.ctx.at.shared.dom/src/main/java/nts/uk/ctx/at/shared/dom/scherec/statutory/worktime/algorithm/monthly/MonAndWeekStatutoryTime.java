package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

@AllArgsConstructor
@Getter
public class MonAndWeekStatutoryTime {

	//週の時間
	private MonthlyEstimateTime weeklyEstimateTime;
	//月の時間
	private MonthlyEstimateTime monthlyEstimateTime;
	
	
}
