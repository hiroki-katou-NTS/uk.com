package nts.uk.screen.at.app.dailyperformance.correction.month.asynctask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.screen.at.app.dailyperformance.correction.monthflex.DPMonthResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthParamInit {
	private boolean loadAfterCalc = false;
	
	private ParamCommonAsync paramCommonAsync;
	
	private DPCorrectionStateParam dpStateParam;
}
