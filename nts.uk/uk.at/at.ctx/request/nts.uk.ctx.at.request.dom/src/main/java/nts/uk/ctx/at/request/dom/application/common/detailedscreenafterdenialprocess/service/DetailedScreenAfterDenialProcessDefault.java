package nts.uk.ctx.at.request.dom.application.common.detailedscreenafterdenialprocess.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalATR;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.detailedacreenafterapprovalprocess.service.DetailedScreenAfterApprovalProcessService;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailedScreenAfterDenialProcessDefault implements DetailedScreenAfterDenialProcessService {

	@Inject
	private DetailedScreenAfterApprovalProcessService afterApprovalProcess;

	@Inject
	private ApprovalFrameRepository frameRepo;

	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;

	@Override
	public boolean canDeniedCheck(String companyID, String appID, int startOrderNum, List<AppApprovalPhase> listPhase) {
		boolean canDeniedCheck = true;
		if (startOrderNum > 0) {
			// アルゴリズム「承認者一覧を取得する」を実行する
			for (AppApprovalPhase phase : listPhase) {
				List<String> listApprover = afterApprovalProcess.actualReflectionStateDecision(appID,
						phase.getPhaseID(), ApprovalATR.APPROVED);
				// ループ中の承認フェーズに承認者がいる
				if (phase.getApprovalATR() == ApprovalATR.APPROVED) {
					List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(),
							phase.getPhaseID());
					for (ApprovalFrame frame : listFrame) {
						if (frame.getApprovalATR() == ApprovalATR.APPROVED) {
							// ログイン者が確定者として承認を行ったかチェックする
							if (frame.getApproverSID().contains(AppContexts.user().employeeId())) {
								canDeniedCheck = false;
							} else {
								canDeniedCheck = true;
							}
						} else {
							// 承認を行ったのはログイン者かチェックする
							// TODO: Check thang dai dien la thang Dang nhap
							// CHECK DIEU KIEN : DAI DIEN LA NGUOI DANG NHAP
							if (frame.getApproverSID().contains(AppContexts.user().employeeId())) {
								canDeniedCheck = false;
							} else {
								canDeniedCheck = true;
							}
						}
					}
				}
			}
		}
		return canDeniedCheck;
	}

	@Override
	public void detailedScreenAfterDenialProcess(String companyID, String appID) {
		// 否認できるフラグ
		boolean canDeniedFlg = false;
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		for (AppApprovalPhase phase : listPhase) {
			// get list Approver
			List<String> listApprover = afterApprovalProcess.actualReflectionStateDecision(appID, phase.getPhaseID(),
					ApprovalATR.APPROVED);
			// Kiem tra theo tung Frame, xem Phase da duoc xu li chua
			List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(), phase.getPhaseID());
			for (ApprovalFrame frame : listFrame) {
				int currentOrder = frame.getDispOrder();
				if (frame.getApprovalATR() == ApprovalATR.UNAPPROVED) {
					// アルゴリズム「否認できるかチェックする」を実行する
					canDeniedFlg = this.canDeniedCheck(companyID, appID, currentOrder - 1, listPhase);
				} else {
					canDeniedFlg = true;
				}
				// ドメインモデル「承認フェーズ」．「承認枠」
				if (canDeniedFlg) {
					// Check nguoi login co phai nguoi xac nhan khong
					if (!frame.getApproverSID().contains(AppContexts.user().employeeId())) {
						// Thuc hien ham lay tu 3.1
						// アルゴリズム「承認代行情報の取得処理」を実行する
						List<String> listRepresenter = new ArrayList<>();
						boolean allAprroval = true;
						for (String representer : listRepresenter) {
							if (representer.equals(AppContexts.user().employeeId())) {
								// (ドメインモデル「承認枠」)承認区分=「否認」、承認者=空、代行者=ログイン者の社員ID
								// update Domain ApprovalFrame voi cac dieu kien ben tren :
								// 承認区分=「否認」
								// 承認者=空
								// 代行者=ログイン者の社員ID
							}
						}
					}
				}
			}

		}
		/// CHUYEN TRANG THAI
		// 「反映情報」．実績反映状態を「否認」にする
		Optional<Application> currentApplication = appRepo.getAppById(companyID, appID);
		currentApplication.get().changeReflectState(ReflectPlanPerState.DENIAL.value);
		// Update Domain Application
		this.appRepo.updateApplication(currentApplication.get());
		/// Thuc hien gui MAIL
		// gửi mail
		// lấy domain 申請種類別設定
		ApplicationType appType = currentApplication.get().getApplicationType();
		// get DiscreteSetting
		Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID,
				appType.value);
		// get flag check auto send mail
		// 承認処理時に自動でメールを送信するが trueの場合
		AppCanAtr sendMailWhenApprovalFlg = discreteSetting.get().getSendMailWhenApprovalFlg();
		// check Continue
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			// 申請者本人にメール送信する ===>>>>Thuc hien gui mail cho nguoi viet don

		}
		// Hien thi Message
		throw new BusinessException("Msg_222");
		// 送信先リストに項目がいる

	}

}
