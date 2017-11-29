package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWorkOfDaily;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.BreakTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.GoOutTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.GoOutTimeSheet;
import nts.uk.ctx.at.record.dom.daily.breaktimegoout.GoOutTimeSheetOfDailyWork;
import nts.uk.ctx.at.record.dom.daily.calcset.SetForNoStamp;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.goout.GoOutManagement;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.DeductionAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.BreakSetOfCommon;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.CalcMethodIfLeaveWorkDuringBreakTime;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.lateleaveearlysetting.LateLeaveEarlyClassification;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.BreakClockOfManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidBreakTimeOfCalcMethod;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除時間帯
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
public class DeductionTimeSheet {
	@Getter
	private final List<TimeSheetOfDeductionItem> forDeductionTimeZoneList;
	@Getter
	private final List<TimeSheetOfDeductionItem> forRecordTimeZoneList;
	@Getter
	private final BreakManagement breakTimeSheet;
	private final GoOutManagement goOutTimeSheet;
	
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
	
	/**
	 * 控除時間帯の作成
	 * @param acqAtr 取得条件区分
	 * @param setMethod 就業時間帯の設定方法
	 * @param clockManage 休憩打刻の時刻管理設定区分
	 * @param dailyGoOutSheet 日別実績の外出時間帯
	 * @param oneDayRange 1日の計算範囲
	 * @param CommonSet 共通の休憩設定
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @param fixedCalc 固定休憩の計算方法
	 * @param workTimeDivision 就業時間帯勤務区分
	 * @param noStampSet 休憩未打刻時の休憩設定
	 * @param fluidSet 固定休憩の設定
	 * @return 控除時間帯
	 */
	public static DeductionTimeSheet createDedctionTimeSheet(AcquisitionConditionsAtr acqAtr,WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage,
			OutingTimeOfDailyPerformance dailyGoOutSheet,CalculationRangeOfOneDay oneDayRange,BreakSetOfCommon CommonSet, AttendanceLeavingWorkOfDaily attendanceLeaveWork
								,FixRestCalcMethod fixedCalc,WorkTimeDivision workTimeDivision,SetForNoStamp noStampSet, FixRestTimeSetting fixedSet, BreakManagement breakTimeSheet){
		/*控除時間帯取得　控除時間帯リストへコピー*/
		List<TimeSheetOfDeductionItem> useDedTimeSheet = collectDeductionTimes(dailyGoOutSheet,oneDayRange,CommonSet
				,attendanceLeaveWork,fixedCalc,workTimeDivision,noStampSet,fixedSet,acqAtr );
		/*重複部分補正処理*/
		useDedTimeSheet = new DeductionTimeSheetAdjustDuplicationTime(useDedTimeSheet).reCreate(setMethod, clockManage);
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
	 * @param dailyGoOutSheet 
	 * @param oneDayRange 
	 * @param CommonSet 
	 * @param attendanceLeaveWork 
	 * @param fixedCalc 
	 * @param workTimeDivision 
	 * @param noStampSet 
	 * @param fluidSet 
	 * @param acqAtr 
	 * @return 
	 */
	public static List<TimeSheetOfDeductionItem> collectDeductionTimes(OutingTimeOfDailyPerformance dailyGoOutSheet,TimeSpanForCalc oneDayRange,BreakSetOfCommon CommonSet
										, TimeLeavingOfDailyPerformance attendanceLeaveWork,FixRestCalcMethod fixedCalc,WorkTimeDivision workTimeDivision,FluidPrefixBreakTimeSet noStampSet
										, FlowRestCalcMethod fluidSet, AcquisitionConditionsAtr acqAtr , BreakTimeManagement breakManagement
										, WorkTimeMethodSet workTimeMethodSet,Optional<FluRestTime> fluRestTime,FluidPrefixBreakTimeSet fluidprefixBreakTimeSet
										,ShortTimeWorkManagement shortTimeWorkManagement,WorkTimeCommonSet workTimeCommonSet) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>(); 
		/*休憩時間帯取得*/
		sheetList.addAll(breakManagement.getBreakTimeSheet(workTimeDivision, fixedCalc, noStampSet, fluidSet));
		/*外出時間帯取得*/
		sheetList.addAll(dailyGoOutSheet.removeUnuseItemBaseOnAtr(acqAtr,workTimeMethodSet,fluRestTime,fluidprefixBreakTimeSet));
		
		/*育児時間帯を取得*/
		sheetList.addAll(shortTimeWorkManagement.getChildCareTime(workTimeCommonSet,attendanceLeaveWork));
		
		/*ソート処理*/
		sheetList.stream().sorted((first,second) -> first.calcrange.getStart().compareTo(second.calcrange.getStart()));
		/*計算範囲による絞り込み*/
		List<TimeSheetOfDeductionItem> reNewSheetList = refineCalcRange(sheetList,oneDayRange,CommonSet.getLeaveWorkDuringBreakTime()
				, attendanceLeaveWork);
		return reNewSheetList;
		
	}
	

