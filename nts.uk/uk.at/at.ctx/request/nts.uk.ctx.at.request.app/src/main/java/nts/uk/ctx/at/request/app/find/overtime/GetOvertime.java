package nts.uk.ctx.at.request.app.find.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.overtime.dto.OvertimeInputDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartApprovalRootService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.StartCheckErrorService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReasonRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.requestofeach.AtWorkAtr;
import nts.uk.ctx.at.request.dom.setting.requestofeach.DisplayFlg;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrame;
import nts.uk.ctx.at.shared.dom.employmentrule.hourlate.overtime.overtimeframe.OvertimeFrameRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetOvertime {
	final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	@Inject
	private StartApprovalRootService startApprovalRootService;
	
	@Inject
	private  StartCheckErrorService  startCheckErrorService;
	
	@Inject
	private OvertimeService overtimeService;
	
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
	
	@Inject
	private WorkTimeRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private ApplicationReasonRepository applicationReasonRepository;
	
	@Inject
	private DivergenceReasonRepository diReasonRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private OvertimeInputRepository overtimeInputRepository;
	
	@Inject
	private OvertimeFrameRepository overtimeFrameRepository;
	
	/**
	 * @param url
	 * @param appDate
	 * @param uiType
	 * @return
	 */
	public OverTimeDto getOvertimeByUIType(String url,String appDate,int uiType){
		
		OverTimeDto result = new OverTimeDto();
		ApplicationDto applicationDto = new ApplicationDto();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		int rootAtr = 1;
		
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT));
		//アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		startApprovalRootService.getApprovalRootPattern(companyID, employeeID, 1, ApplicationType.OVER_TIME_APPLICATION.value, null);
		//アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する 
		startCheckErrorService.checkError(ApplicationType.OVER_TIME_APPLICATION.value);
		// 02_残業区分チェック : check loai lam them
		int overtimeAtr = overtimeService.checkOvertime(url);
		//申請日付を取得 : lay thong tin lam them
		applicationDto.setApplicationDate(appDate);
		// 01-01_残業通知情報を取得
		OverTimeInstruct overtimeInstruct = overtimeService.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
		
		//01-02_時間外労働を取得: lay lao dong ngoai thoi gian
		/*
		 * chưa phải làm
		 */
		// 01-13_事前事後区分を取得
		getDisplayPrePost(companyID, applicationDto, result, uiType,appDate);
		
		String workplaceID = employeeAdapter.getWorkplaceId(companyID, employeeID, GeneralDate.today());
		Optional<RequestAppDetailSetting> requestAppDetailSetting = requestOfEachWorkplaceRepository.getRequestDetail(companyID, workplaceID, ApplicationType.OVER_TIME_APPLICATION.value);
		if (requestAppDetailSetting.isPresent()) {
			// 時刻計算利用チェック
			if (requestAppDetailSetting.get().getTimeCalUseAtr().value == UseAtr.USE.value) {
				result.setDisplayCaculationTime(true);
				//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
				Optional<PersonalLaborCondition> personalLablorCodition = personalLaborConditionRepository.findById(employeeID,GeneralDate.fromString(appDate, DATE_FORMAT));
				// 07_勤務種類取得: lay loai di lam
				getWorkType(companyID, employeeID,personalLablorCodition,result);
				// 08_就業時間帯取得(lay loai gio lam viec) 
				getWorkingHourType(companyID, employeeID,personalLablorCodition,result);
				// 01-14_勤務時間取得(lay thoi gian)
				getWorkingHours(companyID, employeeID,appDate,requestAppDetailSetting,result);
				// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
				getRestTime(companyID, employeeID, requestAppDetailSetting, result);
				
			}else{
				result.setDisplayCaculationTime(false);
			}
		}
		// 01-03_残業枠を取得
		getOvertimeHours(companyID,overtimeAtr,result);
		
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		// 01-04_加給時間を取得
		getBonusTime(companyID,employeeID,appDate,overtimeRestAppCommonSet,result);
		
		// 01-05_申請定型理由を取得, 01-06_申請理由を取得
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID,  ApplicationType.OVER_TIME_APPLICATION.value);
		if(appTypeDiscreteSetting.isPresent()){
			// 01-05_申請定型理由を取得
			if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setTypicalReasonDisplayFlg(true);
				getApplicationReasonType(companyID,result);
			}else{
				result.setTypicalReasonDisplayFlg(false);
			}
			//01-06_申請理由を取得
			if(appTypeDiscreteSetting.get().getDisplayReasonFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setDisplayAppReasonContentFlg(true);
			}else{
				result.setDisplayAppReasonContentFlg(false);
			}
		}
		if(overtimeRestAppCommonSet.isPresent()){
			//01-08_乖離定型理由を取得
			if(overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonForm(true);
				getDivergenceReasonForm(companyID,result);
			}else{
				result.setDisplayDivergenceReasonForm(false);
			}
			//01-07_乖離理由を取得
			if(overtimeRestAppCommonSet.get().getDivergenceReasonInputAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonInput(true);
			}else{
				result.setDisplayDivergenceReasonInput(false);
			}
		}
		//01-09_事前申請を取得
		if(result.getDisplayPrePostFlg() == InitValueAtr.POST.value ){
			getPreApplication(employeeID,overtimeRestAppCommonSet, appDate, applicationDto, result);
		}
		result.setApplication(applicationDto);
		String employeeName = "";
		if(Strings.isNotBlank(applicationDto.getApplicantSID())){
			employeeName = employeeAdapter.getEmployeeName(applicationDto.getApplicantSID());
		} else {
			employeeName = employeeAdapter.getEmployeeName(employeeID);
		}
		result.setEmployeeName(employeeName);
		return result;
	}
	
	/**
	 * 01-13_事前事後区分を取得
	 * @param companyID
	 * @param applicationDto
	 * @param result
	 * @param uiType
	 */
	private void getDisplayPrePost(String companyID,ApplicationDto applicationDto,OverTimeDto result,int uiType,String appDate){
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
				applicationDto.setPrePostAtr(this.otherCommonAlgorithm.preliminaryJudgmentProcessing(EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value,ApplicationType.class), GeneralDate.fromString(appDate, DATE_FORMAT)).value);
			}
		}
	}
	/**
	 * 07_勤務種類取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param result
	 */
	private void getWorkType(String companyID,String employeeID,Optional<PersonalLaborCondition> personalLablorCodition ,OverTimeDto result){
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
				 * personalLablorCodition
				 */
				if(personalLablorCodition.isPresent()){
					//ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードを選択する(chọn cai loai di lam)
					Optional<WorkType> workType = workTypeRepository.findByPK(companyID, personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().toString());
					result.setWorkTypeCode(personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().toString());
					result.setWorkTypeName(workType.get().getName().toString());
				}
				//先頭の勤務種類を選択する(chon cai dau tien trong list loai di lam)
			}
		}
	}
	/**
	 * 08_就業時間帯取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param result
	 */
	private void getWorkingHourType(String companyID,String employeeID,Optional<PersonalLaborCondition> personalLablorCodition,OverTimeDto result){
		// 1.職場別就業時間帯を取得
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID, GeneralDate.today());
		/*
		 * ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
		 * personalLaborConditionRepository
		 */
		if(personalLablorCodition.isPresent()){
			Optional<WorkTime> workTime =  workTimeRepository.findByCode(companyID,personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().toString());
			result.setSiftCode(personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().toString());
			result.setSiftName(workTime.get().getWorkTimeDisplayName().toString());
		}else{
			Optional<WorkTime> workTime =  workTimeRepository.findByCode(companyID,listWorkTimeCodes.get(0));
			result.setSiftCode(listWorkTimeCodes.get(0));
			result.setSiftName(workTime.get().getWorkTimeDisplayName().toString());
		}
	}
	/**
	 * 01-14_勤務時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param requestAppDetailSetting
	 * @param result
	 */
	private void getWorkingHours(String companyID,String employeeID,String appDate,Optional<RequestAppDetailSetting> requestAppDetailSetting,OverTimeDto result){
		if(appDate != null){
			int atWorkAtr = requestAppDetailSetting.get().getAtworkTimeBeginDisFlg().value;
			if(atWorkAtr == AtWorkAtr.DISPLAY.value){
				// team anh lương
			}
		}
	}
	/**
	 * 01-17_休憩時間取得
	 * @param companyID
	 * @param employeeID
	 * @param requestAppDetailSetting
	 * @param result
	 */
	private void getRestTime(String companyID,String employeeID, Optional<RequestAppDetailSetting> requestAppDetailSetting, OverTimeDto result){
		
		if(requestAppDetailSetting.get().getBreakTimeDisFlg().value == DisplayFlg.DISPLAY.value){
			result.setDisplayRestTime(true);
		}else{
			result.setDisplayRestTime(false);
		}
	}
	/**
	 * 01-03_残業枠を取得
	 * @param companyID
	 * @param overtimeAtr
	 * @param result
	 */
	private void getOvertimeHours(String companyID, int overtimeAtr, OverTimeDto result){
		//早出残業の場合
		if(overtimeAtr == OverTimeAtr.PREOVERTIME.value){
			
		}
		//通常残業の場合
		if(overtimeAtr == OverTimeAtr.REGULAROVERTIME.value){
			
		}
		//早出残業・通常残業の場合
		if(overtimeAtr == OverTimeAtr.ALL.value){
			
		}
	}
	/**
	 * 01-04_加給時間を取得
	 * @param companyID
	 * @param appDate
	 * @param result
	 */
	private void getBonusTime(String companyID,String employeeID, String appDate,Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet, OverTimeDto result){
		
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
				result.setDisplayBonusTime(true);
				// アルゴリズム「社員所属職場履歴を取得」を実行する
				SWkpHistImport sWkpHistImport = employeeAdapter.getSWkpHistByEmployeeID(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT));
				//アルゴリズム「職場の特定日設定を取得する」を実行する (hung lam)
				
			}else{
				result.setDisplayBonusTime(false);
			}
		}
	}
	/**
	 * 01-05_申請定型理由を取得
	 * @param companyID
	 * @param result
	 */
	private void getApplicationReasonType(String companyID, OverTimeDto result) {
		List<ApplicationReason> applicationReasons = applicationReasonRepository.getReasonByAppType(companyID,
				ApplicationType.OVER_TIME_APPLICATION.value);
		List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
		for (ApplicationReason applicationReason : applicationReasons) {
			ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
					applicationReason.getReasonTemp());
			applicationReasonDtos.add(applicationReasonDto);
		}
		result.setApplicationReasonDtos(applicationReasonDtos);
	}
	/**
	 * 01-08_乖離定型理由を取得
	 * @param companyID
	 * @param overtimeRestAppCommonSet
	 * @param result
	 */
	private void getDivergenceReasonForm(String companyID, OverTimeDto result){
				
				List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
				List<DivergenceReason> divergenceReasons = diReasonRepository.getDivergenceReason(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
				for(DivergenceReason divergenceReason : divergenceReasons){
					DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
					divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
					divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
					divergenceReasonDtos.add(divergenceReasonDto);
				}
				result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	/**
	 * 01-09_事前申請を取得
	 * @param employeeId
	 * @param overtimeRestAppCommonSet
	 * @param appDate
	 * @param result
	 */
	private void getPreApplication(String employeeId, Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,String appDate,ApplicationDto applicationDto, OverTimeDto result){
		if(overtimeRestAppCommonSet.isPresent()){
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value){
				Optional<Application> application = this.applicationRepository.getApp(employeeId,  GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.POSTERIOR.value, ApplicationType.OVER_TIME_APPLICATION.value);
				if(application.isPresent()){
					applicationDto.setApplicationDate(application.get().getApplicationDate().toString(DATE_FORMAT));
					Optional<AppOverTime> appOvertime = this.overtimeRepository.getAppOvertime(application.get().getCompanyID(), application.get().getApplicationID());
					if(appOvertime.isPresent()){
						
						result.setWorkTypeCode(appOvertime.get().getWorkTypeCode().toString());
						Optional<WorkType> workType = workTypeRepository.findByPK(appOvertime.get().getCompanyID(), appOvertime.get().getWorkTypeCode().toString());
						if(workType.isPresent()){
							result.setWorkTypeName(workType.get().getName().toString());
						}
						
						result.setSiftCode(appOvertime.get().getSiftCode().toString());
						Optional<WorkTime> workTime =  workTimeRepository.findByCode(appOvertime.get().getCompanyID(),appOvertime.get().getSiftCode().toString());
						if(workTime.isPresent()){
							result.setSiftName(workTime.get().getWorkTimeDisplayName().toString());
						}
						
						result.setWorkClockFrom1(appOvertime.get().getWorkClockFrom1());
						result.setWorkClockTo1(appOvertime.get().getWorkClockTo1());
						result.setWorkClockFrom2(appOvertime.get().getWorkClockFrom2());
						result.setWorkClockTo2(appOvertime.get().getWorkClockTo2());
						List<OverTimeInput> overtimeInputs = overtimeInputRepository.getOvertimeInput(appOvertime.get().getCompanyID(), appOvertime.get().getAppID());
						if(overtimeInputs != null && !overtimeInputs.isEmpty()){
							List<OvertimeInputDto> overtimeInputDtos = new ArrayList<>();
							List<Integer> frameNo = new ArrayList<>();
							for(OverTimeInput overTimeInput : overtimeInputs){
								OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
								overtimeInputDto.setAttendanceID(overTimeInput.getAttendanceID().value);
								overtimeInputDto.setAttendanceNo(overTimeInput.getAttendanceNo());
								overtimeInputDto.setStartTime(overTimeInput.getStartTime().v());
								overtimeInputDto.setEndTime(overTimeInput.getEndTime().v());
								overtimeInputDto.setApplicationTime(overTimeInput.getApplicationTime().v());
								overtimeInputDtos.add(overtimeInputDto);
								frameNo.add(overTimeInput.getAttendanceNo());
							}
							List<OvertimeFrame> overtimeFrames =this.overtimeFrameRepository.getOvertimeFrameByFrameNo(frameNo);
							for(OvertimeInputDto overtimeInputDto : overtimeInputDtos){
								for(OvertimeFrame overtimeFrame : overtimeFrames){
									if(overtimeInputDto.getAttendanceNo() == overtimeFrame.getOtFrameNo()){
										overtimeInputDto.setAttendanceName(overtimeFrame.getOvertimeFrameName().toString());
										continue;
									}
								}
							}
							result.setOverTimeInput(overtimeInputDtos);
							result.setOverTimeShiftNight(appOvertime.get().getOverTimeShiftNight());
							result.setFlexExessTime(appOvertime.get().getFlexExessTime());
						}
					}
				}
			}
		}
		
	}
}
