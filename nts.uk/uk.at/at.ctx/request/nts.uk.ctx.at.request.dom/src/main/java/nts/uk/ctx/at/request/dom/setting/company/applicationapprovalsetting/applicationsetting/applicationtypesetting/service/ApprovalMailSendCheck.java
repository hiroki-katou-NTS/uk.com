package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.承認処理後にメールを自動送信するか判定
 * @author Doan Duy Hung
 *
 */
public interface ApprovalMailSendCheck {
	
	/**
	 * 承認処理後にメールを自動送信するか判定
	 * @param appTypeSetting
	 * @param application
	 * @param allApprovalFlg
	 * @return
	 */
	public String sendMail(AppTypeSetting appTypeSetting, Application application, Boolean allApprovalFlg);
}