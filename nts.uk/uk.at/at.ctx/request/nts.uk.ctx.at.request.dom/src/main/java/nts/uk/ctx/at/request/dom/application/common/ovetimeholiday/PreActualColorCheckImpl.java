package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport_Old;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TrackRecordAtr;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.FrameNo;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class PreActualColorCheckImpl implements PreActualColorCheck {
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	public RangeOfDayTimeZoneService rangeOfDayTimeZoneService;
	
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private AppHolidayWorkRepository_Old appHolidayWorkRepository;

	@Override
	public PreActualColorResult preActualColorCheck(UseAtr preExcessDisplaySetting, AppDateContradictionAtr performanceExcessAtr,
			ApplicationType appType, PrePostAtr prePostAtr, List<OvertimeInputCaculation> calcTimeList, List<OvertimeColorCheck> overTimeLst,
			Optional<Application> opAppBefore, boolean beforeAppStatus, List<OvertimeColorCheck> actualLst, ActualStatus actualStatus) {
		PreActualColorResult result = new PreActualColorResult();
		// アルゴリズム「チェック条件」を実行する
		UseAtr preAppSetCheck = commonOvertimeHoliday.preAppSetCheck(prePostAtr, preExcessDisplaySetting);
		// 実績超過チェック　＝　「03-02-1_チェック条件」を取得
		AppDateContradictionAtr actualSetCheck = commonOvertimeHoliday.actualSetCheck(performanceExcessAtr, prePostAtr);
		for(OvertimeColorCheck overtimeColorCheck : overTimeLst){
			// 入力値をチェックする
			int compareCalc = 0;
			if(overtimeColorCheck.appTime!=null) {
				compareCalc = overtimeColorCheck.appTime;
			}
			// ループ中の枠に対する計算値が存在するかチェックする
			Optional<OvertimeInputCaculation> opOvertimeInputCaculation = calcTimeList.stream()
					.filter(x -> x.getAttendanceID()==overtimeColorCheck.attendanceID && x.getFrameNo()==overtimeColorCheck.frameNo).findAny();
			if(opOvertimeInputCaculation.isPresent()){
				// 計算値チェック
				if(opOvertimeInputCaculation.get().getResultCaculation()!=compareCalc){
					overtimeColorCheck.calcError = PreActualError.CALC_ERROR.value;
				} else {
					overtimeColorCheck.calcError = PreActualError.NO_ERROR.value;
				}
				overtimeColorCheck.appTime = opOvertimeInputCaculation.get().getResultCaculation();
			}
			// アルゴリズム「枠別事前申請超過チェック」を実行する
			preAppErrorCheck(appType, overtimeColorCheck, opAppBefore, preAppSetCheck);
			// アルゴリズム「枠別実績超過チェック」を実行する
			actualErrorCheck(overtimeColorCheck, actualLst, actualSetCheck);
		}
		result.beforeAppStatus = beforeAppStatus;
		if (null != actualStatus) {
			result.actualStatus = actualStatus.value;
		}
		result.resultLst = overTimeLst;
		return result;
	}

	@Override
	public PreAppCheckResult preAppStatusCheck(String companyID, String employeeID, GeneralDate appDate, ApplicationType appType) {
		PreAppCheckResult preAppCheckResult = new PreAppCheckResult();
		// ドメインモデル「申請」を取得(lây domain "đơn xin")
//		List<Application_New> appBeforeLst = applicationRepository.getBeforeApplication(companyID, employeeID, appDate, appType.value, PrePostAtr.PREDICT.value);
//		Optional<Application_New> opAppBefore = CollectionUtil.isEmpty(appBeforeLst) ? Optional.empty() : Optional.of(appBeforeLst.get(0)); 
//		// 事前申請漏れチェック
//		if(!opAppBefore.isPresent()){
//			preAppCheckResult.beforeAppStatus = true;
//			preAppCheckResult.opAppBefore = Optional.empty();
//			return preAppCheckResult;
//		}
//		preAppCheckResult.opAppBefore = opAppBefore;
//		Application_New appBefore = opAppBefore.get();
//		ReflectedState_New refPlan = appBefore.getReflectionInformation()
//				.getStateReflectionReal();
//		// 事前申請否認チェック
//		if (refPlan.equals(ReflectedState_New.DENIAL) || refPlan.equals(ReflectedState_New.REMAND)) {
//			preAppCheckResult.beforeAppStatus = true;
//			return preAppCheckResult;
//		}
		return preAppCheckResult;
	}

	@Override
	public ActualStatusCheckResult actualStatusCheck(String companyID, String employeeID, GeneralDate appDate, ApplicationType appType,
			String workType, String workTime, OverrideSet overrideSet, Optional<CalcStampMiss> calStampMiss, List<DeductionTime> deductionTimeLst) {
		List<OvertimeColorCheck> actualLst = new ArrayList<>();
		// Imported(申請承認)「勤務実績」を取得する
		RecordWorkInfoImport_Old recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, appDate);
		if(Strings.isBlank(recordWorkInfoImport.getWorkTypeCode())){
			return new ActualStatusCheckResult(ActualStatus.NO_ACTUAL, "", "", null, null, Collections.emptyList());
		}
		// アルゴリズム「勤務分類変更の判定」を実行する
		JudgmentWorkTypeResult judgmentWorkTypeResult = judgmentWorkTypeChange(companyID, appType, recordWorkInfoImport.getWorkTypeCode(), workType);
		// アルゴリズム「就業時間帯変更の判定」を実行する
		JudgmentWorkTimeResult judgmentWorkTimeResult = judgmentWorkTimeChange(recordWorkInfoImport.getWorkTimeCode(), workTime);
		// アルゴリズム「当日判定」を実行する
		boolean isToday = judgmentToday(appDate, judgmentWorkTimeResult.getCalcWorkTime());
		// アルゴリズム「打刻漏れと退勤打刻補正の判定」を実行する
		JudgmentStampResult judgmentStampResult = judgmentStamp(isToday, overrideSet, calStampMiss, 
				recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst(), appDate);
		// アルゴリズム「実績状態の判定」を実行する
		ActualStatus actualStatus = judgmentActualStatus(judgmentStampResult.isMissStamp(), judgmentStampResult.isStampLeaveChange());
		// アルゴリズム「仮計算実行の判定」を実行する
		boolean calcExecution = judgmentCalculation(actualStatus, 
				judgmentWorkTypeResult.isWorkTypeChange(), 
				judgmentStampResult.isStampLeaveChange(), 
				judgmentWorkTimeResult.isWorkTimeChange());
		if(calcExecution){
			List<Integer> breakStartTime = new ArrayList<>();
			List<Integer> breakEndTime = new ArrayList<>();
			if(CollectionUtil.isEmpty(deductionTimeLst)) {
				List<DeductionTime> deductionTimes = commonOvertimeHoliday.getBreakTimes(companyID, workType, workTime, Optional.empty(), Optional.empty());
				breakStartTime = deductionTimes.stream().map(x -> x.getStart().v()).collect(Collectors.toList());
				breakEndTime = deductionTimes.stream().map(x -> x.getEnd().v()).collect(Collectors.toList());
			} else {
				breakStartTime = deductionTimeLst.stream().map(x -> x.getStart().v()).collect(Collectors.toList());
				breakEndTime = deductionTimeLst.stream().map(x -> x.getEnd().v()).collect(Collectors.toList());
			}
			
			// アルゴリズム「1日分の勤怠時間を仮計算」を実行する
			DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = 
					new DailyAttendanceTimeCaculationImport();
//					dailyAttendanceTimeCaculation.getCalculation(
//							employeeID, 
//							appDate, 
//							judgmentWorkTypeResult.getCalcWorkType(), 
//							judgmentWorkTimeResult.getCalcWorkTime(), 
//							recordWorkInfoImport.getAttendanceStampTimeFirst(), 
//							judgmentStampResult.getCalcLeaveStamp(), 
//							breakStartTime, 
//							breakEndTime);
			if(appType==ApplicationType.OVER_TIME_APPLICATION) {
				actualLst.addAll(dailyAttendanceTimeCaculationImport.getOverTime().entrySet()
						.stream().map(x -> OvertimeColorCheck.createActual(1, x.getKey(), x.getValue().getCalTime())).collect(Collectors.toList()));
				actualLst.add(OvertimeColorCheck.createActual(1, 11, dailyAttendanceTimeCaculationImport.getMidNightTime().getCalTime()));
				actualLst.add(OvertimeColorCheck.createActual(1, 12, dailyAttendanceTimeCaculationImport.getFlexTime().getCalTime()));
				actualLst.addAll(dailyAttendanceTimeCaculationImport.getBonusPayTime().entrySet()
						.stream().map(x -> OvertimeColorCheck.createActual(3, x.getKey(), x.getValue())).collect(Collectors.toList()));
				actualLst.addAll(dailyAttendanceTimeCaculationImport.getSpecBonusPayTime().entrySet()
						.stream().map(x -> OvertimeColorCheck.createActual(4, x.getKey(), x.getValue())).collect(Collectors.toList()));
			} else {
				actualLst.addAll(dailyAttendanceTimeCaculationImport.getHolidayWorkTime().entrySet()
						.stream().map(x -> OvertimeColorCheck.createActual(2, x.getKey(), x.getValue().getCalTime())).collect(Collectors.toList()));
				actualLst.addAll(dailyAttendanceTimeCaculationImport.getBonusPayTime().entrySet()
						.stream().map(x -> OvertimeColorCheck.createActual(3, x.getKey(), x.getValue())).collect(Collectors.toList()));
			}
		} else {
			/*if(appType==ApplicationType_Old.OVER_TIME_APPLICATION) {
				actualLst.addAll(recordWorkInfoImport.getOvertimeCaculation().stream()
						.map(x -> OvertimeColorCheck.createActual(x.getAttendanceID(), x.getFrameNo(), x.getResultCaculation())).collect(Collectors.toList()));
				actualLst.add(OvertimeColorCheck.createActual(1, 11, recordWorkInfoImport.getShiftNightCaculation()));
				actualLst.add(OvertimeColorCheck.createActual(1, 12, recordWorkInfoImport.getFlexCaculation()));
			} else {
				actualLst.addAll(recordWorkInfoImport.getOvertimeHolidayCaculation().stream()
						.map(x -> OvertimeColorCheck.createActual(x.getAttendanceID(), x.getFrameNo(), x.getResultCaculation())).collect(Collectors.toList()));
			}*/
		}
		return new ActualStatusCheckResult(
				actualStatus, 
				judgmentWorkTypeResult.getCalcWorkType(), 
				judgmentWorkTimeResult.getCalcWorkTime(),
				recordWorkInfoImport.getAttendanceStampTimeFirst(),
				judgmentStampResult.getCalcLeaveStamp(),
				actualLst);
	}

	@Override
	public boolean judgmentToday(GeneralDate appDate, String workTime) {
		// 1日の範囲を時間帯で返す
		TimeSpanForCalc timeSpanForCalc = rangeOfDayTimeZoneService.getRangeofOneDay(workTime);
		GeneralDateTime appDateRangeStart = null;
		GeneralDateTime appDateRangeEnd = null;
		if(timeSpanForCalc==null) {
			// 申請日の範囲を作成する
			appDateRangeStart = GeneralDateTime.fromString(appDate.toString("yyyy/MM/dd")+" 00:00", "yyyy/MM/dd HH:mm");
			appDateRangeEnd = GeneralDateTime.fromString(appDate.toString("yyyy/MM/dd")+" 23:59", "yyyy/MM/dd HH:mm");
		} else {
			// 1日の範囲から申請日の範囲を作成する
			appDateRangeStart = getAppDateRange(timeSpanForCalc.getStart(), appDate);
			appDateRangeEnd = getAppDateRange(timeSpanForCalc.getEnd(), appDate);
		}
		GeneralDateTime sysDate = GeneralDateTime.now();
		// システム日時が申請日の範囲内に含まれるかをチェックする
		if (sysDate.afterOrEquals(appDateRangeStart) && sysDate.beforeOrEquals(appDateRangeEnd)) {
			return true;
		}
		return false;
	}
	
	// 1日の範囲から申請日の範囲を作成する
	private GeneralDateTime getAppDateRange(TimeWithDayAttr timeSpan, GeneralDate appDate) {
		if (timeSpan.getDayDivision().value == 0) {
			return GeneralDateTime.ymdhms(appDate.addDays(-1).year(), appDate.addDays(-1).month(),
					appDate.addDays(-1).day(), timeSpan.hour(), timeSpan.minute(), 0);
		}
		if (timeSpan.getDayDivision().value == 1) {
			return GeneralDateTime.ymdhms(appDate.year(), appDate.month(), appDate.day(), timeSpan.hour(),
					timeSpan.minute(), 0);
		}
		if (timeSpan.getDayDivision().value == 2) {
			return GeneralDateTime.ymdhms(appDate.addDays(1).year(), appDate.addDays(1).month(),
					appDate.addDays(1).day(), timeSpan.hour(), timeSpan.minute(), 0);
		}
		if (timeSpan.getDayDivision().value == 3) {
			return GeneralDateTime.ymdhms(appDate.addDays(2).year(), appDate.addDays(2).month(),
					appDate.addDays(2).day(), timeSpan.hour(), timeSpan.minute(), 0);
		}
		return null;
	}

	@Override
	public JudgmentWorkTypeResult judgmentWorkTypeChange(String companyID, ApplicationType appType, String actualWorkType,
			String screenWorkType) {
		// 勤務分類変更
		boolean workTypeChange = false;
		// 計算勤務種類
		String calcWorkType = "";
		// 申請種類をチェックする
		if(appType==ApplicationType.OVER_TIME_APPLICATION){
			workTypeChange = false;
		}
		// 画面に勤務種類が表示されているかチェックする
		if(Strings.isBlank(screenWorkType)){
			calcWorkType = actualWorkType;
		} else {
			calcWorkType = screenWorkType;
		}
		return new JudgmentWorkTypeResult(workTypeChange, calcWorkType);
	}

	@Override
	public JudgmentWorkTimeResult judgmentWorkTimeChange(String actualWorkTime, String screenWorkTime) {
		// 就業時間帯変更
		boolean workTimeChange = false;
		// 計算就業時間帯
		String calcWorkTime = "";
		// 画面に就業時間帯が表示されているかチェックする
		if(Strings.isBlank(screenWorkTime)){
			calcWorkTime = actualWorkTime;
			workTimeChange = false;
			return new JudgmentWorkTimeResult(workTimeChange, calcWorkTime);
		}
		calcWorkTime = screenWorkTime;
		// 就業時間帯が変更されているかチェックする
		if(Strings.isNotBlank(actualWorkTime) && screenWorkTime.equals(actualWorkTime)){
			workTimeChange = false;
		} else {
			workTimeChange = true;
		}
		return new JudgmentWorkTimeResult(workTimeChange, calcWorkTime);
	}

	@Override
	public JudgmentStampResult judgmentStamp(boolean isToday, OverrideSet overrideSet, Optional<CalcStampMiss> calStampMiss,
			Integer startTime, Integer endTime, GeneralDate appDate) {
		// 打刻漏れフラグ 
		boolean missStamp = false;
		// 退勤打刻補正 
		boolean stampLeaveChange = false;
		// 計算退勤時刻 
		Integer calcLeaveStamp = 0;
		if(startTime!=null&&endTime!=null){
			// 打刻漏れ：false
			missStamp = false;
			// アルゴリズム「退勤打刻補正の判定(実績あり)」を実行する
			stampLeaveChange = judgmentStampTimeFull(isToday, overrideSet);
		} else {
			// 打刻漏れ：true
			missStamp = true;
			if(startTime==null){
				// 退勤打刻補正：しない
				stampLeaveChange = false;
			} else {
				// アルゴリズム「退勤打刻補正の判定(打刻漏れ)」を実行する
				stampLeaveChange = judgmentStampTimeMiss(isToday, calStampMiss);
			}
		}
		// 退勤打刻補正をチェックする
		if(stampLeaveChange){
			// 計算退勤時刻：システム時刻
			int systemTime = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
			int compareDate = GeneralDate.today().compareTo(appDate);
			if(compareDate > 0) {
				systemTime = 24 * 60 + systemTime;
			}
			calcLeaveStamp = systemTime;
		} else {
			// 計算退勤時刻：INPUT.退勤時刻
			calcLeaveStamp = endTime;
		}
		return new JudgmentStampResult(missStamp, stampLeaveChange, calcLeaveStamp);
	}

	@Override
	public boolean judgmentStampTimeFull(boolean isToday, OverrideSet overrideSet) {
		// 当日かどうかチェックする
		if(!isToday){
			return false;
		}
		// 退勤時刻優先設定チェック
		if(overrideSet==OverrideSet.SYSTEM_TIME_PRIORITY){
			return true;
		}
		return false;
	}

	@Override
	public boolean judgmentStampTimeMiss(boolean isToday, Optional<CalcStampMiss> calStampMiss) {
		// 当日かどうかチェックする
		if(!isToday){
			return false;
		}
		// 退勤打刻漏れ補正設定チェック
		if((!calStampMiss.isPresent())||calStampMiss.get()==CalcStampMiss.CALC_STAMP_MISS){
			return true;
		}
		return false;
	}

	@Override
	public ActualStatus judgmentActualStatus(boolean missStamp, boolean stampLeaveChange) {
		if(!missStamp){
			// 実績状態：実績あり
			return ActualStatus.ACTUAL;
		}
		if(stampLeaveChange){
			// 実績状態：打刻漏れ(補正済)
			return ActualStatus.FIXED_STAMP_MISS;
		}
		// 実績状態：打刻漏れ
		return ActualStatus.STAMP_MISS;
	}

	@Override
	public boolean judgmentCalculation(ActualStatus actualStatus, boolean workTypeChange, boolean stampLeaveChange,
			boolean workTimeChange) {
		// 実績状態をチェックする
		if(actualStatus==ActualStatus.FIXED_STAMP_MISS){
			return true;
		}
		if(actualStatus!=ActualStatus.ACTUAL){
			return false;
		}
		// 各種区分をチェックする
		if(workTypeChange||stampLeaveChange||workTimeChange){
			return true;
		}
		return false;
	}

	@Override
	public void preAppErrorCheck(ApplicationType appType, OvertimeColorCheck overtimeColorCheck, Optional<Application> opAppBefore, UseAtr preAppSetCheck) {
		String companyID = AppContexts.user().companyId();
		int compareValue = 0;
		// 事前申請をチェックする
		if(null != opAppBefore && opAppBefore.isPresent()){
			Application appBefore = opAppBefore.get();
			// 申請種類をチェックする
			if(appType==ApplicationType.OVER_TIME_APPLICATION){
				AppOverTime_Old appOverTime = overtimeRepository.getFullAppOvertime(companyID, appBefore.getAppID()).get();
				List<OverTimeInput> overTimeInputLst = appOverTime.getOverTimeInput();
				if(!CollectionUtil.isEmpty(overTimeInputLst)) {
					Optional<OverTimeInput> opOverTimeInput = overTimeInputLst
							.stream().filter(x -> x.getAttendanceType().value==overtimeColorCheck.attendanceID && x.getFrameNo()==overtimeColorCheck.frameNo).findAny();
					if(opOverTimeInput.isPresent()){
						// 事前申請時間に勤怠種類・枠NOに応じた時間を設定する
						if(opOverTimeInput.get().getApplicationTime()!=null) {
							overtimeColorCheck.preAppTime = opOverTimeInput.get().getApplicationTime().v();
							compareValue = overtimeColorCheck.preAppTime;
						}
					}
				}
			} else {
				AppHolidayWork_Old appHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, appBefore.getAppID()).get();
				List<HolidayWorkInput> holidayWorkInputLst = appHolidayWork.getHolidayWorkInputs();
				if(!CollectionUtil.isEmpty(holidayWorkInputLst)) {
					Optional<HolidayWorkInput> holidayWorkInput = holidayWorkInputLst
							.stream().filter(x -> x.getAttendanceType().value==overtimeColorCheck.attendanceID && x.getFrameNo()==overtimeColorCheck.frameNo).findAny();
					if(holidayWorkInput.isPresent()){
						// 事前申請時間に勤怠種類・枠NOに応じた時間を設定する
						if(holidayWorkInput.get().getApplicationTime()!=null) {
							overtimeColorCheck.preAppTime = holidayWorkInput.get().getApplicationTime().v();
							compareValue = overtimeColorCheck.preAppTime;
						}
					}
				}
			}
		}
		// 事前申請超過チェックをする必要があるかチェックする
		if(preAppSetCheck==UseAtr.USE){
			// 事前申請超過チェック
			if(overtimeColorCheck.appTime !=null && overtimeColorCheck.appTime > compareValue){
				overtimeColorCheck.preAppError = PreActualError.PRE_ERROR.value;
			} else {
				overtimeColorCheck.preAppError = PreActualError.NO_ERROR.value;
			}
		}
	}

	@Override
	public void actualErrorCheck(OvertimeColorCheck overtimeColorCheck, List<OvertimeColorCheck> actualLst, AppDateContradictionAtr actualSetCheck) {
		int compareValue = 0;
		// 実績をチェックする
		if(!CollectionUtil.isEmpty(actualLst)){
			Optional<OvertimeColorCheck> opActual = actualLst.stream()
					.filter(x -> x.attendanceID==overtimeColorCheck.attendanceID&&x.frameNo==overtimeColorCheck.frameNo).findAny();
			if(opActual.isPresent()){
				// 実績時間に勤怠種類・枠NOに応じた時間を設定する
				overtimeColorCheck.actualTime = opActual.get().actualTime;
				if(overtimeColorCheck.actualTime!=null) {
					compareValue = overtimeColorCheck.actualTime;
				}
			}
		}
		// 実績超過チェックをする必要があるかチェックする
		if(actualSetCheck!=AppDateContradictionAtr.NOTCHECK){
			// 実績時間チェック
			if(overtimeColorCheck.appTime!=null && overtimeColorCheck.appTime > compareValue){
				if(actualSetCheck==AppDateContradictionAtr.CHECKNOTREGISTER) {
					overtimeColorCheck.actualError = PreActualError.ACTUAL_ERROR.value;
				} else {
					overtimeColorCheck.actualError = PreActualError.ACTUAL_ALARM.value;
				}
			} else {
				overtimeColorCheck.actualError = PreActualError.NO_ERROR.value;
			}
		}
	}

	@Override
	public ApplicationTime checkStatus(
			String companyId,
			String employeeId,
			GeneralDate date,
			ApplicationType appType,
			WorkTypeCode workTypeCode,
			WorkTimeCode workTimeCode,
			OverrideSet overrideSet,
			Optional<CalcStampMiss> calOptional,
			List<DeductionTime> breakTimes,
			Optional<ActualContentDisplay> acuActualContentDisplay) {
		ApplicationTime output = new ApplicationTime();
		// INPUT．「表示する実績内容．実績詳細」をチェックする
		Optional<AchievementDetail> opAchievementDetail = acuActualContentDisplay.map(x -> x.getOpAchievementDetail()).orElse(Optional.empty());
		
		if (!(opAchievementDetail.isPresent() && opAchievementDetail.get().getTrackRecordAtr() == TrackRecordAtr.DAILY_RESULTS)) {
			
			return output;
		}
		AchievementDetail achievementDetail = opAchievementDetail.get();
		// INPUT．「表示する実績内容．実績詳細」 <> empty　AND　INPUT．「表示する実績内容．実績詳細．実績スケ区分」 = 日別実績 -> true
		// アルゴリズム「勤務分類変更の判定」を実行する
		JudgmentWorkTypeResult judgmentWorkTypeResult = this.judgmentWorkTypeChange(companyId, appType, achievementDetail.getWorkTypeCD(), workTypeCode.v());
		// アルゴリズム「就業時間帯変更の判定」を実行する
		JudgmentWorkTimeResult judgmentWorkTimeResult = this.judgmentWorkTimeChange(achievementDetail.getWorkTimeCD(), workTimeCode.v());
		
		// アルゴリズム「当日判定」を実行する
		Boolean isJudgmentToday = this.judgmentToday(date, workTimeCode.v());
		// アルゴリズム「打刻漏れと退勤打刻補正の判定」を実行する
		JudgmentStampResult judgmentStampResult = this.judgmentStamp(isJudgmentToday, overrideSet, calOptional, achievementDetail.getOpWorkTime().orElse(null), achievementDetail.getOpLeaveTime().orElse(null), date);
		// アルゴリズム「実績状態の判定」を実行する
		ActualStatus actualStatus = this.judgmentActualStatus(judgmentStampResult.isMissStamp(), judgmentStampResult.isStampLeaveChange());
		// アルゴリズム「仮計算実行の判定」を実行する
		Boolean isJudgmentCalculation =  this.judgmentCalculation(actualStatus,
				judgmentWorkTypeResult.isWorkTypeChange(),
				judgmentStampResult.isStampLeaveChange(),
				judgmentWorkTimeResult.isWorkTimeChange());
		
		if (!isJudgmentCalculation) { // 仮計算実行＝しない
			// 「申請時間<List>」をセットして返す
			OverTimeShiftNight overTimeShiftNight = new OverTimeShiftNight();
			// 表示する実績内容．実績詳細．7勤怠時間．4勤怠種類 = 残業時間
			
			if (achievementDetail.getOpOvertimeLeaveTimeLst().isPresent()) {
				List<OvertimeLeaveTime> overTimeLeaveTimes = achievementDetail.getOpOvertimeLeaveTimeLst().get().stream()
						.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME.value || x.getAttendanceType() == AttendanceType_Update.BREAKTIME.value)
						.collect(Collectors.toList());
				List<OvertimeApplicationSetting> overTimeApplicationTimes = new ArrayList<>();
				overTimeLeaveTimes.forEach(item -> {
					OvertimeApplicationSetting overtimeApplicationSetting = new OvertimeApplicationSetting();
					overtimeApplicationSetting.setAttendanceType(EnumAdaptor.valueOf(item.getAttendanceType(), AttendanceType_Update.class));
					overtimeApplicationSetting.setFrameNo(new FrameNo(item.getFrameNo()));
					overtimeApplicationSetting.setApplicationTime(new TimeWithDayAttr(item.getTime()));
					overTimeApplicationTimes.add(overtimeApplicationSetting);
					
				});
				/*
				・INPUT．「表示する実績内容．実績詳細．7勤怠時間．4勤怠種類 = 残業時間」AND 「実績詳細．7勤怠時間．1枠NO = 11」がある場合：
						　申請時間．フレックス超過時間 = 実績詳細．7勤怠時間．3時間
				*/
				Optional<OvertimeLeaveTime> isFlexOverOp = achievementDetail.getOpOvertimeLeaveTimeLst().get().stream()
						.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME.value || x.getFrameNo() == 11)
						.findFirst();
				if (isFlexOverOp.isPresent()) {
					output.setFlexOverTime(Optional.of(new AttendanceTimeOfExistMinus(isFlexOverOp.get().getTime())));
				}
				/*
				 ・INPUT．「表示する実績内容．実績詳細．7勤怠時間．4勤怠種類 = 残業時間」AND 「実績詳細．7勤怠時間．1枠NO = 12」がある場合：
　					申請時間．就業時間外深夜時間．残業深夜時間 = 実績詳細．7勤怠時間．3時間
				 * */
				Optional<OvertimeLeaveTime> isOverTimeMidNightOp = achievementDetail.getOpOvertimeLeaveTimeLst().get().stream()
						.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME.value || x.getFrameNo() == 12)
						.findFirst();
				if (isOverTimeMidNightOp.isPresent()) {
					overTimeShiftNight.setMidNightOutSide(
							new AttendanceTime(isOverTimeMidNightOp.get().getTime()));
					output.setOverTimeShiftNight(Optional.of(overTimeShiftNight));
				}
				
				
			}
			List<HolidayMidNightTime> midNightHolidayTimes = new ArrayList<HolidayMidNightTime>();
			/**
			 * ・INPUT．「表示する実績内容．実績詳細．法内休出深夜時間」がある場合：
　				申請時間．就業時間外深夜時間．休出深夜時間．法定区分 = 法定内休出
　				申請時間．就業時間外深夜時間．休出深夜時間．時間 = 実績詳細．法内休出深夜時間

			 */
			if (achievementDetail.getOpInlawHolidayMidnightTime().isPresent()) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime(
						achievementDetail.getOpInlawHolidayMidnightTime().get(),
						StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			/**
			 * ・INPUT．「表示する実績内容．実績詳細．法外休出深夜時間」がある場合：
　				申請時間．就業時間外深夜時間．休出深夜時間．法定区分 = 法定外休出
　				申請時間．就業時間外深夜時間．休出深夜時間．時間 = 実績詳細．法外休出深夜時間

			 */
			if (achievementDetail.getOpOutlawHolidayMidnightTime().isPresent()) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime(
						achievementDetail.getOpOutlawHolidayMidnightTime().get(),
						StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			
			/**
			 * 
				・INPUT．「表示する実績内容．実績詳細．祝日休出深夜時間」がある場合：
　				申請時間．就業時間外深夜時間．休出深夜時間．法定区分 = 祝日休出
　				申請時間．就業時間外深夜時間．休出深夜時間．時間 = 実績詳細．祝日休出深夜時間
			 */
			
			if (achievementDetail.getOpPublicHolidayMidnightTime().isPresent()) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime(
						achievementDetail.getOpPublicHolidayMidnightTime().get(),
						StaturoryAtrOfHolidayWork.PublicHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			if (output.getOverTimeShiftNight().isPresent()) {
				output.getOverTimeShiftNight().get().setMidNightHolidayTimes(midNightHolidayTimes);
			}
		} else { // 仮計算実行＝する
			List<DeductionTime> breakTimeList =  new ArrayList<DeductionTime>();
			// INPUT．休憩時間帯(List)をチェックする
			if (breakTimes.isEmpty()) {
				// 計算用の休憩時間帯=休憩時間帯を取得する
				breakTimeList = commonOvertimeHoliday.getBreakTimes(companyId, workTypeCode.v(), workTimeCode.v(), Optional.empty(), Optional.empty());
			} else {
				// 計算用の休憩時間帯=INPUT．休憩時間帯(List)
				for (int i = 0; i < breakTimes.stream().count(); i++) {
					DeductionTime duTime = new DeductionTime();
					duTime.setStart(breakTimes.get(i).getStart());
					duTime.setEnd(breakTimes.get(i).getEnd());
					breakTimeList.add(duTime);
					
				}
				
			}
			// アルゴリズム「1日分の勤怠時間を仮計算」を実行する
			List<TimeZone> timeZones = new ArrayList<TimeZone>();
			Optional<AchievementDetail> opAcOptional = acuActualContentDisplay.map(x -> x.getOpAchievementDetail()).orElse(Optional.empty());
			// 1 QA
			timeZones.add(new TimeZone(
					new TimeWithDayAttr(opAcOptional.map(x -> x.getOpWorkTime().orElse(0)).orElse(0)),
					new TimeWithDayAttr(opAcOptional.map(x -> x.getOpLeaveTime().orElse(0)).orElse(0))));
			//2
			timeZones.add(new TimeZone(
					new TimeWithDayAttr(opAcOptional.map(x -> x.getOpWorkTime2().orElse(0)).orElse(0)),
					new TimeWithDayAttr(opAcOptional.map(x -> x.getOpDepartureTime2().orElse(0)).orElse(0))));
			
			// 1日分の勤怠時間を仮計算 (RQ23)
			output = convertApplicationList(
					companyId,
					employeeId,
					date,
					workTypeCode == null ? Optional.empty() : Optional.of(workTimeCode.v()),
					workTimeCode == null ? Optional.empty() : Optional.ofNullable(workTimeCode.v()),
					timeZones,
					breakTimes).get(0);
			
		}
		
		
		
		return output;
	}
	public List<ApplicationTime> convertApplicationList(
			String companyId,
			String employeeId,
			GeneralDate date,
			Optional<String> workTypeCode,
			Optional<String> workTimeCode,
			List<TimeZone> timeZones,
			List<DeductionTime> breakTimes
			) {
		
		// 1日分の勤怠時間を仮計算 (RQ23)
		List<ApplicationTime> output = new ArrayList<>();
		ApplicationTime applicationTime = new ApplicationTime();
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(
				employeeId,
				date,
				workTypeCode.orElse(null),
				workTimeCode.orElse(null),
				timeZones,
				breakTimes.stream().map(x -> x.getStart().v()).collect(Collectors.toList()),
				breakTimes.stream().map(x -> x.getEnd().v()).collect(Collectors.toList()));
		// 「申請時間」をセットして返す
		
		List<OvertimeApplicationSetting> overtimeApplicationSetting = new ArrayList<OvertimeApplicationSetting>();
		
		List<OvertimeApplicationSetting> overTimes = dailyAttendanceTimeCaculationImport.getOverTime()
																   .entrySet()
																   .stream()
																   .map(x -> x.getValue().getCalTime() > 0  ? new OvertimeApplicationSetting(
																									   x.getKey(),
																									   AttendanceType_Update.NORMALOVERTIME,
																									   x.getValue().getTime())
																		   		: null )
																   .filter(y -> y != null)
																   .collect(Collectors.toList());

		overtimeApplicationSetting.addAll(overTimes);
		
		List<OvertimeApplicationSetting> holidayTimes = dailyAttendanceTimeCaculationImport.getHolidayWorkTime()
																   .entrySet()
																   .stream()
																   .map(x -> x.getValue().getCalTime() > 0 ? new OvertimeApplicationSetting(
																									   x.getKey(),
																									   AttendanceType_Update.BREAKTIME,
																									   x.getValue().getTime())
																		   		: null )
																   .filter(y -> y != null)
																   .collect(Collectors.toList());
		overtimeApplicationSetting.addAll(holidayTimes);
		
		
		List<OvertimeApplicationSetting> bonusPayTimes = dailyAttendanceTimeCaculationImport.getBonusPayTime()
				   .entrySet()
				   .stream()
				   .map(x -> x.getValue() > 0 ? new OvertimeApplicationSetting(
								   x.getKey(),
								   AttendanceType_Update.BONUSPAYTIME,
								   x.getValue())
						   : null
						   		)
				   .filter(y -> y != null)
				   .collect(Collectors.toList());
		
		overtimeApplicationSetting.addAll(bonusPayTimes);
		
		List<OvertimeApplicationSetting> specBonusPayTimes = dailyAttendanceTimeCaculationImport.getSpecBonusPayTime()
				   .entrySet()
				   .stream()
				   .map(x -> x.getValue() > 0 ? new OvertimeApplicationSetting(
								   x.getKey(),
								   AttendanceType_Update.BONUSSPECIALDAYTIME,
								   x.getValue())
						   : null
						   		)
				   .filter(y -> y != null)
				   .collect(Collectors.toList());
		
		overtimeApplicationSetting.addAll(specBonusPayTimes);
		
		applicationTime.setApplicationTime(overtimeApplicationSetting);
		
		
		
		applicationTime.setFlexOverTime(Optional.of(new AttendanceTimeOfExistMinus(dailyAttendanceTimeCaculationImport.getFlexTime().getCalTime())));
		
		
		OverTimeShiftNight overTimeShiftNight = new OverTimeShiftNight();
		
		overTimeShiftNight.setMidNightOutSide(dailyAttendanceTimeCaculationImport.getTimeOutSideMidnight());
		overTimeShiftNight.setOverTimeMidNight(dailyAttendanceTimeCaculationImport.getCalOvertimeMidnight());
		
		List<HolidayMidNightTime> midNightHolidayTimes = dailyAttendanceTimeCaculationImport.getCalHolidayMidnight()
										   .entrySet()
										   .stream()
										   .map(x -> new HolidayMidNightTime(
												   x.getValue(),
												   StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(EnumAdaptor.valueOf(x.getKey(), HolidayAtr.class))))
										   .collect(Collectors.toList());
		
		overTimeShiftNight.setMidNightHolidayTimes(midNightHolidayTimes);
		applicationTime.setOverTimeShiftNight(Optional.of(overTimeShiftNight));
		output.add(applicationTime);
		return output;
	}

}
