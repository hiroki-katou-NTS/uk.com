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
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.HolidayShipmentDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.CollectApprovalRootPatternService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.StartupErrorCheckService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.BreakOutType;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.InitValueAtr;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
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
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Sonnlb
 *
 */
@Stateless
public class HolidayShipmentFinder {

	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	@Inject
	private CollectApprovalRootPatternService collectApprovalRootPatternService;
	@Inject
	private StartupErrorCheckService startupErrorCheckService;
	@Inject
	private WorkplaceAdapter wpAdapter;
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

	final static String DATE_FORMAT = "yyyy/MM/dd";
	AppCommonSettingOutput appCommonSettingOutput;
	HolidayShipmentDto output = new HolidayShipmentDto();

	public HolidayShipmentDto getHolidayShipment(String employeeID, String initDateInput, int uiType) {
		employeeID = employeeID == null ? AppContexts.user().employeeId() : employeeID;
		String companyID = AppContexts.user().companyId();
		GeneralDate initDate = initDateInput == null ? null : GeneralDate.fromString(initDateInput, DATE_FORMAT);
		ApplicationType appType = ApplicationType.COMPLEMENT_LEAVE_APPLICATION;

		// アルゴリズム「起動前共通処理（新規）」を実行する
		commonProcessBeforeStart(appType, companyID, employeeID, initDate);

		GeneralDate refDate = output.getRefDate();

		// アルゴリズム「事前事後区分の判断」を実行する
		judgmentOfPreOrPostType(appType, refDate, uiType);
		// アルゴリズム「平日時就業時間帯の取得」を実行する
		PersonalLaborCondition perLaborCond = getWorkingHourOnWeekDays(employeeID, refDate);
		// アルゴリズム「振休振出申請起動時の共通処理」を実行する
		if (perLaborCond != null) {
			Optional<WorkTimeCode> WorkTimeCodeOpt = perLaborCond.getWorkCategory().getWeekdayTime().getWorkTimeCode();
			String wkTimeCD = WorkTimeCodeOpt.isPresent() ? WorkTimeCodeOpt.get().v() : "";
			GeneralDate appDate, deadDate;
			String takingOutWkTypeCD, wkHourCD, shiftWkTypeCD, shutOffCD;
			appDate = deadDate = null;
			takingOutWkTypeCD = wkHourCD = shiftWkTypeCD = shutOffCD = null;
			commonProcessAtStartup(companyID, employeeID, refDate, appDate, takingOutWkTypeCD, wkTimeCD, wkHourCD,
					deadDate, shiftWkTypeCD, shutOffCD);
			// アルゴリズム「勤務時間初期値の取得」を実行する
			String wkTypeCD = output.getTakingOutWkTypes().size() > 0
					? output.getTakingOutWkTypes().get(0).getWorkTypeCode() : "";
			getWkTimeInitValue(companyID, wkTypeCD, wkTimeCD);
		}

		return output;
	}

	private void judgmentOfPreOrPostType(ApplicationType appType, GeneralDate baseDate, int uiType) {
		if (appCommonSettingOutput.applicationSetting.getDisplayPrePostFlg().equals(AppDisplayAtr.DISPLAY)) {
			if (uiType == 0) {
				// メニューから起動
				output.setPreOrPostType(InitValueAtr.PRE);
			} else {
				// その他のPG（日別修正、トップページアラーム、残業指示）から起動
				output.setPreOrPostType(InitValueAtr.POST);
			}
		}
		output.setPreOrPostType(InitValueAtr.NOCHOOSE);
	}

	private WorkStyle getWkTimeInitValue(String companyID, String wkTypeCD, String wkTimeCode) {
		// ドメインモデル「勤務種類」を取得する
		WorkStyle result = null;
		Optional<WorkType> WkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if (WkTypeOpt.isPresent()) {

			WorkType wkType = WkTypeOpt.get();
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			DailyWork dailyWork = wkType.getDailyWork();
			getDayAttendance(dailyWork);
			if (!wkType.getAttendanceHolidayAttr().equals(AttendanceHolidayAttr.HOLIDAY)) {
				// アルゴリズム「所定時間帯を取得する」を実行する
				getPreTimeZone(companyID, wkTimeCode, wkType);
			}

		}
		return result;

	}

