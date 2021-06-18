package nts.uk.ctx.at.request.dom.mail;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
	public MailSenderResult doCheckTranmission(SendMailAppInfoParam sendMailAppInfoParam, boolean sendMailApplicant, String mailTemplate, AppEmailSet appEmailSet) {
		List<String> successList = new ArrayList<>();
		List<SendMailApproverInfoParam> approverInfoLst = sendMailAppInfoParam.getApproverInfoLst();
		// 申請者にメールを送信するかチェックする
		if(sendMailApplicant) {
			// メール送信する承認者リストに追加する
			approverInfoLst.add(new SendMailApproverInfoParam(sendMailAppInfoParam.getApplication().getEmployeeID(), sendMailAppInfoParam.getOpApplicantMail().get(), ""));
		}
		// ループを開始する　
		for(SendMailApproverInfoParam sendMailApproverInfoParam : approverInfoLst) {
			// アルゴリズム「申請メール埋込URL取得」を実行する
			String urlInfo = this.getAppEmailEmbeddedURL(
					sendMailAppInfoParam.getApplication().getAppID(), 
					sendMailAppInfoParam.getApplication().getAppType(), 
					sendMailAppInfoParam.getApplication().getPrePostAtr(), 
					sendMailApproverInfoParam.getApproverID(),
					appEmailSet.getUrlReason()==NotUseAtr.USE);
			// 送信対象者リストの「メール送信する」の承認者に対してメールを送信する
			try {
				mailSender.sendFromAdmin(
						sendMailApproverInfoParam.getApproverMail(), 
						new MailContents(
								sendMailAppInfoParam.getApplication().getAppDate().getApplicationDate().toString() + "　" + sendMailAppInfoParam.getApplication().getAppType().name,
								mailTemplate + urlInfo));
				successList.add(sendMailApproverInfoParam.getApproverID());
			} catch (Exception e) {
				throw new BusinessException("Msg_1057");
			}
		}
		return new MailSenderResult(successList, new ArrayList<>());
	}

	@Override
	public String getAppEmailEmbeddedURL(String appID, ApplicationType appType, PrePostAtr prePostAtr,
			String employeeID, boolean urlInclude) {
		// URL理込をチェックする
		if(!urlInclude) {
			return "";
		}
		// アルゴリズム「埋込URL情報登録申請」を実行する
		String urlInfo = registerEmbededURL.registerEmbeddedForApp(appID, appType.value, prePostAtr.value, "", employeeID);
		// 埋込用URLが作成された場合
		if(Strings.isBlank(urlInfo)) {
			return "";
		}
		// 本文追加用URLを作成する
		return "\n" + I18NText.getText("KDL030_30") + "\n" + urlInfo;
	}
}
