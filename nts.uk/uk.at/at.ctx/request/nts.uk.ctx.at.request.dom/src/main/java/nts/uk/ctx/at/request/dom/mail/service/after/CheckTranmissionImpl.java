package nts.uk.ctx.at.request.dom.mail.service.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.mail.service.RegisterEmbededURL;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
/**
 * @author hiep.ld
 *
 */
@Stateless
public class CheckTranmissionImpl implements CheckTransmission{
	
	@Inject
	private RegisterEmbededURL registerEmbededUrl;
	@Inject
	private MailSender mailSender;
	
	@Override
	public List<Integer> doCheckTranmission(String appId, int appType, int prePostAtr, List<String> employeeIdList, String mailTitle, String mailBody,
			List<String> fileId) {
		List<Integer> sendMailStatus = new ArrayList<Integer>();
//		String mailTitle = registerEmbededUrl.obtainMailUrl(appId, appType, prePostAtr, employeeIdList);
		// TO DO Request List 228
		List<String> employeeNameList = employeeIdList;
		// TO DO Request List 225
		List<String> mailList = employeeIdList;
		mailList.forEach( x -> {
			try{
				if(!x.isEmpty()){
					mailSender.send("katohiro6180@gmail.com", "hiep.ld@3si.vn", new MailContents(mailTitle, mailBody));
					sendMailStatus.add(1);
				}else {
					sendMailStatus.add(0);
				}
			} catch (SendMailFailedException e) {
				
			}
		});
		return sendMailStatus;
	}

	
	
}
