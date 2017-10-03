package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
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
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.DestinationMailListOuput;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AfterApprovalProcessImpl implements AfterApprovalProcess {
	@Inject
	private ApplicationRepository appRepo;
	@Inject
	private ApprovalFrameRepository frameRepo;
	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;
	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;

	@Override
	public void detailScreenAfterApprovalProcess(String companyID, String appID, Application application) {
		List<String> listMailReceived = new ArrayList<String>();
		// Goi thằng 2.2
		// 承認情報の整理 ＝＝＞ OutPut ・申請データの内容（承認情報をメンテナンス後） 承認フェーズ枠番
		// Trong điều kiện có chuyển trạng thái thì mới update
		if (application.getReflectPerState() == ReflectPlanPerState.WAITREFLECTION) {
			// Thuc hien update Domain 申請」と紐付き「承認情報」
			application.changeReflectState(ReflectPlanPerState.WAITREFLECTION.value);
			appRepo.updateApplication(application);
		}
		// gửi mail
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
			// check Phan anh
			// trong truong hop chua phan anh
			if (application.getReflectPerState() == ReflectPlanPerState.WAITREFLECTION) {
				// Thuc hien gui mail cho nguoi viet don
				// 申請者本人にメール送信する =>>> Dung ham chung SEND MAIL
			}
		}
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
		AppCanAtr sendMailWhenRegisterFlg = discreteSetting.get().getSendMailWhenRegisterFlg();
		if (sendMailWhenRegisterFlg == AppCanAtr.CAN) {
			// 「反映情報」．実績反映状態が「反映待ち」じゃない場合
			if (application.getReflectPerState() != ReflectPlanPerState.WAITREFLECTION) {
				// Thuc hien gui mail cho nguoi viet don
				// 申請者本人にメール送信する =>>> Dung ham chung SEND MAIL
				//2017.01.2017 DuDT: commit tam vi loi
				//listMailReceived = this.MailDestination(application).destinationMail;
				// thuc hien gui Mail
				// TODO
			}
		}
		// Hien thi Message
		throw new BusinessException("Msg_220");

	}

	@Override
	public void invidialApplicationErrorCheck(String appID) {
		// TODO Auto-generated method stub
		// EA chưa viết xong
	}

	@Override
	public void invidialApplicationUpdate(String appID) {
		Optional<Application> currentApplication = appRepo.getAppById(AppContexts.user().companyId(), appID);
		int appType = currentApplication.get().getApplicationType().value;
		switch (appType) {
		case 1:
			// Update domain 打刻申請」
			break;
		case 2:
			// Update domain 残業申請」
			break;
		case 3:
			// Update domain 休日出勤申請」
			break;
		case 4:
			// Update domain 休暇申請」
			break;
		case 5:
			// update domain 出張申請」
			break;
		case 6:
			// update domain 勤務変更申請」
			break;
		case 7:
			// update domain 「直行直帰申請」
			break;
		default:
			// update domain 「振休振出申請」
			break;
		}

	}

	@Override
	public List<String> actualReflectionStateDecision(String appID, String phaseID, ApprovalAtr approvalAtr) {
		// 承認者一覧
		List<String> lstApprover = new ArrayList<>();
		List<String> lstNotApprover = new ArrayList<>();
		List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(), phaseID);
		for (ApprovalFrame frame : listFrame) {
			//2017.09.25
			/*if (frame.getApprovalATR() == ApprovalAtr.APPROVED) {
				lstApprover.add(frame.getApproverSID());
			} else {
				lstNotApprover.add(frame.getApproverSID());
			}*/
			//2017.09.25
			for(ApproveAccepted x : frame.getListApproveAccepted()){
				if (x.getApprovalATR() == ApprovalAtr.APPROVED) {
					lstApprover.add(x.getApproverSID());
				} else {
					lstNotApprover.add(x.getApproverSID());
				}	
			}
			
		}
		// Get distinct List Approver
		lstApprover.stream().distinct().collect(Collectors.toList());
		lstNotApprover.stream().distinct().collect(Collectors.toList());
		if (approvalAtr == ApprovalAtr.APPROVED) {
			return lstApprover;
		} else {
			return lstNotApprover;
		}
	}

	@Override
	public DestinationMailListOuput MailDestination(Application application) {
		DestinationMailListOuput output = new DestinationMailListOuput();
		String appID = application.getApplicationID();
		List<String> listMailReceived = new ArrayList<>();
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(AppContexts.user().companyId(), appID);
		for (AppApprovalPhase phase : listPhase) {
			List<ApprovalFrame> listFrame = frameRepo.findByPhaseID(AppContexts.user().companyId(), phase.getPhaseID());
			for (ApprovalFrame frame : listFrame) {
				if (frame.getDispOrder() >= 1 && frame.getDispOrder() <= 4) {
					// get list nguoi xac nhan
					List<String> listApprover = this.actualReflectionStateDecision(appID, phase.getPhaseID(),
							ApprovalAtr.APPROVED);
					List<String> listNotApprover = this.actualReflectionStateDecision(appID, phase.getPhaseID(),
							ApprovalAtr.UNAPPROVED);
				}
				// Su dung thang 3.2
				// truyen vao mot list dai dien nguoi xac nhan, lay ra list nguoi xac nhan

			}
		}
		//2017.01.09 dudt: commit tam vi loi
		//output.destinationMail = listMailReceived;
		//output.phaseFrameNumber = 1;
		return output;
	}

	public Application isWaitingReflectionAtr(Application application) {
		// FLAG Đã xác nhận hết
		boolean allApprovedFlg = false;
		String companyID = AppContexts.user().companyId();
		String appID = application.getApplicationID();
		// get List 5 承認 Phase
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		for (AppApprovalPhase phase : listPhase) {
			// 承認フェーズ」．承認区分が承認済以外の場合(「承認フェーズ」．承認区分 ≠ 承認済
			if (phase.getApprovalATR() != ApprovalAtr.APPROVED) {
				// Tìm trong những phase chưa được approved, lấy ra những Frame đã dc approved
				List<String> lstApproved = this.actualReflectionStateDecision(appID, phase.getPhaseID(),
						ApprovalAtr.APPROVED);
				// GỌI THẰNG 3.1 : Truyền vào lstApproved vừa nhận được companyID,trả ra 2 list,
				// OUTPUT
				// 承認者の代行情報リスト
				List<String> lstInfoRepresenter = new ArrayList<>();
				// 承認代行者リスト
				List<String> lstRepresenter = new ArrayList<>();
				// 全承認者パス設定フラグ(true, false)
				allApprovedFlg = true;// gia su la true
			} else {
				allApprovedFlg = false;
			}
		}
		// Loop tra va FLAG allAprroved
		// Tra ve trang thai Phan ANH hay cho Phan ANH
		// 「反映情報」．実績反映状態を「反映待ち」にする
		if (allApprovedFlg) {
			application.changeReflectState(ReflectPlanPerState.WAITREFLECTION.value);
		}
		return application;
	}

}
