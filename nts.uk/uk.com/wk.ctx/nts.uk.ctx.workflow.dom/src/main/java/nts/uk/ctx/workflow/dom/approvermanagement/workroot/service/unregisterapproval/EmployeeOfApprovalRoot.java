package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
/**
 * 02.社員の対象申請の承認ルートを取得する
 * @author dudt
 *
 */
public interface EmployeeOfApprovalRoot {
	/**
	 * 02.社員の対象申請の承認ルートを取得する
	 * @param companyId　会社ID
	 * @param rootInfor　ドメインモデル「会社別就業承認ルート」「職場別就業承認ルート」「個人別就業承認ルート」
	 * @param empInfor　対象社員
	 * @param rootType　就業ルート区分　
	 * @param appType　対象申請
	 * @param baseDate　基準日
	 * @return　承認ルートあり＝＞true	// * 承認ルートなし　＝＞false
	 */
	boolean lstEmpApprovalRoot(String companyId,
			List<ApprovalRootCommonOutput> lstCompanyRootInfor,
			List<ApprovalRootCommonOutput> lstWorkpalceRootInfor,
			List<ApprovalRootCommonOutput> lstPersonRootInfor,
			EmployeeUnregisterOutput empInfor, 
			int rootType,
			int appType,
			GeneralDate baseDate);
}
