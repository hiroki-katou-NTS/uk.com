package nts.uk.ctx.at.request.dom.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.shr.com.context.AppContexts;
//import nts.uk.ctx.sys.gateway.dom.mail.service.RegisterEmbededURL;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.url.RegisterEmbededURL;
import nts.uk.shr.com.url.UrlExecInfo;
/**
 * @author hiep.ld
 *
 */
@Stateless
public class CheckTranmissionImpl implements CheckTransmission{
	
	//@Inject
	private UrlEmbeddedRepository urlEmbeddedRepo;
	@Inject
	private MailSender mailSender;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private RegisterEmbededURL registerEmbededURL;
	@Override
	public List<Integer> doCheckTranmission(String appId, int appType, int prePostAtr, List<String> employeeIdList, String mailTitle, String mailBody,
			List<String> fileId) {
		String cid = AppContexts.user().companyId();
		List<Integer> sendMailStatus = new ArrayList<Integer>();
		Optional<UrlEmbedded> urlEmbedded = urlEmbeddedRepo.getUrlEmbeddedById(cid);
		if (!urlEmbedded.isPresent()){
			return null;
		} else {
			int urlEmbeddedCls = urlEmbedded.get().getUrlEmbedded().value;
			if (urlEmbeddedCls == 1){
				Application_New application = applicationRepository.findByID(cid, appId).get();
				// String mailTitle = registerEmbededUrl.obtainMailUrl(appId, appType, prePostAtr, employeeIdList);
				// TO DO Request List 228
				List<String> employeeNameList = employeeIdList;
				// TO DO Request List 225
//				UrlExecInfo urlInfo = registerEmbededURL.obtainApplicationEmbeddedUrl(appId, application.getAppType().value,
//						application.getPrePostAtr().value, application.getEmployeeID());		
//				String mailContentToSend = urlInfo.getScreenId() + "\n" + urlInfo.getEmbeddedId() + "\n" + mailBody;
				List<String> mailList = employeeIdList;
				mailList.forEach( x -> {
					try{
						if(!x.isEmpty()){
							mailSender.send("nts", "hiep.ld@3si.vn", new MailContents(mailTitle, ""));
							sendMailStatus.add(1);
						}else {
							sendMailStatus.add(0);
						}
					} catch (SendMailFailedException e) {
						
					}
				});
			}
		}
		return sendMailStatus;
	}
}
