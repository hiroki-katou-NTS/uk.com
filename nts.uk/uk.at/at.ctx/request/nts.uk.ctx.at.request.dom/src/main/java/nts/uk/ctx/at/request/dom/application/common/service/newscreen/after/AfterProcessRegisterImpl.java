package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformation;
import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

@Stateless
public class AfterProcessRegisterImpl implements AfterProcessRegister {
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private AfterApprovalProcess detailedScreenAfterApprovalProcessService;
	
	@Inject
	private ApprovalAgencyInformation approvalAgencyInformationService;
	
	@Inject
	private DestinationJudgmentProcess destinationJudgmentProcessService;
	
	public void processAfterRegister(String companyID, String appID){
		Optional<Application> applicationOp = applicationRepository.getAppById(companyID, appID);
		if(!applicationOp.isPresent()) return;
		Application application = applicationOp.get();
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, application.getApplicationType().value);
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)) return;
		List<String> destinationList = acquireDestinationList(application);
		if(destinationList.size() < 1) return;
		for(String destination : destinationList) {
			// sendMail(obj);
			// Imported(Employment)[Employee]; // Imported(就業)「社員」 ??? 
		}
	}
	
	public List<String> acquireDestinationList(Application application){
		List<String> destinationList = new ArrayList<>();
		Optional<Application> appOp = applicationRepository.getAppById(application.getCompanyID(), application.getApplicationID());
		Application app = appOp.get();
		if(app.getReflectPerState().equals(ReflectPlanPerState.NOTREFLECTED)) return destinationList;
		List<AppApprovalPhase> appApprovalPhases = appApprovalPhaseRepository.findPhaseByAppID(application.getCompanyID(), application.getApplicationID());
		for( AppApprovalPhase appApprovalPhase : appApprovalPhases) {
			if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.DENIAL)||appApprovalPhase.getApprovalATR().equals(ApprovalAtr.REMAND)){
				break;
			} else if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.APPROVED)) {
				continue;
			} else {
				// アルゴリズム「L」を実行する ( Execute algorithm "Acquire approver list" ) 
				List<String> apPhases = 
						detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
								application.getApplicationID(), 
								appApprovalPhase.getPhaseID(), 
								ApprovalAtr.APPROVED);
				if(apPhases.size() < 1 ) continue;
				
				// アルゴリズム「未承認の承認者一覧を取得する」を実行する ( Execute algorithm "Acquire unapproved approver list" )
				List<String> unApPhases = 
						detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
								application.getApplicationID(), 
								appApprovalPhase.getPhaseID(), 
								ApprovalAtr.UNAPPROVED);
				for(String unApPhase : unApPhases){
					for(String apPhase : apPhases) {
						if(unApPhase.equals(apPhase)) break;
					}
				}
				
				// アルゴリズム「承認代行情報の取得処理」を実行する ( Executes the algorithm "acquisition process of approval substitution information" )
				ApprovalAgencyInformationOutput agencyInformationOutput = approvalAgencyInformationService.getApprovalAgencyInformation(appApprovalPhase.getCompanyID(), unApPhases);
				
				// アルゴリズム「送信先の判断処理」を実行する ( Executes the algorithm "destination determination process" )
				List<String> result = destinationJudgmentProcessService.getDestinationJudgmentProcessService(agencyInformationOutput.getListApproverAndRepresenterSID());
				destinationList.addAll(result);
			}
		}
		destinationList.stream().distinct().collect(Collectors.toList());
		return destinationList;
	}
}
