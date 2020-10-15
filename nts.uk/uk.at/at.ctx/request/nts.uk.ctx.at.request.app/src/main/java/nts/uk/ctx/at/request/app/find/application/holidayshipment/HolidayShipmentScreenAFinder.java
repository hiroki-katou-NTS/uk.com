package nts.uk.ctx.at.request.app.find.application.holidayshipment;
/*import java.util.Collections;
import nts.uk.ctx.at.request.dom.application.appabsence.service.NumberOfRemainOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;*/
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinder;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeSetDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.DisplayInformationApplication;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.WorkTimeInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.WorkTypeKAF011;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.ApplyWorkTypeOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationCombination;
import nts.uk.ctx.at.request.dom.application.holidayshipment.BreakOutType;
import nts.uk.ctx.at.request.dom.application.holidayshipment.HolidayShipmentService;
import nts.uk.ctx.at.request.dom.application.overtime.service.CheckWorkingInfoResult;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Sonnlb
 *
 */
@Stateless
public class HolidayShipmentScreenAFinder {

	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	private WorkplaceAdapter wkPlaceAdapter;
	@Inject
	private EmpSubstVacationRepository empSubrepo;
	@Inject
	private ComSubstVacationRepository comSubrepo;
	@Inject
	private WithDrawalReqSetRepository withDrawRepo;
//	@Inject
//	private ApplicationReasonRepository appResonRepo;
	@Inject
	private EmployeeRequestAdapter empAdaptor;
	@Inject
	private WorkTypeRepository wkTypeRepo;
	@Inject
	private WorkTimeSettingRepository wkTimeRepo;
	@Inject
	private PredetemineTimeSettingRepository preTimeSetRepo;
//	@Inject
//	private CollectAchievement collectAchievement;
//	@Inject
//	private RequestOfEachWorkplaceRepository requestWpRepo;
//	@Inject
//	private RequestOfEachCompanyRepository requestComRepo;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private BasicScheduleService basicService;
	@Inject
	private RequestSettingRepository reqSetRepo;
	@Inject
	private WorkingConditionRepository wkingCondRepo;
	@Inject
	private WorkingConditionItemRepository wkingCondItemRepo;
	@Inject
	private WorkTimeSettingRepository wkTimeSetRepo;
	@Inject
	private AtEmployeeAdapter atEmpAdaptor;
//	@Inject
//	private AbsenceReruitmentMngInPeriodQuery absRertMngInPeriod;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
//	@Inject 
//	private WorkingConditionService workingConditionService;
	
	@Inject
	private AppAbsenceFinder appAbsenceFinder;
	
	@Inject 
	private AppEmploymentSettingRepository appEmploymentSetting;
	
	@Inject
	private HolidayShipmentService holidayShipmentService;
	
	private static final ApplicationType APP_TYPE = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;

	/**
	 * start event
	 * 
	 * @param employeeID
	 * @param initDateInput
	 * @param uiType
	 * @return HolidayShipmentDto
	 */
	// Screen A Start
	public HolidayShipmentDto startPageA(List<String> sIDs, GeneralDate initDate, int uiType) {
		String employeeID = CollectionUtil.isEmpty(sIDs) ? AppContexts.user().employeeId() : sIDs.get(0);
		String companyID = AppContexts.user().companyId();

		AppCommonSettingOutput appCommonSettingOutput = getAppCommonSet(companyID, employeeID, initDate);
		// アルゴリズム「起動前共通処理（新規）」を実行する
		HolidayShipmentDto result = commonProcessBeforeStart(APP_TYPE, companyID, employeeID, initDate,
				appCommonSettingOutput);

		result.setEmployees(atEmpAdaptor.getByListSID(sIDs));

		GeneralDate refDate = result.getRefDate();

		// アルゴリズム「事前事後区分の判断」を実行する
		result.setPreOrPostType(
				otherCommonAlgorithm.judgmentPrePostAtr(APP_TYPE, refDate, uiType == 0 ? true : false).value);
		
        // 1.職場別就業時間帯を取得
        List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,
                appCommonSettingOutput.generalDate).stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
        result.setWorkTimeCDs(listWorkTimeCodes);


		// アルゴリズム「社員の労働条件を取得する」を実行する
		Optional<WorkingConditionItem> wkingItem = getWorkingCondition(companyID, employeeID, refDate);

		String wkTimeCD = getWkTimeCD(wkingItem);
		
        //12.マスタ勤務種類、就業時間帯データをチェック
        CheckWorkingInfoResult checkResult = otherCommonAlgorithm.checkWorkingInfo(companyID, null,wkTimeCD);
        //「職場別就業時間帯」を取得した先頭値を表示
        if(checkResult.isWkTimeError() && !listWorkTimeCodes.isEmpty()){
            wkTimeCD = listWorkTimeCodes.get(0);
        }
		setWkTimeInfo(result, wkTimeCD);
		
        // アルゴリズム「振休振出申請起動時の共通処理」を実行する
		GeneralDate appDate, deadDate;

		String takingOutWkTypeCD, takingOutWkTimeCD, holiDayWkTypeCD, holidayWkTimeCD;

		appDate = deadDate = null;

		takingOutWkTypeCD = takingOutWkTimeCD = holiDayWkTypeCD = holidayWkTimeCD = null;
		
		commonProcessAtStartup(companyID, employeeID, refDate, appDate, takingOutWkTypeCD, takingOutWkTimeCD, deadDate,
				holiDayWkTypeCD, holidayWkTimeCD, result, appCommonSettingOutput);
		// アルゴリズム「勤務時間初期値の取得」を実行する
		String wkTypeCD = result.getRecWkTypes().size() > 0 ? result.getRecWkTypes().get(0).getWorkTypeCode() : "";
		setWorkTimeInfo(result, wkTimeCD, wkTypeCD, companyID);
		//[No.506]振休残数を取得する
//		double absRecMng = absRertMngInPeriod.getAbsRecMngRemain(employeeID, GeneralDate.today()).getRemainDays();
//		result.setAbsRecMng(absRecMng);
		
