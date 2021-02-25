package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
/**
 * 02.社員の対象申請の承認ルートを取得する
 * @author dudt
 *
 */
public interface ApplicationOfEmployee {
	/**
	 * Refactor5 02.社員の対象申請の承認ルートを取得する
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.L:未登録社員リスト.アルゴリズム."02.社員の対象申請の承認ルートを取得する"
	 * 社員の対象申請の承認ルートを取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * @param lstCompanyRootInfor  会社別就業承認ルート
	 * @param lstWorkpalceRootInfor 職場別就業承認ルート
	 * @param lstPersonRootInfor  個人別就業承認ルート
	 * @param sId  対象社員
	 * @param rootAtr  就業ルート区分
	 * @param appType  対象申請
	 * @param baseDate  基準日
	 * @return 承認ルートのデータ
	 */
	List<ApprovalRootCommonOutput> appOfEmployee(List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			String companyID, String sId, AppTypes appType, GeneralDate baseDate, int sysAtr);
	/**
	 * 
	 * 社員の対象申請の承認ルートを取得する( 就業ルート区分(共通)) 
	 * @param lstCompanyRootInfor  会社別就業承認ルート
	 * @param lstWorkpalceRootInfor 職場別就業承認ルート
	 * @param lstPersonRootInfor  個人別就業承認ルート
	 * @param sId  対象社員
	 * @param rootAtr  
	 * @param appType  対象申請
	 * @param baseDate  基準日
	 * @return 承認ルートのデータ
	 */
	List<ApprovalRootCommonOutput> commonOfEmployee(List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			String companyID, String sId, GeneralDate baseDate, int sysAtr);
}
