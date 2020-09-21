package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectManagerFromRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.InformationSettingOfAppForReflect;
import nts.uk.ctx.at.request.dom.applicationreflect.service.InformationSettingOfEachApp;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;

@Stateless
public class RegisterAtApproveReflectionInfoDefault implements RegisterAtApproveReflectionInfoService {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private AppReflectManagerFromRecord appReflectManager;
	@Inject
	private InformationSettingOfAppForReflect appSetting;
	@Override
	public void newScreenRegisterAtApproveInfoReflect(String empID, Application application) {
		// 2.承認する(ApproveService)
		approvalRootStateAdapter.doApprove(application.getAppID(), application.getEnteredPersonID(), "");
		// アルゴリズム「承認全体が完了したか」を実行する(thực hiện thuật toán 「」)
		Boolean approvalCompletionFlag = approvalRootStateAdapter.isApproveAllComplete(application.getAppID());
		if(!approvalCompletionFlag) {
			return;
		}
		// 「反映情報」．実績反映状態を「反映待ち」にする
		for(ReflectionStatusOfDay reflectionStatusOfDay : application.getReflectionStatus().getListReflectionStatusOfDay()) {
			reflectionStatusOfDay.setActualReflectStatus(ReflectedState.WAITREFLECTION);
		}
		applicationRepository.update(application);
		// 反映対象なのかチェックする(check xem có phải đối tượng phản ánh hay k?)
		if((application.isPreApp() && (application.isOverTimeApp() || application.isHolidayWorkApp()))
				|| application.isWorkChangeApp()
				|| application.isGoReturnDirectlyApp()){
			InformationSettingOfEachApp reflectSetting = appSetting.getSettingOfEachApp();
			GeneralDate startDate = application.getOpAppStartDate().isPresent() 
					? application.getOpAppStartDate().get().getApplicationDate() : application.getAppDate().getApplicationDate();
			GeneralDate endDate = application.getOpAppEndDate().isPresent() 
					? application.getOpAppEndDate().get().getApplicationDate() : application.getAppDate().getApplicationDate();
			// 社員の申請を反映(phản ánh employee application)
//			appReflectManager.reflectAppOfAppDate("",
//					application.getEmployeeID(),
//					ExecutionTypeExImport.RERUN,
//					reflectSetting,
//					new DatePeriod(startDate, endDate));
		}
	}
}
