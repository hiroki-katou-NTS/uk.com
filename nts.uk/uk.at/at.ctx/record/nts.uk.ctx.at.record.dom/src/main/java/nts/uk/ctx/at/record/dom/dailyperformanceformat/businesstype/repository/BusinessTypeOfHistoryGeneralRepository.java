package nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * General history work type
 * 
 * @author Trung Tran
 *
 */
public interface BusinessTypeOfHistoryGeneralRepository {
	/**
	 * add new history and update last history
	 * 
	 * @param bEmployeeHistory
	 * @param newDate
	 */
	public void addBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory);

	/**
	 * update history and around it
	 * 
	 * @param bEmployeeHistory
	 * @param newDate
	 */
	public void updateBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item);

	/**
	 * delete history and update around it
	 * 
	 * @param bEmployeeHistory
	 * @param item
	 */
	public void deleteBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item);

}
