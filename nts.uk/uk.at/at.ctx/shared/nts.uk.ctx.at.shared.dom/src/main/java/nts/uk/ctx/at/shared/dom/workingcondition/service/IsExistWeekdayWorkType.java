package nts.uk.ctx.at.shared.dom.workingcondition.service;

import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 労働条件-平日時の勤務種類が登録されている 
 */
public class IsExistWeekdayWorkType {

	public static boolean isExistWeekDayWorkType(IsExistWeekDayWorkTypeRequire require,String employeeId, DatePeriod period) {
		return require.getWorkingCondition(employeeId, period)
				.map(workingCondition -> workingCondition.getWorkCategory().getWorkType().getWeekdayTimeWTypeCode().v())
				.map(workTypeCode -> require.get(AppContexts.user().companyId(), workTypeCode))
				.isPresent();
	}

	public interface IsExistWeekDayWorkTypeRequire{
		//WorkingConditionItemRepository#getBySidAndPeriodOrderByStrD
		Optional<WorkingConditionItem> getWorkingCondition(String employeeId, DatePeriod period);
		
		Optional<WorkType> get(String companyId, String workTypeCode);
	}
}
