package nts.uk.ctx.at.record.pub.specifiedworktype;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface DailyWorkTypeListPub {
	
	/**
 	 * RequestList #328
	 * @param employeeId
	 * @param workTypeList
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	DailyWorkTypeListExport getDailyWorkTypeUsed(String employeeId, List<String> workTypeList, GeneralDate startDate, GeneralDate endDate);

}
