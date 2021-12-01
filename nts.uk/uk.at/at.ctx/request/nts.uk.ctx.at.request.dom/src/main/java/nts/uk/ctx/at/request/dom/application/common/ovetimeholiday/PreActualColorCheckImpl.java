package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TrackRecordAtr;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.FrameNo;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class PreActualColorCheckImpl implements PreActualColorCheck {
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	public RangeOfDayTimeZoneService rangeOfDayTimeZoneService;
	
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	
	

	

	

	

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
		Optional<ApplicationTime> output = Optional.empty();
		// INPUT．「表示する実績内容．実績詳細」をチェックする
		Optional<AchievementDetail> opAchievementDetail = acuActualContentDisplay.map(x -> x.getOpAchievementDetail()).orElse(Optional.empty());
		
		if (!(opAchievementDetail.isPresent() && opAchievementDetail.get().getTrackRecordAtr() == TrackRecordAtr.DAILY_RESULTS)) {
			
			return null;
		}
		AchievementDetail achievementDetail = opAchievementDetail.get();
		// INPUT．「表示する実績内容．実績詳細」 <> empty　AND　INPUT．「表示する実績内容．実績詳細．実績スケ区分」 = 日別実績 -> true
		// アルゴリズム「勤務分類変更の判定」を実行する
		JudgmentWorkTypeResult judgmentWorkTypeResult = this.judgmentWorkTypeChange(companyId, appType, achievementDetail.getWorkTypeCD(), Optional.ofNullable(workTypeCode).map(x -> x.v()).orElse(null));
		// アルゴリズム「就業時間帯変更の判定」を実行する
		JudgmentWorkTimeResult judgmentWorkTimeResult = this.judgmentWorkTimeChange(achievementDetail.getWorkTimeCD(), Optional.ofNullable(workTimeCode).map(x -> x.v()).orElse(null));
		
		// アルゴリズム「当日判定」を実行する
		Boolean isJudgmentToday = this.judgmentToday(date, Optional.ofNullable(workTimeCode).map(x -> x.v()).orElse(null));
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
			Optional<OverTimeShiftNight> overTimeShiftNightOp = Optional.empty();
			// 表示する実績内容．実績詳細．7勤怠時間．4勤怠種類 = 残業時間
			
			if (achievementDetail.getOpOvertimeLeaveTimeLst().isPresent()) {
				List<OvertimeLeaveTime> overTimeLeaveTimes = achievementDetail.getOpOvertimeLeaveTimeLst().get().stream()
						//.filter(x -> x.getAttendanceType() == AttendanceType_Update.NORMALOVERTIME.value || x.getAttendanceType() == AttendanceType_Update.BREAKTIME.value)
						.collect(Collectors.toList());
				List<OvertimeApplicationSetting> overTimeApplicationTimes = new ArrayList<>();
				overTimeLeaveTimes.forEach(item -> {
					if (item.getFrameNo() <= 10 && item.getTime() > 0) {
						OvertimeApplicationSetting overtimeApplicationSetting = new OvertimeApplicationSetting();
						overtimeApplicationSetting.setAttendanceType(EnumAdaptor.valueOf(item.getAttendanceType(), AttendanceType_Update.class));
						overtimeApplicationSetting.setFrameNo(new FrameNo(item.getFrameNo()));
						overtimeApplicationSetting.setApplicationTime(new AttendanceTime(item.getTime()));
						overTimeApplicationTimes.add(overtimeApplicationSetting);
						
					}
					
				});
				
				if (!CollectionUtil.isEmpty(overTimeApplicationTimes)) {
					if (!output.isPresent()) output = Optional.of(new ApplicationTime());
					output.get().setApplicationTime(overTimeApplicationTimes);
				}
				/*
				・INPUT．「表示する実績内容．実績詳細．計算フレックス」がある場合：
　					申請時間．フレックス超過時間 = 実績詳細．計算フレックス
				*/

				if (achievementDetail.getOpFlexTime().isPresent()) {
					if (!output.isPresent()) output = Optional.of(new ApplicationTime());
					output.get().setFlexOverTime(achievementDetail.getOpFlexTime());
				}
				/*
				 *・INPUT．「表示する実績内容．実績詳細．残業深夜時間」がある場合：
　					申請時間．就業時間外深夜時間．残業深夜時間 = 表示する実績内容．実績詳細．残業深夜時間
				 */
				if (achievementDetail.getOpOvertimeMidnightTime().isPresent()) {
					if (!overTimeShiftNightOp.isPresent()) {
						overTimeShiftNightOp = Optional.of(new OverTimeShiftNight());
					}
					overTimeShiftNightOp.get().setOverTimeMidNight(achievementDetail.getOpOvertimeMidnightTime().orElse(null));
					if (!output.isPresent()) output = Optional.of(new ApplicationTime());
					output.get().setOverTimeShiftNight(overTimeShiftNightOp);
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
			if (!output.isPresent()) {
				output = Optional.of(new ApplicationTime());
				
			}
			if (!output.get().getOverTimeShiftNight().isPresent()) {
				output.get().setOverTimeShiftNight(Optional.of(new OverTimeShiftNight()));
			}
			if (output.get().getOverTimeShiftNight().isPresent()) {
				output.get().getOverTimeShiftNight().get().setMidNightHolidayTimes(midNightHolidayTimes);
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
			List<ApplicationTime> outputList = convertApplicationList(
					companyId,
					employeeId,
					date,
					workTypeCode == null ? Optional.empty() : Optional.of(workTypeCode.v()),
					workTimeCode == null ? Optional.empty() : Optional.ofNullable(workTimeCode.v()),
					timeZones,
					breakTimes);
			if (CollectionUtil.isEmpty(outputList)) {
				output = null;
			} else {
				output = Optional.of(outputList.get(0));
			}
			
		}
		
		return output.orElse(null);
		
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
		Map<Integer, TimeZone> timeZoneMap = new HashMap<>();
		for (int i = 0; i < timeZones.size(); i++) {
			timeZoneMap.put(i + 1, timeZones.get(i));
		}
		// 1日分の勤怠時間を仮計算 (RQ23)
		List<ApplicationTime> output = new ArrayList<>();
		ApplicationTime applicationTime = new ApplicationTime();
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(
				employeeId,
				date,
				workTypeCode.orElse(null),
				workTimeCode.orElse(null),
				timeZoneMap,
				breakTimes.stream().map(x -> x.getStart().v()).collect(Collectors.toList()),
				breakTimes.stream().map(x -> x.getEnd().v()).collect(Collectors.toList()),
				Collections.emptyList(),
				Collections.emptyList());
		// 「申請時間」をセットして返す
		
		List<OvertimeApplicationSetting> overtimeApplicationSetting = new ArrayList<OvertimeApplicationSetting>();
		
		List<OvertimeApplicationSetting> overTimes = dailyAttendanceTimeCaculationImport.getOverTime()
																   .entrySet()
																   .stream()
																   .map(x -> x.getValue().getCalTime() > 0  ? new OvertimeApplicationSetting(
																									   x.getKey(),
																									   AttendanceType_Update.NORMALOVERTIME,
																									   x.getValue().getCalTime())
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
																									   x.getValue().getCalTime())
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
