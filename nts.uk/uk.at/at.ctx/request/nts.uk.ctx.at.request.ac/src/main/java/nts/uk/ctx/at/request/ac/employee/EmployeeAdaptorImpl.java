package nts.uk.ctx.at.request.ac.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.EmployeeAdaptor;

public class EmployeeAdaptorImpl implements EmployeeAdaptor {

	/*@Inject
	private EmployeePub employeePub;*/
	
	/**
	 * get employment code by companyID, employeeID and base date
	 * @param companyId 会社ID
	 * @param employeeId　社員ID　
	 * @param baseDate　基準日
	 * @return　雇用コード
	 */
	public String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate){
		/*return employeePub.getEmploymentCode(companyId, employeeId, baseDate);*/
		return null;
	}

}
