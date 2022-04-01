package nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/**
 * 期間中に発生している特定のエラーメッセージを取得する
 */
public class GetEmployeeDailyPerErrorMessage {
	
	public static List<EmployeeDailyPerError> getEmployeeDailyPerError(HasErrorAlarmRequire require, String employeeId, DatePeriod period, List<ErrorAlarmWorkRecordCode> targetErrorAlarmCode) {
		val strCode = targetErrorAlarmCode.stream().map(code -> code.v()).collect(Collectors.toList());
		return require.getErrors(employeeId, period,  strCode)
				.stream()
				.collect(Collectors.toList());
	}
	
	public interface HasErrorAlarmRequire{
		//JpaEmployeeDailyPerErrorRepository#findsByCodeLst が使える
		List<EmployeeDailyPerError> getErrors(String employeeId, DatePeriod period, List<String> code);
	}
}
