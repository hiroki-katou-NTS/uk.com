package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareFrameSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareHolidayWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareOvertimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.HdwkFrameEachHdAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告計算範囲
 * @author shuichi_ishida
 */
@Getter
public class DeclareCalcRange {

	/** 申告設定 */
	private DeclareSet declareSet;
	/** 1日の範囲 */
	private TimeZoneRounding rangeOfOneDay;
	/** 就業判断時間帯 */
	private Optional<TimeZoneRounding> workTimezone;
	/** 控除項目の時間帯 */
	private List<TimeSheetOfDeductionItem> timesheetOfDeduct;
	/** 深夜丸め設定 */
	private TimeRoundingSetting midnightRoundSet;
	/** 出退勤 */
	private DeclareAttdLeave attdLeave;
	/** 計算時刻 */
	private DeclareCalcClock calcClock;
	/** 計算時間 */
	private DeclareCalcTime calcTime;
	/** 編集状態 */
	private DeclareEditState editState;
	/** 勤務種類 */
	private Optional<WorkType> workTypeOpt;
	/** 休出かどうか */
	private boolean isHolidayWork;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	private DeclareCalcRange(String companyId){
		this.declareSet = new DeclareSet(companyId);
		this.rangeOfOneDay = new TimeZoneRounding();
		this.workTimezone = Optional.empty();
		this.timesheetOfDeduct = new ArrayList<>();
		this.midnightRoundSet = new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN);
		this.attdLeave = new DeclareAttdLeave();
		this.calcClock = new DeclareCalcClock();
		this.calcTime = new DeclareCalcTime();
		this.editState = new DeclareEditState();
		this.workTypeOpt = Optional.empty();
		this.isHolidayWork = false;
	}
	
	/**
	 * 申告計算範囲の作成
	 * @param companyId 会社ID
	 * @param workType 勤務種類
	 * @param itgOfWorkTime 統合就業時間帯（申告用）(ref)
	 * @param itgOfDaily 日別実績(Work)
	 * @param calcRangeRecord 1日の計算範囲（実績用）
	 * @param declareSet 申告設定
	 * @param predTimeSet 所定時間設定
	 * @param companyCommonSetting 会社別設定管理
	 * @return 申告計算範囲
	 */
	public static DeclareCalcRange create(
			String companyId,
			WorkType workType,
			IntegrationOfWorkTime itgOfWorkTime,
			IntegrationOfDaily itgOfDaily,
			CalculationRangeOfOneDay calcRangeRecord,
			DeclareSet declareSet,
			Optional<PredetemineTimeSetting> predTimeSet,
			ManagePerCompanySet companyCommonSetting){
		
		DeclareCalcRange domain = new DeclareCalcRange(companyId);
		
		// 勤務種類の保存
		domain.workTypeOpt = Optional.of(workType);
		// 申告設定の取得
		domain.declareSet = declareSet;
		// 申告深夜丸め設定の取得
		{
			WorkTimezoneCommonSet workTimeCommonSet = itgOfWorkTime.getCommonSetting();
			domain.midnightRoundSet = workTimeCommonSet.getLateNightTimeSet().getRoundingSetting().clone();
		}
		// 申告用控除時間帯の取得
		{
			// 休憩設定を変更する　（休憩時間を退勤で区切らず、1日全て計上に変更）
			if (itgOfWorkTime.getFixedWorkSetting().isPresent()){
				itgOfWorkTime.getFixedWorkSetting().get().getFixedWorkRestSetting().getCommonRestSet().setCalculateMethod(
						RestTimeOfficeWorkCalcMethod.APPROP_ALL);
			}
			if (itgOfWorkTime.getFlowWorkSetting().isPresent()){
				itgOfWorkTime.getFlowWorkSetting().get().getRestSetting().getCommonRestSetting().setCalculateMethod(
						RestTimeOfficeWorkCalcMethod.APPROP_ALL);
			}
			// 控除時間帯の取得
			if (itgOfDaily.getAttendanceLeave().isPresent()){
				domain.timesheetOfDeduct = DeductionTimeSheet.createDedctionTimeSheet(
						DeductionAtr.Appropriate,
						workType,
						itgOfWorkTime,
						itgOfDaily,
						calcRangeRecord.getOneDayOfRange(),
						itgOfDaily.getAttendanceLeave().get(),
						calcRangeRecord.getPredetermineTimeSetForCalc());
			}
		}
		// 1日の範囲を確認する
		TimeSpanForDailyCalc rangeOfOneDay = calcRangeRecord.getOneDayOfRange();
		domain.rangeOfOneDay = new TimeZoneRounding(
				new TimeWithDayAttr(rangeOfOneDay.getStart().valueAsMinutes()),
				new TimeWithDayAttr(rangeOfOneDay.getEnd().valueAsMinutes()),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
		// 申告出退勤の作成
		domain.attdLeave = DeclareAttdLeave.create(
				itgOfWorkTime.getWorkTimeSetting(),
				predTimeSet, itgOfDaily.getAttendanceLeave().get(), declareSet);
		// 休出かどうかの判断
		domain.isHolidayWork = workType.isHolidayWork();
		// 申告用就業判断時間帯の取得
		{
			if (domain.isHolidayWork){
				// 休出用就業判断時間帯の取得
				domain.workTimezone = DeclareCalcRange.getWorkSheetForHolidayWork(domain.attdLeave);
			}
			else{
				// 残業用就業判断時間帯の取得
				domain.workTimezone = DeclareCalcRange.getWorkSheetForOvertime(calcRangeRecord);
			}
		}
		// 申告用出勤系範囲の取得
		DeclareAttdRange attdRange = DeclareCalcRange.getAttendanceRange(
				domain, declareSet, itgOfWorkTime, companyCommonSetting);
		domain.calcTime.setEarlyOvertime(attdRange.getEarlyTime());
		domain.calcTime.setEarlyOvertimeMn(attdRange.getEarlyMnTime());
		domain.calcClock.setEarlyOtStart(attdRange.getEarlyStart());
		domain.calcClock.setEarlyOtMnStart(attdRange.getEarlyMnStart());
		// 申告用退勤系範囲の取得
		DeclareLeaveRange leaveRange = DeclareCalcRange.getLeaveRange(
				domain, declareSet, itgOfWorkTime, companyCommonSetting);
		domain.calcTime.setOvertime(leaveRange.getOvertimeTime());
		domain.calcTime.setEarlyOvertimeMn(leaveRange.getOvertimeMnTime());
		domain.calcTime.setHolidayWork(leaveRange.getHolidayWorkTime());
		domain.calcTime.setHolidayWorkMn(leaveRange.getHolidayWorkMnTime());
		domain.calcClock.setOvertimeEnd(leaveRange.getOvertimeEnd());
		domain.calcClock.setOvertimeMnStart(leaveRange.getOvertimeMnStart());
		domain.calcClock.setHolidayWorkEnd(leaveRange.getHolidayWorkEnd());
		domain.calcClock.setHolidayWorkMnStart(leaveRange.getHolidayWorkMnStart());
		
		return domain;
	}
	
	/**
	 * 残業用就業判断時間帯の取得
	 * @param calcRangeRecord １日の計算範囲
	 * @return 就業判断時間帯
	 */
	private static Optional<TimeZoneRounding> getWorkSheetForOvertime(CalculationRangeOfOneDay calcRangeRecord){

		Optional<TimeZoneRounding> result = Optional.empty();
		
		// 就業時間内時間枠を確認する
		WithinWorkTimeSheet withinTimeSheet = calcRangeRecord.getWithinWorkingTimeSheet().get();
		List<WithinWorkTimeFrame> frames = withinTimeSheet.getWithinWorkTimeFrame();
		if (frames.size() > 0){
			WithinWorkTimeFrame minFrame = null;
			WithinWorkTimeFrame maxFrame = null;
			for (WithinWorkTimeFrame frame : frames){
				// 最小の就業時間枠NOを確認する
				if (minFrame == null){
					minFrame = frame;
				}
				else if(minFrame.getWorkingHoursTimeNo().v() > frame.getWorkingHoursTimeNo().v()){
					minFrame = frame;
				}
				// 最大の就業時間枠NOを確認する
				if (maxFrame == null){
					maxFrame = frame;
				}
				else if(maxFrame.getWorkingHoursTimeNo().v() < frame.getWorkingHoursTimeNo().v()){
					maxFrame = frame;
				}
			}
			// 計算用時間帯を返す
			if (minFrame != null && maxFrame != null){
				result = Optional.of(new TimeZoneRounding(
						new TimeWithDayAttr(minFrame.getTimeSheet().getStart().valueAsMinutes()),
						new TimeWithDayAttr(minFrame.getTimeSheet().getEnd().valueAsMinutes()),
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
			}
		}
		return result;
	}
	
	/**
	 * 休出用就業判断時間帯の取得
	 * @param attdLeave 申告出退勤
	 * @return 就業判断時間帯
	 */
	private static Optional<TimeZoneRounding> getWorkSheetForHolidayWork(DeclareAttdLeave attdLeave){
		
		Optional<TimeZoneRounding> result = Optional.empty();

		// 出勤を確認する
		if (attdLeave.getAttendance().isPresent()){
			// 退勤を確認する
			if (attdLeave.getLeave().isPresent()){
				// 出勤～退勤
				result = Optional.of(new TimeZoneRounding(
						new TimeWithDayAttr(attdLeave.getAttendance().get().valueAsMinutes()),
						new TimeWithDayAttr(attdLeave.getLeave().get().valueAsMinutes()),
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
			}
			else{
				// 出勤～出勤
				result = Optional.of(new TimeZoneRounding(
						new TimeWithDayAttr(attdLeave.getAttendance().get().valueAsMinutes()),
						new TimeWithDayAttr(attdLeave.getAttendance().get().valueAsMinutes()),
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
			}
		}
		return result;
	}

	/**
	 * 申告用出勤系範囲の取得
	 * @param calcRange 申告計算範囲
	 * @param declareSet 申告設定
	 * @param itgOfWorkTime 統合就業時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @return 申告用出勤系範囲
	 */
	private static DeclareAttdRange getAttendanceRange(
			DeclareCalcRange calcRange,
			DeclareSet declareSet,
			IntegrationOfWorkTime itgOfWorkTime,
			ManagePerCompanySet companyCommonSetting){
		
		// 申告用出勤系範囲を作成する
		DeclareAttdRange result = new DeclareAttdRange();
		// 就業判断時間帯を確認する
		if (!calcRange.getWorkTimezone().isPresent()) return result;
		result.setEarlyEnd(Optional.of(new TimeWithDayAttr(
				calcRange.getWorkTimezone().get().getStart().valueAsMinutes())));
		// 出退勤を確認する
		result.setAttendance(calcRange.getAttdLeave().getAttendance());
		if (!calcRange.getAttdLeave().getAttdOvertime().isPresent()) return result;
		// 申告時間帯を作成する（始業前）
		DeclareTimeSheet beforeStart = new DeclareTimeSheet(
				new TimeSpanForDailyCalc(
						new TimeWithDayAttr(calcRange.getRangeOfOneDay().getStart().valueAsMinutes()),
						new TimeWithDayAttr(result.getEarlyEnd().get().valueAsMinutes())),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				calcRange.getTimesheetOfDeduct(), new ArrayList<>());
		// 申告早出範囲の計算
		DeclareEarlyRange earlyRange = DeclareCalcRange.calcEarlyRange(
				beforeStart, calcRange.getAttdLeave().getAttdOvertime().get(),
				declareSet, calcRange.getAttdLeave(), itgOfWorkTime, companyCommonSetting);
		// 申告用出勤系範囲　←　結果
		result.setEarlyTime(earlyRange.getEarly());
		result.setEarlyMnTime(earlyRange.getEarlyMidnight());
		result.setEarlyStart(earlyRange.getStart());
		result.setEarlyMnStart(earlyRange.getMidnightStart());
		// 申告用出勤系範囲を返す
		return result;
	}
	
	/**
	 * 申告早出範囲の計算
	 * @param sheet 申告時間帯
	 * @param declareOvertime 時間外の申告
	 * @param declareSet 申告設定
	 * @param attdLeave 申告出退勤
	 * @param itgOfWorkTime 統合就業時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @return 申告早出範囲
	 */
	private static DeclareEarlyRange calcEarlyRange(
			DeclareTimeSheet sheet,
			OvertimeDeclaration declareOvertime,
			DeclareSet declareSet,
			DeclareAttdLeave attdLeave,
			IntegrationOfWorkTime itgOfWorkTime,
			ManagePerCompanySet companyCommonSetting){
		
		// 申告早出範囲を作成する
		DeclareEarlyRange result = new DeclareEarlyRange();
		// 時間帯を後ろから指定時間分抜き出す
		int declareMinutes = declareOvertime.getOverTime().valueAsMinutes()
				+ declareOvertime.getOverLateNightTime().valueAsMinutes();
		Optional<TimeSpanForDailyCalc> earlySpan = sheet.extractBackword(new TimeWithDayAttr(declareMinutes));
		if (!earlySpan.isPresent()) return result;
		// 開始　←　早出．開始
		result.setStart(Optional.of(earlySpan.get().getStart()));
		// 申告深夜範囲の取得
		DeclareTimeSheet early = new DeclareTimeSheet(earlySpan.get(), sheet.getRounding(),
				sheet.getRecordedTimeSheet(), sheet.getDeductionTimeSheet());
		DeclareMidnightRange earlyMidnight = DeclareCalcRange.getMidnightRange(
				early, declareOvertime, declareSet, attdLeave, itgOfWorkTime, companyCommonSetting);
		// 深夜開始　←　早出深夜．開始時刻
		result.setMidnightStart(earlyMidnight.getStart());
		// 時間を計算する
		result.setEarly(new AttendanceTime(declareMinutes - earlyMidnight.getTime().valueAsMinutes()));
		result.setEarlyMidnight(earlyMidnight.getTime());
		// 早出申告時間を返す
		return result;
	}

	/**
	 * 申告用退勤系範囲の取得
	 * @param calcRange 申告計算範囲
	 * @param declareSet 申告設定
	 * @param itgOfWorkTime 統合就業時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @return 申告用退勤系範囲
	 */
	private static DeclareLeaveRange getLeaveRange(
			DeclareCalcRange calcRange,
			DeclareSet declareSet,
			IntegrationOfWorkTime itgOfWorkTime,
			ManagePerCompanySet companyCommonSetting){
		
		// 申告用退勤系範囲を作成する
		DeclareLeaveRange result = new DeclareLeaveRange();
		// 就業判断時間帯を確認する
		if (!calcRange.getWorkTimezone().isPresent()) return result;
		result.setOvertimeStart(Optional.of(new TimeWithDayAttr(
				calcRange.getWorkTimezone().get().getEnd().valueAsMinutes())));
		result.setHolidayWorkStart(Optional.of(new TimeWithDayAttr(
				calcRange.getWorkTimezone().get().getStart().valueAsMinutes())));
		// 出退勤を確認する
		result.setLeave(calcRange.getAttdLeave().getLeave());
		if (!calcRange.getAttdLeave().getLeaveOvertime().isPresent()) return result;
		// 申告時間帯を作成する（終業後）
		DeclareTimeSheet afterEnd = new DeclareTimeSheet(
				new TimeSpanForDailyCalc(
						new TimeWithDayAttr(result.getOvertimeStart().get().valueAsMinutes()),
						new TimeWithDayAttr(calcRange.getRangeOfOneDay().getEnd().valueAsMinutes())),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				calcRange.getTimesheetOfDeduct(), new ArrayList<>());
		// 申告時間外範囲の計算
		DeclareOvertimeRange overtimeRange = DeclareCalcRange.calcOvertimeRange(
				afterEnd, calcRange.getAttdLeave().getLeaveOvertime().get(),
				declareSet, calcRange.getAttdLeave(), itgOfWorkTime, companyCommonSetting);
		// 申告用退勤系範囲　←　残業結果
		result.setOvertimeTime(overtimeRange.getOvertime());
		result.setOvertimeMnTime(overtimeRange.getOvertimeMidnight());
		result.setOvertimeEnd(overtimeRange.getEnd());
		result.setOvertimeMnStart(overtimeRange.getMidnightStart());
		// 申告時間帯を作成する（始業後）
		DeclareTimeSheet afterStart = new DeclareTimeSheet(
				new TimeSpanForDailyCalc(
						new TimeWithDayAttr(result.getHolidayWorkStart().get().valueAsMinutes()),
						new TimeWithDayAttr(calcRange.getRangeOfOneDay().getEnd().valueAsMinutes())),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				calcRange.getTimesheetOfDeduct(), new ArrayList<>());
		// 申告時間外範囲の計算
		DeclareOvertimeRange holidayWorkRange = DeclareCalcRange.calcOvertimeRange(
				afterStart, calcRange.getAttdLeave().getLeaveOvertime().get(),
				declareSet, calcRange.getAttdLeave(), itgOfWorkTime, companyCommonSetting);
		// 申告用退勤系範囲　←　休出結果
		result.setHolidayWorkTime(holidayWorkRange.getOvertime());
		result.setHolidayWorkMnTime(holidayWorkRange.getOvertimeMidnight());
		result.setHolidayWorkEnd(holidayWorkRange.getEnd());
		result.setHolidayWorkMnStart(holidayWorkRange.getMidnightStart());
		// 申告用退勤系範囲を返す
		return result;
	}
	
	/**
	 * 申告時間外範囲の計算
	 * @param sheet 申告時間帯
	 * @param declareOvertime 時間外の申告
	 * @param declareSet 申告設定
	 * @param attdLeave 申告出退勤
	 * @param itgOfWorkTime 統合就業時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @return 申告時間外範囲
	 */
	private static DeclareOvertimeRange calcOvertimeRange(
			DeclareTimeSheet sheet,
			OvertimeDeclaration declareOvertime,
			DeclareSet declareSet,
			DeclareAttdLeave attdLeave,
			IntegrationOfWorkTime itgOfWorkTime,
			ManagePerCompanySet companyCommonSetting){
		
		// 申告時間外範囲を作成する
		DeclareOvertimeRange result = new DeclareOvertimeRange();
		// 時間帯を前から指定時間分抜き出す
		int declareMinutes = declareOvertime.getOverTime().valueAsMinutes()
				+ declareOvertime.getOverLateNightTime().valueAsMinutes();
		Optional<TimeSpanForDailyCalc> overtimeSpan = sheet.extractForward(new TimeWithDayAttr(declareMinutes));
		if (!overtimeSpan.isPresent()) return result;
		// 終了　←　時間外．終了
		result.setEnd(Optional.of(overtimeSpan.get().getEnd()));
		// 申告深夜範囲の取得
		DeclareTimeSheet overtime = new DeclareTimeSheet(overtimeSpan.get(), sheet.getRounding(),
				sheet.getRecordedTimeSheet(), sheet.getDeductionTimeSheet());
		DeclareMidnightRange overtimeMidnight = DeclareCalcRange.getMidnightRange(
				overtime, declareOvertime, declareSet, attdLeave, itgOfWorkTime, companyCommonSetting);
		// 深夜開始　←　時間外深夜．開始時刻
		result.setMidnightStart(overtimeMidnight.getStart());
		// 時間を計算する
		result.setOvertime(new AttendanceTime(declareMinutes - overtimeMidnight.getTime().valueAsMinutes()));
		result.setOvertimeMidnight(overtimeMidnight.getTime());
		// 早出時間外時間を返す
		return result;
	}
	
	/**
	 * 申告深夜範囲の取得
	 * @param sheet 申告時間帯
	 * @param declareOvertime 時間外の申告
	 * @param declareSet 申告設定
	 * @param attdLeave 申告出退勤
	 * @param itgOfWorkTime 統合就業時間帯
	 * @param companyCommonSetting 会社別設定管理
	 * @return 申告深夜範囲
	 */
	private static DeclareMidnightRange getMidnightRange(
			DeclareTimeSheet sheet,
			OvertimeDeclaration declareOvertime,
			DeclareSet declareSet,
			DeclareAttdLeave attdLeave,
			IntegrationOfWorkTime itgOfWorkTime,
			ManagePerCompanySet companyCommonSetting){
		
		// 計算深夜時間　←　時間外深夜時間
		int calcMidnightMinutes = declareOvertime.getOverLateNightTime().valueAsMinutes();
		
		// 深夜時間自動計算の判定
		if (declareSet.checkMidnightAutoCalc()){
			// 枠設定を確認する
			Optional<DeclareTimeSheet> midnightSheet = Optional.empty();
			if (declareSet.getFrameSet() == DeclareFrameSet.WORKTIME_SET){
				// 深夜用　←　パラメータ
				midnightSheet = Optional.of(new DeclareTimeSheet(
						new TimeSpanForDailyCalc(
								new TimeWithDayAttr(sheet.getTimeSheet().getStart().valueAsMinutes()),
								new TimeWithDayAttr(sheet.getTimeSheet().getEnd().valueAsMinutes())),
						new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
						sheet.getRecordedTimeSheet(), sheet.getDeductionTimeSheet()));
				midnightSheet.get().replaceOwnDedTimeSheet();
			}
			if (declareSet.getFrameSet() == DeclareFrameSet.OT_HDWK_SET){
				// 申告深夜時間帯の取得
				midnightSheet = DeclareCalcRange.getSheetMidnight(sheet, attdLeave);
			}
			if (midnightSheet.isPresent()){
				// 深夜時間帯の取得
				MidNightTimeSheet midnightTimeSheet = companyCommonSetting.getMidNightTimeSheet();
				// 深夜時間帯の作成
				WorkTimezoneCommonSet workTimeCommonSet = itgOfWorkTime.getCommonSetting();
				midnightSheet.get().createMidNightTimeSheet(midnightTimeSheet, Optional.of(workTimeCommonSet));
				if (midnightSheet.get().getMidNightTimeSheet().isPresent()){
					// 時間の計算
					calcMidnightMinutes = midnightSheet.get().getMidNightTimeSheet().get().calcTotalTime().valueAsMinutes();
				}
				else{
					// 計算深夜時間　←　0
					calcMidnightMinutes = 0;
				}
			}
			else{
				// 計算深夜時間　←　0
				calcMidnightMinutes = 0;
			}
		}
		// 計算深夜時間を申告時間以下にする
		if (calcMidnightMinutes > declareOvertime.getOverTime().valueAsMinutes()){
			calcMidnightMinutes = declareOvertime.getOverTime().valueAsMinutes();
		}
		
		Optional<TimeWithDayAttr> start = Optional.empty();		// 深夜開始時刻
		if (calcMidnightMinutes > 0){
			// 時間帯を後ろから指定時間分抜き出す
			Optional<TimeSpanForDailyCalc> extract = sheet.extractBackword(new TimeWithDayAttr(calcMidnightMinutes));
			if (extract.isPresent()){
				// 深夜開始時刻　←　申告深夜．開始
				start = Optional.of(new TimeWithDayAttr(extract.get().getStart().valueAsMinutes()));
			}
		}
		
		// 申告深夜範囲を返す
		DeclareMidnightRange result = new DeclareMidnightRange(start, new AttendanceTime(calcMidnightMinutes));
		return result;
	}
	
	/**
	 * 申告深夜時間帯の取得
	 * @param sheet 申告時間帯
	 * @param attdLeave 申告出退勤
	 * @return 申告深夜時間帯（申告時間帯）
	 */
	private static Optional<DeclareTimeSheet> getSheetMidnight(
			DeclareTimeSheet sheet,
			DeclareAttdLeave attdLeave){

		// 深夜用　←　パラメータ
		TimeWithDayAttr start = new TimeWithDayAttr(sheet.getTimeSheet().getStart().valueAsMinutes());
		TimeWithDayAttr end = new TimeWithDayAttr(sheet.getTimeSheet().getEnd().valueAsMinutes());
		DeclareTimeSheet result = new DeclareTimeSheet(
				new TimeSpanForDailyCalc(
						new TimeWithDayAttr(start.valueAsMinutes()), new TimeWithDayAttr(end.valueAsMinutes())),
				new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
				sheet.getRecordedTimeSheet(), sheet.getDeductionTimeSheet());
		// 出勤を確認する
		if (attdLeave.getAttendance().isPresent()){
			if (attdLeave.getAttendance().get().greaterThan(end)) return Optional.empty();
			if (result.getTimeSheet().contains(attdLeave.getAttendance().get())){
				// 開始　←　出勤
				start = new TimeWithDayAttr(attdLeave.getAttendance().get().valueAsMinutes());
			}
		}
		// 退勤を確認する
		if (attdLeave.getLeave().isPresent()){
			if (attdLeave.getLeave().get().lessThan(start)) return Optional.empty();
			if (result.getTimeSheet().contains(attdLeave.getLeave().get())){
				// 終了　←　退勤
				end = new TimeWithDayAttr(attdLeave.getLeave().get().valueAsMinutes());
			}
		}
		// 申告時間帯を返す
		result.replaceTimeSheet(new TimeSpanForDailyCalc(start, end));
		result.replaceOwnDedTimeSheet();
		return Optional.of(result);
	}
	
	/**
	 * 残業休出枠設定を調整する
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param workType 勤務種類
	 */
	public void adjustOvertimeHolidayWorkFrameSet(
			IntegrationOfWorkTime itgOfWorkTime,
			WorkType workType){
		
		// 「枠設定」を確認する
		if (this.declareSet.getFrameSet() == DeclareFrameSet.WORKTIME_SET) return;
		// 申告残業枠の設定
		{
			// 固定勤務の申告残業枠の設定
			this.setDeclareOvertimeFrameOfFixed(itgOfWorkTime, workType);
			// 流動勤務の申告残業枠の設定
			this.setDeclareOvertimeFrameOfFlow(itgOfWorkTime);
		}
		// 申告休出枠の設定
		{
			// 固定勤務の申告休出枠の設定
			this.setDeclareHolidayWorkFrameOfFixed(itgOfWorkTime);
			// 流動勤務の申告休出枠の設定
			this.setDeclareHolidayWorkFrameOfFlow(itgOfWorkTime);
		}
	}
	
	/**
	 * 固定勤務の申告残業枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param workType 勤務種類
	 */
	private void setDeclareOvertimeFrameOfFixed(
			IntegrationOfWorkTime itgOfWorkTime,
			WorkType workType){
		
		if (!itgOfWorkTime.getFixedWorkSetting().isPresent()) return;
		FixedWorkSetting fixedWorkSet = itgOfWorkTime.getFixedWorkSetting().get();
		
		// 残業時間帯を確認する
		if (!fixedWorkSet.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).isPresent()) return;
		List<OverTimeOfTimeZoneSet> targetList = fixedWorkSet.getOverTimeOfTimeZoneSet(workType);
		if (targetList.size() <= 0) return;
		// 残業時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareOvertimeFrame overtimeFrame = this.declareSet.getOvertimeFrame();
		// 残業時間帯の追加（早出残業）
		{
			// 早出残業開始を確認する
			if (this.calcClock.getEarlyOtStart().isPresent()){
				// 残業時間の時間帯設定の作成（早出残業の共通設定）
				OverTimeOfTimeZoneSet addEarly = new OverTimeOfTimeZoneSet();
				if (overtimeFrame.getEarlyOvertime().isPresent()){
					addEarly.setOtFrameNo(new OTFrameNo(overtimeFrame.getEarlyOvertime().get().v()));
					addEarly.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
					addEarly.setEarlyOTUse(true);
					addEarly.setRestraintTimeUse(false);
					addEarly.setLegalOTframeNo(OTFrameNo.getDefaultData());
					addEarly.setSettlementOrder(SettlementOrder.getDefaultData());
				}
				// 早出残業深夜開始を確認する
				if (this.calcClock.getEarlyOtMnStart().isPresent()){
					// 残業時間の時間帯設定を追加する（早出残業）
					if (overtimeFrame.getEarlyOvertime().isPresent()){
						int start = this.calcClock.getEarlyOtStart().get().valueAsMinutes();
						int end = this.calcClock.getEarlyOtMnStart().get().valueAsMinutes();
						if (start != end) {
							addEarly.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addEarly);
							timezoneNo++;
						}
					}
					// 残業時間の時間帯設定を追加する（早出深夜）
					if (overtimeFrame.getEarlyOvertimeMn().isPresent() && this.workTimezone.isPresent()){
						OverTimeOfTimeZoneSet addEarlyMn = new OverTimeOfTimeZoneSet();
						addEarlyMn.setOtFrameNo(new OTFrameNo(overtimeFrame.getEarlyOvertimeMn().get().v()));
						addEarlyMn.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
						addEarlyMn.setEarlyOTUse(true);
						addEarlyMn.setRestraintTimeUse(false);
						addEarlyMn.setLegalOTframeNo(OTFrameNo.getDefaultData());
						addEarlyMn.setSettlementOrder(SettlementOrder.getDefaultData());
						int start = this.calcClock.getEarlyOtMnStart().get().valueAsMinutes();
						int end = this.workTimezone.get().getStart().valueAsMinutes();
						if (start != end) {
							addEarlyMn.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addEarlyMn);
							timezoneNo++;
						}
					}
				}
				else{
					// 残業時間の時間帯設定を追加する（早出残業）
					if (overtimeFrame.getEarlyOvertime().isPresent() && this.workTimezone.isPresent()){
						int start = this.calcClock.getEarlyOtStart().get().valueAsMinutes();
						int end = this.workTimezone.get().getStart().valueAsMinutes();
						if (start != end) {
							addEarly.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addEarly);
							timezoneNo++;
						}
					}
				}
			}
		}
		// 残業時間帯の追加（普通残業）
		{
			// 普通残業終了を確認する
			if (this.calcClock.getOvertimeEnd().isPresent()){
				// 残業時間の時間帯設定の作成（普通残業の共通設定）
				OverTimeOfTimeZoneSet addOvertime = new OverTimeOfTimeZoneSet();
				if (overtimeFrame.getOvertime().isPresent()){
					addOvertime.setOtFrameNo(new OTFrameNo(overtimeFrame.getOvertime().get().v()));
					addOvertime.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
					addOvertime.setEarlyOTUse(false);
					addOvertime.setRestraintTimeUse(false);
					addOvertime.setLegalOTframeNo(OTFrameNo.getDefaultData());
					addOvertime.setSettlementOrder(SettlementOrder.getDefaultData());
				}
				// 普通残業深夜開始を確認する
				if (this.calcClock.getOvertimeMnStart().isPresent()){
					// 残業時間の時間帯設定を追加する（普通残業）
					if (overtimeFrame.getOvertime().isPresent() && this.workTimezone.isPresent()){
						int start = this.workTimezone.get().getEnd().valueAsMinutes();
						int end = this.calcClock.getOvertimeMnStart().get().valueAsMinutes();
						if (start != end) {
							addOvertime.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addOvertime);
							timezoneNo++;
						}
					}
					// 残業時間の時間帯設定を追加する（普通深夜）
					if (overtimeFrame.getOvertimeMn().isPresent()){
						OverTimeOfTimeZoneSet addOvertimeMn = new OverTimeOfTimeZoneSet();
						addOvertimeMn.setOtFrameNo(new OTFrameNo(overtimeFrame.getOvertimeMn().get().v()));
						addOvertimeMn.setWorkTimezoneNo(new EmTimezoneNo(timezoneNo));
						addOvertimeMn.setEarlyOTUse(false);
						addOvertimeMn.setRestraintTimeUse(false);
						addOvertimeMn.setLegalOTframeNo(OTFrameNo.getDefaultData());
						addOvertimeMn.setSettlementOrder(SettlementOrder.getDefaultData());
						int start = this.calcClock.getOvertimeMnStart().get().valueAsMinutes();
						int end = this.calcClock.getOvertimeEnd().get().valueAsMinutes();
						if (start != end) {
							addOvertimeMn.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addOvertimeMn);
							timezoneNo++;
						}
					}
				}
				else{
					// 残業時間の時間帯設定を追加する（普通残業）
					if (overtimeFrame.getOvertime().isPresent() && this.workTimezone.isPresent()){
						int start = this.workTimezone.get().getEnd().valueAsMinutes();
						int end = this.calcClock.getOvertimeEnd().get().valueAsMinutes();
						if (start != end) {
							addOvertime.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addOvertime);
							timezoneNo++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 流動勤務の申告残業枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 */
	private void setDeclareOvertimeFrameOfFlow(IntegrationOfWorkTime itgOfWorkTime){
		
		if (!itgOfWorkTime.getFlowWorkSetting().isPresent()) return;
		FlowWorkSetting flowWorkSet = itgOfWorkTime.getFlowWorkSetting().get();
		
		// 初回経過時間を確認する
		List<FlowOTTimezone> targetList = flowWorkSet.getHalfDayWorkTimezoneLstOTTimezone();
		if (targetList.size() <= 0) return;
		int firstElapsed = targetList.get(0).getFlowTimeSetting().getElapsedTime().valueAsMinutes();
		// 残業時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareOvertimeFrame overtimeFrame = this.declareSet.getOvertimeFrame();
		// 流動残業時間帯の追加
		{
			// 普通残業終了を確認する
			if (this.calcClock.getOvertimeEnd().isPresent()){
				// 流動残業時間帯を追加する（普通残業）
				FlowOTTimezone addOvertime = new FlowOTTimezone();
				if (overtimeFrame.getOvertime().isPresent()){
					addOvertime.setOTFrameNo(new OvertimeWorkFrameNo(
							BigDecimal.valueOf(overtimeFrame.getOvertime().get().v())));
					addOvertime.setWorktimeNo(timezoneNo);
					addOvertime.setRestrictTime(false);
					addOvertime.setInLegalOTFrameNo(OvertimeWorkFrameNo.getDefaultData());
					addOvertime.setSettlementOrder(SettlementOrder.getDefaultData());
					FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
					{
						flowTimeSetting.setElapsedTime(new AttendanceTime(firstElapsed));
						flowTimeSetting.setRounding(new TimeRoundingSetting(
								Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
					}
					addOvertime.setFlowTimeSetting(flowTimeSetting);
					if (this.calcTime.getOvertime().valueAsMinutes() > 0){
						targetList.add(addOvertime);
						timezoneNo++;
					}
				}
				// 普通残業深夜開始を確認する
				if (this.calcClock.getOvertimeMnStart().isPresent()){
					// 流動残業時間帯を追加する（普通深夜）
					if (overtimeFrame.getOvertimeMn().isPresent()){
						FlowOTTimezone addOvertimeMn = new FlowOTTimezone();
						addOvertimeMn.setOTFrameNo(new OvertimeWorkFrameNo(
								BigDecimal.valueOf(overtimeFrame.getOvertimeMn().get().v())));
						addOvertimeMn.setWorktimeNo(timezoneNo);
						addOvertimeMn.setRestrictTime(false);
						addOvertimeMn.setInLegalOTFrameNo(OvertimeWorkFrameNo.getDefaultData());
						addOvertimeMn.setSettlementOrder(SettlementOrder.getDefaultData());
						FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
						{
							flowTimeSetting.setElapsedTime(new AttendanceTime(
									firstElapsed + this.calcTime.getOvertime().valueAsMinutes()));
							flowTimeSetting.setRounding(new TimeRoundingSetting(
									Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
						}
						addOvertimeMn.setFlowTimeSetting(flowTimeSetting);
						targetList.add(addOvertimeMn);
						timezoneNo++;
					}
				}
			}
		}
	}
	
	/**
	 * 固定勤務の申告休出枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 */
	private void setDeclareHolidayWorkFrameOfFixed(IntegrationOfWorkTime itgOfWorkTime){
		
		if (!itgOfWorkTime.getFixedWorkSetting().isPresent()) return;
		FixedWorkSetting fixedWorkSet = itgOfWorkTime.getFixedWorkSetting().get();
		
		// 勤務時間帯を確認する
		List<HDWorkTimeSheetSetting> targetList = fixedWorkSet.getOffdayWorkTimezone().getLstWorkTimezone();
		if (targetList.size() <= 0) return;
		// 勤務時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareHolidayWorkFrame holidayWorkFrame = this.declareSet.getHolidayWorkFrame();
		// 休出時間帯の追加
		{
			// 休出終了を確認する
			if (this.calcClock.getHolidayWorkEnd().isPresent()){
				// 休出時間の時間帯設定の作成（休出の共通設定）
				HDWorkTimeSheetSetting addHolidayWork = new HDWorkTimeSheetSetting();
				if (holidayWorkFrame.getHolidayWork().isPresent()){
					HdwkFrameEachHdAtr eachHdAtr = holidayWorkFrame.getHolidayWork().get();
					addHolidayWork.setWorkTimeNo(timezoneNo);
					addHolidayWork.setInLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
					addHolidayWork.setOutLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
					addHolidayWork.setOutLegalPubHDFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
					addHolidayWork.setLegalHolidayConstraintTime(false);
					addHolidayWork.setNonStatutoryDayoffConstraintTime(false);
					addHolidayWork.setNonStatutoryHolidayConstraintTime(false);
				}
				// 休出深夜開始を確認する
				if (this.calcClock.getHolidayWorkMnStart().isPresent()){
					// 休出時間の時間帯設定を追加する（休出）
					if (holidayWorkFrame.getHolidayWork().isPresent() && this.workTimezone.isPresent()){
						int start = this.workTimezone.get().getStart().valueAsMinutes();
						int end = this.calcClock.getHolidayWorkMnStart().get().valueAsMinutes();
						if (start != end) {
							addHolidayWork.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addHolidayWork);
							timezoneNo++;
						}
					}
					// 休出時間の時間帯設定を追加する（休出深夜）
					if (holidayWorkFrame.getHolidayWorkMn().isPresent()){
						HDWorkTimeSheetSetting addHolidayWorkMn = new HDWorkTimeSheetSetting();
						HdwkFrameEachHdAtr eachHdAtr = holidayWorkFrame.getHolidayWorkMn().get();
						addHolidayWorkMn.setWorkTimeNo(timezoneNo);
						addHolidayWorkMn.setInLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
						addHolidayWorkMn.setOutLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
						addHolidayWorkMn.setOutLegalPubHDFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
						addHolidayWorkMn.setLegalHolidayConstraintTime(false);
						addHolidayWorkMn.setNonStatutoryDayoffConstraintTime(false);
						addHolidayWorkMn.setNonStatutoryHolidayConstraintTime(false);
						int start = this.calcClock.getHolidayWorkMnStart().get().valueAsMinutes();
						int end = this.calcClock.getHolidayWorkEnd().get().valueAsMinutes();
						if (start != end) {
							addHolidayWorkMn.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addHolidayWorkMn);
							timezoneNo++;
						}
					}
				}
				else{
					// 休出時間の時間帯設定を追加する（休出）
					if (holidayWorkFrame.getHolidayWork().isPresent() && this.workTimezone.isPresent()){
						int start = this.workTimezone.get().getStart().valueAsMinutes();
						int end = this.calcClock.getHolidayWorkEnd().get().valueAsMinutes();
						if (start != end) {
							addHolidayWork.setTimezone(new TimeZoneRounding(
									new TimeWithDayAttr(start), new TimeWithDayAttr(end),
									new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN)));
							targetList.add(addHolidayWork);
							timezoneNo++;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 流動勤務の申告休出枠の設定
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 */
	private void setDeclareHolidayWorkFrameOfFlow(IntegrationOfWorkTime itgOfWorkTime){
		
		if (!itgOfWorkTime.getFlowWorkSetting().isPresent()) return;
		FlowWorkSetting flowWorkSet = itgOfWorkTime.getFlowWorkSetting().get();
		
		// 初回経過時間を確認する
		List<FlowWorkHolidayTimeZone> targetList = flowWorkSet.getOffdayWorkTimezoneLstWorkTimezone();
		if (targetList.size() <= 0) return;
		int firstElapsed = targetList.get(0).getFlowTimeSetting().getElapsedTime().valueAsMinutes();
		// 残業時間帯を0件にクリアする
		targetList.clear();
		
		int timezoneNo = 1;
		DeclareHolidayWorkFrame holidayWorkFrame = this.declareSet.getHolidayWorkFrame();
		// 流動残業時間帯の追加
		{
			// 救出終了を確認する
			if (this.calcClock.getHolidayWorkEnd().isPresent()){
				// 流動休出時間帯の作成（普通残業）
				FlowWorkHolidayTimeZone addHolidayWork = new FlowWorkHolidayTimeZone();
				// 流動休出時間帯を追加する（休出）
				if (holidayWorkFrame.getHolidayWork().isPresent()){
					HdwkFrameEachHdAtr eachHdAtr = holidayWorkFrame.getHolidayWork().get();
					addHolidayWork.setWorktimeNo(timezoneNo);
					addHolidayWork.setInLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
					addHolidayWork.setOutLegalBreakFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
					addHolidayWork.setOutLegalPubHolFrameNo(new BreakFrameNo(
							BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
					addHolidayWork.setUseInLegalBreakRestrictTime(false);
					addHolidayWork.setUseOutLegalBreakRestrictTime(false);
					addHolidayWork.setUseOutLegalPubHolRestrictTime(false);
					FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
					{
						flowTimeSetting.setElapsedTime(new AttendanceTime(firstElapsed));
						flowTimeSetting.setRounding(new TimeRoundingSetting(
								Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
					}
					addHolidayWork.setFlowTimeSetting(flowTimeSetting);
					if (this.calcTime.getHolidayWork().valueAsMinutes() > 0){
						targetList.add(addHolidayWork);
						timezoneNo++;
					}
				}
				// 休出深夜開始を確認する
				if (this.calcClock.getHolidayWorkMnStart().isPresent()){
					// 流動休出時間帯を追加する（休出深夜）
					if (holidayWorkFrame.getHolidayWorkMn().isPresent()){
						FlowWorkHolidayTimeZone addHolidayWorkMn = new FlowWorkHolidayTimeZone();
						HdwkFrameEachHdAtr eachHdAtr = holidayWorkFrame.getHolidayWorkMn().get();
						addHolidayWorkMn.setWorktimeNo(timezoneNo);
						addHolidayWorkMn.setInLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getStatutory().v())));
						addHolidayWorkMn.setOutLegalBreakFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatutory().v())));
						addHolidayWorkMn.setOutLegalPubHolFrameNo(new BreakFrameNo(
								BigDecimal.valueOf(eachHdAtr.getNotStatHoliday().v())));
						addHolidayWorkMn.setUseInLegalBreakRestrictTime(false);
						addHolidayWorkMn.setUseOutLegalBreakRestrictTime(false);
						addHolidayWorkMn.setUseOutLegalPubHolRestrictTime(false);
						FlowTimeSetting flowTimeSetting = new FlowTimeSetting();
						{
							flowTimeSetting.setElapsedTime(new AttendanceTime(
									firstElapsed + this.calcTime.getHolidayWork().valueAsMinutes()));
							flowTimeSetting.setRounding(new TimeRoundingSetting(
									Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN));
						}
						addHolidayWorkMn.setFlowTimeSetting(flowTimeSetting);
						targetList.add(addHolidayWorkMn);
						timezoneNo++;
					}
				}
			}
		}
	}
	
	/**
	 * 出退勤時刻を申告処理用に調整する
	 * @param timeLeavingWorks 出退勤(List)(ref)
	 * @param workType 勤務種類
	 */
	public void adjustAttdLeaveClock(List<TimeLeavingWork> timeLeavingWorks, WorkType workType){
		
		// 休出かどうかの判断
		if (workType.isHolidayWork()){
			// 退勤に計算時刻を反映
			if (this.attdLeave.getLeave().isPresent() && this.calcClock.getHolidayWorkEnd().isPresent()){
				this.attdLeave.setLeave(this.calcClock.getHolidayWorkEnd());
			}
		}
		else{
			// 出勤に計算時刻を反映
			if (this.attdLeave.getAttendance().isPresent() && this.calcClock.getEarlyOtStart().isPresent()){
				this.attdLeave.setAttendance(this.calcClock.getEarlyOtStart());
			}
			// 退勤に計算時刻を反映
			if (this.attdLeave.getLeave().isPresent() && this.calcClock.getOvertimeEnd().isPresent()){
				this.attdLeave.setLeave(this.calcClock.getOvertimeEnd());
			}
		}
		// 出退勤リストの更新
		this.attdLeave.updateAttdLeaveList(timeLeavingWorks);
	}
}