	@SuppressWarnings("incomplete-switch")
	private List<TimezoneUse> getPreTimeZone(String companyID, String wkTimeCode, WorkType wkType) {
		Optional<WorkTimeSetting> workTimeOpt = wkTimeRepo.findByCode(companyID, wkTimeCode);
		List<TimezoneUse> result = new ArrayList<TimezoneUse>();
		if (workTimeOpt.isPresent()) {
			WorkTimeSetting workTimeSet = workTimeOpt.get();
			Optional<PredetemineTimeSetting> preTimeSetOpt = preTimeSetRepo.findByWorkTimeCode(companyID,
					workTimeSet.getWorktimeCode().v());
			if (preTimeSetOpt.isPresent()) {

				PredetemineTimeSetting preTimeSet = preTimeSetOpt.get();

				List<TimezoneUse> timezones = preTimeSet.getPrescribedTimezoneSetting().getLstTimezone();
				switch (wkType.getAttendanceHolidayAttr()) {
				case MORNING:
					timezones.stream().forEach(
							x -> x.updateEndTime(preTimeSet.getPrescribedTimezoneSetting().getMorningEndTime()));
					break;
				case AFTERNOON:
					timezones.stream().forEach(
							x -> x.updateStartTime(preTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime()));
					break;
				}

				result = timezones;
			}

		}
		return result;
	}

	private WorkStyle getDayAttendance(DailyWork dailyWork) {
		WorkStyle result = null;
		switch (dailyWork.getWorkTypeUnit()) {
		case OneDay:
			if (isIncludeHoliday(dailyWork.getOneDay(), true)) {
				result = WorkStyle.ONE_DAY_REST;
			} else {
				result = WorkStyle.ONE_DAY_WORK;
			}
			break;
		case MonringAndAfternoon:
			result = getMonringAndAfternoonWork(dailyWork);
			break;
		}
		return result;
	}

	private WorkStyle getMonringAndAfternoonWork(DailyWork dailyWork) {
		WorkTypeClassification morning = dailyWork.getMorning();
		WorkTypeClassification afternoon = dailyWork.getAfternoon();
		WorkStyle result;
		if (isIncludeHoliday(morning, false)) {
			// 【休日として扱う勤務種類の分類】に含まれていない
			if (isIncludeHoliday(afternoon, false)) {
				result = WorkStyle.ONE_DAY_REST;
			} else {
				result = WorkStyle.AFTERNOON_WORK;
			}

		} else {
			// 【休日として扱う勤務種類の分類】に含まれている
			if (isIncludeHoliday(afternoon, false)) {
				result = WorkStyle.MORNING_WORK;
			} else {
				result = WorkStyle.ONE_DAY_WORK;
			}
		}

		return result;
	}

	private boolean isIncludeHoliday(WorkTypeClassification worktype, boolean isOneDay) {
		boolean result;
		switch (worktype) {
		case Holiday:
		case Pause:
		case AnnualHoliday:
		case YearlyReserved:
		case SpecialHoliday:
		case TimeDigestVacation:
		case SubstituteHoliday:
		case ContinuousWork:
		case LeaveOfAbsence:
		case Closure:
			if (isOneDay) {
				result = true;
			} else {
				switch (worktype) {
				case ContinuousWork:
				case LeaveOfAbsence:
				case Closure:
					result = false;
				default:
					result = true;
					break;
				}
			}
			break;
		default:
			result = false;
			break;
		}
		return result;

	}

	private void commonProcessAtStartup(String companyID, String employeeID, GeneralDate refDate, GeneralDate appDate,
			String takingOutWkTypeCD, String wkTimeCD, String wkHourCD, GeneralDate deadDate, String shiftWkTypeCD,
			String shutOffCD) {
		// アルゴリズム「振休振出申請設定の取得」を実行する
		withDrawRepo.getWithDrawalReqSet();
		// アルゴリズム「振休振出申請定型理由の取得」を実行する
		appResonRepo.getReasonByCompanyId(companyID);
		// アルゴリズム「基準日別設定の取得」を実行する
		getDateSpecificSetting(companyID, employeeID, refDate, false, wkTimeCD, appDate, takingOutWkTypeCD, wkHourCD,
				deadDate, shiftWkTypeCD, shutOffCD);

		getAchievement(companyID, employeeID, appDate);

		getAchievement(companyID, employeeID, deadDate);

	}

	private AchievementOutput getAchievement(String companyID, String employeeID, GeneralDate appDate) {
		// アルゴリズム「実績取得」を実行する
		if (appDate != null) {
			return collectAchievement.getAchievement(companyID, employeeID, appDate);
		}
		return null;

	}

