package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public interface SpecifiedWorkTypeService {

	/**
	 * 指定した勤務種類の件数を返す
	 * @param employeeId
	 * @param workTypeList
	 * @param startDate
	 * @param endDate
	 */
	public DailyWorkTypeList getNumberOfSpecifiedWorkType(String employeeId, List<WorkTypeCode> workTypeList, GeneralDate startDate, GeneralDate endDate);
	
}
