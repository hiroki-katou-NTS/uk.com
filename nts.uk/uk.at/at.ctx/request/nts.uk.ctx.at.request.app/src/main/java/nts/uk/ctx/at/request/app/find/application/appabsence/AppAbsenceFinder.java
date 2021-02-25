package nts.uk.ctx.at.request.app.find.application.appabsence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommand;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceCheckRegisterDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceStartScreenBOutput;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AccumulatedRestManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AnualLeaveManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDetailDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeRelationShipDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeWorkTypeParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.DisplayAllScreenParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.HolidayAppTypeName;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.NursingCareLeaveManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.Overtime60HManagementDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamGetAllAppAbsence;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SpecAbsenceParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SubstituteLeaveManagementDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDetailScreenInfoDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.CheckDispHolidayType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AbsenceCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppForLeaveStartOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.SpecialHolidayEventAlgorithm;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author loivt
 *
 */
@Stateless
public class AppAbsenceFinder {

	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private PredetemineTimeSettingRepository predTimeRepository;
	@Inject
	private AppAbsenceRepository repoAppAbsence;
	@Inject
	private HolidayShipmentScreenAFinder hdShipmentScreenAFinder;
	@Inject
	private InitMode initMode;
	@Inject
	private AbsenceServiceProcess absenseProcess;
	@Inject
	private SpecialHolidayEventAlgorithm specHdEventAlg;
	@Inject
	private DisplayReasonRepository displayRep;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;
	
	@Inject
	private IFactoryApplication iFactoryApplication;
	

	/**
	 * 1.休暇申請（新規）起動前処理
	 * @param appDate
	 * @param employeeID
	 * @param employeeIDs
	 * @return
	 */
	public AppAbsenceStartInfoDto getAppForLeave(AppDispInfoStartupDto appDispInfo) {
		String companyID = AppContexts.user().companyId();
		
		// 1.休暇申請（新規）起動処理
		AppAbsenceStartInfoDto appDispInfoStartup = AppAbsenceStartInfoDto.fromDomain(absenseProcess.getVacationActivation(companyID, appDispInfo.toDomain()));
		
		return appDispInfoStartup;
	}

	/**
	 * INIT KAF006B
	 * 5.休暇申請（詳細）起動前処理
	 * @param appID
	 * @return
	 */
	public AppAbsenceDetailDto getByAppID(String appID) {
		// refactor 4 error
		/*String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = new AppAbsenceStartInfoOutput();
		// 詳細画面起動前申請共通設定を取得する
		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = detailAppCommonSetService.getCommonSetBeforeDetail(companyID, appID);
		appAbsenceStartInfoOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		// ドメインモデル「休暇申請」を取得する
		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, appID);
		if (!opAppAbsence.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		AppAbsence appAbsence = opAppAbsence.get();
		appAbsenceStartInfoOutput.setSelectedWorkTypeCD(Optional.of(appAbsence.getWorkTypeCode().v()));
		appAbsenceStartInfoOutput.setSelectedWorkTimeCD(appAbsence.getWorkTimeCode() == null ? Optional.empty() : Optional.of(appAbsence.getWorkTimeCode().v()));
		
		// 休暇申請設定を取得する
		HolidayRequestSetOutput holidayRequestSetOutput = absenseProcess.getHolidayRequestSet(companyID);
		appAbsenceStartInfoOutput.setHdAppSet(holidayRequestSetOutput.getHdAppSet());
		appAbsenceStartInfoOutput.setDisplayReasonLst(holidayRequestSetOutput.getDisplayReasonLst());
		// 勤務種類・就業時間帯情報を取得する
		appAbsenceStartInfoOutput = absenseProcess.getWorkTypeWorkTimeInfo(
				companyID,
				appAbsence, 
				appAbsenceStartInfoOutput);
		// 休暇残数情報を取得する
		RemainVacationInfo remainVacationInfo = absenseProcess.getRemainVacationInfo(
				companyID, 
				appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication().getEmployeeID(), 
				GeneralDate.today());
		appAbsenceStartInfoOutput.setRemainVacationInfo(remainVacationInfo);
		// 取得した情報もとに「休暇残数情報」にセットして返す
		AppAbsenceStartInfoDto appAbsenceStartInfoDto = AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();
		holidayAppTypes = this.getHolidayAppTypeName(
				Optional.of(holidayRequestSetOutput.getHdAppSet()),
				holidayAppTypes,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet());
		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
		appAbsenceStartInfoDto.holidayAppTypeName = holidayAppTypes;
		// 「休暇申請起動時の表示情報」と「休暇申請」を返す
		AppAbsenceDetailDto result = new AppAbsenceDetailDto();
		result.appAbsenceStartInfoDto = appAbsenceStartInfoDto;
		result.appAbsenceDto = AppAbsenceDto.fromDomain(appAbsence);
		return result;*/
		return null;
	}

