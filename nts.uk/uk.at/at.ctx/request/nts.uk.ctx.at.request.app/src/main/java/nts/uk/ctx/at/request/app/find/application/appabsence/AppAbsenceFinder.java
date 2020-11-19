package nts.uk.ctx.at.request.app.find.application.appabsence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommand;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceCheckRegisterDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDetailDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeRelationShipDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.HolidayAppTypeName;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamGetAllAppAbsence;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SpecAbsenceParam;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
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
	public AppAbsenceStartInfoDto getAppForLeave(String appDate, String employeeID,List<String> employeeIDs) {
		AppAbsenceStartInfoDto result = new AppAbsenceStartInfoDto();
		// error EA refactor 4
		/*String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		if(appDate != null) {
			dateLst.add(GeneralDate.fromString(appDate, DATE_FORMAT));
		}
		// 申請共通の起動処理
		// error EA refactor 4
		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
				companyID, 
				ApplicationType_Old.ABSENCE_APPLICATION, 
				employeeIDs, 
				dateLst, 
				true);
		result.appDispInfoStartupOutput = AppDispInfoStartupDto_Old.fromDomain(appDispInfoStartupOutput);
		// 休暇申請設定を取得する
		HolidayRequestSetOutput holidayRequestSetOutput = absenseProcess.getHolidayRequestSet(companyID);
		result.hdAppSet = HdAppSetDto.convertToDto(holidayRequestSetOutput.getHdAppSet());
		result.displayReasonLst = holidayRequestSetOutput.getDisplayReasonLst().stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
		// 休暇残数情報を取得する
		RemainVacationInfo remainVacationInfo = absenseProcess.getRemainVacationInfo(
				companyID, 
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().stream().findFirst().get().getSid(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate());
		result.remainVacationInfo = remainVacationInfo;
		// 取得した情報もとに「休暇残数情報」にセットして返す
		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();
		holidayAppTypes = this.getHolidayAppTypeName(
				Optional.of(holidayRequestSetOutput.getHdAppSet()),
				holidayAppTypes,
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet());
		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
		result.holidayAppTypeName = holidayAppTypes;*/
		
		return result;
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
	public AppAbsenceStartInfoDto getAllDisplay(ParamGetAllAppAbsence param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = absenseProcess.holidayTypeChangeProcess(
				companyID, 
				param.getAppAbsenceStartInfoDto().toDomain(), 
				param.isDisplayHalfDayValue(), 
				param.getAlldayHalfDay(), 
				EnumAdaptor.valueOf(param.getHolidayType(), HolidayAppType.class));
		
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
	public AppAbsenceStartInfoDto getChangeAppDate(String startAppDate, boolean displayHalfDayValue, String employeeID,
			String workTypeCode, Integer holidayType, int alldayHalfDay, int prePostAtr, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
		// error EA refactor 4
		/*String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		GeneralDate targetDate = GeneralDate.fromString(startAppDate, "yyyy/MM/dd");
		dateLst.add(targetDate);
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain();
		AppDispInfoNoDateOutput_Old appDispInfoNoDateOutput = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput();
		AppDispInfoWithDateOutput_Old appDispInfoWithDateOutput = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput();
		// 共通インタラクション「申請日を変更する」を実行する
		appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(
				companyID, 
				dateLst, 
				ApplicationType_Old.ABSENCE_APPLICATION, 
				appDispInfoNoDateOutput,
				appDispInfoWithDateOutput);
		// INPUT．「休暇申請起動時の表示情報」を更新する
		appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().setAppDispInfoWithDateOutput(appDispInfoWithDateOutput);
		// 承認ルートの基準日を確認する
		RequestSetting requestSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getRequestSetting();
		if(requestSetting.getApplicationSetting().getRecordDate() == RecordDate.APP_DATE ) {
			// 終日半日休暇変更時処理
			// INPUT．「休暇申請起動時の表示情報」を更新する
			appAbsenceStartInfoOutput = absenseProcess.allHalfDayChangeProcess(
					companyID, 
					appAbsenceStartInfoOutput, 
					displayHalfDayValue, 
					alldayHalfDay, 
					holidayType == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(holidayType, nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType.class)));
		}
		// 各休暇の管理区分を取得する
		CheckDispHolidayType checkDispHolidayType = absenseProcess.checkDisplayAppHdType(
				companyID, 
				appDispInfoNoDateOutput.getEmployeeInfoLst().stream().findFirst().get().getSid(), 
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate());
		// 「休暇申請起動時の表示情報」を更新する
		appAbsenceStartInfoOutput.getRemainVacationInfo().setYearManage(checkDispHolidayType.isYearManage());
		appAbsenceStartInfoOutput.getRemainVacationInfo().setSubHdManage(checkDispHolidayType.isSubHdManage());
		appAbsenceStartInfoOutput.getRemainVacationInfo().setSubVacaManage(checkDispHolidayType.isSubVacaManage());
		appAbsenceStartInfoOutput.getRemainVacationInfo().setRetentionManage(checkDispHolidayType.isRetentionManage());
		
		AppAbsenceStartInfoDto result = AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
		
		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();
		holidayAppTypes = this.getHolidayAppTypeName(
				Optional.of(appAbsenceStartInfoOutput.getHdAppSet()),
				holidayAppTypes,
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet());
		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
		result.holidayAppTypeName = holidayAppTypes;
		// 「休暇申請起動時の表示情報」を返す
		return result;*/
		return null;
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
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain();
		// INPUT．「休暇種類」を確認する
		if(holidayType!=null) {
			// 休暇種類変更時処理
			appAbsenceStartInfoOutput = absenseProcess.holidayTypeChangeProcess(
					companyID, 
					appAbsenceStartInfoOutput, 
					displayHalfDayValue, 
					alldayHalfDay, 
					EnumAdaptor.valueOf(holidayType, HolidayAppType.class));
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
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain();
		appAbsenceStartInfoOutput = absenseProcess.allHalfDayChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				param.isDisplayHalfDayValue(), 
				param.getAlldayHalfDay(), 
				Optional.of(EnumAdaptor.valueOf(param.getHolidayType(), HolidayAppType.class)));
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput);
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
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain();
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
	public AppAbsenceStartInfoDto getChangeWorkType(ParamGetAllAppAbsence param) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain();
		appAbsenceStartInfoOutput = absenseProcess.workTypeChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				EnumAdaptor.valueOf(param.getHolidayType(), HolidayAppType.class), 
				Optional.ofNullable(param.getWorkTypeCode()));
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
	public List<TimeZoneUseDto> getWorkingHours(String workTimeCode, String workTypeCode, Integer holidayType, AppAbsenceStartInfoDto appAbsenceStartInfoDto) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain();
		appAbsenceStartInfoOutput = absenseProcess.workTimesChangeProcess(
				companyID, 
				appAbsenceStartInfoOutput, 
				workTypeCode, 
				Optional.of(workTimeCode), 
				EnumAdaptor.valueOf(holidayType, HolidayAppType.class));
		return AppAbsenceStartInfoDto.fromDomain(appAbsenceStartInfoOutput).workTimeLst;
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
				specAbsenceParam.specHdEvent.toDomain(), Optional.of(specAbsenceParam.relationCD));
		//}
		return new ChangeRelationShipDto(maxDay);
	};
	
	public AbsenceCheckRegisterDto checkBeforeRegister(CreatAppAbsenceCommand param) {
//		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain();
//		// 会社ID
//		String companyID = AppContexts.user().companyId();
//		// 申請ID
//		String appID = IdentifierUtil.randomUniqueId();
//		// Create Application
//		GeneralDate startDate = param.getApplicationCommand().getStartDate() == null ? null : GeneralDate.fromString(param.getApplicationCommand().getStartDate(), DATE_FORMAT);
//		GeneralDate endDate = param.getApplicationCommand().getEndDate() == null ? null : GeneralDate.fromString(param.getApplicationCommand().getEndDate(), DATE_FORMAT);
//		List<DisplayReasonDto> displayReasonDtoLst = 
//				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
//		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == param.getAppAbsenceCommand().getHolidayAppType())
//				.findAny().orElse(null);
//		String appReason = "";
//		if(displayReasonSet!=null){
//			boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
//			boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
//			String typicalReason = Strings.EMPTY;
//			String displayReason = Strings.EMPTY;
//			if(displayFixedReason){
//				if(Strings.isBlank(param.getApplicationCommand().getAppReasonID())){
//					typicalReason += "";
//				} else {
//					typicalReason += param.getApplicationCommand().getAppReasonID();
//				}
//			}
//			if(displayAppReason){
//				if(Strings.isNotBlank(typicalReason)){
//					displayReason += System.lineSeparator();
//				}
//				if(Strings.isBlank(param.getApplicationCommand().getApplicationReason())){
//					displayReason += "";
//				} else {
//					displayReason += param.getApplicationCommand().getApplicationReason();
//				}
//			}
//			ApplicationSetting applicationSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
//					.getRequestSetting().getApplicationSetting();
//			if(displayFixedReason||displayAppReason){
//				if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
//						&& Strings.isBlank(typicalReason+displayReason)) {
//					throw new BusinessException("Msg_115");
//				}
//			}
//			appReason = typicalReason + displayReason;
//		}
////		Application_New appRoot = iFactoryApplication.buildApplication(appID, startDate,
////				param.getApplicationCommand().getPrePostAtr(), appReason, appReason,
////				ApplicationType.ABSENCE_APPLICATION, startDate, endDate, param.getApplicationCommand().getApplicantSID());
//		Application appRoot = null;
//		AppForSpecLeave specHd = null;
//		AppForSpecLeaveCmd appForSpecLeaveCmd = param.getAppAbsenceCommand().getAppForSpecLeave();
//		if(param.getAppAbsenceCommand().getHolidayAppType() == HolidayAppType.SPECIAL_HOLIDAY.value && appForSpecLeaveCmd != null){
//			specHd = AppForSpecLeave.createFromJavaType(appID, appForSpecLeaveCmd.isMournerFlag(), appForSpecLeaveCmd.getRelationshipCD(), appForSpecLeaveCmd.getRelationshipReason());
//		}
//		AppAbsence appAbsence = new AppAbsence(companyID,
//				appID,
//				param.getAppAbsenceCommand().getHolidayAppType(),
//				param.getAppAbsenceCommand().getWorkTypeCode(),
//				param.getAppAbsenceCommand().getWorkTimeCode(),
//				param.getAppAbsenceCommand().isHalfDayFlg(), 
//				param.getAppAbsenceCommand().isChangeWorkHour(),
//				param.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), 
//				param.getAppAbsenceCommand().getStartTime1(),
//				param.getAppAbsenceCommand().getEndTime1(),
//				param.getAppAbsenceCommand().getStartTime2(),
//				param.getAppAbsenceCommand().getEndTime2(),
//				specHd);
//		appAbsence.setApplication(appRoot);
//		AbsenceCheckRegisterOutput result = absenseProcess.checkBeforeRegister(
//				companyID, 
//				appAbsenceStartInfoOutput, 
//				appRoot, 
//				appAbsence, 
//				param.getAlldayHalfDay(), 
//				param.isAgentAtr(), 
//				Optional.ofNullable(param.getMourningAtr()));
//		return AbsenceCheckRegisterDto.fromDomain(result);
		return null;
	}
	
	public AbsenceCheckRegisterDto checkBeforeUpdate(UpdateAppAbsenceCommand param) {
//		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain();
//		// 会社ID
//		String companyID = AppContexts.user().companyId();
//		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, param.getApplicationCommand().getApplicationID());
//		if(!opAppAbsence.isPresent()){
//			throw new BusinessException("Msg_198");
//		}
//		List<DisplayReasonDto> displayReasonDtoLst = 
//				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
//		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == param.getAppAbsenceCommand().getHolidayAppType())
//				.findAny().orElse(null);
//		OutputMode outputMode = initMode.getDetailScreenInitMode(
//				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getUser(), 
//				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getReflectPlanState().value);
//		// String appReason = opAppAbsence.get().getApplication().getAppReason().v();
//		if(outputMode==OutputMode.EDITMODE){
//			if(displayReasonSet!=null){
//				boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
//				boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
//				String typicalReason = Strings.EMPTY;
//				String displayReason = Strings.EMPTY;
//				if(displayFixedReason){
//					if(Strings.isBlank(param.getApplicationCommand().getAppReasonID())){
//						typicalReason += "";
//					} else {
//						typicalReason += param.getApplicationCommand().getAppReasonID();
//					}
//				}
//				if(displayAppReason){
//					if(Strings.isNotBlank(typicalReason)){
//						displayReason += System.lineSeparator();
//					}
//					if(Strings.isBlank(param.getApplicationCommand().getApplicationReason())){
//						displayReason += "";
//					} else {
//						displayReason += param.getApplicationCommand().getApplicationReason();
//					}
//				}else{
//					if (Strings.isBlank(typicalReason)) {
//						// displayReason = opAppAbsence.get().getApplication().getAppReason().v();
//					}
//				}
//				ApplicationSetting applicationSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
//						.getRequestSetting().getApplicationSetting();
//				if(displayFixedReason||displayAppReason){
//					if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
//							&& Strings.isBlank(typicalReason+displayReason)) {
//						throw new BusinessException("Msg_115");
//					}
//					// appReason = typicalReason + displayReason;
//				}
//			}
//		}
//		AppAbsence appAbsence = opAppAbsence.get();
//		appAbsence.setAllDayHalfDayLeaveAtr(EnumAdaptor.valueOf(param.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), AllDayHalfDayLeaveAtr.class));
//		appAbsence.setChangeWorkHour(param.getAppAbsenceCommand().isChangeWorkHour());
//		appAbsence.setStartTime1(param.getAppAbsenceCommand().getStartTime1() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getStartTime1()));
//		appAbsence.setEndTime1(param.getAppAbsenceCommand().getEndTime1() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getEndTime1()));
//		appAbsence.setStartTime2(param.getAppAbsenceCommand().getStartTime2() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getStartTime2()));
//		appAbsence.setEndTime2(param.getAppAbsenceCommand().getEndTime2() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getEndTime2()));
//		appAbsence.setWorkTypeCode(param.getAppAbsenceCommand().getWorkTypeCode() == null ? null : new WorkTypeCode(param.getAppAbsenceCommand().getWorkTypeCode()));
//		appAbsence.setWorkTimeCode(param.getAppAbsenceCommand().getWorkTimeCode() == null ? null : new WorkTimeCode(param.getAppAbsenceCommand().getWorkTimeCode()));
//		// appAbsence.getApplication().setAppReason(new AppReason(appReason));
//		appAbsence.setVersion(appAbsence.getVersion());
//		// appAbsence.getApplication().setVersion(param.getApplicationCommand().getVersion());
//		AbsenceCheckRegisterOutput result = absenseProcess.checkBeforeUpdate(
//				companyID, 
//				appAbsenceStartInfoOutput, 
//				appAbsence.getApplication(), 
//				appAbsence, 
//				param.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), 
//				param.isAgentAtr(), 
//				Optional.ofNullable(param.getMourningAtr()));
//		return AbsenceCheckRegisterDto.fromDomain(result);
		return null;
	}

}
