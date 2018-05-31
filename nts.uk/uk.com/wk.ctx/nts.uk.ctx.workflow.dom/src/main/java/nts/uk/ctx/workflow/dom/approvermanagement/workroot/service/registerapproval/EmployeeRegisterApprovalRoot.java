package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
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
	 * @param rootAtr 就業ルート区分: 共通ルートを選択すると	値：0、以外：1
	 * @param lstApps 選択する申請対象
	 * @return
	 */
	Map<String, WpApproverAsAppOutput> lstEmps(String companyID, GeneralDate baseDate, List<String> lstEmpIds, List<AppTypes> lstApps);
}
