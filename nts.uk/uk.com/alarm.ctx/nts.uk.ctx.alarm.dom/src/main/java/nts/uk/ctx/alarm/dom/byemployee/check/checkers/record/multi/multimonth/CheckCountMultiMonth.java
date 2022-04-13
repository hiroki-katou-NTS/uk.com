package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multimonth;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounter;
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
 * 複数月のエラーカウント 
 */
@Value
public class CheckCountMultiMonth {
	private List<ErrorAlarmCounter<ErrorAlarmWorkRecordCode, YearMonth>> counterErrorAlarms;
	
	public Iterable<AlarmRecordByEmployee> checkByCount(RequireCheck require, CheckingContextByEmployee context,
			Map<YearMonth, List<IntegrationOfMonthly>> recordPerYearMonth, YearMonthPeriod period) {
		
        BiFunction<YearMonth, YearMonth, DateInfo> periodToDateInfo = (start, end) -> {
            return new DateInfo(new YearMonthPeriod(start, end));
        };

        Function<ErrorAlarmWorkRecordCode, Optional<String>> getErrorAlarmName = code -> {
            return Optional.of("勤怠項目の名前");
        };


        
        Function<ErrorAlarmWorkRecordCode, Iterable<YearMonth>> errorAlarmChecker = code -> {
            return IteratorUtil.iterableFilter(period.iterate(), yearMonth -> {
            	
            	//1か月分の月次データ
            	val recordMonth = recordPerYearMonth.get(yearMonth);
            	//Converter生成
            	val converter = require.getMonthlyRecordToAttendanceItemConverter(recordMonth);
            	val func = getFunction(require, converter, 1.0);
            	val result = require.getExtraResultMonthly(code).getCheckConMonthly().get().check(func);
                return result.equals(WorkCheckResult.ERROR) ? Optional.of(yearMonth) : Optional.empty();
            });
        };
		
		return IteratorUtil.iterableFlatten(
				counterErrorAlarms,
                c -> c.check(context.getTargetEmployeeId(), 
                					  period, 
                					  AlarmListCategoryByEmployee.RECORD_MULTI_MONTH, 
                					  getErrorAlarmName, 
                					  periodToDateInfo, 
                					  errorAlarmChecker
                ));
	}
	
	private Function<List<Integer>, List<Double>> getFunction(Require require, List<MonthlyRecordToAttendanceItemConverter> monthlyRecords, double denominator){
		return item ->{
			//全月に対して指定された勤怠項目IDの値を取得しにき、各勤怠項目IDごとの合計を求める
			return item.stream().map(itemId -> calcurateTargetValue(monthlyRecords, itemId, denominator)).collect(Collectors.toList());
		};
	}
	
	private double calcurateTargetValue(List<MonthlyRecordToAttendanceItemConverter> converters, int itemId, double denominator) {
		double sumResult = converters.stream()
				.map(c -> c.convert(Arrays.asList(itemId)))
				.flatMap(List::stream)
				.collect(Collectors.summingDouble(v -> Double.valueOf(v.getValue()))); 
		
		return sumResult/denominator;
	}
	
	
	public interface Require{

		ExtraResultMonthly getExtraResultMonthly(ErrorAlarmWorkRecordCode codes);
		
		List<MonthlyRecordToAttendanceItemConverter> getMonthlyRecordToAttendanceItemConverter(List<IntegrationOfMonthly> monthlyRecords);
	}

}
