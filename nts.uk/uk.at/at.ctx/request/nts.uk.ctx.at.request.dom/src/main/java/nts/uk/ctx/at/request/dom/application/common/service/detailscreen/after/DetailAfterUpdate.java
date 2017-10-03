package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ApproverResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ApproverWhoApproved;
/**
 * 
 * 4-2.詳細画面登録後の処理
 *
 */
public interface DetailAfterUpdate {
	/**
	 * 4-2.詳細画面登録後の処理
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 */
	public void processAfterDetailScreenRegistration(String companyID, String appID);
	
	/**
	 * 1.承認を行った承認者を取得する
	 * @param approvalPhases
	 * @return approverListWhoApproved, approverList <=> 承認を行った承認者一覧, 承認者一覧
	 */
	public ApproverResult acquireApproverWhoApproved(List<AppApprovalPhase> approvalPhases);
}
