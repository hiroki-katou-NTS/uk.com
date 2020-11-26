package nts.uk.ctx.at.request.dom.application.holidayworktime.commonalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.CheckBeforeOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkBreakTimeSetOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.ICommonAlgorithmOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.CheckWorkingInfoResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.OverTimeContent;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkHours;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWorkRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.ctx.at.shared.dom.worktype.service.JudgmentOneDayHoliday;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
public class CommonAlgorithmHolidayWorkImpl implements ICommonAlgorithmHolidayWork{
	
	@Inject
	private HolidayWorkAppSetRepository holidayWorkAppSetRepository;
	
	@Inject
	private AppReflectOtHdWorkRepository appReflectOtHdWorkRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private JudgmentOneDayHoliday judgmentOneDayHoliday;
	
	@Inject
	private ICommonAlgorithmOverTime commonAlgorithmOverTime;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private AbsenceTenProcessCommon absenceTenProcessCommon;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private RemainNumberTempRequireService requireService;

	@Override
	public HolidayWorkAppSet getHolidayWorkSetting(String companyId) {
		Optional<HolidayWorkAppSet> holidayWorkSetting = holidayWorkAppSetRepository.findSettingByCompany(companyId);
		return holidayWorkSetting.orElse(null);
	}

	@Override
	public AppReflectOtHdWork getHdWorkOvertimeReflect(String companyId) {
		Optional<AppReflectOtHdWork> hdWorkOvertimeReflect = appReflectOtHdWorkRepository.findByCompanyId(companyId);
		return hdWorkOvertimeReflect.orElse(null);
	}

