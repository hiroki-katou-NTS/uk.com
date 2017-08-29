package nts.uk.ctx.at.request.dom.application.common.detailedacreenafterapprovalprocess.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;

/**
 * 8-2.詳細画面承認後の処理
 */
public interface DetailedScreenAfterApprovalProcessService {
	/**
	 * 詳細画面承認後の処理
	 */
	public void detailScreenAfterApprovalProcess(String companyID, String appID, Application application);

	/**
	 * 1.申請個別のエラーチェック
	 */
	public void invidialApplicationErrorCheck(String appID);

	/**
	 * 2.申請個別の更新
	 */
	public void invidialApplicationUpdate(String appID);

	/**
	 * 3.実績反映状態の判断 3.1 : 承認者一覧を取得する : ApprovalATR.APPROVED 
	 * 3.2 : 未承認の承認者一覧を取得する : #ApprovalATR.APPROVED
	 */
	public List<String> actualReflectionStateDecision(String appID, String phaseID, ApprovalAtr approvalAtr);

	/**
	 * 4.メール送信先リストを取得する	
	 * 
	 * @return
	 */
	public DestinationMailListOuput MailDestination(Application application);
}
