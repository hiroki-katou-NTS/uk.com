package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 2-2.新規画面登録時承認反映情報の整理
 * 
 * @author ducpm
 *
 */
@Stateless
public class NewScreenRegisterAtApproveReflectionInfo {

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppApprovalPhaseRepository approvalPhaseRepo;
	
	@Inject 
	private ApprovalFrameRepository frameRepo;

	/**
	 * 承認情報の整理
	 */
	public void OrganizationOfApprovalInfo(String appID) {
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする(
		// get List 5 承認 Phase
		String companyID = AppContexts.user().companyId();
		// 
		List<AppApprovalPhase> listPhase = approvalPhaseRepo.findPhaseByAppID(companyID, appID);
		//get list 5 Phase ID
		List<String> list5PhaseID = listPhase.stream().map(c->c.getAppID()).collect(Collectors.toList());
		//LOOP PHASE
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
				//
				//ApprovalForm /** 承認形態 */
				//
				//「承認フェーズ」．承認形態をチェックする
			} else {
				
			}
			
			
			///CỤM THỨ 2
			// TÍNH XEM ĐÃ xác nhận hết chưa
			
			int intAppForm =  appPhase.getApprovalForm().value;
			//「承認フェーズ」．承認形態が誰か一人
			//承認フラグ
			boolean flgApprovalDone = true;
			//Trường hợp chỉ có một người approval
			if(intAppForm == 2) {
				flgApprovalDone = false;
				//Check xem trong Frame đã xác nhận hay chưa 
				// 承認枠 1～5 のループ
				for (ApprovalFrame frame : listFrame) {
					int intConfirm = frame.getConfirmATR().value;
					//Trường hợp chưa confirm
					if(intConfirm ==0) {
						
					}
				}
				
				
				
				
				
				
			} else {
			//「承認フェーズ」．承認形態が全員承認
			//trong trường hợp nhiều người approval
			//intAppForm == 1	
			//LIÊN QUAN TỚI PHẦN 8.2
				
				
			}
			
			// ドメインモデル「承認フェーズ」．承認区分が承認済(「承認フェーズ」．承認区分 = 承認済)
		}
	}

}
