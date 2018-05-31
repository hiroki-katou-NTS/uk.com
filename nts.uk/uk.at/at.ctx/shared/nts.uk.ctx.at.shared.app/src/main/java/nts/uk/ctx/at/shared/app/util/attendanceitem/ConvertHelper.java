package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public class ConvertHelper {

	public static <T, Q> List<T> mapTo(List<Q> original, Function<Q, T> actions) {
		return original == null ? new ArrayList<>() : original.stream().map(actions).collect(Collectors.toList());
	}

	public static <T, Q, X> List<T> mapTo(Map<X, Q> original, Function<Entry<X, Q>, T> actions) {
		return original == null ? new ArrayList<>()
				: original.entrySet().stream().map(actions).collect(Collectors.toList());
	}

	public static <T> T getEnum(Integer value, Class<T> enumClass) {
		if (value == null) {
			return null;
		}
		
		return EnumAdaptor.valueOf(value, enumClass);
	}
	
	public static List<YearMonth> yearMonthsBetween(DatePeriod range){
		List<YearMonth> result = new ArrayList<>();
		YearMonth start = range.start().yearMonth();
		YearMonth end = range.end().yearMonth();
		while (start.lessThanOrEqualTo(end)) {
			result.add(start);
			start = start.addMonths(1);
		}
		return result;
	}
	
	public static List<YearMonth> yearMonthsBetween(YearMonthPeriod range){
		List<YearMonth> result = new ArrayList<>();
		YearMonth start = range.start();
		YearMonth end = range.end();
		while (start.lessThanOrEqualTo(end)) {
			result.add(start);
			start = start.addMonths(1);
		}
		return result;
	}
	
	public static List<GeneralDate> datesBetween(DatePeriod range){
		List<GeneralDate> result = new ArrayList<>();
		GeneralDate start = range.start();
		while (start.beforeOrEquals(range.end())) {
			result.add(start);
			start = start.addDays(1);
		}
		return result;
	}
}
