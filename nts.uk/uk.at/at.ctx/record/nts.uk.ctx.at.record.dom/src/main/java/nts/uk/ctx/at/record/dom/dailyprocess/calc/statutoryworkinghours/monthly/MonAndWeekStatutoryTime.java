package nts.uk.ctx.at.record.dom.dailyprocess.calc.statutoryworkinghours.monthly;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;

@AllArgsConstructor
public class MonAndWeekStatutoryTime {

	//週の時間
	private MonthlyEstimateTime weeklyEstimateTime;
	//月の時間
	private MonthlyEstimateTime monthlyEstimateTime;
	
}
