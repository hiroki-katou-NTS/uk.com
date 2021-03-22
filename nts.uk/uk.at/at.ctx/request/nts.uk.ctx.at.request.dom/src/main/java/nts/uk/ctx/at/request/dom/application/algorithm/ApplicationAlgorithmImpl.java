package nts.uk.ctx.at.request.dom.application.algorithm;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.SendMailAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationAlgorithmImpl implements ApplicationAlgorithm {

	@Override
	public SendMailAtr checkAutoSendMailRegister(AppTypeSetting appTypeSetting, boolean mailServerSet) {
		// INPUT．メールサーバ設定済区分をチェックする(check INPUT．メールサーバ設定済区分 ) 
		if(!mailServerSet) {
			// 「メール送信区分」　＝＝「送信しない」
			return SendMailAtr.NOT_SEND;
		}
		// Input：ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
		if(!appTypeSetting.isSendMailWhenRegister()) {
			// 「メール送信区分」　＝＝「送信しない」
			return SendMailAtr.NOT_SEND;
		}
		// 「メール送信区分」　＝＝　送信する
		return SendMailAtr.SEND;
	}

}
