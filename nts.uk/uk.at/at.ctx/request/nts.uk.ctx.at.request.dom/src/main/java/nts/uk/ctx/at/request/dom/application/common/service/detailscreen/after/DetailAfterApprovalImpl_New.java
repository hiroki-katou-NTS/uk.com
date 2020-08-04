package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.ApprovalMailSendCheck;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.NewRegisterMailSendCheck;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAfterApprovalImpl_New implements DetailAfterApproval_New {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	/*@Inject
	private AppReflectManager appReflectManager;*/
	
	@Inject
	private ApprovalMailSendCheck approvalMailSendCheck;
	
	@Inject
	private NewRegisterMailSendCheck newRegisterMailSendCheck;
	
	@Override
	public ProcessResult doApproval(String companyID, String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput, String memo) {
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		List<String> autoFailServer = new ArrayList<>();
		String loginEmployeeID = AppContexts.user().employeeId();
		// 2.承認する(ApproveService)
		Integer phaseNumber = approvalRootStateAdapter.doApprove(appID, loginEmployeeID, memo);
		// アルゴリズム「承認全体が完了したか」を実行する ( Thực hiện thuật toán ''Đã hoàn thành toàn bộ approve hay chưa"
		Boolean allApprovalFlg = approvalRootStateAdapter.isApproveAllComplete(appID);
		String reflectAppId = "";
		if(allApprovalFlg.equals(Boolean.TRUE)){
			// 実績反映状態 = 反映状態．反映待ち
			//application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.WAITREFLECTION);
			//予定反映状態 = 反映状態．反映待ち
			//application.getReflectionInformation().setStateReflection(ReflectedState_New.WAITREFLECTION);
			// applicationRepository.update(application);
			reflectAppId = application.getAppID();
			// 反映対象なのかチェックする(check xem có phải đối tượng phản ánh hay k?)
			if((application.isPreApp() && (application.isOverTimeApp() || application.isLeaveTimeApp()))
				|| application.isWorkChangeApp()
				|| application.isGoReturnDirectlyApp()){
				// 社員の申請を反映(phản ánh employee application)
				//appReflectManager.reflectEmployeeOfApp(application);
			}
		} else {
			// ドメインモデル「申請」と紐付き「反映情報」．実績反映状態 = 反映状態．未反映
			//application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
			// applicationRepository.update(application);
			// INPUT．申請表示情報．申請表示情報(基準日関係なし)．メールサーバ設定済区分をチェックする
			if(!appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet()) {
				return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, appID, reflectAppId);
			}
		}
		isAutoSendMail = true;
		// アルゴリズム「承認処理後にメールを自動送信するか判定」を実行する ( Thực hiện thuật toán「Xác định có tự động gửi thư sau khi xử lý phê duyệt hay không」 
		ProcessResult processResult1 = approvalMailSendCheck.sendMail(
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSetting(), 
				application, 
				allApprovalFlg);
		autoSuccessMail.addAll(processResult1.getAutoSuccessMail());
		autoFailMail.addAll(processResult1.getAutoFailMail());
		autoFailServer.addAll(processResult1.getAutoFailServer());
		// アルゴリズム「新規登録時のメール送信判定」を実行する ( Thực hiện thuật toán 「 Xác định gửi mail khi đăng ký mới」
		ProcessResult processResult2 = newRegisterMailSendCheck.sendMail(
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSetting(), 
				application, 
				phaseNumber);
		autoSuccessMail.addAll(processResult2.getAutoSuccessMail());
		autoFailMail.addAll(processResult2.getAutoFailMail());
		autoFailServer.addAll(processResult2.getAutoFailServer());
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, appID, reflectAppId);
	}

}
