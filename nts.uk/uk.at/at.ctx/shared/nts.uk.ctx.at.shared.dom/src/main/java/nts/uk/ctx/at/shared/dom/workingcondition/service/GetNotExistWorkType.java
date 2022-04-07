package nts.uk.ctx.at.shared.dom.workingcondition.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 労働条件-勤務種類が登録されている
 */
public class GetNotExistWorkType {

	public static Map<DatePeriod, String> getByWeekDay(Require require,String employeeId) {
		// 労働条件から平日時勤務種類を取り出す処理
		Function<WorkingConditionItemWithPeriod, String> getWorkTypeCode = condition -> {
			return condition.getWorkingConditionItem().getWorkCategory().getWorkType().getWeekdayTimeWTypeCode().v();
		};
		return getBy(require, employeeId, getWorkTypeCode);
	}

	public static Map<DatePeriod, String> getByHolidayWork(Require require,String employeeId) {
		// 労働条件から休出時勤務種類を取り出す処理
		Function<WorkingConditionItemWithPeriod, String> getWorkTypeCode = condition -> {
			return condition.getWorkingConditionItem().getWorkCategory().getWorkType().getHolidayWorkWTypeCode().v();
		};
		return getBy(require, employeeId, getWorkTypeCode);
	}

	public static Map<DatePeriod, String> getByHoliday(Require require,String employeeId) {
		// 労働条件から休出時勤務種類を取り出す処理
		Function<WorkingConditionItemWithPeriod, String> getWorkTypeCode = condition -> {
			return condition.getWorkingConditionItem().getWorkCategory().getWorkType().getHolidayTimeWTypeCode().v();
		};
		return getBy(require, employeeId, getWorkTypeCode);
	}

	private static Map<DatePeriod, String> getBy(Require require, String employeeId, Function<WorkingConditionItemWithPeriod, String> getWorkTypeCode) {

		//マスタチェックは対象社員の全期間に対するチェックなので、期間はmin-max
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		return require.getWorkingConditions(employeeId, period)
				.stream()
				.filter(wcWithItem -> !require.getWorkType(getWorkTypeCode.apply(wcWithItem)).isPresent())
				.collect(Collectors.toMap(wcWithItemPeriod -> (DatePeriod)wcWithItemPeriod.getDatePeriod(),
						wcWithItemPeriod -> (String)getWorkTypeCode.apply(wcWithItemPeriod)));
	}

	public interface Require{
		List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period);
		
		Optional<WorkType> getWorkType(String workTypeCode);
	}
}
