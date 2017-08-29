package nts.uk.ctx.at.request.dom.application.common.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface EmployeeAdaptor {
	/**
	 * get employment code by companyID, employeeID and base date
	 * @param companyId 会社ID
	 * @param employeeId　社員ID　
	 * @param baseDate　基準日
	 * @return　雇用コード
	 */
	String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate);
	
	
	/**
	 * 所属職場を含む上位職場を取得
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @param date the date
	 * @return the list
	 */
	// RequestList #65
	List<String> findWpkIdsBySCode(String companyId, String employeeCode, GeneralDate date);
}
