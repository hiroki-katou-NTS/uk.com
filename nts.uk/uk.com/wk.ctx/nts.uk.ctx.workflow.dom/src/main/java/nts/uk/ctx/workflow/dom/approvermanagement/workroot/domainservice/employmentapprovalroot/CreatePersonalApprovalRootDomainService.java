package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalRootInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApproverInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.個人別承認ルート情報を作成して登録する
 */
public class CreatePersonalApprovalRootDomainService {

	/**
	 * [1]作成して登録する
	 * 
	 * @param require
	 * @param cid     会社ID
	 * @param sid     社員ID
	 * @param params  承認者設定パラメータ
	 * @return Atomtask
	 */
	public static AtomTask createAndRegister(Require require, String cid, String sid, ApprovalSettingParam param) {
		ApprovalSettingInformation settingInfo = createData(cid, sid, param);
		return AtomTask
				.of(() -> require.insertAll(settingInfo.getPersonApprovalRoot(), settingInfo.getApprovalPhases()));
	}

	/**
	 * [2]作成する
	 * 
	 * @param cid   会社ID
	 * @param sid   社員ID
	 * @param param 承認者設定パラメータ
	 * @return 承認者設定情報
	 */
	public static ApprovalSettingInformation createData(String cid, String sid, ApprovalSettingParam param) {
		ApprovalRootInformation approvalRootInfo = param.getApprovalRootInfo();
		List<ApproverInformation> approvalPhases = param.getApprovalPhases();
		// 個人別承認ルートを作成する

		// TODO
		return null;
	}

	public interface Require {

		// [R-1]個人別承認ルートと承認フェーズを insertする
		void insertAll(PersonApprovalRoot personApprovalRoot, List<ApprovalPhase> approvalPhases);
	}
}
