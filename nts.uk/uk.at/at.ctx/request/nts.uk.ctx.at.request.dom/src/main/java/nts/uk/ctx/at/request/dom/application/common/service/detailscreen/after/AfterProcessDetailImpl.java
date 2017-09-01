package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformation;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.application.common.valueobject.ApproverResult;
import nts.uk.ctx.at.request.dom.application.common.valueobject.ApproverWhoApproved;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

@Stateless
public class AfterProcessDetailImpl implements AfterProcessDetail {

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	private ApprovalFrameRepository approvalFrameRepository;

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;

	@Inject
	private ApprovalAgencyInformation approvalAgencyInformationService;

	@Inject
	private AfterApprovalProcess detailedScreenAfterApprovalProcessService;

	public void processAfterDetailScreenRegistration(String companyID, String appID) {
		List<String> destinationList = new ArrayList<>();
		Optional<Application> applicationOptional = applicationRepository.getAppById(companyID, appID);
		if (!applicationOptional.isPresent())
			return;
		List<AppApprovalPhase> appApprovalPhases = appApprovalPhaseRepository.findPhaseByAppID(companyID, appID);
		ApproverResult approverResult = acquireApproverWhoApproved(appApprovalPhases);
		List<ApproverWhoApproved> approverWhoApproveds = approverResult.getApproverWhoApproveds();
		List<ApproverImport> approvers = approverResult.getApprovers();
		Application application = applicationOptional.get();
		// application.reversionReason = "";
		// application.reflectPerTime = NOTREFLECTED;
		applicationRepository.updateApplication(application);
		for (AppApprovalPhase appApprovalPhase : appApprovalPhases) {
			// appApprovalPhase.approvalATR = UNAPPROVED;
			List<ApprovalFrame> approvalFrames = approvalFrameRepository.getAllApproverByPhaseID(companyID, appID);
			for (ApprovalFrame approvalFrame : approvalFrames) {
				// approvalFrame.approvalATR = UNAPPROVED;
				// approvalFrame.approverSID = "";
				// approvalFrame.representerSID = "";
				// approvalFrame.reason = "";
				// approvalFrame.approvalDate = "";
				approvalFrameRepository.update(approvalFrame);
			}
		}
		if (approverWhoApproveds.size() < 1)
			return;
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository
				.getAppTypeDiscreteSettingByAppType(companyID, application.getApplicationType().value);
		if (appTypeDiscreteSettingOp.get().getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN))
			return;
		for (ApproverWhoApproved approverWhoApproved : approverWhoApproveds) {
			if (approverWhoApproved.isAgentFlag()) {
				ApprovalAgencyInformationOutput agencyInformationOutput = approvalAgencyInformationService
						.getApprovalAgencyInformation(companyID,
								approvers.stream().map(x -> x.getApproverId()).collect(Collectors.toList()));
				for (String id : agencyInformationOutput.getListRepresenterSID()) {
					if (id.equals(approverWhoApproved.getApproverAdaptorDto())) {
						destinationList.add(approverWhoApproved.getApproverAdaptorDto());
					}
				}
			} else {
				destinationList.add(approverWhoApproved.getApproverAdaptorDto());
			}
		}
		destinationList.stream().distinct().collect(Collectors.toList());
		if (destinationList.size() >= 1) {
			// sendMail();
			// Imported(Employment)[Employee]; // Imported(就業)「社員」を取得する ???
		}
	}

	public ApproverResult acquireApproverWhoApproved(List<AppApprovalPhase> appApprovalPhases){
		ApproverResult approverResult = new ApproverResult();
		for(AppApprovalPhase appApprovalPhase : appApprovalPhases){
			List<String> approverList = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), ApprovalAtr.APPROVED);
			if(!approverList.isEmpty()){
				List<ApprovalFrame> approvalFrames = approvalFrameRepository.getAllApproverByPhaseID(appApprovalPhase.getCompanyID(), appApprovalPhase.getAppID());
				for(ApprovalFrame approvalFrame : approvalFrames) {
					if(approvalFrame.getApprovalATR().equals(ApprovalAtr.APPROVED)){
						if(Strings.isNotEmpty(approvalFrame.getRepresenterSID())){
							approverResult.getApproverWhoApproveds().add(new ApproverWhoApproved(approvalFrame.getRepresenterSID(), true));
						} else {
							approverResult.getApproverWhoApproveds().add(new ApproverWhoApproved(approvalFrame.getApproverSID(), false));
						}
						// approverList.add(item1.approverList);
					}	
				}
			}
		}
		return approverResult;
	}
}
