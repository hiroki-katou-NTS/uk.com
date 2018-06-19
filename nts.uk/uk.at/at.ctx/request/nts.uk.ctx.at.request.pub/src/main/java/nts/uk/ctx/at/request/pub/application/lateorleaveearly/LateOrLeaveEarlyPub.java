package nts.uk.ctx.at.request.pub.application.lateorleaveearly;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface LateOrLeaveEarlyPub {
	
	/**
	 * request list #446
	 * 遅刻早退取消表示を返す
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<LateOrLeaveEarlyExport> engravingCancelLateorLeaveearly(String employeeID, GeneralDate startDate, GeneralDate endDate);
	
}
