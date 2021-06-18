package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository;

import java.util.List;
import java.util.Map;
//import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
//import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.history.DateHistoryItem;

public interface BusinessTypeEmpOfHistoryRepository {

	/**
	 * insert BusinessTypeOfEmployeeHistory
	 * 
	 * @param domain:
	 *            BusinessTypeOfEmployeeHistory
	 */
	void add(String companyId,String employeeId,String historyId, GeneralDate startDate,GeneralDate endDate);
	
	/**
	 * @author lanlt
	 * add all BusinessTypeOfEmployeeHistory
	 * @param dateHistItems
	 */
	void addAll(Map<String, DateHistoryItem> dateHistItems);

	/**
	 * update BusinessTypeOfEmployeeHistory
	 * 
	 * @param domain:
	 *            BusinessTypeOfEmployeeHistory
	 */
	void update(String companyId,String employeeId,String historyId, GeneralDate startDate,GeneralDate endDate);
	
	/**
	 * @author lanlt
	 * update all BusinessTypeOfEmployeeHistory
	 * 
	 * @param domain:
	 *            BusinessTypeOfEmployeeHistory
	 */
	void updateAll(Map<String, DateHistoryItem> dateHistItems);

	/**
	 * delete BusinessTypeOfEmployeeHistory
	 * 
	 * @param domain:
	 *            BusinessTypeOfEmployeeHistory
	 */
	void delete(String historyId);
	/**
	 * find by base date and employeeId
	 * @param baseDate
	 * @param sId employeeId
	 * @return BusinessTypeOfEmployeeHistory
	 */
	Optional<BusinessTypeOfEmployeeHistory> findByBaseDate(GeneralDate baseDate,String sId);
	/**
	 * find by employeeId
	 * @param sId employeeId
	 * @return BusinessTypeOfEmployeeHistory
	 */
	Optional<BusinessTypeOfEmployeeHistory> findByEmployee(String cid, String sId);
	
	/**
	 * find by employeeId
	 * @param sId employeeId
	 * @return BusinessTypeOfEmployeeHistory
	 */
	Optional<BusinessTypeOfEmployeeHistory> findByEmployeeDesc(String cid, String sId);
	/**
	 * @author lanlt
	 * find all sids and cid
	 * @param cid
	 * @param sId
	 * @return
	 */
	List<BusinessTypeOfEmployeeHistory> findByEmployeeDesc(String cid, List<String> sids);
	
	/**
	 * find by historyId
	 * @param historyId
	 * @return BusinessTypeOfEmployeeHistory
	 */
	Optional<BusinessTypeOfEmployeeHistory> findByHistoryId(String historyId);
	
	/**
	 * 
	 * tìm ra tất cả  những DateHisItem theo cid, sids, baseDate
	 * @param cid
	 * @param sIds
	 * @param baseDate
	 * @author lanlt
	 */
	List<DateHistoryItem> getDateHistItemByCidAndSidsAndBaseDate(String cid, List<String> sIds, GeneralDate baseDate);

	// get data cps013
	List<DateHistoryItem> getListByListSidsNoWithPeriod(String cid, List<String> sids);
	
	List<BusinessTypeOfEmployeeHistory> findByBaseDate(GeneralDate baseDate, List<String> sIds);

}
