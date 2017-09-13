package nts.uk.ctx.bs.employee.pub.employee.employeeInfo.atworkreferdate;

import java.util.List;

import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;

/**
 * The Interface Employee At Work Reference Date.
 */

public interface EmployeeAtworkReferDate {
	/**
	 * Get List Employee by companyId,standardDate 
	 * For request No.60
	 *
	 */

	List<EmployeeInfoDtoExport> getListEmployee(String companyId , String standardDate);
}