	/**
	 * 一画面を全て表示する
	 * 
	 * @param startAppDate
	 * @param endAppDate
	 * @param workType
	 * @param employeeID
	 * @param holidayType
	 * @param alldayHalfDay
	 * @return
	 */
	public AppAbsenceStartInfoDto getAllDisplay(DisplayAllScreenParam param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenseProcess.holidayTypeChangeProcess(
				companyID, 
				param.getStartInfo().toDomain(companyID), 
				param.getAppDates(),
				EnumAdaptor.valueOf(param.getHolidayAppType(), HolidayAppType.class));
		
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * 申請日を変更する getChangeAppDate
	 * 申請日変更時処理
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param alldayHalfDay
	 * @param prePostAtr
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeAppDate(String companyID, AppAbsenceStartInfoDto appAbsenceStartInfoOutput, List<String> appDates, int holidayType, AppDispInfoWithDateDto appWithDate) {
		// INPUT．「休暇申請起動時の表示情報」を更新する
	    appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appWithDate);
		// 承認ルートの基準日を確認する
		int requestSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getApplicationSetting().getRecordDate();
		if(requestSetting == RecordDate.APP_DATE.value) {
		    // 休暇種類変更時処理
		    AppAbsenceStartInfoOutput appAbsence = this.absenseProcess.holidayTypeChangeProcess(companyID, appAbsenceStartInfoOutput.toDomain(companyID), appDates, EnumAdaptor.valueOf(holidayType, HolidayAppType.class));
		    
			// INPUT．「休暇申請起動時の表示情報」を更新する
			appAbsenceStartInfoOutput = AppAbsenceStartInfoDto.fromDomain(appAbsence);
		}
		// 各休暇の管理区分を取得する
		CheckDispHolidayType checkDispHolidayType = absenseProcess.checkDisplayAppHdType(
				companyID, 
				appAbsenceStartInfoOutput.toDomain(companyID).getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().stream().findFirst().get().getSid(), 
				appAbsenceStartInfoOutput.toDomain(companyID).getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate());
		// 「休暇申請起動時の表示情報」を更新する
		appAbsenceStartInfoOutput.getRemainVacationInfo().setAnnualLeaveManagement(AnualLeaveManagementDto.fromDomain(checkDispHolidayType.getAnnAnualLeaveManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setAccumulatedRestManagement(AccumulatedRestManagementDto.fromDomain(checkDispHolidayType.getAccumulatedRestManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setSubstituteLeaveManagement(SubstituteLeaveManagementDto.fromDomain(checkDispHolidayType.getSubstituteLeaveManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setOvertime60hManagement(Overtime60HManagementDto.fromDomain(checkDispHolidayType.getOvertime60hManagement()));
		appAbsenceStartInfoOutput.getRemainVacationInfo().setNursingCareLeaveManagement(NursingCareLeaveManagementDto.fromDomain(checkDispHolidayType.getNursingCareLeaveManagement()));
		
//		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();
//		holidayAppTypes = this.getHolidayAppTypeName(
//				Optional.of(appAbsenceStartInfoOutput.getHdAppSet()),
//				holidayAppTypes,
//				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet());
//		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
//		result.holidayAppTypeName = holidayAppTypes;
		// 「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}

	/**
	 * 終日休暇半日休暇を切替する（新規）
	 * 
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param alldayHalfDay
	 * @param prePostAtr
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeByAllDayOrHalfDay(AppAbsenceStartInfoDto appAbsenceStartInfoDto, 
			boolean displayHalfDayValue, Integer alldayHalfDay, Integer holidayType) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain(companyID);
		// INPUT．「休暇種類」を確認する
		if(holidayType!=null) {
			// 休暇種類変更時処理
//			appAbsenceStartInfoOutput = absenseProcess.holidayTypeChangeProcess(
//					companyID, 
//					appAbsenceStartInfoOutput, 
//					displayHalfDayValue, 
//					alldayHalfDay, 
//					EnumAdaptor.valueOf(holidayType, HolidayAppType.class));
		}
		// 返ってきた「休暇申請起動時の表示情報」を返す
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * 終日休暇半日休暇を切替する（詳細）
	 * 
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param alldayHalfDay
	 * @param prePostAtr
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeByAllDayOrHalfDayForUIDetail(ParamGetAllAppAbsence param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain(companyID);
//		appAbsenceStartInfoOutput = absenseProcess.allHalfDayChangeProcess(
//				companyID, 
//				appAbsenceStartInfoOutput, 
//				param.isDisplayHalfDayValue(), 
//				param.getAlldayHalfDay(), 
//				Optional.of(EnumAdaptor.valueOf(param.getHolidayType(), HolidayAppType.class)));
//		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
		return null;
	}

	/**
	 * 勤務種類組み合わせ全表示を切替する
	 * 
	 * @param startAppDate
	 * @param displayHalfDayValue
	 * @param employeeID
	 * @param holidayType
	 * @param alldayHalfDay
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeDisplayHalfDay(String startAppDate, boolean displayHalfDayValue, String employeeID,
			String workTypeCode, Integer holidayType, int alldayHalfDay, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain(companyID);
		appAbsenceStartInfoOutput = absenseProcess.allHalfDayChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				displayHalfDayValue, 
				alldayHalfDay, 
				holidayType == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(holidayType, HolidayAppType.class)));
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * 勤務種類を変更する
	 * 
	 * @param startAppDate
	 * @param employeeID
	 * @param workTypeCode
	 * @param holidayType
	 * @param workTimeCode
	 * @return
	 */
	public AppAbsenceStartInfoDto getChangeWorkType(ChangeWorkTypeParam param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getStartInfo().toDomain(companyID);
		appAbsenceStartInfoOutput = absenseProcess.workTypeChangeProcess(
				companyID, 
				param.getAppDates(),
				appAbsenceStartInfoOutput, 
				EnumAdaptor.valueOf(param.getHolidayAppType(), HolidayAppType.class), 
				Optional.ofNullable(param.getWorkTypeCd()));
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * getListWorkTimeCodes
	 * 
	 * @param startDate
	 * @param employeeID
	 * @return
	 */
	public List<String> getListWorkTimeCodes(String startDate, String employeeID) {
		String companyID = AppContexts.user().companyId();
		// 1.職場別就業時間帯を取得
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,
				startDate == null ? GeneralDate.today() : GeneralDate.fromString(startDate, DATE_FORMAT))
				.stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
		return listWorkTimeCodes;
	}

	/**
	 * getWorkingHours
	 * 
	 * @param workTimeCode
	 * @param workTypeCode
	 * @param holidayType
	 * @return
	 */
	public AppAbsenceStartInfoDto getWorkingHours(String date, String workTimeCode, String workTypeCode, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain(companyID);
		// 就業時間帯変更時処理
		appAbsenceStartInfoOutput = absenseProcess.workTimesChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				workTypeCode, 
				Optional.of(workTimeCode));
		
		Optional<GeneralDate> dateOpt = StringUtils.isEmpty(date) ?
		        Optional.empty() : Optional.of(GeneralDate.fromString(date, DATE_FORMAT));
		// 指定する勤務種類に必要な休暇時間を算出する
		AttendanceTime requireTime = absenseProcess.calculateTimeRequired(
		        employeeID,
		        dateOpt,
		        Optional.ofNullable(workTypeCode),
		        Optional.ofNullable(workTimeCode),
		        Optional.empty(), 
		        Optional.empty(), 
		        Optional.empty());
		
		// 返ってきた「必要時間」を「休暇申請起動時の表示情報」にセットする
		appAbsenceStartInfoOutput.setRequiredVacationTimeOptional(Optional.ofNullable(requireTime));
		
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}

	/**
	 * 勤務時間初期値の取得
	 * 
	 * @param companyID
	 * @param workTypeCode
	 * @param workTimeCode
	 * @return
	 */
	public PrescribedTimezoneSetting initWorktimeCode(String companyID, String workTypeCode, String workTimeCode) {

		Optional<WorkType> WkTypeOpt = workTypeRepository.findByPK(companyID, workTypeCode);
		if (WkTypeOpt.isPresent()) {
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			WorkStyle workStyle = basicScheduleService.checkWorkDay(WkTypeOpt.get().getWorkTypeCode().toString());
			if (workStyle == null) {
				return null;
			}
			if (!workStyle.equals(WorkStyle.ONE_DAY_REST)) {
				// アルゴリズム「所定時間帯を取得する」を実行する
				// 所定時間帯を取得する
				if (workTimeCode != null && !workTimeCode.equals("")) {
					if (this.predTimeRepository.findByWorkTimeCode(companyID, workTimeCode).isPresent()) {
						PrescribedTimezoneSetting prescribedTzs = this.predTimeRepository
								.findByWorkTimeCode(companyID, workTimeCode).get().getPrescribedTimezoneSetting();
						return prescribedTzs;
					}
				}
			}
		}
		return null;
	}
	public WorkTypeObjAppHoliday geWorkTypeObjAppHoliday(AppEmploymentSetting x, int hdType) {
		return x.getListWTOAH().stream().filter(y -> y.getSwingOutAtr().isPresent() ? y.getSwingOutAtr().get().value == hdType : y.getHolidayAppType().isPresent() ? y.getHolidayAppType().get().value == hdType : false).findFirst().get();
	}
	private boolean checkHdType(AppEmploymentSetting appEmploymentSetting, int hdType){
		
		AppEmploymentSetting appSetting = appEmploymentSetting;
		if (appSetting == null || CollectionUtil.isEmpty(appSetting.getListWTOAH())) return true;
		
		Optional<WorkTypeObjAppHoliday> optionalWorkTypeObjAppHoliday =appSetting.getListWTOAH().stream().filter(x -> x.getHolidayAppType().isPresent() ? x.getHolidayAppType().get().value == hdType : false).findFirst();
		if(!optionalWorkTypeObjAppHoliday.isPresent()) return true;
		
		WorkTypeObjAppHoliday workTypeObjAppHoliday = optionalWorkTypeObjAppHoliday.get();
		if(!workTypeObjAppHoliday.getHolidayAppType().isPresent()) return false;
		if(workTypeObjAppHoliday.getHolidayAppType().get().value == hdType ){
			//ドメインモデル「休暇申請対象勤務種類」．休暇種類を利用しないがtrue -> ×
			return (workTypeObjAppHoliday.getHolidayTypeUseFlg().isPresent()) ? !workTypeObjAppHoliday.getHolidayTypeUseFlg().get() : true;
		}		
		//ドメインモデル「休暇申請対象勤務種類」．休暇種類を利用しないがfalse -> 〇
		//ドメインモデル「休暇申請対象勤務種類」が取得できない場合 -> 〇
		return true;
	}
	private List<HolidayAppTypeName> getHolidayAppTypeName(Optional<HolidayApplicationSetting> hdAppSet,
			List<HolidayAppTypeName> holidayAppTypes, AppEmploymentSetting appEmploymentSetting){
		List<Integer> holidayAppTypeCodes = new ArrayList<>();
		for(int hdType = 0; hdType <=7; hdType ++){
			if(hdType == 5 || hdType == 6){
				continue;
			}
			if(this.checkHdType(appEmploymentSetting, hdType)){
				holidayAppTypeCodes.add(hdType);
			}
		}
			//comment hoatt 2018.07.16 bug #97414
//			if (CollectionUtil.isEmpty(holidayAppTypeCodes)) {
//				throw new BusinessException("Msg_473");
//			}
		for (Integer holidayCode : holidayAppTypeCodes) {
			holidayAppTypes.add(new HolidayAppTypeName(
					holidayCode,
					hdAppSet.isPresent()
							? hdAppSet.get().getHolidayApplicationTypeDisplayName()
									.stream()
									.filter(i -> i.getHolidayApplicationType().value == holidayCode)
									.findFirst().map(i -> i.getDisplayName().v()).orElse("")
							: ""
			));
		}
		return holidayAppTypes;

	}
	
	public List<TimezoneUse> initWorkingHours(String companyID, String workTypeCode, String workTimeCode) {

		Optional<WorkType> WkTypeOpt = workTypeRepository.findByPK(companyID, workTypeCode);
		if (WkTypeOpt.isPresent()) {
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			WorkStyle workStyle = basicScheduleService.checkWorkDay(WkTypeOpt.get().getWorkTypeCode().toString());
			if (workStyle == null) {
				return Collections.emptyList();
			}
			if (!workStyle.equals(WorkStyle.ONE_DAY_REST)) {
				// アルゴリズム「所定時間帯を取得する」を実行する
				// 所定時間帯を取得する
				return hdShipmentScreenAFinder.getTimeZones(companyID, workTimeCode, EnumAdaptor.valueOf(workStyle.value, AttendanceHolidayAttr.class));
			}
		}
		return Collections.emptyList();
	}
	/**
	 * 続柄・喪主を選択する
	 * @author hoatt
	 * @return
	 */
	public ChangeRelationShipDto changeRelationShip(SpecAbsenceParam specAbsenceParam){
		//hoatt - 2018.08.08 - doi ung specHd
		String companyId = AppContexts.user().companyId();
		/*//続柄の選択値を変更する
		//指定する勤務種類が事象に応じた特別休暇かチェックする
		CheckWkTypeSpecHdEventOutput checkSpecHd = specHdEventAlg.checkWkTypeSpecHdForEvent(companyId, workTypeCD);*/
		MaxDaySpecHdOutput maxDay = null;
		//if(checkSpecHd.isSpecHdForEventFlag()){
			//指定する特休枠の上限日数を取得する
		maxDay = specHdEventAlg.getMaxDaySpecHd(companyId, specAbsenceParam.frameNo,
				specAbsenceParam.specHdEvent.toDomain(), Optional.ofNullable(specAbsenceParam.relationCD));
		//}
		return new ChangeRelationShipDto(maxDay);
	};
	
	public AbsenceCheckRegisterDto checkBeforeRegister(CreatAppAbsenceCommand param) {
	    // 会社ID
	    String companyID = AppContexts.user().companyId();
	    
	    ApplyForLeave applyForLeave = param.getApplyForLeave().toDomain();
	    Application application = Application.createFromNew(
                EnumAdaptor.valueOf(param.getApplication().getPrePostAtr(), PrePostAtr.class),
                param.getApplication().getEmployeeID(), EnumAdaptor.valueOf(param.getApplication().getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(param.getApplication().getAppDate(), "yyyy/MM/dd")),
                param.getApplication().getEmployeeID(),
                param.getApplication().getOpStampRequestMode() == null ? Optional.empty()
                        : Optional.of(EnumAdaptor.valueOf(param.getApplication().getOpStampRequestMode(),
                                StampRequestMode.class)),
                Optional.of(new ReasonForReversion(param.getApplication().getOpReversionReason())),
                param.getApplication().getOpAppStartDate() == null ? Optional.empty()
                        : Optional.of(new ApplicationDate(
                                GeneralDate.fromString(param.getApplication().getOpAppStartDate(), "yyyy/MM/dd"))),
                        param.getApplication().getOpAppEndDate() == null ? Optional.empty()
                        : Optional.of(new ApplicationDate(
                                GeneralDate.fromString(param.getApplication().getOpAppEndDate(), "yyyy/MM/dd"))),
                Optional.of(new AppReason(param.getApplication().getOpAppReason())),
                param.getApplication().getOpAppStandardReasonCD() == null ? 
                        Optional.empty() : Optional.of(new AppStandardReasonCode(param.getApplication().getOpAppStandardReasonCD())));
	    applyForLeave.setApplication(application);
	    
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain(companyID);
		
		AbsenceCheckRegisterOutput result = absenseProcess.checkBeforeRegister(
				companyID, 
				appAbsenceStartInfoOutput, 
				applyForLeave,
				param.isAgentAtr());
		return AbsenceCheckRegisterDto.fromDomain(result);
	}
	
	public AbsenceCheckRegisterDto checkBeforeUpdate(CreatAppAbsenceCommand param) {
	 // 会社ID
        String companyID = AppContexts.user().companyId();
        
        ApplyForLeave applyForLeave = param.getApplyForLeave().toDomain();
        param.getApplication().setInputDate(GeneralDateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
        Application application = param.getApplication().toDomain();
        applyForLeave.setApplication(application);
        AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain(companyID);
        
		return AbsenceCheckRegisterDto.fromDomain(absenseProcess.checkBeforeUpdate(companyID, appAbsenceStartInfoOutput, applyForLeave, param.isAgentAtr()));
	}

	public VacationCheckOutput checkVacationTyingManage(WorkTypeDto wtBefore, WorkTypeDto wtAfter,
            List<LeaveComDayOffManaDto> leaveComDayOffMana, List<PayoutSubofHDManagementDto> payoutSubofHDManagements) {
	    return this.absenseProcess.checkVacationTyingManage(
	            wtBefore != null ? wtBefore.toDomain() : null, 
	            wtAfter != null ? wtAfter.toDomain() : null, 
	            leaveComDayOffMana.stream().map(item -> item.toDomain()).collect(Collectors.toList()), 
	            payoutSubofHDManagements.stream().map(item -> item.toDomain()).collect(Collectors.toList()));
	}
	
	public AbsenceStartScreenBOutput getAppForLeaveStartB(String companyID, String appID, AppDispInfoStartupDto appDispInfoStartupOutput) {
	    AppForLeaveStartOutput appForLeaveStart = absenseProcess.getAppForLeaveStartB(companyID, appID, appDispInfoStartupOutput.toDomain());
	    
	    return new AbsenceStartScreenBOutput(
	            AppAbsenceStartInfoDto.fromDomain(appForLeaveStart.getAppAbsenceStartInfoOutput()), 
	            ApplyForLeaveDto.fromDomain(appForLeaveStart.getApplyForLeave()));
	}
	
	public AppAbsenceStartInfoDto getChangeHolidayDates(String companyID, List<String> holidayDates, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
	    AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenseProcess.getChangeHolidayDates(
	            companyID, 
	            holidayDates.stream().map(x -> GeneralDate.fromString(x, "yyyy-MM-dd")).collect(Collectors.toList()), 
	            appAbsenceStartInfoDto.toDomain(companyID));
	    return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
	}
	
	public AbsenceCheckRegisterDto checkBeforeRegisterHolidayDates(String companyID, ApplicationDto oldApplication, ApplicationDto newApplication, AppAbsenceStartInfoDto appAbsenceStartInfoDto, ApplyForLeaveDto originApplyForLeave, ApplyForLeaveDto newApplyForLeave) {
	    Application oldApp = oldApplication.toDomain();
	    Application newApp = newApplication.toDomain();
	    ApplyForLeave oldApplyForLeave = originApplyForLeave.toDomain();
	    oldApplyForLeave.setApplication(oldApp);
	    ApplyForLeave applyForLeave = newApplyForLeave.toDomain();
	    applyForLeave.setApplication(newApp);
	    
	    AbsenceCheckRegisterOutput absenceCheckRegisterOutput = absenseProcess.checkAppAbsenceRegister(true, companyID, 
	            appAbsenceStartInfoDto.toDomain(companyID), 
	            oldApplyForLeave, 
	            applyForLeave);
	    return AbsenceCheckRegisterDto.fromDomain(absenceCheckRegisterOutput);
	}
}
