package nts.uk.ctx.at.request.dom.application.common.adapter;

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
}
