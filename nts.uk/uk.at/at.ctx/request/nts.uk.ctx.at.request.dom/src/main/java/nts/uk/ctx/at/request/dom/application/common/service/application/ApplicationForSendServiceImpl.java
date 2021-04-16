package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.PesionInforImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.AppSendMailByEmp;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.emailset.Division;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationForSendServiceImpl implements IApplicationForSendService{
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private AppEmailSetRepository appEmailSetRepository;
	/**
	 * ダイアログを開く kdl030
	 */
	@Override
	public ApplicationForSendOutput getApplicationForSend(List<String> appIDLst) {
		String companyID = AppContexts.user().companyId();
		List<AppSendMailByEmp> appSendMailByEmpLst = new ArrayList<>();
		// ドメインモデル「申請メール設定」を取得する(get domain model 「」)
		AppEmailSet appEmailSet = appEmailSetRepository.findByDivision(Division.APPLICATION_APPROVAL);
		// ドメイン「申請」より各種情報を取得する
		List<Application> applicationLst = applicationRepository.findByIDLst(appIDLst);
		// imported（申請承認）「社員メールアドレス」を取得する 
		List<MailDestinationImport> lstApplicantMail = envAdapter.getEmpEmailAddress(
				companyID, 
				applicationLst.stream().map(x -> x.getEmployeeID()).collect(Collectors.toList()), 
				6);
		// メール本文を編集する
		PesionInforImport loginer = employeeRequestAdapter.getEmployeeInfor(AppContexts.user().employeeId());
		String loginName = Objects.isNull(loginer) ? "" : loginer.getPname();
		String mailTemplate = I18NText.getText("Msg_2151", 
				loginName, 
				appEmailSet.getEmailContentLst().stream().findFirst().map(x -> x.getOpEmailText().map(y -> y.v()).orElse("")).orElse(""));
		// imported（申請承認）「社員名（ビジネスネーム）」を取得する
		Map<String, String> applicantNameMap = new HashMap<>();
		for(Application application : applicationLst) {
			PesionInforImport applicant = employeeRequestAdapter.getEmployeeInfor(application.getEmployeeID());
			String empName = Objects.isNull(applicant) ? "" : applicant.getPname();
			applicantNameMap.put(application.getEmployeeID(), empName);
		}
		// 承認ルートの内容取得
		for(Application application : applicationLst) {
			List<ApprovalPhaseStateImport_New> phaseLst = approvalRootStateAdapter.getApprovalDetail(application.getAppID());
			ApprovalRootContentImport_New approvalRootContentImport = new ApprovalRootContentImport_New(
					new ApprovalRootStateImport_New(
							application.getAppID(), 
							phaseLst, 
							application.getAppDate().getApplicationDate()), 
					ErrorFlagImport.NO_ERROR);
			ApprovalRootOutput approvalRoot = ApprovalRootOutput.fromApprovalRootImportToOutput(approvalRootContentImport);
			String applicantName = applicantNameMap.get(application.getEmployeeID());
			/**
			 * 申請者のメールアドレス
			 */
			String applicantMail = lstApplicantMail.stream().filter(x -> x.getEmployeeID().equals(application.getEmployeeID()))
					.findAny().map(x -> x.getOutGoingMails().stream().findFirst().map(y -> y.getEmailAddress()).orElse("")).orElse("");
			
			appSendMailByEmpLst.add(new AppSendMailByEmp(approvalRoot, application, applicantName, applicantMail));
		}
		
		return new ApplicationForSendOutput(mailTemplate, appEmailSet, appSendMailByEmpLst);
	}
}
