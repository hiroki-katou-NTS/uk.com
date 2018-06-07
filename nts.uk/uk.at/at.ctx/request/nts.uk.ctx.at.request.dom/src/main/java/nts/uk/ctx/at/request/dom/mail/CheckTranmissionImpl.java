package nts.uk.ctx.at.request.dom.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.OutGoingMailImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
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

	@Inject
	private AtEmployeeAdapter empAdapter;
	
	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private AppDispNameRepository repoAppDispName;
	/**
	 * 送信・送信後チェック
	 */
	@Override
	public MailSenderResult doCheckTranmission(String appId, int appType, int prePostAtr, List<String> employeeIdList,
			String mailTitle, String mailBody, List<String> fileId, String appDate) {
		String cid = AppContexts.user().companyId();
		Application_New application = applicationRepository.findByID(cid, appId).get();
		Optional<UrlEmbedded> urlEmbedded = urlEmbeddedRepo.getUrlEmbeddedById(cid);
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		//get list mail by list sID : rq419
		List<MailDestinationImport> lstMail = envAdapter.getEmpEmailAddress(cid, employeeIdList, 6);
		Optional<AppDispName> appDispName = repoAppDispName.getDisplay(appType);
		String appName = "";
		if(appDispName.isPresent()){
			appName = appDispName.get().getDispName().v();
		}
		String titleMail = appDate + " " + appName;
		for(String employeeToSendId: employeeIdList){
			//find mail by sID
			OutGoingMailImport mail = envAdapter.findMailBySid(lstMail, employeeToSendId);
			String employeeMail = mail == null ? "" : mail.getEmailAddress();
			if (mail == null) {//TH k co mail -> se k xay ra
				//imported（申請承認）「社員名（ビジネスネーム）」を取得する 
				List<EmployeeInfoImport> empObj = empAdapter.getByListSID(Arrays.asList(employeeToSendId));
				if(!empObj.isEmpty() && empObj.size() > 1){
					errorList.add(empObj.get(0).getBussinessName());
				}
			} else {
				mailSender.sendFromAdmin(employeeMail, new MailContents(titleMail, mailBody));
				successList.add(employeeToSendId);
		
			}
		}

		return new MailSenderResult(successList, errorList);
	}
}
