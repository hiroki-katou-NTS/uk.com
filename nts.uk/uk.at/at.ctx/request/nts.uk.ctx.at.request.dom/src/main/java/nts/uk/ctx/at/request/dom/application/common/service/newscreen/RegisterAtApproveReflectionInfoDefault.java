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
		this.organizationOfApprovalInfo(application);
		// アルゴリズム「実績反映状態の判断」を実行する
		this.performanceReflectedStateJudgment(application);
	}

	/**
	 * 1.承認情報の整理
	 */
	@Override
	public ApprovalInfoOutput organizationOfApprovalInfo(Application application) {
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする
		ApprovalInfoOutput output = new ApprovalInfoOutput();
		String companyID = AppContexts.user().companyId();
		List<AppApprovalPhase> listAppPhase = new ArrayList<AppApprovalPhase>();
		List<AppApprovalPhase> listPhase = application.getListPhase();
		List<Integer> listDispOrder = new ArrayList<Integer>();
		// LOOP PHASE
		for (AppApprovalPhase appPhase : listPhase) {
			String  loginEmp = AppContexts.user().employeeId();
			List<ApprovalFrame> listFrame = appPhase.getListFrame();
			// ドメインモデル「承認フェーズ」．承認区分が承認済じゃない(「承認フェーズ」．承認区分 ≠ 承認済)
			if (appPhase.getApprovalATR() != ApprovalAtr.APPROVED) {
				// LOOP FRAME
				// 承認枠 1～5 のループ
				for (ApprovalFrame frame : listFrame) {
					List<ApproveAccepted> lstApproveAccepted = frame.getListApproveAccepted();
					// ループ中の「承認枠」．承認者リストに承認者がいるかチェックする
					if (!lstApproveAccepted.isEmpty()) {
						List<String> lstApproverIds = lstApproveAccepted.stream().map(x -> x.getApproverSID())
								.collect(Collectors.toList());
						// ログイン者が承認者かチェックする
						if (lstApproverIds.contains(loginEmp)) {
							// (ループ中の「承認枠」)承認区分=「承認済」、承認者=ログイン者の社員ID、代行者=空
							for (ApproveAccepted approveAccepted : lstApproveAccepted) {
								// (ループ中の「承認枠」)承認区分=「承認済」、
								approveAccepted.setApprovalATR(ApprovalAtr.APPROVED);
								// 代行者=空
								approveAccepted.setRepresenterSID(null);
							}
						} else {
							// アルゴリズム「承認代行情報の取得処理」を実行する
							AgentPubImport agency = this.approvalAgencyInformationService
									.getApprovalAgencyInformation(companyID, lstApproverIds);
							// 返す結果の承認代行者リスト. Contains(ログイン者社員ID)
							if (agency.getListRepresenterSID().contains(loginEmp)) {
								// (ドメインモデル「承認枠」)承認区分=「承認済」、承認者=空、代行者=ログイン者の社員ID
								for (ApproveAccepted approveAccepted : lstApproveAccepted) {
									// (ループ中の「承認枠」)承認区分=「承認済」、
									approveAccepted.setApprovalATR(ApprovalAtr.APPROVED);
									// 承認者=空
									approveAccepted.setApproverSID(null);
								}
							}
						}
						//set list Approve Accepted after change
						frame.setListApproveAccepted(lstApproveAccepted);
					} else {
						continue;
					}
				}

			} else {
				continue;
			}
			// 「承認フェーズ」．承認形態をチェックする
			// CỤM THỨ 2
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
					List<String> listApproved = approvalProcess.actualReflectionStateDecision(application.getApplicationID(), appPhase.getPhaseID(), ApprovalAtr.APPROVED);
					for(ApproveAccepted accepted: frame.getListApproveAccepted()){
						// if文がtrue ==>> 確定者が承認済かチェックする(
						if (accepted.getConfirmATR() == ConfirmAtr.USEATR_USE) {
							// 確定者が承認済かチェックする
							if (accepted.getApprovalATR() == ApprovalAtr.APPROVED) {
								flgApprovalDone = true;
							} else {
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
					List<String> listUnApprover = approvalProcess.actualReflectionStateDecision(application.getApplicationID(), appPhase.getPhaseID(),ApprovalAtr.UNAPPROVED);
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
	public void performanceReflectedStateJudgment(Application application) {
		List<AppApprovalPhase> listPhase = application.getListPhase();
		for (AppApprovalPhase phase : listPhase) {
			// 「承認フェーズ」．承認区分が承認済以外の場合(「承認フェーズ」．承認区分 ≠ 承認済)
			if (phase.getApprovalATR() != ApprovalAtr.APPROVED) {
				// Lay danh sach nguoi xac nhan TU
				List<String> listApprover = approvalProcess.actualReflectionStateDecision(application.getApplicationID(), phase.getPhaseID(), ApprovalAtr.APPROVED);
				// Thuc hien lay danh sach nguoi xac nhan dai dien, PATH setting
				AgentPubImport agency = this.approvalAgencyInformationService.getApprovalAgencyInformation(AppContexts.user().companyId(), listApprover);
				if (!agency.isFlag()) {
					continue;
				}
			} else {
				// APPROVAL DONE
				// 「反映情報」．実績反映状態を「反映待ち」にする
				application.changeReflectState(ReflectPlanPerState.WAITREFLECTION.value);
				appRepo.updateApplication(application);
			}
		}

	}

}
