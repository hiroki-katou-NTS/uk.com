package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.record.dom.daily.OverTimeWorkOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionAtr;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DeductionTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.WithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationOfOverTimeWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.OverDayEndCalcSet;
import nts.uk.ctx.at.shared.dom.workrule.overtimework.StatutoryPrioritySet;
import nts.uk.ctx.at.shared.dom.worktime.commomsetting.overworkset.StatutoryOverTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidOverTimeWorkSheet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.FluidWorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.WorkTimeCommonSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 残業時間帯
 * @author keisuke_hoshina
 *
 */
public class OverTimeWorkSheet {
	
	private static OverTimeWorkOfDaily dailyOverWorkTime; 
	
	private StatutoryOverTimeWorkSet statutoryOverWorkSet;
	
	private DailyTime dailyTime;
	
	private  OverTimeWorkSheet(OverTimeWorkOfDaily dailyOverWork) {
		this.dailyOverWorkTime = dailyOverWork;
	}
	
	

	/**
	 * 残業枠分ループし残業枠時間帯の作成
	 * @param overTimeHourSetList 固定勤務の時間帯設定クラス
	 * @param workingSystem　労働制クラス
	 * @param attendanceLeave 出退勤クラス
	 * @param secondStartTime 2回目の勤務の開始時間
	 * @param workNo　現在処理をしている勤務回数
	 * @return
	 */
	public static OverDayEnd.SplitOverTimeWork createOverWorkFrame(List<OverTimeHourSet> overTimeHourSetList,WorkingSystem workingSystem,
												AttendanceLeavingWork attendanceLeave,TimeWithDayAttr secondStartTime,int workNo,
												BreakdownTimeDay breakdownTimeDay,DailyTime dailyTime,AutoCalculationOfOverTimeWork autoCalculationSet,
												StatutoryOverTimeWorkSet statutorySet,OverDayEndCalcSet dayEndSet,WorkTimeCommonSet overDayEndSet ,List<OverTimeWorkFrameTimeSheet> overTimeWorkItem,
												WorkType beforeDay,WorkType toDay,WorkType afterDay, StatutoryPrioritySet prioritySet ) {
		List<OverTimeWorkFrameTimeSheet> createTimeSheet = new ArrayList<>();
		for(OverTimeHourSet overTimeHourSet:overTimeHourSetList) {
			if(overTimeHourSet.getTimeSpan().contains(attendanceLeave.getTimeSpan()));
				createTimeSheet.add(OverTimeWorkFrameTimeSheet.createOverWorkFramTimeSheet(overTimeHourSet
																						  ,overTimeHourSet.getTimeSpan().getDuplicatedWith(attendanceLeave.getTimeSpan()).get()));
		}
		/*変形残業　振替*/
		List<OverTimeWorkFrameTimeSheet> afterVariableWork = new ArrayList<>();
		afterVariableWork = dicisionCalcVariableWork(workingSystem,createTimeSheet,breakdownTimeDay,dailyTime,autoCalculationSet);
		/*法定内残業　振替*/
		List<OverTimeWorkFrameTimeSheet> afterCalcStatutoryOverTimeWork = new ArrayList<>();
		afterCalcStatutoryOverTimeWork = diciaionCalcStatutory(statutorySet ,dailyTime ,afterVariableWork,autoCalculationSet, prioritySet);
		/*0時跨ぎ*/
		
		OverDayEnd processOverDayEnd = new OverDayEnd();
		OverDayEnd.SplitOverTimeWork process = processOverDayEnd.new SplitOverTimeWork(dayEndSet,overDayEndSet ,afterCalcStatutoryOverTimeWork,beforeDay,toDay,afterDay);
		/*return*/
		return process;
	}
	