	@Override
	public HdWorkDispInfoWithDateOutput getHdWorkDispInfoWithDateOutput(String companyId, String employeeId,
			Optional<GeneralDate> date, GeneralDate baseDate, PrePostInitAtr prePostAtr, AppEmploymentSet employmentSet,
			List<WorkTimeSetting> workTimeList, HolidayWorkAppSet holidayWorkSetting,
			AppReflectOtHdWork hdWorkOvertimeReflect, List<ActualContentDisplay> actualContentDisplayList) {
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = new HdWorkDispInfoWithDateOutput();
		
		//1-2.起動時勤務種類リストを取得する
		List<WorkType> workTypeList = new ArrayList<WorkType>();
		if(employmentSet.getTargetWorkTypeByAppLst() != null && !employmentSet.getTargetWorkTypeByAppLst().isEmpty()) {
			List<String> workTypeCodeList = employmentSet.getTargetWorkTypeByAppLst().stream()
				.filter(workTypeByApp -> workTypeByApp.getAppType().equals(ApplicationType.HOLIDAY_WORK_APPLICATION))
				.map(workTypeByApp -> workTypeByApp.getWorkTypeLst())
				.flatMap(List::stream)
				.distinct()
				.collect(Collectors.toList());
			workTypeList = workTypeRepository.findWorkTypeByCodes(companyId, workTypeCodeList, DeprecateClassification.NotDeprecated.value, WorkTypeUnit.OneDay.value);
			workTypeList.sort(Comparator.comparing(WorkType::getWorkTypeCode));
		} else {
			workTypeList = workTypeRepository.findWorkOneDay(companyId, DeprecateClassification.NotDeprecated.value, WorkTypeUnit.OneDay.value, WorkTypeClassification.HolidayWork.value);
		}
		hdWorkDispInfoWithDateOutput.setWorkTypeList(Optional.of(workTypeList));
		
		//1-3.起動時勤務種類・就業時間帯の初期選択		
		Optional<WorkingConditionItem> workingConditionItem = workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
		Optional<WorkTypeCode> initWorkTypeCd = Optional.empty();
		Optional<WorkTimeCode> initWorkTimeCd = Optional.empty();
		if(!actualContentDisplayList.isEmpty()) {
			//	休出の法定区分を取得
			Optional<HolidayAtr> holidayAtr = judgmentOneDayHoliday.getHolidayAtr(companyId, actualContentDisplayList.get(0).getOpAchievementDetail().isPresent() ? 
					actualContentDisplayList.get(0).getOpAchievementDetail().get().getWorkTypeCD() : null);
			//	休日日の勤務種類・就業時間帯の初期選択
			if(holidayAtr.isPresent() && workingConditionItem.isPresent()) {
				boolean hasSetting = false;
				
				switch(holidayAtr.get()) {
				case STATUTORY_HOLIDAYS:
					if(workingConditionItem.get().getWorkCategory().getInLawBreakTime().isPresent()) {
						initWorkTypeCd = workingConditionItem.get().getWorkCategory().getInLawBreakTime().get().getWorkTypeCode();
						initWorkTimeCd = workingConditionItem.get().getWorkCategory().getInLawBreakTime().get().getWorkTimeCode();
						hasSetting = true;
					}	
					break;
				case NON_STATUTORY_HOLIDAYS:
					if(workingConditionItem.get().getWorkCategory().getOutsideLawBreakTime().isPresent()) {
						initWorkTypeCd = workingConditionItem.get().getWorkCategory().getOutsideLawBreakTime().get().getWorkTypeCode();
						initWorkTimeCd = workingConditionItem.get().getWorkCategory().getOutsideLawBreakTime().get().getWorkTimeCode();
						hasSetting = true;
					}	
					break;
				case PUBLIC_HOLIDAY:
					if(workingConditionItem.get().getWorkCategory().getHolidayAttendanceTime().isPresent()) {
						initWorkTypeCd = workingConditionItem.get().getWorkCategory().getHolidayAttendanceTime().get().getWorkTypeCode();
						initWorkTimeCd = workingConditionItem.get().getWorkCategory().getHolidayAttendanceTime().get().getWorkTimeCode();
						hasSetting = true;
					}	
					break;
				default: break;
				}
				
				if(!hasSetting) {
					initWorkTimeCd = workingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTimeCode();
					
					Optional<HolidayAtr> noneSettingHolidayAtr = judgmentOneDayHoliday.getHolidayAtr(companyId, 
							workingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTypeCode().orElse(null).v());
					if(noneSettingHolidayAtr.isPresent()) {
						if(holidayAtr.equals(noneSettingHolidayAtr)) {
							initWorkTypeCd = workingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTypeCode();
						} else {
							//	指定する勤務種類リストから指定する休日区分の勤務種類を取得する
							Optional<WorkType> specifiedHdWorkType = Optional.empty(); 
							specifiedHdWorkType = workTypeList.stream().filter(workType -> !workType.getWorkTypeSetList().isEmpty() ? 
										workType.getWorkTypeSetList().get(0).getHolidayAtr().equals(holidayAtr.orElse(null)) : false).findFirst();
							if(specifiedHdWorkType.isPresent()) {
								initWorkTypeCd = Optional.of(specifiedHdWorkType.get().getWorkTypeCode());
							}
						}
					}
				}
			}
			if(!initWorkTypeCd.isPresent()) {
				initWorkTypeCd = Optional.of(workTypeList.get(0).getWorkTypeCode());
			}
			if(!initWorkTimeCd.isPresent()) {
				initWorkTimeCd = Optional.of(workTimeList.get(0).getWorktimeCode());
			}
		} else {
			initWorkTypeCd = workingConditionItem.isPresent() ? 
					workingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTypeCode() : Optional.empty();
			initWorkTimeCd = workingConditionItem.isPresent() ? 
					workingConditionItem.get().getWorkCategory().getHolidayWork().getWorkTimeCode() : Optional.empty();
		}
		
		Optional<WorkType> initWorkType = workTypeRepository.findByPK(companyId, initWorkTypeCd.orElse(null).v());
		Optional<WorkTimeSetting> initWorkTime = workTimeSettingRepository.findByCode(companyId, initWorkTimeCd.orElse(null).v());
		
		hdWorkDispInfoWithDateOutput.setInitWorkType(initWorkTypeCd);
		hdWorkDispInfoWithDateOutput.setInitWorkTime(initWorkTimeCd);
		hdWorkDispInfoWithDateOutput.setInitWorkTypeName(Optional.ofNullable(initWorkType.orElse(null).getName()));
		hdWorkDispInfoWithDateOutput.setInitWorkTimeName(Optional.ofNullable(initWorkTime.orElse(null).getWorkTimeDisplayName().getWorkTimeName()));
		
		//	初期表示する出退勤時刻を取得する
		OverTimeContent overTimeContent = this.getOverTimeContent(initWorkTypeCd, initWorkTimeCd, actualContentDisplayList);
		
		WorkHours workHours = commonAlgorithmOverTime.initAttendanceTime(companyId, date, overTimeContent, holidayWorkSetting.getApplicationDetailSetting());
		hdWorkDispInfoWithDateOutput.setWorkHours(workHours);
		
		//01-01_休憩時間を取得する
		HdWorkBreakTimeSetOutput hdWorkBreakTimeSetOutput = this.getBreakTime(companyId, ApplicationType.HOLIDAY_WORK_APPLICATION, initWorkTypeCd.orElse(null).v(), 
				initWorkTimeCd.orElse(null).v(), workHours.getStartTimeOp1(), workHours.getEndTimeOp1(), 
						UseAtr.toEnum(holidayWorkSetting.getApplicationDetailSetting().getTimeCalUse().value), 
						hdWorkOvertimeReflect.getHolidayWorkAppReflect().getAfter().getBreakLeaveApplication().getBreakReflectAtr().value==0 ? new Boolean(false) :new Boolean(true));
		hdWorkDispInfoWithDateOutput.setBreakTimeZoneSettingList(Optional.of(new BreakTimeZoneSetting(hdWorkBreakTimeSetOutput.getDeductionTimeLst())));
		
		//07-02_実績取得・状態チェック
		ApplicationTime applicationTime = preActualColorCheck.checkStatus(companyId, employeeId, date.orElse(null), ApplicationType.HOLIDAY_WORK_APPLICATION, 
				initWorkTypeCd.orElse(null), initWorkTimeCd.orElse(null), holidayWorkSetting.getOvertimeLeaveAppCommonSet().getOverrideSet(), 
				Optional.of(holidayWorkSetting.getCalcStampMiss()), hdWorkBreakTimeSetOutput.getDeductionTimeLst(), 
				!actualContentDisplayList.isEmpty() ? Optional.of(actualContentDisplayList.get(0)): Optional.empty());
		hdWorkDispInfoWithDateOutput.setActualApplicationTime(Optional.of(applicationTime));
		
		//10-2.代休の設定を取得する
		SubstitutionHolidayOutput subHolidayOutput = absenceTenProcessCommon.getSettingForSubstituteHoliday(companyId, employeeId, baseDate);
		if(subHolidayOutput != null) {
			hdWorkDispInfoWithDateOutput.setSubHdManage(subHolidayOutput.isSubstitutionFlg());
		}
		
		return hdWorkDispInfoWithDateOutput;
	}