	/**
	 * 計算範囲による絞り込みを行うためのループ
	 * @param dedTimeSheets 控除項目の時間帯
	 * @param oneDayRange　1日の範囲
	 * @return 控除項目の時間帯リスト
	 */
	public static List<TimeSheetOfDeductionItem> refineCalcRange(List<TimeSheetOfDeductionItem> dedTimeSheets,TimeSpanForCalc oneDayRange,CalcMethodIfLeaveWorkDuringBreakTime calcMethod
												,AttendanceLeavingWorkOfDaily attendanceLeaveWork) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>(); 
		for(TimeSheetOfDeductionItem timeSheet: dedTimeSheets){
			switch(timeSheet.getDeductionAtr()) {
			case CHILD_CARE:
				/*要確認*/
			case GO_OUT:
				Optional<TimeSpanForCalc> duplicateGoOutSheet = oneDayRange.getDuplicatedWith(timeSheet.calcrange);
				if(duplicateGoOutSheet.isPresent()) {
						/*ここで入れる控除、加給、特定日、深夜は duplicateGoOutSheetと同じ範囲に絞り込む*/
						sheetList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
																						  timeSheet.timeSheet
																						, duplicateGoOutSheet.get()
																						, timeSheet.deductionTimeSheets
																						, timeSheet.bonusPayTimeSheet
																						, timeSheet.specifiedBonusPayTimeSheet
																						, timeSheet.midNightTimeSheet
																						, timeSheet.getGoOutReason()
																						, timeSheet.getBreakAtr()
																						, timeSheet.getDeductionAtr()
																						, timeSheet.getWithinStatutoryAtr()));
						
				}
			case BREAK:
			