	/**
	 * 変形基準内時間の計算をするか判定
	 * @param workingSystem 労働制
	 */
	public static List<OverTimeWorkFrameTimeSheet> dicisionCalcVariableWork(WorkingSystem workingSystem,List<OverTimeWorkFrameTimeSheet> overTimeWorkFrameTimeSheetList
															,BreakdownTimeDay breakdownTimeDay,DailyTime dailyTime,AutoCalculationOfOverTimeWork autoCalculationSet) {
		if(workingSystem.isVariableWorkingTimeWork()) {
			/*振替処理  基準内残業時間を計算する*/
			return reclassified(calcDeformationCriterionOvertime(dailyTime.valueAsMinutes(),breakdownTimeDay.getPredetermineWorkTime()),overTimeWorkFrameTimeSheetList,autoCalculationSet);
		}
		return overTimeWorkFrameTimeSheetList;
	}
	/**
	 * 法定内残業時間の計算をするか判定
	 * @param statutoryOverWorkSet 法定内残業設定クラス
	 */
	public static List<OverTimeWorkFrameTimeSheet> diciaionCalcStatutory(StatutoryOverTimeWorkSet statutorySet,DailyTime dailyTime,List<OverTimeWorkFrameTimeSheet> overTimeWorkFrameTimeSheetList
																	,AutoCalculationOfOverTimeWork autoCalculationSet, StatutoryPrioritySet prioritySet) {
		if(statutorySet.isAutoCalcStatutoryOverWork()) {
			List<OverTimeWorkFrameTimeSheet> copyList = new ArrayList<>();
			if(prioritySet.isPriorityNormal()) {
				/*普通を優先*/
				copyList.addAll(overTimeWorkFrameTimeSheetList.stream().filter(tc -> !tc.isGoEarly()).collect(Collectors.toList()));
				copyList.addAll(overTimeWorkFrameTimeSheetList.stream().filter(tc -> tc.isGoEarly()).collect(Collectors.toList()));
			}else {
				/*早出を優先*/
				copyList.addAll(overTimeWorkFrameTimeSheetList.stream().filter(tc -> tc.isGoEarly()).collect(Collectors.toList()));
				copyList.addAll(overTimeWorkFrameTimeSheetList.stream().filter(tc -> !tc.isGoEarly()).collect(Collectors.toList()));
			}
			/*振替処理   法定内基準時間を計算する*/
			return reclassified(calcDeformationCriterionOvertime(dailyTime.valueAsMinutes(),),copyList ,autoCalculationSet);
		}
		return overTimeWorkFrameTimeSheetList;
	}

	

	/**
	 * 振替処理
	 * @param ableRangeTime 振替できる時間
	 * @param overTimeWorkFrameTimeSheetList　残業時間枠時間帯クラス
	 * @param autoCalculationSet　時間外の自動計算設定
	 */
	public static List<OverTimeWorkFrameTimeSheet> reclassified(int ableRangeTime,List<OverTimeWorkFrameTimeSheet> overTimeWorkFrameTimeSheetList,AutoCalculationOfOverTimeWork autoCalculationSet) {
		int overTime = 0;
		int transTime = 0;
		boolean loopEnd = false;
		List<OverTimeWorkFrameTimeSheet> copyList = overTimeWorkFrameTimeSheetList;
		while(!loopEnd) {
			/*残業時間帯分のループ*/
			loopEnd = true;
			for(int number = 0; number < overTimeWorkFrameTimeSheetList.size(); number++) {
				if(decisionCalcAtr(overTimeWorkFrameTimeSheetList.get(number),autoCalculationSet)) {
					overTime = overTimeWorkFrameTimeSheetList.get(number).calcTotalTime();
				}
				else {
					overTime = 0;
				}
			
				if(ableRangeTime >= overTime) {
					transTime = overTime;
				}
				
				TimeWithDayAttr endTime = reCreateSiteiTimeFromStartTime(transTime,copyList.get(number));
				int copyListSize = overTimeWorkFrameTimeSheetList.size();
				/*ここで分割*/
				overTimeWorkFrameTimeSheetList = correctTimeSpan(overTimeWorkFrameTimeSheetList.get(number).gege(endTime),overTimeWorkFrameTimeSheetList,number);
				if(copyList.size() != copyListSize) {
					loopEnd = false;
					break;
				}
				ableRangeTime -= transTime; 
				if(ableRangeTime <= 0) {
					loopEnd = true;
					break;
				}
				else {
					loopEnd = false;
				}
			}
			copyList = overTimeWorkFrameTimeSheetList;
		}
		return copyList;
	}
	
	/**
	 * 開始から指定時間経過後の終了時刻を取得
	 * @param transTime
	 * @return
	 */
	public static TimeWithDayAttr reCreateSiteiTimeFromStartTime(int transTime,OverTimeWorkFrameTimeSheet overTimeWork) {
		return overTimeWork.reCreateTreatAsSiteiTimeEnd(transTime,overTimeWork).getEnd();
	}

	/**
	 * 振替にできる時間の計算
	 * @param statutoryTime 法定労働時間
	 * @param 
	 * @return 変形基準内残業にできるじかん
	 */
	public  static int calcDeformationCriterionOvertime(int statutoryTime,int predetermineWorkTime) {
		return statutoryTime - predetermineWorkTime;
	}

