package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.DataSourceApproverList;
/**
 * 01.申請者としての承認ルートを取得する
 * @author dudt
 *
 */
public interface EmployeeRegisterApprovalRoot {
	/**
	 * excel for N
	 * Refactor UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.N:承認者一覧.アルゴリズム."01.申請者としての承認ルートを取得する"
	 * 01.申請者としての承認ルートを取得する
	 * Hoatt customize
	 * @param companyID 会社ID 
	 * @param baseDate 基準日
	 * @param lstEmpIds 選択する対象社員リスト
	 * @param rootAtr 就業ルート区分: 共通ルートを選択すると	値：0、以外：1
	 * @param lstApps 選択する申請対象
	 * @return
	 */
	DataSourceApproverList lstEmps(String companyID, int sysAtr, GeneralDate baseDate,  List<String> lstEmpIds, List<AppTypes> lstApps);
}
