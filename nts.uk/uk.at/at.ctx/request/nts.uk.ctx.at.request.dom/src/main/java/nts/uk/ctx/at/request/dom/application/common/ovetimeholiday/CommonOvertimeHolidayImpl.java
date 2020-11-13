package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.WpSpecificDateSettingAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.WpSpecificDateSettingImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.AgreementTimeService;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.Time36UpperLimitCheck;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppTimeItem;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.Time36ErrorOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.Time36UpperLimitCheckResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidaySixProcess;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayThreeProcess;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetailRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail_Update;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.WeekdayHolidayClassification;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReasonRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone.BreakTimeZoneService;
import nts.uk.ctx.at.shared.dom.employmentrules.employmenttimezone.BreakTimeZoneSharedOutPut;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;



@Stateless
public class CommonOvertimeHolidayImpl implements CommonOvertimeHoliday {

	final static String DATE_FORMAT = "yyyy/MM/dd";

	@Inject
	private BasicScheduleService basicService;

	@Inject
	private BreakTimeZoneService timeService;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;

	@Inject
	private AgreementTimeService agreementTimeService;

	@Inject
	private EmployeeRequestAdapter employeeAdapter;

	@Inject
	private WpSpecificDateSettingAdapter wpSpecificDateSettingAdapter;

	@Inject
	private BPTimeItemRepository bPTimeItemRepository;

	@Inject
	private DivergenceReasonRepository diReasonRepository;

//	@Inject
//	private AppTypeDiscreteSettingRepository discreteRepo;

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Inject
	private PredetemineTimeSettingRepository workTimeSetRepository;

	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;

	@Inject
	private ApplicationRepository appRepository;

	@Inject
	private OvertimeInputRepository overtimeInputRepository;

	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;

	@Inject
	private HolidayWorkInputRepository holidayWorkInputRepository;

	@Inject
	private WorkdayoffFrameRepository breaktimeFrameRep;

	@Inject
	private Time36UpperLimitCheck time36UpperLimitCheck;

	@Inject
	private AppOvertimeDetailRepository appOvertimeDetailRepository;

	@Inject
	private OvertimeSixProcess overtimeSixProcess;

	@Inject
	private HolidaySixProcess holidaySixProcess;

	@Inject
	private HolidayThreeProcess holidayThreeProcess;

	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;

	@Inject
	public RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	@Override
	// 休憩時間帯を取得する
	public List<DeductionTime> getBreakTimes(String companyID, String workTypeCode, String workTimeCode, 
			Optional<TimeWithDayAttr> opStartTime, Optional<TimeWithDayAttr> opEndTime) {
		List<DeductionTime> result = new ArrayList<>();
		// 1日半日出勤・1日休日系の判定
		WorkStyle workStyle = this.basicService.checkWorkDay(workTypeCode);
		// 平日か休日か判断する
		WeekdayHolidayClassification weekDay = checkHolidayOrNot(workTypeCode);
		// 休憩時間帯の取得
		BreakTimeZoneSharedOutPut breakTimeZoneSharedOutPut = this.timeService.getBreakTimeZone(companyID, workTimeCode,
				weekDay.value, workStyle);
		Collections.sort(breakTimeZoneSharedOutPut.getLstTimezone(), new Comparator<DeductionTime>() {
			@Override
			public int compare(DeductionTime o1, DeductionTime o2) {
				return o1.getStart().v().compareTo(o2.getStart().v());
			}
		});
		// Input．開始時刻とInput．終了時刻をチェック
		if(!opStartTime.isPresent() || !opEndTime.isPresent()){
			return breakTimeZoneSharedOutPut.getLstTimezone();
		}
		for(DeductionTime deductionTime : breakTimeZoneSharedOutPut.getLstTimezone()){
			// 状態区分　＝　「重複の判断処理」を実行
			TimeWithDayAttr startTime = opStartTime.get();
			TimeWithDayAttr endTime = opEndTime.get();
			TimeSpanForCalc timeSpanFirstTime = new TimeSpanForCalc(endTime, startTime);
			TimeSpanForCalc timeSpanSecondTime = new TimeSpanForCalc(deductionTime.getEnd(), deductionTime.getStart());
			// アルゴリズム「時刻入力期間重複チェック」を実行する
			DuplicateStateAtr duplicateStateAtr = this.rangeOfDayTimeZoneService
					.checkPeriodDuplication(timeSpanFirstTime, timeSpanSecondTime);
			// 重複状態区分チェック
			DuplicationStatusOfTimeZone duplicationStatusOfTimeZone = this.rangeOfDayTimeZoneService
					.checkStateAtr(duplicateStateAtr);
			// 取得した状態区分をチェック
			if(duplicationStatusOfTimeZone != DuplicationStatusOfTimeZone.NON_OVERLAPPING){
				result.add(deductionTime);
			}
		}
		return result;
	}

	private WeekdayHolidayClassification checkHolidayOrNot(String workTypeCd) {
		String companyId = AppContexts.user().companyId();
		Optional<WorkType> WorkTypeOptional = this.workTypeRepository.findByPK(companyId, workTypeCd);
		if (!WorkTypeOptional.isPresent()) {
			return WeekdayHolidayClassification.WEEKDAY;
		}
		// check null?
		WorkType workType = WorkTypeOptional.get();
		DailyWork dailyWork = workType.getDailyWork();
		WorkTypeClassification oneDay = dailyWork.getOneDay();
		// 休日出勤
		if (oneDay.value == 11) {
			return WeekdayHolidayClassification.HOLIDAY;
		}
		return WeekdayHolidayClassification.WEEKDAY;
	}

