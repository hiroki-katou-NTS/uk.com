package nts.uk.ctx.at.request.dom.application.common.service.application;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> pj/at/dev/Team_D/KDL034
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
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;
<<<<<<< HEAD
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;
=======
>>>>>>> pj/at/dev/Team_D/KDL034
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
	
	@Override
	public ApplicationForSendOutput getApplicationForSend(String appID) {
		String companyID = AppContexts.user().companyId();
		Optional<Application_New> application_New = this.applicationRepository.findByID(companyID, appID);
		ApprovalRootContentImport_New approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, null, null, null, appID, false);
<<<<<<< HEAD
		ApprovalRootOutput approvalRoot = this.fromApprovalRootToApprovalRootOutput(approvalRootContentImport);
=======
		List<PesionInforImport> listApproverDetail = new ArrayList<PesionInforImport>();
		approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState().forEach(x -> { 
			x.getListApprovalFrame().forEach(y -> {
				y.getListApprover().forEach(z -> {
					listApproverDetail.add(employeeRequestAdapter.getEmployeeInfor(z.getApproverID()));
				});
			});
		});
>>>>>>> pj/at/dev/Team_D/KDL034
		Optional<ApprovalTemp> appTemp = appRep.getAppTem();
		String appTempAsStr = "";
		if (appTemp.isPresent()){
			if (!Objects.isNull(appTemp.get().getContent())) {
				appTempAsStr = appTemp.get().getContent().v();
			}
		}
		if (application_New.isPresent()){
			Application_New app = application_New.get();
<<<<<<< HEAD
			PesionInforImport applicant = employeeRequestAdapter.getEmployeeInfor(app.getEmployeeID());
			String empName = Objects.isNull(applicant) ? "" : applicant.getPname();
			String applicantMail = "hiep.ld@3si.vn";
			PesionInforImport loginer = employeeRequestAdapter.getEmployeeInfor(AppContexts.user().employeeId());
			String loginName = Objects.isNull(loginer) ? "" : loginer.getPname();
			String loginMail = "D00001@nittsusystime.co.jp";
=======
			String loginName = "D00001";
			String loginMail = "D00001@nittsusystime.co.jp";
			String empName = "D00001 name";
>>>>>>> pj/at/dev/Team_D/KDL034
			String appContent = appContentService.getApplicationContent(application_New.get());
			String mailContentToSend = I18NText.getText("Msg_703",
					loginName, appTempAsStr,
					GeneralDate.today().toString(), app.getAppType().nameId,
					empName, app.getAppDate().toLocalDate().toString(),
					appContent, loginName, loginMail);
<<<<<<< HEAD
			return new ApplicationForSendOutput(app, mailContentToSend, approvalRoot, applicantMail);
		}
		return null;
	}
	private ApprovalRootOutput fromApprovalRootToApprovalRootOutput(ApprovalRootContentImport_New approvalRoot){
		ApprovalRootOutput approvalRootOutput = ApprovalRootOutput.fromApprovalRootImportToOutput(approvalRoot);
		// set email
		approvalRootOutput.getListApprovalPhaseState().forEach(x -> {
			x.getListApprovalFrame().forEach(y -> {
				y.getListApprover().forEach(z ->{
					//RQL 419 (replace 225)
					String sMail = "hiep.ld@3si.vn";
					z.setSMail(sMail);
				});
			});
		});
		return approvalRootOutput;
	}
=======
			return new ApplicationForSendOutput(app, mailContentToSend, approvalRootContentImport, listApproverDetail, employeeRequestAdapter.getEmployeeInfor(app.getEmployeeID()));
		}
		return null;
	}
	
>>>>>>> pj/at/dev/Team_D/KDL034
}