	/**
	 * 計算区分の判定処理
	 * @return 打刻から計算する
	 */
	public static boolean decisionCalcAtr(OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet,AutoCalculationOfOverTimeWork autoCalculationSet) {
		if(overTimeWorkFrameTimeSheet.getWithinStatutoryAtr().isWithinStatutory()) {
			if(overTimeWorkFrameTimeSheet.isGoEarly()) {
				/*早出残業区分を参照*/
				return autoCalculationSet.getEarlyOvertimeHours().getCalculationClassification().isCalculateEmbossing();
			}
			else {
				/*普通残業計算区分を参照*/
				return autoCalculationSet.getNormalOvertimeHours().getCalculationClassification().isCalculateEmbossing();
			}
		}
		else {
			/*法定内の場合*/
			return autoCalculationSet.getLegalOvertimeHours().getCalculationClassification().isCalculateEmbossing();
		}
	}
	
	/**
	 * 分割後の残業時間枠時間帯を受け取り
	 * @param insertList　補正した時間帯
	 * @param originList　補正する前の時間帯
	 * @return　
	 */
	public static List<OverTimeWorkFrameTimeSheet> correctTimeSpan(List<OverTimeWorkFrameTimeSheet> insertList,List<OverTimeWorkFrameTimeSheet> originList,int nowNumber){
		originList.remove(nowNumber);
		originList.addAll(insertList);
		return originList;
	}
	
	
	/**
	 * 残業時間の計算(残業時間帯の合計の時間を取得し1日の範囲に返す)
	 * @return
	 */
	public static OverTimeWorkOfDaily calcOverTimeWork(AutoCalculationOfOverTimeWork autoCalcSet) {
		ControlOverFrameTime returnClass = new ControlOverFrameTime(dailyOverWorkTime.collectOverTimeWorkTime(autoCalcSet));
		
		dailyOverWorkTime.addToList(returnClass);
		
		return  dailyOverWorkTime;
	}
	
	/**
	 * 深夜時間計算後の時間帯再作成
	 * @return
	 */
	public OverTimeWorkSheet reCreateToCalcExcessWork() {
		
	}
	
	
	
	//＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊

//	/**
//	 * 流動勤務（就外、平日）
//	 * @return
//	 */
//	public OverTimeWorkSheet createOverTimeWorkSheet(
//			TimeSpanForCalc calcRange,/*1日の計算範囲の計算範囲*/
//			WithinWorkTimeSheet withinWorkTimeSheet,/*流動勤務（平日・就内）で作成した就業時間内時間帯*/
//			DeductionTimeSheet deductionTimeSheet,
//			FluidWorkTimeSetting fluidWorkTimeSetting
//			) {
//		
//		//計算範囲の取得
//		TimeSpanForCalc timeSpan = new TimeSpanForCalc(
//				withinWorkTimeSheet.getFrameAt(0).getTimeSheet().getEnd(),
//				calcRange.getEnd());
//		
//		//控除時間帯を取得　（保科くんが作ってくれた処理を呼ぶ）
//		
//		//残業枠の開始時刻
//		TimeWithDayAttr startClock = calcRange.getStart();
//		//残業枠設定分ループ
//		for(FluidOverTimeWorkSheet fluidOverTimeWorkSheet: fluidWorkTimeSetting.overTimeWorkSheet) {
//			//残業枠n+1の経過時間を取得
////			AttendanceTime nextElapsedTime = getnextElapsedTime(
////					fluidOverTimeWorkSheet,
////					fluidWorkTimeSetting,
////					new AttendanceTime(calcRange.lengthAsMinutes()));
//			//控除時間から残業時間帯を作成
//			OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet;
//			
//			
//			//次の残業枠の開始時刻に終了時刻を入れる。
//			startClock = overTimeWorkFrameTimeSheet.getTimeSheet().getEnd();
//		}
//		//時間休暇溢れ分の割り当て
//			
//		
//	}
	
	
	/**
	 * 残業枠ｎ+1．経過時間を取得する
	 * @param fluidOverTimeWorkSheet
	 * @param fluidWorkTimeSetting
	 * @param timeOfCalcRange
	 * @return
	 */
	public AttendanceTime getnextElapsedTime(
			FluidOverTimeWorkSheet fluidOverTimeWorkSheet,
			FluidWorkTimeSetting fluidWorkTimeSetting,
			AttendanceTime timeOfCalcRange) {
		int nextOverWorkTimeNo = fluidOverTimeWorkSheet.getOverWorkTimeNo() + 1;
		AttendanceTime nextlapsedTime;
		Optional<FluidOverTimeWorkSheet> nextFluidOverTimeWorkSheet = 
				fluidWorkTimeSetting.getMatchWorkNoOverTimeWorkSheet(nextOverWorkTimeNo);
		if(nextFluidOverTimeWorkSheet==null) {
			nextlapsedTime = timeOfCalcRange;
			return nextlapsedTime;
		}
		nextlapsedTime = nextFluidOverTimeWorkSheet.get().getFluidWorkTimeSetting().getElapsedTime();
		return nextlapsedTime;
	}
	
	
	
	
}
