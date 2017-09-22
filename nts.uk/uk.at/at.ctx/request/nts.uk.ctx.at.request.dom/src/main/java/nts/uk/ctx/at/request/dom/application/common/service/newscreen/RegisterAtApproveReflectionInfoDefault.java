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
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcessImpl;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformation;
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

	// @Inject
	private AfterApprovalProcessImpl approvalProcess;

	/**
	 * 3-1.承認代行情報の取得処理
	 */
	@Inject
	private ApprovalAgencyInformation appAgencyInfoService;

	@Override
	public void newScreenRegisterAtApproveInfoReflect(String SID, Application application) {
		String appID = application.getApplicationID();
		// アルゴリズム「承認情報の整理」を実行する
		this.organizationOfApprovalInfo(appID);
		// アルゴリズム「実績反映状態の判断」を実行する
		this.performanceReflectedStateJudgment(appID);
	}

	@Override
	public void organizationOfApprovalInfo(String appID) {
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする(
		// get List 5 承認 Phase
		String companyID = AppContexts.user().companyId();
		//
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		// get list 5 Phase ID
		List<String> list5PhaseID = listPhase.stream().map(c -> c.getAppID()).collect(Collectors.toList());
		// LOOP PHASE
		for (AppApprovalPhase appPhase : listPhase) {
			// get List 5 FRAME by phase ID
			String phaseID = appPhase.getPhaseID();
			List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(companyID, phaseID);
			// ドメインモデル「承認フェーズ」．承認区分が承認済じゃない(「承認フェーズ」．承認区分 ≠ 承認済)
			if (appPhase.getApprovalATR().value != 1) {
				/// LOOP FRAME
				// 承認枠 1～5 のループ
				for (ApprovalFrame frame : listFrame) {
					// get 承認者ID
					String approver = frame.getApproverSID();
					// get 承認区分
					int approvalAtr = frame.getApprovalATR().value;
					// 承認者一覧に承認者がいる
					// KIỂM TRA NGƯỜI ĐẠI DIỆN
					// trả về người đại diện, có phải là người login hay không ? hay để trống
					if (approvalAtr != 1) {
						// ログイン者が承認者かチェックする
						// if文がfalse
						if (approver != AppContexts.user().employeeId()) {
							// アルゴリズム「承認代行情報の取得処理」を実行する
							// Xử lý thu thập thông tin đại diện phê duyệt
							// Chờ thằng 3.1
							// List approver truyền vào 3.1
							ArrayList<String> lstApprover = new ArrayList<String>();
							lstApprover.add(approver);
						}
					}
				}
				// ApprovalForm /** 承認形態 */
				// 「承認フェーズ」．承認形態をチェックする
			} else {

			}

			/// CỤM THỨ 2
			ApprovalForm aprovalForm = appPhase.getApprovalForm();
			// 「承認フェーズ」．承認形態が誰か一人
			// 承認フラグ
			boolean flgApprovalDone = true;
			// Trường hợp chỉ có một người approval
			if (aprovalForm == ApprovalForm.SINGLEAPPROVED) {
				flgApprovalDone = false;
				// if文：「承認枠」1．確定区分 == true OR 「承認枠」2．確定区分 == true OR...「承認枠」5．確定区分 == true
				for (ApprovalFrame frame : listFrame) {
					// if文がtrue ==>> 確定者が承認済かチェックする(
					if (frame.getConfirmATR() == ConfirmAtr.USEATR_USE) {
						// 確定者が承認済かチェックする
						if (frame.getApprovalATR() == ApprovalAtr.APPROVED) {
							flgApprovalDone = true;
						} else {
							// goi XU LI 3.1 ==>> lay thang dai dien xac nhan
							// tra ve bien allApprovalFlg
							/*
							 * if(allApprovalFlg){ flgApprovalDone = true; }else{ flgApprovalDone = false; }
							 */
						}
					} else {
						// if文がfalse ==> 承認済の承認枠があるかチェックする
						if (frame.getApprovalATR() == ApprovalAtr.APPROVED) {
							flgApprovalDone = true;
						} else {
							// 「承認者一覧を取得する」を実行する
							List<String> listApproved = approvalProcess.actualReflectionStateDecision(appID,
									appPhase.getPhaseID(), ApprovalAtr.APPROVED);
							// GOI HAM 3.1 Lay thang DAI DIEN xac nhan
							// tra ve CO allApprovalFlg
							/*
							 * if(allApprovalFlg){ flgApprovalDone = true; }else{ flgApprovalDone = false; }
							 */
						}

					}
				}
			} else {
				// TRUONG HOP NHIEU NGUOI APPROVAL
				// aprovalForm == ApprovalForm.EVERYONEAPPROVED
				// 承認完了フラグ = true（初期化）
				flgApprovalDone = true;
				// 全員承認したかチェックする
				for (ApprovalFrame frame : listFrame) {
					// if文： 「承認枠」1．承認者リストに承認者がいる の「承認枠」1．承認区分 == 承認済 AND 「承認枠」2．承認者リストに承認者がいる
					// の「承認枠」2．承認区分 == 承認済 AND ... 「承認枠」5．承認者リストに承認者がいる の「承認枠」5．承認区分 == 承認済
					// ====>>>> if文がfalse
					if (frame.getApprovalATR() != ApprovalAtr.APPROVED) {
						// 未承認の承認者一覧を取得する」を実行する
						List<String> listUnApproved = approvalProcess.actualReflectionStateDecision(appID,
								appPhase.getPhaseID(), ApprovalAtr.UNAPPROVED);
						// 3-1.承認代行情報の取得処理
						ApprovalAgencyInformationOutput agency = this.appAgencyInfoService
								.getApprovalAgencyInformation(companyID, listUnApproved);
						/*
						 * if(allApprovalFlg){ flgApprovalDone = true; }else{ flgApprovalDone = false; }
						 */
					} else {
						// if文がtrue
						flgApprovalDone = true;
					}
				}
			}

			/// 承認完了フラグをチェックする(check 承認完了フラグ)
			if (flgApprovalDone) {
				// CON DOAN CUOI = > CHUA HIEU
				// ループ中のドメインモデル「承認フェーズ」．承認区分 = 承認済

				// 承認フェーズ枠番 = ループ中の「承認フェーズ」．順序
			}
			// ドメインモデル「承認フェーズ」．承認区分が承認済(「承認フェーズ」．承認区分 = 承認済)
			// KHONG LAM GI CA
		}

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
				List<String> listApprover = approvalProcess.actualReflectionStateDecision(appID, phase.getPhaseID(),
						ApprovalAtr.APPROVED);
				// Thuc hien lay danh sach nguoi xac nhan dai dien, PATH setting
				// GOI HAM 3.1 ===>> tra ve list Representer
				List<String> listRepresenter = new ArrayList<>();
				boolean allApproverPathSet = true;
				if (!allApproverPathSet) {

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
