package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

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
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
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
	private AppHolidayWorkRepository appHolidayWorkRepository;

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
					dailyAttendanceTimeCaculation.getCalculation(
							employeeID, 
							appDate, 
							judgmentWorkTypeResult.getCalcWorkType(), 
							judgmentWorkTimeResult.getCalcWorkTime(), 
							recordWorkInfoImport.getAttendanceStampTimeFirst(), 
							judgmentStampResult.getCalcLeaveStamp(), 
							breakStartTime, 
							breakEndTime);
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
				AppOverTime appOverTime = overtimeRepository.getFullAppOvertime(companyID, appBefore.getAppID()).get();
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
				AppHolidayWork appHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, appBefore.getAppID()).get();
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

}