		return result;
	}

	private void setWkTimeInfo(HolidayShipmentDto result, String wkTimeCD) {

		result.setWkTimeCD(wkTimeCD);
		result.setWkTimeName(getWkTimeName(wkTimeCD));

	}

	private String getWkTimeName(String wkTimeCD) {
		String companyId = AppContexts.user().companyId();

		StringBuilder builder = new StringBuilder();
		builder.append("");
		if (!StringUtil.isNullOrEmpty(wkTimeCD, true)) {
			this.wkTimeSetRepo.findByCode(companyId, wkTimeCD).ifPresent(x -> {
				builder.append(x.getWorkTimeDisplayName().getWorkTimeName().v());
			});
		}

		return builder.toString();
	}

	private Optional<WorkingConditionItem> getWorkingCondition(String companyID, String employeeID,
			GeneralDate refDate) {
		List<WorkingConditionItem> wkingItems = new ArrayList<WorkingConditionItem>();
		wkingCondRepo.getBySidAndStandardDate(companyID, employeeID, refDate).ifPresent(wkCond -> {
			wkCond.getDateHistoryItem().forEach(hisItem -> {
				wkingCondItemRepo.getByHistoryId(hisItem.identifier()).ifPresent(wkItem -> {
					wkingItems.add(wkItem);
				});
				;
			});
		});
		return CollectionUtil.isEmpty(wkingItems) ? Optional.empty() : Optional.of(wkingItems.get(0));
	}

	public AppCommonSettingOutput getAppCommonSet(String companyID, String employeeID, GeneralDate initDate) {
		int rootAtr = 1;
		// 1-1.新規画面起動前申請共通設定を取得する
		return beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, rootAtr, APP_TYPE,
				initDate);
	}

	private void setWorkTimeInfo(HolidayShipmentDto result, String wkTimeCD, String wkTypeCD, String companyID) {
		if (wkTimeCD != null) {
			if (StringUtils.isNoneEmpty(wkTypeCD)) {
				result.setWorkTimeInfo(getWkTimeInfoInitValue(companyID, wkTypeCD, wkTimeCD));
			}

		}

	}

	public WorkTimeInfoDto changeWorkType(String workTypeCD, String wkTimeCD) {
		String companyID = AppContexts.user().companyId();
		return getWkTimeInfoInitValue(companyID, workTypeCD, wkTimeCD);

	}
	
	//振出日を変更する
	public DisplayInforWhenStarting changeWorkingDateRefactor(GeneralDate workingDate, GeneralDate holidayDate, DisplayInforWhenStarting displayInforWhenStarting) {
		// error EA refactor 4
		/*String companyId = AppContexts.user().companyId();
		
		List<GeneralDate> listTagetDate = new ArrayList<>();
		listTagetDate.add(workingDate == null ? null : workingDate);
		listTagetDate.add(holidayDate == null ? null : holidayDate);
		
		//申請日を変更する(Thay đổi Applicationdate)
		AppDispInfoWithDateOutput_Old appDateProcess = commonAlgorithm.changeAppDateProcess(
				companyId, 
				listTagetDate,
				ApplicationType_Old.COMPLEMENT_LEAVE_APPLICATION, 
				displayInforWhenStarting.getAppDispInfoStartup().appDispInfoNoDateOutput.toDomain(), 
				displayInforWhenStarting.getAppDispInfoStartup().appDispInfoWithDateOutput.toDomain());
		//「振休振出申請起動時の表示情報」．申請表示情報．申請表示情報(基準日関係あり)=上記取得した「申請表示情報(基準日関係あり)」 
		displayInforWhenStarting.getAppDispInfoStartup().appDispInfoWithDateOutput = AppDispInfoWithDateDto_Old .fromDomain(appDateProcess);
		//INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする 
		if(displayInforWhenStarting.getAppDispInfoStartup().appDispInfoNoDateOutput.requestSetting.applicationSetting.recordDate == RecordDate.APP_DATE.value) {
			//1.振出申請（新規）起動処理(申請対象日関係あり)/1.xử lý khởi động đơn xin làm bù(new)(có liên quan ApplicationTargetdate)
			DisplayInformationApplication applicationForWorkingDay = this.applicationForWorkingDay(
					companyId,
					displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
					displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput().getBaseDate(),
					displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(),
					displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput().getWorkTimeLst()
					);
			//「振休振出申請起動時の表示情報」．振出申請起動時の表示情報=上記取得した「振出申請起動時の表示情報」
			displayInforWhenStarting.setApplicationForWorkingDay(applicationForWorkingDay);
		}
		return displayInforWhenStarting;*/
		return null;
	}
	
	//振休日を変更する
	public DisplayInforWhenStarting changeHolidayDateRefactor(GeneralDate workingDate, GeneralDate holidayDate, DisplayInforWhenStarting displayInforWhenStarting) {
		// error EA refactor 4
		/*String companyId = AppContexts.user().companyId();
		
		List<GeneralDate> listTagetDate = new ArrayList<>();
		listTagetDate.add(workingDate == null ? null : workingDate);
		listTagetDate.add(holidayDate == null ? null : holidayDate);
		
		//申請日を変更する(Thay đổi Applicationdate)
		AppDispInfoWithDateOutput_Old appDateProcess = commonAlgorithm.changeAppDateProcess(
				companyId, 
				listTagetDate, 
				ApplicationType_Old.COMPLEMENT_LEAVE_APPLICATION, 
				displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoNoDateOutput(), 
				displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput());
		//「振休振出申請起動時の表示情報」．申請表示情報．申請表示情報(基準日関係あり)=上記取得した「申請表示情報(基準日関係あり)」 
		displayInforWhenStarting.getAppDispInfoStartup().appDispInfoWithDateOutput = AppDispInfoWithDateDto_Old.fromDomain(appDateProcess);
		//INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする 
		if(displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoNoDateOutput().getRequestSetting().getApplicationSetting().getRecordDate() == RecordDate.APP_DATE) {
			//1.振出申請（新規）起動処理(申請対象日関係あり)/1.xử lý khởi động đơn xin làm bù(new)(có liên quan ApplicationTargetdate)
			DisplayInformationApplication applicationForHoliday = this.applicationForHoliday(
					companyId,
					displayInforWhenStarting.getAppDispInfoStartup().toDomain().getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode()
					);
			//「振休振出申請起動時の表示情報」．振休申請起動時の表示情報=上記取得した「振休申請起動時の表示情報」
			displayInforWhenStarting.setApplicationForHoliday(applicationForHoliday);
		}
		return displayInforWhenStarting;*/
		return null;
	}
	

	public HolidayShipmentDto changeAppDate(GeneralDate recDate, GeneralDate absDate, int comType, int uiType) {
		
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		
		GeneralDate baseDate = getBaseDate(comType, absDate, recDate);
		
		AppCommonSettingOutput appCommonSettingOutput = getAppCommonSet(companyID, employeeID, baseDate);
		
		HolidayShipmentDto output = commonProcessBeforeStart(APP_TYPE, companyID, employeeID, baseDate,
				appCommonSettingOutput);
		// アルゴリズム「実績の取得」を実行する
		// AchievementOutput achievementOutput = getAchievement(companyID,
		// employeeID, baseDate);
		// アルゴリズム「申請日の変更」を実行する
		setChangeAppDateData(recDate, absDate, companyID, employeeID, uiType, output, appCommonSettingOutput, baseDate);

		return output;
	}

	private GeneralDate getBaseDate(int comType, GeneralDate absDate, GeneralDate recDate) {
		if (comType == ApplicationCombination.Abs.value) {
			return absDate;
		}
		if(comType == ApplicationCombination.RecAndAbs.value && recDate == null){
			return GeneralDate.today();
		}
		return recDate;
	}

	public WorkTimeInfoDto getSelectedWorkingHours(String wkTypeCD, String wkTimeCD) {
		String companyID = AppContexts.user().companyId();
		return getWkTimeInfoInitValue(companyID, wkTypeCD, wkTimeCD);
	}

	private String getWkTimeCD(Optional<WorkingConditionItem> wkingItem) {
		StringBuilder builder = new StringBuilder();
		builder.append("");

		wkingItem.ifPresent(item -> {
			if (item == null) {return;}
			if (item.getWorkCategory() == null) {return;}
			if (item.getWorkCategory().getWeekdayTime() == null) {return;}
			if (item.getWorkCategory().getWeekdayTime().getWorkTimeCode() == null) {return;}
			item.getWorkCategory().getWeekdayTime().getWorkTimeCode().ifPresent(wkIimeCd -> {
				builder.append(wkIimeCd);
			});
		});

		return builder.toString();
	}

	private void setChangeAppDateData(GeneralDate recDate, GeneralDate absDate, String companyID, String employeeID,
			int uiType, HolidayShipmentDto output, AppCommonSettingOutput appCommonSettingOutput, GeneralDate appDate) {
		// アルゴリズム「基準申請日の決定」を実行する
		GeneralDate referenceDate = holidayShipmentService.detRefDate(recDate, absDate);
		int rootAtr = EmploymentRootAtr.APPLICATION.value;
		AppCommonSettingOutput appCommonSet = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID, rootAtr, APP_TYPE, referenceDate);

		ApplicationSetting appSet = appCommonSet.applicationSetting;
		// 承認ルート基準日をチェックする
		GeneralDate inputDate;
		if (appSet.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)) {

			inputDate = referenceDate;
		} else {
			inputDate = GeneralDate.today();
		}
		// アルゴリズム「社員の対象申請の承認ルートを取得する」を実行する
	/*	List<ApprovalRootImport> approvalRoots = rootAdapter.getApprovalRootOfSubjectRequest(companyID, employeeID,
				rootAtr, APP_TYPE.value, referenceDate);*/
		boolean getSetting = true;
		String recWkTypeCD, recWkTimeCode, absWkTypeCD, absWkTimeCode;
		recWkTypeCD = recWkTimeCode = absWkTypeCD = absWkTimeCode = null;
		// アルゴリズム「基準日別設定の取得」を実行する
		setDateSpecificSetting(companyID, employeeID, inputDate, getSetting, recWkTypeCD, recWkTimeCode, absWkTypeCD,
				absWkTimeCode, appCommonSet, output);
		// アルゴリズム「事前事後区分の最新化」を実行する
		if (appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().value == AppDisplayAtr.NOTDISPLAY.value) {
			// error EA refactor 4
			// 3.事前事後の判断処理(事前事後非表示する場合)
			/*PrePostAtr_Old prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(
					EnumAdaptor.valueOf(ApplicationType_Old.BREAK_TIME_APPLICATION.value, ApplicationType_Old.class),
					appDate,0);
			if(prePostAtrJudgment != null){
				output.setPreOrPostType(prePostAtrJudgment.value);
			}*/
		}

		output.setRefDate(inputDate);

	}

	private WorkTimeInfoDto getWkTimeInfoInitValue(String companyID, String wkTypeCode, String wkTimeCode) {
		// ドメインモデル「勤務種類」を取得する
		WorkTimeInfoDto result = new WorkTimeInfoDto();
		WorkType wkType = null;
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCode);
		if (wkTypeOpt.isPresent()) {
			wkType = wkTypeOpt.get();

			result.setWkType(WorkTypeDto.fromDomain(wkType));
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			basicService.checkWorkDay(wkTypeCode);

		}
		if (wkType != null && wkTimeCode != null) {

			setWkTimeZones(wkType.getAttendanceHolidayAttr(), companyID, wkTimeCode, result);

		}
		return result;

	}

	private void setWkTimeZones(AttendanceHolidayAttr wkTypeAttendance, String companyID, String wkTimeCode,
			WorkTimeInfoDto result) {

		if (!wkTypeAttendance.equals(AttendanceHolidayAttr.HOLIDAY)) {

			// アルゴリズム「所定時間帯を取得する」を実行する
			List<TimezoneUse> timeZones = getTimeZones(companyID, wkTimeCode, wkTypeAttendance);

			result.setTimezoneUseDtos(
					timeZones.stream().map(x -> TimeZoneUseDto.fromDomain(x)).collect(Collectors.toList()));

		}

	}

	@SuppressWarnings("incomplete-switch")
	public List<TimezoneUse> getTimeZones(String companyID, String wkTimeCode, AttendanceHolidayAttr wkTypeAttendance) {

		List<TimezoneUse> timeZones = new ArrayList<TimezoneUse>();

		// ○就業時間帯を取得
		Optional<WorkTimeSetting> workTimeOpt = wkTimeRepo.findByCode(companyID, wkTimeCode);

		if (workTimeOpt.isPresent()) {

			wkTimeCode = workTimeOpt.get().getWorktimeCode().v();
			// ○所定時間帯を取得
			Optional<PredetemineTimeSetting> preTimeSetOpt = preTimeSetRepo.findByWorkTimeCode(companyID, wkTimeCode);

			if (preTimeSetOpt.isPresent()) {

				PredetemineTimeSetting preTimeSet = preTimeSetOpt.get();

				List<TimezoneUse> timeZonesInPreTimeSet = preTimeSet.getPrescribedTimezoneSetting().getLstTimezone();

				switch (wkTypeAttendance) {
				case MORNING:
					timeZonesInPreTimeSet.stream().forEach(
							x -> x.updateEndTime(preTimeSet.getPrescribedTimezoneSetting().getMorningEndTime()));
					break;
				case AFTERNOON:
					timeZonesInPreTimeSet.stream().forEach(
							x -> x.updateStartTime(preTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime()));
					break;
				}

				timeZones = timeZonesInPreTimeSet;
			}

		}
		return timeZones;
	}

	public void commonProcessAtStartup(String companyID, String employeeID, GeneralDate refDate, GeneralDate recDate,
			String recWkTypeCD, String recWkTimeCD, GeneralDate absDate, String absWkTypeCD, String absWkTimeCD,
			HolidayShipmentDto output, AppCommonSettingOutput appSetOutput) {
		// アルゴリズム「振休振出申請設定の取得」を実行する
		setDrawReqSet(output);

		// アルゴリズム「振休振出申請定型理由の取得」を実行する

//		output.setAppReasonComboItems(appResonRepo.getReasonByAppType(companyID, APP_TYPE.value).stream()
//				.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList()));

		// アルゴリズム「基準日別設定の取得」を実行する
		setDateSpecificSetting(companyID, employeeID, refDate, false, recWkTypeCD, recWkTimeCD, absWkTypeCD,
				absWkTimeCD, appSetOutput, output);
		// アルゴリズム「実績の取得」を実行する
		getAchievement(companyID, employeeID, recDate);
		// アルゴリズム「実績の取得」を実行する
		getAchievement(companyID, employeeID, absDate);

	}

	private void setDrawReqSet(HolidayShipmentDto output) {
		Optional<WithDrawalReqSet> withDrawalReqSetOpt = withDrawRepo.getWithDrawalReqSet();
		if (withDrawalReqSetOpt.isPresent()) {
			output.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSetOpt.get()));
		}

	}

	public AchievementOutput getAchievement(String companyID, String employeeID, GeneralDate appDate) {
		// アルゴリズム「実績取得」を実行する
		/*if (appDate != null) {
			return collectAchievement.getAchievement(companyID, employeeID, appDate);
		}*/
		return null;

	}
	/**
	 * 基準日別設定の取得
	 * @param companyID
	 * @param employeeID
	 * @param refDate
	 * @param isGetSetting
	 * @param recWkTypeCD
	 * @param recWkTimeCD
	 * @param absWkTypeCD
	 * @param absWkTimeCD
	 * @param appCommonSet
	 * @param output
	 */

	public void setDateSpecificSetting(String companyID, String employeeID, GeneralDate refDate, boolean isGetSetting,
			String recWkTypeCD, String recWkTimeCD, String absWkTypeCD, String absWkTimeCD,
			AppCommonSettingOutput appCommonSet, HolidayShipmentDto output) {
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wkPlaceAdapter.getEmpHistBySid(companyID, employeeID, refDate);
		// アルゴリズム「所属職場を含む上位職場を取得」を実行する
		
		// [No.571]職場の上位職場を基準職場を含めて取得する
		// List<String> workpaceIds = empAdaptor.getWorkplaceIdAndUpper(companyID, refDate, workplaceId);

		if (empImpOpt.isPresent()) {

			String employmentCD = empImpOpt.get().getEmploymentCode();

			if (isGetSetting) {
				// INPUT.設定取得＝true
				// アルゴリズム「雇用別申請承認設定の取得」を実行するz
				setAppEmploymentSettings(appCommonSet, employmentCD, output);

				// アルゴリズム「申請承認機能設定の取得」を実行する
				setApprovalFunctionSetting(employeeID, refDate, output, companyID);

			}
			// アルゴリズム「振出用勤務種類の取得」を実行する
			WorkTypeKAF011 wkTypeRec = this.getWorkTypeFor(companyID, employmentCD, recWkTypeCD, appCommonSet, BreakOutType.WORKING_DAY);
			output.setMasterUnregRec(wkTypeRec.isMasterUnreg());
			output.setRecWkTypes(wkTypeRec.getLstWorkType().stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList()));

			// アルゴリズム「選択済の就業時間帯の取得」を実行する rec
			setWkTimeInfoForRecApp(companyID, recWkTimeCD, output);

			// アルゴリズム「選択済の就業時間帯の取得」を実行する abs			
			WorkTypeKAF011 wkTypeAbs = this.getWorkTypeFor(companyID, employmentCD, absWkTimeCD, appCommonSet, BreakOutType.HOLIDAY);
			output.setMasterUnregAbs(wkTypeAbs.isMasterUnreg());
			output.setAbsWkTypes(wkTypeAbs.getLstWorkType().stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList()));
			// アルゴリズム「振休用勤務種類の取得」を実行する
			setWkTimeInfoForAbsApp(companyID, absWkTimeCD, output);

		}

	}

	private void setWkTimeInfoForAbsApp(String companyID, String WkTimeCD, HolidayShipmentDto output) {
		AbsenceLeaveAppDto absAppOutPut = output.getAbsApp();
		if (absAppOutPut != null) {
			setSelectedWkTimeInfo(companyID, WkTimeCD, absAppOutPut);
		}

	}

	private void setWkTimeInfoForRecApp(String companyID, String WkTimeCD, HolidayShipmentDto output) {
		RecruitmentAppDto recAppOutPut = output.getRecApp();
		if (recAppOutPut != null) {
			setSelectedWkTimeInfo(companyID, WkTimeCD, recAppOutPut);
		}
	}

	private void setApprovalFunctionSetting(String employeeID, GeneralDate refDate, HolidayShipmentDto output,
			String companyID) {
		List<String> workPlaceIds = empAdaptor.findWpkIdsBySid(companyID, employeeID, refDate);
//		if (!CollectionUtil.isEmpty(workPlaceIds)) {
//			output.setApprovalFunctionSetting(
//					ApprovalFunctionSettingDto.convertToDto(AcApprovalFuncSet(companyID, workPlaceIds)));
//		}
	}

	private void setAppEmploymentSettings(AppCommonSettingOutput appCommonSet, String employmentCD,
			HolidayShipmentDto output) {

		Optional<AppEmploymentSetting> appSetOpt = appCommonSet.appEmploymentWorkType;
		if (appSetOpt.isPresent()) {
			addAppSetToList(appSetOpt.get(), output);
		}

	}

	private void addAppSetToList(AppEmploymentSetting appSet, HolidayShipmentDto output) {

		boolean isAppSetsNotEmpty = !CollectionUtil.isEmpty(output.getAppEmploymentSettings());

		if (isAppSetsNotEmpty) {

			output.getAppEmploymentSettings().add(AppEmploymentSettingDto.fromDomain(appSet));

		} else {

			List<AppEmploymentSettingDto> newAppSets = new ArrayList<AppEmploymentSettingDto>();

			newAppSets.add(AppEmploymentSettingDto.fromDomain(appSet));

			output.setAppEmploymentSettings(newAppSets);

		}

	}

	private void setSelectedWkTimeInfo(String companyID, String wkTimeCode, HolidayShipmentAppDto recAppOutPut) {
		// アルゴリズム「就業時間帯表示情報（単体）の取得」を実行する
		if (wkTimeCode != null && recAppOutPut != null) {
			boolean isGetHiddenItems = true;
			setWkTimeZoneDisplayInfo(companyID, wkTimeCode, isGetHiddenItems, recAppOutPut);
		}

	}

	private void setWkTimeZoneDisplayInfo(String companyID, String wkTimeCode, boolean isGetHiddenItems,
			HolidayShipmentAppDto recAppOutPut) {
		if (isGetHiddenItems && recAppOutPut != null) {
			Optional<WorkTimeSetting> wkTimeOpt = this.wkTimeSetRepo.findByCode(companyID, wkTimeCode);

			if (wkTimeOpt.isPresent()) {

				recAppOutPut.updateFromWkTimeSet(wkTimeOpt.get());

				wkTimeCode = wkTimeOpt.get().getWorktimeCode().v();

				Optional<PredetemineTimeSetting> preTimeSetOpt = preTimeSetRepo.findByWorkTimeCode(companyID,
						wkTimeCode);
				if (preTimeSetOpt.isPresent()) {
					recAppOutPut.updateFromPreTimeSet(preTimeSetOpt.get());
					recAppOutPut.setWorkTimeName(wkTimeOpt.get().getWorkTimeDisplayName().getWorkTimeName().v());
				}
			}
		}

	}

