package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
/**
 * 02.社員の対象申請の承認ルートを取得する
 * @author dudt
 *
 */
public interface EmployeeOfApprovalRoot {
	/**
	 * 02.社員の対象申請の承認ルートを取得する
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.L:未登録社員リスト.アルゴリズム."02.社員の対象申請の承認ルートを取得する"
	 * @param companyId　会社ID
	 * @param rootInfor　ドメインモデル「会社別就業承認ルート」「職場別就業承認ルート」「個人別就業承認ルート」
	 * @param empInfor　対象社員
	 * @param rootType　就業ルート区分　
	 * @param appType　対象申請
	 * @param baseDate　基準日
	 * @param sysAtr 承認ルート区分
	 * @return　承認ルートあり＝＞true	// * 承認ルートなし　＝＞false
	 */
	boolean lstEmpApprovalRoot(String companyId, List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			EmployeeImport empInfor, String typeV, GeneralDate baseDate, int empR, int sysAtr);
}