				List<TimeSpanForCalc> duplicateBreakSheet = getBreakCalcRange(attendanceLeaveWork.getAttendanceLeavingWorkTime(),calcMethod,oneDayRange.getDuplicatedWith(timeSheet.calcrange));
				if(!duplicateBreakSheet.isEmpty())
				{
					duplicateBreakSheet.forEach(tc -> {
						/*ここで入れる控除、加給、特定日、深夜は duplicateGoOutSheetと同じ範囲に絞り込む*/
						sheetList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
																				  timeSheet.getTimeSheet()
																				, timeSheet.calcrange
																				, timeSheet.deductionTimeSheets
																				, timeSheet.bonusPayTimeSheet
																				, timeSheet.specifiedBonusPayTimeSheet
																				, timeSheet.midNightTimeSheet
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

	/**
	 * 休憩時間帯の計算範囲の取得 
	 * @param timeList 出勤退勤の時間リスト
	 * @param calcMethod　休憩時間中に退勤した場合の計算方法
	 * @param deplicateoneTimeRange 1日の範囲と控除時間帯の重複部分
	 * @return
	 */
	public static List<TimeSpanForCalc> getBreakCalcRange(List<AttendanceLeavingWork> timeList,CalcMethodIfLeaveWorkDuringBreakTime calcMethod,Optional<TimeSpanForCalc> deplicateOneTimeRange) {
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
	 * @param time 出退勤クラス
	 * @param calcMethod　休憩時間中に退勤した場合の計算方法
	 * @param oneDayRange 
	 * @return
	 */
	public static TimeSpanForCalc getIncludeAttendanceOrLeaveDuplicateTimeSheet(AttendanceLeavingWork time,CalcMethodIfLeaveWorkDuringBreakTime calcMethod,TimeSpanForCalc oneDayRange) {
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

	 /**
	  * 全控除項目の時間帯の合計を算出する
	  * @return 控除時間
	  */
	 public AttendanceTime calcDeductionAllTimeSheet(DeductionAtr dedAtr,TimeSpanForCalc workTimeSpan) {
	  List<TimeSheetOfDeductionItem> duplicatitedworkTime = getCalcRange(workTimeSpan);
	  AttendanceTime sumTime = new AttendanceTime(0);
	  
	  /*stream.collect.summingInt()*/
	  for(TimeSheetOfDeductionItem dedItem :duplicatitedworkTime) {
	   sumTime.addMinutes(dedItem.calcTotalTime().valueAsMinutes());
	  }
	  return sumTime;
	 }
	 
	 /**
	  * 休憩時間帯の合計時間を算出する
	  * @param deductionTimeSheetList
	  * @return
	  */
	 public AttendanceTime calcDeductionTotalTime(List<TimeSheetOfDeductionItem> deductionItemTimeSheetList) {
	  AttendanceTime totalTime = new AttendanceTime(0);
	  for(TimeSheetOfDeductionItem deductionItemTimeSheet: deductionItemTimeSheetList) {
	   totalTime.addMinutes(deductionItemTimeSheet.calcTotalTime().valueAsMinutes());
	  }
	  return totalTime;
	 }
	
	
	/**
	 * 引数の時間帯に重複する休憩時間帯の合計時間（分）を返す
	 * @param baseTimeSheet
	 * @return
	 */
	public int sumBreakTimeIn(TimeSpanForCalc baseTimeSheet) {
		return this.breakTimeSheet.sumBreakTimeIn(baseTimeSheet);
	}
	
	/**
	 * 算出された休憩時間の合計を取得する
	 * @param dedAtr
	 * @return
	 */
	public DeductionTotalTime getTotalBreakTime(DeductionAtr dedAtr) {
		DeductionTotalTime dedTotalTime;
		switch(dedAtr) {
		case Appropriate:
			dedTotalTime = getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()));
		case Deduction:
			dedTotalTime = getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()));
		default:
			throw new RuntimeException("unknown DeductionAtr" + dedAtr);
		}
		return dedTotalTime;
	}
	
	/**
	 * 算出された外出時間の合計を取得する
	 * @param dedAtr
	 * @return
	 */
	public List<DeductionTotalTime> getTotalGoOutTime(DeductionAtr dedAtr) {
		List<DeductionTotalTime> dedTotalTimeList;
		switch(dedAtr) {
		case Appropriate:
			dedTotalTimeList.add(getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isPrivate())
																						.collect(Collectors.toList())
																						,dedAtr));
			dedTotalTimeList.add(getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isCompensation())
																						.collect(Collectors.toList())
																						,dedAtr));
			dedTotalTimeList.add(getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isPublic())
																						.collect(Collectors.toList())
																						,dedAtr));
			dedTotalTimeList.add(getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isUnion())
																						.collect(Collectors.toList())
																						,dedAtr));
		case Deduction:
			dedTotalTimeList.add(getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isPrivate())
																						.collect(Collectors.toList())
																						,dedAtr));
			dedTotalTimeList.add(getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isCompensation())
																						.collect(Collectors.toList())
																						,dedAtr));
			dedTotalTimeList.add(getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isPublic())
																						.collect(Collectors.toList())
																						,dedAtr));
			dedTotalTimeList.add(getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																						.filter(tc -> tc.getGoOutReason().get().isUnion())
																						.collect(Collectors.toList())
																						,dedAtr));
		default:
			throw new RuntimeException("unknown DeductionAtr" + dedAtr);
		}
		return dedTotalTimeList;
	}
	

	/**
	 * 法定内・外、相殺時間から合計時間算出
	 * @param deductionTimeSheetList　
	 * @return
	 */
	public DeductionTotalTime getDeductionTotalTime(
			List<TimeSheetOfDeductionItem> deductionTimeSheetList,
			DeductionAtr dedAtr
			) {
		int statutoryTotalTime         = calcDeductionTotalTime(deductionTimeSheetList.stream()
																					  .filter(tc -> tc.getWithinStatutoryAtr().isWithinStatutory())
																					  .collect(Collectors.toList()));
		int excessOfStatutoryTotalTime = calcDeductionTotalTime(deductionTimeSheetList.stream()
																					  .filter(tc -> tc.getWithinStatutoryAtr().isExcessOfStatutory())
																					  .collect(Collectors.toList()));
		//時間休暇で相殺を行うかチェックする
		if(dedAtr.isAppropriate() && deductionTimeSheetList.get(0).getGoOutReason().get().isPrivateOrUnion()){//条件を満たしている場合
			//相殺時間の計算（法定内）
			DeductionOffSetTime calcStatutoryDeductionOffSetTime = calcStatutoryDeductionOffSetTime(deductionTimeSheetList,statutoryTotalTime,this.goOutTimeSheet.getDailyOfGoOutTime().getHolidayUseTime());
			//日別実績の時間休暇使用時間から法定内外出時間の控除総裁時間を控除する
			TimevacationUseTimeOfDaily correctTimevacationUseTimeOfDaily = this.goOutTimeSheet.getDailyOfGoOutTime().getHolidayUseTime().calcTimevacationUseTimeOfDaily(calcStatutoryDeductionOffSetTime);	
			//計上時間から相殺時間を控除する
			statutoryTotalTime -= calcStatutoryDeductionOffSetTime.getTotalOffSetTime();
			excessOfStatutoryTotalTime -= calcStatutoryDeductionOffSetTime(deductionTimeSheetList,excessOfStatutoryTotalTime,correctTimevacationUseTimeOfDaily).getTotalOffSetTime();
		}
		
		return DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(statutoryTotalTime+excessOfStatutoryTotalTime))
									,TimeWithCalculation.sameTime(new AttendanceTime(statutoryTotalTime))
									,TimeWithCalculation.sameTime(new AttendanceTime(excessOfStatutoryTotalTime)));
	}
		
	/**
	 * 相殺時間の計算
	 * 外出の場合の相殺時間の計算処理
	 * @author ken_takasu
	 * @param deductionTimeSheetList
	 * @param statutoryTotalTime
	 * @return
	 */
	public DeductionOffSetTime calcStatutoryDeductionOffSetTime(
			List<TimeSheetOfDeductionItem> deductionTimeSheetList,
			int statutoryTotalTime,
			TimevacationUseTimeOfDaily TimeVacationAdditionRemainingTime
			) {
		DeductionOffSetTime deductionOffSetTime = new DeductionOffSetTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));
		//控除項目の時間帯（外出かつ法定内のみ）毎に控除相殺時間を計算し、その合計時間を計算する
		deductionOffSetTime.sumDeductionOffSetTime(deductionTimeSheetList.stream()
				  														.filter(tc -> tc.getWithinStatutoryAtr().isWithinStatutory())
				  														.map(tc -> tc.calcGoOutDeductionOffSetTime(statutoryTotalTime, TimeVacationAdditionRemainingTime))
				  														.collect(Collectors.toList()));
		return deductionOffSetTime;
	}
	
	public void calcCoreDuplicateWithDeductionTime() {
		
	}
	
	/**
	 * 計算を行う範囲に存在する控除時間帯の抽出
	 * @param workTimeSpan 計算範囲
	 * @return　計算範囲内に存在する控除時間帯
	 */
	public List<TimeSheetOfDeductionItem> getCalcRange(TimeSpanForCalc workTimeSpan){
		return forDeductionTimeZoneList.stream().filter(tc -> workTimeSpan.contains(tc.calcrange.getSpan())).collect(Collectors.toList());
	}	
	
	
	/**
	 * 流動休憩開始までの間にある外出分、休憩をずらす
	 */
	public void includeUntilFluidBreakTimeStart() {
		
	}
	
	/**
	 * 法定内区分を法定内から法定外へ変更する
	 */
	public void replaceStatutoryAtr() {
		this.withinStatutoryAtr = WithinStatutoryAtr.ExcessOfStatutory;
	}
	
	
	/**
	 * 控除時間帯の仮確定(流動用) 
	 * @author ken_takasu
	 */
	public void provisionalDecisionOfDeductionTimeSheet(FluidWorkSetting fluidWorkSetting) {
		//固定休憩か流動休憩か確認する
		if(fluidWorkSetting.getWeekdayWorkTime().getRestTime().getUseFixedRestTime()) {//固定休憩の場合
			switch(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidPrefixBreakTimeSet().getCalcMethod()) {
				//マスタを参照する
				case ReferToMaster:
				this.createDedctionTimeSheet(acqAtr, setMethod, clockManage, dailyGoOutSheet, oneDayRange, CommonSet, attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fluidSet);
				//予定を参照する
				case ReferToSchedule:
				this.createDedctionTimeSheet(acqAtr, setMethod, clockManage, dailyGoOutSheet, oneDayRange, CommonSet, attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fluidSet);
				//参照せずに打刻する
				case StampWithoutReference:
				this.createDedctionTimeSheet(acqAtr, setMethod, clockManage, dailyGoOutSheet, oneDayRange, CommonSet, attendanceLeaveWork, fixedCalc, workTimeDivision, noStampSet, fluidSet);
			}
		}else{//流動休憩の場合
			switch(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidBreakTimeSet().getCalcMethod()) {
				//マスタを参照する
				case ReferToMaster:
				
				//マスタと打刻を併用する	
				case ConbineMasterWithStamp:
				
				//参照せずに打刻する	
				//case StampWithoutReference:
			
			}
		}
		
	}
	
	
	/**
	 * 控除時間帯の作成   流動勤務で固定休憩の場合にシフトから計算する場合の処理の事
	 * @author ken_takasu
	 */
	public void createLateTimeSheetForFluid(
			WithinWorkTimeFrame withinWorkTimeFrame,
			FluidWorkSetting fluidWorkSetting,
			CalculationRangeOfOneDay oneDayRange) {
		
		//計算範囲の取得
		oneDayRange.
		//控除時間帯の取得　・・・保科君が作成済みの処理を呼ぶ
		List<TimeSheetOfDeductionItem> deductionTimeSheet = this.collectDeductionTimes(
				dailyGoOutSheet, 
				oneDayRange, 
				CommonSet, 
				attendanceLeaveWork, 
				fixedCalc, 
				workTimeDivision, 
				noStampSet, 
				fluidSet, 
				acqAtr);
		//控除時間帯同士の重複部分を補正
		deductionTimeSheet = new DeductionTimeSheetAdjustDuplicationTime(deductionTimeSheet).reCreate(setMethod, clockManage);
		//控除合計時間クラスを作成　　不要な可能性あり
		DeductionTotalTimeForFluidCalc deductionTotalTime = new DeductionTotalTimeForFluidCalc();
		//流動休憩時間帯を取得する
		List<FluRestTimeSetting> fluRestTimeSheetList = 
				fluidWorkSetting.getWeekdayWorkTime().getRestTime().getFluidRestTime().getFluidRestTimes();
		//外出取得開始時刻を作成する
		AttendanceTime getGoOutStartClock = new AttendanceTime(withinWorkTimeFrame.getCalcrange().getStart().valueAsMinutes());
		//一時的に作成
		int roopNo = 0;
		//一時的に作成
		List<TimeSheetOfDeductionItem> restTimeSheetList = new ArrayList<>();
		//流動休憩時間帯分ループ
		for(FluRestTimeSetting fluRestTimeSetting : fluRestTimeSheetList) {
			roopNo++;
			//外出のみの控除時間帯リストを作成する
			List<TimeSheetOfDeductionItem> goOutDeductionTimelist = 
					this.forDeductionTimeZoneList.stream().filter(ts -> ts.getGoOutReason().isPresent()).collect(Collectors.toList());		
			//流動休憩時間帯の作成（引数にgetGoOutStartClockを渡す
			TimeSheetOfDeductionItem restTimeSheet = deductionTotalTime.createDeductionFluidRestTime(
					fluRestTimeSetting, 
					getGoOutStartClock, 
					ｔimeSheetOfDeductionItem, 
					roopNo, 
					fluidWorkSetting, 
					deductionTimeSheet, 
					goOutDeductionTimelist);
			//作成した時間帯を休憩時間帯リストに格納
			restTimeSheetList.add(restTimeSheet);
		}
		//退勤時刻までの外出の処理
		deductionTotalTime.collectDeductionTotalTime(list, getGoOutStartClock, fluidWorkSetting, roopNo);
		//控除時間帯をソート
		
	}
	
	
	/**
	 * 控除時間中の時間休暇相殺時間の計算
	 * @author ken_takasu
	 * @return
	 */
	public DeductionOffSetTime calcTotalDeductionOffSetTime(
			LateTimeOfDaily lateTimeOfDaily,
			LateTimeSheet lateTimeSheet,
			LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
			LeaveEarlyTimeSheet LeaveEarlyTimeSheet
			) {
		
		//遅刻相殺時間の計算
		DeductionOffSetTime lateDeductionOffSetTime = lateTimeSheet.calcDeductionOffSetTime(lateTimeOfDaily.getTimePaidUseTime(),DeductionAtr.Deduction);	

		//早退相殺時間の計算
		DeductionOffSetTime leaveEarlyDeductionOffSetTime = LeaveEarlyTimeSheet.calcDeductionOffSetTime(leaveEarlyTimeOfDaily.getTimePaidUseTime(),DeductionAtr.Deduction);
		
		//外出相殺時間の計算（法定内）
		DeductionOffSetTime statutoryDeductionOffSetTime = calcStatutoryDeductionOffSetTime(
				createDeductionTimeSheetList(WithinStatutoryAtr.WithinStatutory),
				this.goOutTimeSheet.getDailyOfGoOutTime().getForDeductionTotalTime().getWithinStatutoryTotalTime().getCalcTime().valueAsMinutes(),
				this.goOutTimeSheet.getDailyOfGoOutTime().getHolidayUseTime());
		
		//外出相殺時間の計算（法定外）
		DeductionOffSetTime excessOfStatutoryDeductionOffSetTime = calcStatutoryDeductionOffSetTime(
				createDeductionTimeSheetList(WithinStatutoryAtr.ExcessOfStatutory),
				this.goOutTimeSheet.getDailyOfGoOutTime().getForDeductionTotalTime().getExcessOfStatutoryTotalTime().getCalcTime().valueAsMinutes(),
				this.goOutTimeSheet.getDailyOfGoOutTime().getHolidayUseTime());
				
		//相殺時間を合算する
		DeductionOffSetTime timeVacationOffSetTime = new DeductionOffSetTime(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));
		List<DeductionOffSetTime> deductionOffSetTimeList = new ArrayList<>();
		deductionOffSetTimeList.add(lateDeductionOffSetTime);
		deductionOffSetTimeList.add(leaveEarlyDeductionOffSetTime);
		deductionOffSetTimeList.add(statutoryDeductionOffSetTime);
		deductionOffSetTimeList.add(excessOfStatutoryDeductionOffSetTime);
		timeVacationOffSetTime.sumDeductionOffSetTime(deductionOffSetTimeList);
		
		return timeVacationOffSetTime;
	}
	
	
	/**
	 * 指定した法定内区分に一致する外出のみの控除項目の時間帯Listを返す
	 * @author ken_takasu
	 * @param withinStatutoryAtr
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> createDeductionTimeSheetList(WithinStatutoryAtr withinStatutoryAtr){
		List<TimeSheetOfDeductionItem> deductionTimeSheetList;
		switch(withinStatutoryAtr) {
			case WithinStatutory:
				deductionTimeSheetList = this.forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																				.filter(tc -> tc.getWithinStatutoryAtr().isWithinStatutory())
																				.collect(Collectors.toList());
			case ExcessOfStatutory:
				deductionTimeSheetList = this.forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																				.filter(tc -> tc.getWithinStatutoryAtr().isExcessOfStatutory())
																				.collect(Collectors.toList());
			default:
				throw new RuntimeException("unknown DeductionAtr" + withinStatutoryAtr);
		}
		return deductionTimeSheetList;
	}
	
}
