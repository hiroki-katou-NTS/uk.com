package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ApprovalInfoOutput;
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
	public Application organizationOfApprovalInfo(Application application) {
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする
		ApprovalInfoOutput output = new ApprovalInfoOutput();
		String companyID = AppContexts.user().companyId();
		String loginEmp = AppContexts.user().employeeId();
		List<AppApprovalPhase> listAppPhase = new ArrayList<AppApprovalPhase>();
		List<AppApprovalPhase> listPhase = application.getListPhase();
		List<Integer> listDispOrder = new ArrayList<Integer>();
		// LOOP PHASE
		for (AppApprovalPhase appPhase : listPhase) {
			
			// ドメインモデル「承認フェーズ」．承認区分が承認済(「承認フェーズ」．承認区分 = 承認済)
			if(appPhase.getApprovalATR() == ApprovalAtr.APPROVED) {
				continue;
			}
			List<ApprovalFrame> listFrame = appPhase.getListFrame();
			
			for (ApprovalFrame frame : listFrame) {
				boolean checkAllApproval = true;				
				List<ApproveAccepted> lstApprover = frame.getListApproveAccepted();
				//ループ中の「承認枠」．承認者リストに承認者がいるかチェックする(check 「承認枠」．承認者リスト dang xử lý có người xác nhận hay không)
				//duyệt list người approval xem có ai chưa approved k, nếu có thì :checkAllApproval = false
				for(ApproveAccepted approveAccepted : lstApprover) {
					//「承認枠」．承認区分が承認済じゃない(「承認枠」．承認区分 ≠ 承認済)
					if(approveAccepted.getApprovalATR() != ApprovalAtr.APPROVED) {
						checkAllApproval = false;
						break;
					}
				}
				//「承認枠」．承認区分が承認済(「承認枠」．承認区分 = 承認済)
				if(checkAllApproval) {
					continue;
				}
				//ログイン者が承認者かチェックする(check xem người login có phải người xác nhận hay không)				
				lstApprover.stream().filter(x -> x.getApproverSID().equals(loginEmp)).forEach(y -> {
					//if文がtrue
					//(ループ中の「承認枠」)承認区分=「承認済」、承認者=ログイン者の社員ID、代行者=空
					y.setApprovalATR(ApprovalAtr.APPROVED);
					y.setRepresenterSID("");
				});
				//if文がfalse
				List<String> lstApproverIds = lstApprover.stream().map(x -> x.getApproverSID())
						.collect(Collectors.toList());
				if(lstApproverIds.contains(loginEmp)) {
					//アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)
					AgentPubImport agency = this.approvalAgencyInformationService
							.getApprovalAgencyInformation(companyID, lstApproverIds);
					//ログイン者社員が返す結果の承認代行者リストに存在するかチェックする(người login có trong list người đại diện xác nhận trả về hay không)
					if (agency.getListRepresenterSID().contains(loginEmp)) {
						//(ドメインモデル「承認枠」)承認区分=「承認済」、承認者=空、代行者=ログイン者の社員ID
						lstApprover.stream().filter(x -> x.getApproverSID().equals(loginEmp)).forEach(y -> {
							//insert them 1 ban ghi vao bang KRQDT_APPROVE_ACCEPTED (ko co trong EAP)
							ApproveAccepted approveAccepted = ApproveAccepted.createFromJavaType(companyID,
									UUID.randomUUID().toString(),
									"", 
									ApprovalAtr.APPROVED.value, 
									ConfirmAtr.USEATR_USE.value, 
									GeneralDate.today(),
									"", //xem lai comment
									loginEmp);
							approveAcceptedRepo.createApproverAccepted(approveAccepted, frame.getFrameID());
						});
					}
				}
				
			}	
			// 「承認フェーズ」．承認形態をチェックする(check 「承認フェーズ」．承認形態)
			ApprovalForm aprovalForm = appPhase.getApprovalForm();
			// 「承認フェーズ」．承認形態が誰か一人
			// 承認フラグ
			boolean flgApprovalDone = false;
			// Trường hợp chỉ có một người approval
			if (aprovalForm == ApprovalForm.SINGLEAPPROVED) {
				flgApprovalDone = false;	
				// if文：「承認枠」1．確定区分 == true OR 「承認枠」2．確定区分 == true OR...「承認枠」5．確定区分 == true
				for (ApprovalFrame frame : listFrame) {
					List<String> lstApproverIds = frame.getListApproveAccepted().stream()
							.map(x -> x.getApproverSID()).collect(Collectors.toList());
					List<String> listApproved = approvalProcess.actualReflectionStateDecision(
							application.getApplicationID(), appPhase.getPhaseID(), ApprovalAtr.APPROVED);
					for (ApproveAccepted accepted : frame.getListApproveAccepted()) {
						// if文がtrue ==>> 確定者が承認済かチェックする(
						if (accepted.getConfirmATR() == ConfirmAtr.USEATR_USE) {
							// 確定者が承認済かチェックする
							if (accepted.getApprovalATR() == ApprovalAtr.APPROVED) {
								flgApprovalDone = true;
							} else {
								// アルゴリズム「承認代行情報の取得処理」を実行する
								AgentPubImport agency = this.approvalAgencyInformationService
										.getApprovalAgencyInformation(companyID, lstApproverIds);
								if (!agency.isFlag()) {
									flgApprovalDone = false;
								}
							}
						} else {
							// if文がfalse ==> 承認済の承認枠があるかチェックする
							if (accepted.getApprovalATR() == ApprovalAtr.APPROVED) {
								flgApprovalDone = true;
							} else {
								AgentPubImport agency = this.approvalAgencyInformationService
										.getApprovalAgencyInformation(companyID, lstApproverIds);
								if (!agency.isFlag()) {
									flgApprovalDone = false;
								}
							}
						}
					}
				}
			} else {
				// 「承認フェーズ」．承認形態が全員承認
				flgApprovalDone = true;
				// 全員承認したかチェックする
				boolean allApprovePhase = true;
				for (ApprovalFrame frame : listFrame) {
					// 全員承認したかチェックする
					// tat ca da dc approver
					if (!this.isFrameApprove(frame)) {
						allApprovePhase = false;
					}
				}
				if (!allApprovePhase) {
					// 未承認の承認者一覧を取得する
					List<String> listUnApprover = approvalProcess.actualReflectionStateDecision(
							application.getApplicationID(), appPhase.getPhaseID(), ApprovalAtr.UNAPPROVED);
					// 承認代行情報の取得処理
					AgentPubImport agency = this.approvalAgencyInformationService
							.getApprovalAgencyInformation(companyID, listUnApprover);
					if (!agency.isFlag()) {
						flgApprovalDone = false;
					}
				}

			}
			// その以外
			if (flgApprovalDone) {
				// 承認完了フラグをチェックする
				// ループ中のドメインモデル「承認フェーズ」．承認区分 = 承認済
				appPhase.changeApprovalATR(ApprovalAtr.APPROVED);
				listAppPhase.add(appPhase);
				listDispOrder.add(appPhase.getDispOrder());
			} else {
				listAppPhase.add(appPhase);
			}
		}
		output.setAppApprovalPhase(listAppPhase);
		output.setDispOrderPhase(listDispOrder);
		return application;
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
		if (listPhase != null) {
			for (AppApprovalPhase phase : listPhase) {
				// 「承認フェーズ」．承認区分が承認済以外の場合(「承認フェーズ」．承認区分 ≠ 承認済)
				if (phase.getApprovalATR() != ApprovalAtr.APPROVED) {
					// Lay danh sach nguoi xac nhan TU
					List<String> listApprover = approvalProcess.actualReflectionStateDecision(
							application.getApplicationID(), phase.getPhaseID(), ApprovalAtr.APPROVED);
					// Thuc hien lay danh sach nguoi xac nhan dai dien, PATH setting
					AgentPubImport agency = this.approvalAgencyInformationService
							.getApprovalAgencyInformation(AppContexts.user().companyId(), listApprover);
					if (!agency.isFlag()) {
						continue;
					}
				} else {
					// APPROVAL DONE
					// 「反映情報」．実績反映状態を「反映待ち」にする
					application.changeReflectState(ReflectPlanPerState.WAITREFLECTION.value);
					//appRepo.updateApplication(application);
				}
			}
		}
		//return application;
	}

}
