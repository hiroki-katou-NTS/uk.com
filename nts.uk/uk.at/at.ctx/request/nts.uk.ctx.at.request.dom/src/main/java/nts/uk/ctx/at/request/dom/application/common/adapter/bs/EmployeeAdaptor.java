package nts.uk.ctx.at.request.dom.application.common.adapter.bs;

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
	
	List<String> getListWorkplaceID(String companyId, String employeeId, GeneralDate baseDate);
}