	private void getDateSpecificSetting(String companyID, String employeeID, GeneralDate refDate, boolean getSetting,
			String workTimeCode, GeneralDate appDate, String takingOutWkTypeCD, String wkHourCD, GeneralDate deadDate,
			String shiftWkTypeCD, String shutOffCD) {
		// Imported(就業.shared.組織管理.社員情報.所属雇用履歴)「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> empImpOpt = wpAdapter.getEmpHistBySid(companyID, employeeID, refDate);
		// アルゴリズム「所属職場を含む上位職場を取得」を実行する
		List<String> wpkIds = empAdaptor.findWpkIdsBySid(companyID, employeeID, refDate);
		if (empImpOpt.isPresent() && CollectionUtil.isEmpty(wpkIds)) {
			// INPUT.設定取得＝false -> Không xử lý đoạn
			// アルゴリズム「雇用別申請承認設定の取得」を実行する và アルゴリズム「申請承認機能設定の取得」を実行する

			// アルゴリズム「振出用勤務種類の取得」を実行する
			// TakingOut
			output.setTakingOutWkTypes(getWorkTypeFor(companyID, empImpOpt.get().getEmploymentCode(), takingOutWkTypeCD)
					.stream().map(x -> WorkTypeDto.fromDomainWorkTypeLanguage(x)).collect(Collectors.toList()));

			// INPUT.振出就業時間帯コード＝設定なし
			// アルゴリズム「振休用勤務種類の取得」を実行する
			// Holiday
			output.setHolidayWkTypes(getWorkTypeFor(companyID, empImpOpt.get().getEmploymentCode(), shiftWkTypeCD)
					.stream().map(x -> WorkTypeDto.fromDomainWorkTypeLanguage(x)).collect(Collectors.toList()));
			// INPUT.振休就業時間帯コード＝設定なし

		}

	}

	private List<WorkType> getWorkTypeFor(String companyID, String employmentCode, String wkTypeCD) {

		// TODO - ドメインモデル「勤務種類」を取得する yêu cầu team anh Chình trả về wktypes
		List<WorkType> wkTypes = wkTypeRepo.findByCompanyId(companyID);
		// アルゴリズム「対象勤務種類の抽出」を実行する
		List<WorkType> outputWkTypes = extractTargetWkTypes(companyID, employmentCode, BreakOutType.PAUSE.value,
				wkTypes);
		if (StringUtils.isEmpty(wkTypeCD)) {
			// アルゴリズム「申請済み勤務種類の存在判定と取得」を実行する
			appliedWorkType(companyID, outputWkTypes, wkTypeCD);

		}
		// sort by CD
		outputWkTypes.sort(Comparator.comparing(WorkType::getWorkTypeCode));
		return outputWkTypes;

	}

	private boolean appliedWorkType(String companyID, List<WorkType> wkTypes, String wkTypeCD) {
		boolean masterUnregistered = true;

		Optional<WorkType> WkTypeOpt = wkTypeRepo.findByPK(companyID, wkTypeCD);
		if (WkTypeOpt.isPresent()) {
			wkTypes.add(WkTypeOpt.get());
			masterUnregistered = false;

		}
		return masterUnregistered;

	}

	private List<WorkType> extractTargetWkTypes(String companyID, String employmentCode, int breakOutType,
			List<WorkType> wkTypes) {
		Optional<AppEmploymentSetting> empSetOpt = appCommonSettingOutput.appEmploymentWorkType.stream()
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

	private void commonProcessBeforeStart(ApplicationType appType, String companyID, String employeeID,
			GeneralDate baseDate) {
		AppHolidayWorkDto result = new AppHolidayWorkDto();

		int rootAtr = 1;

		// 1-1.新規画面起動前申請共通設定を取得する
		appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID,
				rootAtr, appType, baseDate);

		output.setRefDate(appCommonSettingOutput.generalDate);

		output.setApplicationSetting(ApplicationSettingDto.convertToDto(appCommonSettingOutput.applicationSetting));

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
		Optional<EmploymentHistoryImported> empImpOpt = wpAdapter.getEmpHistBySid(companyID, employeeID, baseDate);
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
				ComSubstVacation comSub = comSubOpt.get();
				if (comSub.getSetting().getIsManage().equals(ManageDistinct.NO)) {
					throw new BusinessException("Msg_323");
				}

			}
		}
	}

}
