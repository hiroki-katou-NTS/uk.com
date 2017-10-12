package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author ducpm
 *
 */
@Stateless
public class AfterDenialProcessImpl implements AfterDenialProcess {

	@Inject
	private AfterApprovalProcess afterApprovalProcess;

	@Inject
	private ApprovalFrameRepository frameRepo;

	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;

	@Inject
	private AgentAdapter approvalAgencyInformationService;
	
	@Inject 
	private EmployeeAdapter employeeAdapter;

	@Override
	public String detailedScreenAfterDenialProcess(Application application) {
		// 否認できるフラグ
		boolean canDeniedFlg = false;
		String email = "";
		String loginEmp = AppContexts.user().employeeId();
		String companyID = AppContexts.user().companyId();
		String appID = application.getApplicationID();
		//ドメインモデル「申請」．「承認フェーズ」5～1の順でループする
		List<AppApprovalPhase> listPhase = application.getListPhase().stream().sorted(Comparator.comparingInt(AppApprovalPhase::getDispOrder).reversed()).collect(Collectors.toList());
		for (AppApprovalPhase phase : listPhase) {
			//アルゴリズム「承認者一覧を取得する」を実行する
			List<String> listApprover = afterApprovalProcess.actualReflectionStateDecision(appID, phase.getPhaseID(), ApprovalAtr.APPROVED);
			// Check All ApproveAtr is NOT Approve
			if (this.isAllUnapproved(phase)) {
				if(phase.getDispOrder() - 1 < 0) {
					continue;
				}
				canDeniedFlg = this.canDeniedCheck(application,phase.getDispOrder() - 1);
				if (!canDeniedFlg) {
					continue;
				}
			} else {
				List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(),
						phase.getPhaseID());
				for (ApprovalFrame frame : listFrame) {
					List<String> approverIds = frame.getListApproveAccepted().stream().map(x -> x.getApproverSID())
							.collect(Collectors.toList());
					if (approverIds.contains(loginEmp)) {
						for (ApproveAccepted appAccepted : frame.getListApproveAccepted()) {
							if (appAccepted.getApprovalATR() == ApprovalAtr.UNAPPROVED) {
								// (ループ中の「承認枠」)承認区分=「否認」、承認者=ログイン者の社員ID、代行者=空
								appAccepted.changeApprovalATR(ApprovalAtr.DENIAL);
								appAccepted.changeApproverSID(loginEmp);
								appAccepted.changeRepresenterSID(null);
							}
						}
					} else {
						// 3-1.承認代行情報の取得処理
						AgentPubImport agency = this.approvalAgencyInformationService
								.getApprovalAgencyInformation(companyID, approverIds);
						if (agency.getListApproverAndRepresenterSID().contains(loginEmp)) {
							// (ドメインモデル「承認枠」)承認区分=「否認」、承認者=空、代行者=ログイン者の社員ID
							for (ApproveAccepted appAccepted : frame.getListApproveAccepted()) {
								appAccepted.changeApprovalATR(ApprovalAtr.DENIAL);
								appAccepted.changeApproverSID(null);
								appAccepted.changeRepresenterSID(loginEmp);
							}
						}
					}
				}
			}
		}
		// 「反映情報」．実績反映状態を「否認」にする
		application.changeReflectState(ReflectPlanPerState.DENIAL.value);
		//SEND mail
		// lấy domain 申請種類別設定
		ApplicationType appType = application.getApplicationType();
		// get DiscreteSetting
		Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID,
				appType.value);
		// get flag check auto send mail
		// 承認処理時に自動でメールを送信するが trueの場合
		AppCanAtr sendMailWhenApprovalFlg = discreteSetting.get().getSendMailWhenApprovalFlg();
		// check Continue
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			// 申請者本人にメール送信する ===>>>>Thuc hien gui mail cho nguoi viet don
			email = employeeAdapter.empEmail(loginEmp);
		}
		// Hien thi Message
		// 送信先リストに項目がいる
		return email;
	}
	/**
	 * 否認できるかチェックする true：否認できる false：否認できない
	 */
	@Override
	public boolean canDeniedCheck(Application application, int startOrderNum) {
		String appID = application.getApplicationID();
		List<AppApprovalPhase> listPhase = application.getListPhase();
		if (startOrderNum > 0) {
			// アルゴリズム「承認者一覧を取得する」を実行する
			for (AppApprovalPhase phase : listPhase) {
				List<String> listApprover = afterApprovalProcess.actualReflectionStateDecision(appID,
						phase.getPhaseID(), ApprovalAtr.APPROVED);
				// ループ中の承認フェーズに承認者がいる
				if (phase.getApprovalATR() == ApprovalAtr.APPROVED) {
					List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(),
							phase.getPhaseID());
					boolean isAllFalse = this.isAllConfirm(phase);
					if (isAllFalse) {
						for (ApprovalFrame frame : listFrame) {
							for (ApproveAccepted x : frame.getListApproveAccepted()) {
								if (x.getApproverSID().contains(AppContexts.user().employeeId())
										|| x.getRepresenterSID().contains(AppContexts.user().employeeId())) {
									return false;
								}
							}
						}
					} else {
						for (ApprovalFrame frame : listFrame) {
							for (ApproveAccepted x : frame.getListApproveAccepted()) {
								if (x.getConfirmATR() == ConfirmAtr.USEATR_USE) {
									if (x.getApproverSID().contains(AppContexts.user().employeeId())
											|| x.getRepresenterSID().contains(AppContexts.user().employeeId())) {
										return false;
									}
								}
							}
						}
					}
					// 「承認フェーズ」．承認区分が承認済じゃない(「承認フェーズ」．承認区分 ≠ 承認済)
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Check list ApproveAccepted is Unapproved
	 * 
	 * @param listApprove
	 * @return
	 */
	private boolean isAllUnapproved(AppApprovalPhase phase) {
		boolean isAllUnapproved = true;
		for (ApprovalFrame frame : phase.getListFrame()) {
			for (ApproveAccepted x : frame.getListApproveAccepted()) {
				if(x.getApprovalATR() ==ApprovalAtr.APPROVED) {
					isAllUnapproved = false;
				}
			}
		}
		return isAllUnapproved;
	}

	/**
	 * Check list ApproveAccepted is
	 * 
	 * @param phase
	 * @return
	 */
	private boolean isAllConfirm(AppApprovalPhase phase) {
		boolean isAllConfirm = false;
		for (ApprovalFrame frame : phase.getListFrame()) {
			for (ApproveAccepted x : frame.getListApproveAccepted()) {
				if (x.getConfirmATR() == ConfirmAtr.USEATR_USE) {
					isAllConfirm = true;
				}
			}
		}
		return isAllConfirm;
	}

}
