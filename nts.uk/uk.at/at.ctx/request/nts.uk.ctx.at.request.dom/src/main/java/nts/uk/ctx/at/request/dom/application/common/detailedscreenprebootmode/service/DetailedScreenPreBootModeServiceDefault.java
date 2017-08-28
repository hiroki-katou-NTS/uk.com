package nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.CanBeApprovedOutput;
import nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.OtherCommonAlgorithmService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailedScreenPreBootModeServiceDefault implements DetailedScreenPreBootModeService {

	@Inject
	OtherCommonAlgorithmService otherCommonAlgorithmService;

	@Inject
	AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	ApprovalFrameRepository approvalFrameRepository;

	@Override
	public DetailedScreenPreBootModeOutput getDetailedScreenPreBootMode(Application applicationData,
			GeneralDate baseDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// Output variables
		String outputApproverSID = "";
		ReflectPlanPerState outputStatus  = null;
		Boolean outputAuthorizableFlags = null;
		ApprovalAtr outputApprovalATR  = null;
		Boolean outputAlternateExpiration = null;
		
		List<GeneralDate> listDate = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID, employeeID, baseDate);
		GeneralDate startDate = listDate.get(0);
		GeneralDate endDate = listDate.get(1);

		AppApprovalPhase appApprovalPhase = appApprovalPhaseRepository
				.findPhaseByAppID(companyID, applicationData.getApplicationID()).get(0);
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		// Check if current user has in list Approver
		List<ApprovalFrame> filtedApprovalFrame = listApprovalFrame.stream()
				.filter(e -> e.getApproverSID() == employeeID).collect(Collectors.toList());
		boolean isUserIsApprover = !filtedApprovalFrame.isEmpty();
		String approverSID = filtedApprovalFrame.get(0).getApproverSID();
		
		if (startDate.after(applicationData.getApplicationDate()) == false) {
			outputStatus = applicationData.getReflectPlanState();
		} else {
			outputStatus = ReflectPlanPerState.OTHER;
		}

		if (checkAsApprover(applicationData)) {
			if (isUserIsApprover) {
				applicationData.setApplicantSID(employeeID);
				outputApproverSID = employeeID;
			} else {
				outputApproverSID = approverSID;
			}
			CanBeApprovedOutput canBeApprovedOutput = canBeApproved(applicationData, outputStatus);
			outputAuthorizableFlags = canBeApprovedOutput.getAuthorizableFlags();
			outputApprovalATR = canBeApprovedOutput.getApprovalATR();
			outputAlternateExpiration = canBeApprovedOutput.getAlternateExpiration();
		} else {
			if (isUserIsApprover) {
				outputApproverSID = applicationData.getApplicantSID();
			} else {
				outputApproverSID = "OTHER";
			}
		}
		
		return new DetailedScreenPreBootModeOutput(outputApproverSID, outputStatus, outputAuthorizableFlags, outputApprovalATR, outputAlternateExpiration);
	}

	@Override
	public boolean checkAsApprover(Application applicationData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status) {
		// TODO Auto-generated method stub
		return null;
	}

}
