package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.GoOutTimeSheetOfDailyWork;
import nts.uk.ctx.at.record.dom.daily.calcset.SetForNoStamp;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.BreakSetOfCommon;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.CalcMethodIfLeaveWorkDuringBreakTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidBreakTimeOfCalcMethod;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除時間帯
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class DeductionTimeSheet {
	private final List<TimeSheetOfDeductionItem> forDeductionTimeZoneList;
	private final List<TimeSheetOfDeductionItem> forRecordTimeZoneList;
	@Getter
	private final BreakManagement breakTimeSheet;
	
	public void devideDeductionsBy(ActualWorkingTimeSheet actualWorkingTimeSheet,BreakManagement breakTimeSheet) {

		devide(this.forDeductionTimeZoneList, actualWorkingTimeSheet);
		devide(this.forRecordTimeZoneList, actualWorkingTimeSheet);
	}
	
	private static void devide(
			List<TimeSheetOfDeductionItem> source,
			ActualWorkingTimeSheet devider) {
		
		val devided = new ArrayList<TimeSheetOfDeductionItem>();
		source.forEach(deductionTimeZone -> {
			devided.addAll(deductionTimeZone.devideIfContains(devider.start()));
			devided.addAll(deductionTimeZone.devideIfContains(devider.end()));
		});
		
		source.clear();
		source.addAll(devided);
	}
	
	public DeductionTimeSheet createDedctionTimeSheet(AcquisitionConditionsAtr acqAtr){
		/*控除時間帯リストへコピー*/
		List<TimeSheetOfDeductionItem> useDedTimeSheet = collectDeductionTimes();
		/*重複部分補正処理*/
		
		/*計上用↓*/
		List<TimeSheetOfDeductionItem> recordDedTimeSheet = useDedTimeSheet;
		
		
		/*控除でない外出削除*/
		List<TimeSheetOfDeductionItem> goOutDeletedList = new ArrayList<TimeSheetOfDeductionItem>();
		for(TimeSheetOfDeductionItem timesheet: useDedTimeSheet) {
			if(!(timesheet.getDeductionAtr().isGoOut() && timesheet.getGoOutReason().get().isPublicOrCmpensation())) {
				goOutDeletedList.add(timesheet);
			}
		}
		
		/*ここに丸め設定系の処理を置く*/
		
		return new DeductionTimeSheet(goOutDeletedList, recordDedTimeSheet, breakTimeSheet);
	}

	
	/**
	 * 控除時間に該当する項目を集め控除時間帯を作成する
	 */
	public List<TimeSheetOfDeductionItem> collectDeductionTimes(GoOutTimeSheetOfDailyWork dailyGoOutSheet,CalculationRangeOfOneDay oneDayRange,BreakSetOfCommon CommonSet
										, AttendanceLeavingWorkOfDaily attendanceLeaveWork,FixRestCalcMethod fixedCalc
										,workTimeDivision workTimeDivision,SetForNoStamp noStampSet, FluidBreakTimeOfCalcMethod fluidSet,AcquisitionConditionsAtr acqAtr ) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>(); 
		/*休憩時間帯取得*/
		breakTimeSheet.getBreakTimeSheet(workTimeDivision, fixedCalc, noStampSet, fluidSet);
		/*外出時間帯取得*/
		dailyGoOutSheet.RemoveUnuseItemBaseOnAtr(acqAtr)
								.forEach(tc ->{
									sheetList.add(TimeSheetOfDeductionItem.reateBreakTimeSheetAsFixed(new TimeSpanForCalc(tc.getGoOut().getEngrave().getTimesOfDay(),tc.getComeBack().getEngrave().getTimesOfDay())
																									,Finally.of(tc.getGoOutReason())
																									,null
																									,DeductionClassification.GO_OUT
																									,WithinStatutoryAtr.WithinStatury));	
								});
		/*育児時間帯を取得*/
		/*ソート処理*/
		sheetList.stream().sorted((first,second) -> first.calculationTimeSheet.getStart().compareTo(second.calculationTimeSheet.getStart()));
		/*計算範囲による絞り込み*/
		List<TimeSheetOfDeductionItem> reNewSheetList = refineCalcRange(sheetList,oneDayRange.getOneDayOfRange(),CommonSet.getLeaveWorkDuringBreakTime()
				, attendanceLeaveWork);
		return reNewSheetList;
		
	}
	

	/**
	 * 計算範囲による絞り込みを行うためのループ
	 * @param dedTimeSheets 控除項目の時間帯
	 * @param oneDayRange　1日の範囲
	 * @return 控除項目の時間帯リスト
	 */
	public List<TimeSheetOfDeductionItem> refineCalcRange(List<TimeSheetOfDeductionItem> dedTimeSheets,TimeSpanForCalc oneDayRange,CalcMethodIfLeaveWorkDuringBreakTime calcMethod
												,AttendanceLeavingWorkOfDaily attendanceLeaveWork) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>(); 
		for(TimeSheetOfDeductionItem timeSheet: dedTimeSheets){
			switch(timeSheet.getDeductionAtr()) {
			case CHILD_CARE:
				/*要確認*/
			case GO_OUT:
				Optional<TimeSpanForCalc> duplicateGoOutSheet = getGoOutCalcRange(timeSheet,oneDayRange);
				if(duplicateGoOutSheet.isPresent()) {
						sheetList.add(TimeSheetOfDeductionItem.reateBreakTimeSheetAsFixed( duplicateGoOutSheet.get()
																						, timeSheet.getGoOutReason()
																						, timeSheet.getBreakAtr()
																						, timeSheet.getDeductionAtr()
																						, timeSheet.getWithinStatutoryAtr()));
				}
			case BREAK:
			
				List<TimeSpanForCalc> duplicateBreakSheet = getBreakCalcRange(attendanceLeaveWork.getAttendanceLeavingWorkTime(),calcMethod,oneDayRange.getDuplicatedWith(timeSheet.calculationTimeSheet));
				if(!duplicateBreakSheet.isEmpty())
				{
					duplicateBreakSheet.forEach(tc -> {
						sheetList.add(TimeSheetOfDeductionItem.reateBreakTimeSheetAsFixed( tc
							, timeSheet.getGoOutReason()
							, timeSheet.getBreakAtr()
							, timeSheet.getDeductionAtr()
							, timeSheet.getWithinStatutoryAtr()));	
					});
				}
			default:
				throw new RuntimeException("unknown deductionAtr:" + timeSheet.getDeductionAtr());
			}
		}
		return sheetList;
	}
	
	
	//外出時間帯の計算範囲の取得(親(控除時間帯？)でやれる)
	public Optional<TimeSpanForCalc> getGoOutCalcRange(TimeSheetOfDeductionItem shet,TimeSpanForCalc oneDayRange) {
		
		return oneDayRange.getDuplicatedWith(shet.calculationTimeSheet); 
	
	}

	/**
	 * 休憩時間帯の計算範囲の取得 
	 * @param timeList 出勤退勤の時間リスト
	 * @param calcMethod　休憩時間中に退勤した場合の計算方法
	 * @param deplicateoneTimeRange 1日の範囲と控除時間帯の重複部分
	 * @return
	 */
	public List<TimeSpanForCalc> getBreakCalcRange(List<AttendanceLeavingWork> timeList,CalcMethodIfLeaveWorkDuringBreakTime calcMethod,Optional<TimeSpanForCalc> deplicateOneTimeRange) {
		if(deplicateOneTimeRange.isPresent()) {
			return null;
		}
		List<TimeSpanForCalc> timesheets = new ArrayList<TimeSpanForCalc>();
		for(AttendanceLeavingWork time : timeList) {
			timesheets.add(getIncludeAttendanceOrLeaveDuplicateTimeSheet(time, calcMethod, deplicateOneTimeRange.get()));
		}
		return timesheets;
	}
	
	

	/**
	 * 休憩時間帯に出勤、退勤が含まれているかの判定ののち重複時間帯の取得
	 * @param time
	 * @param calcMethod
	 * @param oneDayRange
	 * @return
	 */
	public TimeSpanForCalc getIncludeAttendanceOrLeaveDuplicateTimeSheet(AttendanceLeavingWork time,CalcMethodIfLeaveWorkDuringBreakTime calcMethod,TimeSpanForCalc oneDayRange) {
		TimeWithDayAttr newStart = oneDayRange.getStart();
		TimeWithDayAttr newEnd = oneDayRange.getEnd();
		if(oneDayRange.contains(time.getLeaveWork().getActualEngrave().getTimesOfDay())) {
			if(oneDayRange.contains(time.getAttendance().getActualEngrave().getTimesOfDay())){
				newStart = time.getAttendance().getActualEngrave().getTimesOfDay();
			}
		
			switch(calcMethod) {
			case NotRecordAll:
				return null;
			case RecordAll:
				return new TimeSpanForCalc(newStart,newEnd);
			case RecordUntilLeaveWork:
				return new TimeSpanForCalc(newStart,time.getLeaveWork().getEngrave().getTimesOfDay());
			default:
				throw new RuntimeException("unknown CalcMethodIfLeaveWorkDuringBreakTime:" + calcMethod);
			}
		}
		else
		{
			return oneDayRange.getDuplicatedWith(new TimeSpanForCalc(time.getAttendance().getEngrave().getTimesOfDay(),time.getLeaveWork().getEngrave().getTimesOfDay())).get();
		}
	}
	
}
