package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
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
public class DetailAfterRemandImpl implements DetailAfterRemand {

	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject
	private MailSender mailsender;
	
	@Override
	public MailSenderResult doRemand(String companyID, String appID, Long version, Integer order, String returnReason) {
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value).get();
		MailSenderResult mailSenderResult = null;
		if(order!=null){
			List<String> employeeList = approvalRootStateAdapter.doRemandForApprover(companyID, appID, order);
			if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)){
				mailSenderResult = this.getMailSenderResult(employeeList);
			}
		} else {
			approvalRootStateAdapter.doRemandForApplicant(companyID, appID);
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.REMAND);
			application.getReflectionInformation().setStateReflection(ReflectedState_New.REMAND);
			if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)){
				mailSenderResult = this.getMailSenderResult(Arrays.asList(application.getEmployeeID()));
			}
		}
		application.setReversionReason(new AppReason(returnReason));
		applicationRepository.updateWithVersion(application);
		return mailSenderResult;
	}

	@Override
	public MailSenderResult getMailSenderResult(List<String> employeeList) {
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		employeeList.forEach(employee -> {
			String employeeName = employeeAdapter.getEmployeeName(employee);
			String employeeMail = employeeAdapter.empEmail(employee);
			if(Strings.isBlank(employeeMail)){
				errorList.add(employeeName);
				return;
			} else {
				try {
					mailsender.send("nts", employeeMail, new MailContents("nts mail", "approval mail from NTS"));
				} catch (SendMailFailedException e) {
					throw new BusinessException("Msg_223");
				}
				successList.add(employeeName);
			}
		});
		return new MailSenderResult(successList, errorList);
	}

}
