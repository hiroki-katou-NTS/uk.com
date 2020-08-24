package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessDeleteResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
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

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	/*@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;*/

	@Override
	public ProcessDeleteResult screenAfterDelete(String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		String companyID = AppContexts.user().companyId();
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		List<String> autoFailServer = new ArrayList<>();
		// ノートのIF文を参照
		boolean condition = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet() &&
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSetting().isSendMailWhenRegister();
		if(condition) {
			isAutoSendMail = true;
			// アルゴリズム「削除時のメール通知者を取得する」を実行する ( Thực hiện thuật toán 「削除時のメール通知者を取得するLấy người thông báo mail khi delete」
			List<String> converList = approvalRootStateAdapter.getMailNotifierList(companyID, appID);
			// 送信先リストに項目がいるかチェックする(kiểm tra danh sách người xác nhận có mục nào hay không)
			if(!CollectionUtil.isEmpty(converList)){
				// 送信先リストにメールを送信する(gửi mail cho danh sách người xác nhận)
				// MailResult mailResult =
				// otherCommonAlgorithm.sendMailApproverDelete(converList, application);
				// autoSuccessMail = mailResult.getSuccessList();
				// autoFailMail = mailResult.getFailList();
				// autoFailServer = mailResult.getFailServerList();
			}
		}
		// アルゴリズム「申請を削除する」を実行する (Thực hiện thuật toán"Delete application" )
		applicationApprovalService.delete(appID);
		//TODO hien thi thong tin Msg_16
		/*if (converList != null) {
			//TODO Hien thi thong tin 392
		}*/

		// 暫定データの登録(Đăng ký dữ liệu tạm thời)
		List<GeneralDate> lstDate = new ArrayList<>();
		if(application.getOpAppStartDate().isPresent() && application.getOpAppEndDate().isPresent()) {
			GeneralDate startDate = application.getOpAppStartDate().get().getApplicationDate();
			GeneralDate endDate = application.getOpAppEndDate().get().getApplicationDate();
			for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
				lstDate.add(loopDate);
			}
		}
		// refactor 4
		/*interimRemainDataMngRegisterDateChange.registerDateChange(
				companyID,
				application.getEmployeeID(),
				lstDate.isEmpty() ? Arrays.asList(application.getAppDate().getApplicationDate()) : lstDate);*/
		return new ProcessDeleteResult(
				new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, appID,""),
				application.getAppType());
	}

}
