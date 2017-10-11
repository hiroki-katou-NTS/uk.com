package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.CanBeApprovedOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DecideAgencyExpiredOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BeforePreBootModeImpl implements BeforePreBootMode {
	/** 14-2.詳細画面起動前モードの判断 */
	@Inject
	OtherCommonAlgorithm otherCommonAlgorithmService;

	@Inject
	AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	ApprovalFrameRepository approvalFrameRepository;

	@Inject
	AfterApprovalProcess detailedScreenAfterApprovalProcessService;

	@Inject
	AgentAdapter approvalAgencyInformationService;

	@Inject
	ApproveAcceptedRepository approveAcceptedRepository;

	/**
	 * 
	 */
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
		// get status
		if (startDate.after(applicationData.getApplicationDate()) == false) {
			outputStatus = applicationData.getReflectPlanState();
		} else {
			outputStatus = ReflectPlanPerState.PASTAPP;
		}
		
		List<AppApprovalPhase> appApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID, applicationData.getApplicationID());
		for (AppApprovalPhase phase : appApprovalPhase) {
			List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID, phase.getPhaseID());
			for (ApprovalFrame approvalFrame : listApprovalFrame) {
				approvalFrame.setListApproveAccepted(approvalFrame.getListApproveAccepted());
			}
		}
		// get User
		// ドメインモデル「申請」．申請者 = ログイン者社員ID がtrue
		// "Application".Applicant = login If employee ID is true
		if (decideByApprover(applicationData)) {
			if (applicationData.getApplicantSID() == employeeID) {
				outputUser = User.APPLICANT_APPROVER;
			} else {
				outputUser = User.APPROVER;
			}
			CanBeApprovedOutput canBeApprovedOutput = canBeApproved(applicationData, outputStatus);
			outputAuthorizableFlags = canBeApprovedOutput.getAuthorizableFlags();
			outputApprovalATR = canBeApprovedOutput.getApprovalATR();
			outputAlternateExpiration = canBeApprovedOutput.getAlternateExpiration();
		} else {
			if (applicationData.getEnteredPersonSID() == employeeID) {
				outputUser = User.APPLICANT;
			} else {
				outputUser = User.OTHER;
			}
		}
		// 利用者をチェックする(Check người sử dụng)
		// Check the user (Check ngườử sử dụng)
		if (outputUser == User.APPLICANT_APPROVER || outputUser == User.APPROVER) {
			// アルゴリズム「承認できるかの判断」を実行する(phán đoán xem có thể approve hay không)

		}
		return new DetailedScreenPreBootModeOutput(outputUser, outputStatus, outputAuthorizableFlags, outputApprovalATR,
				outputAlternateExpiration);
	}

	/**
	 * 14.2-3.承認できるかの判断
	 */
	@Override
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status) {
		String companyID = AppContexts.user().companyId();
		// Output Data
		boolean outputAuthorizableflags = false;
		ApprovalAtr outputApprovalATR = ApprovalAtr.UNAPPROVED;
		boolean outputAlternateExpiration = false;
		// Get List AppApprovalPhase
		if (status == ReflectPlanPerState.DENIAL || status == ReflectPlanPerState.WAITREFLECTION
				|| status == ReflectPlanPerState.NOTREFLECTED || status == ReflectPlanPerState.REMAND) {
			List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID,
					applicationData.getApplicationID());
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
				// アルゴリズム「承認者一覧を取得する」を実行する
				// Execute algorithm "Acquire approver list"
				List<String> listApprover = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
						applicationData.getApplicationID(), appApprovalPhase.getPhaseID(), ApprovalAtr.APPROVED);
				// OutputApprovalATR
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

			List<ApproveAccepted> listApproveAccepted = approveAcceptedRepository
					.getAllApproverAccepted(approvalFrame.getCompanyID(), approvalFrame.getFrameID());
			for (ApproveAccepted approveAccepted : listApproveAccepted) {
				// 「承認枠」．承認区分が未承認
				// "Approval frame".Approval ATR =Not Approved

				if (approveAccepted.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
					// 「承認枠」．承認者リスト. Contains(ログイン者社員ID)
					// "Approval frame". Approver list. Contains (login employee
					// ID)
					if (approveAccepted.getApproverSID() == employeeID) {
						outputAuthorizableflags = true;
						outputApprovalATR = ApprovalAtr.UNAPPROVED;
						outputAlternateExpiration = false;
						return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
								outputAlternateExpiration);
					} else {
						// DecideAgencyExpiredOutput decideAgencyExpired =
						// decideAgencyExpired(approvalFrame);
						List<String> approver = null;
						AgentPubImport approvalAgencyInformationOutput = approvalAgencyInformationService
								.getApprovalAgencyInformation(companyID, approver);

						if (approvalAgencyInformationOutput.getListRepresenterSID().contains(employeeID)) {
							outputAuthorizableflags = true;
							outputApprovalATR = ApprovalAtr.UNAPPROVED;
							outputAlternateExpiration = false;
							return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
									outputAlternateExpiration);

						} else {
							continue;
						}
					}
				} else {
					// 「承認枠」．承認者 == ログイン者社員ID
					// "Approval frame".Approver == Login Employee ID
					if (approveAccepted.getApproverSID() == employeeID) {
						outputAuthorizableflags = true;
						outputApprovalATR = ApprovalAtr.APPROVED;
						outputAlternateExpiration = false;
						return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
								outputAlternateExpiration);

					} else {
						// 「承認枠」．代行者 == ログイン者社員ID
						// "Approval frame".substitute == login employee ID
						if (approveAccepted.getRepresenterSID() == employeeID) {
							outputAuthorizableflags = true;
							outputApprovalATR = ApprovalAtr.APPROVED;
							DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(
									approvalFrame.getListApproveAccepted());
							outputAlternateExpiration = decideAgencyExpired.isOutputAlternateExpiration();
							return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
									outputAlternateExpiration);

						} else {
							continue;
						}
					}
				}
			}
		}
		return null;
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
			List<ApproveAccepted> listApproveAccepted = approveAcceptedRepository
					.getAllApproverAccepted(approvalFrame.getCompanyID(), approvalFrame.getFrameID());
			for (ApproveAccepted approveAccepted : listApproveAccepted) {
				// Whether the loginer is an approver
				if (approveAccepted.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
					// (login employee ID)
					if (approveAccepted.getApproverSID() == employeeID) {
						return new CanBeApprovedOutput(true, ApprovalAtr.DENIAL, false);
					} else {
						DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(
								approvalFrame.getListApproveAccepted());
						// Whether the login person is a substitute approver
						if (decideAgencyExpired.getOutputApprover().contains(employeeID)) {
							return new CanBeApprovedOutput(true, ApprovalAtr.DENIAL, false);
						} else {
							continue;
						}
					}
				} else { // Check whether the logger approved as approver
					if (approveAccepted.getApproverSID() == employeeID) {
						return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
					} else {
						// Check if login authorized as delegate approver
						if (approveAccepted.getRepresenterSID() == employeeID) {
							DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(
									approvalFrame.getListApproveAccepted());
							return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED,
									decideAgencyExpired.isOutputAlternateExpiration());
						} else {
							continue;
						}
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
		// Get listApprovalFrame
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());

		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			List<ApproveAccepted> listApproveAccepted = approveAcceptedRepository
					.getAllApproverAccepted(approvalFrame.getCompanyID(), approvalFrame.getFrameID());
			for (ApproveAccepted approveAccepted : listApproveAccepted) {
				// ログイン者が承認者かチェックする
				// Whether the loginer is an approver
				if (employeeID == approveAccepted.getApproverSID()) {
					outputAuthorizableflags = true;
					return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
							outputAlternateExpiration);
				} else {
					appApprovalPhase.getApprovalATR();
					List<String> listApproverID = detailedScreenAfterApprovalProcessService
							.actualReflectionStateDecision(appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(),
									ApprovalAtr.APPROVED);
					if (listApproverID.contains(employeeID)) {
						outputAuthorizableflags = true;
						return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
								outputAlternateExpiration);
					}
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
		// Get listApprovalFrame
		List<ApprovalFrame> listApprovalFrame = approvalFrameRepository.getAllApproverByPhaseID(companyID,
				appApprovalPhase.getPhaseID());
		if (appApprovalPhase.getApprovalForm() == ApprovalForm.EVERYONEAPPROVED) {
			for (ApprovalFrame approvalFrame : listApprovalFrame) {
				List<ApproveAccepted> listApproveAccepted = approveAcceptedRepository
						.getAllApproverAccepted(approvalFrame.getCompanyID(), approvalFrame.getFrameID());
				for (ApproveAccepted approveAccepted : listApproveAccepted) {
					if (employeeID == approveAccepted.getApproverSID()) {
						return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
					} else {
						if (employeeID == approveAccepted.getRepresenterSID()) {
							DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(
									approvalFrame.getListApproveAccepted());
							return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED,
									decideAgencyExpired.isOutputAlternateExpiration());
						}
					}

				}
			}
			return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
		} else {
			// 2017.09.25
			/*
			 * List<ApprovalFrame> listApprovalFrame =
			 * approvalFrameRepository.getAllApproverByPhaseID(companyID,
			 * appApprovalPhase.getPhaseID());
			 */
			for (ApprovalFrame approvalFrame : listApprovalFrame) {
				List<ApproveAccepted> listApproveAccepted = approveAcceptedRepository
						.getAllApproverAccepted(approvalFrame.getCompanyID(), approvalFrame.getFrameID());
				for (ApproveAccepted approveAccepted : listApproveAccepted) {
					List<ApproveAccepted> someoneApprovalConfirm = listApproveAccepted.stream()
							.filter(f -> f.getConfirmATR() == ConfirmAtr.USEATR_USE).collect(Collectors.toList());

					if (!someoneApprovalConfirm.isEmpty()) {
						if (employeeID == approveAccepted.getApproverSID()) {
							return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
						} else {
							if (employeeID == approveAccepted.getRepresenterSID()) {
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
						if (employeeID == approveAccepted.getApproverSID()
								&& approveAccepted.getApprovalATR() == ApprovalAtr.APPROVED) {
							return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED, false);
						} else { // Check Check whether the login person
									// approved as
									// an // agent
							if (employeeID == approveAccepted.getApproverSID()) {
								outputAuthorizableflags = true;
								outputApprovalATR = ApprovalAtr.APPROVED;
								DecideAgencyExpiredOutput decideAgencyExpired = decideAgencyExpired(
										approvalFrame.getListApproveAccepted());
								return new CanBeApprovedOutput(true, ApprovalAtr.APPROVED,
										decideAgencyExpired.isOutputAlternateExpiration());
							} else {
								return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR,
										outputAlternateExpiration);
							}
						}
					}
				}
			}
		}

		return new CanBeApprovedOutput(outputAuthorizableflags, outputApprovalATR, outputAlternateExpiration);
	}

	/**
	 * 6.代行者期限切れの判断
	 */
	@Override
	public DecideAgencyExpiredOutput decideAgencyExpired(List<ApproveAccepted> lstApproved) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// List<String> approver = null;
		List<String> listID = lstApproved.stream().map(x -> x.getApproverSID()).collect(Collectors.toList());
		// Get list
		AgentPubImport approvalAgencyInformationOutput = approvalAgencyInformationService
				.getApprovalAgencyInformation(companyID, listID);
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

	/**
	 * Decide by Approver 14-2.詳細画面起動前モード�?判断- 2
	 */
	@Override
	public boolean decideByApprover(Application applicationData) {
		String companyID = AppContexts.user().companyId();
		String logger = AppContexts.user().userId();
		String employeeID = AppContexts.user().employeeId();
		boolean approverFlag = false;
		List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID,
				applicationData.getApplicationID());
		List<String> listApproverID = new ArrayList<String>();

		for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
			listApproverID.addAll(detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
					appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR()));
		}
		// Remove duplicate Approver
		listApproverID = listApproverID.stream().distinct().collect(Collectors.toList());
		if (listApproverID.contains(logger)) {
			approverFlag = true;
		} else {
			val approvalAgencyInformationOutput = approvalAgencyInformationService
					.getApprovalAgencyInformation(companyID, listApproverID);
			if (approvalAgencyInformationOutput.getListRepresenterSID().contains(logger)) {
				approverFlag = true;
			}
		}
		// 承認者フラグをチェックする
		// Check Approver Flag
		if (approverFlag == false){ 
			// get all lstApproverSID
			List<String> lstRepresenterSID = new ArrayList<>();
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
				for (ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()) {
					List<String> lstTemp = approvalFrame.getListApproveAccepted().stream().map(item -> {
						return item.getApproverSID();
					}).collect(Collectors.toList());
					lstRepresenterSID.addAll(lstTemp);
				}
			}
			if (lstRepresenterSID.contains(employeeID)) {
				approverFlag = true;
			} else {
				approverFlag = true;
			}
		} else {
			approverFlag = false;
		}
		return approverFlag;
	}

	@Override
	/* 14-2 3-5.承認中の承認フェーズの判断 */
	public boolean checkFlag(Application applicationData, int dispOrder) {
		String companyID = AppContexts.user().companyId();
		boolean outputAuthorizableflags = false;
		List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID,
				applicationData.getApplicationID());
       //順序(input)の異常をチェックする
		if (1 <= dispOrder && dispOrder <= 5) {
			//順序をチェックする
			List<Boolean> listCheckApproved = new ArrayList<>();
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase){
				for (ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
					listCheckApproved.addAll(approvalFrame.getListApproveAccepted().stream().map(item -> {
						return item.getApprovalATR() == ApprovalAtr.APPROVED;
					}).collect(Collectors.toList()));
				}
			}
			//Check Order
			if (dispOrder == 1) {
				//後ろの承認フェーズが未承認かチェックする
				if(listCheckApproved.contains(false) == true){
					//承認中ズフラグ = true
					outputAuthorizableflags = true;
				}
				else{
					outputAuthorizableflags = false;
				}
			} else if (dispOrder == 2 || dispOrder == 3 || dispOrder == 4) {
				//後ろの承認フェーズが未承認かチェックする 
				if(listCheckApproved.contains(false) == false){
					outputAuthorizableflags = false;
				}else{
					//TODO Chua co link
				}
			
			} else if (dispOrder == 5) {
			}
			// TODO
		}

		return outputAuthorizableflags;
	}

}