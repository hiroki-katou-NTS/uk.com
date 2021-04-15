package nts.uk.ctx.at.request.dom.mail;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.url.RegisterEmbededURL;

/**
 * @author hiep.ld
 *
 */
@Stateless
public class CheckTranmissionImpl implements CheckTransmission {
	
	@Inject
	private MailSender mailSender;

	@Inject
	private RegisterEmbededURL registerEmbededURL;
	
	/**
	 * 送信・送信後チェック
	 */
	@Override
	public MailSenderResult doCheckTranmission(SendMailAppInfoParam sendMailAppInfoParam, boolean sendMailApplicant, String mailTemplate) {
		List<String> successList = new ArrayList<>();
		List<SendMailApproverInfoParam> approverInfoLst = sendMailAppInfoParam.getApproverInfoLst();
		// 申請者にメールを送信するかチェックする
		if(sendMailApplicant) {
			// 申請者のメールアドレスをチェックする
			if(!sendMailAppInfoParam.getOpApplicantMail().isPresent()) {
				// エラーメッセージを表示する（Msg_1309）
				throw new BusinessException("Msg_1309");
			}
			// メール送信する承認者リストに追加する
			approverInfoLst.add(new SendMailApproverInfoParam(sendMailAppInfoParam.getApplication().getEmployeeID(), sendMailAppInfoParam.getOpApplicantMail().get(), ""));
		}
		// ループを開始する　
		for(SendMailApproverInfoParam sendMailApproverInfoParam : approverInfoLst) {
			// アルゴリズム「申請メール埋込URL取得」を実行する
			String urlInfo = registerEmbededURL.registerEmbeddedForApp(
					sendMailAppInfoParam.getApplication().getAppID(), 
					sendMailAppInfoParam.getApplication().getAppType().value, 
					sendMailAppInfoParam.getApplication().getPrePostAtr().value, 
					"", 
					sendMailApproverInfoParam.getApproverID());
			// 送信対象者リストの「メール送信する」の承認者に対してメールを送信する
			try {
				mailSender.sendFromAdmin(
						sendMailApproverInfoParam.getApproverMail(), 
						new MailContents(
								sendMailAppInfoParam.getApplication().getAppDate().getApplicationDate().toString("yyyy/MM/dd") + "　" + sendMailAppInfoParam.getApplication().getAppType().name,
								mailTemplate + urlInfo));
				successList.add(sendMailApproverInfoParam.getApproverID());
			} catch (Exception e) {
				throw new BusinessException("Msg_1057");
			}
		}
		return new MailSenderResult(successList, new ArrayList<>());
	}
}
