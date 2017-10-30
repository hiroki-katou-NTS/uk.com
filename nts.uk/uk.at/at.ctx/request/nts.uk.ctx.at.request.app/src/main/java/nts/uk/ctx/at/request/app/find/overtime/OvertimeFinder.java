package nts.uk.ctx.at.request.app.find.overtime;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.shr.com.context.AppContexts;


public class OvertimeFinder {
	
	/** アルゴリズム「1-1.新規画面起動前申請共通設定を取得する」を実行する (Thực thi 「1-1.新規画面起動前申請共通設定を取得する」) */
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	/** アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する (Thực thi 「1-4.新規画面起動時の承認ルート取得パターン」) */
	@Inject
	private StartApprovalRootService startApprovalRootService;
	
	/** アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する (Thực thi 「1-5.新規画面起動時のエラーチェック」) */
	@Inject
	private  StartCheckErrorService  startCheckErrorService;
	
	// 02_残業区分チェック 
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private OvertimeInstructRepository overtimeInstructRepository;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository discreteRepo;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	@Inject 
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;
	
	@Inject
	private AppEmploymentSettingRepository appEmploymentSettingRepository;
	
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	public OverTimeDto getAllOvertime(String url,String appDate,int uiType){
		
		OverTimeDto result = new OverTimeDto();
		ApplicationDto applicationDto = new ApplicationDto();
		OverTimeInstruct overtimeInstruct = new OverTimeInstruct();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		int rootAtr = 1;
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), GeneralDate.localDate(LocalDate.parse(appDate)));
		//アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		startApprovalRootService.getApprovalRootPattern(companyID, employeeID, 1, ApplicationType.OVER_TIME_APPLICATION.value, null);
		//アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する 
		startCheckErrorService.checkError(ApplicationType.OVER_TIME_APPLICATION.value);
		// 02_残業区分チェック : check loai lam them
		int overtimeAtr = overtimeService.checkOvertime(url);
		//申請日付を取得 : lay thong tin lam them
		applicationDto.setInputDate(appDate);
		
		if(appCommonSettingOutput != null){
			int useAtr = appCommonSettingOutput.requestOfEachCommon.getRequestAppDetailSettings().get(0).getUserAtr().value;
			if(useAtr == UseAtr.USE.value){
				if(appDate != null){
					overtimeInstruct = overtimeInstructRepository.getOvertimeInstruct(GeneralDate.localDate(LocalDate.parse(appDate)), employeeID);
				}
			}
		}
		//時間外労働を取得: lay lao dong ngoai thoi gian
		/*
		 * chưa phải làm
		 */
		// 事前事後区分を取得
			getDisplayPrePost(companyID, applicationDto, result, uiType);
			String workplaceID = employeeAdapter.getWorkplaceId(companyID, employeeID, GeneralDate.today());
			
			Optional<RequestAppDetailSetting> requestAppDetailSetting = requestOfEachWorkplaceRepository.getRequestDetail(companyID, workplaceID, ApplicationType.OVER_TIME_APPLICATION.value);
			if (requestAppDetailSetting.isPresent()) {
				// 時刻計算利用チェック
				if (requestAppDetailSetting.get().getTimeCalUseAtr().value == UseAtr.USE.value) {
					// 07_勤務種類取得: lay loai di lam
					getWorkType(companyID, employeeID,appDate,result);
					// 就業時間帯を取得(lay mui gio lam viec)
					getWorkingHours(companyID, employeeID, result);
				}
			}
		return null;
	}
	// 事前事後区分を取得
	private void getDisplayPrePost(String companyID,ApplicationDto applicationDto,OverTimeDto result,int uiType){
		Optional<ApplicationSetting> applicationSetting = applicationSettingRepository.getApplicationSettingByComID(companyID);
		if(applicationSetting.isPresent()){
			// if display then check What call UI?
			if(applicationSetting.get().getDisplayPrePostFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setDisplayPrePostFlg(AppDisplayAtr.DISPLAY.value);
				/**
				 * check UI
				 * 0: メニューから起動 :menu
				 * other: 日別修正、トップページアラームから起動,残業指示から起動
				 */
				if(uiType == 0){
					Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo.getAppTypeDiscreteSettingByAppType(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
					if(discreteSetting.isPresent()){
						applicationDto.setPrePostAtr(discreteSetting.get().getPrePostInitFlg().value);
					}
				}else{
					//事後申請として起動する(khoi dong cai xin sau len)
					applicationDto.setPrePostAtr(InitValueAtr.POST.value);
				}
			}else{
				//if not display
				result.setDisplayPrePostFlg(AppDisplayAtr.NOTDISPLAY.value);
			}
		}
	}
	private void getWorkType(String companyID,String employeeID,String appDate,OverTimeDto result){
		// アルゴリズム「社員所属雇用履歴を取得」を実行する
		SEmpHistImport sEmpHistImport = employeeAdapter.getEmpHist(companyID, employeeID, GeneralDate.today());
		if (sEmpHistImport != null) {
			// ドメインモデル「申請別対象勤務種類」を取得
			List<AppEmployWorkType> employWorkTypes = this.appEmploymentSettingRepository.getEmploymentWorkType(
					companyID, sEmpHistImport.getEmploymentCode(), ApplicationType.OVER_TIME_APPLICATION.value);
			if (employWorkTypes != null) {
				// ドメインモデル「申請別対象勤務種類」.勤務種類リストを表示する(hien thi list(申請別対象勤務種類))
			} else {
				/*
				 * ドメインモデル「勤務種類」を取得 anh chinh lam
				 */
				/*
				 * ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
				 */
				
			}
		}
	}
	private void getWorkingHours(String companyID,String employeeID,OverTimeDto result){
		// 1.職場別就業時間帯を取得
		otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID, GeneralDate.today());
		/*
		 * ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
		 */
	}
}
