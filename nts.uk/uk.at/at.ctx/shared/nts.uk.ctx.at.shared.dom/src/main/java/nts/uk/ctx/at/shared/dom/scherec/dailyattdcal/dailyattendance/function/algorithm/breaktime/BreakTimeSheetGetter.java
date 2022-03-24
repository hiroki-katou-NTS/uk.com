package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.BreakClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別勤怠の休憩時間帯を取得する
 */
public class BreakTimeSheetGetter {

	/** 休憩時間帯取得 */
	public static List<BreakTimeSheet> get(RequireM1 require,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			IntegrationOfDaily domainDaily, boolean correctWithEndTime) {
		

		
		val cid = AppContexts.user().companyId();
		
		val workType = require.workType(cid, domainDaily.getWorkInformation().getRecordInfo().getWorkTypeCode()).orElse(null);
		if (workType == null) {
			return new ArrayList<>();
		}
		/** 出勤系か判定する */
		if (workType.getAttendanceHolidayAttr() == AttendanceHolidayAttr.HOLIDAY) {
			
			/** 休憩をクリアする */
			return new ArrayList<>();
		}
		
		/** require.就業時間帯を取得 */
		val workTimeSet = IntegrationOfWorkTime.getWorkTime(require, cid,
				domainDaily.getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull());
		
		/** 就業時間帯=NULLチェック */
		if(workTimeSet == null) {
			return new ArrayList<>();
		}
		if (!workTimeSet.isFixBreak(workType) && !domainDaily.getAttendanceLeave().isPresent()) {
			return new ArrayList<>();
		}
		/** 「１日の計算範囲」クラスを作成 */
		CalculationRangeOfOneDay oneDayCalcRange = require.createOneDayRange( 
				domainDaily, Optional.of(workTimeSet.getCommonSetting()), 
				workType, JustCorrectionAtr.USE, Optional.of(workTimeSet.getCode()));
		
		List<TimeSheetOfDeductionItem> deductionTimeSheet = new ArrayList<>();
		
		switch (workTimeSet.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm()) {
		case FIXED: /** 固定勤務 */
			deductionTimeSheet = oneDayCalcRange.getDeductionTimeSheetOnFixed(
					workType, workTimeSet, domainDaily, companyCommonSetting, personDailySetting, correctWithEndTime);
			break;
		case FLEX: /** フレックス勤務 */
			
			if(workTimeSet.isFixBreak(workType)) {
				/** 固定休憩 */
				deductionTimeSheet = oneDayCalcRange.getDeductionTimeSheetOnFixed(
						workType, workTimeSet, domainDaily, companyCommonSetting, personDailySetting, correctWithEndTime);
				
			} else  {
				
				/** 流動休憩 */
				deductionTimeSheet = getDeductionTimeSheetOnFlexFlow(require, workType, workTimeSet, 
						domainDaily, oneDayCalcRange, companyCommonSetting, personDailySetting, correctWithEndTime);
			}
			break;
		case FLOW: /** 流動 */
			if(workTimeSet.isFixBreak(workType)) {
				deductionTimeSheet =  oneDayCalcRange.getDeductionTimeSheetOnFixed(workType, workTimeSet, domainDaily,
						companyCommonSetting, personDailySetting, correctWithEndTime);
			}
			else {
				WithinWorkTimeSheet withinWorkTimeSheet = new WithinWorkTimeSheet(new ArrayList<>(), new ArrayList<>(), 
						Optional.empty(), Optional.empty());	
				
				/** 補正用事前処理 */
				deductionTimeSheet = oneDayCalcRange.prePocessForFlowCorrect(
						companyCommonSetting, personDailySetting, workType, workTimeSet, domainDaily, 
						domainDaily.getAttendanceLeave().get(), 
						withinWorkTimeSheet, correctWithEndTime);	
			}
			
			
			break;
		default:
			
			deductionTimeSheet = new ArrayList<>();
			break;
		}
		
		/** 休憩時間帯に変換 */ 
		deductionTimeSheet = deductionTimeSheet.stream()
				.filter(c -> c.getDeductionAtr() == DeductionClassification.BREAK
						&& (c.getBreakAtr().isPresent() ? c.getBreakAtr().get() != BreakClassification.BREAK_STAMP
								: true))
				.collect(Collectors.toList());

		List<BreakTimeSheet> breakTimeSheet = new ArrayList<>();
		for(int idx = 0; idx < deductionTimeSheet.size(); idx++) {
			val dudectionSheet = deductionTimeSheet.get(idx);
			
			breakTimeSheet.add(new BreakTimeSheet(new BreakFrameNo(idx + 1),
							   dudectionSheet.getTimeSheet().getStart(), 
							   dudectionSheet.getTimeSheet().getEnd()));
		}
		
		/** 休憩時間帯を返す */
		return breakTimeSheet;
	}
	
	private static List<TimeSheetOfDeductionItem> getDeductionTimeSheetOnFlexFlow(RequireM3 require, WorkType workType,
			IntegrationOfWorkTime workTime, IntegrationOfDaily integrationOfDaily,
			CalculationRangeOfOneDay oneDayCalcRange,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet personDailySetting,
			boolean correctWithEndTime) {
		
		if (!integrationOfDaily.getAttendanceLeave().isPresent()) {
			return new ArrayList<>();
		}
		
		val attendanceLeaveWorks = integrationOfDaily.getAttendanceLeave().map(c -> c.getTimeLeavingWorks()).orElse(new ArrayList<>());
		
		val withinWorkTimeSheet = new WithinWorkTimeSheet(new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty());
		
		List<TimeLeavingWork> calcLateTimeLeavingWorksWorks = new ArrayList<>();
		for(TimeLeavingWork timeLeavingWork : attendanceLeaveWorks) {
			calcLateTimeLeavingWorksWorks.add(
					oneDayCalcRange.calcLateTimeSheet(workType, workTime, integrationOfDaily,
							new DeductionTimeSheet(
									new ArrayList<>(),
									new ArrayList<>(),
									new BreakTimeOfDailyAttd(),
									Optional.empty(),
									new ArrayList<>()),
							personDailySetting.getAddSetting().getAddSetOfWorkingTime(),
							timeLeavingWork, 
							withinWorkTimeSheet));
		}
		
		val attendanceLeave = new TimeLeavingOfDailyAttd(calcLateTimeLeavingWorksWorks, new WorkTimes(calcLateTimeLeavingWorksWorks.size()));
		
		val lateTimeSheet = withinWorkTimeSheet.getWithinWorkTimeFrame().stream()
												.filter(c -> c.getLateTimeSheet().isPresent())
												.map(c -> c.getLateTimeSheet().get())
												.collect(Collectors.toList());
		
		/** 流動休憩用の時間帯作成 */
		val timeSheet = oneDayCalcRange.provisionalDeterminationOfDeductionTimeSheet(
				workType, workTime, integrationOfDaily, 
				oneDayCalcRange.getOneDayOfRange(), Optional.of(attendanceLeave), 
				oneDayCalcRange.getPredetermineTimeSetForCalc(), lateTimeSheet, correctWithEndTime, Optional.empty(),
				companyCommonSetting, personDailySetting);
		
		return timeSheet.getForDeductionTimeZoneList();
	}

	public static interface RequireM3 extends PredetemineTimeSetting.Require{

	}

	public static interface RequireM1 extends IntegrationOfWorkTime.RequireM2, RequireM3, WorkType.Require {
		
		CalculationRangeOfOneDay createOneDayRange(
				IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet,
				WorkType workType, JustCorrectionAtr justCorrectionAtr, Optional<WorkTimeCode> workTimeCode);
	}
}