	@Override
	public HdWorkBreakTimeSetOutput getBreakTime(String companyID, ApplicationType appType, String workTypeCD,
			String workTimeCD, Optional<TimeWithDayAttr> startTime, Optional<TimeWithDayAttr> endTime,
			UseAtr timeCalUse, Boolean breakTimeDisp) {
		HdWorkBreakTimeSetOutput result = new HdWorkBreakTimeSetOutput(false, Collections.emptyList());
		// 01-17_休憩時間帯を表示するか判断
		boolean displayRestTime = commonOvertimeHoliday.getRestTime(
				companyID,
				timeCalUse,
				breakTimeDisp,
				ApplicationType.HOLIDAY_WORK_APPLICATION);
		result.setDisplayRestTime(displayRestTime);
		//	休憩時間帯表示区分をチェック 
		if(displayRestTime) {
			//	休憩時間帯を取得する
			List<DeductionTime> deductionTimeLst = commonOvertimeHoliday.getBreakTimes(companyID, workTypeCD, workTimeCD, startTime, endTime);
			result.setDeductionTimeLst(deductionTimeLst);
		}
		return result;
	}

	public WorkContent getWorkContent(HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput) {
		WorkContent workContent = new WorkContent();
		workContent.setWorkTypeCode(hdWorkDispInfoWithDateOutput.getInitWorkType().isPresent()
				? Optional.of(hdWorkDispInfoWithDateOutput.getInitWorkType().get().v())
				: Optional.empty());
		workContent.setWorkTimeCode(hdWorkDispInfoWithDateOutput.getInitWorkTime().isPresent()
				? Optional.of(hdWorkDispInfoWithDateOutput.getInitWorkTime().get().v())
				: Optional.empty());
		TimeZone timeZoneNo1 = new TimeZone(hdWorkDispInfoWithDateOutput.getWorkHours().getStartTimeOp1().orElse(null),
				hdWorkDispInfoWithDateOutput.getWorkHours().getEndTimeOp1().orElse(null));
		TimeZone timeZoneNo2 = new TimeZone(hdWorkDispInfoWithDateOutput.getWorkHours().getStartTimeOp2().orElse(null),
				hdWorkDispInfoWithDateOutput.getWorkHours().getEndTimeOp2().orElse(null));
		List<TimeZone> timeZones = new ArrayList<TimeZone>();
		timeZones.add(timeZoneNo1);
		timeZones.add(timeZoneNo2);
		workContent.setTimeZones(timeZones);

		List<BreakTimeSheet> breakTimes = new ArrayList<BreakTimeSheet>();
		if (hdWorkDispInfoWithDateOutput.getBreakTimeZoneSettingList().isPresent()) {
			List<DeductionTime> timeZoneList = hdWorkDispInfoWithDateOutput.getBreakTimeZoneSettingList().get()
					.getTimeZones();
			for (int i = 1; i < timeZoneList.size() + 1 && i <= 10; i++) {
				TimeZone timeZone = timeZoneList.get(i - 1);
				BreakTimeSheet breakTimeSheet = new BreakTimeSheet(new BreakFrameNo(i), timeZone.getStart(),
						timeZone.getEnd());
				breakTimes.add(breakTimeSheet);
			}
		}
		workContent.setBreakTimes(breakTimes);
		return workContent;
	}

