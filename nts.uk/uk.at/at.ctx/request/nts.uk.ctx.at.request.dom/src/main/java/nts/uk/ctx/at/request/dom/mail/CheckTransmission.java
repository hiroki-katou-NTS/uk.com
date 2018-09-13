package nts.uk.ctx.at.request.dom.mail;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;

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
	public MailSenderResult doCheckTranmission(String appId, int appType, int prePostAtr, List<String> employeeIdList, 
			String mailTitle, String mailBody, List<String> fileId, String appDate, String applicantID, boolean sendMailApplicaint);
}
