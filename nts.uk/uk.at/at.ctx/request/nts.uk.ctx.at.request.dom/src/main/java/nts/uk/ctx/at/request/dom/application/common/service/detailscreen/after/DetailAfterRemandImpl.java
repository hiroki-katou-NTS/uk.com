package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.logging.log4j.util.Strings;
import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMailRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.url.RegisterEmbededURL;
import nts.uk.shr.com.url.UrlExecInfo;

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

	@Inject
	private RegisterEmbededURL registerEmbededURL;

	@Inject
	private ContentOfRemandMailRepository remandRepo;

	@Override
	public MailSenderResult doRemand(String companyID, String appID, Long version, Integer order, String returnReason) {
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository
				.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value).get();
		MailSenderResult mailSenderResult = null;
		UrlExecInfo urlInfo = registerEmbededURL.obtainApplicationEmbeddedUrl(appID, application.getAppType().value,
				application.getPrePostAtr().value, application.getEmployeeID());
		if (order != null) {
			List<String> employeeList = approvalRootStateAdapter.doRemandForApprover(companyID, appID, order);
			if (appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
				mailSenderResult = this.getMailSenderResult(application, employeeList, urlInfo);
			}
		} else {
			approvalRootStateAdapter.doRemandForApplicant(companyID, appID);
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.REMAND);
			application.getReflectionInformation().setStateReflection(ReflectedState_New.REMAND);
			if (appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
				mailSenderResult = this.getMailSenderResult(application, Arrays.asList(application.getEmployeeID()),
						urlInfo);
			}
		}
		application.setReversionReason(new AppReason(returnReason));
		applicationRepository.updateWithVersion(application);
		return mailSenderResult;
	}

	@Override
	public MailSenderResult getMailSenderResult(Application_New application, List<String> employeeList,
			UrlExecInfo urlInfo) {
		String mailTitle;
		String mailBody;
		String cid = AppContexts.user().companyId();
		ContentOfRemandMail remandTemp = remandRepo.getRemandMailById(cid).orElse(null);
		if (!Objects.isNull(remandTemp)) {
			mailTitle = remandTemp.getMailTitle();
			mailBody = remandTemp.getMailBody();
		} else {
			mailTitle = remandTemp.getMailTitle();
			mailBody = remandTemp.getMailBody();
		}
		String mailContentToSend = I18NText.getText("Msg_1060",
				employeeAdapter.getEmployeeName(AppContexts.user().employeeId()), mailBody,
				GeneralDate.today().toString(), EnumAdaptor.convertToValueName(application.getAppType()).toString(),
				employeeAdapter.getEmployeeName(application.getEmployeeID()), application.getAppDate().toString(),
				application.getAppReason().toString(), employeeAdapter.getEmployeeName(AppContexts.user().employeeId()), employeeAdapter.empEmail(AppContexts.user().employeeId()));
		mailContentToSend +=  "\n" + urlInfo.getScreenId() + "\n" + urlInfo.getEmbeddedId();
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		for(String employee: employeeList){
			String employeeName = employeeAdapter.getEmployeeName(employee);
			String employeeMail = employeeAdapter.empEmail(employee);
			if (Strings.isBlank(employeeMail)) {
				errorList.add(employeeName);
				continue;
			} else {
				mailsender.send("nts", employeeMail, new MailContents(mailTitle, mailContentToSend));
				successList.add(employeeName);
			}
		}
		return new MailSenderResult(successList, errorList);
	}

}
