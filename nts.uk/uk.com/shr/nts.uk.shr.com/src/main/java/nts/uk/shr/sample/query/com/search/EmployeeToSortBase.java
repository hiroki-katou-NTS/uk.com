package nts.uk.shr.sample.query.com.search;

import java.util.List;

/**
 * 社員のソート用の規定情報
 * @author m_kitahira
 *
 */
public class EmployeeToSortBase {
	
	private String employeeId;
	private String employeeCode;
	private String clsssCode;
	
	public int compareTo(EmployeeToSortBase other, EmployeeSortType sortType) {
		switch (sortType) {
		case EMPLOYEE_CODE:
			this.employeeCode.compareTo(other.employeeCode);
		case CLASS_CODE:
			this.clsssCode.compareTo(other.clsssCode);
		default:
			return 0;
		}
	}
}
