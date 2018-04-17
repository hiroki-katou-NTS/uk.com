package nts.uk.ctx.at.request.app.find.application.holidayshipment;

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
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeSetDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.WorkTimeInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.ApplicationMetaOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationCombination;
import nts.uk.ctx.at.request.dom.application.holidayshipment.BreakOutType;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
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
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	@Inject
	private WorkplaceAdapter wkPlaceAdapter;
	@Inject
	private EmpSubstVacationRepository empSubrepo;
	@Inject
	private ComSubstVacationRepository comSubrepo;
	@Inject
	private PersonalLaborConditionRepository perLaborConRepo;
	@Inject
	private WithDrawalReqSetRepository withDrawRepo;
	@Inject
	private ApplicationReasonRepository appResonRepo;
	@Inject
	private EmployeeRequestAdapter empAdaptor;
	@Inject
	private WorkTypeRepository wkTypeRepo;
	@Inject
	private WorkTimeSettingRepository wkTimeRepo;
	@Inject
	private PredetemineTimeSettingRepository preTimeSetRepo;
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private ApprovalRootAdapter rootAdapter;
	@Inject
	private RequestOfEachWorkplaceRepository requestWpRepo;
	@Inject
	private RequestOfEachCompanyRepository requestComRepo;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private BasicScheduleService basicService;
	@Inject
	private RequestSettingRepository reqSetRepo;
	@Inject
	private WorkTimeSettingRepository wkTimeSetRepo;
	ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;
	AppCommonSettingOutput appCommonSettingOutput;
	RecruitmentApp recApp;
	AbsenceLeaveApp absApp;
	ApplicationMetaOutput recAppOutput;
	ApplicationMetaOutput absAppOutput;
	String companyID;
	HolidayShipmentDto output;

	/**
	 * start event
	 * 
	 * @param employeeID
	 * @param initDateInput
	 * @param uiType
	 * @return HolidayShipmentDto
	 */
	// Screen A Start
	public HolidayShipmentDto startPageA(String employeeID, GeneralDate initDate, int uiType) {
		employeeID = employeeID == null ? AppContexts.user().employeeId() : employeeID;
		String companyID = AppContexts.user().companyId();
		// アルゴリズム「起動前共通処理（新規）」を実行する
		HolidayShipmentDto result = commonProcessBeforeStart(appType, companyID, employeeID, initDate);

		GeneralDate refDate = result.getRefDate();

		// アルゴリズム「事前事後区分の判断」を実行する
		result.setPreOrPostType(
				otherCommonAlgorithm.judgmentPrePostAtr(appType, refDate, uiType == 0 ? true : false).value);

		// アルゴリズム「平日時就業時間帯の取得」を実行する
		PersonalLaborCondition perLaborCondition = getWorkingHourOnWeekDays(employeeID, refDate);

		String wkTimeCD = getWkTimeCD(perLaborCondition);

		GeneralDate appDate, deadDate;

		String takingOutWkTypeCD, takingOutWkTimeCD, holiDayWkTypeCD, holidayWkTimeCD;

		appDate = deadDate = null;

		takingOutWkTypeCD = takingOutWkTimeCD = holiDayWkTypeCD = holidayWkTimeCD = null;

		// アルゴリズム「振休振出申請起動時の共通処理」を実行する
		commonProcessAtStartup(companyID, employeeID, refDate, appDate, takingOutWkTypeCD, takingOutWkTimeCD, deadDate,
				holiDayWkTypeCD, holidayWkTimeCD, result, appCommonSettingOutput);
		// アルゴリズム「勤務時間初期値の取得」を実行する
		if (wkTimeCD != null) {
			String wkTypeCD = result.getRecWkTypes().size() > 0 ? result.getRecWkTypes().get(0).getWorkTypeCode() : "";
			if (StringUtils.isNoneEmpty(wkTypeCD)) {
				result.setWorkTimeInfo(getWkTimeInfoInitValue(companyID, wkTypeCD, wkTimeCD));
			}

		}

		return result;
	}

	public WorkTimeInfoDto changeWorkType(String workTypeCD, String wkTimeCD) {
		String companyID = AppContexts.user().companyId();
		return getWkTimeInfoInitValue(companyID, workTypeCD, wkTimeCD);

	}

	public HolidayShipmentDto changeAppDate(GeneralDate recDate, GeneralDate absDate, int comType, int uiType) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		GeneralDate baseDate = comType == ApplicationCombination.Abs.value ? absDate : recDate;
		// アルゴリズム「実績の取得」を実行する
		HolidayShipmentDto output = commonProcessBeforeStart(appType, companyID, employeeID, baseDate);
		// AchievementOutput achievementOutput = getAchievement(companyID,
		// employeeID, baseDate);

		setChangeAppDateData(recDate, absDate, companyID, employeeID, uiType, output);

		return output;
	}

	private String getWkTimeCD(PersonalLaborCondition perLaborCondition) {
		if (perLaborCondition != null) {
			Optional<WorkTimeCode> wkTimeCodeOpt = perLaborCondition.getWorkCategory().getWeekdayTime()
					.getWorkTimeCode();

			if (wkTimeCodeOpt.isPresent()) {

				return wkTimeCodeOpt.get().v();

			} else {

				return "";
			}

		} else {
			return "";
		}
	}

	private void setChangeAppDateData(GeneralDate recDate, GeneralDate absDate, String companyID, String employeeID,
			int uiType, HolidayShipmentDto output) {
		// アルゴリズム「基準申請日の決定」を実行する
		GeneralDate referenceDate = DetRefDate(recDate, absDate);
		int rootAtr = EmploymentRootAtr.APPLICATION.value;
		AppCommonSettingOutput appCommonSet = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID, rootAtr, ApplicationType.BREAK_TIME_APPLICATION, referenceDate);

		ApplicationSetting appSet = appCommonSet.applicationSetting;
		// 承認ルート基準日をチェックする
		GeneralDate inputDate;
		if (!appSet.getBaseDateFlg().equals(BaseDateFlg.APP_DATE)) {
			// アルゴリズム「社員の対象申請の承認ルートを取得する」を実行する
			inputDate = referenceDate;
		} else {
			inputDate = GeneralDate.today();
		}
		List<ApprovalRootImport> approvalRoots = rootAdapter.getApprovalRootOfSubjectRequest(companyID, employeeID,
				rootAtr, appType.value, referenceDate);

		// アルゴリズム「基準日別設定の取得」を実行する
		getDateSpecificSetting(companyID, employeeID, inputDate, true, null, null, null, null, appCommonSet, output);
		// アルゴリズム「事前事後区分の最新化」を実行する
		output.setPreOrPostType(
				otherCommonAlgorithm.judgmentPrePostAtr(appType, referenceDate, uiType == 0 ? true : false).value);

		output.setRefDate(inputDate);

	}

	public static GeneralDate DetRefDate(GeneralDate recDate, GeneralDate absDate) {

		if (absDate != null && recDate != null) {

			if (recDate.after(absDate)) {

				return absDate;
			} else {
				return recDate;

			}
		} else {
			if (recDate != null) {
				return recDate;
			}
			if (absDate != null) {
				return absDate;
			}

		}
		return null;

	}

	private WorkTimeInfoDto getWkTimeInfoInitValue(String companyID, String wkTypeCode, String wkTimeCode) {
		// ドメインモデル「勤務種類」を取得する
		WorkTimeInfoDto result = new WorkTimeInfoDto();

		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCode);
		if (wkTypeOpt.isPresent()) {

			WorkType wkType = wkTypeOpt.get();
			result.setWkType(WorkTypeDto.fromDomain(wkType));
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			basicService.checkWorkDay(wkTypeCode);
			AttendanceHolidayAttr wkTypeAttendance = wkType.getAttendanceHolidayAttr();

			if (!wkTypeAttendance.equals(AttendanceHolidayAttr.HOLIDAY)) {

				// アルゴリズム「所定時間帯を取得する」を実行する
				List<TimezoneUse> timeZones = getTimeZones(companyID, wkTimeCode, wkTypeAttendance);

				result.setTimezoneUseDtos(
						timeZones.stream().map(x -> TimeZoneUseDto.fromDomain(x)).collect(Collectors.toList()));

			}

		}
		return result;

	}

	@SuppressWarnings("incomplete-switch")
	private List<TimezoneUse> getTimeZones(String companyID, String wkTimeCode,
			AttendanceHolidayAttr wkTypeAttendance) {

		List<TimezoneUse> timeZones = new ArrayList<TimezoneUse>();

		Optional<WorkTimeSetting> workTimeOpt = wkTimeRepo.findByCode(companyID, wkTimeCode);

		if (workTimeOpt.isPresent()) {

			wkTimeCode = workTimeOpt.get().getWorktimeCode().v();

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
		Optional<WithDrawalReqSet> withDrawalReqSetOpt = withDrawRepo.getWithDrawalReqSet();
		if (withDrawalReqSetOpt.isPresent()) {
			output.setDrawalReqSet(WithDrawalReqSetDto.fromDomain(withDrawalReqSetOpt.get()));
		}

		// アルゴリズム「振休振出申請定型理由の取得」を実行する

		output.setAppReasonComboItems(appResonRepo.getReasonByCompanyId(companyID).stream()
				.map(x -> ApplicationReasonDto.convertToDto(x)).collect(Collectors.toList()));

		// アルゴリズム「基準日別設定の取得」を実行する
		getDateSpecificSetting(companyID, employeeID, refDate, false, recWkTypeCD, recWkTimeCD, absWkTypeCD,
				absWkTimeCD, appSetOutput, output);
		// アルゴリズム「実績の取得」を実行する
		getAchievement(companyID, employeeID, recDate);
		// アルゴリズム「実績の取得」を実行する
		getAchievement(companyID, employeeID, absDate);

	}

	private AchievementOutput getAchievement(String companyID, String employeeID, GeneralDate appDate) {
		// アルゴリズム「実績取得」を実行する
		if (appDate != null) {
			return collectAchievement.getAchievement(companyID, employeeID, appDate);
		}
		return null;

	}

	public void getDateSpecificSetting(String companyID, String employeeID, GeneralDate refDate, boolean getSetting,
			String recWkTypeCD, String recWkTimeCode, String absWkTypeCD, String absWkTimeCode,
			AppCommonSettingOutput appCommonSet, HolidayShipmentDto output) {
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wkPlaceAdapter.getEmpHistBySid(companyID, employeeID, refDate);
		// アルゴリズム「所属職場を含む上位職場を取得」を実行する
		List<String> wpkIds = empAdaptor.findWpkIdsBySid(companyID, employeeID, refDate);
		if (empImpOpt.isPresent()) {
			String employmentCD = empImpOpt.get().getEmploymentCode();

			if (getSetting) {
				// INPUT.設定取得＝true
				// アルゴリズム「雇用別申請承認設定の取得」を実行するz
				Optional<AppEmploymentSetting> appEmploymentSettingOpt = appCommonSet.appEmploymentWorkType.stream()
						.filter(x -> x.getEmploymentCode().equals(employmentCD)).findFirst();
				if (appEmploymentSettingOpt.isPresent()) {
					if (!CollectionUtil.isEmpty(output.getAppEmploymentSettings())) {
						output.getAppEmploymentSettings()
								.add(AppEmploymentSettingDto.fromDomain(appEmploymentSettingOpt.get()));
					} else {
						List<AppEmploymentSettingDto> appEmploymentSettings = new ArrayList<AppEmploymentSettingDto>();
						appEmploymentSettings.add(AppEmploymentSettingDto.fromDomain(appEmploymentSettingOpt.get()));
						output.setAppEmploymentSettings(appEmploymentSettings);

					}
				}
				// アルゴリズム「申請承認機能設定の取得」を実行する
				if (!CollectionUtil.isEmpty(wpkIds)) {
					output.setApprovalFunctionSetting(
							ApprovalFunctionSettingDto.convertToDto(AcApprovalFuncSet(companyID, wpkIds)));
				}

			}
			// アルゴリズム「振出用勤務種類の取得」を実行する
			output.setRecWkTypes(
					getWorkTypeFor(companyID, employmentCD, recWkTypeCD, appCommonSet, BreakOutType.WORKING_DAY)
							.stream().map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList()));

			// INPUT.振出就業時間帯コード＝設定なし
			// アルゴリズム「振休用勤務種類の取得」を実行する
			output.setAbsWkTypes(
					getWorkTypeFor(companyID, employmentCD, absWkTypeCD, appCommonSet, BreakOutType.HOLIDAY).stream()
							.map(x -> WorkTypeDto.fromDomain(x)).collect(Collectors.toList()));
			// INPUT.振休就業時間帯コード＝設定なし
		}

		// アルゴリズム「選択済の就業時間帯の取得」を実行する rec

		setSelectedWkHourInfo(companyID, recWkTimeCode, output.getRecApp());
		// アルゴリズム「選択済の就業時間帯の取得」を実行する abs

		setSelectedWkHourInfo(companyID, absWkTimeCode, output.getAbsApp());

	}

	private void setSelectedWkHourInfo(String companyID, String wkTimeCode, HolidayShipmentAppDto appDto) {
		// アルゴリズム「就業時間帯表示情報（単体）の取得」を実行する
		if (wkTimeCode != null && appDto != null) {
			setWkTimeZoneDisplayInfo(companyID, wkTimeCode, true, appDto);
		}

	}

	private void setWkTimeZoneDisplayInfo(String companyID, String wkTimeCode, boolean isGetHiddenItems,
			HolidayShipmentAppDto appDto) {
		if (isGetHiddenItems) {
			Optional<WorkTimeSetting> wkTimeOpt = this.wkTimeSetRepo.findByCode(companyID, wkTimeCode);

			if (wkTimeOpt.isPresent()) {

				appDto.updateFromWkTimeSet(wkTimeOpt.get());

				wkTimeCode = wkTimeOpt.get().getWorktimeCode().v();

				Optional<PredetemineTimeSetting> preTimeSetOpt = preTimeSetRepo.findByWorkTimeCode(companyID,
						wkTimeCode);
				if (preTimeSetOpt.isPresent()) {
					appDto.updateFromPreTimeSet(preTimeSetOpt.get());
				}
			}
		}

	}

	private ApprovalFunctionSetting AcApprovalFuncSet(String companyID, List<String> wpkIds) {
		ApprovalFunctionSetting result = null;
		for (String wpID : wpkIds) {
			Optional<ApprovalFunctionSetting> wpOpt = requestWpRepo.getFunctionSetting(companyID, wpID, appType.value);
			if (wpOpt.isPresent()) {
				result = wpOpt.get();
			}
		}
		// 職場別設定なし
		Optional<ApprovalFunctionSetting> comOpt = requestComRepo.getFunctionSetting(companyID, appType.value);
		if (comOpt.isPresent()) {
			result = comOpt.get();
		}
		return result;

	}

	private List<WorkType> getWorkTypeFor(String companyID, String employmentCode, String wkTypeCD,
			AppCommonSettingOutput appCommonSet, BreakOutType workType) {

		List<WorkType> wkTypes;
		if (workType.equals(BreakOutType.HOLIDAY)) {
			// ドメインモデル「勤務種類」を取得する
			wkTypes = wkTypeRepo.findWorkTypeForPause(companyID);
		} else {
			// ドメインモデル「勤務種類」を取得する
			wkTypes = wkTypeRepo.findWorkTypeForShorting(companyID);
		}
		// アルゴリズム「対象勤務種類の抽出」を実行する
		List<WorkType> outputWkTypes = extractTargetWkTypes(companyID, employmentCode, workType.value, wkTypes,
				appCommonSet);
		if (!StringUtils.isEmpty(wkTypeCD)) {
			// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する
			appliedWorkType(companyID, outputWkTypes, wkTypeCD);

		}
		// sort by CD
		outputWkTypes.sort(Comparator.comparing(WorkType::getWorkTypeCode));
		return outputWkTypes;

	}

	public boolean appliedWorkType(String companyID, List<WorkType> wkTypes, String wkTypeCD) {
		boolean masterUnregistered = true;

		Optional<WorkType> WkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if (WkTypeOpt.isPresent() && !wkTypes.contains((WkTypeOpt.get()))) {
			wkTypes.add(WkTypeOpt.get());
			masterUnregistered = false;

		}
		return masterUnregistered;

	}

	private List<WorkType> extractTargetWkTypes(String companyID, String employmentCode, int breakOutType,
			List<WorkType> wkTypes, AppCommonSettingOutput appCommonSet) {
		Optional<AppEmploymentSetting> empSetOpt = appCommonSet.appEmploymentWorkType.stream()
				.filter(x -> x.getHolidayOrPauseType() == breakOutType).findFirst();
		if (empSetOpt.isPresent()) {
			AppEmploymentSetting empSet = empSetOpt.get();
			if (empSet.isDisplayFlag()) {
				return wkTypes.stream()
						.filter(x -> empSet.getLstWorkType().stream()
								.filter(y -> y.getWorkTypeCode().equals(x.getWorkTypeCode().v())).findFirst()
								.isPresent())
						.collect(Collectors.toList());
			} else {
				return wkTypes;
			}
		} else {
			return wkTypes;
		}

	}

	private PersonalLaborCondition getWorkingHourOnWeekDays(String employeeID, GeneralDate baseDate) {
		Optional<PersonalLaborCondition> perLaborConOpt = perLaborConRepo.findById(employeeID, baseDate);
		if (!perLaborConOpt.isPresent()) {
			return null;
		} else {
			return perLaborConOpt.get();
		}

	}

	public HolidayShipmentDto commonProcessBeforeStart(ApplicationType appType, String companyID, String employeeID,
			GeneralDate baseDate) {
		HolidayShipmentDto result = new HolidayShipmentDto();
		int rootAtr = 1;
		result.setEmployeeID(employeeID);
		result.setEmployeeName(empAdaptor.getEmployeeName(employeeID));
		// 1-1.新規画面起動前申請共通設定を取得する
		appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID,
				rootAtr, appType, baseDate);

		result.setRefDate(appCommonSettingOutput.generalDate);

		result.setApplicationSetting(ApplicationSettingDto.convertToDto(appCommonSettingOutput.applicationSetting));
		Optional<RequestSetting> reqSetOpt = reqSetRepo.findByCompany(companyID);
		if (reqSetOpt.isPresent()) {
			RequestSetting reqSet = reqSetOpt.get();
			Optional<AppTypeSetDto> appTypeSetDtoOpt = AppTypeSetDto.convertToDto(reqSet).stream()
					.filter(x -> x.getAppType().equals(appType.value)).findFirst();

			if (appTypeSetDtoOpt.isPresent()) {
				result.setAppTypeSet(appTypeSetDtoOpt.get());
			}

		}

		result.setManualSendMailAtr(
				appCommonSettingOutput.applicationSetting.getManualSendMailAtr().value == 1 ? true : false);
		// アルゴリズム「1-4.新規画面起動時の承認ルート取得パターン」を実行する
		ApprovalRootPattern approvalRootPattern = collectApprovalRootPatternService.getApprovalRootPatternService(
				companyID, employeeID, EmploymentRootAtr.APPLICATION, appType, appCommonSettingOutput.generalDate, "",
				true);
		// アルゴリズム「1-5.新規画面起動時のエラーチェック」を実行する
		startupErrorCheckService.startupErrorCheck(appCommonSettingOutput.generalDate, appType.value,
				approvalRootPattern.getApprovalRootContentImport());
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
				if (empSub.getSetting().getIsManage().equals(ManageDistinct.NO)) {
					throw new BusinessException("Msg_323");
				}
			} else {
				Optional<ComSubstVacation> comSubOpt = comSubrepo.findById(companyID);
				if (!comSubOpt.isPresent() || comSubOpt.get().getSetting().getIsManage().equals(ManageDistinct.NO)) {
					throw new BusinessException("Msg_323");
				}

			}
		}
		return result;
	}

}
