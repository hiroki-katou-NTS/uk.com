package nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/**
 * 期間中に発生している特定のエラーメッセージを取得する
 */
public class GetEmployeeDailyPerErrorMessage {
	
	public static List<EmployeeDailyPerError> getEmployeeDailyPerError(Require require, String employeeId, DatePeriod period, ErrorAlarmWorkRecordCode targetErrorAlarmCode) {
		return require.getErrors(employeeId, period,  targetErrorAlarmCode.v())
				.stream()
				.collect(Collectors.toList());
	}
	
	public interface Require{
		//JpaEmployeeDailyPerErrorRepository#findsByCodeLst が使える
		List<EmployeeDailyPerError> getErrors(String employeeId, DatePeriod period, String code);
	}
}
