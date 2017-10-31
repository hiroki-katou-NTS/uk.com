package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.Reason;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
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
	@Inject
	private AfterApprovalProcess afterProcess;
	
	@Override
	public Application newScreenRegisterAtApproveInfoReflect(String SID, Application application) {
		// アルゴリズム「承認情報の整理」を実行する
		this.organizationOfApprovalInfo(application, "");
		// アルゴリズム「実績反映状態の判断」を実行する
		return this.performanceReflectedStateJudgment(application);
	}

	/**
	 * 1.承認情報の整理
	 */
	@Override
	public Application organizationOfApprovalInfo(Application application, String approverMemo) {
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする
		String companyID = AppContexts.user().companyId();
		String loginEmp = AppContexts.user().employeeId();
		List<AppApprovalPhase> listPhase = application.getListPhase();
		// LOOP PHASE
		for (AppApprovalPhase appPhase : listPhase) {
			
			// ドメインモデル「承認フェーズ」．承認区分が承認済(「承認フェーズ」．承認区分 = 承認済)
			if(appPhase.getApprovalATR() == ApprovalAtr.APPROVED) {
				continue;
			}
			List<ApprovalFrame> listFrame = appPhase.getListFrame();
			List<String> allAcceptedPhase = new ArrayList<>();
			appPhase.getListFrame().stream().forEach(x -> {
				x.getListApproveAccepted().stream().forEach(y -> {
					allAcceptedPhase.add(y.getApproverSID());
				});
			});
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
				boolean isLogin = false;
				for(ApproveAccepted approveAccepted : lstApprover) {
					if(approveAccepted.getApproverSID().equals(loginEmp)) {
						approveAccepted.setApprovalATR(ApprovalAtr.APPROVED);
						approveAccepted.setRepresenterSID("");
						isLogin = true;
						break;
					}
				}
				if(isLogin) {
					continue;
				}
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
						ApproveAccepted representer = new ApproveAccepted(companyID,
								UUID.randomUUID().toString(),
								"", 
								ApprovalAtr.APPROVED, 
								ConfirmAtr.USEATR_NOTUSE, 
								GeneralDate.today(),
								new Reason(approverMemo), 
								loginEmp);
						lstApprover.add(representer);
						//chuyen trang thai nhung nguoi trong frame thanh APPROVED
						lstApprover.stream().forEach(x ->{
							x.setApprovalATR(ApprovalAtr.APPROVED);
							x.setRepresenterSID(loginEmp);
						});
					}
				}
				
			}
			// 「承認フェーズ」．承認形態をチェックする(check 「承認フェーズ」．承認形態)
			ApprovalForm aprovalForm = appPhase.getApprovalForm();
			// 承認完了フラグ
			boolean flgApprovalDone = false;
			// Trường hợp chỉ có một người approval
			// 「承認フェーズ」．承認形態が誰か一人
			if (aprovalForm == ApprovalForm.SINGLEAPPROVED) {
				flgApprovalDone = false;	
				
				// if文：「承認枠」1．確定区分 == true OR 「承認枠」2．確定区分 == true OR...「承認枠」5．確定区分 == true		
				boolean isConfirmAtr = true;
				List<String> lstApproverIds = new ArrayList<>();
				for (ApprovalFrame frame : listFrame) {
					lstApproverIds = frame.getListApproveAccepted().stream()
							.map(x -> x.getApproverSID()).collect(Collectors.toList());
					
					//確定者を設定したかチェックする(check có cài đặt người 確定者 hay không)
					for (ApproveAccepted accepted : frame.getListApproveAccepted()) {
						if(accepted.getConfirmATR() == ConfirmAtr.USEATR_NOTUSE) {
							isConfirmAtr = false;
							break;
						}
					}
				}
				//if文がtrue
				if(isConfirmAtr) {
					
					//確定者が承認済かチェックする(check nguoif 確定者 đã xác nhận hay chưa)
					boolean isConfirmted = true;
					for (ApprovalFrame frame : listFrame) {
						for(ApproveAccepted accepted : frame.getListApproveAccepted()) {
							if(accepted.getConfirmATR() == ConfirmAtr.USEATR_USE && accepted.getApprovalATR() != ApprovalAtr.APPROVED) {
								isConfirmted = false;
								break;
							}
						}
					}
					
					//if文がtrue
					if(isConfirmted) {
						flgApprovalDone = true;
					}else {
						//アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)
						AgentPubImport agency = this.approvalAgencyInformationService
								.getApprovalAgencyInformation(companyID, lstApproverIds);
						if (agency.isFlag()) {
							flgApprovalDone = true;
						}
					}
				}else {
					boolean isApproved = true;
					//承認済の承認枠があるかチェックする(check xem có 承認枠 đã được xác nhận hay khồng)
					for (ApprovalFrame frame : listFrame) {
						for(ApproveAccepted accepted : frame.getListApproveAccepted()) {
							if(accepted.getApprovalATR() != ApprovalAtr.APPROVED) {
								isApproved = false;
								break;
							}
						}
					}
					
					//if文がtrue
					if(isApproved) {
						flgApprovalDone = true;
					}else {
						//アルゴリズム「承認者一覧を取得する」を実行する(thực hiện xử lý 「承認者一覧を取得する」)
						//アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)
						AgentPubImport agency = this.approvalAgencyInformationService
								.getApprovalAgencyInformation(companyID, allAcceptedPhase);
						if (agency.isFlag()) {
							flgApprovalDone = true;
						}
					}
				}
			} else {
				// 「承認フェーズ」．承認形態が全員承認
				flgApprovalDone = true;
				// 全員承認したかチェックする(check xem tất cả người xác nhận đã xác nhận chưa)
				boolean isApproved = true;
				for (ApprovalFrame frame : listFrame) {
					for(ApproveAccepted accepted : frame.getListApproveAccepted()) {
						if(accepted.getApprovalATR() != ApprovalAtr.APPROVED) {
							isApproved = false;
							break;
						}
					}
				}
				//if文がtrue
				if(isApproved) {
					flgApprovalDone = true;
				}else {
					//アルゴリズム「未承認の承認者一覧を取得する」を実行する(thực hiện xử lý 「未承認の承認者一覧を取得する」)
					List<String> lstNotApproved = new ArrayList<>();
					listFrame.stream().forEach(x -> {
						x.getListApproveAccepted().stream().forEach(y -> {
							if(y.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
								lstNotApproved.add(y.getApproverSID());
							}
						});
					});
					//アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)
					
					AgentPubImport agency = this.approvalAgencyInformationService
							.getApprovalAgencyInformation(companyID, lstNotApproved);
					if (agency.isFlag()) {
						flgApprovalDone = true;
					}else {
						flgApprovalDone = false;
					}
				}				
			}
			// 承認完了フラグをチェックする(check 承認完了フラグ)
			if (flgApprovalDone) {
				// 承認完了フラグをチェックする
				// ループ中のドメインモデル「承認フェーズ」．承認区分 = 承認済
				appPhase.changeApprovalATR(ApprovalAtr.APPROVED);				
			} 
		}
		return application;
	}

	@Override
	public Application performanceReflectedStateJudgment(Application application) {
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
		return application;
	}

}
