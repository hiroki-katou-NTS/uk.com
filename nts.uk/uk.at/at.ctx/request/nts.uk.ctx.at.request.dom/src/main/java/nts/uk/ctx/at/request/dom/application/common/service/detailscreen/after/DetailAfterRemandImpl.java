package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.OutGoingMailImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.application.IApplicationContentService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbedded;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedRepository;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMail;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting.ContentOfRemandMailRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.url.RegisterEmbededURL;

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

	@Inject
	private UrlEmbeddedRepository urlEmbeddedRepo;

	@Inject 
	private IApplicationContentService appContentService;
	
	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private AppDispNameRepository repoAppDispName;
	
	@Override
	public MailSenderResult doRemand(String companyID, String appID, Long version, Integer order, String returnReason) {
		Application_New application = applicationRepository.findByID(companyID, appID).get();
		application.setReversionReason(new AppReason(returnReason));
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingRepository
				.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value).get();
		MailSenderResult mailSenderResult = null;
		if (order != null) {
			// 差し戻し先が承認者の場合
			List<String> employeeList = approvalRootStateAdapter.doRemandForApprover(companyID, appID, order);
			if (appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
				mailSenderResult = this.getMailSenderResult(application, employeeList);
			} else {
				mailSenderResult = new MailSenderResult(null, null);
			}
		} else {
			// 差し戻し先が申請本人の場合
			approvalRootStateAdapter.doRemandForApplicant(companyID, appID);
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.REMAND);
			application.getReflectionInformation().setStateReflection(ReflectedState_New.REMAND);
			if (appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
				mailSenderResult = this.getMailSenderResult(application, Arrays.asList(application.getEmployeeID()));
			} else {
				mailSenderResult = new MailSenderResult(null, null);
			}
		}
		applicationRepository.updateWithVersion(application);
		return mailSenderResult;
	}

	@Override
	public MailSenderResult getMailSenderResult(Application_New application, List<String> employeeList) {
		String mailTitle = "";
		String mailBody = "";
		String cid = AppContexts.user().companyId();
		String appContent = appContentService.getApplicationContent(application);	
		ContentOfRemandMail remandTemp = remandRepo.getRemandMailById(cid).orElse(null);
		if (!Objects.isNull(remandTemp)) {
			mailTitle = remandTemp.getMailTitle().v();
			mailBody = remandTemp.getMailBody().v();
		}
		String emp = employeeAdapter.empEmail(AppContexts.user().employeeId());
		if (Strings.isEmpty(emp)) {
			emp = employeeAdapter.getEmployeeName(AppContexts.user().employeeId());
		}
		Optional<UrlEmbedded> urlEmbedded = urlEmbeddedRepo.getUrlEmbeddedById(AppContexts.user().companyId());
		List<String> successList = new ArrayList<>();
		List<String> errorList = new ArrayList<>();
		
		// Using RQL 419 instead (1 not have mail)
		//get list mail by list sID
		List<MailDestinationImport> lstMail = envAdapter.getEmpEmailAddress(cid, employeeList, 6);
		Optional<AppDispName> appDispName = repoAppDispName.getDisplay(application.getAppType().value);
		String appName = "";
		if(appDispName.isPresent()){
			appName = appDispName.get().getDispName().v();
		}
		String titleMail = application.getAppDate() + " " + appName;
		for (String employee : employeeList) {
			String employeeName = employeeAdapter.getEmployeeName(employee);
			OutGoingMailImport mail = envAdapter.findMailBySid(lstMail, employee);
			String employeeMail = mail == null || mail.getEmailAddress() == null ? "" : mail.getEmailAddress();
			// TODO
			String urlInfo = "";
			if (urlEmbedded.isPresent()) {
				int urlEmbeddedCls = urlEmbedded.get().getUrlEmbedded().value;
				NotUseAtr checkUrl = NotUseAtr.valueOf(urlEmbeddedCls);
				if (checkUrl == NotUseAtr.USE) {
					urlInfo = registerEmbededURL.obtainApplicationEmbeddedUrl(application.getAppID(),
							application.getAppType().value, application.getPrePostAtr().value, employee);
				}
			}
			if (!Strings.isBlank(urlInfo)) {
				appContent += "\n" + I18NText.getText("KDL030_30") + " " + application.getAppID() + "\n" + urlInfo;
			}
			String mailContentToSend = I18NText.getText("Msg_1060",
					employeeAdapter.getEmployeeName(AppContexts.user().employeeId()), mailBody,
					GeneralDate.today().toString(), application.getAppType().nameId,
					employeeAdapter.getEmployeeName(application.getEmployeeID()),
					application.getAppDate().toLocalDate().toString(), appContent,
					employeeAdapter.getEmployeeName(AppContexts.user().employeeId()), emp);
			if (Strings.isBlank(employeeMail)) {
				errorList.add(I18NText.getText("Msg_768", employeeName));
				continue;
			} else {
				try {
					mailsender.sendFromAdmin(employeeMail, new MailContents(titleMail, mailContentToSend));
					successList.add(employeeName);
				} catch (Exception ex) {
					throw new BusinessException("Msg_1057");
				}
			}
		}
		return new MailSenderResult(successList, errorList);
	}
}
