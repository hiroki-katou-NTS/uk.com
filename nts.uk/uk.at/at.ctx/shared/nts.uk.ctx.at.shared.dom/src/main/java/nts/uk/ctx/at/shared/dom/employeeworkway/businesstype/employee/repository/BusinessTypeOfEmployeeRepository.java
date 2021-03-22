package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;

public interface BusinessTypeOfEmployeeRepository {
	/**
	 * find all by list businessTypeCodes
	 * 
	 * @param businessTypeCodes
	 * @return List BusinessTypeOfEmployee
	 */
	List<BusinessTypeOfEmployee> findAllByListCode(List<String> businessTypeCodes);

	/**
	 * insert BusinessTypeOfEmployee
	 * 
	 * @param businessTypeOfEmployee
	 */
	void insert(BusinessTypeOfEmployee businessTypeOfEmployee);
	
	/**
	 * @author lanlt
	 * add all BusinessTypeOfEmployee
	 * 
	 * @param businessTypeOfEmployee
	 */
	void addAll(List<BusinessTypeOfEmployee> domains);

	/**
	 * update BusinessTypeOfEmployee
	 * 
	 * @param businessTypeOfEmployee
	 */
	void update(BusinessTypeOfEmployee businessTypeOfEmployee);
	

	/**
	 * @author lanlt
	 * update BusinessTypeOfEmployee
	 * 
	 * @param businessTypeOfEmployee
	 */
	void updateAll(List<BusinessTypeOfEmployee> domains);

	/**
	 * delete BusinessTypeOfEmployee
	 * 
	 * @param businessTypeOfEmployee
	 */
	void delete(String historyId);
	/**
	 * find by HistoryID
	 * @param historyId
	 * @return BusinessTypeOfEmployee
	 */
	Optional<BusinessTypeOfEmployee> findByHistoryId(String historyId);
	
	/**
	 * find all by list employeeId, date
	 * 
	 * @param businessTypeCodes
	 * @return List BusinessTypeOfEmployee
	 */
	List<BusinessTypeOfEmployee> findAllByEmpAndDate(List<String> employeeIds, DatePeriod date);
	
	/**
	 * find all by histIds
	 * @param histIds
	 * @return
	 */
	List<BusinessTypeOfEmployee> findAllByHistIds(List<String> histIds);
}
