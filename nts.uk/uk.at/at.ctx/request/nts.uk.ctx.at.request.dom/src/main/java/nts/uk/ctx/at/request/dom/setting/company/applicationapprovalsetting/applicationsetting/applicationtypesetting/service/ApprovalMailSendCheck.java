package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 */
public interface ApprovalMailSendCheck {
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.承認処理後にメールを自動送信するか判定.承認処理後にメールを自動送信するか判定
	 * @param appTypeSetting 申請種類別設定
	 * @param application 申請
	 * @param allApprovalFlg 承認完了フラグ
	 * @return
	 */
	public String sendMailApplicant(AppTypeSetting appTypeSetting, Application application, Boolean allApprovalFlg);
	
	/**
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.承認処理後にメールを承認者に送信判定.承認処理後にメールを承認者に送信判定
	 * @param appTypeSetting 申請種類別設定
	 * @param application 申請
	 * @param phaseNumber 承認フェーズ枠番
	 * @return
	 */
	public List<String> sendMailApprover(AppTypeSetting appTypeSetting, Application application, Integer phaseNumber);
}