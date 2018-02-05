package nts.uk.ctx.at.request.app.find.application.holidaywork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto_New;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkPreAndReferDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HolidayWorkInputDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayPreProcess;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTimeHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTypeHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.TimeItemTypeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.RecordWorkOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AppHolidayWorkFinder {
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private HolidayPreProcess holidayPreProcess;
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	@Inject
	private HolidayService holidayService;
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;
	
	
	public AppHolidayWorkDto getAppHolidayWork(String appDateInput,int uiType){
		
		AppHolidayWorkDto result = new AppHolidayWorkDto();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		int rootAtr = 1;
		
		//1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				rootAtr, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class),appDateInput == null ? null : GeneralDate.fromString(appDateInput, DATE_FORMAT));
		result.setManualSendMailAtr(appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value  ==1 ?true : false);
		//アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(companyID, employeeID, EmploymentRootAtr.APPLICATION, EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class), appCommonSettingOutput.generalDate, "", true);
		//アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する 
		 startupErrorCheckService.startupErrorCheck(appCommonSettingOutput.generalDate, ApplicationType.BREAK_TIME_APPLICATION.value, approvalRootPattern.getApprovalRootContentImport());
		 // 申請対象日のパラメータがあるかチェックする
		 

		 if(appDateInput != null){
			 //13.実績の取得
			 AchievementOutput achievementOutput = collectAchievement.getAchievement(companyID, employeeID,  GeneralDate.fromString(appDateInput, DATE_FORMAT));
		 }
		 // アルゴリズム「初期データの取得」を実行する
		 getData(companyID,employeeID,appDateInput,appCommonSettingOutput,result,uiType);
		return result;
	}
	
	/**
	 * 01_初期データ取得
	 * @param companyID
	 * @param employeeID
	 * @param appDateHolidayWork
	 * @param appCommonSettingOutput
	 * @param result
	 */
	private void getData(String companyID,String employeeID,String appDate,AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result,int uiType){
		ApplicationDto_New applicationDto = new ApplicationDto_New();
		List<HolidayWorkInputDto> holidayWorkInputDtos = new ArrayList<>();
		//01-12_申請日付取得
		applicationDto.setApplicationDate(appDate);
		// 01-01_休出通知情報を取得
		HolidayWorkInstruction holidayWorkInstruction = holidayPreProcess.getHolidayInstructionInformation(appCommonSettingOutput, appDate, employeeID);
		result.setDisplayHolidayInstructInforFlg(holidayWorkInstruction.isDisplayHolidayWorkInstructInforFlg());
		result.setHolidayInstructInformation(holidayWorkInstruction.getHolidayWorkInstructInfomation());
		//01-02_時間外労働を取得 : waitting
		// 01-13_事前事後区分を取得
		DisplayPrePost displayPrePost =	iOvertimePreProcess.getDisplayPrePost(companyID, uiType,appDate,ApplicationType.BREAK_TIME_APPLICATION.value);
		result.setDisplayPrePostFlg(displayPrePost.getDisplayPrePostFlg());
		applicationDto.setPrePostAtr(displayPrePost.getPrePostAtr());
		result.setApplication(applicationDto);
		//4.勤務種類を取得する, 5.就業時間帯を取得する,01-14_勤務時間取得, 01-17_休憩時間取得
		getWorkTypeAndWorkTime(companyID,employeeID,appDate,appCommonSettingOutput,result);
		// 01-03_休出時間を取得
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
			HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
			holidayWorkInputDto.setAttendanceType(AttendanceType.BREAKTIME.value);
			holidayWorkInputDto.setFrameNo(breaktimeFrame.getWorkdayoffFrNo().v().intValueExact());
			holidayWorkInputDto.setFrameName(breaktimeFrame.getWorkdayoffFrName().toString());
			holidayWorkInputDtos.add(holidayWorkInputDto);
		}
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		// xu li hien thi du lieu xin truoc
		if(overtimeRestAppCommonSet.isPresent()){
			//時間外表示区分
			result.setExtratimeDisplayFlag(overtimeRestAppCommonSet.get().getExtratimeDisplayAtr().value == 1 ? true : false);
		}
		// 01-04_加給時間を取得
		if(overtimeRestAppCommonSet.isPresent()){
			result.setDisplayBonusTime(false);
			if(overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
				result.setDisplayBonusTime(true);
				List<BonusPayTimeItem> bonusPayTimeItems= this.iOvertimePreProcess.getBonusTime(employeeID,overtimeRestAppCommonSet,appDate,companyID, result.getWorkTime());
				List<BonusPayTimeItem> bonusPayTimeItemNotSpecial = bonusPayTimeItems.stream().filter(x -> x.getTimeItemTypeAtr().value == TimeItemTypeAtr.NORMAL_TYPE.value).collect(Collectors.toList());
				for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItemNotSpecial){
					holidayWorkInputDtos.add(convertBonusPayTimeItem(bonusPayTimeItem,AttendanceType.BONUSPAYTIME.value));
				}
				List<BonusPayTimeItem> bonusPayTimeItemSpecial = bonusPayTimeItems.stream().filter(x -> x.getTimeItemTypeAtr().value == TimeItemTypeAtr.SPECIAL_TYPE.value).collect(Collectors.toList());
				for(BonusPayTimeItem bonusPayTimeItem : bonusPayTimeItemSpecial){
					holidayWorkInputDtos.add(convertBonusPayTimeItem(bonusPayTimeItem,AttendanceType.BONUSSPECIALDAYTIME.value));
				}
			}
		}
		result.setHolidayWorkInputDtos(holidayWorkInputDtos);
		// 01-05_申請定型理由を取得,01-06_申請理由を取得
		getAppReason(appCommonSettingOutput, result,companyID);
		// 01-08_乖離定型理由を取得, 01-07_乖離理由を取得
		getDivigenceReason(overtimeRestAppCommonSet,result,companyID);
		
		// xu li hien thi du lieu xin truoc
		if(overtimeRestAppCommonSet.isPresent()){
			// hien thi du lieu thuc te
			if(result.getApplication().getPrePostAtr() == InitValueAtr.POST.value && overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
				result.setReferencePanelFlg(true);
			}
			// hien thi don xin truoc
			if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.NOTUSE.value && result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value){
				result.setAllPreAppPanelFlg(false);
			}else{
				result.setAllPreAppPanelFlg(true);
			}
		}
		// 01-09_事前申請を取得
		if(result.isAllPreAppPanelFlg()){
			//01-09_事前申請を取得
			if(result.getApplication().getPrePostAtr()  == PrePostAtr.POSTERIOR.value ){
				result.setPreAppPanelFlg(false);
				AppHolidayWork appHolidayWork = holidayPreProcess.getPreApplicationHoliday(companyID, employeeID,overtimeRestAppCommonSet, appDate,result.getApplication().getPrePostAtr());
				if(appHolidayWork != null){
					result.setPreAppPanelFlg(true);
					convertAppHolidayWorkDto(companyID,result,appHolidayWork);
				}			
			}
		}
	}
	/**
	 * 4.勤務種類を取得する, 5.就業時間帯を取得する,01-14_勤務時間取得, 01-17_休憩時間取得
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param appCommonSettingOutput
	 * @param result
	 */
	private void getWorkTypeAndWorkTime(String companyID,String employeeID,String appDate,AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result){
		ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
		result.setDisplayCaculationTime(false);
		if(approvalFunctionSetting != null){
			// 時刻計算利用チェック
			if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeCalUse().equals(UseAtr.USE)) {
				result.setDisplayCaculationTime(true);
				// 4.勤務種類を取得する : TODO
				GeneralDate baseDate = appCommonSettingOutput.generalDate;
				//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
				Optional<PersonalLaborCondition> personalLablorCodition = personalLaborConditionRepository.findById(employeeID,baseDate);
				List<AppEmploymentSetting> appEmploymentWorkType = appCommonSettingOutput.appEmploymentWorkType;
				WorkTypeHolidayWork WorkTypes =  holidayService.getWorkTypes(companyID, employeeID, appEmploymentWorkType, baseDate, personalLablorCodition);
				result.setWorkTypes(WorkTypes.getWorkTypeCodes());
				result.getWorkType().setWorkTypeCode(WorkTypes.getWorkTypeCode());
				result.getWorkType().setWorkTypeName(WorkTypes.getWorkTypeName());
				// 5.就業時間帯を取得する
				WorkTimeHolidayWork workTimes = this.holidayService.getWorkTimeHolidayWork(companyID, employeeID, baseDate, personalLablorCodition);
				result.setWorkTimes(workTimes.getWorkTimeCodes());
				result.getWorkTime().setSiftCode(workTimes.getWorkTimeCode());
				result.getWorkTime().setSiftName(workTimes.getWorkTimeName());
				// 01-14_勤務時間取得(lay thoi gian): chua xong  Imported(申請承認)「勤務実績」を取得する(lay domain 「勤務実績」): to do
				RecordWorkOutput recordWorkOutput = iOvertimePreProcess.getWorkingHours(companyID, employeeID,appDate,approvalFunctionSetting,result.getWorkTime().getSiftCode());
				result.setWorkClockStart1(recordWorkOutput.getStartTime1());
				result.setWorkClockStart2(recordWorkOutput.getStartTime2());
				result.setWorkClockEnd1(recordWorkOutput.getEndTime1());
				result.setWorkClockEnd2(recordWorkOutput.getEndTime2());
				// 01-17_休憩時間取得(lay thoi gian nghi ngoi)
				boolean displayRestTime = iOvertimePreProcess.getRestTime(approvalFunctionSetting);
				result.setDisplayRestTime(displayRestTime);
			}
		}
	}
	/**
	 * convert BonusPayTimeItem -> HolidayWorkInputDto
	 * @param bonusPayTimeItem
	 * @param attendanceType
	 * @return
	 */
	private HolidayWorkInputDto convertBonusPayTimeItem(BonusPayTimeItem bonusPayTimeItem,int attendanceType){
		HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
		holidayWorkInputDto.setAttendanceType(attendanceType);
		holidayWorkInputDto.setFrameNo(bonusPayTimeItem.getId());
		holidayWorkInputDto.setFrameName(bonusPayTimeItem.getTimeItemName().toString());
		return holidayWorkInputDto;
	}
	/**
	 * 01-05_申請定型理由を取得,01-06_申請理由を取得
	 * @param appCommonSettingOutput
	 * @param result
	 * @param companyID
	 */
	private void getAppReason(AppCommonSettingOutput appCommonSettingOutput,AppHolidayWorkDto result,String companyID){
		List<AppTypeDiscreteSetting> appTypeDiscreteSettings = appCommonSettingOutput.appTypeDiscreteSettings;
		if(!CollectionUtil.isEmpty(appTypeDiscreteSettings)){
			Optional<AppTypeDiscreteSetting> appTypeDiscreteSetting = Optional.of(appTypeDiscreteSettings.get(0));
			// 01-05_申請定型理由を取得
			result.setTypicalReasonDisplayFlg(false);
			if(appTypeDiscreteSetting.get().getTypicalReasonDisplayFlg().value == AppDisplayAtr.DISPLAY.value){
				result.setTypicalReasonDisplayFlg(true);
				List<ApplicationReason> applicationReasons = iOvertimePreProcess.getApplicationReasonType(companyID,ApplicationType.BREAK_TIME_APPLICATION.value,appTypeDiscreteSetting);
				List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
				for (ApplicationReason applicationReason : applicationReasons) {
					ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
							applicationReason.getReasonTemp(), applicationReason.getDefaultFlg().value);
					applicationReasonDtos.add(applicationReasonDto);
				}
				result.setApplicationReasonDtos(applicationReasonDtos);
			}
			//01-06_申請理由を取得
			result.setDisplayAppReasonContentFlg(iOvertimePreProcess.displayAppReasonContentFlg(appTypeDiscreteSetting));
		}
	}
	/**
	 * 01-08_乖離定型理由を取得, 01-07_乖離理由を取得
	 * @param overtimeRestAppCommonSet
	 * @param result
	 * @param companyID
	 */
	private void getDivigenceReason(Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet,AppHolidayWorkDto result,String companyID){
		
		if(overtimeRestAppCommonSet.isPresent()){
			//01-08_乖離定型理由を取得
			result.setDisplayDivergenceReasonForm(false);
			if(result.getApplication().getPrePostAtr() != PrePostAtr.PREDICT.value && overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
				result.setDisplayDivergenceReasonForm(true);
				List<DivergenceReason> divergenceReasons = iOvertimePreProcess.getDivergenceReasonForm(companyID,ApplicationType.BREAK_TIME_APPLICATION.value,overtimeRestAppCommonSet);
				convertToDivergenceReasonDto(divergenceReasons,result);
			}
			//01-07_乖離理由を取得
			result.setDisplayDivergenceReasonInput(result.getApplication().getPrePostAtr() != PrePostAtr.PREDICT.value && iOvertimePreProcess.displayDivergenceReasonInput(overtimeRestAppCommonSet));
		}
	}
	
	/**
	 * @param divergenceReasons
	 * @param result
	 */
	private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, AppHolidayWorkDto result){
		List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
		for(DivergenceReason divergenceReason : divergenceReasons){
			DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
			divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
			divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
			divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	private void convertAppHolidayWorkDto(String companyID, AppHolidayWorkDto result,AppHolidayWork appHolidayWork){
		AppHolidayWorkPreAndReferDto  appHolidayWorkPreAndReferDto= new AppHolidayWorkPreAndReferDto();
		if(appHolidayWork.getApplication() != null){
			if(appHolidayWork.getApplication().getAppDate() != null){
				appHolidayWorkPreAndReferDto.setAppDate(appHolidayWork.getApplication().getAppDate().toString(DATE_FORMAT));
			}
		}
		
		if (appHolidayWork.getWorkTypeCode() != null) {
			WorkTypeOvertime workTypeOvertime = new WorkTypeOvertime();
			workTypeOvertime.setWorkTypeCode(appHolidayWork.getWorkTypeCode().toString());
			Optional<WorkType> workType = workTypeRepository.findByPK(companyID,
					appHolidayWork.getWorkTypeCode().toString());
			if (workType.isPresent()) {
				workTypeOvertime.setWorkTypeName(workType.get().getName().toString());
			}
			appHolidayWorkPreAndReferDto.setWorkType(workTypeOvertime);
		}
		if (appHolidayWork.getWorkTimeCode() != null) {
			SiftType siftType = new SiftType();

			siftType.setSiftCode(appHolidayWork.getWorkTimeCode().toString());
			Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyID,
					appHolidayWork.getWorkTimeCode().toString());
			if (workTime.isPresent()) {
				siftType.setSiftName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().toString());
			}
			appHolidayWorkPreAndReferDto.setWorkTime(siftType);
		}
		appHolidayWorkPreAndReferDto.setWorkClockStart1(appHolidayWork.getWorkClock1().getStartTime().v());
		appHolidayWorkPreAndReferDto.setWorkClockEnd1(appHolidayWork.getWorkClock1().getEndTime().v());
		appHolidayWorkPreAndReferDto.setWorkClockStart2(appHolidayWork.getWorkClock2().getStartTime() == null ? null : appHolidayWork.getWorkClock2().getStartTime().v());
		appHolidayWorkPreAndReferDto.setWorkClockEnd2(appHolidayWork.getWorkClock2().getEndTime() == null ? null : appHolidayWork.getWorkClock2().getEndTime().v());
		
		List<HolidayWorkInputDto> holidayWorkInputDtos = new ArrayList<>();
		List<HolidayWorkInput> holidayWorkInputs = appHolidayWork.getHolidayWorkInputs();
		if (holidayWorkInputs != null && !holidayWorkInputs.isEmpty()) {
			List<Integer> frameNo = new ArrayList<>();
			for (HolidayWorkInput holidayWorkInput : holidayWorkInputs) {
				HolidayWorkInputDto holidayWorkInputDto = new HolidayWorkInputDto();
				holidayWorkInputDto.setAttendanceType(holidayWorkInput.getAttendanceType().value);
				holidayWorkInputDto.setFrameNo(holidayWorkInput.getFrameNo());
				holidayWorkInputDto.setStartTime(holidayWorkInput.getStartTime().v());
				holidayWorkInputDto.setEndTime(holidayWorkInput.getEndTime().v());
				holidayWorkInputDto.setApplicationTime(holidayWorkInput.getApplicationTime().v());
				holidayWorkInputDtos.add(holidayWorkInputDto);
				frameNo.add(holidayWorkInput.getFrameNo());
			}
			List<WorkdayoffFrame> workDayoffFrames = this.workdayoffFrameRepository.getWorkdayoffFrameBy(new CompanyId(companyID),frameNo);
			for (HolidayWorkInputDto holidayWorkInputDto : holidayWorkInputDtos) {
				for (WorkdayoffFrame workdayoffFrame : workDayoffFrames) {
					if (holidayWorkInputDto.getFrameNo() == workdayoffFrame.getWorkdayoffFrNo().v().intValueExact()) {
						holidayWorkInputDto.setFrameName(workdayoffFrame.getWorkdayoffFrName().toString());
						continue;
					}
				}
			}
			appHolidayWorkPreAndReferDto.setHolidayWorkInputs(holidayWorkInputDtos);
			
		}
		result.setPreAppHolidayWorkDto(appHolidayWorkPreAndReferDto);
	}
}
