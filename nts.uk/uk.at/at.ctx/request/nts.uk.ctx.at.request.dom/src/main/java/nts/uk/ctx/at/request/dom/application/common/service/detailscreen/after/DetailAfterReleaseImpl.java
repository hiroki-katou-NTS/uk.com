package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenProcessAfterOutput;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DetailAfterReleaseImpl implements DetailAfterRelease {
	
	@Inject 
	private AfterApprovalProcess afterApprovalProcess;
	
	@Inject
	private ApplicationRepository appRepo;
	
	@Override
	public void detailAfterRelease(Application application, String loginID) {
		List<AppApprovalPhase> appApprovalPhaseList = application.getListPhase();
		boolean cancelFlag = false;
		
		// loop Phase
		for(int i = 0; i < appApprovalPhaseList.size(); i++){
			AppApprovalPhase appApprovalPhase = appApprovalPhaseList.get(i);
			// アルゴリズム「承認者一覧を取得する」を実行する(thực hiện xử lý 「承認者一覧を取得する」)
			List<String> approverList = afterApprovalProcess.getListApprover(application.getCompanyID(), application.getApplicationID());
			
			// ループ中の承認フェーズに承認者がいる
			// không có approver trong phase thì chuyển phase
			if(approverList.size() < 1 ) continue;	
			
			// ループ中の承認フェーズには承認を行ったか( kiểm tra approve phase đang xử lý đã được xác nhận chưa)
			// không có thì chuyển phase
			boolean approvalFlag = appApprovalPhase.getApprovalATR().equals(ApprovalAtr.UNAPPROVED);
			for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
				for(ApproveAccepted approveAccepted : approvalFrame.getListApproveAccepted()){
					approvalFlag = approvalFlag && approveAccepted.getApprovalATR().equals(ApprovalAtr.UNAPPROVED);
				}
			}
			if(approvalFlag) continue;
			
			// 解除できるかチェックする(kiểm tra có thể release hay không)
			// không thì kết thúc
			cancelFlag = this.cancelCheck(appApprovalPhase, loginID);
			if(!cancelFlag) break;
			
			// ドメインモデル「承認フェーズ」．「承認枠」1～5ループする(loop xu ly theo domain 「承認フェーズ」．「承認枠」1～5)
			for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
				for(ApproveAccepted approveAccepted : approvalFrame.getListApproveAccepted()){
					// ログイン者が承認を行った承認者かチェックする(kiểm tra xem người đã xác nhận có phải nguoi login hay không)
					// có thì thay đổi approveAccepted
					if(approveAccepted.getApproverSID().equals(loginID)||approveAccepted.getRepresenterSID().equals(loginID)){
						approveAccepted.setApprovalATR(ApprovalAtr.UNAPPROVED);
						// clear ApproverSID and RepresenterSID ???
						// approveAccepted.setApproverSID("");
						// approveAccepted.setRepresenterSID("");
					}
				}
			}
			
			appApprovalPhase.setApprovalATR(ApprovalAtr.UNAPPROVED);
			
		}
		
		if(!cancelFlag) {
			throw new BusinessException("Khong the release");
		}
		
		// 「反映情報」．実績反映状態を「未反映」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「未反映」)
		application.setReflectPerState(ReflectPlanPerState.NOTREFLECTED);
		appRepo.fullUpdateApplication(application);
		// ドメインモデル「申請」と紐付き「承認情報」「反映情報」をUpdateする(update domain 「申請」 và 「承認情報」「反映情報」 tương ứng)
		// 情報メッセージ（Msg_221）(hiển thị message thông báo （Msg_221）)
	}

	@Override
	public boolean cancelCheck(AppApprovalPhase appApprovalPhase, String loginID) {
		boolean cancelCheck = false;
		
		// 「承認フェーズ」．承認形態をチェックする(kiểm tra 「承認フェーズ」．承認形態)
		boolean approvalAllFlag = true;
		for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
			for(ApproveAccepted approveAccepted : approvalFrame.getListApproveAccepted()){
				approvalAllFlag = approvalAllFlag && !approveAccepted.getApprovalATR().equals(ApprovalAtr.UNAPPROVED);
			}
		}
		if(!approvalAllFlag) {
			// 確定者を設定したかチェックする(kiểm tra có cài đặt 確定者 hay không)
			boolean confirmFlag = false;
			for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
				for(ApproveAccepted approveAccepted : approvalFrame.getListApproveAccepted()){
					confirmFlag = confirmFlag || approveAccepted.getConfirmATR().equals(ConfirmAtr.USEATR_USE);
				}
			}
			if(confirmFlag){
				// ログイン者が承認を行った承認者かチェックする(kiểm tra xem người đã xác nhận có phải là người login hay không)
				// có thì set cancelCheck = true
				for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
					for(ApproveAccepted approveAccepted : approvalFrame.getListApproveAccepted()){
						if(approveAccepted.getApproverSID().equals(loginID)||approveAccepted.getRepresenterSID().equals(loginID)){
							cancelCheck = true;
							return cancelCheck;
						};
					}
				}
			} 
		}
		// ログイン者が承認を行った承認者かチェックする(kiểm tra xem người đã xác nhận có phải là người login hay không)
		// có thì set cancelCheck = true
		boolean loginFlag = false;
		for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()){
			for(ApproveAccepted approveAccepted : approvalFrame.getListApproveAccepted()){
				loginFlag = loginFlag || approveAccepted.getApproverSID().equals(loginID)||approveAccepted.getRepresenterSID().equals(loginID);
			}
		}
		if(loginFlag) {
			cancelCheck = true;
		}
		return cancelCheck;
	}
}
