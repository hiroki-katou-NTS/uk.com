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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別勤怠の休憩時間帯を取得する
 */
public class BreakTimeSheetGetter {

	/** 休憩時間帯取得 */
	public static List<BreakTimeSheet> get(RequireM1 require, 
			ManagePerPersonDailySet personDailySetting,
			IntegrationOfDaily domainDaily) {
		
		if (!domainDaily.getAttendanceLeave().isPresent()) {
			return new ArrayList<>();
		}
		
		val cid = AppContexts.user().companyId();
		/** require.就業時間帯を取得 */
		val workTimeSet = getWorkTime(require, cid, domainDaily);
		
		/** 就業時間帯=NULLチェック */
		if(workTimeSet == null) {
			return new ArrayList<>();
		}
		
		val workType = require.workType(cid, domainDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).orElse(null);
		if(workType == null) {
			return new ArrayList<>();
		}
		
		/** 「１日の計算範囲」クラスを作成 */
		val oneDayCalcRange = require.createOneDayRange(require.predetemineTimeSetting(cid, workTimeSet.getCode().v()), 
				domainDaily, Optional.of(workTimeSet.getCommonSetting()), 
				workType, Optional.of(workTimeSet.getCode()));
		
		List<TimeSheetOfDeductionItem> deductionTimeSheet = new ArrayList<>();
		
		switch (workTimeSet.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeForm()) {
		case FIXED: /** 固定勤務 */
			deductionTimeSheet = oneDayCalcRange.getDeductionTimeSheetOnFixed(workType, workTimeSet, domainDaily);
			break;
		case FLEX: /** フレックス勤務 */
			val wts = workTimeSet.getWorkTimeSetting();
			
			if(wts.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
				/** 固定休憩 */
				deductionTimeSheet = oneDayCalcRange.getDeductionTimeSheetOnFixed(workType, workTimeSet, domainDaily);
				
			} else if(wts.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
				
				/** 流動休憩 */
				deductionTimeSheet = getDeductionTimeSheetOnFlexFlow(require, workType, workTimeSet, 
						domainDaily, oneDayCalcRange, personDailySetting);
			}
			break;
		case FLOW: /** 流動 */
			
			/** 補正用事前処理 */
			deductionTimeSheet = oneDayCalcRange.prePocessForFlowCorrect(personDailySetting, workType, workTimeSet, domainDaily, 
																domainDaily.getAttendanceLeave().get().getTimeLeavingWorks(), 
																oneDayCalcRange.getWithinWorkingTimeSheet().get());
			break;
		default:
			
			deductionTimeSheet = new ArrayList<>();
			break;
		}
		
		/** 休憩時間帯に変換 */ 
		deductionTimeSheet = deductionTimeSheet.stream().filter(c -> c.getDeductionAtr() == DeductionClassification.BREAK)
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
			CalculationRangeOfOneDay oneDayCalcRange, ManagePerPersonDailySet personDailySetting) {
		
		if (!integrationOfDaily.getAttendanceLeave().isPresent()) {
			return new ArrayList<>();
		}
		
		val attendanceLeaveWorks = integrationOfDaily.getAttendanceLeave().map(c -> c.getTimeLeavingWorks()).orElse(new ArrayList<>());
		
		List<TimeLeavingWork> calcLateTimeLeavingWorksWorks = new ArrayList<>();
		for(TimeLeavingWork timeLeavingWork : attendanceLeaveWorks) {
			calcLateTimeLeavingWorksWorks.add(
					oneDayCalcRange.calcLateTimeSheet(workType, workTime, integrationOfDaily,
							new ArrayList<>(), personDailySetting.getAddSetting().getVacationCalcMethodSet(),
							timeLeavingWork, 
							new WithinWorkTimeSheet(new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty())));
		}
		
		val attendanceLeave = new TimeLeavingOfDailyAttd(calcLateTimeLeavingWorksWorks, new WorkTimes(calcLateTimeLeavingWorksWorks.size()));
		
		/** 流動休憩用の時間帯作成 */
		val timeSheet = oneDayCalcRange.provisionalDeterminationOfDeductionTimeSheet(workType, workTime, integrationOfDaily, 
				oneDayCalcRange.getOneDayOfRange(), attendanceLeave, 
				oneDayCalcRange.getPredetermineTimeSetForCalc());
		
		return timeSheet.getForDeductionTimeZoneList();
	}
	
	private static IntegrationOfWorkTime getWorkTime(RequireM2 require, String cid, IntegrationOfDaily domainDaily) {
		/** require.就業時間帯を取得 */
		val workTimeSet = domainDaily.getWorkInformation().getRecordInfo()
				.getWorkTimeCodeNotNull()
				.flatMap(c -> require.workTimeSetting(cid, c.v()))
				.orElse(null);
		
		/** 就業時間帯=NULLチェック */
		if(workTimeSet == null) {
			return null;
		}
		
		switch(workTimeSet.getWorkTimeDivision().getWorkTimeForm()) {
			case FIXED:				
				return new IntegrationOfWorkTime(workTimeSet.getWorktimeCode(), workTimeSet, 
						                         require.fixedWorkSetting(cid, workTimeSet.getWorktimeCode().v()).get());
			case FLEX:				
				return new IntegrationOfWorkTime(workTimeSet.getWorktimeCode(), workTimeSet, 
												 require.flexWorkSetting(cid, workTimeSet.getWorktimeCode().v()).get());
			case FLOW:				
				return new IntegrationOfWorkTime(workTimeSet.getWorktimeCode(), workTimeSet, 
						                         require.flowWorkSetting(cid, workTimeSet.getWorktimeCode().v()).get());
			case TIMEDIFFERENCE:	
				throw new RuntimeException("Unimplemented");/*時差勤務はまだ実装しない。2020/5/19 渡邉*/
			default:				
				throw new RuntimeException("Non-conformity No Work");
		}
	}

	public static interface RequireM3 {

		Optional<PredetemineTimeSetting> predetemineTimeSetting(String cid, String workTimeCode);
	}

	public static interface RequireM2 {
		
		Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode);
		
		Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode);
		
		Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode);
		
		Optional<FlexWorkSetting> flexWorkSetting(String companyId,String workTimeCode);
	}

	public static interface RequireM1 extends RequireM2, RequireM3 {
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
		
		CalculationRangeOfOneDay createOneDayRange(Optional<PredetemineTimeSetting> predetemineTimeSet,
				IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet,
				WorkType workType, Optional<WorkTimeCode> workTimeCode);
	}
}
