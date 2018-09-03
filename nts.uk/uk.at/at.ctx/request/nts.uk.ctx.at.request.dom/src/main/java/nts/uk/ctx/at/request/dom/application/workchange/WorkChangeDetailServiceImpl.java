package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforeAppCommonSetting;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class WorkChangeDetailServiceImpl implements IWorkChangeDetailService {

	@Inject
	private ApplicationRepository_New appRepository;

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
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;
	
	@Override
	public WorkChangeDetail getWorkChangeDetailById(String cid, String appId) {
		WorkChangeDetail workChangeDetail = new WorkChangeDetail();
		//15.詳細画面申請データを取得する
		Optional<Application_New> applicationOpt = appRepository.findByID(cid, appId);		
		if (!applicationOpt.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		Application_New application = applicationOpt.get();
		// アルゴリズム「14-1.詳細画面起動前モードの判断」を実行する
		workChangeDetail.setPrelaunchAppSetting(beforeAppCommonSetting.getPrelaunchAppSetting(appId));

		// アルゴリズム「14-2.詳細画面起動前申請共通設定を取得する」を実行する
		DetailedScreenPreBootModeOutput preBootOuput = beforePreBootMode.judgmentDetailScreenMode(cid, application.getEmployeeID(), appId, application.getAppDate());
		workChangeDetail.setDetailedScreenPreBootModeOutput(preBootOuput);
		
		//アルゴリズム「勤務変更申請基本データ（更新）」を実行する
		AppWorkChange appWorkChange = appWorkChangeReposity.getAppworkChangeById(cid, appId).get();		
		//Get work type name & work time name
		Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(cid, appWorkChange.getWorkTimeCd());
		if (workTime.isPresent()) {
			appWorkChange.setWorkTimeName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().v());
		}
		Optional<WorkType> workType = workTypeRepository.findByPK(cid, appWorkChange.getWorkTypeCd());
		if (workType.isPresent()) {
			appWorkChange.setWorkTypeName(workType.get().getName().v());
		}
		workChangeDetail.setAppWorkChange(appWorkChange);
		workChangeDetail.setTimeRequired(workChangeRegisterService.isTimeRequired(appWorkChange.getWorkTypeCd()));
		//アルゴリズム「14-3.詳細画面の初期モード」を実行する
		workChangeDetail.setDetailScreenInitModeOutput(initMode.getDetailScreenInitMode(preBootOuput.getUser(), preBootOuput.getReflectPlanState().value));
		// Setting application property
		workChangeDetail.setApplication(application);
		workChangeDetail.setEmployeeName(employeeAdapter.getEmployeeName(application.getEmployeeID()));
		
		//基準日　＝　申請日付（開始日）
		GeneralDate basicDate = GeneralDate.today();
		GeneralDate endDate = GeneralDate.today();
		
		if(application.getStartDate().isPresent()){
			basicDate = application.getStartDate().get();
		}
		if(application.getEndDate().isPresent()){
			endDate = application.getEndDate().get();
		}
		List<String> workTypes = new ArrayList<String>();
		List<String> workTimes = new ArrayList<String>();		
		while(basicDate.beforeOrEquals(endDate)){
			//13.実績を取得する
			AchievementOutput achievement  = collectAchievement.getAchievement(cid, application.getEmployeeID(), basicDate);
			workTypes.add(achievement.getWorkType().getWorkTypeCode());
			workTimes.add(achievement.getWorkTime().getWorkTimeCD());
			//基準日　＝　基準日＋１
			basicDate = basicDate.addDays(1);
		}
		workChangeDetail.setWorkTimeCodes(workTimes);
		workChangeDetail.setWorkTypeCodes(workTypes);
		
		return workChangeDetail;
	}

}
