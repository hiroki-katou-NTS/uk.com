package nts.uk.ctx.bs.employee.pub.employee.employeeInfo.atworkreferdate;

import java.util.List;

import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;

/**
 * The Interface Employee AtWork Reference StanardDate.
 */

public interface EmployeeAtworkReferenDatePub {
	/**
	 * Get List Employee by companyId,standardDate 
	 * For request No.60
	 *
	 */

	List<EmployeeInfoDtoExport> getListEmployeeByStandardDate(String companyId , String standardDate);
}
