package nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author phongtq
 *
 */
public interface DailyProcessRemandPub {

	/**
	 * RequestList233
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<DailyAggregationProcessExport> findByIDRemand(List<String> employeeID, GeneralDate startDate,
			GeneralDate endDate);

}