//	private ApprovalFunctionSetting AcApprovalFuncSet(String companyID, List<String> wpkIds) {
//		ApprovalFunctionSetting result = null;
//		for (String wpID : wpkIds) {
//			Optional<ApprovalFunctionSetting> wpOpt = requestWpRepo.getFunctionSetting(companyID, wpID, APP_TYPE.value);
//			if (wpOpt.isPresent()) {
//				result = wpOpt.get();
//			}
//		}
//		// 職場別設定なし
//		Optional<ApprovalFunctionSetting> comOpt = requestComRepo.getFunctionSetting(companyID, APP_TYPE.value);
//		if (comOpt.isPresent()) {
//			result = comOpt.get();
//		}
//		return result;
//
//	}
	/**
	 * 振出用勤務種類の取得
	 * @param companyID
	 * @param employmentCode
	 * @param wkTypeCD
	 * @param appCommonSet
	 * @param workType
	 * @return
	 */
	private WorkTypeKAF011 getWorkTypeFor(String companyID, String employmentCode, String wkTypeCD,
			AppCommonSettingOutput appCommonSet, BreakOutType workType) {
		WorkTypeKAF011 result = new WorkTypeKAF011();
		List<WorkType> unfilteredWkTypes;
		boolean isWorkTypeIsHoliday = workType.equals(BreakOutType.HOLIDAY);
		if (isWorkTypeIsHoliday) {
			// ドメインモデル「勤務種類」を取得する
			unfilteredWkTypes = wkTypeRepo.findWorkTypeForPause(companyID);
		} else {
			// ドメインモデル「勤務種類」を取得する
			unfilteredWkTypes = wkTypeRepo.findWorkTypeForShorting(companyID);
		}
		// アルゴリズム「対象勤務種類の抽出」を実行する
		List<WorkType> outputWkTypes = extractTargetWkTypes(companyID, employmentCode, workType.value,
				unfilteredWkTypes, appCommonSet);

		boolean isWkTypeCDNotNullOrEmpty = !StringUtils.isEmpty(wkTypeCD);
		if (isWkTypeCDNotNullOrEmpty) {
			// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する
			boolean masterUnreg = commonAlgorithm.appliedWorkType(companyID, outputWkTypes, wkTypeCD).isMasterUnregister();
			if(masterUnreg){
				result.setMasterUnreg(masterUnreg);
			}
		}
		// sort by CD
		List<WorkType> disOrderList = outputWkTypes.stream().filter(w -> w.getDispOrder() != null)
				.sorted(Comparator.comparing(WorkType::getDispOrder)).collect(Collectors.toList());

		List<WorkType> wkTypeCDList = outputWkTypes.stream().filter(w -> w.getDispOrder() == null)
				.sorted(Comparator.comparing(WorkType::getWorkTypeCode)).collect(Collectors.toList());

		disOrderList.addAll(wkTypeCDList);
		result.setLstWorkType(disOrderList);
		return result;

	}

	private List<WorkType> extractTargetWkTypes(String companyID, String employmentCode, int breakOutType,
			List<WorkType> wkTypes, AppCommonSettingOutput appCommonSet) {
		// ドメインモデル「申請別対象勤務種類」を取得する
		Optional<AppEmploymentSetting> empSetOpt = appCommonSet.appEmploymentWorkType;
		
		if (empSetOpt.isPresent()) {
			AppEmploymentSetting appEmploymentSetting = empSetOpt.get();
			List<WorkTypeObjAppHoliday> workTypeObjAppHolidayList = appEmploymentSetting.getListWTOAH();
			if(!CollectionUtil.isEmpty(empSetOpt.get().getListWTOAH())) {
				WorkTypeObjAppHoliday item = workTypeObjAppHolidayList.stream().filter(x -> x.getSwingOutAtr().isPresent() ? x.getSwingOutAtr().get().value == breakOutType : false).findFirst().get();
				List<AppEmployWorkType> lstEmploymentWorkType = CollectionUtil.isEmpty(item.getWorkTypeList()) ? null :
						item.getWorkTypeList().stream().map(x -> new AppEmployWorkType(companyID, employmentCode, appEmploymentSetting.getListWTOAH().get(0).getAppType(),
								appEmploymentSetting.getListWTOAH().get(0).getAppType().value == 10 ? appEmploymentSetting.getListWTOAH().get(0).getSwingOutAtr().get().value 
										: (appEmploymentSetting.getListWTOAH().get(0).getAppType().value == 1 ? appEmploymentSetting.getListWTOAH().get(0).getHolidayAppType().get().value : 9), x))
						.collect(Collectors.toList());
					if(lstEmploymentWorkType !=null) {
						
						return wkTypes.stream()
								.filter(x -> lstEmploymentWorkType.stream()
										.filter(y -> y.getWorkTypeCode().equals(x.getWorkTypeCode().v())).findFirst()
										.isPresent())
								.collect(Collectors.toList());
					}
			}
			
		} 
		return wkTypes;

	}

	public HolidayShipmentDto commonProcessBeforeStart(ApplicationType appType, String companyID, String employeeID,
			GeneralDate baseDate, AppCommonSettingOutput appCommonSettingOutput) {
		HolidayShipmentDto result = new HolidayShipmentDto();

		result.setEmployeeID(employeeID);

		result.setEmployeeName(empAdaptor.getEmployeeName(employeeID));

		result.setRefDate(appCommonSettingOutput.generalDate);

		result.setApplicationSetting(ApplicationSettingDto.convertToDto(appCommonSettingOutput.applicationSetting));

		setAppTypeSet(result, appType.value, companyID);

		result.setManualSendMailAtr(
				appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value == 1 ? true : false);
//		result.setSendMailWhenApprovalFlg(
//				appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenApprovalFlg().value == 1 ? true
//						: false);
//		result.setSendMailWhenRegisterFlg(
//				appCommonSettingOutput.appTypeDiscreteSettings.get(0).getSendMailWhenRegisterFlg().value == 1 ? true
//						: false);
		startupErrorCheck(employeeID, baseDate, companyID);

		return result;
	}

	private void startupErrorCheck(String employeeID, GeneralDate baseDate, String companyID) {
		// // アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		// ApprovalRootPattern approvalRootPattern =
		// collectApprovalRootPatternService.getApprovalRootPatternService(
		// companyID, employeeID, EmploymentRootAtr.APPLICATION, appType,
		// appCommonSettingOutput.generalDate, "",
		// true);
		// // アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する
		// startupErrorCheckService.startupErrorCheck(appCommonSettingOutput.generalDate,
		// appType.value,
		// approvalRootPattern.getApprovalRootContentImport());
		// アルゴリズム「振休管理チェック」を実行する
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wkPlaceAdapter.getEmpHistBySid(companyID, employeeID, baseDate);
		if (empImpOpt.isPresent()) {
			// アルゴリズム「振休管理設定の取得」を実行する
			EmploymentHistoryImported empImp = empImpOpt.get();
			String emptCD = empImp.getEmploymentCode();
			Optional<EmpSubstVacation> empSubOpt = empSubrepo.findById(companyID, emptCD);
			if (empSubOpt.isPresent()) {

				EmpSubstVacation empSub = empSubOpt.get();
				boolean isNotManage = empSub.getSetting().getIsManage().equals(ManageDistinct.NO);
				if (isNotManage) {
					throw new BusinessException("Msg_323");
				}
			} else {
				Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(companyID);
				boolean isNoComSubOrNotManage = comSubOpt.isPresent()
						&& comSubOpt.get().getSetting().getIsManage().equals(ManageDistinct.NO);
				if (isNoComSubOrNotManage) {
					throw new BusinessException("Msg_323");
				}

			}
		}

	}

	public void setAppTypeSet(HolidayShipmentDto result, int appType, String companyID) {
		Optional<RequestSetting> reqSetOpt = reqSetRepo.findByCompany(companyID);
		if (reqSetOpt.isPresent()) {
			RequestSetting reqSet = reqSetOpt.get();
			Optional<AppTypeSetDto> appTypeSetDtoOpt = AppTypeSetDto.convertToDto(reqSet).stream()
					.filter(x -> x.getAppType().equals(appType)).findFirst();

			if (appTypeSetDtoOpt.isPresent()) {
				result.setAppTypeSet(appTypeSetDtoOpt.get());
			}

		}

	}
	
	// 1.振休振出申請（新規）起動処理
	public DisplayInforWhenStarting startPageARefactor(String companyId, List<String> lstEmployee, List<GeneralDate> dateLst) {
		DisplayInforWhenStarting result = new DisplayInforWhenStarting();
		// error EA refactor 4
		/*// 起動時の申請表示情報を取得する (Lấy thông tin hiển thị Application khi  khởi động)
		// error EA refactor 4
		// AppDispInfoStartupOutput_Old appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart(companyId, ApplicationType_Old.COMPLEMENT_LEAVE_APPLICATION, lstEmployee, dateLst,true);
		result.setAppDispInfoStartup(AppDispInfoStartupDto_Old.fromDomain(appDispInfoStartupOutput));
		
		//振休管理チェック (Check quản lý nghỉ bù)
		this.startupErrorCheck(lstEmployee.get(0), appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(), companyId);
		
		// 振休振出申請設定の取得
		WithDrawalReqSet withDrawalReqSet = this.getWithDrawalReqSet(companyId);
		result.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSet));
		
		//1.振出申請（新規）起動処理(申請対象日関係あり)(Xử lý khời động Application làm bù (New )(có liên quan application ngày đối tượng )
		DisplayInformationApplication applicationForWorkingDay = this.applicationForWorkingDay(
																		companyId,
																		appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
																		appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(),
																		appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(),
																		appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getWorkTimeLst()
																		);
		result.setApplicationForWorkingDay(applicationForWorkingDay);
		
		//1.振休申請（新規）起動処理(申請対象日関係あり)(Xử lý khời động Application nghỉ bù (New )(có liên quan application ngày đối tượng )
		DisplayInformationApplication applicationForHoliday = this.applicationForHoliday(companyId, appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode());
		result.setApplicationForHoliday(applicationForHoliday);
		
		//[No.506]振休残数を取得する ([No.506]Lấy số ngày nghỉ bù còn lại)
		AbsRecRemainMngOfInPeriod absRecMngRemain = absRertMngInPeriod.getAbsRecMngRemain(lstEmployee.get(0), GeneralDate.today());
		
		result.setRemainingHolidayInfor(new RemainingHolidayInfor(absRecMngRemain));*/
		
		return result;
	}
	
	//1.振出申請（新規）起動処理(申請対象日関係あり)
	public DisplayInformationApplication applicationForWorkingDay(String companyId, String employeeId, GeneralDate baseDate, String employmentCode, List<WorkTimeSetting> workTimeLst) {
	
		DisplayInformationApplication result = new DisplayInformationApplication();
		
		//社員の労働条件を取得する(Lấy điều kiện lao động của employee)
		// Optional<WorkingConditionItem> workingConditionItem = workingConditionService.findWorkConditionByEmployee(employeeId, baseDate);
		Optional<WorkingConditionItem> workingConditionItem = null;
		if(workingConditionItem.isPresent()) {
			result.setSelectionWorkTime(workingConditionItem.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().map(x -> x.v()).orElse(null));
		}else {
			result.setSelectionWorkTime("");
			//12.マスタ勤務種類、就業時間帯データをチェック
	        CheckWorkingInfoResult checkResult = otherCommonAlgorithm.checkWorkingInfo(companyId, null, result.getSelectionWorkTime());
	        //「職場別就業時間帯」を取得した先頭値を表示
	        if(checkResult.isWkTimeError() && !workTimeLst.isEmpty()){
	        	result.setSelectionWorkTime(workTimeLst.get(0).getWorktimeCode().v());
	        }
		}
		
		//振出用勤務種類の取得(Lấy worktype của làm bù)
		List<WorkType> workTypeForWorkingDay = this.getWorkTypeForWorkingDay(companyId, employmentCode, null);
		
		//振出申請起動時の表示情報．勤務種類リスト=取得した振出用勤務種類(List)//(DisplayInfo khi khởi động đơn xin làm bu. WorktypeList= worktype của làm bù đã lấy(List))
		result.setWorkTypeList(workTypeForWorkingDay.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
		
		if(!workTypeForWorkingDay.isEmpty()) {
			//振出申請起動時の表示情報．初期選択勤務種類=取得した振出用勤務種類(List)の先頭の勤務種類 /(DisplayInfo khi khởi động đơn xin làm bù. InitialSelectionWorkType= worktype đầu tiên của worktype làm bù(list) đã lấy)
			result.setSelectionWorkType(workTypeForWorkingDay.get(0).getWorkTypeCode().v());
		}
		//勤務時間初期値の取得(lấy giá trị khởi tạo worktime)
		PrescribedTimezoneSetting prescribedTimezoneSetting = appAbsenceFinder.initWorktimeCode(companyId, result.getSelectionWorkType(), result.getSelectionWorkTime());
		if(prescribedTimezoneSetting != null) {
			for (TimezoneUse time : prescribedTimezoneSetting.getLstTimezone()) {
				if(time.getWorkNo() == 1) {
					//振出申請起動時の表示情報．開始時刻=取得した時間帯(使用区分付き)．開始 (DisplayInfo khi khởi động đơn xin làm bù. StartTime= TimeSheet with UseAtr. StartTime đã lấy)
					result.setStartTime(time.getStart().v());
					//振出申請起動時の表示情報．終了時刻=取得した時間帯(使用区分付き)．終了(DisplayInfo khi khởi động đơn xin làm bù. EndTime= TimeSheet withUseAtr. EndTime đã lấy)
					result.setEndTime(time.getEnd().v());
				}
				if(time.getWorkNo() == 2) {
					//振出申請起動時の表示情報．開始時刻=取得した時間帯(使用区分付き)．開始 (DisplayInfo khi khởi động đơn xin làm bù. StartTime= TimeSheet with UseAtr. StartTime đã lấy)
					result.setStartTime2(time.getStart().v());
					//振出申請起動時の表示情報．終了時刻=取得した時間帯(使用区分付き)．終了(DisplayInfo khi khởi động đơn xin làm bù. EndTime= TimeSheet withUseAtr. EndTime đã lấy)
					result.setEndTime2(time.getEnd().v());
				}
			}
		}
		return result;
	}
	
	//振出用勤務種類の取得
	public List<WorkType> getWorkTypeForWorkingDay(String companyId, String employmentCode, String wkTypeCD) {
		List<WorkType> result = wkTypeRepo.findWorkTypeForShorting(companyId);
		
		// アルゴリズム「対象勤務種類の抽出」を実行する
		List<WorkType> outputWkTypes = this.extractTargetWkTypes(companyId, employmentCode, BreakOutType.WORKING_DAY.value, result);

		boolean isWkTypeCDNotNullOrEmpty = !StringUtils.isEmpty(wkTypeCD);
		if (isWkTypeCDNotNullOrEmpty ) {
			// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する
			ApplyWorkTypeOutput applyWorkType = commonAlgorithm.appliedWorkType(companyId, outputWkTypes, wkTypeCD);
			return applyWorkType.getWkTypes();
		}
		// sort by CD
		List<WorkType> disOrderList = outputWkTypes.stream().filter(w -> w.getDispOrder() != null)
				.sorted(Comparator.comparing(WorkType::getDispOrder)).collect(Collectors.toList());

		List<WorkType> wkTypeCDList = outputWkTypes.stream().filter(w -> w.getDispOrder() == null)
				.sorted(Comparator.comparing(WorkType::getWorkTypeCode)).collect(Collectors.toList());

		disOrderList.addAll(wkTypeCDList);
		return disOrderList;

	}

	//アルゴリズム「対象勤務種類の抽出」を実行する(Thực hiện thuật toán [trích xuất worktype])
	public List<WorkType> extractTargetWkTypes(String companyID, String employmentCode, int breakOutType, List<WorkType> wkTypes) {
		// ドメインモデル「申請別対象勤務種類」を取得する
		// AppEmploymentRepository change return method to Optional<AppEmploymentSetting>
		Optional<AppEmploymentSetting> empSetOpt = appEmploymentSetting.getEmploymentSetting(companyID, employmentCode, ApplicationType.COMPLEMENT_LEAVE_APPLICATION.value);
		if (empSetOpt.isPresent()) {
			AppEmploymentSetting appEmploymentSetting = empSetOpt.get();
			List<WorkTypeObjAppHoliday> workTypeObjAppHolidayList = appEmploymentSetting.getListWTOAH();
			if(!CollectionUtil.isEmpty(empSetOpt.get().getListWTOAH())) {
				WorkTypeObjAppHoliday item = workTypeObjAppHolidayList.stream().filter(x -> x.getSwingOutAtr().isPresent() ? x.getSwingOutAtr().get().value == breakOutType : false).findFirst().get();
				List<AppEmployWorkType> lstEmploymentWorkType = CollectionUtil.isEmpty(item.getWorkTypeList()) ? null :
						item.getWorkTypeList().stream().map(x -> new AppEmployWorkType(companyID, employmentCode, appEmploymentSetting.getListWTOAH().get(0).getAppType(),
								appEmploymentSetting.getListWTOAH().get(0).getAppType().value == 10 ? appEmploymentSetting.getListWTOAH().get(0).getSwingOutAtr().get().value : appEmploymentSetting.getListWTOAH().get(0).getAppType().value == 1 ? appEmploymentSetting.getListWTOAH().get(0).getHolidayAppType().get().value : 9, x))
						.collect(Collectors.toList());
					if(lstEmploymentWorkType !=null) {
						
						return wkTypes.stream()
								.filter(x -> lstEmploymentWorkType.stream()
										.filter(y -> y.getWorkTypeCode().equals(x.getWorkTypeCode().v())).findFirst()
										.isPresent())
								.collect(Collectors.toList());
					}
			}
			
		} else {
			return wkTypes;
		}
		return wkTypes;
	
	}

	//1.振休申請（新規）起動処理(申請対象日関係あり)
	public DisplayInformationApplication applicationForHoliday(String companyId, String employmentCode) {
		DisplayInformationApplication result = new DisplayInformationApplication();
		//振休用勤務種類の取得(Lấy worktype nghỉ bù)
		List<WorkType> workTypeForHoliday = this.getWorkTypeForHoliday(companyId, employmentCode, null);
		
		result.setWorkTypeList(workTypeForHoliday.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
		
		return result;
	}
	
	//振出用勤務種類の取得
	public List<WorkType> getWorkTypeForHoliday(String companyId, String employmentCode, String wkTypeCD) {
		List<WorkType> result = wkTypeRepo.findWorkTypeForPause(companyId);
		
		// アルゴリズム「対象勤務種類の抽出」を実行する
		List<WorkType> outputWkTypes = this.extractTargetWkTypes(companyId, employmentCode, BreakOutType.HOLIDAY.value, result);

		boolean isWkTypeCDNotNullOrEmpty = !StringUtils.isEmpty(wkTypeCD);
		if (isWkTypeCDNotNullOrEmpty ) {
			// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する
			ApplyWorkTypeOutput applyWorkType = commonAlgorithm.appliedWorkType(companyId, outputWkTypes, wkTypeCD);
			return applyWorkType.getWkTypes();
		}
		// sort by CD
		List<WorkType> disOrderList = outputWkTypes.stream().filter(w -> w.getDispOrder() != null)
				.sorted(Comparator.comparing(WorkType::getDispOrder)).collect(Collectors.toList());

		List<WorkType> wkTypeCDList = outputWkTypes.stream().filter(w -> w.getDispOrder() == null)
				.sorted(Comparator.comparing(WorkType::getWorkTypeCode)).collect(Collectors.toList());

		disOrderList.addAll(wkTypeCDList);
		return disOrderList;

	}
	
	/**
	 * 振休振出申請設定の取得
	 * @param companyID 会社ID
	 * @return
	 */
	public WithDrawalReqSet getWithDrawalReqSet(String companyID) {
		// ドメインモデル「振休振出申請設定」を取得する
		WithDrawalReqSet withDrawalReqSet = withDrawRepo.getWithDrawalReqSet().get();
		// 取得した情報を返す
		return withDrawalReqSet;
	}
}
