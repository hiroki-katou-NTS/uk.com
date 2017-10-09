package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
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
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ApprovalInfoOutput;
//import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformation;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.shr.com.context.AppContexts;

/**
 * 2-2.新規画面登録時承認反映情報の整理
 * 
 * @author ducpm
 *
 */
@Stateless
public class RegisterAtApproveReflectionInfoDefault implements RegisterAtApproveReflectionInfoService {

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;

	@Inject
	private ApprovalFrameRepository frameRepo;

	@Inject
	private AfterApprovalProcess approvalProcess;

	/**
	 * 3-1.承認代行情報の取得処理
	 */
	@Inject
	private AgentAdapter approvalAgencyInformationService;
	
	@Inject
	private ApproveAcceptedRepository approveAcceptedRepo;

	@Override
	public void newScreenRegisterAtApproveInfoReflect(String SID, Application application) {
		String appID = application.getApplicationID();
		// アルゴリズム「承認情報の整理」を実行する
		this.organizationOfApprovalInfo(appID);
		// アルゴリズム「実績反映状態の判断」を実行する
		this.performanceReflectedStateJudgment(appID);
	}

	@Override
	public ApprovalInfoOutput organizationOfApprovalInfo(String appID) {
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする
		// get List 5 承認 Phase
		ApprovalInfoOutput output = new ApprovalInfoOutput();
		String companyID = AppContexts.user().companyId();
		List<AppApprovalPhase> listAppPhase = new ArrayList<AppApprovalPhase>();
		List<Integer> listDispOrder = new ArrayList<Integer>();
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		// LOOP PHASE
		for (AppApprovalPhase appPhase : listPhase) {
			// get List 5 FRAME by phase ID
			String phaseID = appPhase.getPhaseID();
			String  loginEmp = AppContexts.user().employeeId();
			List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(companyID, phaseID);
			for(ApprovalFrame approvalFrame : listFrame) {
				List<ApproveAccepted> listAccept = approveAcceptedRepo.getAllApproverAccepted(
						approvalFrame.getCompanyID(),approvalFrame.getFrameID());
				approvalFrame.setListApproveAccepted(listAccept);
			}
			// ドメインモデル「承認フェーズ」．承認区分が承認済じゃない(「承認フェーズ」．承認区分 ≠ 承認済)
			if (appPhase.getApprovalATR() != ApprovalAtr.APPROVED) {
				/// LOOP FRAME
				// 承認枠 1～5 のループ
				for (ApprovalFrame frame : listFrame) {
					List<String> lstApproverIds = frame.getListApproveAccepted().stream().map(x->x.getApproverSID()).collect(Collectors.toList());
					//ログイン者が承認者かチェックする
					if(lstApproverIds.contains(loginEmp)) {
						//(ループ中の「承認枠」)承認区分=「承認済」、承認者=ログイン者の社員ID、代行者=空
					}else {
						//アルゴリズム「承認代行情報の取得処理」を実行する
						AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, lstApproverIds);
						//返す結果の承認代行者リスト. Contains(ログイン者社員ID)
						if(agency.getListRepresenterSID().contains(loginEmp)) {
							//(ドメインモデル「承認枠」)承認区分=「承認済」、承認者=空、代行者=ログイン者の社員ID
						}
					}
				}
			}
			// 「承認フェーズ」．承認形態をチェックする
			/// CỤM THỨ 2
			ApprovalForm aprovalForm = appPhase.getApprovalForm();
			// 「承認フェーズ」．承認形態が誰か一人
			// 承認フラグ
			boolean flgApprovalDone = false;
			// Trường hợp chỉ có một người approval
			if (aprovalForm == ApprovalForm.SINGLEAPPROVED) {
				flgApprovalDone = false;
				// if文：「承認枠」1．確定区分 == true OR 「承認枠」2．確定区分 == true OR...「承認枠」5．確定区分 == true
				for (ApprovalFrame frame : listFrame) {
					List<String> lstApproverIds = frame.getListApproveAccepted().stream().map(x->x.getApproverSID()).collect(Collectors.toList());
					List<String> listApproved = approvalProcess.actualReflectionStateDecision(appID, appPhase.getPhaseID(), ApprovalAtr.APPROVED);
					for(ApproveAccepted accepted: frame.getListApproveAccepted()){
						// if文がtrue ==>> 確定者が承認済かチェックする(
						if (accepted.getConfirmATR() == ConfirmAtr.USEATR_USE) {
							// 確定者が承認済かチェックする
							if (accepted.getApprovalATR() == ApprovalAtr.APPROVED) {
								flgApprovalDone = true;
							} else {
								// goi XU LI 3.1 ==>> lay thang dai dien xac nhan
								//アルゴリズム「承認代行情報の取得処理」を実行する
								AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, lstApproverIds);
								if(!agency.isFlag()){ 
									flgApprovalDone = false; 
								}
							}
						} else {
							// if文がfalse ==> 承認済の承認枠があるかチェックする
							if (accepted.getApprovalATR() == ApprovalAtr.APPROVED) {
								flgApprovalDone = true;
							} else {
								AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, lstApproverIds);
								if(!agency.isFlag()){ 
									flgApprovalDone = false; 
								}
							}
						}
					}
				}
			} else {
				//「承認フェーズ」．承認形態が全員承認
				flgApprovalDone = true;
				// 全員承認したかチェックする
				boolean allApprovePhase = true;
				for (ApprovalFrame frame : listFrame) {
					//全員承認したかチェックする
					//tat ca da dc approver 
					if (!this.isFrameApprove(frame)) {
						allApprovePhase = false;
					}
				}
				if(!allApprovePhase) {
					//未承認の承認者一覧を取得する
					List<String> listUnApprover = approvalProcess.actualReflectionStateDecision(appID, appPhase.getPhaseID(),ApprovalAtr.UNAPPROVED);
					//承認代行情報の取得処理
					AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, listUnApprover);
					if(!agency.isFlag()) {
						flgApprovalDone = false;
					}
				}
				
			}
			//その以外
			if (flgApprovalDone) {
				//承認完了フラグをチェックする
				//ループ中のドメインモデル「承認フェーズ」．承認区分 = 承認済
				appPhase.changeApprovalATR(ApprovalAtr.APPROVED);
				listAppPhase.add(appPhase);
				listDispOrder.add(appPhase.getDispOrder());
			}else {
				listAppPhase.add(appPhase);
			}
		}
		output.setAppApprovalPhase(listAppPhase);
		output.setDispOrderPhase(listDispOrder);
		return output;
	}
	/**
	 * Check : has aprrover in a frame ? 
	 * @param frame
	 * @return
	 */
	private boolean isFrameApprove(ApprovalFrame frame) {
		boolean allApprover = true;
		for(ApproveAccepted accepted : frame.getListApproveAccepted()) {
			if(accepted.getApprovalATR() != ApprovalAtr.APPROVED) {
				allApprover = false;
			}
		}
		return allApprover;
	}

	@Override
	public void performanceReflectedStateJudgment(String appID) {
		String companyID = AppContexts.user().companyId();
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		for (AppApprovalPhase phase : listPhase) {
			// 「承認フェーズ」．承認区分が承認済以外の場合(「承認フェーズ」．承認区分 ≠ 承認済)
			// Trong truong hop khac loai APPROVAL DONE
			if (phase.getApprovalATR() != ApprovalAtr.APPROVED) {
				// Lay danh sach nguoi xac nhan TU
				List<String> listApprover = approvalProcess.actualReflectionStateDecision(appID, phase.getPhaseID(), ApprovalAtr.APPROVED);
				// Thuc hien lay danh sach nguoi xac nhan dai dien, PATH setting
				AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(companyID, listApprover);
				if (!agency.isFlag()) {
					continue;
				}
			} else {
				// APPROVAL DONE
				// 「反映情報」．実績反映状態を「反映待ち」にする
				Optional<Application> currentApplication = appRepo.getAppById(companyID, appID);
				currentApplication.get().changeReflectState(ReflectPlanPerState.WAITREFLECTION.value);
				appRepo.updateApplication(currentApplication.get());
			}
		}

	}

}
