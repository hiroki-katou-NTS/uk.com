package nts.uk.ctx.workflow.ac.employee;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.pub.employee.EmployeePub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;

/**
 * 社員についてデータを取得する
 * @author dudt
 *
 */
@Stateless
public class EmployeeAdapterImpl implements EmployeeApproveAdapter{
	@Inject
	private EmployeePub employeePub;
	/**
	 * 「所属職場履歴」をすべて取得する
	 * get employee information by companyId, workplaceId and base date
	 * @param companyId　会社ID
	 * @param workplaceIds　職場IDリスト
	 * @param baseDate　基準日
	 * @return 社員情報
	 */
	public List<EmployeeApproveDto> findByWpkIds(String companyId, 
			List<String> workplaceIds,
			GeneralDate baseDate){
		List<EmployeeDto> empDto = employeePub.findByWpkIds(companyId, workplaceIds, baseDate);
		List<EmployeeApproveDto> lstEmployees = new ArrayList<>();
		for (EmployeeDto employeeDto : empDto) {
			EmployeeApproveDto appDto = new EmployeeApproveDto();
			appDto.setCompanyId(employeeDto.getCompanyId());
			appDto.setJoinDate(employeeDto.getJoinDate());
			appDto.setPId(employeeDto.getPId());
			appDto.setRetirementDate(employeeDto.getRetirementDate());
			appDto.setSCd(employeeDto.getSCd());
			appDto.setSId(employeeDto.getSId());
			appDto.setSMail(employeeDto.getSMail());
			lstEmployees.add(appDto);
		}
		return lstEmployees;
	}
	/**
	 * get workplace id by employeeId and basedate
	 * @param companyId 会社ID
	 * @param employeeId　社員ID
	 * @param baseDate　基準日
	 * @return　職場ID
	 */
	public String getWorkplaceId(String companyId,
			String employeeId, 
			GeneralDate baseDate) {
		return employeePub.getWorkplaceId(companyId, employeeId, baseDate);
	}
	/**
	 * get employment code by companyID, employeeID and base date
	 * @param companyId 会社ID
	 * @param employeeId　社員ID　
	 * @param baseDate　基準日
	 * @return　雇用コード
	 */
	public String getEmploymentCode(String companyId, 
			String employeeId, 
			GeneralDate baseDate){
		return employeePub.getEmploymentCode(companyId, employeeId, baseDate);
	}
}