	public OverTimeContent getOverTimeContent(Optional<WorkTypeCode> workTypeCode, Optional<WorkTimeCode> workTimeCode, List<ActualContentDisplay> actualContentDisplayList) {
		OverTimeContent overTimeContent = new OverTimeContent();
		WorkHours workHoursSPR = new WorkHours();
		workHoursSPR.setStartTimeOp1(Optional.empty());
		workHoursSPR.setStartTimeOp2(Optional.empty());
		workHoursSPR.setEndTimeOp1(Optional.empty());
		workHoursSPR.setEndTimeOp2(Optional.empty());
		WorkHours workHoursActual = new WorkHours();
		if(!actualContentDisplayList.isEmpty()) {
			ActualContentDisplay actualContentDisplay = actualContentDisplayList.get(0);
			if (actualContentDisplay.getOpAchievementDetail().isPresent()) {
				AchievementDetail achievementDetail = actualContentDisplay.getOpAchievementDetail().get();
				if (achievementDetail.getOpWorkTime().isPresent()) {
					workHoursActual.setStartTimeOp1(Optional.of(new TimeWithDayAttr(achievementDetail.getOpWorkTime().get())));
				}
				if (achievementDetail.getOpLeaveTime().isPresent()) {
					workHoursActual.setEndTimeOp1(Optional.of(new TimeWithDayAttr(achievementDetail.getOpLeaveTime().get())));
				}
				if (achievementDetail.getOpWorkTime2().isPresent()) {
					workHoursActual.setStartTimeOp2(Optional.of(new TimeWithDayAttr(achievementDetail.getOpWorkTime2().get())));
				}
				if (achievementDetail.getOpDepartureTime2().isPresent()) {
					workHoursActual.setEndTimeOp2(Optional.of(new TimeWithDayAttr(achievementDetail.getOpDepartureTime2().get())));
				}
			}
		}
		
		overTimeContent.setWorkTypeCode(workTypeCode);
		overTimeContent.setWorkTimeCode(workTimeCode);
		overTimeContent.setSPRTime(Optional.of(workHoursSPR));
		overTimeContent.setActualTime(Optional.of(workHoursActual));
		return overTimeContent;
	}

