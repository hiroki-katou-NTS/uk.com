package nts.uk.ctx.at.request.app.find.application.appabsence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.appabsence.AppForSpecLeaveCmd;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommand;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceCheckRegisterDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDetailDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeRelationShipDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.HolidayAppTypeName;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamGetAllAppAbsence;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SpecAbsenceParam;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.AbsenceWorkType;
import nts.uk.ctx.at.request.dom.application.appabsence.AllDayHalfDayLeaveAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.CheckDispHolidayType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.HolidayRequestSetOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.RemainVacationInfo;
import nts.uk.ctx.at.request.dom.application.appabsence.service.four.AppAbsenceFourProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AbsenceCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.three.AppAbsenceThreeProcess;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailScreenInitModeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.OutputMode;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.RecordDate;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.SpecialHolidayEventAlgorithm;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	private BeforePrelaunchAppCommonSet bfPrelaunchAppCmSet;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private HdAppSetRepository hdAppSetRepository;
	@Inject
	private ApplicationReasonRepository repoAppReason;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private AppAbsenceThreeProcess appAbsenceThreeProcess;
	@Inject
	private AppAbsenceFourProcess appAbsenceFourProcess;
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
	private BeforePreBootMode beforePreBootMode;
	@Inject
	private InitMode initMode;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	@Inject
	private AbsenceServiceProcess absenseProcess;
	@Inject
	private SpecialHolidayEventAlgorithm specHdEventAlg;
	@Inject
	private AppForSpecLeaveRepository repoAppLeaveSpec;
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
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		if(appDate != null) {
			dateLst.add(GeneralDate.fromString(appDate, DATE_FORMAT));
		}
		// 申請共通の起動処理
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(
				companyID, 
				ApplicationType.ABSENCE_APPLICATION, 
				employeeIDs, 
				dateLst, 
				true);
		result.appDispInfoStartupOutput = AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput);
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
		result.holidayAppTypeName = holidayAppTypes;
		
		return result;
	}

	/**
	 * INIT KAF006B
	 * 5.休暇申請（詳細）起動前処理
	 * @param appID
	 * @return
	 */
	public AppAbsenceDetailDto getByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = new AppAbsenceStartInfoOutput();
		// 詳細画面起動前申請共通設定を取得する
		AppDispInfoStartupOutput appDispInfoStartupOutput = detailAppCommonSetService.getCommonSetBeforeDetail(companyID, appID);
		appAbsenceStartInfoOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		// ドメインモデル「休暇申請」を取得する
		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, appID);
		if (!opAppAbsence.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		AppAbsence appAbsence = opAppAbsence.get();
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
		return result;
		
		/*AppAbsenceDto result = new AppAbsenceDto();
		String companyID = AppContexts.user().companyId();
		String employeeIDLogin = AppContexts.user().employeeId();
		//アルゴリズム「詳細画面起動前申請共通設定を取得する」を実行する - 「Lấy app common setting trước khi khởi động màn hình detail」
		//14-1.詳細画面起動前申請共通設定を取得する
		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, appID);
		if (!opAppAbsence.isPresent()) {
			throw new BusinessException("Msg_198");
		}
		AppAbsence appAbsence = opAppAbsence.get();
		result = AppAbsenceDto.fromDomain(appAbsence);
		result.setDisplayReasonDtoLst(displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList()));
		//アルゴリズム「詳細画面起動前モードの判断」を実行する-「Kiểm tra mode trước khi khởi động màn hình detail」
		//アルゴリズム「14-2.詳細画面起動前申請共通設定を取得する」を実行する
		DetailedScreenPreBootModeOutput preBootOuput = beforePreBootMode.judgmentDetailScreenMode(companyID,
				employeeIDLogin, appID, appAbsence.getApplication().getAppDate());
		//アルゴリズム「詳細画面の初期モード」を実行する-[Mode Khởi tạo ban đầu của màn hình Detail]
		//14-3.詳細画面の初期モード
		DetailScreenInitModeOutput detail = initMode.getDetailScreenInitMode(preBootOuput.getUser(),
				preBootOuput.getReflectPlanState().value);
		result.setInitMode(detail.getOutputMode().value);
		//--
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSet = bfPrelaunchAppCmSet.prelaunchAppCommonSetService(
				companyID, appAbsence.getApplication().getEmployeeID(), EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				appAbsence.getApplication().getAppDate());
		//---
		//HOATT - thieu
		//アルゴリズム「実績の取得」を実行する-[Lấy kết quả thực tế]
		//13.実績を取得する(getAchievement)
		//--
		// ドメインモデル「休暇申請設定」を取得する(lấy dữ liệu domain 「休暇申請設定」)
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		//アルゴリズム「勤務種類を取得する（詳細）」を実行する-[Lấy WorkType(detail)]
		//2.勤務種類を取得する（詳細）
		List<WorkType> workTypes = this.appAbsenceThreeProcess.getWorkTypeDetails(
				appCommonSet.appEmploymentWorkType.stream().findFirst().orElse(null), companyID,
				appAbsence.getHolidayAppType().value, appAbsence.getAllDayHalfDayLeaveAtr().value,
				appAbsence.isHalfDayFlg());
		//取得した勤務種類リストに「ドメインモデル「休暇申請」．勤務種類コード」が存在するかチェックする-(Check WorkTypeCode có tồn tại k?)
		boolean masterUnreg = false;
		if (!CollectionUtil.isEmpty(workTypes)) {
			if (appAbsence.getWorkTypeCode() != null) {
				List<WorkType> workTypeCodeInWorkTypes = workTypes.stream()
						.filter(x -> x.getWorkTypeCode().toString().equals(appAbsence.getWorkTypeCode() == null ? null : appAbsence.getWorkTypeCode().toString()))
						.collect(Collectors.toList());
				if (!CollectionUtil.isEmpty(workTypeCodeInWorkTypes)) {//存在する
					result.setWorkTypeCode(appAbsence.getWorkTypeCode().toString());
				} else {
					// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する - [Kiểm tra sự tồn tại  và lấy WorkType đã xin ]
					masterUnreg = hdShipmentScreenAFinder.appliedWorkType(companyID, workTypes,
							appAbsence.getWorkTypeCode().toString());
					result.setMasterUnreg(masterUnreg);
					result.setWorkTypeCode(appAbsence.getWorkTypeCode().toString());
				}
			}
		}
		List<AbsenceWorkType> absenceWorkTypes = new ArrayList<>();
		for (WorkType workType : workTypes) {
			AbsenceWorkType absenceWorkType = new AbsenceWorkType(workType.getWorkTypeCode().toString(),
					workType.getWorkTypeCode().toString() + "　　" + workType.getName().toString());
			absenceWorkTypes.add(absenceWorkType);
		}
		// display list work Type
		result.setWorkTypes(absenceWorkTypes);
		// 1.就業時間帯の表示制御(xu li hien thị A6_1)
		if(appAbsence.getWorkTypeCode() == null){
			result.setDisplayWorkChangeFlg(false);
		}else{
			result.setDisplayWorkChangeFlg(this.appAbsenceFourProcess
					.getDisplayControlWorkingHours(appAbsence.getWorkTypeCode().toString(), hdAppSet, companyID));
		}
		//アルゴリズム「職場別就業時間帯を取得」を実行する-[Lấy WorkTime cho từng workplace]
		// 1.職場別就業時間帯を取得
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID,
				appAbsence.getApplication().getEmployeeID(), appAbsence.getApplication().getAppDate())
				.stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
		result.setWorkTimeCodes(listWorkTimeCodes);
		if (result.getWorkTimeCode() != null) {
			WorkTimeSetting workTime = workTimeRepository.findByCode(companyID, result.getWorkTimeCode()).isPresent()
					? workTimeRepository.findByCode(companyID, result.getWorkTimeCode()).get() : null;
			if (workTime != null) {
				result.setWorkTimeName(workTime.getWorkTimeDisplayName().getWorkTimeName().toString());
			}
		}
		// 1-1.起動時のエラーチェック
		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();

		holidayAppTypes = getHolidayAppTypeName(hdAppSet, holidayAppTypes, appCommonSet.getAppEmploymentWorkType().get(0));
		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
		result.setHolidayAppTypeName(holidayAppTypes);
		getAppReason(result, companyID);
		// get employeeName, employeeID
		String employeeName = "";
		employeeName = employeeAdapter.getEmployeeName(appAbsence.getApplication().getEmployeeID());
		result.setEmployeeName(employeeName);
		if(appCommonSet.applicationSetting != null){
			result.setAppReasonRequire(appCommonSet.applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED));
		}
		//--
		//HOATT - thieu
		//ドメインモデル「申請定型理由」を取得する-(Lấy domain 「ApplicationFormReason」)
		//--
		// 8.休暇系の設定を取得する :TODO
		//選択する休暇種類をチェックする-(Check HolidayType-loại phép đang chọn)
		if(appAbsence.getHolidayAppType().equals(HolidayAppType.SPECIAL_HOLIDAY)){//選択する休暇種類が特別休暇(holidayType = nghỉ đặc biệt)
			//hoatt - 2018.08.08 - doi ung specHd
			//get 特別休暇申請
			Optional<AppForSpecLeave> appSpec = repoAppLeaveSpec.getAppForSpecLeaveById(companyID, appID);
			if(!appSpec.isPresent()){
				return result;
			}
			AppForSpecLeave specLeave = appSpec.get();
			result.setSpecHdDto(new AppForSpecLeaveDto(specLeave.getAppID(), specLeave.isMournerFlag(), 
					specLeave.getRelationshipCD().v(), specLeave.getRelationshipReason().v()));
			//指定する勤務種類が事象に応じた特別休暇かチェックする
			CheckWkTypeSpecHdEventOutput checkSpecHd = specHdEventAlg.checkWkTypeSpecHdForEvent(companyID, appAbsence.getWorkTypeCode().v());
			if(!checkSpecHd.isSpecHdForEventFlag()){//その以外
				return result;
			}
			////事象に応じた特休フラグがtrue(SpecialHolidayEventFlag = true)
			SpecialHolidayEvent specHd = checkSpecHd.getSpecHdEvent().get();
			MaxDaySpecHdOutput maxDay = null;
			List<DateSpecHdRelationOutput> lstRela = new ArrayList<>();
			//取得したメインモデル「事象に対する特別休暇」．上限日数．種類をチェックする-(Check domain [SpecialHolidayEvent]. GrantDay.Type)
			if(specHd.getMaxNumberDay().equals(MaxNumberDayType.LIMIT_FIXED_DAY)){//種類が固定日数を上限とする(type = FixedDayGrant)
				//指定する特休枠の上限日数を取得する
				maxDay = specHdEventAlg.getMaxDaySpecHd(companyID, checkSpecHd.getFrameNo().get(), specHd, Optional.of(specLeave.getRelationshipCD().v()));
			}else{//その以外
				//指定する特休枠の続柄に対する上限日数を取得する-(get MaxDay SpecHd ByRela FrameNo)
				lstRela = specHdEventAlg.getMaxDaySpecHdByRelaFrameNo(companyID, checkSpecHd.getFrameNo().get());
				//指定する特休枠の上限日数を取得する - (get MaxDay SpecHd)
				maxDay = specHdEventAlg.getMaxDaySpecHd(companyID, checkSpecHd.getFrameNo().get(), specHd, lstRela.size() == 0 ? Optional.empty() : Optional.of(lstRela.get(0).getRelationCD()));
			}
			result.setSpecHdForEventFlag(checkSpecHd.isSpecHdForEventFlag());
			result.setMaxNumberDayType(specHd.getMaxNumberDay().value);
			result.setMaxDayObj(maxDay);
			result.setLstRela(lstRela);
			result.setMakeInvitation(specHd.getMakeInvitation().value);
		}
		//No.376
		//残数取得する
		//Bug#101904
		//・基準日＝システム日付
		CheckDispHolidayType checkDispHolidayType = absenseProcess.checkDisplayAppHdType(companyID, appAbsence.getApplication().getEmployeeID(),
				GeneralDate.today());
		NumberOfRemainOutput numberRemain = absenseProcess.getNumberOfRemaining(
				companyID, 
				appAbsence.getApplication().getEmployeeID(),
				GeneralDate.today(),
				checkDispHolidayType.isYearManage(), 
				checkDispHolidayType.isSubHdManage(), 
				checkDispHolidayType.isSubVacaManage(), 
				checkDispHolidayType.isRetentionManage());
		result.setNumberRemain(numberRemain);
		return result;*/
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
		
		/*String employeeID = param.getEmployeeID();
		String startAppDate = param.getStartAppDate();
		Integer holidayType = param.getHolidayType();
		Integer alldayHalfDay = param.getAlldayHalfDay();
		boolean displayHalfDayValue = param.isDisplayHalfDayValue();
		if (employeeID == null) {
			employeeID = AppContexts.user().employeeId();
		}
		String companyID = AppContexts.user().companyId();
		AppAbsenceDto result = new AppAbsenceDto();

		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSet = bfPrelaunchAppCmSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				startAppDate == null ? null : GeneralDate.fromString(startAppDate, DATE_FORMAT));
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		// 1.勤務種類を取得する（新規）
		List<AbsenceWorkType> workTypes = this.appAbsenceThreeProcess.getWorkTypeCodes(
				appCommonSet.appEmploymentWorkType, companyID, employeeID, holidayType, alldayHalfDay,
				displayHalfDayValue, hdAppSet);
		result.setWorkTypes(workTypes);
		// 1.就業時間帯の表示制御(xu li hien thị A6_1)
		if (CollectionUtil.isEmpty(workTypes)) {
			result.setChangeWorkHourFlg(false);
			return result;
		} 
		result.setChangeWorkHourFlg(this.appAbsenceFourProcess
				.getDisplayControlWorkingHours(workTypes.get(0).getWorkTypeCode(), hdAppSet, companyID));
		
		if (holidayType == HolidayAppType.DIGESTION_TIME.value) {
			// TODO
			// 9.必要な時間を算出する
		} else if (holidayType == HolidayAppType.SPECIAL_HOLIDAY.value) {
			//hoatt - 2018.08.08 - doi ung specHd
			//指定する勤務種類が事象に応じた特別休暇かチェックする
			CheckWkTypeSpecHdEventOutput checkSpecHd = specHdEventAlg.checkWkTypeSpecHdForEvent(companyID, workTypes.get(0).getWorkTypeCode());
			if(!checkSpecHd.isSpecHdForEventFlag()){//その以外
				return result;
			}
			//事象に応じた特休フラグがtrue(specialHolidayEventFlag = true)
			//取得したメインモデル「事象に対する特別休暇」．上限日数．種類をチェックする-(Check domain [SpecialHolidayEvent]. GrantDay.Type)
			SpecialHolidayEvent specHd = checkSpecHd.getSpecHdEvent().get();
			result.setMaxNumberDayType(specHd.getMaxNumberDay().value);
			MaxDaySpecHdOutput maxDay = null;
			List<DateSpecHdRelationOutput> lstRela = new ArrayList<>();
			if(specHd.getMaxNumberDay().equals(MaxNumberDayType.LIMIT_FIXED_DAY)){//種類が固定日数を上限とする(type = FixedDayGrant)
				//指定する特休枠の上限日数を取得する
				maxDay = specHdEventAlg.getMaxDaySpecHd(companyID, checkSpecHd.getFrameNo().get(), specHd,
						param.getRelationCD() == null ? Optional.empty() : Optional.of(param.getRelationCD()));
			}else{//その以外
				//指定する特休枠の続柄に対する上限日数を取得する-(get MaxDay SpecHd ByRela FrameNo)
				lstRela = specHdEventAlg.getMaxDaySpecHdByRelaFrameNo(companyID, checkSpecHd.getFrameNo().get());
				//指定する特休枠の上限日数を取得する - (get MaxDay SpecHd)
				maxDay = specHdEventAlg.getMaxDaySpecHd(companyID, checkSpecHd.getFrameNo().get(), specHd, lstRela.size() == 0 ? Optional.empty() : Optional.of(lstRela.get(0).getRelationCD()));
			}
			result.setMaxDayObj(maxDay);
			result.setLstRela(lstRela);
			result.setMakeInvitation(specHd.getMakeInvitation().value);
		}
		return result;*/
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
		String companyID = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		GeneralDate targetDate = GeneralDate.fromString(startAppDate, "yyyy/MM/dd");
		dateLst.add(targetDate);
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = appAbsenceStartInfoDto.toDomain();
		// 共通インタラクション「申請日を変更する」を実行する
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(
				companyID, 
				dateLst, 
				targetDate,
				ApplicationType.ABSENCE_APPLICATION, 
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput());
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
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().stream().findFirst().get().getSid(), 
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
		return result;
		/*
		AppAbsenceDto result = new AppAbsenceDto();
		ApplicationDto_New application = new ApplicationDto_New();
		if (employeeID == null) {
			employeeID = AppContexts.user().employeeId();
		}
		String companyID = AppContexts.user().companyId();

		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = bfPrelaunchAppCmSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				startAppDate == null ? null : GeneralDate.fromString(startAppDate, DATE_FORMAT));

		// ドメインモデル「申請表示設定」．事前事後区分表示をチェックする
		if (appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value) {
			result.setPrePostFlg(AppDisplayAtr.NOTDISPLAY.value == 1 ? true : false);
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(
					EnumAdaptor.valueOf(ApplicationType.BREAK_TIME_APPLICATION.value, ApplicationType.class),
					appCommonSettingOutput.generalDate,0);
			if (prePostAtrJudgment != null) {
				prePostAtr = prePostAtrJudgment.value;
			}
		} else {
			result.setPrePostFlg(AppDisplayAtr.DISPLAY.value == 1 ? true : false);
		}
		application.setPrePostAtr(prePostAtr);
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする ( Domain model "application setting".
		// Check base date of approval route )
		if (appCommonSettingOutput.applicationSetting.getBaseDateFlg().value == BaseDateFlg.APP_DATE.value) {
			if(holidayType != null){
				String workTypeCDForChange = "";
				Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
				// 1.勤務種類を取得する（新規） :TODO
				List<AbsenceWorkType> workTypes = this.appAbsenceThreeProcess.getWorkTypeCodes(
						appCommonSettingOutput.appEmploymentWorkType, companyID, employeeID, holidayType, alldayHalfDay,
						displayHalfDayValue, hdAppSet);
				if (!CollectionUtil.isEmpty(workTypes)) {
					List<AbsenceWorkType> workTypeForFilter = workTypes.stream()
							.filter(x -> x.getWorkTypeCode().equals(workTypeCode == null ? "" : workTypeCode))
							.collect(Collectors.toList());
					if (CollectionUtil.isEmpty(workTypeForFilter)) {
						workTypeCDForChange = workTypes.get(0).getWorkTypeCode();
					} else {
						workTypeCDForChange = workTypeCode;
					}
				}
				result.setWorkTypes(workTypes);
				result.setWorkTypeCode(workTypeCDForChange);
			}
		}
		result.setApplication(application);
		//2018/07/31　EA修正履歴No.2402　にて追加
		//アルゴリズム「14.休暇種類表示チェック」を実行する
		CheckDispHolidayType checkDis = absenseProcess.checkDisplayAppHdType(companyID, employeeID, GeneralDate.fromString(startAppDate, DATE_FORMAT));
		List<HolidayAppTypeName> holidayAppTypes = new ArrayList<>();
		// ドメインモデル「休暇申請設定」を取得する(lấy dữ liệu domain 「休暇申請設定」)
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		holidayAppTypes = this.getHolidayAppTypeName(hdAppSet,holidayAppTypes,appCommonSettingOutput.getAppEmploymentWorkType().get(0));
		holidayAppTypes.sort((a, b) -> a.getHolidayAppTypeCode().compareTo(b.getHolidayAppTypeCode()));
		result.setHolidayAppTypeName(holidayAppTypes);
		result.setCheckDis(checkDis);
		return result;
		*/
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
		/*
		AppAbsenceDto result = new AppAbsenceDto();
		if (employeeID == null) {
			employeeID = AppContexts.user().employeeId();
		}
		String companyID = AppContexts.user().companyId();
		if (holidayType == null) {
			return result;
		}
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = bfPrelaunchAppCmSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				startAppDate == null ? null : GeneralDate.fromString(startAppDate, DATE_FORMAT));

		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		// 1.勤務種類を取得する（新規）
		List<AbsenceWorkType> workTypes = this.appAbsenceThreeProcess.getWorkTypeCodes(
				appCommonSettingOutput.appEmploymentWorkType, companyID, employeeID, holidayType, alldayHalfDay,
				displayHalfDayValue, hdAppSet);
		result.setWorkTypes(workTypes);
		// 1.就業時間帯の表示制御(xu li hien thị A6_1)
		if (CollectionUtil.isEmpty(workTypes)) {
			result.setChangeWorkHourFlg(false);
		} else {
			result.setChangeWorkHourFlg(this.appAbsenceFourProcess
					.getDisplayControlWorkingHours(workTypes.get(0).getWorkTypeCode(), hdAppSet, companyID));
		}
		if (result.isChangeWorkHourFlg()) {
			// 2.就業時間帯を取得する
			// 1.職場別就業時間帯を取得
			List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,
					appCommonSettingOutput.generalDate).stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
			result.setWorkTimeCodes(listWorkTimeCodes);
		}
		return result;
		*/
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
		/*AppAbsenceDto result = new AppAbsenceDto();
		if (employeeID == null) {
			employeeID = AppContexts.user().employeeId();
		}
		String companyID = AppContexts.user().companyId();
		if (holidayType == null) {
			return result;
		}
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = bfPrelaunchAppCmSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				startAppDate == null ? null : GeneralDate.fromString(startAppDate, DATE_FORMAT));
		// 2.勤務種類を取得する（詳細）
		List<WorkType> workTypes = this.appAbsenceThreeProcess.getWorkTypeDetails(
				appCommonSettingOutput.appEmploymentWorkType.stream().findFirst().orElse(null), 
				companyID, 
				EnumAdaptor.valueOf(holidayType, HolidayAppType.class), 
				alldayHalfDay,
				displayHalfDayValue);
		List<AbsenceWorkType> absenceWorkTypes = new ArrayList<>();
		for (WorkType workType : workTypes) {
			AbsenceWorkType absenceWorkType = new AbsenceWorkType(workType.getWorkTypeCode().toString(),
					workType.getWorkTypeCode().toString() + "　　" + workType.getName().toString());
			absenceWorkTypes.add(absenceWorkType);
		}
		result.setWorkTypes(absenceWorkTypes);
		// 1.就業時間帯の表示制御(xu li hien thị A6_1)
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		if (CollectionUtil.isEmpty(workTypes)) {
			result.setChangeWorkHourFlg(false);
		} else {
			result.setChangeWorkHourFlg(this.appAbsenceFourProcess
					.getDisplayControlWorkingHours(absenceWorkTypes.get(0).getWorkTypeCode(), hdAppSet, companyID));
		}
		if (result.isChangeWorkHourFlg()) {
			// 2.就業時間帯を取得する
			// 1.職場別就業時間帯を取得
			List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,
					appCommonSettingOutput.generalDate).stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
			result.setWorkTimeCodes(listWorkTimeCodes);
		}
		return result;*/
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
		/*AppAbsenceDto result = new AppAbsenceDto();
		if (employeeID == null) {
			employeeID = AppContexts.user().employeeId();
		}
		String companyID = AppContexts.user().companyId();
		// 1-1.新規画面起動前申請共通設定を取得する
		AppCommonSettingOutput appCommonSettingOutput = bfPrelaunchAppCmSet.prelaunchAppCommonSetService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION.value,
				EnumAdaptor.valueOf(ApplicationType.ABSENCE_APPLICATION.value, ApplicationType.class),
				startAppDate == null ? null : GeneralDate.fromString(startAppDate, DATE_FORMAT));
		if (holidayType == null) {
			return result;
		}
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		// 1.勤務種類を取得する（新規）
		List<AbsenceWorkType> workTypes = this.appAbsenceThreeProcess.getWorkTypeCodes(
				appCommonSettingOutput.appEmploymentWorkType, companyID, employeeID, holidayType, alldayHalfDay,
				displayHalfDayValue, hdAppSet);
		String workTypeCDForChange = "";
		if (!CollectionUtil.isEmpty(workTypes)) {
			List<AbsenceWorkType> workTypeForFilter = workTypes.stream()
					.filter(x -> x.getWorkTypeCode().equals(workTypeCode == null ? "" : workTypeCode))
					.collect(Collectors.toList());
			if (CollectionUtil.isEmpty(workTypeForFilter)) {
				workTypeCDForChange = workTypes.get(0).getWorkTypeCode();
			} else {
				workTypeCDForChange = workTypeCode;
			}
		}
		result.setWorkTypes(workTypes);
		result.setWorkTypeCode(workTypeCDForChange);
		// 1.就業時間帯の表示制御(xu li hien thị A6_1)

		if (CollectionUtil.isEmpty(workTypes) || (workTypeCDForChange != null && workTypeCDForChange.equals(""))) {
			result.setChangeWorkHourFlg(false);
		} else {
			result.setChangeWorkHourFlg(
					this.appAbsenceFourProcess.getDisplayControlWorkingHours(workTypeCDForChange, hdAppSet, companyID));
		}
		if (result.isChangeWorkHourFlg()) {
			// 2.就業時間帯を取得する
			// 1.職場別就業時間帯を取得
//			List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,
//					appCommonSettingOutput.generalDate);

		}
		return result;*/
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
		/*String workTypeCode = param.getWorkTypeCode();
		Integer holidayType = param.getHolidayType();
		String workTimeCode = param.getWorkTimeCode();
		String companyID = AppContexts.user().companyId();
		AppAbsenceDto result = new AppAbsenceDto();
		// 1.就業時間帯の表示制御(xu li hien thị A6_1)
		Optional<HdAppSet> hdAppSet = this.hdAppSetRepository.getAll();
		if (workTypeCode == null) {
			result.setChangeWorkHourFlg(false);
		} else {
			result.setChangeWorkHourFlg(
					this.appAbsenceFourProcess.getDisplayControlWorkingHours(workTypeCode, hdAppSet, companyID));
		}
		if (result.isChangeWorkHourFlg()) {
			// 勤務時間初期値の取得
			PrescribedTimezoneSetting prescribedTimezone = initWorktimeCode(companyID, workTypeCode, workTimeCode);
			if (prescribedTimezone != null) {
				if (!CollectionUtil.isEmpty(prescribedTimezone.getLstTimezone())
						&& prescribedTimezone.getLstTimezone().get(0).isUsed()) {
					result.setStartTime1(prescribedTimezone.getLstTimezone().get(0).getStart().v());
					result.setEndTime1(prescribedTimezone.getLstTimezone().get(0).getEnd().v());
				}
			}
		}
		if (holidayType != null && holidayType == HolidayAppType.DIGESTION_TIME.value) {
			// TODO
			// 9.必要な時間を算出する
		} else if (holidayType != null && holidayType == HolidayAppType.SPECIAL_HOLIDAY.value) {
			//hoatt - 2018.08.08 - doi ung specHd
			//指定する勤務種類が事象に応じた特別休暇かチェックする
			CheckWkTypeSpecHdEventOutput checkSpecHd = specHdEventAlg.checkWkTypeSpecHdForEvent(companyID, workTypeCode);
			if(!checkSpecHd.isSpecHdForEventFlag()){//その以外
				return result;
			}
			////事象に応じた特休フラグがtrue(SpecialHolidayEventFlag = true)
			SpecialHolidayEvent specHd = checkSpecHd.getSpecHdEvent().get();
			//指定する特休枠の上限日数を取得する
			MaxDaySpecHdOutput maxDay = null;
			//取得したメインモデル「事象に対する特別休暇」．上限日数．種類をチェックする-(Check domain [SpecialHolidayEvent]. GrantDay.Type)
			//指定する特休枠の続柄に対する上限日数を取得する-(get MaxDay SpecHd ByRela FrameNo)
			List<DateSpecHdRelationOutput> lstRela = specHdEventAlg.getMaxDaySpecHdByRelaFrameNo(companyID, checkSpecHd.getFrameNo().get());
			//取得したメインモデル「事象に対する特別休暇」．上限日数．種類をチェックする-(Check domain [SpecialHolidayEvent]. GrantDay.Type)
			if(specHd.getMaxNumberDay().equals(MaxNumberDayType.LIMIT_FIXED_DAY)){//種類が固定日数を上限とする(type = FixedDayGrant)
				//指定する特休枠の上限日数を取得する
				maxDay = specHdEventAlg.getMaxDaySpecHd(companyID, checkSpecHd.getFrameNo().get(), specHd, lstRela.size() == 0 ? Optional.empty() : Optional.of(lstRela.get(0).getRelationCD()));
			}else{//その以外
				//指定する特休枠の続柄に対する上限日数を取得する-(get MaxDay SpecHd ByRela FrameNo)
				lstRela = specHdEventAlg.getMaxDaySpecHdByRelaFrameNo(companyID, checkSpecHd.getFrameNo().get());
				//指定する特休枠の上限日数を取得する - (get MaxDay SpecHd)
				maxDay = specHdEventAlg.getMaxDaySpecHd(companyID, checkSpecHd.getFrameNo().get(), specHd, lstRela.size() == 0 ? Optional.empty() : Optional.of(lstRela.get(0).getRelationCD()));
			}
			result.setMaxDayObj(maxDay);
			result.setSpecHdForEventFlag(checkSpecHd.isSpecHdForEventFlag());
			result.setLstRela(lstRela);
			result.setMaxNumberDayType(specHd.getMaxNumberDay().value);
			result.setMakeInvitation(specHd.getMakeInvitation().value);
		}
		result.setHolidayAppType(holidayType);
		return result;*/
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
		/*String companyID = AppContexts.user().companyId();
		List<TimeZoneUseDto> result = new ArrayList<>();
		if (holidayType != null && holidayType == HolidayAppType.DIGESTION_TIME.value) {
			// TODO
			// 9.必要な時間を算出する
		} else {
			// 勤務時間初期値の取得
			List<TimezoneUse> listTimezone = initWorkingHours(companyID, workTypeCode, workTimeCode);
			if (!CollectionUtil.isEmpty(listTimezone)) {
				return listTimezone.stream().map(x -> TimeZoneUseDto.fromDomain(x)).collect(Collectors.toList());
			}
		}
		return result;*/
	}

	/**
	 * ドメインモデル「申請定型理由」を取得する(lấy dữ liệu domain 「申請定型理由」) (hien thị A7_2)
	 * 
	 * @param result
	 * @param companyID
	 */
	private void getAppReason(AppAbsenceDto result, String companyID) {
		List<ApplicationReason> applicationReasons = repoAppReason.getReasonByAppType(companyID,
				ApplicationType.ABSENCE_APPLICATION.value);
		List<ApplicationReasonDto> applicationReasonDtos = new ArrayList<>();
		for (ApplicationReason applicationReason : applicationReasons) {
			ApplicationReasonDto applicationReasonDto = new ApplicationReasonDto(applicationReason.getReasonID(),
					applicationReason.getReasonTemp().v(), applicationReason.getDefaultFlg().value);
			applicationReasonDtos.add(applicationReasonDto);
		}
		result.setApplicationReasonDtos(applicationReasonDtos);
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
	private boolean checkHdType(AppEmploymentSetting appEmploymentSetting, int hdType){
		if(appEmploymentSetting.getHolidayOrPauseType() == hdType){
			//ドメインモデル「休暇申請対象勤務種類」．休暇種類を利用しないがtrue -> ×
			//ドメインモデル「休暇申請対象勤務種類」．休暇種類を利用しないがfalse -> 〇
			return appEmploymentSetting.getHolidayTypeUseFlg() ? false : true;
		}
		//ドメインモデル「休暇申請対象勤務種類」が取得できない場合 -> 〇
		return true;
	}
	private List<HolidayAppTypeName> getHolidayAppTypeName(Optional<HdAppSet> hdAppSet,
			List<HolidayAppTypeName> holidayAppTypes,AppEmploymentSetting appEmploymentSetting){
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
				switch (holidayCode) {
				case 0://年休
					holidayAppTypes.add(new HolidayAppTypeName(holidayCode,
							hdAppSet.get().getYearHdName() == null ? "" : hdAppSet.get().getYearHdName().toString()));
					break;
				case 1://代休
					holidayAppTypes.add(new HolidayAppTypeName(holidayCode,
							hdAppSet.get().getObstacleName() == null ? "" : hdAppSet.get().getObstacleName().toString()));
					break;
				case 2:
					holidayAppTypes.add(new HolidayAppTypeName(holidayCode,
							hdAppSet.get().getAbsenteeism()== null ? "" : hdAppSet.get().getAbsenteeism().toString()));
					break;
				case 3:
					holidayAppTypes.add(new HolidayAppTypeName(holidayCode,
							hdAppSet.get().getSpecialVaca() == null ? "" : hdAppSet.get().getSpecialVaca().toString()));
					break;
				case 4://積立
					holidayAppTypes.add(new HolidayAppTypeName(holidayCode,
							hdAppSet.get().getYearResig() == null ? "" : hdAppSet.get().getYearResig().toString()));
					break;
				case 7://振休
					holidayAppTypes.add(new HolidayAppTypeName(holidayCode,
							hdAppSet.get().getFurikyuName() == null ? "" :  hdAppSet.get().getFurikyuName().toString()));
					break;
				default:
					break;
				}
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
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain();
		// 会社ID
		String companyID = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// Create Application
		GeneralDate startDate = param.getApplicationCommand().getStartDate() == null ? null : GeneralDate.fromString(param.getApplicationCommand().getStartDate(), DATE_FORMAT);
		GeneralDate endDate = param.getApplicationCommand().getEndDate() == null ? null : GeneralDate.fromString(param.getApplicationCommand().getEndDate(), DATE_FORMAT);
		List<DisplayReasonDto> displayReasonDtoLst = 
				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == param.getAppAbsenceCommand().getHolidayAppType())
				.findAny().orElse(null);
		String appReason = "";
		if(displayReasonSet!=null){
			boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
			boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
			String typicalReason = Strings.EMPTY;
			String displayReason = Strings.EMPTY;
			if(displayFixedReason){
				if(Strings.isBlank(param.getApplicationCommand().getAppReasonID())){
					typicalReason += "";
				} else {
					typicalReason += param.getApplicationCommand().getAppReasonID();
				}
			}
			if(displayAppReason){
				if(Strings.isNotBlank(typicalReason)){
					displayReason += System.lineSeparator();
				}
				if(Strings.isBlank(param.getApplicationCommand().getApplicationReason())){
					displayReason += "";
				} else {
					displayReason += param.getApplicationCommand().getApplicationReason();
				}
			}
			ApplicationSetting applicationSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
					.getRequestSetting().getApplicationSetting();
			if(displayFixedReason||displayAppReason){
				if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
						&& Strings.isBlank(typicalReason+displayReason)) {
					throw new BusinessException("Msg_115");
				}
			}
			appReason = typicalReason + displayReason;
		}
		Application_New appRoot = iFactoryApplication.buildApplication(appID, startDate,
				param.getApplicationCommand().getPrePostAtr(), appReason, appReason,
				ApplicationType.ABSENCE_APPLICATION, startDate, endDate, param.getApplicationCommand().getApplicantSID());
		AppForSpecLeave specHd = null;
		AppForSpecLeaveCmd appForSpecLeaveCmd = param.getAppAbsenceCommand().getAppForSpecLeave();
		if(param.getAppAbsenceCommand().getHolidayAppType() == HolidayAppType.SPECIAL_HOLIDAY.value && appForSpecLeaveCmd != null){
			specHd = AppForSpecLeave.createFromJavaType(appID, appForSpecLeaveCmd.isMournerFlag(), appForSpecLeaveCmd.getRelationshipCD(), appForSpecLeaveCmd.getRelationshipReason());
		}
		AppAbsence appAbsence = new AppAbsence(companyID,
				appID,
				param.getAppAbsenceCommand().getHolidayAppType(),
				param.getAppAbsenceCommand().getWorkTypeCode(),
				param.getAppAbsenceCommand().getWorkTimeCode(),
				param.getAppAbsenceCommand().isHalfDayFlg(), 
				param.getAppAbsenceCommand().isChangeWorkHour(),
				param.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), 
				param.getAppAbsenceCommand().getStartTime1(),
				param.getAppAbsenceCommand().getEndTime1(),
				param.getAppAbsenceCommand().getStartTime2(),
				param.getAppAbsenceCommand().getEndTime2(),
				specHd);
		appAbsence.setApplication(appRoot);
		AbsenceCheckRegisterOutput result = absenseProcess.checkBeforeRegister(
				companyID, 
				appAbsenceStartInfoOutput, 
				appRoot, 
				appAbsence, 
				param.getAlldayHalfDay(), 
				param.isAgentAtr(), 
				Optional.ofNullable(param.getMourningAtr()));
		return AbsenceCheckRegisterDto.fromDomain(result);
	}
	
	public AbsenceCheckRegisterDto checkBeforeUpdate(UpdateAppAbsenceCommand param) {
		AppAbsenceStartInfoOutput appAbsenceStartInfoOutput = param.getAppAbsenceStartInfoDto().toDomain();
		// 会社ID
		String companyID = AppContexts.user().companyId();
		Optional<AppAbsence> opAppAbsence = repoAppAbsence.getAbsenceByAppId(companyID, param.getApplicationCommand().getApplicationID());
		if(!opAppAbsence.isPresent()){
			throw new BusinessException("Msg_198");
		}
		List<DisplayReasonDto> displayReasonDtoLst = 
				displayRep.findDisplayReason(companyID).stream().map(x -> DisplayReasonDto.fromDomain(x)).collect(Collectors.toList());
		DisplayReasonDto displayReasonSet = displayReasonDtoLst.stream().filter(x -> x.getTypeOfLeaveApp() == param.getAppAbsenceCommand().getHolidayAppType())
				.findAny().orElse(null);
		DetailScreenInitModeOutput output = initMode.getDetailScreenInitMode(
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getUser(), 
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getReflectPlanState().value);
		String appReason = opAppAbsence.get().getApplication().getAppReason().v();
		if(output.getOutputMode()==OutputMode.EDITMODE){
			if(displayReasonSet!=null){
				boolean displayFixedReason = displayReasonSet.getDisplayFixedReason() == 1 ? true : false;
				boolean displayAppReason = displayReasonSet.getDisplayAppReason() == 1 ? true : false;
				String typicalReason = Strings.EMPTY;
				String displayReason = Strings.EMPTY;
				if(displayFixedReason){
					if(Strings.isBlank(param.getApplicationCommand().getAppReasonID())){
						typicalReason += "";
					} else {
						typicalReason += param.getApplicationCommand().getAppReasonID();
					}
				}
				if(displayAppReason){
					if(Strings.isNotBlank(typicalReason)){
						displayReason += System.lineSeparator();
					}
					if(Strings.isBlank(param.getApplicationCommand().getApplicationReason())){
						displayReason += "";
					} else {
						displayReason += param.getApplicationCommand().getApplicationReason();
					}
				}else{
					if (Strings.isBlank(typicalReason)) {
						displayReason = opAppAbsence.get().getApplication().getAppReason().v();
					}
				}
				ApplicationSetting applicationSetting = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
						.getRequestSetting().getApplicationSetting();
				if(displayFixedReason||displayAppReason){
					if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
							&& Strings.isBlank(typicalReason+displayReason)) {
						throw new BusinessException("Msg_115");
					}
					appReason = typicalReason + displayReason;
				}
			}
		}
		AppAbsence appAbsence = opAppAbsence.get();
		appAbsence.setAllDayHalfDayLeaveAtr(EnumAdaptor.valueOf(param.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), AllDayHalfDayLeaveAtr.class));
		appAbsence.setChangeWorkHour(param.getAppAbsenceCommand().isChangeWorkHour());
		appAbsence.setStartTime1(param.getAppAbsenceCommand().getStartTime1() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getStartTime1()));
		appAbsence.setEndTime1(param.getAppAbsenceCommand().getEndTime1() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getEndTime1()));
		appAbsence.setStartTime2(param.getAppAbsenceCommand().getStartTime2() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getStartTime2()));
		appAbsence.setEndTime2(param.getAppAbsenceCommand().getEndTime2() == null ? null : new TimeWithDayAttr(param.getAppAbsenceCommand().getEndTime2()));
		appAbsence.setWorkTypeCode(param.getAppAbsenceCommand().getWorkTypeCode() == null ? null : new WorkTypeCode(param.getAppAbsenceCommand().getWorkTypeCode()));
		appAbsence.setWorkTimeCode(param.getAppAbsenceCommand().getWorkTimeCode() == null ? null : new WorkTimeCode(param.getAppAbsenceCommand().getWorkTimeCode()));
		appAbsence.getApplication().setAppReason(new AppReason(appReason));
		appAbsence.setVersion(appAbsence.getVersion());
		appAbsence.getApplication().setVersion(param.getApplicationCommand().getVersion());
		AbsenceCheckRegisterOutput result = absenseProcess.checkBeforeUpdate(
				companyID, 
				appAbsenceStartInfoOutput, 
				appAbsence.getApplication(), 
				appAbsence, 
				param.getAppAbsenceCommand().getAllDayHalfDayLeaveAtr(), 
				false, 
				Optional.empty());
		return AbsenceCheckRegisterDto.fromDomain(result);
	}

}