	@Override
	public Optional<AgreeOverTimeOutput> getAgreementTime(String companyID, String employeeID,
			ApplicationType appType) {
		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = Optional.empty();
		// 時間外表示区分チェック(check 時間外表示区分)
		Optional<OvertimeRestAppCommonSetting> otRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, appType.value);
		if (otRestAppCommonSet.isPresent() && (otRestAppCommonSet.get().getExtratimeDisplayAtr() == UseAtr.USE)) {
			// ３６時間の表示
			opAgreeOverTimeOutput = Optional.of(agreementTimeService.getAgreementTime(companyID, employeeID));
		}
		return opAgreeOverTimeOutput;
	}

	@Override
	public List<BonusPayTimeItem> getBonusTime(String companyID, String employeeID, GeneralDate appDate,
			UseAtr bonusTimeDisplayAtr) {
		List<BonusPayTimeItem> result = new ArrayList<>();
		WpSpecificDateSettingImport wpSpecificDateSettingImport = new WpSpecificDateSettingImport(null, null);
		if (bonusTimeDisplayAtr == UseAtr.USE) {
			// アルゴリズム「社員所属職場履歴を取得」を実行する
			SWkpHistImport sWkpHistImport = employeeAdapter.getSWkpHistByEmployeeID(employeeID, appDate);
			// アルゴリズム「職場の特定日設定を取得する」を実行する
			if (sWkpHistImport != null) {
				wpSpecificDateSettingImport = this.wpSpecificDateSettingAdapter
						.workplaceSpecificDateSettingService(companyID, sWkpHistImport.getWorkplaceId(), appDate);
			}

			List<BonusPayTimeItem> bonusPayTimeItems = this.bPTimeItemRepository
					.getListBonusPayTimeItemInUse(companyID);
			for (BonusPayTimeItem bonusItem : bonusPayTimeItems) {
				result.add(bonusItem);
			}
			if (!CollectionUtil.isEmpty(wpSpecificDateSettingImport.getNumberList())) {
				List<BonusPayTimeItem> bonusPayTimeItemSpecs = this.bPTimeItemRepository
						.getListSpecialBonusPayTimeItemInUse(companyID);
				for (BonusPayTimeItem bonusItem : bonusPayTimeItemSpecs) {
					result.add(bonusItem);
				}
			}
		}
		return result;
	}

	@Override
	public boolean displayDivergenceReasonInput(PrePostAtr prePostAtr, UseAtr divergenceReasonInputAtr) {
		// Input．乖離理由入力区分チェック
		if (divergenceReasonInputAtr == UseAtr.USE) {
			return true;
		}
		return false;
	}

	@Override
	public List<DivergenceReason> getDivergenceReasonForm(String companyID, PrePostAtr prePostAtr,
			UseAtr divergenceReasonFormAtr, ApplicationType appType) {
		// 事前事後区分チェック
		if (prePostAtr == PrePostAtr.PREDICT) {
			return Collections.emptyList();
		}
		// Input．.乖離理由定型区分チェック
		if (divergenceReasonFormAtr == UseAtr.USE) {
			List<DivergenceReason> divergenceReasons = diReasonRepository.getDivergenceReason(companyID, appType.value);
			return divergenceReasons;
		}
		return Collections.emptyList();
	}

	@Override
	public DisplayPrePost getDisplayPrePost(String companyID, ApplicationType appType, Integer uiType,
			OverTimeAtr overtimeAtr, GeneralDate appDate, AppDisplayAtr displayPrePostFlg) {
		DisplayPrePost result = new DisplayPrePost();
		// Input．事前事後区分表示チェック
		if (displayPrePostFlg == AppDisplayAtr.DISPLAY) {
			result.setDisplayPrePostFlg(AppDisplayAtr.DISPLAY.value);
			// ドメインモデル「申請種類別設定」.事前事後区分の初期表示
//			Optional<AppTypeDiscreteSetting> discreteSetting = discreteRepo
//					.getAppTypeDiscreteSettingByAppType(companyID, appType.value);
//			if (discreteSetting.isPresent()) {
//				result.setPrePostAtr(discreteSetting.get().getPrePostInitFlg().value);
//				result.setPrePostCanChangeFlg(discreteSetting.get().getPrePostCanChangeFlg().value == 1 ? true : false);
//			}
		} else {
			// 事前事後区分表示を非表示
			result.setDisplayPrePostFlg(AppDisplayAtr.NOTDISPLAY.value);
			// error EA refactor 4
			// アルゴリズム「事前事後の判断処理(事前事後非表示する場合)」を実行する
			/*result.setPrePostAtr(
					this.otherCommonAlgorithm.preliminaryJudgmentProcessing(appType, appDate, overtimeAtr.value).value);*/
		}
		return result;
	}

	@Override
	public boolean getRestTime(String companyID, UseAtr timeCalUse, Boolean breakInputFieldDisp,
			ApplicationType appType) {
		// 時刻計算利用チェック
		if (timeCalUse == UseAtr.NOTUSE) {
			return false;
		}
		// 休憩入力欄を表示するをチェックする
		return breakInputFieldDisp;
	}

	@Override
	public List<ConfirmMsgOutput> inconsistencyCheck(String companyID, String employeeID, GeneralDate appDate,
			ApplicationType appType, AppDateContradictionAtr appDateContradictionAtr) {
		// Input．申請日矛盾区分をチェックする
		if (appDateContradictionAtr == AppDateContradictionAtr.NOTCHECK) {
			return Collections.emptyList();
		}
		WorkType workType = otherCommonAlgorithm.getWorkTypeScheduleSpec(companyID, employeeID, appDate);
		if (workType == null) {
			// 「申請日矛盾区分」をチェックする
			if (appDateContradictionAtr == AppDateContradictionAtr.CHECKNOTREGISTER) {
				throw new BusinessException("Msg_1519", appDate.toString("yyyy/MM/dd"));
			}
			return Arrays.asList(new ConfirmMsgOutput("Msg_1520", Arrays.asList(appDate.toString("yyyy/MM/dd"))));
		}
		boolean checked = false;
		// Input．申請種類をチェック
		if (appType == ApplicationType.OVER_TIME_APPLICATION) {
			// アルゴリズム「03-08_01 残業申請の勤務種類矛盾チェック」を実行する
			checked = this.checkOverTime(workType);
		} else {
			// 03-08_01 休日出勤の勤務種類矛盾チェック
			checked = this.workTypeInconsistencyCheck(workType);
		}
		if (!checked) {
			return Collections.emptyList();
		}
		String name = workType.getName().v();
		// 「申請日矛盾区分」をチェックする
		if (appDateContradictionAtr == AppDateContradictionAtr.CHECKNOTREGISTER) {
			throw new BusinessException("Msg_1521", appDate.toString("yyyy/MM/dd"),
					Strings.isNotBlank(name) ? name : "未登録のマスタ");
		}
		return Arrays.asList(new ConfirmMsgOutput("Msg_1522", Arrays.asList(appDate.toString("yyyy/MM/dd"), Strings.isNotBlank(name) ? name : "未登録のマスタ") ));

	}

	/**
	 * 03-08_01 残業申請の勤務種類矛盾チェック
	 * 
	 * @param workType
	 * @return
	 */
	private boolean checkOverTime(WorkType workType) {
		boolean error = false;
		// INPUT.ドメインモデル「勤務種類.勤務の単位(WORK_ATR)」をチェックする
		if (workType.getDailyWork().getWorkTypeUnit().equals(WorkTypeUnit.MonringAndAfternoon)) {
			// INPUT.ドメインモデル「勤務種類.午前の勤務分類(MORNING_CLS)」をチェックする
			int wkMorning = workType.getDailyWork().getMorning().value;
			// INPUT.ドメインモデル「勤務種類.午後の勤務分類(AFTERNOON_CLS)」をチェックする
			int wkAfternoon = workType.getDailyWork().getAfternoon().value;
			List<Integer> holidayTypes = Arrays.asList(1, 2, 3, 4, 5, 6, 8, 9, 11);
			boolean morningIsHoliday = holidayTypes.indexOf(wkMorning) != -1;
			boolean afternoonIsHoliday = holidayTypes.indexOf(wkAfternoon) != -1;
			if (morningIsHoliday && afternoonIsHoliday) {
				error = true;
			} else {
				error = false;
			}
		} else {
			// INPUT.ドメインモデル「勤務種類.1日勤務分類(ONE_DAY_CLS)」をチェックする
			WorkTypeClassification workTypeClassification = workType.getDailyWork().getOneDay();
			if (workTypeClassification.equals(WorkTypeClassification.Attendance)
					|| workTypeClassification.equals(WorkTypeClassification.Shooting)) {
				error = false;
			} else {
				error = true;
			}
		}
		return error;
	}

	/**
	 * 03-08_01 休日出勤の勤務種類矛盾チェック
	 * 
	 * @param workType
	 * @return 矛盾なし = false or 矛盾あり = true
	 */
	private boolean workTypeInconsistencyCheck(WorkType workType) {
		// INPUT.ドメインモデル「勤務種類.勤務の単位(WORK_ATR)」が１日であるかをチェックする
		if (workType.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.MonringAndAfternoon) {
			return false;
		}
		// INPUT.ドメインモデル「勤務種類.1日勤務分類(ONE_DAY_CLS)」をチェックする
		WorkTypeClassification workTypeClassification = workType.getDailyWork().getOneDay();
		if (workTypeClassification == WorkTypeClassification.Holiday
				|| workTypeClassification == WorkTypeClassification.Pause
				|| workTypeClassification == WorkTypeClassification.HolidayWork) {
			return false;
		}
		return true;
	}

