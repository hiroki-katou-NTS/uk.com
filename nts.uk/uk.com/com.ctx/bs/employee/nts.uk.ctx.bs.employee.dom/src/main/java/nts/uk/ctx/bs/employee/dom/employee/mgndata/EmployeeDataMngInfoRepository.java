/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employee.mgndata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
public interface EmployeeDataMngInfoRepository {
	void add(EmployeeDataMngInfo domain);

	void update(EmployeeDataMngInfo domain);

	void remove(EmployeeDataMngInfo domain);

	void remove(String sid, String pId);

	EmployeeDataMngInfo findById(String sid, String pId);
	// Lanlt code start

	Optional<EmployeeInfo> findById(String sid);

	Optional<EmployeeInfo> getDepartment(String departmentId, GeneralDate date);

	// Lanlt code end

	List<EmployeeDataMngInfo> findByEmployeeId(String sid);

	List<EmployeeDataMngInfo> findByPersonId(String pid);

	List<EmployeeDataMngInfo> findByCompanyId(String cid);

	// sonnlb code start

	List<EmployeeDataMngInfo> getEmployeeNotDeleteInCompany(String cId, String sCd);

	// sonnlb code end

	void updateRemoveReason(EmployeeDataMngInfo domain);

	List<EmployeeDataMngInfo> getListEmpToDelete(String cid);

	Optional<EmployeeDataMngInfo> findByEmployeCD(String empcode, String cid);
	
	// add by duongtv
	/**
	 * Find by list employee id.
	 *
	 * @param companyId the company id
	 * @param employeeIds the employee ids
	 * @return the list
	 */
	List<EmployeeDataMngInfo> findByListEmployeeId(String companyId, List<String> employeeIds);
	
	/**
	 * Find by list employee code.
	 *
	 * @param companyId the company id
	 * @param employeeCodes the employee codes
	 * @return the list
	 */
	List<EmployeeDataMngInfo> findByListEmployeeCode(String companyId, List<String> employeeCodes);
}
