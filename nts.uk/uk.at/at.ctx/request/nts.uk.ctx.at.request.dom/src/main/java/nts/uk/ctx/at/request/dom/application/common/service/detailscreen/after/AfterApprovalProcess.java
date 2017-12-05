package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.DestinationMailListOuput;

/**
 * 8-2.詳細画面承認後の処理
 */
public interface AfterApprovalProcess {
	/**
	 * 詳細画面承認後の処理
	 */
	public String detailScreenAfterApprovalProcess(Application application, String approverMemo);

	/**
	 * 1.申請個別のエラーチェック
	 */
	public void invidialApplicationErrorCheck(String appID);

	/**
	 * 2.申請個別の更新
	 */
	public void invidialApplicationUpdate(Application application);

	/**
	 * 3.1 : 承認者一覧を取得する : ApprovalATR.APPROVED 
	 * 3.2 : 未承認の承認者一覧を取得する : ApprovalATR.UNAPPROVED
	 */
	public List<String> actualReflectionStateDecision(String appID, String phaseID, ApprovalAtr approvalAtr);
	
	/**
	 * 3.実績反映状態の判断 
	 */
	public void judgmentActualReflection(Application application);
	
	/**
	 * 4.メール送信先リストを取得する	
	 * 
	 * @return
	 */
	public DestinationMailListOuput MailDestination(Application application);
	
	/**
	 * 3.1 : 承認者一覧を取得する
	 * 1.承認フェーズ毎の承認者を取得する
	 * @param companyID
	 * @param appID
	 * @return
	 */
	public List<String> getListApprover(String companyID, String appID);
}
