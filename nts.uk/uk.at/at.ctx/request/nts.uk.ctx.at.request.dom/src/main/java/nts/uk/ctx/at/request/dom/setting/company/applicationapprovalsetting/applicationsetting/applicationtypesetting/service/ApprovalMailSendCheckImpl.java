package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * 承認処理後にメールを自動送信するか判定
 *
 */
@Stateless
public class ApprovalMailSendCheckImpl implements ApprovalMailSendCheck {

	@Override
	public String sendMail(AppTypeSetting appTypeSetting, Application application, Boolean allApprovalFlg) {
		// ドメインモデル「申請種類別設定」．承認処理時に自動でメールを送信するをチェックする(check domain 「申請種類別設定」．承認処理時に自動でメールを送信する)
		if(!appTypeSetting.isSendMailWhenApproval()) {
			return "";
		}
		// アルゴリズム「承認全体が完了したか」の実行結果をチェックする
		if(!allApprovalFlg) {
			return "";
		}
		// 申請者本人をメール送信先リストに追加して、返す 
		return application.getEmployeeID();
	}
}
