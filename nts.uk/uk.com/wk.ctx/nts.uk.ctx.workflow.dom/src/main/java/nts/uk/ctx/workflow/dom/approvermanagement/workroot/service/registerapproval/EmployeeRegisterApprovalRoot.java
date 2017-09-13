package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
/**
 * 01.申請者としての承認ルートを取得する
 * @author dudt
 *
 */
public interface EmployeeRegisterApprovalRoot {
	/**
	 * 01.申請者としての承認ルートを取得する
	 * @param companyID 会社ID 
	 * @param baseDate 基準日
	 * @param lstEmpIds 選択する対象社員リスト
	 * @param rootAtr 就業ルート区分
	 * @param lstApps 選択する申請対象
	 * @return
	 */
	List<EmployeeApproveDto> lstEmps(String companyID, GeneralDate baseDate, List<String> lstEmpIds, EmploymentRootAtr rootAtr, List<ApplicationType> lstApps);
}
