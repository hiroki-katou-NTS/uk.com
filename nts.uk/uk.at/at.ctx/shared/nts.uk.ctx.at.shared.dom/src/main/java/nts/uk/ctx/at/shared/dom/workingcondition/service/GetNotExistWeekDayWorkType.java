package nts.uk.ctx.at.shared.dom.workingcondition.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 労働条件-平日時の勤務種類が登録されている 
 */
public class GetNotExistWeekDayWorkType {

	public static Map<DatePeriod, String> get(Require require,String employeeId) {
		//マスタチェックは対象社員の全期間に対するチェックなので、期間はmin-max
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		return require.getWorkingConditions(employeeId, period)
				.stream()
				.filter(wcWithItem -> !require.getWorkType(wcWithItem.getWorkingConditionItem().getWorkCategory().getWorkType().getWeekdayTimeWTypeCode().v()).isPresent())
				.collect(Collectors.toMap(wcWithItemPeriod -> (DatePeriod)wcWithItemPeriod.getDatePeriod(), 
														 wcWithItemPeriod -> (String)wcWithItemPeriod.getWorkingConditionItem().getWorkCategory().getWorkType().getWeekdayTimeWTypeCode().v()))
				;
	}

	public interface Require{
		List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, DatePeriod period);
		
		Optional<WorkType> getWorkType(String workTypeCode);
	}
}
