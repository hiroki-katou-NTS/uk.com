package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.anyperiod;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/**
 * 任意期間のエラーアラームチェック 
 */
@Value
public class CheckAnyPeriodErrorAlarm {
	
	private List<ErrorAlarmWorkRecordCode> rawErrorAlarms;
	private List<ErrorAlarmWorkRecordCode> averageErrorAlarms;
	
	public List<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context, AttendanceTimeOfAnyPeriod record){
		val converter = require.getAnyPeriodRecordToAttendanceItemConverter(context.getTargetEmployeeId(), record);
		
		val averageResult = checkByAverage(require, context, converter, record);
		val rawResult = checkByRaw(require, context, converter);
		
		return Stream.concat(rawResult.stream(), averageResult.stream())
					.map(error -> createError(require, context, error))
					.collect(Collectors.toList());
	}
	
	private List<ErrorAlarmAnyPeriod> checkByRaw(Require require, CheckingContextByEmployee context, 
			AnyPeriodRecordToAttendanceItemConverter converter){
		return check(require, converter, rawErrorAlarms, 1.0);
	}
	
	private List<ErrorAlarmAnyPeriod> checkByAverage(Require require, CheckingContextByEmployee context, 
			AnyPeriodRecordToAttendanceItemConverter converter, AttendanceTimeOfAnyPeriod record){
		val attendanceDays = record.getVerticalTotal().getWorkDays().getAttendanceDays().getDays().v();
		return check(require, converter, averageErrorAlarms, attendanceDays);
	}
	

	private List<ErrorAlarmAnyPeriod> check(Require require,AnyPeriodRecordToAttendanceItemConverter converter, 
			List<ErrorAlarmWorkRecordCode> errorAlarmCodes, double denominator){
		
		val errorAlarmFunction = getFunction(converter,denominator);
		
		return require.getErrorAlarmAnyPeriod(errorAlarmCodes)
				.stream()
				.filter(v -> v.getCondition().check(errorAlarmFunction).equals(WorkCheckResult.ERROR))
				.collect(Collectors.toList())
				;
	}
	
	/**
	 *エラーアラームで使うFunctionの生成 
	 * @param denominator 勤怠項目の値をこの数値で割る
	 */
	private Function<List<Integer>, List<Double>> getFunction(AnyPeriodRecordToAttendanceItemConverter converter, double denominator){
		return item ->{
			val itemValues = converter.convert(item);
			return itemValues.stream()
										 .map(v -> Double.valueOf(v.getValue())/denominator)
										 .collect(Collectors.toList());
		};
	}
	
	private AlarmRecordByEmployee createError(Require require, CheckingContextByEmployee context, ErrorAlarmAnyPeriod error) {
		
		AlarmListAlarmMessage errorMessage = error.getMessage().isPresent()
				? new AlarmListAlarmMessage(error.getMessage().get().toString())
				: new AlarmListAlarmMessage("");
		
		return new AlarmRecordByEmployee(
				context.getTargetEmployeeId(), 
				new DateInfo(require.getAnyAggrPeriod(context.getCheckingPeriod().getAnyPeriod()).getPeriod()), 
				AlarmListCategoryByEmployee.RECORD_ANY_PERIOD, 
				"任意期間のエラーアラーム", 
				"任意期間のエラーアラーム", 
				"任意期間のエラーアラーム", 
				errorMessage);
	}
	
	public interface Require{
		List<ErrorAlarmAnyPeriod> getErrorAlarmAnyPeriod(List<ErrorAlarmWorkRecordCode> code);
		AnyPeriodRecordToAttendanceItemConverter getAnyPeriodRecordToAttendanceItemConverter(String employeeId, AttendanceTimeOfAnyPeriod record);
		AnyAggrPeriod getAnyAggrPeriod(AnyAggrFrameCode code);
	}
}
