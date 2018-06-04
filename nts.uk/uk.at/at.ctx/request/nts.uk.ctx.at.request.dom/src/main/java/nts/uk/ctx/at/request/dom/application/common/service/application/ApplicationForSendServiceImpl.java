package nts.uk.ctx.at.request.dom.application.common.service.application;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.OutGoingMailImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTemp;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTempRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationForSendServiceImpl implements IApplicationForSendService{
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject
	private ApprovalTempRepository appRep;
	
	@Inject 
	private IApplicationContentService appContentService;
	
	@Inject
	private EnvAdapter envAdapter;
	
	@Override
	public ApplicationForSendOutput getApplicationForSend(String appID) {
		String companyID = AppContexts.user().companyId();
		Optional<Application_New> application_New = this.applicationRepository.findByID(companyID, appID);
		ApprovalRootContentImport_New approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, null, null, null, appID, false);
		ApprovalRootOutput approvalRoot = this.fromApprovalRootToApprovalRootOutput(approvalRootContentImport);
		List<PesionInforImport> listApproverDetail = new ArrayList<PesionInforImport>();
		approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState().forEach(x -> { 
			x.getListApprovalFrame().forEach(y -> {
				y.getListApprover().forEach(z -> {
					listApproverDetail.add(employeeRequestAdapter.getEmployeeInfor(z.getApproverID()));
				});
			});
		});
		Optional<ApprovalTemp> appTemp = appRep.getAppTem();
		String appTempAsStr = "";
		if (appTemp.isPresent()){
			if (!Objects.isNull(appTemp.get().getContent())) {
				appTempAsStr = appTemp.get().getContent().v();
			}
		}
		if (application_New.isPresent()){
			Application_New app = application_New.get();
			PesionInforImport applicant = employeeRequestAdapter.getEmployeeInfor(app.getEmployeeID());
			String empName = Objects.isNull(applicant) ? "" : applicant.getPname();
			//RQL 419 (replace 225)
			List<MailDestinationImport> lstMail = envAdapter.getEmpEmailAddress(companyID, Arrays.asList(app.getEmployeeID()), 6);
			List<OutGoingMailImport> outGoingMails = lstMail.get(0).getOutGoingMails();
			String applicantMail = outGoingMails.isEmpty() ? "" : outGoingMails.get(0).getEmailAddress();
//			String applicantMail = "hiep.ld@3si.vn";
			PesionInforImport loginer = employeeRequestAdapter.getEmployeeInfor(AppContexts.user().employeeId());
			String loginName = Objects.isNull(loginer) ? "" : loginer.getPname();
			//get mail login : rq419
			List<MailDestinationImport> lstMailLogin = envAdapter.getEmpEmailAddress(companyID, Arrays.asList(AppContexts.user().employeeId()), 6);
			List<OutGoingMailImport> outMails = lstMailLogin.get(0).getOutGoingMails();
			String loginMail = outMails.isEmpty() ? "" : outMails.get(0).getEmailAddress();
//			String loginMail = "D00001@nittsusystime.co.jp";
			String appContent = appContentService.getApplicationContent(application_New.get());
			String mailContentToSend = I18NText.getText("Msg_703",
					loginName, appTempAsStr,
					GeneralDate.today().toString(), app.getAppType().nameId,
					empName, app.getAppDate().toLocalDate().toString(),
					appContent, loginName, loginMail);
			return new ApplicationForSendOutput(app, mailContentToSend, approvalRoot, applicantMail);
		}
		return null;
	}
	private ApprovalRootOutput fromApprovalRootToApprovalRootOutput(ApprovalRootContentImport_New approvalRoot){
		ApprovalRootOutput approvalRootOutput = ApprovalRootOutput.fromApprovalRootImportToOutput(approvalRoot);
		// set email
		String cid = AppContexts.user().companyId();
		approvalRootOutput.getListApprovalPhaseState().forEach(x -> {
			x.getListApprovalFrame().forEach(y -> {
				y.getListApprover().forEach(z ->{
					//RQL 419 (replace 225)
					List<MailDestinationImport> lstMail = envAdapter.getEmpEmailAddress(cid, Arrays.asList(z.getApproverID()), 6);
					List<OutGoingMailImport> outGoingMails = lstMail.get(0).getOutGoingMails();
					String sMail = outGoingMails.isEmpty() ? "" : outGoingMails.get(0).getEmailAddress();
//					String sMail = "hiep.ld@3si.vn";
					z.setSMail(sMail);
				});
			});
		});
		return approvalRootOutput;
	}
}
