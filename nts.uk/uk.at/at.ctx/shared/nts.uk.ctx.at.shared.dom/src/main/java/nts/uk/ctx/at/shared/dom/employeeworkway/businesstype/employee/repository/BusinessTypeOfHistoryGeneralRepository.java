package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistoryInter;
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
	 * @author lanlt
	 * add new history and update last history
	 * 
	 * @param bEmployeeHistory
	 * @param newDate
	 */
	void addAll(List<BusinessTypeOfEmployeeHistory> domains);

	/**
	 * update history and around it
	 * 
	 * @param bEmployeeHistory
	 * @param newDate
	 */
	public void updateBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item);
	
	/**
	 * @author lanlt
	 * update history and around it
	 * 
	 * @param bEmployeeHistory
	 * @param newDate
	 */
	void updateAll(List<BusinessTypeOfEmployeeHistoryInter> domainsInter);

	/**
	 * delete history and update around it
	 * 
	 * @param bEmployeeHistory
	 * @param item
	 */
	public void deleteBusinessTypeEmpOfHistory(BusinessTypeOfEmployeeHistory bEmployeeHistory, DateHistoryItem item);

}
