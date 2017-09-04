package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class EmployeeAdapterInforFinder {
	@Inject
	private EmployeeApproveAdapter employeeInfor;
	/**
	 * find employee by workplaceId and base date
	 * @param workplaceIds
	 * @param baseDate
	 * @return
	 */
	public List<EmployeeApproveDto> findEmployeeByWpIdAndBaseDate(List<String> workplaceIds, GeneralDate baseDate){
		return employeeInfor.findByWpkIds(AppContexts.user().companyId(), workplaceIds, baseDate);
	}
	/**
	 *  get workplace id by employeeId and basedate
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public String getWorkplaceId(String employeeId, GeneralDate baseDate) {
		return employeeInfor.getWorkplaceId(AppContexts.user().companyId(), employeeId, baseDate);
	}
	/**
	* get employment code by companyID, employeeID and base date
	 * @param employeeId　社員ID　
	 * @param baseDate　基準日
	 * @return　雇用コード
	 */
	public String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate) {
		return employeeInfor.getEmploymentCode(AppContexts.user().companyId(), employeeId, baseDate);
	}
}
