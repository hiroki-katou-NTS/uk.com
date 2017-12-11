package nts.uk.ctx.at.request.dom.application.workchange;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeAppCommonSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;

@Stateless
public class WorkChangeDetailServiceImpl implements IWorkChangeDetailService {

	@Inject
	private ApplicationRepository appRepository;

	@Inject
	private EmployeeRequestAdapter employeeAdapter;

	@Inject
	private BeforeAppCommonSetting beforeAppCommonSetting;

	/** アルゴリズム「14-2.詳細画面起動前申請共通設定を取得する」を実行する*/
	@Inject 
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private IAppWorkChangeRepository appWorkChangeReposity;
	
	@Inject 
	private InitMode initMode;
	
	@Inject
	ApplicationReasonRepository appFormReposity;
	
	@Override
	public WorkChangeDetail getWorkChangeDetailById(String cid, String appId) {
		WorkChangeDetail workChangeDetail = new WorkChangeDetail();
		Application application = appRepository.getAppById(cid, appId).get();
		
		// アルゴリズム「14-1.詳細画面起動前モードの判断」を実行する
		workChangeDetail.setPrelaunchAppSetting(beforeAppCommonSetting.getPrelaunchAppSetting(appId));

		// アルゴリズム「14-2.詳細画面起動前申請共通設定を取得する」を実行する
		DetailedScreenPreBootModeOutput preBootOuput = beforePreBootMode.judgmentDetailScreenMode(application, application.getApplicationDate());
		workChangeDetail.setDetailedScreenPreBootModeOutput(preBootOuput);
		
		//アルゴリズム「勤務変更申請基本データ（更新）」を実行する
		AppWorkChange appWorkChange = appWorkChangeReposity.getAppworkChangeById(cid, appId).get();
		workChangeDetail.setAppWorkChange(appWorkChange);
		
		//アルゴリズム「14-3.詳細画面の初期モード」を実行する
		workChangeDetail.setDetailScreenInitModeOutput(initMode.getDetailScreenInitMode(preBootOuput.getUser(), preBootOuput.getReflectPlanState().value));
		
		// Setting application property
		workChangeDetail.setApplication(application);
		workChangeDetail.setEmployeeName(employeeAdapter.getEmployeeName(application.getApplicantSID()));
		
		//基準日　＝　申請日付（開始日）
		GeneralDate basicDate = application.getStartDate();
		GeneralDate endDate = application.getEndDate();
		while(basicDate.beforeOrEquals(endDate)){
			//「休日に関して」チェック有無は判定
			if(appWorkChange.getExcludeHolidayAtr() == 0 || checkHoliday(basicDate)){
				//TODO: アルゴリズム「実績の取得」を実行する
				//13.実績を取得する
			}
			//基準日　＝　基準日＋１
			basicDate = basicDate.addDays(1);
		}
		
		return workChangeDetail;
	}
	
	private boolean checkHoliday(GeneralDate basicDate){
		return true;
	}

}
