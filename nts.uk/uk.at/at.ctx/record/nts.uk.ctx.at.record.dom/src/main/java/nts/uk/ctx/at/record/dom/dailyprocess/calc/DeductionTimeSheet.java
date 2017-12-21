package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DeductionTotalTime;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.BreakSetOfCommon;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.CalcMethodIfLeaveWorkDuringBreakTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.set.FixRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flexworkset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluRestTime;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.FluidPrefixBreakTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.RestClockManageAtr;

/**
 * 控除時間帯
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
@Getter
public class DeductionTimeSheet {
	//控除用
	private final List<TimeSheetOfDeductionItem> forDeductionTimeZoneList;
	//計上用
	private final List<TimeSheetOfDeductionItem> forRecordTimeZoneList;
	private final BreakTimeManagement breakTimeSheet;
	private final GoOutManagement goOutTimeSheet;
	
//	public void devideDeductionsBy(ActualWorkingTimeSheet actualWorkingTimeSheet,BreakManagement breakTimeSheet) {
//
//		devide(this.forDeductionTimeZoneList, actualWorkingTimeSheet);
//		devide(this.forRecordTimeZoneList, actualWorkingTimeSheet);
//	}
//	
//	private static void devide(
//			List<TimeSheetOfDeductionItem> source,
//			ActualWorkingTimeSheet devider) {
//		
//		val devided = new ArrayList<TimeSheetOfDeductionItem>();
//		source.forEach(deductionTimeZone -> {
//			devided.addAll(deductionTimeZone.devideIfContains(devider.start()));
//			devided.addAll(deductionTimeZone.devideIfContains(devider.end()));
//		});
//		
//		source.clear();
//		source.addAll(devided);
//	}
//	
	

	public List<TimeSheetOfDeductionItem> provisionalDecisionOfDeductionTimeSheet(
			WithinWorkTimeFrame withinWorkTimeFrame, CalculationRangeOfOneDay oneDayRange,
			CalcSetOfDailyPerformance calcSet, TimeLeavingWork timeLeavingWork, FlowRestCalcMethod fluidSet,
			CalcMethodIfLeaveWorkDuringBreakTime duringBreakTime, AcquisitionConditionsAtr acqAtr,
			WorkTimeMethodSet setMethod, RestClockManageAtr clockManage, OutingTimeOfDailyPerformance dailyGoOutSheet,
			BreakSetOfCommon commonSet, TimeLeavingOfDailyPerformance attendanceLeaveWork, FixRestCalcMethod fixedCalc,
			WorkTimeDivision workTimeDivision, FluidPrefixBreakTimeSet noStampSet, FlowRestCalcMethod fluidSet2,
			BreakTimeManagement breakmanage, Optional<FluRestTime> fluRestTime,
			FluidPrefixBreakTimeSet fluidprefixBreakTimeSet, RestClockManageAtr restClockManageAtr,
			ShortTimeWorkManagement shortTimeWorkManagement, WorkTimeCommonSet workTimeCommonSet,
			FlowRestSet flowRestSet) {
		// TODO Auto-generated method stub
		return null;
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
	public static DeductionTimeSheet createDedctionTimeSheet(AcquisitionConditionsAtr acqAtr,WorkTimeMethodSet setMethod,RestClockManageAtr clockManage,
			OutingTimeOfDailyPerformance dailyGoOutSheet,TimeSpanForCalc oneDayRange,BreakSetOfCommon CommonSet, TimeLeavingOfDailyPerformance attendanceLeaveWork
								,FixRestCalcMethod fixedCalc,WorkTimeDivision workTimeDivision,FluidPrefixBreakTimeSet noStampSet, FlowRestCalcMethod fluidSet, BreakTimeManagement breakmanage
								, Optional<FluRestTime> fluRestTime,FluidPrefixBreakTimeSet fluidprefixBreakTimeSet){
		
		/*控除時間帯取得　控除時間帯リストへコピー*/
		List<TimeSheetOfDeductionItem> useDedTimeSheet = collectDeductionTimes(dailyGoOutSheet,oneDayRange,CommonSet
				,attendanceLeaveWork,fixedCalc,workTimeDivision,noStampSet,fluidSet,acqAtr,breakmanage
				,workTimeDivision.getWorkTimeMethodSet(),fluRestTime,fluidprefixBreakTimeSet);
		
		/*重複部分補正処理*/
		useDedTimeSheet = new DeductionTimeSheetAdjustDuplicationTime(useDedTimeSheet).reCreate(setMethod, clockManage,workTimeDivision.getWorkTimeDailyAtr());
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
		
		return new DeductionTimeSheet(goOutDeletedList, recordDedTimeSheet, breakmanage);
	}

	

	/**
	 * 控除時間に該当する項目を集め控除時間帯を作成する
	 * @param dailyGoOutSheet 日別実績の外出時間帯
	 * @param oneDayRange 計算時間帯 
	 * @param CommonSet 共通の休憩設定
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @param fixedCalc 固定休憩の計算方法
	 * @param workTimeDivision 就業時間帯勤務区分
	 * @param fluidSet 流動休憩の計算方法
	 * @param acqAtr 取得条件区分
	 * @param breakManagement 休憩管理
	 * @param workTimeMethodSet 就業時間帯の設定方法
	 * @param fluRestTime FluRestTime 流動勤務の休憩時間帯
	 * @param fluidprefixBreakTimeSet 流動固定休憩設定
	 * @param shortTimeWorkManagement 短時間勤務管理
	 * @param workTimeCommonSet  就業時間帯の共通設定
	 * @return
	 */
	public static List<TimeSheetOfDeductionItem> collectDeductionTimes(OutingTimeOfDailyPerformance dailyGoOutSheet,TimeSpanForCalc oneDayRange,BreakSetOfCommon CommonSet
										, TimeLeavingOfDailyPerformance attendanceLeaveWork,FixRestCalcMethod fixedCalc,WorkTimeDivision workTimeDivision
										, FlowRestCalcMethod fluidSet, AcquisitionConditionsAtr acqAtr , BreakTimeManagement breakManagement
										,Optional<FluRestTime> fluRestTime,FluidPrefixBreakTimeSet fluidprefixBreakTimeSet
										,ShortTimeWorkManagement shortTimeWorkManagement,WorkTimeCommonSet workTimeCommonSet) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>(); 
		/*休憩時間帯取得*/
		sheetList.addAll(breakManagement.getBreakTimeSheet(workTimeDivision, fixedCalc, fluidprefixBreakTimeSet, fluidSet));
		/*外出時間帯取得*/
		sheetList.addAll(dailyGoOutSheet.removeUnuseItemBaseOnAtr(acqAtr,workTimeDivision.getWorkTimeMethodSet(),fluRestTime,fluidprefixBreakTimeSet));
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
												,TimeLeavingOfDailyPerformance attendanceLeaveWork) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>(); 
		for(TimeSheetOfDeductionItem timeSheet: dedTimeSheets){
			switch(timeSheet.getDeductionAtr()) {
			case CHILD_CARE:
			case GO_OUT:
				Optional<TimeSpanForCalc> duplicateGoOutSheet = oneDayRange.getDuplicatedWith(timeSheet.calcrange);
				if(duplicateGoOutSheet.isPresent()) {
						/*ここで入れる控除、加給、特定日、深夜は duplicateGoOutSheetと同じ範囲に絞り込む*/
						sheetList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
																						  timeSheet.timeSheet
																						, duplicateGoOutSheet.get()
																						, timeSheet.deductionTimeSheet
																						, timeSheet.bonusPayTimeSheet
																						, timeSheet.specBonusPayTimesheet
																						, timeSheet.midNightTimeSheet
																						, timeSheet.getGoOutReason()
																						, timeSheet.getBreakAtr()
																						, timeSheet.getDeductionAtr()));
						
				}
			case BREAK:
			
				List<TimeSpanForCalc> duplicateBreakSheet = timeSheet.getBreakCalcRange(attendanceLeaveWork.getTimeLeavingWorks(),calcMethod,oneDayRange.getDuplicatedWith(timeSheet.calcrange));
				if(!duplicateBreakSheet.isEmpty())
				{
					duplicateBreakSheet.forEach(tc -> {
						/*ここで入れる控除、加給、特定日、深夜は duplicateGoOutSheetと同じ範囲に絞り込む*/
						sheetList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
																				  timeSheet.getTimeSheet()
																				, timeSheet.calcrange
																				, timeSheet.deductionTimeSheet
																				, timeSheet.bonusPayTimeSheet
																				, timeSheet.specBonusPayTimesheet
																				, timeSheet.midNightTimeSheet
																				, timeSheet.getGoOutReason()
																				, timeSheet.getBreakAtr()
																				, timeSheet.getDeductionAtr()));
					});
				}
			default:
				throw new RuntimeException("unknown deductionAtr:" + timeSheet.getDeductionAtr());
			}
		}
		return sheetList;
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
			break;
		case Deduction:
			dedTotalTime = getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()));
			break;
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
		List<DeductionTotalTime> dedTotalTimeList = new ArrayList<>();
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
		AttendanceTime statutoryTotalTime         = calcDeductionTotalTime(deductionTimeSheetList.stream()
																					  //.filter(tc -> tc)　一時的に
																					  .collect(Collectors.toList()));
		AttendanceTime excessOfStatutoryTotalTime = calcDeductionTotalTime(deductionTimeSheetList.stream()
																					  //.filter(tc -> tc.getWithinStatutoryAtr().isExcessOfStatutory()) 一時的に
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
	 * 法定内区分を法定外へ変更する
	 * @return　法定内区分変更後の控除時間帯
	 */
	public List<TimeSheetOfDeductionItem> replaceStatutoryAtrToExcess() {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for(TimeSheetOfDeductionItem deductionTimeSheet : forDeductionTimeZoneList) {
			returnList.add(deductionTimeSheet.createWithExcessAtr());
		}
		return returnList;
	}
	
	/**
	 * 流動休憩開始までの間にある外出分、休憩をずらす
	 */
	public void includeUntilFluidBreakTimeStart() {
		
	}

	
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
	
	/**
	 * 控除時間帯の仮確定(流動用) 
	 * @author ken_takasu
	 */
	public List<TimeSheetOfDeductionItem> provisionalDecisionOfDeductionTimeSheet(FluidWorkSetting fluidWorkSetting,DeductionClassification deductionClassification,
			WithinWorkTimeFrame withinWorkTimeFrame,
			CalculationRangeOfOneDay oneDayRange,
			CalcSetOfDailyPerformance calcSet,
			TimeLeavingWork timeLeavingWork,
			FlowRestClockCalcMethod flowRestCalcMethod,
			CalcMethodIfLeaveWorkDuringBreakTime duringBreakTime,
			AcquisitionConditionsAtr acqAtr,
			WorkTimeMethodSet setMethod,
			RestClockManageAtr clockManage,
			OutingTimeOfDailyPerformance dailyGoOutSheet,
			BreakSetOfCommon commonSet, 
			TimeLeavingOfDailyPerformance attendanceLeaveWork,
			FixRestCalcMethod fixedCalc,
			WorkTimeDivision workTimeDivision,
			FluidPrefixBreakTimeSet noStampSet, 
			FlowRestCalcMethod fluidSet, 
			BreakTimeManagement breakmanage, 
			Optional<FluRestTime> fluRestTime,
			FluidPrefixBreakTimeSet fluidprefixBreakTimeSet,
			RestClockManageAtr restClockManageAtr,
			ShortTimeWorkManagement shortTimeWorkManagement,
			WorkTimeCommonSet workTimeCommonSet,
			FlowRestSet flowRestSet
								) {
		List<TimeSheetOfDeductionItem> deductionList = new ArrayList();
		//固定休憩か流動休憩か確認する
		if(fluidWorkSetting.getWeekdayWorkTime().getRestTime().getUseFixedRestTime()) {//固定休憩の場合
			switch(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidPrefixBreakTimeSet().getCalcMethod()) {
				//マスタを参照する
				case ReferToMaster:
					this.collectDeductionTimes(dailyGoOutSheet,
											   oneDayRange.getOneDayOfRange(),
											   commonSet,
											   attendanceLeaveWork,
											   fixedCalc,
											   workTimeDivision,
											   fluidSet,
											   acqAtr,
											   breakmanage,
											   fluRestTime,
											   fluidprefixBreakTimeSet,
											   shortTimeWorkManagement,
											   workTimeCommonSet
											   );
				//予定を参照する
				case ReferToSchedule:
					this.collectDeductionTimes(dailyGoOutSheet,
							   oneDayRange.getOneDayOfRange(),
							   commonSet,
							   attendanceLeaveWork,
							   fixedCalc,
							   workTimeDivision,
							   fluidSet,
							   acqAtr,
							   breakmanage,
							   fluRestTime,
							   fluidprefixBreakTimeSet,
							   shortTimeWorkManagement,
							   workTimeCommonSet
							   );
				//参照せずに打刻する
				case StampWithoutReference:
					this.collectDeductionTimes(dailyGoOutSheet,
							   oneDayRange.getOneDayOfRange(),
							   commonSet,
							   attendanceLeaveWork,
							   fixedCalc,
							   workTimeDivision,
							   fluidSet,
							   acqAtr,
							   breakmanage,
							   fluRestTime,
							   fluidprefixBreakTimeSet,
							   shortTimeWorkManagement,
							   workTimeCommonSet
							   );
			}
		}else{//流動休憩の場合
			switch(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidBreakTimeSet().getCalcMethod()) {
				//マスタを参照する
				case ReferToMaster:
					return createLateTimeSheetForFluid(withinWorkTimeFrame,
													   fluidWorkSetting,
													   oneDayRange,
													   calcSet,
													   timeLeavingWork,
													   flowRestCalcMethod,
													   duringBreakTime,
													   workTimeDivision,
													   restClockManageAtr,
													   dailyGoOutSheet,
													   CommonSet,
													   attendanceLeaveWork,
													   fixedCalc,
													   fluidSet, 
													   acqAtr , 
													   breakmanage, 
													   fluRestTime,
													   fluidprefixBreakTimeSet,
													   shortTimeWorkManagement,
													   workTimeCommonSet,
													   flowRestSet);
				//マスタと打刻を併用する	
				case ConbineMasterWithStamp:
				
				//参照せずに打刻する	
				//case StampWithoutReference:
			
			}
		}
		
	}
	
	
	/**
	 * 控除時間帯の作成   流動勤務で固定休憩の場合にシフトから計算する場合の処理の事
	 */
	public List<TimeSheetOfDeductionItem> createLateTimeSheetForFluid(
			WithinWorkTimeFrame withinWorkTimeFrame,
			FluidWorkSetting fluidWorkSetting,
			CalculationRangeOfOneDay oneDayRange,
			CalcSetOfDailyPerformance calcSet,
			TimeLeavingWork timeLeavingWork,
			FlowRestClockCalcMethod flowRestCalcMethod,
			CalcMethodIfLeaveWorkDuringBreakTime duringBreakTime,
			WorkTimeDivision workDivision,
			RestClockManageAtr restClockManageAtr,
			OutingTimeOfDailyPerformance dailyGoOutSheet,
			BreakSetOfCommon CommonSet,
			TimeLeavingOfDailyPerformance attendanceLeaveWork,
			FixRestCalcMethod fixedCalc,
			FlowRestCalcMethod fluidSet, 
			AcquisitionConditionsAtr acqAtr , 
			BreakTimeManagement breakManagement, 
			Optional<FluRestTime> fluRestTime,
			FluidPrefixBreakTimeSet fluidprefixBreakTimeSet,
			ShortTimeWorkManagement shortTimeWorkManagement,
			WorkTimeCommonSet workTimeCommonSet,
			FlowRestSet flowRestSet
			) {
		
		//計算範囲の取得
		AttendanceTime calcRange= oneDayRange.getPredetermineTimeSetForCalc().getOneDayRange();
		//控除時間帯の取得　・・・保科君が作成済みの処理を呼ぶ
		List<TimeSheetOfDeductionItem> deductionTimeSheet = this.collectDeductionTimes(
				dailyGoOutSheet,
				oneDayRange.getOneDayOfRange(),
				CommonSet,
				attendanceLeaveWork,
				fixedCalc,
				workDivision,
				fluidSet,
				acqAtr,
				breakManagement,
				fluRestTime,
				fluidprefixBreakTimeSet,
				shortTimeWorkManagement,
				workTimeCommonSet
				);
		//控除時間帯同士の重複部分を補正
		deductionTimeSheet = new DeductionTimeSheetAdjustDuplicationTime(deductionTimeSheet)
									.reCreate(workDivision.getWorkTimeMethodSet(),restClockManageAtr,workDivision.getWorkTimeDailyAtr());
		//控除合計時間クラスを作成　　不要な可能性あり
		//→合計時間を保持しておくためにこのインスタンスは必要(2017.11.27 by hoshina)
		DeductionTotalTimeForFluidCalc deductionTotalTime = new DeductionTotalTimeForFluidCalc(new AttendanceTime(0),new AttendanceTime(0));
		//流動休憩時間帯を取得する
		List<FluRestTimeSetting> fluRestTimeSheetList = 
				fluidWorkSetting.getWeekdayWorkTime().getRestTime().getFluidRestTime().getFluidRestTimes();
		//外出取得開始時刻を作成する(休憩計算開始時刻を取得)
		AttendanceTime getGoOutStartClock = getBreakTimeStartClock();
		//AttendanceTime getGoOutStartClock = new AttendanceTime(withinWorkTimeFrame.getCalcrange().getStart().valueAsMinutes());
		//一時的に作成(最後に控除時間帯へ追加する休憩時間帯リスト)
		List<TimeSheetOfDeductionItem> restTimeSheetListForAddToDeductionList = new ArrayList<>();
		
		//流動休憩時間帯分ループ
		for(int nowLoop = 0 ; nowLoop <fluRestTimeSheetList.size(); nowLoop++) {		

			//流動休憩時間帯の作成（引数にgetGoOutStartClockを渡す
			TimeSheetOfDeductionItem restTimeSheet = deductionTotalTime.createDeductionFluidRestTime(
					fluRestTimeSheetList.get(nowLoop), 
					getGoOutStartClock, 
					nowLoop, 
					fluidWorkSetting, 
					
					deductionTimeSheet,
					
					calcSet.getOutingAndBreakRelationSet()/*外出と流動休憩の関係設定*/,
					flowRestCalcMethod,
					timeLeavingWork,
					duringBreakTime/*外出と流動休憩の関係設定*/,
					workDivision,
					fluRestTime.get(),
					flowRestSet);
			//作成した時間帯を休憩時間帯リストに格納
			restTimeSheetListForAddToDeductionList.add(restTimeSheet);
			getGoOutStartClock = new AttendanceTime(restTimeSheet.calcrange.getStart().valueAsMinutes());
		}
		//退勤時刻までの外出の処理
		//deductionTotalTime.collectDeductionTotalTime(list, getGoOutStartClock, fluidWorkSetting, roopNo);
		//控除時間帯をソート
		deductionTimeSheet.sort((first,second) -> first.calcrange.getStart().compareTo(second.calcrange.getStart()));
		return deductionTimeSheet;
	}

	/**
	 * 休憩計算開始時刻を取得
	 * @return
	 */
	private TimeWithDayAttr getBreakTimeStartClock(Optional<LateTimeSheet> lateTimeSheet,WorkTimeDivision workTimeDivision,Optional<FlexWorkSetting> flex,
												  WorkType workType,TimeLeavingOfDailyPerformance attendanceLeave ) {
		if(lateTimeSheet.isPresent()) {
			return lateTimeSheet.get().getForDeducationTimeSheet().get().calcrange.getEnd();
		}
		else {
			TimeWithDayAttr breakStartTime = new TimeWithDayAttr(0);
			if(workTimeDivision.getWorkTimeDailyAtr().isFlex()) {
				breakStartTime = flex.get().getMostEarlyTime(workType);
				attendanceLeave.getTimeLeavingWorks().stream().sorted((first,second)->(second.getWorkNo().compareTo(first.getWorkNo())));
				if(attendanceLeave.getTimeLeavingWorks().get(0).getTimeSpan().getStart().greaterThan(/*就業時間帯開始時刻*/)) {
					return attendanceLeave.getTimeLeavingWorks().get(0).getTimeSpan().getStart();
				}
				else {
					return /*就業時間帯開始時刻*/;
				}
				
			}
//			else if(workTimeDivision.getWorkTimeMethodSet().isFluidWork()) {
//				
//			}
			else {
				throw new Exception("Isn't flex or fluid ");
			}
		}
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
