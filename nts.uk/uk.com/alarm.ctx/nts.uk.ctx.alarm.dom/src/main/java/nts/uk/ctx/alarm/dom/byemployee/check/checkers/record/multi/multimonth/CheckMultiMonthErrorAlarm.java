package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multimonth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multimonth.MultiMonthlyCheckerByEmployee.RequireCheck;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;

/**
 *  複数月のエラーアラームチェック
 */
public class CheckMultiMonthErrorAlarm {
	private List<ErrorAlarmWorkRecordCode> totalErrorAlarms;
//	平均の分母はaverageErrorAlarms.size()
	private List<ErrorAlarmWorkRecordCode> averageErrorAlarms;
	//発生カウント
	private CheckCountMultiMonth countErrorAlarms; 
	
	public Iterable<AlarmRecordByEmployee> check(RequireCheck require, CheckingContextByEmployee context, List<IntegrationOfMonthly> records){
		
        val recordPerYearMonth = records.stream().collect(Collectors.groupingBy(r -> r.getYearMonth()));
        val start = recordPerYearMonth.keySet().stream().min(Comparator.comparing(YearMonth::v)).get();
        val end = recordPerYearMonth.keySet().stream().max(Comparator.comparing(YearMonth::v)).get();
        val period = new YearMonthPeriod(start, end);
		
		val converters = require.getMonthlyRecordToAttendanceItemConverter(records);
		
		List<AlarmRecordByEmployee> errors = new ArrayList<>();
		val countResult = countErrorAlarms.checkByCount(require, context, recordPerYearMonth, period);
		val totalResult = checkByTotalValue(require, context, converters)
				.stream()
				.map(v -> createError(context, period, v))
				.collect(Collectors.toList());
		val totalAverage = checkByAverage(require, context, converters, period)
				.stream()
				.map(v -> createError(context, period, v))
				.collect(Collectors.toList());
		
		errors.addAll(totalResult);
		errors.addAll(totalAverage);
		
		return IteratorUtil.merge(
				countResult,
				() ->{
					return errors.iterator();
				});
	}
	
	private List<ExtraResultMonthly> checkByTotalValue(Require require, CheckingContextByEmployee context, 
			List<MonthlyRecordToAttendanceItemConverter> converters){
		return check(require, converters, totalErrorAlarms, 1.0);
	}
	
	private List<ExtraResultMonthly> checkByAverage(Require require, CheckingContextByEmployee context, 
			List<MonthlyRecordToAttendanceItemConverter> converters, YearMonthPeriod period){
		return check(require, converters, averageErrorAlarms,  (double)period.yearMonthsBetween().size());
	}
	

	private List<ExtraResultMonthly> check(Require require,List<MonthlyRecordToAttendanceItemConverter> converter, 
			List<ErrorAlarmWorkRecordCode> errorAlarmCodes, double denominator){
		
		val errorAlarmFunction = getFunction(converter,denominator);
		
		return require.getExtraResultMonthly(errorAlarmCodes)
				.stream()
				.filter(v -> v.getCheckConMonthly().get().check(errorAlarmFunction).equals(WorkCheckResult.ERROR))
				.collect(Collectors.toList());
	}
	
	private Function<List<Integer>, List<Double>> getFunction(List<MonthlyRecordToAttendanceItemConverter> converter, double denominator){
		return item ->{
			//全月に対して指定された勤怠項目IDの値を取得しにき、各勤怠項目IDごとの合計を求める
			return item.stream().map(itemId -> calcurateTargetValue(converter, itemId, denominator)).collect(Collectors.toList());
		};
	}
	
	private double calcurateTargetValue(List<MonthlyRecordToAttendanceItemConverter> converters, int itemId, double denominator) {
		double sumResult = converters.stream()
				.map(c -> c.convert(Arrays.asList(itemId)))
				.flatMap(List::stream)
				.collect(Collectors.summingDouble(v -> Double.valueOf(v.getValue()))); 
		
		return sumResult/denominator;
	}
	
	private AlarmRecordByEmployee createError(CheckingContextByEmployee context, YearMonthPeriod period, ExtraResultMonthly extraResult){
		
		AlarmListAlarmMessage message = extraResult.getDisplayMessage()
				.map(v -> new AlarmListAlarmMessage(v.v()))
				.orElse(new AlarmListAlarmMessage(""));
		
		return new AlarmRecordByEmployee(
				context.getTargetEmployeeId(), 
				new DateInfo(period), 
				AlarmListCategoryByEmployee.RECORD_MULTI_MONTH, 
				"複数月", 
				"複数月", 
				"複数月", 
				message);
	}
	
	public interface Require extends CheckCountMultiMonth.Require{

		List<ExtraResultMonthly> getExtraResultMonthly(List<ErrorAlarmWorkRecordCode> codes);
		
		List<MonthlyRecordToAttendanceItemConverter> getMonthlyRecordToAttendanceItemConverter(List<IntegrationOfMonthly> monthlyRecords);
	}
}
