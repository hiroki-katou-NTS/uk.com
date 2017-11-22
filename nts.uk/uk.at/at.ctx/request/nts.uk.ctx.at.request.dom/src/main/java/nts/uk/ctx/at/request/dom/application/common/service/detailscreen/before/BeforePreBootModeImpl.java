package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
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
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
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
	

	@Override
	public DetailedScreenPreBootModeOutput judgmentDetailScreenMode(Application applicationData,
			GeneralDate baseDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// Output variables
		DetailedScreenPreBootModeOutput outputData = new DetailedScreenPreBootModeOutput(User.OTHER, ReflectPlanPerState.NOTREFLECTED, false, ApprovalAtr.UNAPPROVED, false, false);
		if(applicationData.getEnteredPersonSID().contains(employeeID)) {
			outputData.setLoginInputOrApproval(true);
		}
		//4.社員の当月の期間を算出する
		PeriodCurrentMonth listDate = otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID,
				employeeID, baseDate);
		GeneralDate startDate = listDate.getStartDate();
		// 締め開始日 >  ドメインモデル「申請」．申請日 がtrue
		if (startDate.after(applicationData.getApplicationDate())) {
			//ステータス = 過去申請(status= 過去申請)
			outputData.setReflectPlanState(ReflectPlanPerState.PASTAPP);
		} else {
			//ステータス = ドメインモデル「反映情報」．実績反映状態(Set status = 「反映情報」．実績反映状態)
			outputData.setReflectPlanState(applicationData.getReflectPerState());
		}	
		
		// get User
		// "Application".Applicant = login If employee ID is true
		//ログイン者が承認者かチェックする(Check xem login có phải là người approve ko?)
		
		if (decideByApprover(applicationData)) {
			outputData.setLoginInputOrApproval(true);
			//ログイン者が申請本人かチェックする(Check xem login có phải là 申請本人 không)
			// ドメインモデル「申請」．申請者 = ログイン者社員ID がtrue
			if (applicationData.getApplicantSID().equals(employeeID)) {
				//利用者 = 申請本人&承認者				
				outputData.setUser(User.APPLICANT_APPROVER);
			} else {
				//利用者 = 承認者
				outputData.setUser(User.APPROVER);
			}
		} else {//承認者フラグがfalse
			//ドメインモデル「申請」．申請者 = ログイン者社員ID がtrue
			if (applicationData.getApplicantSID().equals(employeeID)) {
				//利用者 = 申請本人
				outputData.setUser(User.APPLICANT);
			} else {//ドメインモデル「申請」．申請者 = ログイン者社員ID がfalse
				//利用者 = その他
				outputData.setUser(User.OTHER);
			}
		}
		
		outputData.setApprovalATR(ApprovalAtr.UNAPPROVED);
		applicationData.getListPhase().stream().forEach(x -> {
			x.getListFrame().stream().forEach(y ->{
				//ログイン者の承認区分
				y.getListApproveAccepted().stream().forEach(z ->{
					if(z.getApproverSID().equals(employeeID)) {
						outputData.setApprovalATR(z.getApprovalATR());
					}
				});				
			});
		});
		// 利用者をチェックする(Check người sử dụng)
		// 利用者が「申請本人&承認者」、又は「承認者」の場合
		if (outputData.getUser() == User.APPLICANT_APPROVER 
				|| outputData.getUser() == User.APPROVER) {
			// アルゴリズム「承認できるかの判断」を実行する(phán đoán xem có thể approve hay không)
			CanBeApprovedOutput canBeApprovedOutput = canBeApproved(applicationData, outputData.getReflectPlanState());
			outputData.setAlternateExpiration(canBeApprovedOutput.getAlternateExpiration());
			outputData.setAuthorizableFlags(canBeApprovedOutput.getAuthorizableFlags());
			return outputData;
			
		}
		return outputData;
	}

	@Override
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status) {
		//承認区分 = 未承認、承認できるフラグ = false、代行期限切れフラグ = false(初期化)
		CanBeApprovedOutput outputData =  new CanBeApprovedOutput(false, ApprovalAtr.UNAPPROVED, false);
		// ステータスが否認、反映待ち、未反映、差し戻し(status = 否認、反映待ち、未反映、差し戻し)
		if (status.equals(ReflectPlanPerState.DENIAL) 
				|| status.equals(ReflectPlanPerState.WAITREFLECTION)
				|| status.equals(ReflectPlanPerState.NOTREFLECTED)
				|| status.equals(ReflectPlanPerState.REMAND)) {
			List<AppApprovalPhase> listAppApprovalPhase = applicationData.getListPhase();
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
				// アルゴリズム「承認者一覧を取得する」を実行する
				List<String> listApprovers = new ArrayList<>();
				appApprovalPhase.getListFrame().stream()
				.forEach(x ->{
					x.getListApproveAccepted().stream().forEach(y -> {
						listApprovers.add(y.getApproverSID());
					});
				});
				//承認中の承認フェーズの判断
				if(checkApprovalProgressPhase(applicationData, appApprovalPhase.getDispOrder())) {
					//ループ中のドメインモデル「承認フェーズ」．承認区分をチェックする
					//ループ中のドメインモデル「承認フェーズ」．承認区分が差し戻し
					if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.REMAND)) {
						//2.承認状況の判断(差し戻し)
						outputData = approvedRemand(appApprovalPhase);						
					}else if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.APPROVED)) {//ループ中のドメインモデル「承認フェーズ」．承認区分が承認済
						//1.承認状況の判断(承認済)
						outputData = approvedApproved(appApprovalPhase);
					}else if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.DENIAL)) {//ループ中のドメインモデル「承認フェーズ」．承認区分が否認
						//3.承認状況の判断(否認)
						outputData = approvedDential(appApprovalPhase);
					}else if (appApprovalPhase.getApprovalATR().equals(ApprovalAtr.UNAPPROVED)) {//ループ中のドメインモデル「承認フェーズ」．承認区分が未承認
						//4.承認状況の判断(未承認)
						outputData = approvedUnapproved(appApprovalPhase);						
					}
					//承認できるフラグをチェックする
					if(outputData.getAuthorizableFlags()) {
						return outputData;
					}
				}
			}
			return outputData;
		}
		return outputData;
	}

	@Override
	public CanBeApprovedOutput approvedRemand(AppApprovalPhase appApprovalPhase) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		//承認区分 = 未承認、承認できるフラグ = false、代行期限切れフラグ = false(初期化)
		CanBeApprovedOutput outputData =  new CanBeApprovedOutput(false, ApprovalAtr.UNAPPROVED, false);
		// Get listApprovalFrame
		List<ApprovalFrame> listApprovalFrame = appApprovalPhase.getListFrame();
		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			List<String> approvers = new ArrayList<>();
			approvalFrame.getListApproveAccepted().stream()
			.forEach(x -> {
				approvers.add(x.getApproverSID());
			});
			//ログイン者が承認者かチェックする
			if(approvers.contains(employeeID)) {
				//承認区分=未承認、承認可能なフラグ=真、	置換有効期限フラグ= false
				outputData.setAlternateExpiration(false);
				outputData.setAuthorizableFlags(true);
				return outputData;
			}else {
				//アルゴリズム「承認代行情報の取得処理」を実行する
				
				//3-1.承認代行情報の取得処理
				AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID,approvers);
				//ログインユーザーが代理承認者かどうか
				if(agency.getListRepresenterSID().contains(employeeID)) {
					//承認区分=未承認、承認可能なフラグ=真、	置換有効期限フラグ= false
					outputData.setAlternateExpiration(false);
					outputData.setAuthorizableFlags(true);
					return outputData;
				}
			}			
		}
		return outputData;
	}

	@Override
	public CanBeApprovedOutput approvedApproved(AppApprovalPhase appApprovalPhase) {
		String employeeID = AppContexts.user().employeeId();
		//承認区分 = 未承認、承認できるフラグ = false、代行期限切れフラグ = false(初期化)
		CanBeApprovedOutput outputData =  new CanBeApprovedOutput(false, ApprovalAtr.UNAPPROVED, false);		
		// Get listApprovalFrame
		List<ApprovalFrame> listApprovalFrame = appApprovalPhase.getListFrame();
		//「承認フェーズ」．承認形態をチェックする
		//「承認フェーズ」．承認形態が全員承認		
		if (appApprovalPhase.getApprovalForm().equals(ApprovalForm.EVERYONEAPPROVED)) {
			for (ApprovalFrame approvalFrame : listApprovalFrame) {				
				List<ApproveAccepted> listApprovers = approvalFrame.getListApproveAccepted();
				List<String> approvers = new ArrayList<>();
				List<String> representers = new ArrayList<>(); 
				listApprovers.stream()
				.forEach(x -> {
					approvers.add(x.getApproverSID());
					representers.add(x.getRepresenterSID());
				});
				//ログイン者が承認者として承認を行ったかチェックする
				if(approvers.contains(employeeID)) {
					//承認区分 = 承認済、承認できるフラグ = true、代行期限切れフラグ = false
					outputData.setAlternateExpiration(false);
					outputData.setAuthorizableFlags(true);
					outputData.setApprovalATR(ApprovalAtr.APPROVED);
					return outputData;
				}else {
					//ログイン者が代行承認者として承認を行ったかチェックする
					if(!CollectionUtil.isEmpty(representers) && representers.contains(employeeID)) {
						//承認区分 = 承認済、承認できるフラグ = true
						outputData.setApprovalATR(ApprovalAtr.APPROVED);
						outputData.setAuthorizableFlags(true);
						//アルゴリズム「代行者期限切れの判断」を実行する
						outputData.setAlternateExpiration(decideAgencyExpired(approvers));
						return outputData;
					}
				}
			}
		} else {//「承認フェーズ」．承認形態が誰か一人
			//確定者が設定したかをチェックする  => chu y can phai xac nhan lai xem 確定者 thuoc frame hay approver???			
			for (ApprovalFrame approvalFrame : listApprovalFrame) {
				List<ApproveAccepted> listApproveAccepted = approvalFrame.getListApproveAccepted();			
				//ループ中の承認枠．確定区分をチェックする
				List<ApproveAccepted> someoneApprovalConfirm = listApproveAccepted.stream()
						.filter(f -> f.getConfirmATR().equals(ConfirmAtr.USEATR_USE)).collect(Collectors.toList());
				//「承認枠」(承認区分=承認済)
				List<String> approveds = listApproveAccepted.stream()
						.filter(x -> x.getApprovalATR().equals(ApprovalAtr.APPROVED))
						.map(y -> y.getApproverSID()).collect(Collectors.toList());
				//確定者設定＝true
				if (!CollectionUtil.isEmpty(someoneApprovalConfirm)) {
					List<String> listApprover = new ArrayList<>();
					List<String> listRepresenter = new ArrayList<>();
					someoneApprovalConfirm.stream().forEach(x -> {
						listApprover.add(x.getApproverSID());
						listRepresenter.add(x.getRepresenterSID());
					});
					//ログイン者が確定者として承認を行ったかチェックする
					if (listApprover.contains(employeeID)) {
						//承認区分 = 承認済、承認できるフラグ = true、代行期限切れフラグ = false
						outputData.setApprovalATR(ApprovalAtr.APPROVED);
						outputData.setAuthorizableFlags(true);
						outputData.setAlternateExpiration(false);
						return outputData;
					} else {
						//ログイン者が確定者の代行者として承認を行ったかチェックする
						if(!CollectionUtil.isEmpty(listRepresenter)) {
							if(listRepresenter.contains(employeeID)) {
								//承認区分 = 承認済、承認できるフラグ = true
								outputData.setApprovalATR(ApprovalAtr.APPROVED);
								outputData.setAuthorizableFlags(true);
								//アルゴリズム「代行者期限切れの判断」を実行する
								outputData.setAlternateExpiration(decideAgencyExpired(listApprover));
								return outputData;
							}
						}							
					}
				}
				//ログイン者が承認者として承認を行ったかチェックする
				for(ApproveAccepted approver: listApproveAccepted) {
					if(approver.getApprovalATR().equals(ApprovalAtr.APPROVED)
							&& approver.getApproverSID().equals(employeeID)) {
						//承認区分 = 承認済、承認できるフラグ = true、代行期限切れフラグ = false
						outputData.setApprovalATR(ApprovalAtr.APPROVED);
						outputData.setAuthorizableFlags(true);
						outputData.setAlternateExpiration(false);
						return outputData;
					}else {
						//ログイン者が代行者として承認を行ったかチェックする
						if(approver.getRepresenterSID().equals(employeeID)) {
							//承認区分 = 承認済、承認できるフラグ = true
							outputData.setApprovalATR(ApprovalAtr.APPROVED);
							outputData.setAuthorizableFlags(true);
							//アルゴリズム「代行者期限切れの判断」を実行する
							outputData.setAlternateExpiration(decideAgencyExpired(approveds));
							return outputData;
						}
					}
				}
			}
		}

		return outputData;
	}


	@Override
	public boolean decideAgencyExpired(List<String> listApprovers) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		// アルゴリズム「承認代行情報の取得処理」を実行する
		AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, listApprovers);
		//ログイン者が代行権限を持っているかチェックする
		if(agency.getListRepresenterSID().contains(employeeID)) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * Decide by Approver 
	 * 2.承認者かの判断
	 */
	@Override
	public boolean decideByApprover(Application applicationData) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		boolean approverFlag = false;
		List<String> listApproverID = new ArrayList<>();
		List<String> listRepresenter = new ArrayList<>();
		applicationData.getListPhase().stream().forEach(x -> {
			x.getListFrame().stream().forEach(y -> {		
				y.getListApproveAccepted().stream().forEach(z -> {
					
					listApproverID.add(z.getApproverSID());
					if(z.getRepresenterSID() != null) {
						listRepresenter.add(z.getRepresenterSID());
					}
				});
			});
		});
		if(listApproverID.contains(employeeID)) {
			return true;
		}
		//ログイン者が承認者かチェックする Whether the loginer is an approver
		if (!approverFlag) {
			//アルゴリズム「承認代行情報の取得処理」を実行する
			AgentPubImport approvalAgencyInformationOutput = approvalAgencyInformationService
					.getApprovalAgencyInformation(companyID, listApproverID);
			//ログイン者が代行承認者かチェックする
			if (approvalAgencyInformationOutput.getListRepresenterSID().contains(employeeID)) {
				return true;
			}
		}
		// 承認者フラグをチェックする
		// Check Approver Flag
		if (!approverFlag){
			// ログイン者が承認代行者として承認を行ったかチェックする
			if(listRepresenter.contains(employeeID)) {
				return true;
			}else {
				approverFlag = false;
			}
		}
		return approverFlag;
	}

	@Override
	public boolean checkApprovalProgressPhase(Application applicationData, int dispOrder) {
		//承認中ズフラグ = false
		List<AppApprovalPhase> listAppApprovalPhase = applicationData.getListPhase();
		//順序(input)の異常をチェックする
		//順序が1
		if(dispOrder == 1) {
			//後ろの承認フェーズが未承認かチェックする
			return checkAfterApproverFlg(listAppApprovalPhase, 1);
		}else if (dispOrder == 5) {//順序が5
			//前の承認フェーズが承認済かチェックする
			return checkBeforeApproverFlg(listAppApprovalPhase, dispOrder);
		}else {//順序が2,3,4
			if(!checkBeforeApproverFlg(listAppApprovalPhase, dispOrder)) {
				return false;
			}			
			//前の承認フェーズが承認済かチェックする
			return checkAfterApproverFlg(listAppApprovalPhase, dispOrder);
		}
		
	}
	/**
	 * //後ろの承認フェーズが未承認かチェックする
	 * @param listAppApprovalPhase
	 * @param dispNumber
	 * @return
	 */
	private boolean checkAfterApproverFlg(List<AppApprovalPhase> listAppApprovalPhase, int dispNumber) {
		for(AppApprovalPhase appPhase : listAppApprovalPhase) {
			if(appPhase.getDispOrder() > dispNumber) {
				List<ApprovalFrame> appFrames = appPhase.getListFrame();
				for(ApprovalFrame appFrame: appFrames) {
					
					if(!appFrame.getListApproveAccepted().stream().anyMatch(x -> ApprovalAtr.APPROVED.equals(x.getApprovalATR()))) {
						return false;
					}					
				}
			}
		}
		return true;
	}
	/**
	 * 前の承認フェーズが承認済かチェックする
	 * @param listAppApprovalPhase
	 * @param dispNumber
	 * @return
	 */
	private boolean checkBeforeApproverFlg(List<AppApprovalPhase> listAppApprovalPhase, int dispNumber) {
		for(AppApprovalPhase appPhase : listAppApprovalPhase) {
			if(appPhase.getDispOrder() < dispNumber) {
				List<ApprovalFrame> appFrames = appPhase.getListFrame();
				for(ApprovalFrame appFrame: appFrames) {
					if(!appFrame.getListApproveAccepted().stream().anyMatch(x -> ApprovalAtr.APPROVED.equals(x.getApprovalATR()))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public CanBeApprovedOutput approvedDential(AppApprovalPhase appApprovalPhase) {
		String employeeID = AppContexts.user().employeeId();
		//承認区分 = 未承認、承認できるフラグ = false、代行期限切れフラグ = false(初期化)
		CanBeApprovedOutput outputData =  new CanBeApprovedOutput(false, ApprovalAtr.UNAPPROVED, false);
		List<ApprovalFrame> listApprovalFrame = appApprovalPhase.getListFrame();
		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			List<ApproveAccepted> listApproveAccepted = approvalFrame.getListApproveAccepted();
			List<String> approvers = new ArrayList<>(); 
			listApproveAccepted.stream().forEach(x -> {
				approvers.add(x.getApproverSID());
			});
			for (ApproveAccepted approveAccepted : listApproveAccepted) {
				//「承認枠」．承認区分をチェックする 
				//「承認枠」．承認区分が否認
				if (approveAccepted.getApprovalATR() == ApprovalAtr.DENIAL) {
					//ログイン者が承認者として否認を行ったかチェックする (login employee ID)
					if (approveAccepted.getApproverSID().equals(employeeID)) {
						//承認区分 = 否認、承認できるフラグ = true、代行期限切れフラグ = false
						outputData.setApprovalATR(ApprovalAtr.DENIAL);
						outputData.setAuthorizableFlags(true);
						outputData.setAlternateExpiration(false);
						return outputData;
					} else {
						//ログイン者が代行承認者として否認を行ったかチェックする
						if(approveAccepted.getRepresenterSID().equals(employeeID)) {
							//承認区分 = 否認、承認できるフラグ = true
							outputData.setApprovalATR(ApprovalAtr.DENIAL);
							outputData.setAuthorizableFlags(true);
							outputData.setAlternateExpiration(decideAgencyExpired(approvers));
							return outputData;
						}
					}
				} 
			}
		}

		return outputData;
	}

	@Override
	public CanBeApprovedOutput approvedUnapproved(AppApprovalPhase appApprovalPhase) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		//承認区分 = 未承認、承認できるフラグ = false、代行期限切れフラグ = false(初期化)
		CanBeApprovedOutput outputData =  new CanBeApprovedOutput(false, ApprovalAtr.UNAPPROVED, false);		
		List<ApprovalFrame> listApprovalFrame = appApprovalPhase.getListFrame();

		for (ApprovalFrame approvalFrame : listApprovalFrame) {
			List<ApproveAccepted> listApproveAccepted = approvalFrame.getListApproveAccepted();
			List<String> approvers = new ArrayList<>();
			List<String> representers = new ArrayList<>();
			listApproveAccepted.stream().forEach(x ->{
				approvers.add(x.getApproverSID());
				representers.add(x.getRepresenterSID());
			});
			for (ApproveAccepted approveAccepted : listApproveAccepted) {
				// 「承認枠」．承認区分が未承認
				// "Approval frame".Approval ATR =Not Approved
				if (approveAccepted.getApprovalATR().equals(ApprovalAtr.UNAPPROVED)) {
					// 「承認枠」．承認者リスト. Contains(ログイン者社員ID)
					// "Approval frame". Approver list. Contains (login employee
					// ID)
					if (approveAccepted.getApproverSID().equals(employeeID)) {
						//承認区分 = 未承認、承認できるフラグ = true、代行期限切れフラグ = false
						outputData.setApprovalATR(ApprovalAtr.UNAPPROVED);
						outputData.setAuthorizableFlags(true);
						outputData.setAlternateExpiration(false);
						return outputData;
					} else {
						//アルゴリズム「承認代行情報の取得処理」を実行する
						AgentPubImport approvalAgencyInformationOutput = approvalAgencyInformationService
								.getApprovalAgencyInformation(companyID, approvers);
						//ログイン者が代行承認者かチェックする
						if(approvalAgencyInformationOutput.getListRepresenterSID().contains(employeeID)) {
							//承認区分 = 未承認、承認できるフラグ = true、代行期限切れフラグ = false
							outputData.setApprovalATR(ApprovalAtr.UNAPPROVED);
							outputData.setAuthorizableFlags(true);
							outputData.setAlternateExpiration(false);
							return outputData;
						}
					}
				} else if(approveAccepted.getApprovalATR().equals(ApprovalAtr.APPROVED)) {//「承認枠」．承認区分が承認済 "Approval frame". Approval ATR  Approved
					// ログイン者が承認者として承認を行ったかチェックする
					// "Approval frame".Approver == Login Employee ID
					if (approveAccepted.getApproverSID().equals(employeeID)) {
						//承認区分 = 承認済、承認できるフラグ = true、代行期限切れフラグ = false
						outputData.setApprovalATR(ApprovalAtr.APPROVED);
						outputData.setAuthorizableFlags(true);
						outputData.setAlternateExpiration(false);
						return outputData;
					} else {
						//ログイン者が代行承認者として承認を行ったかチェックする
						if(representers.contains(employeeID)) {
							//承認区分 = 承認済、承認できるフラグ = true
							outputData.setApprovalATR(ApprovalAtr.APPROVED);
							outputData.setAuthorizableFlags(true);
							outputData.setAlternateExpiration(decideAgencyExpired(approvers));
							return outputData;
						}
					}
				}
			}
		}
		return outputData;
	}
}