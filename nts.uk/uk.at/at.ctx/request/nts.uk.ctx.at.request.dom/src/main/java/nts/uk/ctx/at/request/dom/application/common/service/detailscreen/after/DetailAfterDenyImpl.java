package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DetailAfterDenyImpl implements DetailAfterDeny {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	@Override
	public ProcessResult doDeny(String companyID, String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput) {
		String loginID = AppContexts.user().employeeId();
		boolean isProcessDone = false;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		List<String> autoFailServer = new ArrayList<>();
		// 3.否認する(DenyService)
		Boolean releaseFlg = approvalRootStateAdapter.doDeny(appID, loginID);
		if(!releaseFlg) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, appID,"");
		}
		isProcessDone = true;
		// 「反映情報」．実績反映状態を「否認」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「否認」)
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.DENIAL);
		}
		// アルゴリズム「反映状態の更新」を実行する
		applicationRepository.update(application);
		
		// 暫定データの登録
		List<GeneralDate> dateLst = new ArrayList<>();
		GeneralDate startDate = application.getOpAppStartDate().map(x -> x.getApplicationDate()).orElse(application.getAppDate().getApplicationDate());
		GeneralDate endDate = application.getOpAppEndDate().map(x -> x.getApplicationDate()).orElse(application.getAppDate().getApplicationDate());
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
			dateLst.add(loopDate);
		}
		// interimRemainDataMngRegisterDateChange.registerDateChange(companyID, application.getEmployeeID(), dateLst);
		// ノートのIF文を参照
		boolean condition = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet() &&
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSetting().isSendMailWhenApproval();
		if(!condition) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, appID,"");
		}
		isAutoSendMail = true;
		// 申請者本人にメール送信する(gửi mail cho người viết đơn)
		MailResult mailResult = otherCommonAlgorithm.sendMailApplicantDeny(application); 
		autoSuccessMail = mailResult.getSuccessList();
		autoFailMail = mailResult.getFailList();
		autoFailServer = mailResult.getFailServerList();
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, appID,"");
	}

}
