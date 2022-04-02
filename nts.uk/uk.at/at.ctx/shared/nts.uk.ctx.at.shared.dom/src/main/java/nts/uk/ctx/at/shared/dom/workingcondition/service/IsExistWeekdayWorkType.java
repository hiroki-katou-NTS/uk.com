package nts.uk.ctx.at.shared.dom.workingcondition.service;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 労働条件-平日時の勤務種類が登録されている 
 */
public class IsExistWeekdayWorkType {

	public static boolean isExistWeekDayWorkType(IsExistWeekDayWorkTypeRequire require,String employeeId) {
		return require.getWorkingCondition(employeeId)
				.stream()
				.map(workingCondition -> workingCondition.getWorkCategory().getWorkType().getWeekdayTimeWTypeCode().v())
				.allMatch(workTypeCode -> require.get(AppContexts.user().companyId(), workTypeCode).isPresent());
	}

	public interface IsExistWeekDayWorkTypeRequire{
		List<WorkingConditionItem> getWorkingCondition(String employeeId);
		
		Optional<WorkType> get(String companyId, String workTypeCode);
	}
}
