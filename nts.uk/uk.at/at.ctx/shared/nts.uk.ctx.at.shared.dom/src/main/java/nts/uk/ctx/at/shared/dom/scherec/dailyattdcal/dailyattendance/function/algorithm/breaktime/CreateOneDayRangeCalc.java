package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface CreateOneDayRangeCalc {

	CalculationRangeOfOneDay createOneDayRange(
			IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet, 
			WorkType workType, JustCorrectionAtr justCorrectionAtr, Optional<WorkTimeCode> workTimeCode);
}
