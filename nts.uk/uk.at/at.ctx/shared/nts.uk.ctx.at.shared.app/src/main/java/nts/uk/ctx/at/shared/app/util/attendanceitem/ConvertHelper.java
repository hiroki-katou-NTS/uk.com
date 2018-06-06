package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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
		return range.yearMonthsBetween();
	}
	
	public static List<YearMonth> yearMonthsBetween(YearMonthPeriod range){
		return range.yearMonthsBetween();
	}
	
	public static List<GeneralDate> datesBetween(DatePeriod range){
		return range.datesBetween();
	}
	
	public static boolean isCollection(Object c) {
	  return Collection.class.isAssignableFrom(c.getClass());
	}
	
	public static boolean isOptional(Object c) {
	  return Optional.class.isAssignableFrom(c.getClass());
	}
}
