package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.CanBeApprovedOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DecideAgencyExpiredOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformation;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BeforePreBootModeImpl implements BeforePreBootMode {
	/**14-2.詳細画面起動前モードの判断*/	
	@Inject
	OtherCommonAlgorithm otherCommonAlgorithmService;

	@Inject
	AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	ApprovalFrameRepository approvalFrameRepository;

	@Inject
	AfterApprovalProcess detailedScreenAfterApprovalProcessService;

	@Inject
	ApprovalAgencyInformation approvalAgencyInformationService;

	@Override
	public DetailedScreenPreBootModeOutput getDetailedScreenPreBootMode(Application applicationData,
			GeneralDate baseDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// Output variables
		User outputUser = null;
		ReflectPlanPerState outputStatus = null;
		boolean outputAuthorizableFlags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.UNAPPROVED;
		boolean outputAlternateExpiration = false;

		PeriodCurrentMonth listDate = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID,
				employeeID, baseDate);
		GeneralDate startDate = listDate.getStartDate();
		GeneralDate endDate = listDate.getEndDate();

		AppApprovalPhase appApprovalPhase = appApprovalPhaseRepository
				.findPhaseByAppID(companyID, applicationData.getApplicationID()).get(0);
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		// Check if current user has in list Approver
		List<ApprovalFrame> filtedApprovalFrame = listApprovalFrame.stream()
				.filter(e -> e.getApproverSID() == employeeID).collect(Collectors.toList());
		boolean isUserIsApprover = !filtedApprovalFrame.isEmpty();
		String approverSID = null;
		if(!filtedApprovalFrame.isEmpty()) {
			approverSID = filtedApprovalFrame.get(0).getApproverSID();
		}
		//get status
		if (startDate.after(applicationData.getApplicationDate()) == false) {
			outputStatus = applicationData.getReflectPlanState();
		} else {
			outputStatus = ReflectPlanPerState.PASTAPP;
		}
		//get User
		if (decideByApprover(applicationData)) {
			if (isUserIsApprover == true) {
				applicationData.setApplicantSID(employeeID);
				outputUser = User.APPLICANT_APPROVER;
			} else {
				outputUser = User.APPROVER;
			}
			CanBeApprovedOutput canBeApprovedOutput = canBeApproved(applicationData, outputStatus);
			outputAuthorizableFlags = canBeApprovedOutput.getAuthorizableFlags();
			outputApprovalATR = canBeApprovedOutput.getApprovalATR();
			outputAlternateExpiration = canBeApprovedOutput.getAlternateExpiration();
		} else {
			if (isUserIsApprover == true) {
				outputUser = User.APPLICANT;
			} else {
				outputUser = User.OTHER;
			}
		}

		return new DetailedScreenPreBootModeOutput(outputUser, outputStatus, outputAuthorizableFlags, outputApprovalATR,
				outputAlternateExpiration);
	}

	@Override
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status) {
		String companyID = AppContexts.user().companyId();
		// Output Data
		boolean outputAuthorizableflags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.UNAPPROVED;
		boolean outputAlternateExpiration = false;
		//Get List AppApprovalPhase
		if (status == ReflectPlanPerState.DENIAL || status == ReflectPlanPerState.WAITREFLECTION
				|| status == ReflectPlanPerState.NOTREFLECTED || status == ReflectPlanPerState.REMAND) {
			List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID,
					applicationData.getApplicationID());
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
				/** Truyen list rỗng 8231 */
				List<String> listApprover = new ArrayList<String>();
				//OutputApprovalATR
				if (!listApprover.isEmpty()) {
					if (checkFlag(applicationData, appApprovalPhase.getDispOrder())) {
						CanBeApprovedOutput canBeApprovedOutput = null;
						if (appApprovalPhase.getApprovalATR() == ApprovalAtr.REMAND) {
							canBeApprovedOutput = approvedRemand(appApprovalPhase);
						} else if (appApprovalPhase.getApprovalATR() == ApprovalAtr.APPROVED) {
							canBeApprovedOutput = approvedApproved(appApprovalPhase);
						} else if (appApprovalPhase.getApprovalATR() == ApprovalAtr.DENIAL) {
							canBeApprovedOutput = approvedDential(appApprovalPhase);
						} else if (appApprovalPhase.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
							canBeApprovedOutput = approvedUnapproved(appApprovalPhase);
						}
						outputAuthorizableflags = canBeApprovedOutput.getAuthorizableFlags();
						outputApprovalATR = canBeApprovedOutput.getApprovalATR();
						outputAlternateExpiration = canBeApprovedOutput.getAlternateExpiration();
						if (outputAuthorizableflags == true) {
							return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
									outputAlternateExpiration);
						}
					}
				}
			}
			return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
		} else {
			return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
		}
	}
	
	/** 14-2.3.4 Can be approvedUnapproved */
	private CanBeApprovedOutput approvedUnapproved(AppApprovalPhase appApprovalPhase) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// Initial output value
		boolean outputAuthorizableflags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.UNAPPROVED;
		boolean outputAlternateExpiration = false;
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			if (approvalFrame.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
				if (listApprovalFrame.contains(employeeID)) {
					outputAuthorizableflags = true;
					outputApprovalATR = ApprovalAtr.UNAPPROVED;
					outputAlternateExpiration = false;

				} else {
					DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(approvalFrame);

				}
			} else {
				outputApprovalATR = ApprovalAtr.APPROVED;
			}
		}
		return new CanBeApprovedOutput(true, ApprovalAtr.UNAPPROVED, false);
	}
	/** 14-2.3.3 Can be approvedDential */
	private CanBeApprovedOutput approvedDential(AppApprovalPhase appApprovalPhase) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// Initial output value
		boolean outputAuthorizableflags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.DENIAL;
		boolean outputAlternateExpiration = false;
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			// Whether the loginer is an approver
			if (approvalFrame.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
				// (login employee ID)
				if (approvalFrame.getApproverSID() == employeeID) {
					return new CanBeApprovedOutput(true, ApprovalAtr.DENIAL, false);
				} else {
					DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(approvalFrame);
					// Whether the login person is a substitute approver
					if (decideAgencyExpired.getOutputApprover().contains(employeeID)) {
						return new CanBeApprovedOutput(true, ApprovalAtr.DENIAL, false);
					} else {
						continue;
					}
				}
			} else { // Check whether the logger approved as approver
				if (approvalFrame.getApproverSID() == employeeID) {
					return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
				} else {
					// Check if login authorized as delegate approver
					if (approvalFrame.getRepresenterSID() == employeeID) {
						DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(approvalFrame);
						return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, decideAgencyExpired.isOutputAlternateExpiration());
					}
					else {
						continue;
					}
				}

			}
		}

		return new CanBeApprovedOutput(outputAuthorizableflags, ApprovalAtr.DENIAL, outputAlternateExpiration);
	}

	/** 14-2.3.2 Can be approvedRemand */
	@Override
	public CanBeApprovedOutput approvedRemand(AppApprovalPhase appApprovalPhase) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// Output Data
		boolean outputAuthorizableflags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.UNAPPROVED;
		boolean outputAlternateExpiration = false;
		//Get listApprovalFrame
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			if (employeeID == approvalFrame.getApproverSID()) {
				outputAuthorizableflags = true;
				return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
			} else {
				List<String> listApproverID = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
						appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR());
				if (listApproverID.contains(employeeID)) {
					outputAuthorizableflags = true;
					return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
							outputAlternateExpiration);
				}
			}
		}
		return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
	}
	
	/** 14-2.3.1 Can be approvedApproved */
	@Override
	public CanBeApprovedOutput approvedApproved(AppApprovalPhase appApprovalPhase) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();

		// Initial output value
		boolean outputAuthorizableflags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.UNAPPROVED;
		boolean outputAlternateExpiration = false;
		//Get listApprovalFrame
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		if (appApprovalPhase.getApprovalForm() == ApprovalForm.EVERYONEAPPROVED) {
			for (ApprovalFrame approvalFrame : listApprovalFrame) {
				if (employeeID == approvalFrame.getApproverSID()) {
					return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
				} else {
					if (employeeID == approvalFrame.getRepresenterSID()) {
						DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(approvalFrame);
						return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED,
								decideAgencyExpired.isOutputAlternateExpiration());
					}
				}
			}
			return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
		} else {
			List<ApprovalFrame> someoneApprovalConfirm = listApprovalFrame.stream()
					.filter(f -> f.getConfirmATR() == ConfirmAtr.USEATR_USE).collect(Collectors.toList());
			for (ApprovalFrame approvalFrame : listApprovalFrame) {
				if (!someoneApprovalConfirm.isEmpty()) {
					if (employeeID == approvalFrame.getApproverSID()) {
						return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
					} else {
						if (employeeID == approvalFrame.getRepresenterSID()) {
							outputAuthorizableflags = true;
							outputApprovalATR = ApprovalAtr.APPROVED;
							outputAlternateExpiration = false;
							return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
						} else {
							return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
									outputAlternateExpiration);
						}
					}
				} else {
					if (employeeID == approvalFrame.getApproverSID()
							&& approvalFrame.getApprovalATR() == ApprovalAtr.APPROVED) {
						return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
					} else {
						// Check Check whether the login person approved as an
						// agent
						if (employeeID == approvalFrame.getApproverSID()) {
							outputAuthorizableflags = true;
							outputApprovalATR = ApprovalAtr.APPROVED;
							DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(approvalFrame);
							return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED,
									decideAgencyExpired.isOutputAlternateExpiration());
						} else {
							return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
									outputAlternateExpiration);
						}
					}
				}
			}
			return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
		}
	}

	@Override
	public DecideAgencyExpiredOutput decideAgencyExpired(ApprovalFrame approvalFrame) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		List<String> approver = null;
		//Get list
		ApprovalAgencyInformationOutput approvalAgencyInformationOutput = approvalAgencyInformationService
				.getApprovalAgencyInformation(companyID, approver);
		List<String> outputApprover = approvalAgencyInformationOutput.getListApproverAndRepresenterSID().stream()
				.map(x -> x.getApprover()).collect(Collectors.toList());
		boolean outputAlternateExpiration = false;
		if (outputApprover.contains(employeeID)) {
			outputAlternateExpiration = false;
		} else {
			outputAlternateExpiration = true;
		}

		return new DecideAgencyExpiredOutput(outputApprover, outputAlternateExpiration);
	}
	/**Decide by Approver
	 * 14-2.詳細画面起動前モード�?判断- 2*/
	@Override
	public boolean decideByApprover(Application applicationData) {
		String companyID = AppContexts.user().companyId();
		boolean approverFlag = false ;
		List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository
				.findPhaseByAppID(companyID, applicationData.getApplicationID());
		for(AppApprovalPhase appApprovalPhase : listAppApprovalPhase ){
			List<String> listApproverID = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
					appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR());
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				return false;
	}

	@Override
	/*14-2 3-5.承認中の承認フェーズの判断 */
	public boolean checkFlag(Application applicationData, int dispOrder) {
		String companyID = AppContexts.user().companyId();
		boolean outputAuthorizableflags = false;
	
		if(1 <= dispOrder && dispOrder <=5 ){
			if(dispOrder == 1 ){
				//TODO
			}
			else if(dispOrder == 2 || dispOrder == 3 || dispOrder == 4){
				//TODO
			}
			else if(dispOrder == 5){}
			//TODO
		}
		
		
		
		return outputAuthorizableflags;
	}

}