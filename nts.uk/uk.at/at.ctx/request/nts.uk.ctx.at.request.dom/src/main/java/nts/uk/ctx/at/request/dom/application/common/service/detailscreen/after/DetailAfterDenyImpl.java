package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
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
	
//	@Inject
//	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

	@Override
	public ProcessResult doDeny(String companyID, String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput, String memo) {
		String loginID = AppContexts.user().employeeId();
		ProcessResult processResult = new ProcessResult();
		processResult.setAppID(appID);
		// 3.否認する(DenyService)
		Boolean releaseFlg = approvalRootStateAdapter.doDeny(appID, loginID, memo);
		if(!releaseFlg) {
			return processResult;
		}
		processResult.setProcessDone(true);
		// 「反映情報」．実績反映状態を「否認」にする(chuyển trạng thái 「反映情報」．実績反映状態 thành 「否認」)
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.DENIAL);
		}
		// アルゴリズム「反映状態の更新」を実行する
		applicationRepository.update(application);
		
		// 暫定データの登録
//		List<GeneralDate> dateLst = new ArrayList<>();
//		GeneralDate startDate = application.getOpAppStartDate().map(x -> x.getApplicationDate()).orElse(application.getAppDate().getApplicationDate());
//		GeneralDate endDate = application.getOpAppEndDate().map(x -> x.getApplicationDate()).orElse(application.getAppDate().getApplicationDate());
//		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)){
//			dateLst.add(loopDate);
//		}
		// interimRemainDataMngRegisterDateChange.registerDateChange(companyID, application.getEmployeeID(), dateLst);
		return processResult;
	}

}
