package nts.uk.ctx.at.request.dom.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

<<<<<<< HEAD
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
=======
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
>>>>>>> pj/at/dev/Team_D/KDL030
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.url.RegisterEmbededURL;

/**
 * @author hiep.ld
 *
 */
@Stateless
public class CheckTranmissionImpl implements CheckTransmission {

	@Inject
	private UrlEmbeddedRepository urlEmbeddedRepo;
	@Inject
	private MailSender mailSender;

	@Inject
	private ApplicationRepository_New applicationRepository;

	@Inject
	private RegisterEmbededURL registerEmbededURL;
	
<<<<<<< HEAD
=======
	@Inject
	private AtEmployeeAdapter employeeRequestAdapter;
	
>>>>>>> pj/at/dev/Team_D/KDL030
	@Override
	public MailSenderResult doCheckTranmission(String appId, int appType, int prePostAtr, List<String> employeeIdList,
			String mailTitle, String mailBody, List<String> fileId) {
		String cid = AppContexts.user().companyId();
		Application_New application = applicationRepository.findByID(cid, appId).get();
<<<<<<< HEAD
		Optional<UrlEmbedded> urlEmbedded = urlEmbeddedRepo.getUrlEmbeddedById(cid);
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		String appContent = "app contents";
		if (urlEmbedded.isPresent()) {
			int urlEmbeddedCls = urlEmbedded.get().getUrlEmbedded().value;
			NotUseAtr checkUrl = NotUseAtr.valueOf(urlEmbeddedCls);
			if (checkUrl == NotUseAtr.USE) {
				String urlInfo = registerEmbededURL.obtainApplicationEmbeddedUrl(appId, application.getAppType().value,
						application.getPrePostAtr().value, application.getEmployeeID());
				if (!Strings.isEmpty(urlInfo)){
					appContent += "\n" + "#KDL030_30" + " " + application.getAppID() + "\n" + urlInfo;
				}
			}
		}
		String loginName = "D00001";
		String loginMail = "D00001@nittsusystime.co.jp";
		String empName = "D00001 name";
		String mailContentToSend = I18NText.getText("Msg_703",
				loginName, mailBody,
				GeneralDate.today().toString(), application.getAppType().nameId,
				empName, application.getAppDate().toLocalDate().toString(),
				appContent, loginName, loginMail);
		// TO - DO
		// request list 225
		// request list 228
		// get mail (may be get from client but re-get here)
		for(String employeeToSendId: employeeIdList){
			String mail = employeeToSendId;
			String employeeMail = "hiep.ld@3si.vn";
			if (Strings.isBlank(mail)) {
				errorList.add(mail);
			} else {
				mailSender.send("tarou@nittsusystem.co.jp", employeeMail, new MailContents("", mailContentToSend));
				successList.add(employeeToSendId);
=======
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		List<EmployeeInfoImport> listEmpName = employeeRequestAdapter.getByListSID(employeeIdList);
		for(String employeeToSendId: employeeIdList){
			List<EmployeeInfoImport> listEmpName1 = employeeRequestAdapter.getByListSID(Arrays.asList(employeeToSendId));
			String empName = (!listEmpName1.isEmpty() ? listEmpName1.get(0).getBussinessName() : "");
			// TODO
			// request list 419 - get mail
			String employeeMail = "hiep.ld@3si.vn";
			// アルゴリズム「申請メール埋込URL取得」を実行する
//			Optional<UrlEmbedded> urlEmbedded = urlEmbeddedRepo.getUrlEmbeddedById(cid);
//			if (urlEmbedded.isPresent()) {
//				int urlEmbeddedCls = urlEmbedded.get().getUrlEmbedded().value;
//				NotUseAtr checkUrl = NotUseAtr.valueOf(urlEmbeddedCls);
//				if (checkUrl == NotUseAtr.USE) {
//					String urlInfo = registerEmbededURL.obtainApplicationEmbeddedUrl(appId, application.getAppType().value,
//							application.getPrePostAtr().value, employeeToSendId);
//					if (!Strings.isEmpty(urlInfo)){
//						appContent += "\n" + I18NText.getText("KDL030_30") + " " + application.getAppID() + "\n" + urlInfo;
//					} 
//				}
//			}
			if (Strings.isBlank(employeeMail)) {
				errorList.add(empName);
			} else {
				// 送信対象者リストの「メール送信する」の承認者に対してメールを送信する
				mailSender.send("mailadmin@uk.com", employeeMail, new MailContents("", mailBody));
				successList.add(empName);
>>>>>>> pj/at/dev/Team_D/KDL030
				
			}
		}
		return new MailSenderResult(successList, errorList);
	}
}
