package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.CreateOneDayRangeCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Stateless
public class CreateOneDayRangeCalcImpl implements CreateOneDayRangeCalc {

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSetRepo;
	
	@Override
	public CalculationRangeOfOneDay createOneDayRange(
			IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet,
			WorkType workType, JustCorrectionAtr justCorrectionAtr, Optional<WorkTimeCode> workTimeCode) {
		
		return CalculateDailyRecordServiceImpl.createOneDayCalculationRange(
				new CalculateDailyRecordServiceImpl.RequireM1() {
					@Override
					public Optional<PredetemineTimeSetting> predetemineTimeSetting(String cid, WorkTimeCode workTimeCode) {
						return predetemineTimeSetRepo.findByWorkTimeCode(cid, workTimeCode.v());
					}
				},
				integrationOfDaily, commonSet, false, workType, justCorrectionAtr, workTimeCode);
	}

}
