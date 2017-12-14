/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employeeinfo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * The Interface EmployeeRepository.
 */
public interface EmployeeRepository {

	/**
	 * Gets the person id by employee code.
	 *
	 * @param companyId
	 *            the company id
	 * @param employeeCode
	 *            the employee code
	 * @return the person id by employee code
	 */
	Optional<Employee> findByEmployeeCode(String companyId, String employeeCode, GeneralDate standardDate);

	/**
	 * Gets the list person by list employee.
	 *
	 * @param companyId
	 *            the company id
	 * @param employeeCode
	 *            the employee code
	 * @return the list person by list employee
	 */
	List<Employee> findByListEmployeeCode(String companyId, List<String> employeeCode);

	/**
	 * Gets the all employee.
	 *
	 * @param companyId
	 *            the company id
	 * @return the all employee
	 */
	List<Employee> findAll(String companyId);

	/**
	 * Gets the list person by list employee.
	 *
	 * @param companyId
	 *            the company id
	 * @param employeeIds
	 *            the employee ids
	 * @return the list person by list employee
	 */
	List<Employee> findByListEmployeeId(String companyId, List<String> employeeIds);

	/**
	 * Find by sid.
	 *
	 * @param employeeId
	 *            the employee id
	 * @return the optional
	 */
	Optional<Employee> findBySid(String companyId, String employeeId);

	/**
	 * Find last employee in db
	 * 
	 * @return the optional
	 */
	String findLastEml(String companyId, String startLetter);

	/**
	 * 
	 * @param employeeId
	 * @return the optional
	 */
	Optional<Employee> getBySid(String employeeId);

	List<Employee> getListEmpByStandardDate(String companyId, GeneralDate standardDate);

	// laitv
	/**
	 * Get Employee Info
	 * 
	 * @param employeeId
	 * @return
	 */
	Optional<Object[]> getEmployeeInfoToDelete(String employeeId);

	/**
	 * Get Employee Info
	 * 
	 * @param employeeId
	 * @return
	 */
	Optional<Object[]> getEmployeeDetailToDelete(String employeeId);

	/**
	 * Get list EmployeeDelete
	 * 
	 * @return
	 */

	List<Object[]> getAllEmployeeInfoToDelete();

	/**
	 * Update Employee
	 * 
	 * @param domain
	 */
	void updateEmployee(Employee domain);

	/**
	 * @param employeeId
	 * @param companyId
	 * @param systemDate
	 *            RequestList #101
	 * @return the optional
	 */
	Optional<Employee> findBySidCidSystemDate(String companyId, String personId, GeneralDate systemDate);

	/**
	 * Gets the list Employee by list employeeIds.
	 *
	 * @param employeeIds
	 *            the employee ids
	 * @return the list person by list employee
	 */
	List<Employee> getByListEmployeeId(List<String> employeeIds);

	Optional<Employee> getInfoById(String employeeId);

	/**
	 * @author lanlt find employee
	 * @param companyId
	 * @param sid
	 * @param standardDate
	 * @return Employee
	 */
	Optional<Employee> findByEmployeeID(String companyId, String sid, GeneralDate standardDate);

}