//	@Override
//	public RecordWorkOutput getWorkingHours(String companyID, String employeeID, GeneralDate appDate, UseAtr timeCalUse, AtWorkAtr atworkTimeBeginDisp,
//			ApplicationType appType, String workTimeCD, Optional<Integer> startTime, Optional<Integer> endTime, ApprovalFunctionSetting approvalFunctionSetting) {
//		UseAtr recordWorkDisplay = UseAtr.NOTUSE;
//		Integer startTime1 = null;
//		Integer endTime1 = null;
//		Integer startTime2 = null;
//		Integer endTime2 = null;
//		if (timeCalUse == UseAtr.NOTUSE && appType == ApplicationType.OVER_TIME_APPLICATION) {
//			return new RecordWorkOutput(recordWorkDisplay, startTime1, endTime1, startTime2, endTime2);
//		}
//		recordWorkDisplay = UseAtr.USE;
//		if (appDate == null) {
//			return new RecordWorkOutput(recordWorkDisplay, startTime1, endTime1, startTime2, endTime2);
//		}
//
//		switch (atworkTimeBeginDisp) {
//		case NOTDISPLAY: {
//			break;
//		}
//		case DISPLAY: {
//			// 01-14-2_実績から出退勤を初期表示
//			RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, appDate);
//			startTime1 = recordWorkInfoImport.getAttendanceStampTimeFirst();
//			endTime1 = recordWorkInfoImport.getLeaveStampTimeFirst();
//			startTime2 = recordWorkInfoImport.getAttendanceStampTimeSecond();
//			endTime2 = recordWorkInfoImport.getLeaveStampTimeSecond();
//			break;
//		}
//		case AT_START_WORK_OFF_PERFORMANCE: {
//			// 01-14-3_始業時刻、退勤時刻を初期表示
//			RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, appDate);
//			Optional<PredetemineTimeSetting> workTimeSet = workTimeSetRepository.findByWorkTimeCode(companyID,
//					recordWorkInfoImport.getWorkTimeCode());
//			if (workTimeSet.isPresent()) {
//				List<TimezoneUse> lstTimezone = workTimeSet.get().getPrescribedTimezoneSetting().getLstTimezone()
//						.stream().sorted(Comparator.comparing(TimezoneUse::getWorkNo)).collect(Collectors.toList());
//				if (lstTimezone.size() > 1 && lstTimezone.get(1).getUseAtr().value == UseAtr.USE.value) {
//					startTime2 = lstTimezone.get(1).getStart().v();
//				}
//				if (lstTimezone.size() > 0) {
//					startTime1 = lstTimezone.get(0).getStart().v();
//				}
//			}
//			if (recordWorkInfoImport.getLeaveStampTimeFirst() == null) {
//				if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeEndDispFlg()
//						.equals(DisplayBreakTime.SYSTEM_TIME)) {
//					endTime1 = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
//				}
//			} else {
//				endTime1 = recordWorkInfoImport.getLeaveStampTimeFirst();
//			}
//			if (recordWorkInfoImport.getLeaveStampTimeSecond() == null) {
//				if (approvalFunctionSetting.getApplicationDetailSetting().get().getTimeEndDispFlg()
//						.equals(DisplayBreakTime.SYSTEM_TIME)) {
//					endTime2 = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
//				}
//			} else {
//				endTime2 = recordWorkInfoImport.getLeaveStampTimeSecond();
//			}
//			break;
//		}
//		case AT_START_WORK_OFF_ENDWORK: {
//			// 01-14-4_始業時刻、終業時刻を初期表示
//			Optional<PredetemineTimeSetting> workTimeSet = workTimeSetRepository.findByWorkTimeCode(companyID, workTimeCD);
//			if (workTimeSet.isPresent()) {
//				List<TimezoneUse> lstTimezone = workTimeSet.get().getPrescribedTimezoneSetting().getLstTimezone()
//						.stream().sorted(Comparator.comparing(TimezoneUse::getWorkNo)).collect(Collectors.toList());
//				if (lstTimezone.size() > 1 && lstTimezone.get(1).getUseAtr().value == UseAtr.USE.value) {
//					startTime2 = lstTimezone.get(1).getStart().v();
//					endTime2 = lstTimezone.get(1).getEnd().v();
//				}
//				if (lstTimezone.size() > 0) {
//					startTime1 = lstTimezone.get(0).getStart().v();
//					endTime1 = lstTimezone.get(0).getEnd().v();
//				}
//			}
//			break;
//		}
//		default:
//			break;
//		}
//
//		return new RecordWorkOutput(recordWorkDisplay, startTime1, endTime1, startTime2, endTime2);
//		return null;
//	}

	/**
	 * 03-01_事前申請超過チェック
	 */
	@Override
	public ColorConfirmResult preApplicationExceededCheck(String companyId, GeneralDate appDate,
			GeneralDateTime inputDate, PrePostAtr prePostAtr, int attendanceId, List<OverTimeInput> overtimeInputs,
			String employeeID) {
		String employeeName = employeeAdapter.getEmployeeName(employeeID);
		OvertimeCheckResult result = new OvertimeCheckResult();
		result.setFrameNo(-1);
		// 社員ID
		// String EmployeeId = AppContexts.user().employeeId();
		// チェック条件を確認
		if (!this.confirmCheck(companyId, prePostAtr)) {
			result.setErrorCode(0);
			return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
		}
		// ドメインモデル「申請」を取得
		// 事前申請漏れチェック
//		List<Application_New> beforeApplication = appRepository.getBeforeApplication(companyId, employeeID, appDate,
//				ApplicationType.OVER_TIME_APPLICATION.value, PrePostAtr.PREDICT.value);
//		if (beforeApplication.isEmpty()) {
//			return new ColorConfirmResult(true, 0, 0, "Msg_1508", Arrays.asList(employeeName), null, null);
//		}
		// 事前申請否認チェック
		// 否認以外：
		// 反映情報.実績反映状態＝ 否認、差し戻し
//		ReflectedState_New refPlan = beforeApplication.get(0).getReflectionInformation().getStateReflectionReal();
//		if (refPlan.equals(ReflectedState_New.DENIAL) || refPlan.equals(ReflectedState_New.REMAND)) {
//			// 背景色を設定する
//			return new ColorConfirmResult(true, 0, 0, "Msg_1508", Arrays.asList(employeeName), null, null);
//		}
//		String beforeCid = beforeApplication.get(0).getCompanyID();
//		String beforeAppId = beforeApplication.get(0).getAppID();
//
//		// 事前申請の申請時間
//		List<OverTimeInput> beforeOvertimeInputs = overtimeInputRepository.getOvertimeInput(beforeCid, beforeAppId)
//				.stream()
//				.filter(item -> item.getAttendanceType() == EnumAdaptor.valueOf(attendanceId, AttendanceType.class))
//				.collect(Collectors.toList());
//		if (beforeOvertimeInputs.isEmpty()) {
//			result.setErrorCode(0);
//			return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
//		}
//		// 残業時間１～１０、加給時間
//		// すべての残業枠をチェック
//		for (int i = 0; i < overtimeInputs.size(); i++) {
//			OverTimeInput afterTime = overtimeInputs.get(i);
//			int frameNo = afterTime.getFrameNo();
//			Optional<OverTimeInput> beforeTimeOpt = beforeOvertimeInputs.stream()
//					.filter(item -> item.getFrameNo() == frameNo).findFirst();
//			if (!beforeTimeOpt.isPresent()) {
//				continue;
//			}
//			OverTimeInput beforeTime = beforeTimeOpt.get();
//			if (null == beforeTime) {
//				continue;
//			}
//			// 事前申請の申請時間＞事後申請の申請時間
//			if (beforeTime.getApplicationTime() != null && afterTime.getApplicationTime() != null
//					&& beforeTime.getApplicationTime().v() < afterTime.getApplicationTime().v()) {
//				// 背景色を設定する
//				Optional<OvertimeWorkFrame> overtimeWorkFrame = this.overtimeFrameRepository
//						.findOvertimeWorkFrame(new CompanyId(companyId), frameNo);
//				return new ColorConfirmResult(true, 1, frameNo,
//						"Msg_424", Arrays
//								.asList(employeeName,
//										overtimeWorkFrame.isPresent()
//												? overtimeWorkFrame.get().getOvertimeWorkFrName().toString() : ""),
//						null, null);
//			}
//		}
		result.setErrorCode(0);
		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
	}

	/**
	 * チェック条件
	 * 
	 * @return True：チェックをする, False：チェックをしない
	 */
	private boolean confirmCheck(String companyId, PrePostAtr prePostAtr) {
		// 事前事後区分チェック
		if (prePostAtr.equals(PrePostAtr.PREDICT)) {
			return false;
		}
		// ドメインモデル「残業休出申請共通設定」を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyId, ApplicationType.OVER_TIME_APPLICATION.value);
		if (overtimeRestAppCommonSet.isPresent()) {
			// 残業休出申請共通設定.事前表示区分＝表示する
			if (overtimeRestAppCommonSet.get().getPreExcessDisplaySetting().equals(UseAtr.USE)) {
				// 表示する:Trueを返す
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc) 03-01_事前申請超過チェック
	 * 
	 * @see nts.uk.ctx.at.request.dom.application.holidayworktime.service.
	 * HolidayThreeProcess#preApplicationExceededCheck(java.lang.String,
	 * nts.arc.time.GeneralDate, nts.arc.time.GeneralDateTime,
	 * nts.uk.ctx.at.request.dom.application.PrePostAtr, int, java.util.List)
	 */
	@Override
	public ColorConfirmResult preApplicationExceededCheck010(String companyId, GeneralDate appDate,
			GeneralDateTime inputDate, PrePostAtr prePostAtr, int attendanceId,
			List<HolidayWorkInput> holidayWorkInputs, String employeeID) {
		String employeeName = employeeAdapter.getEmployeeName(employeeID);
		OvertimeCheckResult result = new OvertimeCheckResult();
		result.setFrameNo(-1);
		// 社員ID
		// String EmployeeId = AppContexts.user().employeeId();
		// チェック条件を確認
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyId, ApplicationType.HOLIDAY_WORK_APPLICATION.value);
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.get().getPreExcessDisplaySetting();
		if (this.preAppSetCheck(prePostAtr, preExcessDisplaySetting)==UseAtr.NOTUSE) {
			result.setErrorCode(0);
			return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
		}
		// ドメインモデル「申請」を取得
		// 事前申請漏れチェック
//		List<Application_New> beforeApplication = appRepository.getBeforeApplication(companyId, employeeID, appDate,
//				ApplicationType.HOLIDAY_WORK_APPLICATION.value, PrePostAtr.PREDICT.value);
//		if (beforeApplication.isEmpty()) {
//			return new ColorConfirmResult(true, 0, 0, "Msg_1508", Arrays.asList(employeeName), null, null);
//		}
//		// 事前申請否認チェック
//		// 否認以外：
//		// 反映情報.実績反映状態＝ 否認、差し戻し
//		ReflectedState_New refPlan = beforeApplication.get(0).getReflectionInformation().getStateReflectionReal();
//		if (refPlan.equals(ReflectedState_New.DENIAL) || refPlan.equals(ReflectedState_New.REMAND)) {
//			// 背景色を設定する
//			return new ColorConfirmResult(true, 0, 0, "Msg_1508", Arrays.asList(employeeName), null, null);
//		}
//		String beforeCid = beforeApplication.get(0).getCompanyID();
//		String beforeAppId = beforeApplication.get(0).getAppID();
//
//		// 事前申請の申請時間
//		List<HolidayWorkInput> beforeOvertimeInputs = holidayWorkInputRepository
//				.getHolidayWorkInputByAppID(beforeCid, beforeAppId).stream()
//				.filter(item -> item.getAttendanceType() == EnumAdaptor.valueOf(attendanceId, AttendanceType.class))
//				.collect(Collectors.toList());
//		if (beforeOvertimeInputs.isEmpty()) {
//			return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
//		}
//		// 残業時間１～１０、加給時間
//		// すべての残業枠をチェック
//		for (int i = 0; i < holidayWorkInputs.size(); i++) {
//			HolidayWorkInput afterTime = holidayWorkInputs.get(i);
//			int frameNo = afterTime.getFrameNo();
//			Optional<HolidayWorkInput> beforeTimeOpt = beforeOvertimeInputs.stream()
//					.filter(item -> item.getFrameNo() == frameNo).findFirst();
//			if (!beforeTimeOpt.isPresent()) {
//				continue;
//			}
//			HolidayWorkInput beforeTime = beforeTimeOpt.get();
//			if (null == beforeTime) {
//				continue;
//			}
//			// 事前申請の申請時間＞事後申請の申請時間
//			if (afterTime.getApplicationTime() != null
//					&& beforeTime.getApplicationTime().v() < afterTime.getApplicationTime().v()) {
//				// 背景色を設定する
//				Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep
//						.findWorkdayoffFrame(new CompanyId(companyId), frameNo);
//				return new ColorConfirmResult(true, 1, frameNo, "Msg_424", Arrays.asList(employeeName,
//						workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : ""),
//						null, null);
//			}
//		}
		result.setErrorCode(0);
		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
	}

	/**
	 * 03-06_計算ボタンチェック
	 */
	@Override
	public void calculateButtonCheck(int calculateFlg, UseAtr timeCalUse) {
		// 申請詳細設定.時刻計算利用区分=利用する
		if (timeCalUse != UseAtr.USE) {
			return;
		}
		// 計算フラグのチェック
		if (calculateFlg == 1) {
			// 計算フラグ=1の場合:メッセージを表示する(Msg_750)
			throw new BusinessException("Msg_750");
		}
	}

	@Override
	public Optional<AppOvertimeDetail> registerOvertimeCheck36TimeLimit(String companyId, String employeeId,
			GeneralDate appDate, List<OverTimeInput> overTimeInput) {
		// 代行申請かをチェックする
		// TODO
		// ３６時間の上限チェック(新規登録)
		List<AppTimeItem> appTimeItems = overTimeInput.stream()
				.filter(x -> x != null && x.getApplicationTimeValue() != null).collect(Collectors.toList()).stream()
				.map(x -> {
					return new AppTimeItem(x.getApplicationTimeValue(), x.getFrameNo());
				}).collect(Collectors.toList());
		Time36UpperLimitCheckResult result = time36UpperLimitCheck.checkRegister(companyId, employeeId, appDate,
				ApplicationType.OVER_TIME_APPLICATION, appTimeItems);
		// 上限エラーフラグがtrue AND ドメインモデル「残業休出申請共通設定」.時間外超過区分がチェックする（登録不可）
		if (result.getErrorFlg().size() > 0) {
			BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
			for (Time36ErrorOutput time36ErrorOutput : result.getErrorFlg()) {
				switch (time36ErrorOutput.errorFlg) {
				case MONTH:
					bundledBusinessExceptions.addMessage("Msg_1535", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case YEAR:
					bundledBusinessExceptions.addMessage("Msg_1536", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case MAX_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1537", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case AVERAGE_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1538", "1900/01", "1900/01", "00:00", "00:00",
							time36ErrorOutput.yearMonthStart, time36ErrorOutput.yearMonthEnd,
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime);
					break;
				default:
					break;
				}
			}
			throw bundledBusinessExceptions;
		}
		return result.getAppOvertimeDetail();
	}

	@Override
	public Optional<AppOvertimeDetail> updateOvertimeCheck36TimeLimit(String companyId, String appId,
			String enteredPersonId, String employeeId, GeneralDate appDate, List<OverTimeInput> overTimeInput) {
		Optional<AppOvertimeDetail> appOvertimeDetailOpt = appOvertimeDetailRepository
				.getAppOvertimeDetailById(companyId, appId);
		// ３６時間の上限チェック(照会)
		List<AppTimeItem> appTimeItems = CollectionUtil.isEmpty(overTimeInput) ? Collections.emptyList()
				: overTimeInput.stream().map(x -> {
					return new AppTimeItem(x.getApplicationTimeValue(), x.getFrameNo());
				}).collect(Collectors.toList());
		Time36UpperLimitCheckResult result = time36UpperLimitCheck.checkUpdate(companyId, appOvertimeDetailOpt,
				employeeId, appDate, ApplicationType.OVER_TIME_APPLICATION, appTimeItems);
		// 上限エラーフラグがtrue AND ドメインモデル「残業休出申請共通設定」.時間外超過区分がチェックする（登録不可）
		if (result.getErrorFlg().size() > 0) {
			BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
			for (Time36ErrorOutput time36ErrorOutput : result.getErrorFlg()) {
				switch (time36ErrorOutput.errorFlg) {
				case MONTH:
					bundledBusinessExceptions.addMessage("Msg_1535", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case YEAR:
					bundledBusinessExceptions.addMessage("Msg_1536", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case MAX_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1537", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case AVERAGE_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1538", "1900/01", "1900/01", "00:00", "00:00",
							time36ErrorOutput.yearMonthStart, time36ErrorOutput.yearMonthEnd,
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime);
					break;
				default:
					break;
				}
			}
			throw bundledBusinessExceptions;
		}
		return result.getAppOvertimeDetail();
	}

	@Override
	public Optional<AppOvertimeDetail> registerHdWorkCheck36TimeLimit(String companyId, String employeeId,
			GeneralDate appDate, List<HolidayWorkInput> holidayWorkInputs) {
		// 代行申請かをチェックする
		// TODO
		// ３６時間の上限チェック(新規登録)
		List<AppTimeItem> appTimeItems = holidayWorkInputs.stream().filter(x -> x != null && x.getApptime() != null)
				.collect(Collectors.toList()).stream().map(x -> {
					return new AppTimeItem(x.getApptime(), x.getFrameNo());
				}).collect(Collectors.toList());
		Time36UpperLimitCheckResult result = time36UpperLimitCheck.checkRegister(companyId, employeeId, appDate,
				ApplicationType.HOLIDAY_WORK_APPLICATION, appTimeItems);
		// 上限エラーフラグがtrue AND ドメインモデル「残業休出申請共通設定」.時間外超過区分がチェックする（登録不可）
		if (result.getErrorFlg().size() > 0) {
			BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
			for (Time36ErrorOutput time36ErrorOutput : result.getErrorFlg()) {
				switch (time36ErrorOutput.errorFlg) {
				case MONTH:
					bundledBusinessExceptions.addMessage("Msg_1535", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case YEAR:
					bundledBusinessExceptions.addMessage("Msg_1536", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case MAX_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1537", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case AVERAGE_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1538", "1900/01", "1900/01", "00:00", "00:00",
							time36ErrorOutput.yearMonthStart, time36ErrorOutput.yearMonthEnd,
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime);
					break;
				default:
					break;
				}
			}
			throw bundledBusinessExceptions;
		}
		return result.getAppOvertimeDetail();
	}

	@Override
	public Optional<AppOvertimeDetail> updateHdWorkCheck36TimeLimit(String companyId, String appId,
			String enteredPersonId, String employeeId, GeneralDate appDate, List<HolidayWorkInput> holidayWorkInputs) {
		Optional<AppOvertimeDetail> appOvertimeDetailOpt = appOvertimeDetailRepository
				.getAppOvertimeDetailById(companyId, appId);
		// ３６時間の上限チェック(照会)
		List<AppTimeItem> appTimeItems = CollectionUtil.isEmpty(holidayWorkInputs) ? Collections.emptyList()
				: holidayWorkInputs.stream().map(x -> {
					return new AppTimeItem(x.getApptime(), x.getFrameNo());
				}).collect(Collectors.toList());
		Time36UpperLimitCheckResult result = time36UpperLimitCheck.checkUpdate(companyId, appOvertimeDetailOpt,
				employeeId, appDate, ApplicationType.HOLIDAY_WORK_APPLICATION, appTimeItems);
		// 上限エラーフラグがtrue AND ドメインモデル「残業休出申請共通設定」.時間外超過区分がチェックする（登録不可）
		if (result.getErrorFlg().size() > 0) {
			BundledBusinessException bundledBusinessExceptions = BundledBusinessException.newInstance();
			for (Time36ErrorOutput time36ErrorOutput : result.getErrorFlg()) {
				switch (time36ErrorOutput.errorFlg) {
				case MONTH:
					bundledBusinessExceptions.addMessage("Msg_1535", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case YEAR:
					bundledBusinessExceptions.addMessage("Msg_1536", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case MAX_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1537", "00:00", "00:00", "", "",
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime, time36ErrorOutput.yearMonthStart,
							time36ErrorOutput.yearMonthEnd);
					break;
				case AVERAGE_MONTH:
					bundledBusinessExceptions.addMessage("Msg_1538", "1900/01", "1900/01", "00:00", "00:00",
							time36ErrorOutput.yearMonthStart, time36ErrorOutput.yearMonthEnd,
							time36ErrorOutput.realTime, time36ErrorOutput.limitTime);
					break;
				default:
					break;
				}
			}
			throw bundledBusinessExceptions;
		}
		return result.getAppOvertimeDetail();
	}

	// 03-02-1_チェック条件
	@Override
	public boolean checkCodition(int prePostAtr, String companyID, boolean isCalculator) {
		if (prePostAtr == PrePostAtr.POSTERIOR.value) {
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository
					.getOvertimeRestAppCommonSetting(companyID, ApplicationType.HOLIDAY_WORK_APPLICATION.value);
			if (overtimeRestAppCommonSetting.isPresent()) {
				if (isCalculator) {
					// ドメインモデル「残業休出申請共通設定」.実績表示区分チェック
					if (overtimeRestAppCommonSetting.get().getPerformanceDisplayAtr().value == UseAtr.USE.value) {
						return true;
					}
				} else {
					// 休出の事前申請よりも超過している場合確認メッセージを表示するかどうかの区分
					if ((overtimeRestAppCommonSetting.get()
							.getPerformanceExcessAtr() == AppDateContradictionAtr.CHECKNOTREGISTER)
							|| (overtimeRestAppCommonSetting.get()
									.getPerformanceExcessAtr() == AppDateContradictionAtr.CHECKREGISTER)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/*
	 * 06-01_色表示チェック
	 * 
	 * @see
	 * nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess
	 * #checkDisplayColor(java.util.List, java.util.List, int,
	 * nts.arc.time.GeneralDateTime, nts.arc.time.GeneralDate, int,
	 * java.lang.String, java.lang.String,
	 * nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting)
	 */
//	@Override
//	public List<CaculationTime> checkDisplayColor(List<CaculationTime> overTimeInputs,
//			List<OvertimeInputCaculation> overtimeInputCaculations, int prePostAtr, GeneralDateTime inputDate,
//			GeneralDate appDate, int appType, String employeeID, String companyID,
//			ApprovalFunctionSetting approvalFunctionSetting, String siftCD) {
//		for (CaculationTime overtimeInput : overTimeInputs) {
//			for (OvertimeInputCaculation overtimeInputCaculation : overtimeInputCaculations) {
//				if (overtimeInput.getFrameNo() == overtimeInputCaculation.getFrameNo()) {
//					if (overtimeInput.getApplicationTime() != null && !overtimeInput.getApplicationTime()
//							.equals(overtimeInputCaculation.getResultCaculation())) {
//						overtimeInput.setErrorCode(3); // 色定義名：計算値
//					}
//					if (overtimeInputCaculation.getResultCaculation() != null
//							&& overtimeInputCaculation.getResultCaculation() == 0) {
//						continue;
//					} else if (overtimeInputCaculation.getResultCaculation() != null
//							&& overtimeInputCaculation.getResultCaculation() > 0) {
//						// 06-04_計算実績超過チェック
//						List<CaculationTime> caculations = new ArrayList<>();
//						caculations.add(overtimeInput);
//						List<OvertimeInputCaculation> overtimeCals = new ArrayList<>();
//						overtimeCals.add(overtimeInputCaculation);
//						overtimeInput.setErrorCode(overtimeSixProcess
//								.checkCaculationActualExcess(prePostAtr, appType, employeeID, companyID,
//										approvalFunctionSetting, appDate, caculations, siftCD, overtimeCals)
//								.getErrorCode());
//					}
//				}
//			}
//		}
//		return overTimeInputs;
//
//	}
//
//	@Override
//	public ColorConfirmResult checkDisplayColorCF(List<CaculationTime> overTimeInputs,
//			List<OvertimeInputCaculation> overtimeInputCaculations, int prePostAtr, GeneralDateTime inputDate,
//			GeneralDate appDate, int appType, String employeeID, String companyID,
//			ApprovalFunctionSetting approvalFunctionSetting, String siftCD) {
//		for (CaculationTime overtimeInput : overTimeInputs) {
//			for (OvertimeInputCaculation overtimeInputCaculation : overtimeInputCaculations) {
//				if (overtimeInput.getFrameNo() == overtimeInputCaculation.getFrameNo()) {
//					if (overtimeInput.getApplicationTime() != null && !overtimeInput.getApplicationTime()
//							.equals(overtimeInputCaculation.getResultCaculation())) {
//						overtimeInput.setErrorCode(3); // 色定義名：計算値
//					}
//					if (overtimeInputCaculation.getResultCaculation() != null
//							&& overtimeInputCaculation.getResultCaculation() == 0) {
//						continue;
//					} else if (overtimeInputCaculation.getResultCaculation() != null
//							&& overtimeInputCaculation.getResultCaculation() > 0) {
//						// 03-01_事前申請超過チェック
//						ColorConfirmResult colorConfirmResult = this.preApplicationExceededCheck(companyID, appDate,
//								inputDate, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
//								overtimeInputCaculation.getAttendanceID(), overtimeSixProcess.convert(overtimeInput),
//								employeeID);
//						if (colorConfirmResult.isConfirm()) {
//							return colorConfirmResult;
//						}
//					}
//				}
//			}
//		}
//		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
//
//	}

	@Override
	public List<CaculationTime> checkDisplayColorHol(List<CaculationTime> breakTimeInputs,
			Map<Integer, TimeWithCalculationImport> holidayWorkCal, int prePostAtr, GeneralDateTime inputDate,
			GeneralDate appDate, int appType, String employeeID, String companyID, String siftCD) {

		for (CaculationTime breakTime : breakTimeInputs) {
			for (Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()) {
				if (breakTime.getFrameNo() == entry.getKey()) {
					if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() == 0) {
						continue;
					} else if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() > 0) {
						// 03-02_実績超過チェック
						breakTime = this.holidayThreeProcess.checkCaculationActualExcess(prePostAtr, appType,
								employeeID, companyID, appDate, breakTime, siftCD, entry.getValue().getCalTime(), true, false);
					}
				}
			}
		}
		return breakTimeInputs;
	}

	@Override
	public ColorConfirmResult checkDisplayColorHolCF(List<CaculationTime> breakTimeInputs,
			Map<Integer, TimeWithCalculationImport> holidayWorkCal, int prePostAtr, GeneralDateTime inputDate,
			GeneralDate appDate, int appType, String employeeID, String companyID, String siftCD) {
		for (CaculationTime breakTime : breakTimeInputs) {
			for (Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()) {
				if (breakTime.getFrameNo() == entry.getKey()) {
					if (breakTime.getApplicationTime() != null
							&& !breakTime.getApplicationTime().equals(entry.getValue().getCalTime())) {
						breakTime.setErrorCode(3); // 色定義名：計算値
					}
					if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() == 0) {
						continue;
					} else if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() > 0) {
						// 03-01_事前申請超過チェック
						ColorConfirmResult colorConfirmResult = this.preApplicationExceededCheck010(companyID, appDate,
								inputDate, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
								AttendanceType.BREAKTIME.value, holidaySixProcess.convert(breakTime), employeeID);
						if (colorConfirmResult.isConfirm()) {
							return colorConfirmResult;
						}
					}
				}
			}
		}
		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
	}

	@Override
	public List<CaculationTime> checkDisplayColorForApprover(List<CaculationTime> breakTimeInputs,
			Map<Integer, TimeWithCalculationImport> holidayWorkCal, int prePostAtr, GeneralDateTime inputDate,
			GeneralDate appDate, int appType, String employeeID, String companyID, String siftCD) {
		for (CaculationTime breakTime : breakTimeInputs) {
			for (Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()) {
				if (breakTime.getFrameNo() == entry.getKey()) {
					if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() == 0) {
						continue;
					} else if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() > 0) {
						// 03-02-a_実績超過チェック（承認者）
						breakTime = this.holidayThreeProcess.checkCaculationActualExcessForApprover(prePostAtr, appType,
								employeeID, companyID, appDate, breakTime, siftCD, entry.getValue().getCalTime(), true);
					}
				}
			}
		}
		return breakTimeInputs;
	}

	@Override
	public ColorConfirmResult checkDisplayColorForApproverCF(List<CaculationTime> breakTimeInputs,
			Map<Integer, TimeWithCalculationImport> holidayWorkCal, int prePostAtr, GeneralDateTime inputDate,
			GeneralDate appDate, int appType, String employeeID, String companyID, String siftCD) {
		for (CaculationTime breakTime : breakTimeInputs) {
			for (Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()) {
				if (breakTime.getFrameNo() == entry.getKey()) {
					if (breakTime.getApplicationTime() != null
							&& !breakTime.getApplicationTime().equals(entry.getValue().getCalTime())) {
						breakTime.setErrorCode(3); // 色定義名：計算値
					}
					if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() == 0) {
						continue;
					} else if (entry.getValue().getCalTime() != null && entry.getValue().getCalTime() > 0) {
						// 03-01_事前申請超過チェック
						ColorConfirmResult colorConfirmResult = this.preApplicationExceededCheck010(companyID, appDate,
								inputDate, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
								AttendanceType.BREAKTIME.value, holidaySixProcess.convert(breakTime), employeeID);
						if (colorConfirmResult.isConfirm()) {
							return colorConfirmResult;
						}
					}
				}
			}
		}
		return new ColorConfirmResult(false, 0, 0, "", Collections.emptyList(), null, null);
	}

	/* 06-02-2_申請時間を取得 */
	@Override
	public List<CaculationTime> getAppOvertimeCaculation(List<CaculationTime> caculationTimes,
			List<OvertimeInputCaculation> overtimeInputCaculations) {

		for (CaculationTime caculationTime : caculationTimes) {
			for (OvertimeInputCaculation caculation : overtimeInputCaculations) {
				if (caculationTime.getFrameNo() == caculation.getFrameNo()) {
					caculationTime.setApplicationTime(caculation.getResultCaculation());
				}
			}
		}
		return caculationTimes;
	}

	/*
	 * 06-03_加給時間を取得
	 * 
	 * @see
	 * nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess
	 * #getCaculationBonustime(java.lang.String, java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public List<CaculationTime> getCaculationBonustime(String companyID, String employeeId, String appDate, int appType,
			List<CaculationTime> caculationTimes) {

		// 06-03-1_加給事前申請を取得
		List<CaculationTime> overtimeInputs = overtimeSixProcess.getAppBonustimePre(companyID, employeeId, appDate,
				appType, caculationTimes);
		return overtimeInputs;
		// 06-03-2_加給計算時間を取得
		// TODO
	}

	@Override
	public List<OvertimeInputCaculation> calculator(AppCommonSettingOutput appCommonSettingOutput, String appDate,
			String siftCD, String workTypeCode, Integer startTime, Integer endTime, List<Integer> startTimeRests,
			List<Integer> endTimeRests) {
//		String companyID = AppContexts.user().companyId();
//		String employeeID = AppContexts.user().employeeId();
//		boolean isSettingDisplay = appCommonSettingOutput.approvalFunctionSetting.getApplicationDetailSetting().get()
//				.getBreakInputFieldDisp().equals(true)
//				&& appCommonSettingOutput.getApprovalFunctionSetting().getApplicationDetailSetting().get()
//						.getTimeCalUse().equals(UseAtr.USE);
//		if (!isSettingDisplay) {
//			Optional<TimeWithDayAttr> opStartTime = startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(startTime)); 
//			Optional<TimeWithDayAttr> opEndTime = endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(endTime)); 
//			// 休憩時間帯を取得する
//			List<DeductionTime> breakTimes = this.getBreakTimes(companyID, workTypeCode, siftCD, opStartTime, opEndTime);
//
//			if (!CollectionUtil.isEmpty(breakTimes)) {
//				startTimeRests = breakTimes.stream().map(x -> x.getStart().v())
//						.collect(Collectors.toList());
//
//				endTimeRests = breakTimes.stream().map(x -> x.getEnd().v())
//						.collect(Collectors.toList());
//			}
//		}
//
//		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation
//				.getCalculation(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), workTypeCode, siftCD,
//						startTime, endTime, startTimeRests, endTimeRests);
//		Map<Integer, TimeWithCalculationImport> overTime = dailyAttendanceTimeCaculationImport.getOverTime();
//		List<OvertimeInputCaculation> overtimeInputCaculations = convertMaptoList(overTime,
//				dailyAttendanceTimeCaculationImport.getFlexTime(),
//				dailyAttendanceTimeCaculationImport.getMidNightTime());
//		return overtimeInputCaculations;
		return null;
	}

	private List<OvertimeInputCaculation> convertMaptoList(Map<Integer, TimeWithCalculationImport> overTime,
			TimeWithCalculationImport flexTime, TimeWithCalculationImport midNightTime) {
		List<OvertimeInputCaculation> result = new ArrayList<>();

		for (Map.Entry<Integer, TimeWithCalculationImport> entry : overTime.entrySet()) {
			OvertimeInputCaculation overtimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value,
					entry.getKey(), entry.getValue().getCalTime());
			result.add(overtimeCal);
		}
		OvertimeInputCaculation flexTimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, 12,
				(flexTime.getCalTime() == null || flexTime.getCalTime() < 0) ? null : flexTime.getCalTime());
		OvertimeInputCaculation midNightTimeCal = new OvertimeInputCaculation(AttendanceType.NORMALOVERTIME.value, 11,
				midNightTime.getCalTime());
		result.add(flexTimeCal);
		result.add(midNightTimeCal);
		return result;
	}

	@Override
	public UseAtr preAppSetCheck(PrePostAtr prePostAtr, UseAtr preExcessDisplaySetting) {
		// Input．事前事後区分をチェック
		if(prePostAtr == PrePostAtr.PREDICT){
			return UseAtr.NOTUSE;
		}
		// Input．事前超過表示設定をチェック
		return preExcessDisplaySetting;
	}

	@Override
	public AppDateContradictionAtr actualSetCheck(AppDateContradictionAtr performanceExcessAtr, PrePostAtr prePostAtr) {
		// Input．事前事後区分チェック
		if(prePostAtr == PrePostAtr.PREDICT){
			return AppDateContradictionAtr.NOTCHECK;
		}
		// Input．実績超過区分チェック
		return performanceExcessAtr;
	}

	@Override
	public List<ConfirmMsgOutput> preAppExcessCheckHdApp(String employeeName, GeneralDate appDate,
			PreActualColorResult preActualColorResult, List<WorkdayoffFrame> breaktimeFrames) {
		List<ConfirmMsgOutput> outputLst = new ArrayList<>();
		// 計算結果の事前申請状態をチェック
		if(preActualColorResult.beforeAppStatus) {
			// Output．エラー情報　＝　確認メッセージ（Msg_1508）
			outputLst.add(new ConfirmMsgOutput("Msg_1508", Arrays.asList(employeeName)));
			return outputLst;
		}
		// 計算結果の事前申請超過があるかないかをチェック
		List<Integer> frameError = new ArrayList<>();
		for(OvertimeColorCheck overtimeColorCheck : preActualColorResult.resultLst) {
			if(overtimeColorCheck.preAppError != 0) {
				frameError.add(overtimeColorCheck.frameNo);
			}
		}
		if(CollectionUtil.isEmpty(frameError)) {
			return Collections.emptyList();
		}
		// Output．エラー情報　＝　確認メッセージ（Msg_424）
		String paramMsg = "";
		for(Integer frame : frameError) {
			String name = breaktimeFrames.stream().filter(x -> x.getWorkdayoffFrNo().v().intValue() == frame).findAny().get().getWorkdayoffFrName().v();
			paramMsg = paramMsg + name;
			if(frameError.indexOf(frame) < frameError.size() - 1) {
				paramMsg = paramMsg + ",";
			}
		}
		outputLst.add(new ConfirmMsgOutput("Msg_424", Arrays.asList(employeeName, paramMsg)));
		return outputLst;
	}

	@Override
	public List<ConfirmMsgOutput> achievementCheckHdApp(String employeeName, GeneralDate appDate,
			AppDateContradictionAtr performanceExcessAtr, PreActualColorResult preActualColorResult,
			List<WorkdayoffFrame> breaktimeFrames) {
		List<ConfirmMsgOutput> outputLst = new ArrayList<>();
		// 計算結果の実績状態をチェック
		if(preActualColorResult.actualStatus == ActualStatus.NO_ACTUAL.value) {
			// Input．実績超過区分をチェック
			if(performanceExcessAtr == AppDateContradictionAtr.CHECKNOTREGISTER) {
				// エラーメッセージ（Msg_1565）を表示する
				throw new BusinessException("Msg_1565", employeeName, appDate.toString(), "登録できません。");
			} 
			if(performanceExcessAtr == AppDateContradictionAtr.CHECKREGISTER) {
				// Output．エラー情報　＝　確認メッセージ（Msg_1565）
				outputLst.add(new ConfirmMsgOutput("Msg_1565", Arrays.asList(employeeName, appDate.toString(), "登録してもよろしいですか？")));
				return outputLst;
			}
		}
		// 計算結果の実績超過があるかないかをチェック
		List<Integer> frameError = new ArrayList<>();
		for(OvertimeColorCheck overtimeColorCheck : preActualColorResult.resultLst) {
			if(overtimeColorCheck.actualError != 0) {
				frameError.add(overtimeColorCheck.frameNo);
			}
		}
		if(CollectionUtil.isEmpty(frameError)) {
			return Collections.emptyList();
		}
		String paramMsg = "";
		for(Integer frame : frameError) {
			// String name = breaktimeFrames.stream().filter(x -> x.getWorkdayoffFrNo().v().intValue() == frame).findAny().get().getWorkdayoffFrName().v();
			// paramMsg = paramMsg + name;
			paramMsg = paramMsg + frame;
			if(frameError.indexOf(frame) < frameError.size() - 1) {
				paramMsg = paramMsg + ",";
			}
		}
		// Input．実績超過区分をチェック
		if(performanceExcessAtr == AppDateContradictionAtr.CHECKREGISTER) {
			// Output．エラー情報　＝　確認メッセージ（Msg_423）
			outputLst.add(new ConfirmMsgOutput("Msg_423", Arrays.asList(ApplicationType.HOLIDAY_WORK_APPLICATION.name, paramMsg, "登録してもよろしいですか？")));
			return outputLst;
		}
		// エラーメッセージ（Msg_1565）を表示する
		throw new BusinessException("Msg_423", ApplicationType.HOLIDAY_WORK_APPLICATION.name, paramMsg, "登録できません。");
	}

	@Override
	public List<ApplicationTime> calculator(String companyId, String employeeId, GeneralDate date, String workTypeCode,
			String workTimeCode, List<TimeZone> timeZones, List<BreakTimeSheet> breakTimes) {
		// 1日分の勤怠時間を仮計算 (RQ23) waiting for QA
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = new DailyAttendanceTimeCaculationImport(); 
		// 「申請時間<List>」をセットして返す pendding
		
		return Collections.emptyList();
	}
}
