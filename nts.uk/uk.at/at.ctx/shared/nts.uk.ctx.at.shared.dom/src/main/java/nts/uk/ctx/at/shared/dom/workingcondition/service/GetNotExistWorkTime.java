package nts.uk.ctx.at.shared.dom.workingcondition.service;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetNotExistWorkTime {

	public static Map<DatePeriod, String> getByWeekDay(Require require, String employeeId) {
		// 労働条件から平日時就業時間帯を取り出す処理
		Function<WorkingConditionItemWithPeriod, String> getWorkTimeCode = condition -> {
			return condition.getWorkingConditionItem().getWorkCategory().getWorkTime().getHolidayWork().getWorkTimeCode().get().v();
		};
		return getBy(require, employeeId, getWorkTimeCode);
	}

	public static Map<DatePeriod, String> getByHolidayWork(Require require, String employeeId) {
		// 労働条件から休出時就業時間帯を取り出す処理
		Function<WorkingConditionItemWithPeriod, String> getWorkTimeCode = condition -> {
			return condition.getWorkingConditionItem().getWorkCategory().getWorkTime().getWeekdayTime().getWorkTimeCode().get().v();
		};
		return getBy(require, employeeId, getWorkTimeCode);
	}

	private static Map<DatePeriod, String> getBy(Require require, String employeeId, Function<WorkingConditionItemWithPeriod, String> getWorkTimeCode) {

		//マスタチェックは対象社員の全期間に対するチェックなので、期間はmin-max
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		return require.getWorkingConditions(employeeId, period)
				.stream()
				.filter(wcWithItem -> !require.getWorkTime(getWorkTimeCode.apply(wcWithItem)).isPresent())
				.collect(Collectors.toMap(wcWithItemPeriod -> (DatePeriod)wcWithItemPeriod.getDatePeriod(),
						wcWithItemPeriod -> (String)getWorkTimeCode.apply(wcWithItemPeriod)));
	}

	public interface Require{
		List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period);

		Optional<WorkTimeSetting> getWorkTime(String workTimeCode);
	}
}
