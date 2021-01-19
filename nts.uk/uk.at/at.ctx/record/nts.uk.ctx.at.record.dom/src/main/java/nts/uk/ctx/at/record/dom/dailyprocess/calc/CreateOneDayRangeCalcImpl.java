package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.CreateOneDayRangeCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Stateless
public class CreateOneDayRangeCalcImpl implements CreateOneDayRangeCalc {

	@Override
	public CalculationRangeOfOneDay createOneDayRange(Optional<PredetemineTimeSetting> predetemineTimeSet,
			IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet,
			WorkType workType, JustCorrectionAtr justCorrectionAtr, Optional<WorkTimeCode> workTimeCode) {
		
		return CalculateDailyRecordServiceImpl.createOneDayCalculationRange(new CalculateDailyRecordServiceImpl.RequireM1() {
			
			@Override
			public Optional<PredetemineTimeSetting> predetemineTimeSetting(String cid, String workTimeCode) {
				return predetemineTimeSet;
			}
		}, integrationOfDaily, commonSet, false, workType, justCorrectionAtr, workTimeCode);
	}

}
