package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import javax.ejb.Stateless;
//import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
//import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
//import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcess;
//import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
//import nts.uk.shr.com.mail.MailSender;

/**
 * 
 * @author tutk
 *
 */
@Stateless
public class AfterProcessReturnImpl implements AfterProcessReturn {
//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeSettingRepo;
//	@Inject
//	private AgentAdapter  approvalAgencyInformationServiceRepo;
//	@Inject
//	private DestinationJudgmentProcess destinationJudgmentProcessServiceRepo;
//	@Inject
//	private MailSender mailSender;
	@Override
	public void detailScreenProcessAfterReturn(Application application, boolean checkApplicant, int orderPhase) {
		/*
		//String companyID = AppContexts.user().companyId();
		// if 差し戻し先が申請本人の場合(the applicant )
		
		//list application phase
		List<AppApprovalPhase> listPhase = phaseRepo.findPhaseByAppID(application.getCompanyID(), application.getApplicationID());
		// get information application setting by companyID  and app type
		Optional<AppTypeDiscreteSetting> appSetting = appTypeSettingRepo.getAppTypeDiscreteSettingByAppType(
				application.getCompanyID(), 
				application.getApplicationType().value);
		//list send email
		List<String> listDestination = new ArrayList<>(); 
		if(checkApplicant){
			for(AppApprovalPhase appApprovalPhase:listPhase) {
				appApprovalPhase.setApprovalForm(null);
				appApprovalPhase.setApprovalATR(null);
				// set value setApprovalForm and setApprovalATR = null
				phaseRepo.update(appApprovalPhase);
				List<ApprovalFrame> listFrame = frameRepo.getAllApproverByPhaseID(appApprovalPhase.getCompanyID(), appApprovalPhase.getPhaseID());
				//2017.09.25 DuDT
//				for(ApprovalFrame approvalFrame:listFrame) {
//					approvalFrame.setApprovalATR(null);
//					approvalFrame.setApprovalDate(null);
//					approvalFrame.setApproverSID(null);
//					approvalFrame.setReason(null);
//					approvalFrame.setRepresenterSID(null);
//					// set value frame =  null
//					frameRepo.update(approvalFrame);
//				}
				for(ApprovalFrame approvalFrame:listFrame) {
					approvalFrame.getListApproveAccepted().forEach(x -> {
						x.changeApprovalATR(ApprovalAtr.UNAPPROVED);
						x.changeApproverSID("");
						x.changeRepresenterSID("");
						x.changeReason(new Reason(""));
						x.changeApprovalDate(null);
					});
					frameRepo.update(approvalFrame, appApprovalPhase.getPhaseID());
				}
				//2017.09.25 DuDT
			}
			
			
			//update stateReflectionReal
			application.setReflectPerState(ReflectPlanPerState.REMAND);
			appRepo.updateApplication(application);
			
			//check : send mail when approval = can
			if(appSetting.get().getSendMailWhenApprovalFlg() == AppCanAtr.CAN) {
				//send mail 
			}
			
		}else {//else 差し戻し先が承認者の場合(the approver)
			for(int i=0;i<orderPhase;i++) {
				listPhase.get(i).setApprovalATR(ApprovalAtr.UNAPPROVED);
				listPhase.get(i).setApprovalForm(null);
				phaseRepo.update(listPhase.get(i));
				List<ApprovalFrame> listFrame = frameRepo.getAllApproverByPhaseID(
						listPhase.get(i).getCompanyID(), 
						listPhase.get(i).getPhaseID());
				// 2017.09.25
				for(ApprovalFrame approvalFrame:listFrame) {
					approvalFrame.setApprovalATR(null);
					approvalFrame.setApprovalDate(null);
					approvalFrame.setApproverSID(null);
					approvalFrame.setReason(null);
					approvalFrame.setRepresenterSID(null);
					// set value frame =  null
					frameRepo.update(approvalFrame);
				}
				
				for(ApprovalFrame approvalFrame:listFrame) {
					approvalFrame.getListApproveAccepted().forEach(x -> {
						x.changeApprovalATR(ApprovalAtr.UNAPPROVED);
						x.changeApproverSID("");
						x.changeRepresenterSID("");
						x.changeReason(new Reason(""));
						x.changeApprovalDate(null);
					});
					frameRepo.update(approvalFrame, listPhase.get(i).getPhaseID());
				}
				// 2017.09.25
			}
			//check : send mail when register = can
			if(appSetting.get().getSendMailWhenRegisterFlg() == AppCanAtr.CAN) {
				//get 8.2 -> 3 -> 3.1
				List<String> listApprover = new ArrayList<>();
				//get List Approver And RepresenterSID
				List<ApproverRepresenterImport> getListApproverRep = approvalAgencyInformationServiceRepo
						.getApprovalAgencyInformation(
						application.getCompanyID(), 
						listApprover).getListApproverAndRepresenterSID();
				List<String> listSendMail = destinationJudgmentProcessServiceRepo
						.getDestinationJudgmentProcessService(getListApproverRep);
				// check duplicate and delete for list
				 listDestination = listSendMail.stream()
						.distinct()
						.collect(Collectors.toList());
				//send mail
			}
		}
		//show messeger 223
		if(listDestination.size()>0) {
			//Imported(就業)「社員」を取得する
			//情報メッセージに（Msg_392）を表示する
			
			listDestination.stream().forEach(x -> {
				try {
					if(!Strings.isBlank(x)){
						mailSender.send("nts", x, new MailContents("nts mail", "return mail from NTS"));
					}
					
				} catch (SendMailFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			});
			
		}
		*/	
		
	}
	
	

}