	@Override
	public CheckBeforeOutput individualErrorCheck(boolean require, String companyId,
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput, AppHolidayWork appHolidayWork, Integer mode) {
		//	勤務種類、就業時間帯チェックのメッセージを表示
		CheckWorkingInfoResult checkWorkingInfoResult = otherCommonAlgorithm.checkWorkingInfo(companyId, 
				appHolidayWork.getWorkInformation().getWorkTypeCode().v(), appHolidayWork.getWorkInformation().getWorkTimeCode().v());
		if(checkWorkingInfoResult.isWkTypeError() && checkWorkingInfoResult.isWkTimeError()) {
			throw new BusinessException("Msg_1530", "勤務種類コード"+appHolidayWork.getWorkInformation().getWorkTypeCode().v()+","
					+"就業時間帯コード"+appHolidayWork.getWorkInformation().getWorkTimeCode().v());
		}else {
			if(checkWorkingInfoResult.isWkTypeError()) {
				throw new BusinessException("Msg_1530", "勤務種類コード"+appHolidayWork.getWorkInformation().getWorkTypeCode().v());
			}
			if(checkWorkingInfoResult.isWkTimeError()) {
				throw new BusinessException("Msg_1530", "就業時間帯コード"+appHolidayWork.getWorkInformation().getWorkTimeCode().v());
			}
		}
		
		//	計算ボタン未クリックチェック
		commonOvertimeHoliday.calculateButtonCheck(appHdWorkDispInfoOutput.getCalculationResult().orElse(null).getCalculatedFlag().value, 
				EnumAdaptor.valueOf(appHdWorkDispInfoOutput.getHolidayWorkAppSet().getApplicationDetailSetting().getTimeCalUse().value, UseAtr.class));
		
		//	休出時間のチェック
		int totalTime = 0;
		totalTime += appHolidayWork.getApplicationTime().getApplicationTime().stream().mapToInt(applicationTime -> applicationTime.getApplicationTime().v()).sum();
		if(appHolidayWork.getApplicationTime().getOverTimeShiftNight().isPresent()) {
			totalTime += appHolidayWork.getApplicationTime().getOverTimeShiftNight().get().getOverTimeMidNight().v();
			totalTime += appHolidayWork.getApplicationTime().getOverTimeShiftNight().get().getMidNightHolidayTimes().stream()
								.mapToInt(midNightHolidayTime -> midNightHolidayTime.getAttendanceTime().v()).sum();
		}
		if(totalTime <= 0) {
			throw new BusinessException("Msg_1654");
		}
		
		//	事前申請が必須か確認する
		appHdWorkDispInfoOutput.getHolidayWorkAppSet().getApplicationDetailSetting().checkAdvanceApp(ApplicationType.HOLIDAY_WORK_APPLICATION, 
				appHolidayWork.getApplication().getPrePostAtr(), Optional.empty(), 
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpPreAppContentDisplayLst()
					.orElse(Collections.emptyList()).get(0).getAppHolidayWork());
		
		//	事前申請・実績超過チェック
		List<ConfirmMsgOutput> confirmMsgOutputs = new ArrayList<ConfirmMsgOutput>();
		OverStateOutput overStateOutput = appHdWorkDispInfoOutput.getHolidayWorkAppSet().getOvertimeLeaveAppCommonSet()
				.checkPreApplication(appHolidayWork.getApplication().getPrePostAtr(), 
						Optional.ofNullable(appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput()
						.getOpPreAppContentDisplayLst().orElse(Collections.emptyList()).get(0).getAppHolidayWork().orElse(null).getApplicationTime()), 
						Optional.ofNullable(appHolidayWork.getApplicationTime()), 
						appHdWorkDispInfoOutput.getHdWorkDispInfoWithDateOutput().getActualApplicationTime());
		if(overStateOutput.getIsExistApp()) {
			confirmMsgOutputs.add(new ConfirmMsgOutput("Msg_1508", Collections.emptyList()));
		} else {
			OutDateApplication outDateApplication = overStateOutput.getAdvanceExcess();
			boolean testExcessStateDetail = !outDateApplication.getExcessStateDetail().stream()
					.filter(excessStateDetail -> excessStateDetail.getExcessState().equals(ExcessState.EXCESS_ALARM)).collect(Collectors.toList()).isEmpty();
			boolean testExcessStateMidnight = !outDateApplication.getExcessStateMidnight().stream()
					.filter(excessStateMidnight -> excessStateMidnight.getExcessState().equals(ExcessState.EXCESS_ALARM)).collect(Collectors.toList()).isEmpty();
			boolean testFlex = outDateApplication.getFlex().equals(ExcessState.EXCESS_ALARM);
			boolean testOverTimeLate = outDateApplication.getOverTimeLate().equals(ExcessState.EXCESS_ALARM);
			if(testExcessStateDetail || testExcessStateMidnight || testFlex || testOverTimeLate) {
				outDateApplication.getExcessStateDetail().stream()
				.filter(excessStateDetail -> excessStateDetail.getType().equals(AttendanceType_Update.NORMALOVERTIME) 
						&& excessStateDetail.getExcessState().equals(ExcessState.EXCESS_ALARM))
				.map(excessStateDetail -> {
					return excessStateDetail;
				}).collect(Collectors.toList());
				confirmMsgOutputs.add(new ConfirmMsgOutput("Msg_424", 
						Arrays.asList(appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid()))); //huytodo
			}
		}
		switch(overStateOutput.getAchivementStatus()) {
			case EXCESS_ERROR:
				throw new BusinessException("Msg_1746");//huytodo
			case EXCESS_ALARM:
				confirmMsgOutputs.add(new ConfirmMsgOutput("Msg_1745", Collections.emptyList()));//huytodo
				break;
			case NO_EXCESS:
				OutDateApplication outDateApplication = overStateOutput.getAchivementExcess();
				if(!outDateApplication.getExcessStateDetail().stream()
						.filter(excessStateDetail -> excessStateDetail.getExcessState().equals(ExcessState.EXCESS_ERROR)).collect(Collectors.toList()).isEmpty()
						|| !outDateApplication.getExcessStateMidnight().stream()
						.filter(excessStateMidnight -> excessStateMidnight.getExcessState().equals(ExcessState.EXCESS_ERROR)).collect(Collectors.toList()).isEmpty()
						|| outDateApplication.getFlex().equals(ExcessState.EXCESS_ERROR)
						|| outDateApplication.getOverTimeLate().equals(ExcessState.EXCESS_ERROR)) {
					throw new BusinessException("Msg_1748"); //huytodo
				} else {
					if(!outDateApplication.getExcessStateDetail().stream()
							.filter(excessStateDetail -> excessStateDetail.getExcessState().equals(ExcessState.EXCESS_ALARM)).collect(Collectors.toList()).isEmpty()
							|| !outDateApplication.getExcessStateMidnight().stream()
							.filter(excessStateMidnight -> excessStateMidnight.getExcessState().equals(ExcessState.EXCESS_ALARM)).collect(Collectors.toList()).isEmpty()
							|| outDateApplication.getFlex().equals(ExcessState.EXCESS_ALARM)
							|| outDateApplication.getOverTimeLate().equals(ExcessState.EXCESS_ALARM)) {
						confirmMsgOutputs.add(new ConfirmMsgOutput("Msg_1747", Collections.emptyList()));//huytodo
					}
				}
				break;
				default: break;
		}
		
		//	申請時の乖離時間をチェックする
		overtimeService.checkDivergenceTime(require, ApplicationType.HOLIDAY_WORK_APPLICATION, 
				Optional.empty(), Optional.of(appHolidayWork), appHdWorkDispInfoOutput.getHolidayWorkAppSet().getOvertimeLeaveAppCommonSet());
		
		//	社員に対応する締め期間を取得する
		val requireM3 = requireService.createRequire();
        val cacheCarrier = new CacheCarrier();
        DatePeriod closingPeriod = ClosureService.findClosurePeriod(requireM3, cacheCarrier, appHolidayWork.getApplication().getEmployeeID(), 
        		appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate());
		return null;
	}

}
