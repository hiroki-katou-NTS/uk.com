package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAfterDenyImpl implements DetailAfterDeny {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;
	
	@Inject
	private MailSender mailsender;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;

	@Override
	public String doDeny(String companyID, String appID, String employeeID, String memo) {
		String strMail = "";
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		Boolean releaseFlg = approvalRootStateAdapter.doDeny(companyID, appID, employeeID);
		approvalRootStateAdapter.updateReason(appID, employeeID, memo);
		if(releaseFlg.equals(Boolean.TRUE)){
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.DENIAL);
			applicationRepository.updateWithVersion(application);
			AppTypeDiscreteSetting discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value).get();
			if (discreteSetting.getSendMailWhenApprovalFlg().equals(AppCanAtr.CAN)) {
				String email = employeeAdapter.empEmail(application.getEmployeeID());
				if(Strings.isNotBlank(email)) {
					try {
						mailsender.send("nts", email, new MailContents("nts mail", "approval mail from NTS"));
					} catch (SendMailFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					strMail += email + System.lineSeparator();
				}
			}
		}
		return strMail;
	}

}
