package nts.uk.ctx.at.request.dom.mail;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;

/**
 * @author hiep.ld
 *
 */
public interface CheckTransmission {

	/**
	 * 「メール送信する」の承認者全員に対してメールを送信する
	 * アルゴリズム「送信・送信後チェック」を実行する
	 * @param appId
	 * @param appType
	 * @param prePostAtr
	 * @param employeeIdList
	 * @param mailTitle
	 * @param mailBody
	 * @param fileId
	 * @param appDate
	 * @param applicantID
	 * @return sendMailResult
	 */
	public MailSenderResult doCheckTranmission(SendMailAppInfoParam sendMailAppInfoParam, boolean sendMailApplicant, String mailTemplate, AppEmailSet appEmailSet);
	
	/**
	 * 申請メール埋込URL取得
	 * @param appID 申請ID
	 * @param appType 申請種類
	 * @param prePostAtr 事前事後区分
	 * @param employeeID 社員ID
	 * @param urlInclude URL理込(boolean)
	 * @return
	 */
	public String getAppEmailEmbeddedURL(String appID, ApplicationType appType, PrePostAtr prePostAtr, String employeeID, boolean urlInclude);
}
