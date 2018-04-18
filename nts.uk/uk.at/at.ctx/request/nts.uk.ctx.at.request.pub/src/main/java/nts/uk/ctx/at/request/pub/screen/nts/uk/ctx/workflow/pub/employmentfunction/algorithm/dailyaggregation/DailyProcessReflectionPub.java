package nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author phongtq
 *
 */
public interface DailyProcessReflectionPub {

	/**
	 * RequestList232
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return List<DailyAggregationProcessExport>
	 */
	List<DailyAggregationProcessExport> findByIDReflec(List<String> employeeID, GeneralDate startDate, GeneralDate endDate);
	
}
