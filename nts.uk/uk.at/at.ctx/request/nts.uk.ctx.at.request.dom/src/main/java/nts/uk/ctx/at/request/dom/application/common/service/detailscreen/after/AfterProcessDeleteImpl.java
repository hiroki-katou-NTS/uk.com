package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
/** 5-2.詳細画面削除後の処理*/
public class AfterProcessDeleteImpl implements AfterProcessDelete {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationApprovalService applicationApprovalService;
	
	/*@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;*/
	
	@Override
	public List<String> screenAfterDelete(String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		String companyID = AppContexts.user().companyId();
		List<String> destinationLst = new ArrayList<>();
		// ノートのIF文を参照
		AppTypeSetting appTypeSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType() == application.getAppType()).findAny().get();
		boolean condition = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet() && 
				appTypeSetting.isSendMailWhenRegister();
		if(condition) {
			// アルゴリズム「削除時のメール通知者を取得する」を実行する ( Thực hiện thuật toán 「削除時のメール通知者を取得するLấy người thông báo mail khi delete」
			destinationLst = approvalRootStateAdapter.getMailNotifierList(companyID, appID);
		}
		// アルゴリズム「申請を削除する」を実行する (Thực hiện thuật toán"Delete application" )
		applicationApprovalService.delete(appID);
		//TODO hien thi thong tin Msg_16 
		/*if (converList != null) {
			//TODO Hien thi thong tin 392
		}*/
		
		// 暫定データの登録(Đăng ký dữ liệu tạm thời)
//		List<GeneralDate> lstDate = new ArrayList<>();
//		if(application.getOpAppStartDate().isPresent() && application.getOpAppEndDate().isPresent()) {
//			GeneralDate startDate = application.getOpAppStartDate().get().getApplicationDate();
//			GeneralDate endDate = application.getOpAppEndDate().get().getApplicationDate();
//			for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
//				lstDate.add(loopDate);
//			}	
//		}
		// refactor 4
		/*interimRemainDataMngRegisterDateChange.registerDateChange(
				companyID, 
				application.getEmployeeID(), 
				lstDate.isEmpty() ? Arrays.asList(application.getAppDate().getApplicationDate()) : lstDate);*/
		return destinationLst;
	}

}
