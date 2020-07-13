package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateDailyRecordServiceCenterNew;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

@Stateless
public class CalculateDailyServiceFromShared implements CalculateDailyRecordServiceCenterNew{

	@Inject
	private DailyCalculationEmployeeServiceImpl impl;
	
	@Override
	public List<Boolean> calculate(List<String> employeeId, DatePeriod datePeriod,
			ExecutionType reCalcAtr, String empCalAndSumExecLogID, Boolean isCalWhenLock) {
		return impl.calculate(employeeId, datePeriod, null, ExecutionType.NORMAL_EXECUTION, empCalAndSumExecLogID, isCalWhenLock);
	}

}
