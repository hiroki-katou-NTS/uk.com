package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.*;

/**
 * 02.社員の対象申請の承認ルートを取得する
 * @author dudt
 *
 */
public interface EmployeeOfApprovalRoot {
	/**
	 * L02.社員の対象申請の承認ルートを取得する
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.L:未登録社員リスト.アルゴリズム
	 * @param companyId　会社ID
	 * @param lstCompanyRootInfor　ドメインモデル「会社別就業承認ルート」
	 * @param lstWorkpalceRootInfor　ドメインモデル「職場別就業承認ルート」
	 * @param lstPersonRootInfor　ドメインモデル「個人別就業承認ルート」
	 * @param empInfor　対象社員
	 * @param baseDate　基準日
	 * @param sysAtr システム区分
	 * @param rootAtr 承認ルート区分
	 * @param appTarget 申請対象
	 * @return エラーなし => null, 承認ルートなし => empty object, エラーあり object with data
	 */
	UnregisteredApprovalCheckResult lstEmpApprovalRoot(
			String companyId,
			List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor,
			List<PersonApprovalRoot> lstPersonRootInfor,
			EmployeeImport empInfor,
			GeneralDate baseDate,
			int sysAtr,
			EmploymentRootAtr rootAtr,
			String appTarget
	);

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.L:未登録社員リスト.アルゴリズム
	 * L03.承認ルートの承認者が有効か判断する
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @param approvalIds
	 * @param sysAtr
	 * @param lowerApproval
	 * @return
	 */
	List<ErrorContent> validateApproverOfApprovalRoot(
			String companyId,
			String employeeId,
			GeneralDate baseDate,
			List<String> approvalIds,
			int sysAtr,
			Optional<Boolean> lowerApproval
	);
}
